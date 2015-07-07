package edu.upenn.team19;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class FinishReducer extends Reducer<Text, Text, Text, Text>{
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
		String userranks = "";
		
		System.out.println("Finish Reducer... key: " + key.toString());
		for (Text value : values) {
			userranks += value.toString();
		}
		System.out.println("key : " + key.toString() + " values :  " + userranks);
		context.write(new Text(key.toString()), new Text(userranks));
	}
}