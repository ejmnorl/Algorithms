package edu.upenn.cis.nets212.hw3;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This basic Mapper takes lines of Text.  Between it and
 * its associated Reducer it should return the *max*
 * amount of change between the current and previous
 * iteration. 
 *  
 * @author zives
 *
 */
public class SocialRankDiffMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public SocialRankDiffMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {
		String line = value.toString();

		// This is the current iteration
		Configuration conf = context.getConfiguration();
		int iter = conf.getInt("iteration", 0);
		
	}
	
}
