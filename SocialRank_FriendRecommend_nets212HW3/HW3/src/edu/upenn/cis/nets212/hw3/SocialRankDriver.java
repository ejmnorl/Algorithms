package edu.upenn.cis.nets212.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

/**
 * Main driver for SocialRank, NETS212 HW3
 * 
 * @author nets212
 *
 */
public class SocialRankDriver extends Configured implements Tool {
	
	/**
	 * Command-line args usage screen
	 * 
	 * @param args
	 */
	private void printUsage(String[] args) {
		// TODO: update to include composite
		System.err.println("Usage: SocialRankDriver <init|iter|diff|finish> <input dir> [input dir2] <output dir> [cur-iter#] <reducers>");
		
		for (String parm: args)
			System.err.println(parm);
	}

	/**
	 * Main invocation, which triggers different mappers & reducers depending on the
	 * command line args
	 */
	public final int run(final String[] args) throws Exception {
		String cmd = "";
		
		if (args.length > 0)
			cmd = args[0];
		
		// TODO: we need to test for the composite command, which has more options!
		if (args.length < 5 || args.length > 6) {
			printUsage(args);
			return(1);
		}
		int numReducers = 1;
		try {
			numReducers = Integer.valueOf(args[args.length-1]);
			
			if (numReducers < 1) {
				printUsage(args);
				return 1;
			}

		} catch (NumberFormatException nf) {
			printUsage(args);
			return 1;
		}
		int iter = 1;
		if (cmd.equals("iter") || cmd.equals("diff")) {
			try {
				iter = Integer.valueOf(args[args.length-2]);
				
				if (iter < 0) {
					printUsage(args);
					return 1;
				}
	
			} catch (NumberFormatException nf) {
				printUsage(args);
				return 1;
			}
		}
		
		// run different jobs, depending on the command
		if (cmd.equals("init")) {
			return runInit (args[1], args[2], iter, numReducers);
		} else if (cmd.equals("iter")) {
			return runIter (args[1], args[2], iter, numReducers);
		} else if (cmd.equals("diff")) {
			return runDiff (args[1], args[2], args[3], iter, numReducers);
		} else if (cmd.equals("finish")) {
			return runFinish (args[1], args[2], iter, numReducers);
		} else {
			// TODO: see if it's composite!!  If so we need to run multiple
			// rounds
			return 0;
		}
	}
	
	// factor out the common parts of all map/reduce jobs
	private Job createRankJob(String input, String output, int iter, int numReducers) throws IllegalArgumentException, IOException{
		Job rankJob = new Job(super.getConf());
		// Configure the number of reducers
		rankJob.setNumReduceTasks(numReducers);
		rankJob.setJarByClass(SocialRankDriver.class);
		
		// Set a parameter that the Mapper and Reducer can see
		rankJob.getConfiguration().setInt("iteration", iter);
		
		// set the input and output paths
		FileInputFormat.addInputPath(rankJob, new Path(input));
		Path outputPath = new Path(output);
		FileOutputFormat.setOutputPath(rankJob, outputPath);
		
		FileSystem fs = FileSystem.get(rankJob.getConfiguration());		
		if (fs.exists(outputPath))
			fs.delete(outputPath, true); // delete file, true for recursive
		return rankJob;
	}
	
	private int runInit (String input, String output, int iter, int numReducers) throws IOException, ClassNotFoundException, InterruptedException{
		Job rankJob = createRankJob(input, output, iter, numReducers);
		
		// TODO: consider whether you want the Value to be a string (Text)
		// that gets formatted + parsed between stages, or if you want
		// it to be a custom Writable...  Also note this may affect what
		// subsequent Mapper inputs are...
		rankJob.setMapOutputKeyClass(Text.class);
		rankJob.setMapOutputValueClass(Text.class);
		
		//set the classes to be used 
		rankJob.setMapperClass(SocialRankInitMapper.class);
		rankJob.setReducerClass(SocialRankInitReducer.class);

		if (rankJob.waitForCompletion(true) == false)
			return 1;
		else
			return 0;
	}
	
	private int runIter (String input, String output, int iter, int numReducers) throws ClassNotFoundException, IOException, InterruptedException{
		Job rankJob = createRankJob(input, output, iter, numReducers);		
		// TODO: maybe modify Value, if you decided to use a custom Writable
		rankJob.setMapOutputKeyClass(Text.class);
		rankJob.setMapOutputValueClass(Text.class);
		
		//set the classes to be used 
		rankJob.setMapperClass(SocialRankIterMapper.class);
		rankJob.setReducerClass(SocialRankIterReducer.class);

		if (rankJob.waitForCompletion(true) == false)
			return 1;
		else
			return 0;
	}
	
	private int runDiff (String input, String input2, String output, int iter, int numReducers) throws ClassNotFoundException, IOException, InterruptedException{
		Job rankJob = createRankJob(input, output, iter, numReducers);	
		
		// add extra input file 
		FileInputFormat.addInputPath(rankJob, new Path(input2));
		
		// TODO: maybe modify Value, if you decided to use a custom Writable
		rankJob.setMapOutputKeyClass(Text.class);
		rankJob.setMapOutputValueClass(Text.class);
		
		//set the classes to be used 
		rankJob.setMapperClass(SocialRankDiffMapper.class);
		rankJob.setReducerClass(SocialRankDiffReducer.class);

		if (rankJob.waitForCompletion(true) == false)
			return 1;
		else
			return 0;
	}
	
	private int runFinish (String input, String output, int iter, int numReducers) throws ClassNotFoundException, IOException, InterruptedException{
		Job rankJob = createRankJob(input, output, iter, numReducers);		
		// TODO: maybe modify Value, if you decided to use a custom Writable
		rankJob.setMapOutputKeyClass(Text.class);
		rankJob.setMapOutputValueClass(Text.class);
		
		//set the classes to be used 
		rankJob.setMapperClass(SocialRankFinishMapper.class);
		rankJob.setReducerClass(SocialRankFinishReducer.class);

		if (rankJob.waitForCompletion(true) == false)
			return 1;
		else
			return 0;
	}
	
	// this function will search the input folder, and read a Double from the first line of the part-r-00000 file
	// Set hdfs = true if you want to read an HDFS file, and false if you want to read from the file system
	private double readDouble (String input, boolean hdfs) throws IOException {
		InputStream stream;
		
		if (!hdfs)
			stream = new FileInputStream(new File(input + "/part-r-00000"));
		else {
			FileSystem fs = FileSystem.get(getConf());
			stream = fs.open(new Path(input = "/part-r-00000"));
		}
		
		InputStreamReader isr = new InputStreamReader(stream);
		
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		br.close();
		return Double.parseDouble(line);
	}

	/**
	 * @param args
	 * @throws InterruptedException, ClassNotFoundException, IOException
	 */
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();

		int res = ToolRunner.run(conf, new SocialRankDriver(), args);
		
		System.exit(res);
	}

}
