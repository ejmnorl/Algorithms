package com.momoda.absorption;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;
/**
 * Return the total change in SocialRank
 * 
 * @author nets212
 *
 */
public class DiffReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) {
		Map<String, Double> diff = new HashMap<String, Double>();
		double max = 0.0;
		int cnt = 0;
		for (Text v : values) {
			cnt++;
			System.out.println("CNT ===================" + cnt +  " vertex : " + key.toString() );
			AdsorptionWritable cur = AdsorptionWritable.stringToObject(v.toString(), AdsorptionWritable.class);
			//AdsorptionWritable cur = AdsorptionWritable.stringToObject(v.toString(), AdsorptionWritable.class);
			Map<String, Double> ranks = cur.recommend_friend_Ranks;
			for (String vertex : ranks.keySet()) {
				System.out.println("recommend :::::::::::::::::" + vertex);
				if (!diff.containsKey(vertex)) {
					diff.put(vertex, ranks.get(vertex));
				} else {
					double tmp1 = ranks.get(vertex);
					double tmp2 = diff.get(vertex);
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^ " + tmp1 + "    " + tmp2);
					Double delta = Math.abs(ranks.get(vertex) - diff.get(vertex));
					max = Math.max(max, delta);
				}
			}
		}
		//System.out.println("^^^^^^^^^^^^^^^^ cnt = " + cnt);
		try {
			System.out.println("123123123123123123123123 : " + String.valueOf(max));
			context.write(new Text("diff"), new Text(String.valueOf(max)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
