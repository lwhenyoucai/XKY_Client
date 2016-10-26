package com.nanshan.xky_client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView_xianshi extends Activity {
	
	WebView mwebView1,mwebView2,mwebView3;
	WebSettings webSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		mwebView1=(WebView) findViewById(R.id.webView1);
        mwebView1.loadUrl("http://i.guet.edu.cn/news.php?id=61295");
        webSet();
        mwebView1.setWebViewClient(new WebViewClient());
		
	}
	
	
@SuppressLint("SetJavaScriptEnabled") void webSet(){
    	
    	webSettings = mwebView1.getSettings();
        //webSettings3.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        
        
    	webSettings.setJavaScriptEnabled(true);
    	webSettings.setDefaultTextEncodingName("UTF-8");
    	webSettings.setUseWideViewPort(true);//显示正常的视图
    	webSettings.setLoadWithOverviewMode(true);//负载的WebView完全缩小
    	webSettings.setSupportZoom(true);
    	webSettings.setBuiltInZoomControls(true);
    	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(mwebView1.canGoBack()){
			mwebView1.goBack();
		}else {
			Intent intent = new Intent(WebView_xianshi.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
}
