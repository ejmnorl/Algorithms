package edu.upenn.cis.nets212.hw3;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This basic Mapper takes lines of intermediate data.  Between it
 * and its associated Reducer we should recompute the SocialRank
 * for each node.
 *  
 * @author zives
 *
 */
public class SocialRankIterMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public SocialRankIterMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {
		String line = value.toString();
		
		Configuration conf = context.getConfiguration();
		int iter = conf.getInt("iteration", 0);
	}
	
}
