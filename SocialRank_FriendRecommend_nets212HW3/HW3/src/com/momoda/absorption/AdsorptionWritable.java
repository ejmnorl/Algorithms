package com.momoda.absorption;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.hadoop.io.*;

public class AdsorptionWritable implements Serializable {

/**
   *   序列化处理类，由于中间结果很多需要保存，所以必须自己写序列化，例如每次迭代完的结果 
   */
	public Double friend_weight;
	public String[] adjacency_friend_List;
	public Map<String, Double> recommend_friend_Ranks;
	public Boolean only_rank;
	public String info;

	public AdsorptionWritable(Double weight, String[] adjacencyList,
			Map<String, Double> adsorptionRanks) {
		set(weight, adjacencyList, adsorptionRanks);
	}

	
	//进行序列化
	public static String objectToString(Serializable object) {
		String encoded = null;

		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();
			encoded = new String(Base64.encodeBase64(byteArrayOutputStream
					.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T stringToObject(String string,
			Class<T> clazz) {
		byte[] bytes = Base64.decodeBase64(string.getBytes());
		T object = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new ByteArrayInputStream(bytes));
			object = (T) objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return object;
	}

	//读取序列化文件
	public static AdsorptionWritable fromString(String serialized)
			throws java.io.IOException, ClassNotFoundException {
		byte[] data = Base64.decodeBase64(serialized);
		ObjectInputStream input = new ObjectInputStream(
				new ByteArrayInputStream(data));
		Object object = input.readObject();
		input.close();

		return (AdsorptionWritable) object;
	}

	public String toString() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ObjectOutputStream objectOutput = null;
		try {
			objectOutput = new ObjectOutputStream(output);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			objectOutput.writeObject(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(Base64.encodeBase64(output.toByteArray()));
	}

	public void set(Double weight, String[] adjacencyList,
			Map<String, Double> adsorptionRanks2) {
		this.friend_weight = weight;
		this.adjacency_friend_List = adjacencyList;
		this.recommend_friend_Ranks = adsorptionRanks2;
		this.only_rank = false;
		this.info = "0";
	}

	public Double getWeight() {
		return friend_weight;
	}

	public String[] getAdjacencyList() {
		return adjacency_friend_List;
	}

	public Map<String, Double> getAdsorptionRanks() {
		return recommend_friend_Ranks;
	}

	public Boolean isRanksOnly() {
		return only_rank;
	}

	public AdsorptionWritable purgeRanks() {
		AdsorptionWritable out = new AdsorptionWritable(this.getWeight(),
				new String[] {}, this.getAdsorptionRanks());
		out.only_rank = false;
		return out;
	}

	private void setRanksOnlyToTrue() {
		only_rank = true;
	}

}