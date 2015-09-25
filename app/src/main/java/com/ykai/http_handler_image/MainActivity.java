/*
 * Copyright (c) 2015-2015 by Shanghai shootbox Information Technology Co., Ltd.
 * Create: 2015/9/25 0:10:51
 * Project: Http_handler_image
 * File: MainActivity.java
 * Class: MainActivity
 * Module: app
 * Author: yangyankai
 * Version: 1.0
 */

package com.ykai.http_handler_image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	private Handler handler = new Handler() {
		// Handler 里面的消息处理是异步的
		public void handleMessage(Message msg)
		{
			img.setImageBitmap((Bitmap) msg.obj);
		}
	};

	private              ImageView img  = null;
	private static final String    PATH = "http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.img = (ImageView) super.findViewById(R.id.image);

		new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					byte data[] = getUrlData();
					Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
					Message message = new Message();
					message.obj = bm;
					handler.sendMessage(message);
				} catch (Exception e)
				{
				}
			}
		}).start();

	}

	/**
	 * 通过Urlconnection 请求获取数据
	 * @return
	 * @throws Exception
	 */
	public byte[] getUrlData() throws Exception
	{
		ByteArrayOutputStream byteArrayOutputStream = null;

		try
		{
			URL url = new URL(PATH);
			byteArrayOutputStream = new ByteArrayOutputStream();
			byte data[] = new byte[1024];
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream input = conn.getInputStream();
			int len = 0;
			while ((len = input.read(data)) != -1)
			{
				byteArrayOutputStream.write(data, 0, len);
			}
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			if (byteArrayOutputStream != null)
			{
				byteArrayOutputStream.close();
			}
		}
	}

}
