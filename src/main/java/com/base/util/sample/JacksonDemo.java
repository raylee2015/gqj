/**   
* @Title: Test.java 
* @Package com.base.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016年11月27日 上午11:04:39 
* @version V1.0   
*/
package com.base.util.sample;

import java.util.ArrayList;
import java.util.List;

import com.base.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sample.entity.Sample;

/**
 * 实例实现利用jackson实现实体对象与json字符串的互相转换
 * 
 * @author liangming.deng
 * 
 */
public class JacksonDemo {
	public static void main(String[] args) {
		Sample sample1 = new Sample();
		sample1.setUserId(1l);
		sample1.setUserName("llh");
		sample1.setUserCode("1230");
		Sample sample2 = new Sample();
		sample2.setUserId(2l);
		sample2.setUserName("lyc");
		sample2.setUserCode("1231");

		List<Sample> Samples = new ArrayList<Sample>();
		Samples.add(sample1);
		Samples.add(sample2);

		// 对象转json
		String SampleToJson = JacksonUtil.toJSon(sample1);
		System.out.println("---Sample to json--");
		System.out.println(SampleToJson);

		// json转字符串
		Sample jsonToSample = JacksonUtil.readValue(SampleToJson, Sample.class);
		System.out.println();
		System.out.println("--json to Sample--");
		System.out.println(jsonToSample.toString());

		// List 转json字符串
		String listToJson = JacksonUtil.toJSon(Samples);
		System.out.println();
		System.out.println("--list to json--");
		System.out.println(listToJson);

		// 数组json转 List
		List<Sample> jsonToSamples = JacksonUtil.readValue(listToJson, new TypeReference<List<Sample>>() {
		});
		String SampleString = "";
		for (Sample Sample : jsonToSamples) {
			SampleString += Sample.toString() + "\n";
		}
		System.out.println();
		System.out.println("--json to Samples--");
		System.out.println(SampleString);
	}

}