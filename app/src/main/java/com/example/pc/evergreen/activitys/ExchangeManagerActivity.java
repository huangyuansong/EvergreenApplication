package com.example.pc.evergreen.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.ExchangeApplyAdapter;
import com.example.pc.evergreen.beans.Apply;
import com.example.pc.evergreen.beans.Goods;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
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
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

public class ExchangeManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;//返回
    private TextView title;//界面标题
    private SwipeMenuRecyclerView mRecyclerViewExchange;
    private ArrayList<Apply> applyData = null;
    private RecyclerView.Adapter mAdapterExchange;
    private String userDataObjId;
    private String goodsDataObjId;
    private UserData mUserData;
    private Goods mGoods;
    private Apply apply;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_manager);
        mRecyclerViewExchange = findViewById(R.id.recycler_exchange_manager);
        // 设置Item添加和移除的动画
        mRecyclerViewExchange.setItemAnimator(new DefaultItemAnimator());
// 设置Item之间间隔样式
        mRecyclerViewExchange.addItemDecoration(new SimpleDividerItemDecoration(this));

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

                addItem = new SwipeMenuItem(ExchangeManagerActivity.this)
                        .setBackground(R.color.menu_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                addItem1 = new SwipeMenuItem(ExchangeManagerActivity.this)
                        .setBackground(R.color.menu_update)
                        .setText("兑换")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
                swipeRightMenu.addMenuItem(addItem1); // 添加菜单到右侧。

            }
        };
        //(二）为滑动菜单添加事件监听
        SwipeMenuItemClickListener mSwipeMenuItemClickListener = new SwipeMenuItemClickListener() {
            String  applyObjId;
            @Override
            public void onItemClick(final SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。

                final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                applyObjId =  applyData.get(adapterPosition).getObjectId();
                goodsDataObjId = applyData.get(adapterPosition).getApplyGoodsId();
                userDataObjId = applyData.get(adapterPosition).getApplyUserDataId();
               // goodsDataObjId = applyData.get(adapterPosition).getApplyGoodsId();
               // userDataObjId = applyData.get(adapterPosition).getApplyUserDataId();
                //一个弹出框的dialog的显示
                if (menuPosition == 0) {
                    //删除
                    final PromptMessageDialog promptMessageDialog = new PromptMessageDialog(ExchangeManagerActivity.this, "删除后将不可恢复，确定要删除？");
                    promptMessageDialog.button_no.setText("取消");
                    promptMessageDialog.button_yes.setText("确定");
                    promptMessageDialog.button_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Apply apply = new Apply();
                            apply.delete(ExchangeManagerActivity.this,applyObjId, new DeleteListener() {
                                @Override
                                public void onSuccess() {
                                    new MessageDialog(ExchangeManagerActivity.this,"删除成功！");

                                }

                                @Override
                                public void onFailure(int i, String s) {
                                   new MessageDialog(ExchangeManagerActivity.this,"删除失败！错误代码是："+i);


                                }
                            });

                            promptMessageDialog.close();
                            getData();

                        }
                    });
                    promptMessageDialog.button_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            promptMessageDialog.close();
                        }
                    });

                } else if (menuPosition == 1) {
                    BmobQuery<Goods> goodsDataBmobQuery = new BmobQuery<>();
                    goodsDataBmobQuery.getObject(ExchangeManagerActivity.this, goodsDataObjId, new GetListener<Goods>() {
                        @Override
                        public void onSuccess(final Goods goods) {
                            //如果库存小于1,退出
                            if (goods.getGoodsIntegral()<1){
                                new MessageDialog(ExchangeManagerActivity.this,"已被抢光了！");
                            }else {
                                final Goods newGoods = goods;
                                BmobQuery<UserData> userDataBmobQuery = new BmobQuery<>();
                                userDataBmobQuery.getObject(ExchangeManagerActivity.this, userDataObjId, new GetListener<UserData>() {
                                    @Override
                                    public void onSuccess(final UserData userData) {
                                        //如果个人积分小于商品积分，退出
                                        final Integer userDataintegral = userData.getIntegral();
                                        final Integer goodsIntegral = newGoods.getGoodsIntegral();
                                        if (userDataintegral<goodsIntegral){
                                            new MessageDialog(ExchangeManagerActivity.this,"积分不足！");
                                            return;
                                        }else {
                                            final UserData newUserData = userData;
                                            newGoods.increment("inventory",-1);
                                            newGoods.update(ExchangeManagerActivity.this,newGoods.getObjectId(),new UpdateListener(){
                                                @Override
                                                public void onSuccess() {
                                                    newUserData.increment("integral",-goodsIntegral);
                                                    newUserData.update(ExchangeManagerActivity.this,newUserData.getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void onSuccess() {

                                                            Apply apply = new Apply();
                                                            apply.setObjectId(applyObjId);
                                                            apply.delete(ExchangeManagerActivity.this, new DeleteListener() {
                                                                @Override
                                                                public void onSuccess() {
                                                                    getData();
                                                                    MessageDialog messageDialog = new MessageDialog(ExchangeManagerActivity.this,"兑换成功！");

                                                                }

                                                                @Override
                                                                public void onFailure(int i, String s) {
                                                                    new MessageDialog(ExchangeManagerActivity.this,"操作失败！错误代码是："+i);
                                                                }
                                                            });
                                                        }
                                                        @Override
                                                        public void onFailure(int i, String s) {
                                                            new MessageDialog(ExchangeManagerActivity.this,"操作失败！错误代码是："+i);
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(int i, String s) {
                                                    new MessageDialog(ExchangeManagerActivity.this,"操作失败！22222错误代码是："+s);

                                                }
                                            });

                                        }
                                    }
                                    @Override
                                    public void onFailure(int i, String s) {
                                        new MessageDialog(ExchangeManagerActivity.this,"操作失败！错误代码是："+s);

                                    }
                                });
                            }
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            new MessageDialog(ExchangeManagerActivity.this,"操作失败！错误代码是："+s);
                        }
                    });
                }
            }
        };

        mRecyclerViewExchange.setSwipeMenuCreator(mSipeMenuCreator);
        mRecyclerViewExchange.setSwipeMenuItemClickListener(mSwipeMenuItemClickListener);
        mRecyclerViewExchange.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewExchange.setItemAnimator(new DefaultItemAnimator());

        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        title.setText("兑换管理");
        back.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       getData();
    }

    public List<Apply> getData() {
        applyData = new ArrayList<Apply>();
        BmobQuery<Apply> query = new BmobQuery<Apply>();
        query.setLimit(5);
        query.findObjects(ExchangeManagerActivity.this, new FindListener<Apply>() {
            @Override
            public void onSuccess(List<Apply> list) {

                for (int i = 0;i<list.size();i++){
                    if (list.size()>0){
                        applyData.add((Apply) list.get(i));
                        mAdapterExchange = new ExchangeApplyAdapter(ExchangeManagerActivity.this,applyData);
                        mRecyclerViewExchange.setLayoutManager(new LinearLayoutManager(ExchangeManagerActivity.this));
                        // 设置adapter
                        mRecyclerViewExchange.setAdapter(mAdapterExchange);
                    }else {
                        return;
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                new MessageDialog(ExchangeManagerActivity.this,"获取数据失败！请查看网络是否正常！");
            }
        });
        return applyData;
    }
}
