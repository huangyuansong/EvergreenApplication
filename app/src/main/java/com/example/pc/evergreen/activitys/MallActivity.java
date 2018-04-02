package com.example.pc.evergreen.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.GoodsExchangeAdapter;
import com.example.pc.evergreen.beans.Goods;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.utils.SimpleDividerItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

//积分兑换业务

public class MallActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton back;
    private TextView title;
    private SwipeMenuRecyclerView recycler_goods_exchange;
    private RecyclerView.Adapter mAdapterMall;
    private ArrayList<Goods> mDataGoods = null;
    private TextView tv_exchange_integral;
    public UserData userData;

    public MallActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);

        recycler_goods_exchange = findViewById(R.id.recycler_goods_exchange);

// 设置Item添加和移除的动画
        recycler_goods_exchange.setItemAnimator(new DefaultItemAnimator());
// 设置Item之间间隔样式
        recycler_goods_exchange.addItemDecoration(new SimpleDividerItemDecoration(this));


// 设置Item之间间隔样式
        recycler_goods_exchange.setLayoutManager(new LinearLayoutManager(MallActivity.this));
        recycler_goods_exchange.setAdapter(mAdapterMall);

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
        title.setText("积分兑换");
        tv_exchange_integral = findViewById(R.id.tv_exchange_integral);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    public List<Goods> getData() {
       userData = (UserData) getIntent().getSerializableExtra("userData");
        Integer integral = userData.getIntegral();
        tv_exchange_integral.setText("我的积分："+integral+"分");
        mDataGoods = new ArrayList<Goods>();
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.setLimit(50);
        query.findObjects(this, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                for (int i = 0;i<list.size();i++){
                    mDataGoods.add((Goods) list.get(i));
                    mAdapterMall = new GoodsExchangeAdapter(MallActivity.this, (ArrayList<Goods>) mDataGoods,userData);
                    // 设置adapter
                    recycler_goods_exchange.setAdapter(mAdapterMall);

                }
            }
            @Override
            public void onError(int i, String s) {

            }
        });
        return mDataGoods;
    }


}
