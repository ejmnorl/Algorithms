package com.momoda.absorption;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AdsorptionFinishReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		//输出结果就行，不做事
		for (Text t : values) {
			context.write(key, t);
			System.out.println(key.toString() + "......." + t.toString());
		}		
	}
}
