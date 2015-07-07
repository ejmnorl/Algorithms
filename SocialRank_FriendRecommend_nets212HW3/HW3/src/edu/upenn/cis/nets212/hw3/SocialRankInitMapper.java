package edu.upenn.cis.nets212.hw3;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This basic mapper takes lines of Text.  Between it and its
 * Reducer, we should get intermediate result data including
 * Follows connectivity, SocialRank, iteration, etc.  This
 * counts as Iteration 0.
 *  
 * @author zives
 *
 */
public class SocialRankInitMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public SocialRankInitMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {
		String line = value.toString();
		
		System.out.println(line);
	}
	
}
