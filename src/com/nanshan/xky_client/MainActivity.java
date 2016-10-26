package com.nanshan.xky_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.integer;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	ListView listView;
	List<Map< String,Object>> list1;
	String path = "http://i.guet.edu.cn/";
	
	private ViewPager mViewPager;
	private TextView tvTitle;
	private LinearLayout llContainer;

	private int[] mImageIds = new int[] { R.drawable.a, R.drawable.b,
			R.drawable.c, R.drawable.d, R.drawable.e };
	private int[] imageItem = {R.drawable.item_01,R.drawable.item_02,R.drawable.item_03,R.drawable.item_04,
			R.drawable.item_05,R.drawable.item_06,R.drawable.item_07,R.drawable.item_04,R.drawable.item_05,
			R.drawable.item_03,R.drawable.item_01};

	// ͼƬ���⼯��
	private final String[] mImageDes = { "�������ģ�����ǰ��", "����ѧһ��������ר�⵳��",
			"��ҵһ����", "2017������ѧ����", "У�����ǩԼ��ʽ" };

	private int mPreviousPos;// ��һ��ҳ��λ��

	Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak") @SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			
			switch(msg.what){
			
			case 1:
				 list1=(List<Map<String, Object>>) msg.obj;
				 MyAdapterList adapter = new MyAdapterList(list1,MainActivity.this);
				 listView.setAdapter(adapter);
				 break;
			
			}
				int currentItem = mViewPager.getCurrentItem();// ��ȡ��ǰҳ��λ��
				mViewPager.setCurrentItem(++currentItem);// ������һ��ҳ��
	
				// ����������ʱ2�����Ϣ, �γ����Ƶݹ��Ч��, ʹ���һֱѭ���л�
				mHandler.sendEmptyMessageDelayed(0, 2000);
				
			
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (ViewPager) findViewById(R.id.vp_pager);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		llContainer = (LinearLayout) findViewById(R.id.ll_container);
		listView=(ListView) findViewById(R.id.listView1);
		
		
		mViewPager.setAdapter(new MyAdapter());// ��viewpager��������
		// mViewPager.setCurrentItem(Integer.MAX_VALUE/2);
		mViewPager.setCurrentItem(mImageIds.length * 10000);
		Thread t = new Thread(){
					
					public void run(){
						
						List<Map< String,Object>> list=new ArrayList<Map< String,Object>>();
						int i=0;
						Data data = new Data();
						
						Document doc;
						try {
							doc = (Document)Jsoup.connect(path).timeout(3000).get();
							
							Element element = doc.getElementById("xygg_div");
							
							Elements el = element.select("a");
							
							for(Element el0:el){
								Map<String, Object> map = new HashMap<String, Object>();
								String text=el0.text();
								String link=el0.attr("href");
								data.setlink(link);
								data.setText(text);
								map.put("up", data.getText());
								
								map.put("down", imageItem[i]);
								i++;
								list.add(map);
							}
							
							Message msg = Message.obtain();
							msg.what=1;
							msg.obj=list;
							mHandler.sendMessage(msg);
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				};
				t.start();
		
		// ���û�������
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			// ĳ��ҳ�汻ѡ��
			@Override
			public void onPageSelected(int position) {
				int pos = position % mImageIds.length;
				tvTitle.setText(mImageDes[pos]);// �������ű���

				// ����СԲ��
				llContainer.getChildAt(pos).setEnabled(true);// ��ѡ�е�ҳ���Բ������Ϊ��ɫ
				// ����һ��Բ���Ϊ��ɫ
				llContainer.getChildAt(mPreviousPos).setEnabled(false);

				// ������һ��ҳ��λ��
				mPreviousPos = pos;
			}

			// ����������
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			// ����״̬�仯
			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		tvTitle.setText(mImageDes[0]);// ��ʼ�����ű���

		// ��̬����5��СԲ��
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView view = new ImageView(this);
			view.setImageResource(R.drawable.shape_point_selector);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			if (i != 0) {// �ӵ�2��Բ�㿪ʼ������߾�, ��֤Բ��֮��ļ��
				params.leftMargin = 6;
				view.setPressed(false);// ���ò�����, ��Ϊ��ɫԲ��
			}

			view.setLayoutParams(params);

			llContainer.addView(view);
		}

		// ��ʱ2����¹��������Ϣ
		mHandler.sendEmptyMessageDelayed(0, 3000);
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mHandler.removeCallbacksAndMessages(null);// ���������Ϣ��Runnable����
					break;
				case MotionEvent.ACTION_UP:
					// �����ֲ����
					mHandler.sendEmptyMessageDelayed(0, 3000);
					break;

				default:
					break;
				}

				return false;// ����false, ��viewpagerԭ������Ч����������
			}
		});
		
		//listView ��Ŀ����¼�
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch(arg2){
				case 0:
					Intent intent = new Intent(MainActivity.this,WebView_xianshi.class);
					startActivity(intent);
					finish();
			        break;
				case 1:break;
				case 2:break;
				case 3:break;
				case 4:break;
				case 5:break;
				case 6:break;
				}
				
			}
		});
		
		
		
	}
	

	
	//ViewPager������
	class MyAdapter extends PagerAdapter {

		// ����item�ĸ���
		@Override
		public int getCount() {
			// return mImageIds.length;
			return Integer.MAX_VALUE;
		}

		// �жϵ�ǰҪչʾ��view�ͷ��ص�object�Ƿ���һ������, �����,��չʾ
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		// ����getView����, ��ʼ��ÿ��item�Ĳ���, viewpagerĬ���Զ�����ǰһ�źͺ�һ��ͼƬ, ��֤ʼ�ձ���3��ͼƬ,
		// ʣ��Ķ���Ҫ����
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 0,1, 5->0, 6->1, 10->0
			int pos = position % mImageIds.length;

			ImageView view = new ImageView(getApplicationContext());
			// view.setImageResource(mImageIds[position]);
			view.setBackgroundResource(mImageIds[pos]);

			// ��item�Ĳ������Ӹ�����
			container.addView(view);
			// System.out.println("��ʼ��item..." + pos);

			return view;// ����item�Ĳ��ֶ���
		}

		// item���ٵĻص�����
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// ���������Ƴ����ֶ���
			container.removeView((View) object);
			// System.out.println("����item..." + position);
		}

	}
	
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
     //   int id = item.getItemId();
        switch(item.getItemId()){
        case R.id.action_jiaoxue:
        	break;
        case R.id.action_jiaowu:
        	break;
        case R.id.action_student:
        	break;
        case R.id.action_settings:
        	finish();
        	break;
        }
        
        return super.onOptionsItemSelected(item);
    }	
	
}

//���������õ�ListView������
@SuppressLint({ "ViewHolder", "InflateParams" }) class MyAdapterList extends BaseAdapter {
	
	List<Map< String,Object>> list;
	TextView text1,text2;
	ImageView image;
	LayoutInflater mInflater;
	//Context context;
	
	
	MyAdapterList(List<Map< String,Object>> list,Context context){
		this.list=list;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v = mInflater.inflate(R.layout.item, null);
		
		image = (ImageView) v.findViewById(R.id.image);
		text1 = (TextView) v.findViewById(R.id.textMiaoshu);
		
		text1.setText((String)list.get(arg0).get("up"));
		
		image.setImageResource((Integer)list.get(arg0).get("down"));
		return v;
	}

}






