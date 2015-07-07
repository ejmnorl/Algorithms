package com.momoda.absorption;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class AdsorptionInitReducer extends Reducer<Text, Text, Text, Text>  { 

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		String vertex = key.toString();
		String edgeList = "";

		for (Text value : values) 
			//所有相关的朋友全部存起来，当前这个朋友的所有朋友
			edgeList += value.toString() + ","; //Appends the adjacency list from mapper as a String; "," to split() easily.
		
		int length = 0; 
		//将朋友存入数组中
		String[] adjacencyList = null;
		
		if (edgeList != null && edgeList.length()> 1) { //Checks to make sure at least one node exists before String manipulation
			edgeList = edgeList.substring(0, edgeList.length() - 1); //Removes final ","
		 	adjacencyList = edgeList.split(",");
		 	length = adjacencyList.length;
		}
		
		//确定每个朋友的权重值
		Double weight = length == 0 ? new Double(0) : new Double(1.0/length);

		Map<String, Double> ranks = new HashMap<String, Double>();
		//当前key，1
		ranks.put(new String(vertex), new Double(1));
		
		

		//进行序列化
		AdsorptionWritable output = new AdsorptionWritable(weight, adjacencyList, ranks);
		String serialized = AdsorptionWritable.objectToString(output);
		
		//输出 key  weight  adjacecyList  ranks
		context.write(key, new Text(serialized)); 

	}
}
