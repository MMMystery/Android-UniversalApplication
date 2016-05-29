package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.SeachUserAdapter;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_search;
    private Button bt_search;
    private RecyclerView rv_searchfriend;
    private SwipeRefreshLayout sw_refresh;
    private SeachUserAdapter seachUserAdapter;
    private List<MyUser> userDatasList;
    private MyUser userDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.addfriendactivity_et_search);
        bt_search = (Button) findViewById(R.id.addfriendactivity_bt_search);
        rv_searchfriend = (RecyclerView) findViewById(R.id.addfriendactivity_rc_searchfriend);
        sw_refresh = (SwipeRefreshLayout) findViewById(R.id.addfriendactivity_sw_refresh);
        bt_search.setOnClickListener(this);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        rv_searchfriend.setLayoutManager(layoutManager);
        userDatasList = new ArrayList<MyUser>();
        seachUserAdapter = new SeachUserAdapter(this, userDatasList);
        rv_searchfriend.setAdapter(seachUserAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addfriendactivity_bt_search:
                    search();
                break;
        }
    }

    private void search() {
        String name =et_search.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastUtils.show(getApplicationContext(),"请填写用户名");
            sw_refresh.setRefreshing(false);
            return;
        }else{
            BmobQuery<MyUser> query = new BmobQuery<>();
            //去掉当前用户
            try {
                BmobUser user = BmobUser.getCurrentUser(this);
                query.addWhereNotEqualTo("username",user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
            query.addWhereContains("username", name);
            query.setLimit(20);//设置搜索20条
            query.order("-createdAt");
            query.findObjects(this, new FindListener<MyUser>() {
                @Override
                public void onSuccess(List<MyUser> object) {
                    if (object != null && object.size() > 0) {
                        for (MyUser datasList : object) {
                            userDatas = new MyUser();//几组数据就得new几次
                            userDatas.setAvater(datasList.getAvater());
                            userDatas.setObjectId(datasList.getObjectId());
                            userDatas.setUsername(datasList.getUsername());
                            userDatasList.add(userDatas);//每一组数据也都得加到List当中去
                        }

                        //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                        //这样的话就不需要网络请求完后才能做setAdapter这些操作了

                        seachUserAdapter.notifyDataSetChanged();
//                        sw_refresh.setRefreshing(false);

                    } else {
                        //没查到这个人
//                        sw_refresh.setRefreshing(false);
//                        seachUserAdapter.setDatas(null);
//                        seachUserAdapter.notifyDataSetChanged();
                        ToastUtils.show(getApplicationContext(),"没查到此人");
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }

    }

}
