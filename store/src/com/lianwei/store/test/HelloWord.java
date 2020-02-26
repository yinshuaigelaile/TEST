package com.lianwei.store.test;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class HelloWord {
	public static void main(String[] args) {
	
		ActiveXComponent ax = null;

		try {

		ax = new ActiveXComponent("Sapi.SpVoice");

		Dispatch spVoice = ax.getObject();

		ax = new ActiveXComponent("Sapi.SpFileStream");

		Dispatch spFileStream = ax.getObject();

		ax = new ActiveXComponent("Sapi.SpAudioFormat");

		Dispatch spAudioFormat = ax.getObject();

		//设置音频流格式

		Dispatch.put(spAudioFormat, "Type", new Variant(22));

		//设置文件输出流格式

		Dispatch.putRef(spFileStream, "Format", spAudioFormat);

		//调用输出 文件流打开方法，创建一个.wav文件

		Dispatch.call(spFileStream, "Open", new Variant("F:\\test.wav"), new Variant(3), new Variant(true));

		//设置声音对象的音频输出流为输出文件对象

		Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);

		//设置音量 0到100

		Dispatch.put(spVoice, "Volume", new Variant(100));

		//设置朗读速度

		Dispatch.put(spVoice, "Rate", new Variant(-2));

		//开始朗读

		Dispatch.call(spVoice, "Speak", new Variant("张三，李四"));

		//关闭输出文件

		Dispatch.call(spFileStream, "Close");

		Dispatch.putRef(spVoice, "AudioOutputStream", null);

		spAudioFormat.safeRelease();

		spFileStream.safeRelease();

		spVoice.safeRelease();

		ax.safeRelease();
		System.out.println("我进来了");

		} catch (Exception e) {

		e.printStackTrace();

		}

	}
}
