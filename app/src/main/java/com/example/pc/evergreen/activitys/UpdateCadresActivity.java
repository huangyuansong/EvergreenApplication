package com.example.pc.evergreen.activitys;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.UpdateCadresAdapter;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.PromptMessageDialog;
import com.example.pc.evergreen.utils.SimpleDividerItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

//老干部资料管理列表界面List（）
public class UpdateCadresActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private TextView title;
    private SwipeMenuRecyclerView  mRecyclerView;
    private UpdateCadresAdapter mAdapter;
    private ArrayList<UserData> mData;
    private Button bt_add_cadres;

    private RecyclerView.LayoutManager mLayoutManager;

    public UpdateCadresActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cadres);

       mRecyclerView = findViewById(R.id.recycler_cdres);


// 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
// 设置Item之间间隔样式
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        //一、添加item的滑动菜单功能：
        //(一)创建滑动菜单,添加滑动菜单到右侧
        SwipeMenuCreator mSipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.menu_delete);

// 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem addItem;
                SwipeMenuItem addItem1;

                addItem = new SwipeMenuItem(UpdateCadresActivity.this)
                        .setBackground(R.color.menu_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                        //.setImage(R.drawable.delete);
                addItem1 = new SwipeMenuItem(UpdateCadresActivity.this)
                        .setBackground(R.color.menu_update)
                        .setText("修改")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
                swipeRightMenu.addMenuItem(addItem1); // 添加菜单到右侧。

            }
        };
        //(二）为滑动菜单添加事件监听
        SwipeMenuItemClickListener mSwipeMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                System.out.println("点击了Item：" + adapterPosition);
                //一个弹出框的dialog的显示
                if (menuPosition == 0) {
                    final PromptMessageDialog promptMessageDialog = new PromptMessageDialog(UpdateCadresActivity.this, "删除该信息后将不可恢复，确定要删除？");
                    promptMessageDialog.button_no.setText("取消");
                    promptMessageDialog.button_yes.setText("确定");
                    promptMessageDialog.button_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            promptMessageDialog.close();
                        }
                    });
                    promptMessageDialog.button_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            promptMessageDialog.close();
                        }
                    });

                }else if (menuPosition == 1 ){
                    Intent intent = new Intent(UpdateCadresActivity.this,ModifyPersonalDataActivity.class);
                    UserData userMotify = mData.get(adapterPosition);
                    intent.putExtra("userMotify",userMotify);
                    startActivity(intent);
                }
            }
        };


        mRecyclerView.setSwipeMenuCreator(mSipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mSwipeMenuItemClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(UpdateCadresActivity.this,PersonalInformationActivity.class);
                UserData userItem = mData.get(position);
                intent.putExtra("userItem",userItem);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initView() {

        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("老干部资料");
        bt_add_cadres = findViewById(R.id.bt_add_cadres);
        bt_add_cadres.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.bt_add_cadres:
                Intent intentAddCadresActivity = new Intent(UpdateCadresActivity.this,AddCadresActivity.class);
                startActivity(intentAddCadresActivity);
                break;
        }
    }


    public List<UserData> getData() {
        mData = new ArrayList<UserData>();
        BmobQuery<UserData> query = new BmobQuery<UserData>();
        query.addWhereEqualTo("permissions", "members");
        query.setLimit(50);
        query.findObjects(this, new FindListener<UserData>() {
            @Override
            public void onSuccess(List<UserData> list) {
                    for (int i = 0;i<list.size();i++){
                    mData.add((UserData) list.get(i));
                    mAdapter = new UpdateCadresAdapter(UpdateCadresActivity.this,mData);

                        // 设置adapter
                        mRecyclerView.setAdapter(mAdapter);
                    }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        return mData;
    }

}
