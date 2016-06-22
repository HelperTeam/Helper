package com.help.helper.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.help.helper.AddInfoActivity;
import com.help.helper.R;


/**
 * Created by 小怪兽 on 2016/6/2.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {
    private InfoSurroundFragment infoSurroundFragment;
    private InfoMessageFragment infoMessageFragment;
    private InfoFriendFragment infoFriendFragment;
    private InfoUserFragment infoUserFragment;
    private Fragment[] fragments;
    private Button[] Tabs;
    private int currentTabIndex;
    private int index;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_info,container,false);
        infoSurroundFragment = new InfoSurroundFragment();
        infoMessageFragment = new InfoMessageFragment();
        infoFriendFragment = new InfoFriendFragment();
        infoUserFragment = new InfoUserFragment();
        fragments = new Fragment[]{infoSurroundFragment,infoMessageFragment,infoFriendFragment,infoUserFragment};
        getChildFragmentManager().beginTransaction().add(R.id.info_fragment_content,infoSurroundFragment).add(R.id.info_fragment_content,infoMessageFragment).add(R.id.info_fragment_content,infoFriendFragment).add(R.id.info_fragment_content,infoUserFragment).show(infoSurroundFragment).hide(infoMessageFragment).hide(infoFriendFragment).hide(infoUserFragment).commit();
        Tabs = new Button[5];
        Tabs[0] = (Button)view.findViewById(R.id.info_surround);
        Tabs[0].setOnClickListener(this);
        Tabs[1] = (Button)view.findViewById(R.id.info_message);
        Tabs[1].setOnClickListener(this);
        Tabs[2] = (Button)view.findViewById(R.id.info_add);
        Tabs[2].setOnClickListener(this);
        Tabs[3] = (Button)view.findViewById(R.id.info_friend);
        Tabs[3].setOnClickListener(this);
        Tabs[4] = (Button)view.findViewById(R.id.info_user);
        Tabs[4].setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.info_surround:
                index = 0;
                break;
            case R.id.info_message:
                index = 1;
                break;
            case R.id.info_add:
                Intent intent = new Intent(getActivity(),AddInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.info_friend:
                index = 2;
                break;
            case R.id.info_user:
                index = 3;
                break;
            default:
                break;
        }
        if(currentTabIndex != index){
            FragmentTransaction trx = getChildFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            trx.show(fragments[index]).commit();
        }
        Tabs[currentTabIndex].setSelected(false);
        Tabs[index].setSelected(true);
        currentTabIndex = index;
    }
}
