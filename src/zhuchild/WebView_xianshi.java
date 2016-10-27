package zhuchild;
import java.util.List;
import java.util.Map;

import com.nanshan.xky_client.MainActivity;
import com.nanshan.xky_client.R;
import com.nanshan.xky_client.R.id;
import com.nanshan.xky_client.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebView_xianshi extends Activity {
	
	WebView mwebView1;
	WebSettings webSettings;
	List<Map< String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		Intent intent = this.getIntent();
        String path = intent.getStringExtra("key");
		mwebView1=(WebView) findViewById(R.id.webView1);
		
//		mwebView1.setInitialScale(150);//设置初始网页显示长宽

        mwebView1.loadUrl("http://i.guet.edu.cn"+path);

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
