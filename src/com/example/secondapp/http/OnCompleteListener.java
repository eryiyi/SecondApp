package com.example.secondapp.http;

import com.example.secondapp.http.AsyncHttpClient.HttpClientThread;

public interface OnCompleteListener {
	public void onComplete(HttpClientThread clientThread);
}
