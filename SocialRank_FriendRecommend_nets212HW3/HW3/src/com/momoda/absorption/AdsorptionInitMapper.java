package com.momoda.absorption;


import java.io.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.HashSet;

/**
 *  读取朋友关系的文档
 *
 *
 */
public class AdsorptionInitMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public AdsorptionInitMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {
		String line = value.toString();
		String[] edgeList = line.split("\t");
		
		if (edgeList.length > 1) {
			context.write(new Text(edgeList[0]), new Text(edgeList[1])); 
			System.out.println(edgeList[0]+"," +edgeList[1]);
		}
		
	}
	
}
