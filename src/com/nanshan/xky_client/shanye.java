package com.nanshan.xky_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class shanye extends Activity {

		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.activity_shanye);
	        
	        new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(shanye.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			},2000);
	        
	    }

}
