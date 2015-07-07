package edu.upenn.team19;
/*
 * 
 * 
 * 启动类，进行各种选项启动
 */

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PennBookDriver extends Configured
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
    rankJob.setJarByClass(PennBookDriver.class);

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

    rankJob.setMapperClass(InitMapper.class);
    rankJob.setReducerClass(InitReducer.class);

    if (!rankJob.waitForCompletion(true)) {
      return 1;
    }
    return 0;
  }

  private int runIter(String input, String output, int iter, int numReducers) throws ClassNotFoundException, IOException, InterruptedException {
    Job rankJob = generateRankJob(input, output, iter, numReducers);

    rankJob.setMapOutputKeyClass(Text.class);
    rankJob.setMapOutputValueClass(Text.class);

    rankJob.setMapperClass(IterMapper.class);
    rankJob.setReducerClass(IterReducer.class);

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

    rankJob.setMapperClass(FinishMapper.class);
    rankJob.setReducerClass(FinishReducer.class);
    
    System.out.println("FININSH...");
    if (!rankJob.waitForCompletion(true)) {
    	System.out.println("HIT");
      return 1;
    }
    return 0;
  }

  private int composite(String input, String output, String inter1, String inter2, String diffDir, int iter, int numReducers) throws IOException, ClassNotFoundException, InterruptedException {
    runInit(input, inter1, -99, numReducers);
    int iteration = 0;

    while (iteration <= 5) {
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


  public static void main(String[] args)
    throws Exception
  {
    Configuration conf = new Configuration();

    int res = ToolRunner.run(conf, new PennBookDriver(), args);

    System.exit(res);
  }

}