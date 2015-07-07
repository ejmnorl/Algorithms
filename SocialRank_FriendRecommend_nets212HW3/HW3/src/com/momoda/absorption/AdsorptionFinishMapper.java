package com.momoda.absorption;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap; 
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *  最后处理类，其实每个朋友都已经计算完了，这是只要取出结果就行了
 * 
 *
 */
public class AdsorptionFinishMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public AdsorptionFinishMapper() {
		super();
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
	throws IOException, InterruptedException {

		String[] values = value.toString().split("\t");
		String vertex = values[0];

		AdsorptionWritable graph = AdsorptionWritable.stringToObject(values[1], AdsorptionWritable.class);
		Map<String, Double> ranks = graph.getAdsorptionRanks();
		System.out.println("Vertex :" + vertex + ", " + ranks.size());
		String[] friends = graph.getAdjacencyList();

		//对朋友进行权重排序
		TreeMap<Double, String> sortedRanks = new TreeMap<Double, String>(Collections.reverseOrder());

		
		
		for (String node : ranks.keySet()) {
			if (!Arrays.asList(friends).contains(node.toString()))
				sortedRanks.put(ranks.get(node), node);
			
		}
		
		ArrayList<String> recommendations = new ArrayList<String>();
		
		for (Map.Entry<Double,String> entry : sortedRanks.entrySet()) {
			  Double k = entry.getKey();
			  String v = entry.getValue();

			  System.out.println("22222222 recommend : " + v + " vertex : " + vertex);
			  if (v.equals(vertex)) {
				  continue;
			  }
			  recommendations.add(v);
		}
		context.write(new Text(vertex), new Text(recommendations.toString()));
	}
	
}

