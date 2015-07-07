package edu.upenn.cis.nets212.hw3;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This basic Mapper takes lines of intermediate data.  Between it and
 * its associated Reducer we should get the final SocialRank for
 * each person.
 *  
 * @author zives
 *
 */
public class SocialRankFinishMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public SocialRankFinishMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {
		String line = value.toString();
		
	}
	
}
