package com.help.helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.help.helper.fragment.InfoFragment;
import com.help.helper.fragment.MapFragment;

/**
 * Created by 小怪兽 on 2016/5/31.
 */
public class MainActivity extends Activity{
    private MapFragment mapFragment;
    private InfoFragment infoFragment;
    private Fragment[] fragments;
    private Button[] Tabs;
    private int index;
    private int currentTabIndex;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mapFragment = new MapFragment();
        infoFragment = new InfoFragment();
        fragments = new Fragment[]{mapFragment,infoFragment}; //把fragment放入数组中
        //把mapFragment和infoFragment放入容器中，显示mapFragment并隐藏infoFragment
        getFragmentManager().beginTransaction().add(R.id.fragment_content,mapFragment).add(R.id.fragment_content,infoFragment).show(mapFragment).hide(infoFragment).commit();
    }
    public void init(){
        Tabs = new Button[2];
        Tabs[0] = (Button)findViewById(R.id.tab_map);
        Tabs[1] = (Button)findViewById(R.id.tab_info);
        Tabs[0].setSelected(true);
    }
    //在布局文件中设置按钮的onClick属性为onTabClick，在这里为其设置点击事件
    public void onTabClick(View view){
        switch (view.getId()){
            case R.id.tab_map:
                index = 0;
                break;
            case R.id.tab_info:
                index = 1;
                break;
            default:
                break;
        }
        if (currentTabIndex != index){
            FragmentTransaction trx = getFragmentManager().beginTransaction(); //实例化FragmentTransaction并且开启一个事务
            trx.hide(fragments[currentTabIndex]); //隐藏fragment
            trx.show(fragments[index]).commit(); //显示fragment并且结束事务
        }
        Tabs[currentTabIndex].setSelected(false); //把初始按钮设置为非选中状态
        Tabs[index].setSelected(true); //把当前按钮设置为选中状态
        currentTabIndex = index;
    }
    public void onBackPressed(){
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
