package com.momoda.absorption;

import java.io.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.HashSet;

/**

 *  基本思想：每个朋友都有一堆的推荐朋友，这对朋友在自己这里有一个rank，这个rank的值是通过这个朋友和自己有多少个间接认识的朋友来进行推荐的
 *  例如，如果a和b、c、d认识，b、c 都认识e，而d认识f，这里明显  e的推荐度要高于f，所以优先推荐e给a，这里的思路就是，把每个用户的直接认识朋友
 *  进行打散，这些朋友之间就是有间接认识关系，然后map分发出去，然后reduce进行权重更新，例如朋友a 和  c  发现有  10个间接认识关系，那么 a的推荐朋友列表里面肯定
 *  有c了，这样把每个用户的推荐列表算出来
 * 
 * 
 *
 */
public class AdsorptionIterMapper extends 
Mapper<LongWritable, Text, Text, Text> {

	public AdsorptionIterMapper() {
		super();
	}
	
	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		//整個作為一行存了進來
		String[] values = value.toString().split("\t");
		
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh mapper key : " + key.toString());
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhh mapper value:" + value.toString());
		String vertex = values[0];

		AdsorptionWritable graph = AdsorptionWritable.stringToObject(values[1], AdsorptionWritable.class);
		AdsorptionWritable data = AdsorptionWritable.stringToObject(values[1], AdsorptionWritable.class);
		
		graph.info = "Direct Links";
		data.info = "Indirect Links";
		
		String[] friends = graph.getAdjacencyList();
		String[] arr = graph.getAdjacencyList();
		//為了打印出來 
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
		   result.append( arr[i] + ", " );
		}

		
		System.out.println(result.toString());
		
		for (String friend : friends) {
			System.out.println("Friend: "+ friend);
			//data和graph里面存的都是当前用户的朋友，这是朋友和朋友之间 只是一个间接链接关系（通过当前用户）,所以发送出去
			context.write(new Text(friend), new Text(AdsorptionWritable.objectToString(data)));
		}
		//这里当前用户和graph里面的 朋友都是直接链接的，所以也发送出去
		context.write(new Text(vertex), new Text(AdsorptionWritable.objectToString(graph)));
	}
	
}
