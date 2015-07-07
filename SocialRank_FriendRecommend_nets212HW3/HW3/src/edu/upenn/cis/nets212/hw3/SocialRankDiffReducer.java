package edu.upenn.cis.nets212.hw3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Return the total change in SocialRank
 * 
 * @author nets212
 *
 */
public class SocialRankDiffReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) {
		
		
	}
}
