package com.example.thread;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

@SuppressWarnings("deprecation")
public class UploadThread extends Thread {
	public String username;
	public File path;
	public StringBody usernamebody;
	public String url;
	public Handler handler;
	public int key;
	public FileBody fileBody;

	public UploadThread(String username, File path, Handler handler) {
		this.path = path;
		this.username = username;
		this.handler = handler;
		this.url = "http://1.daoshi58alarm.sinaapp.com/user/upload.php";

	}

	public void run() {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost httppost = new HttpPost(url);

		MultipartEntity entity = new MultipartEntity();

		fileBody = new FileBody(path);
		try {
			usernamebody = new StringBody(username);

		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}

		entity.addPart("musicfile", fileBody);
		entity.addPart("username", usernamebody);

		httppost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpEntity resEntity = response.getEntity();
		try {
			String result = EntityUtils.toString(resEntity);
			result =result.substring(result.length()-1);
			Log.i("upload_result", result);
			if (result.equals("1")) {
				key = 1;

			} else if (result.equals("3")) {
				key = 3;

			} else {

				key = 0;

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();
		Message message = Message.obtain(handler, key);
		handler.sendMessage(message);

	}
}
