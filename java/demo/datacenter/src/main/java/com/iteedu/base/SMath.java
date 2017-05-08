package com.iteedu.base;

import java.text.DecimalFormat;

public class SMath {

	public static void main(String[] args) {
		Double[] testData = new Double[] { 1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d };
		System.out.println("最大值：" + getMax(testData));
		System.out.println("最小值：" + getMin(testData));
		System.out.println("计数：" + getCount(testData));
		System.out.println("求和：" + getSum(testData));
		System.out.println("求平均：" + getAverage(testData));
		System.out.println("方差：" + getVariance(testData));
		System.out.println("标准差：" + getStandardDiviation(testData));

	}

	/**
	 * 求给定双精度数组中值的最大值
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果,如果输入值不合法，返回为-1
	 */
	public static Double getMax(Double[] inputData) {
		if (inputData == null || inputData.length == 0)
			return -1d;
		int len = inputData.length;
		Double max = inputData[0];
		for (int i = 0; i < len; i++) {
			if (max < inputData[i])
				max = inputData[i];
		}
		return max;
	}

	/**
	 * 求求给定双精度数组中值的最小值
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果,如果输入值不合法，返回为-1
	 */
	public static Double getMin(Double[] inputData) {
		if (inputData == null || inputData.length == 0)
			return -1d;
		int len = inputData.length;
		Double min = inputData[0];
		for (int i = 0; i < len; i++) {
			if (min > inputData[i])
				min = inputData[i];
		}
		return min;
	}

	/**
	 * 求给定双精度数组中值的和
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果
	 */
	public static Double getSum(Double[] inputData) {
		if (inputData == null || inputData.length == 0)
			return -1d;
		int len = inputData.length;
		Double sum = 0d;
		for (int i = 0; i < len; i++) {
			sum = sum + inputData[i];
		}

		return sum;

	}

	/**
	 * 求给定双精度数组中值的数目
	 * 
	 * @param input
	 *            Data 输入数据数组
	 * @return 运算结果
	 */
	public static int getCount(Double[] inputData) {
		if (inputData == null)
			return -1;

		return inputData.length;
	}

	/**
	 * 求给定双精度数组中值的平均值
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果
	 */
	public static Double getAverage(Double[] inputData) {
		if (inputData == null || inputData.length == 0)
			return -1d;
		int len = inputData.length;
		Double result;
		result = getSum(inputData) / len;

		return result;
	}

	/**
	 * 求给定双精度数组中值的平方和
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果
	 */
	public static Double getSquareSum(Double[] inputData) {
		if (inputData == null || inputData.length == 0)
			return -1d;
		int len = inputData.length;
		Double sqrsum = 0.0;
		for (int i = 0; i < len; i++) {
			sqrsum = sqrsum + inputData[i] * inputData[i];
		}

		return sqrsum;
	}

	/**
	 * 求给定双精度数组中值的方差
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果
	 */
	public static Double getVariance(Double[] inputData) {
		int count = getCount(inputData);
		Double sqrsum = getSquareSum(inputData);
		Double average = getAverage(inputData);
		Double result;
		result = (sqrsum - count * average * average) / count;

		return result;
	}

	/**
	 * 求给定双精度数组中值的标准差
	 * 
	 * @param inputData
	 *            输入数据数组
	 * @return 运算结果
	 */
	public static Double getStandardDiviation(Double[] inputData) {
		Double result;
		// 绝对值化很重要
		result = Math.sqrt(Math.abs(getVariance(inputData)));

		return result;

	}

	public static Double dformat(Double d){
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.parseDouble(df.format(d));
	}
	
	public static double log(double v,double b){
		return Math.log(v)/Math.log(b);
	}
	
}