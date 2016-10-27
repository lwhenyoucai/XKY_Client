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

import slidingMenu.SlidingMenu;
import slidingMenu_left.MyFragment;
import zhuchild.WebView_xianshi;
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
import android.support.v4.app.FragmentActivity;
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

public class MainActivity extends FragmentActivity {
	
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

	// 图片标题集合
	private final String[] mImageDes = { "不忘初心，继续前行", "“两学一做”教育专题党课",
			"创业一条街", "2017新生开学典礼", "校企合作签约仪式" };

	private int mPreviousPos;// 上一个页面位置

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
				int currentItem = mViewPager.getCurrentItem();// 获取当前页面位置
				mViewPager.setCurrentItem(++currentItem);// 跳到下一个页面
	
				// 继续发送延时2秒的消息, 形成类似递归的效果, 使广告一直循环切换
				mHandler.sendEmptyMessageDelayed(0, 2000);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//<<<<<<<<<<<<<<------侧滑菜单设置---------->>>>>>>>>>>>>>>>>>	
		
		// 侧滑菜单设置 
        SlidingMenu menu = new SlidingMenu(this);  
        menu.setMode(SlidingMenu.LEFT);  
        // 设置触摸屏幕的模式  
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  
        menu.setShadowWidthRes(R.dimen.shadow_width);  
        menu.setShadowDrawable(R.drawable.shadow);  
  
        // 设置滑动菜单视图的宽度  
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);  
        // 设置渐入渐出效果的值  
        menu.setFadeDegree(0.5f);  
        /** 
         * SLIDING_WINDOW will include the Title/ActionBar in the content 
         * section of the SlidingMenu, while SLIDING_CONTENT does not. 
         */  
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);////高度在ActionBar之下 
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW); //高度超过ActionBar 
        //为侧滑菜单设置布局  
        menu.setMenu(R.layout.leftmenu); 
        MyFragment fragment = new MyFragment();
        fragment.setContext(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.leftmenu, fragment).commit();
//        menu.toggle();//初始化时展现左侧滑菜单
        menu.setFadeEnabled(true);//设置SlidingMenu是否淡入/淡出
        
//<<<<<<<<<<<<<<<<--------图片轮播实现代码------->>>>>>>>>>>>>>>>>>    
		
		
		mViewPager = (ViewPager) findViewById(R.id.vp_pager);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		llContainer = (LinearLayout) findViewById(R.id.ll_container);
		listView=(ListView) findViewById(R.id.listView1);
		getWebData(list1);
		
		mViewPager.setAdapter(new MyAdapter());// 给viewpager设置数据
		// mViewPager.setCurrentItem(Integer.MAX_VALUE/2);
		mViewPager.setCurrentItem(mImageIds.length * 10000);
		
//<<<<<<<<<<<<<<<<--------设置滑动监听------->>>>>>>>>>>>>>>>>>    
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			// 某个页面被选中
			@Override
			public void onPageSelected(int position) {
				int pos = position % mImageIds.length;
				tvTitle.setText(mImageDes[pos]);// 更新新闻标题

				// 更新小圆点
				llContainer.getChildAt(pos).setEnabled(true);// 将选中的页面的圆点设置为红色
				// 将上一个圆点变为灰色
				llContainer.getChildAt(mPreviousPos).setEnabled(false);

				// 更新上一个页面位置
				mPreviousPos = pos;
			}

			// 滑动过程中
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			// 滑动状态变化
			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		tvTitle.setText(mImageDes[0]);// 初始化新闻标题

//<<<<<<<<<<<<<<<<--------动态添加5个小圆点------->>>>>>>>>>>>>>>>>>    
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView view = new ImageView(this);
			view.setImageResource(R.drawable.shape_point_selector);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			if (i != 0) {// 从第2个圆点开始设置左边距, 保证圆点之间的间距
				params.leftMargin = 6;
				view.setPressed(false);// 设置不可用, 变为灰色圆点
			}

			view.setLayoutParams(params);

			llContainer.addView(view);
		}

//<<<<<<<<<<<<<<<<-------- 延时2秒更新广告条的消息------->>>>>>>>>>>>>>>>>>
		mHandler.sendEmptyMessageDelayed(0, 3000);
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mHandler.removeCallbacksAndMessages(null);// 清除所有消息和Runnable对象
					break;
				case MotionEvent.ACTION_UP:
					// 继续轮播广告
					mHandler.sendEmptyMessageDelayed(0, 3000);
					break;

				default:
					break;
				}

				return false;// 返回false, 让viewpager原生触摸效果正常运行
			}
		});
		
//<<<<<<<<<<<<<<<<--------listView 项目点击事件------->>>>>>>>>>>>>>>>>>
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
	
				Intent intent = new Intent();
                intent.setClass(MainActivity.this, WebView_xianshi.class);
                TextView t = (TextView) arg1.findViewById(R.id.textwangzhi);
                String URL =t.getText().toString();
                intent.putExtra("key", URL);
                startActivity(intent);
                finish();
			}
		});
	}
	
	
//<<<<<<<<<<<<<<<<--------	获取网络数据------->>>>>>>>>>>>>>>>>>
	public List<Map< String,Object>> getWebData(List<Map< String,Object>> list1){
		
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
						map.put("href", data.getlink());
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
		return list1;
	}
	

	
//<<<<<<<<<<<<<<<<--------ViewPager适配器------->>>>>>>>>>>>>>>>>>	
	class MyAdapter extends PagerAdapter {

		// 返回item的个数
		@Override
		public int getCount() {
			// return mImageIds.length;
			return Integer.MAX_VALUE;
		}

		// 判断当前要展示的view和返回的object是否是一个对象, 如果是,才展示
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		// 类似getView方法, 初始化每个item的布局, viewpager默认自动加载前一张和后一张图片, 保证始终保持3张图片,
		// 剩余的都需要销毁
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 0,1, 5->0, 6->1, 10->0
			int pos = position % mImageIds.length;

			ImageView view = new ImageView(getApplicationContext());
			// view.setImageResource(mImageIds[position]);
			view.setBackgroundResource(mImageIds[pos]);

			// 将item的布局添加给容器
			container.addView(view);
			// System.out.println("初始化item..." + pos);

			return view;// 返回item的布局对象
		}

		// item销毁的回调方法
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 从容器中移除布局对象
			container.removeView((View) object);
			// System.out.println("销毁item..." + position);
		}

	}
	
//<<<<<<<<<<<<<<<<--------菜单设置------->>>>>>>>>>>>>>>>>>
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch(item.getItemId()){
        case R.id.action_guanyu:
        	break;
        case R.id.action_settings:
        	finish();
        	break;
        }
        
        return super.onOptionsItemSelected(item);
    }	
	
}

//<<<<<<<<<<<<<<<<--------ListView适配器------->>>>>>>>>>>>>>>>>>加载数据用的ListView适配器
@SuppressLint({ "ViewHolder", "InflateParams" }) class MyAdapterList extends BaseAdapter {
	
	List<Map< String,Object>> list;
	TextView text1,text2;
	ImageView image;
	LayoutInflater mInflater;
	//Context context;
	MyAdapterList(List<Map< String,Object>> list1){
		this.list=list1;
	}
	
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
		ViewHolder holder = null;
		if(arg1==null){
			holder = new ViewHolder();
			arg1 = mInflater.inflate(R.layout.item, null);
			
			holder.image = (ImageView) arg1.findViewById(R.id.image);
			holder.text1 = (TextView) arg1.findViewById(R.id.textMiaoshu);
			holder.text2 = (TextView) arg1.findViewById(R.id.textwangzhi);
			arg1.setTag(holder);
		}else {
            holder = (ViewHolder) arg1.getTag();
        }

		holder.text1.setText((String)list.get(arg0).get("up"));
		holder.text2.setText((String)list.get(arg0).get("href"));
		holder.image.setImageResource((Integer)list.get(arg0).get("down"));
		return arg1;
	}
	
	class ViewHolder{

        TextView text1,text2;
        ImageView image ;
    }

	

}







