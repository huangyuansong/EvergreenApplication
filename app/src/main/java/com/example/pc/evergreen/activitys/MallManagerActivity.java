package com.example.pc.evergreen.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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
import com.example.pc.evergreen.adapters.GoodsAdapter;
import com.example.pc.evergreen.adapters.UpdateCadresAdapter;
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


//商品管理
public class MallManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton back;
    private TextView title;
    private SwipeMenuRecyclerView mRecyclerViewMall;
    private RecyclerView.Adapter mAdapterMall;
    private ArrayList<Goods> mDataGoods = null;
    private Button bt_add_goods;

    private RecyclerView.LayoutManager mLayoutManagerMall;

    public MallManagerActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_manager);

        mRecyclerViewMall = findViewById(R.id.recycler_goods_mall);


// 设置Item添加和移除的动画
        mRecyclerViewMall.setItemAnimator(new DefaultItemAnimator());
// 设置Item之间间隔样式
        mRecyclerViewMall.addItemDecoration(new SimpleDividerItemDecoration(this));

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

                addItem = new SwipeMenuItem(MallManagerActivity.this)
                        .setBackground(R.color.menu_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                addItem1 = new SwipeMenuItem(MallManagerActivity.this)
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

                final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                System.out.println("点击了Item：" + adapterPosition);
                //一个弹出框的dialog的显示
                if (menuPosition == 0) {
                    //删除
                    final PromptMessageDialog promptMessageDialog = new PromptMessageDialog(MallManagerActivity.this, "商品删除后将不可恢复，确定要删除？");
                    promptMessageDialog.button_no.setText("取消");
                    promptMessageDialog.button_yes.setText("确定");
                    promptMessageDialog.button_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           String objectId1 =  mDataGoods.get(adapterPosition).getObjectId();
                           Goods goods = new Goods();
                           goods.setObjectId(objectId1);
                           goods.delete(MallManagerActivity.this,objectId1, new DeleteListener() {
                               @Override
                               public void onSuccess() {
                                   getData();
                                   new MessageDialog(MallManagerActivity.this,"删除成功！");

                               }

                               @Override
                               public void onFailure(int i, String s) {
                                   new MessageDialog(MallManagerActivity.this,"删除失败！错误代码是："+i);
                               }
                           });
                            promptMessageDialog.close();

                        }
                    });
                    promptMessageDialog.button_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            promptMessageDialog.close();
                        }
                    });

                } else if (menuPosition == 1) {
                    //修改
                    Intent intent = new Intent(MallManagerActivity.this, ModifyGoods.class);
                    Goods goods = mDataGoods.get(adapterPosition);
                    intent.putExtra("goodsData", goods);
                    startActivity(intent);
                }
            }
        };



        mRecyclerViewMall.setSwipeMenuCreator(mSipeMenuCreator);
        mRecyclerViewMall.setSwipeMenuItemClickListener(mSwipeMenuItemClickListener);
        mRecyclerViewMall.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewMall.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewMall.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(MallManagerActivity.this,GoodsDetails.class);
                Goods goodsrItem = mDataGoods.get(position);
                intent.putExtra("goodsrItem",goodsrItem);
                startActivity(intent);
            }
        });
        mRecyclerViewMall.setAdapter(mAdapterMall);


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
        title.setText("商品管理");
        bt_add_goods = findViewById(R.id.bt_add_goods);
        bt_add_goods.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.bt_add_goods:
                Intent intentAddGoods = new Intent(MallManagerActivity.this,AddGoodsActivity.class);
                startActivity(intentAddGoods);
                break;
        }
    }


    public List<Goods> getData() {
        mDataGoods = new ArrayList<Goods>();
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        //query.addWhereEqualTo("permissions", "members");
        query.setLimit(50);
        query.findObjects(MallManagerActivity.this, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                if (list.size()>0){
                    for (int i = 0;i<list.size();i++){
                        mDataGoods.add((Goods) list.get(i));
                        mAdapterMall = new GoodsAdapter(MallManagerActivity.this, (ArrayList<Goods>) mDataGoods);
                        mRecyclerViewMall.setLayoutManager(new LinearLayoutManager(MallManagerActivity.this));
                        // 设置adapter
                        mRecyclerViewMall.setAdapter(mAdapterMall);
                    }
                }else {
                    return;
                }


            }

            @Override
            public void onError(int i, String s) {
                new MessageDialog(MallManagerActivity.this,"数据获取失败，请查看网络状态！");

            }
        });
        return mDataGoods;
    }

}
