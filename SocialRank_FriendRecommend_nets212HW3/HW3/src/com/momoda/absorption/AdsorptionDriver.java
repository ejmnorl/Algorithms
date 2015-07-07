package com.momoda.absorption;
/*
 * 
 * 
 * 启动类，进行各种选项启动
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AdsorptionDriver extends Configured
  implements Tool
{
  private void showUsage(String[] args)
  {
    System.err.println("Usage: SocialRankDriver <init|iter|diff|finish> <input dir> [input dir2] <output dir> [cur-iter#] <reducers>");

    for (String parm : args)
      System.err.println(parm);
  }

  public final int run(String[] args)
    throws Exception
  {
    String command = "";

    if (args.length > 0) {
      command = args[0];
    }

    if ((args.length < 5) || (args.length > 8)) {
    	System.out.println("args size : " + args.length);
      showUsage(args);
      return 1;
    }
    int numReducers = 1;
    try {
      numReducers = Integer.valueOf(args[(args.length - 1)]).intValue();

      if (numReducers < 1) {
        showUsage(args);
        return 1;
      }
    }
    catch (NumberFormatException nf) {
    	System.out.println("nf: " + nf.getMessage());
      showUsage(args);
      return 1;
    }
    int iter = 1;
    if ((command.equals("iter")) || (command.equals("diff"))) {
      try {
        iter = Integer.valueOf(args[(args.length - 2)]).intValue();

        if (iter < 0) {
          showUsage(args);
          return 1;
        }
      }
      catch (NumberFormatException nf) {
        showUsage(args);
        return 1;
      }

    }

    if (command.equals("init"))
      return runInit(args[1], args[2], iter, numReducers);
    if (command.equals("iter"))
      return runIter(args[1], args[2], iter, numReducers);
    if (command.equals("finish"))
      return runFinish(args[1], args[2], iter, numReducers);
    if (command.equals("composite")) {
      return composite(args[1], args[2], args[3], args[4], args[5], iter, numReducers);
    }

    return 0;
  }

  private Job generateRankJob(String input, String output, int iter, int numReducers)
    throws IllegalArgumentException, IOException
  {
    Job rankJob = new Job(super.getConf());

    rankJob.setNumReduceTasks(numReducers);
    rankJob.setJarByClass(AdsorptionDriver.class);

    rankJob.getConfiguration().setInt("iteration", iter);

    FileInputFormat.addInputPath(rankJob, new Path(input));
    Path outputPath = new Path(output);
    FileOutputFormat.setOutputPath(rankJob, outputPath);
    try
    {
      FileSystem fs = FileSystem.get(rankJob.getConfiguration());
      if (fs.exists(outputPath)) {
        fs.delete(outputPath, true);
      }
    }
    catch (IllegalArgumentException e)
    {
      System.out.println("This happened.");
    }
    return rankJob;
  }

  private int runInit(String input, String output, int iter, int numReducers) throws IOException, ClassNotFoundException, InterruptedException {
    Job rankJob = generateRankJob(input, output, iter, numReducers);

    rankJob.setMapOutputKeyClass(Text.class);
    rankJob.setMapOutputValueClass(Text.class);

    rankJob.setMapperClass(AdsorptionInitMapper.class);
    rankJob.setReducerClass(AdsorptionInitReducer.class);

    if (!rankJob.waitForCompletion(true)) {
      return 1;
    }
    return 0;
  }

  private int runIter(String input, String output, int iter, int numReducers) throws ClassNotFoundException, IOException, InterruptedException {
    System.out.println("RUNNING ITER : INPUT : " + input + " OUTPUT : " + output);
	  Job rankJob = generateRankJob(input, output, iter, numReducers);

    rankJob.setMapOutputKeyClass(Text.class);
    rankJob.setMapOutputValueClass(Text.class);

    rankJob.setMapperClass(AdsorptionIterMapper.class);
    rankJob.setReducerClass(AdsorptionIterReducer.class);

    if (!rankJob.waitForCompletion(true)) {
      return 1;
    }
    return 0;
  }

  private int runFinish(String input, String output, int iter, int numReducers) throws ClassNotFoundException, IOException, InterruptedException
  {
    Job rankJob = generateRankJob(input, output, iter, numReducers);

    rankJob.setMapOutputKeyClass(Text.class);
    rankJob.setMapOutputValueClass(Text.class);

    rankJob.setMapperClass(AdsorptionFinishMapper.class);
    rankJob.setReducerClass(AdsorptionFinishReducer.class);

    if (!rankJob.waitForCompletion(true)) {
      return 1;
    }
    return 0;
  }

  
  private int composite(String input, String output, String inter1, String inter2, String diffDir, int iter, int numReducers) throws Exception {
	  runInit(input, inter1, -1, numReducers);
	  int round = 0;
	  
	  double threadhold = 0.001;
	  double diff = 1.0;
	  while (diff >= threadhold) {
		  if (round % 2 == 0) {
			  runIter(inter1, inter2, -1, numReducers);
		  } else {
			  runIter(inter2, inter1, -1, numReducers);
		  }
		  round++;  
		  if (round % 5 == 0) {
			  runDiff(inter1, inter2, diffDir, -1, numReducers);
			  diff = readDiff(diffDir);
			  System.out.println("At this round, diff value is : " + diff);
			  deleteDirectory(diffDir);
		  }
		 
	  }
	  int ret = 0;
	  if (round % 2 == 0) {
		  ret = runFinish(inter1, output, -1, 1);
	  } else {
		  ret = runFinish(inter2, output, -1, 1);
	  }
	  return ret;
  }
  
  private int runDiff(String input1, String input2, String diffDir, int iter, int numReducers) throws IOException, ClassNotFoundException, InterruptedException  {
	  Job job = new Job(super.getConf());

	  job.setNumReduceTasks(numReducers);
	  job.setJarByClass(AdsorptionDriver.class);

	  job.getConfiguration().setInt("iteration", iter);

	    FileInputFormat.addInputPath(job, new Path(input1));
	    FileInputFormat.addInputPath(job, new Path(input2));
	    Path outputPath = new Path(diffDir);
	    FileOutputFormat.setOutputPath(job, outputPath);

	    try {
			deleteDirectory(diffDir);
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(Text.class);
	    
	    job.setMapperClass(DiffMapper.class);
	    job.setReducerClass(DiffReducer.class);
	    
	    while (!job.waitForCompletion(true)) {
	    	System.out.println("diff job failed!");
	    	System.exit(1);
	    }
	    return 1;
  }
  
  
  private double readDiff(String diffFile) throws Exception {
	  Double ret = 0.0;
	  Configuration conf  = new Configuration();
	
	  FileSystem fs = FileSystem.get(URI.create(diffFile), conf);
	   
	  Path diffPath = new Path(diffFile);
	  if (fs.exists(diffPath)) {
		  FileStatus[] files = fs.listStatus(diffPath);
		  for (FileStatus f : files) {
			  if (f.getPath().getName().startsWith("part-r-0000")) {
				  FSDataInputStream fdis = fs.open(f.getPath());
				  BufferedReader br = new BufferedReader(new InputStreamReader(fdis));
				  String ss = br.readLine();
				  System.out.println("sssssssssssssssssssss : " + ss);
				  String s = ss.split("diff")[1].trim();
				  ret = Double.parseDouble(s);
			  }
		  }  
	  }
	  return ret;
  }
  
  private int composite3(String input, String output, String inter1, String inter2, String diffDir, int iter, int numReducers) throws IOException, ClassNotFoundException, InterruptedException {
    runInit(input, inter1, -99, numReducers);
    int iteration = 0;

    while (iteration < 1) {
    	System.out.println("ITERATION : " + iteration);
      if (iteration % 2 == 0) {
        runIter(inter1, inter2, -99, numReducers);
      }
      else {
        runIter(inter2, inter1, -99, numReducers);
      }

      iteration++;
      System.out.println(iteration);
    }
    int ret;
    if (iteration % 2 == 0) {
      ret = runFinish(inter1, output, -99, 1);
    }
    else
    {
      ret = runFinish(inter2, output, -99, 1);
    }

    return ret;
  }  

  
  static void deleteDirectory(String path) throws Exception {
	    Path f = new Path(path);
	    Configuration conf = new Configuration();
	    FileSystem fs = FileSystem.get(URI.create(path),conf);
	    
	    if (fs.exists(f)) 
	      fs.delete(f, true);
	      
	    fs.close();
  }

  public static void main(String[] args)
    throws Exception
  {
    Configuration conf = new Configuration();

    int res = ToolRunner.run(conf, new AdsorptionDriver(), args);

    System.exit(res);
  }

}