package slidingMenu_left;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nanshan.xky_client.MainActivity;
import com.nanshan.xky_client.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyFragment extends Fragment {
	
	Context context;
	Fragment_first fragment1;
	MainActivity fragment2;
	String title[]={"数字化校园","教学服务","教务系统","毕业生就业"};
	
	public void setContext(Context context){
		
		this.context=context;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.list_left_menu, null);
	}
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ListView listView = (ListView) view.findViewById(R.id.listViewleft);
		listView.setAdapter(new SimpleAdapter(context,
				getData(),
				R.layout.list_item_leftmenu,
				new String[]{"title"},
				new int[]{R.id.textViewlistmenu}
				));
		listView.setCacheColorHint(0);//设置listView背景为透明
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				switch(arg2)
				{
				case 0:
					
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				}
				
			}
		});
		
		Button button = (Button) view.findViewById(R.id.imageuttonleftout);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity act = (MainActivity) getActivity();
				act.finish();
			}
		});
		
	}
	
	public List<Map<String, Object>> getData(){
		 List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<4;i++)
		{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", title[i]);
			list.add(map);
		}
		return list;
		
	}
	
	
}
