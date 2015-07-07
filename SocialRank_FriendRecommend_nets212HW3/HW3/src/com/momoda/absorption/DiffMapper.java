package com.momoda.absorption;


import java.io.IOException;
import java.util.*;

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
public class DiffMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public DiffMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {
		//String line = value.toString();

		// This is the current iteration
		//Configuration conf = context.getConfiguration();
		//int iter = conf.getInt("iteration", 0);
		String[] values = value.toString().split("\t");
		String vertex = values[0];
		AdsorptionWritable data = AdsorptionWritable.stringToObject(values[1], AdsorptionWritable.class);
		Map<String, Double>	test = data.recommend_friend_Ranks;
		 
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ vertex : " + vertex);
		for (String s : test.keySet()) {
			System.out.print(s + " ");
		}
		System.out.println("....................................................");
		context.write(new Text(vertex),  new Text(values[1]));
		
		//context.write(new Text(key.toString()),  new Text(AdsorptionWritable.objectToString(value)));
	}
	
}
