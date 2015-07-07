package com.momoda.absorption;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdsorptionIterReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		HashMap<String, Double> ranks = new HashMap<String, Double>();
		AdsorptionWritable output = null;

		for (Text v : values) {
			AdsorptionWritable value = AdsorptionWritable.stringToObject(v.toString(), AdsorptionWritable.class);
			//计算间接链接的用户，对于当前这个用户来说，value里面的朋友都是间接和自己链接的，所以更新rank的排名
			if (value.info.equals("Indirect Links")) {
				System.out.println("FLAG");
				double weight = value.getWeight();
				Map<String, Double> inRanks = value.getAdsorptionRanks();

				for (String node : inRanks.keySet()) {
					double val = inRanks.get(node);
					
					if (ranks.containsKey(node)) {
						//当前自己的rank里面已经有了这个朋友，则进行权重更新
						Double out = ranks.get(node) + weight * val;
						ranks.put(node, out);
						System.out.println("Node: " + out);
					}
					else {
						//自己当前的rank队列里面没有这个朋友，则直接插入
						ranks.put(node, new Double(weight * val));
						System.out.println("Node: " + weight*val);
					}
				}
			}

			//每次迭代，最后总会有whole graph
			else if (value.info.equals("Direct Links")) {
				System.out.println("HI");
				//如果是直接认识，则不必要处理
				output = new AdsorptionWritable(value.getWeight(), value.getAdjacencyList(), new HashMap<String, Double>());
			}			
		}
		
		if (output != null) {
			output.recommend_friend_Ranks = ranks;
			//更新完，则重新迭代
			System.out.println("hhhh : key : " + key + " values: " + AdsorptionWritable.objectToString(output));
			context.write(key, new Text(AdsorptionWritable.objectToString(output)));
		}
		
	}
}
