package com.example.pc.evergreen.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.beans.Apply;
import com.example.pc.evergreen.beans.Goods;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.dialog.PromptMessageDialog;
import com.example.pc.evergreen.utils.VolleyLoadPicture;
import java.util.ArrayList;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by pc on 2018/3/27.
 * 商品兑换的Adapter
 */

public class GoodsExchangeAdapter extends RecyclerView.Adapter<GoodsExchangeAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Goods> mData;
    private UserData userData;

    public GoodsExchangeAdapter(Context context, ArrayList<Goods> data,UserData userData) {
        this.mData = data;
        this.context = context;
        this.userData = userData;
    }


    @Override
    public GoodsExchangeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.goods_exchange_item, parent, false);
        // 实例化viewholder
        GoodsExchangeAdapter.ViewHolder viewHolder = new GoodsExchangeAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GoodsExchangeAdapter.ViewHolder holder, int position) {
        final Goods goods = mData.get(position);

        // 绑定数据
        //Volley加载网络图片
        VolleyLoadPicture vlp = new VolleyLoadPicture(context,holder.goods_image_exchange_item);
        if (goods.getGoodsImage()==null){
            holder.goods_image_exchange_item.setImageResource(R.mipmap.noimage);
        }else {
            vlp.getmImageLoader().get(goods.getGoodsImage(), vlp.getOne_listener());
        }

        holder.goods_name_exchange_item.setText(goods.getGoodsName());
        holder.goods_model_exchange_item.setText( goods.getGoodsModel());
        holder.goods_Integral_exchange_item.setText( goods.getGoodsIntegral().toString());
        holder.goods_inventory_exchange_item.setText(goods.getInventory().toString());
        holder.bt_goods_exchange_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer goodsIntegral = goods.getGoodsIntegral();
                final Integer goodsInventory = goods.getInventory();
                if (goodsInventory < 1) {
                    new MessageDialog(context, "库存不足！");
                    return;
                } else {
                    Integer mIntegral = userData.getIntegral();
                    if (mIntegral < goodsIntegral) {
                        new MessageDialog(context, "您的积分不足，参加活动获取更多积分！");
                        return;
                    } else {
                        final PromptMessageDialog promptMessageDialog = new PromptMessageDialog(context, "您确定用" + goodsIntegral + "积分兑换此商品吗？");
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
                                Apply apply = new Apply();
                                apply.setApplyUserDataId(userData.getObjectId());
                                apply.setApplyGoodsId(goods.getObjectId());
                                apply.save(context, new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        new MessageDialog(context, "已提交,请尽快到市老干部局领取！（联系电话：28230936）");
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        new MessageDialog(context, "提交失败！错误代码是：" + i);
                                    }
                                });
                                promptMessageDialog.close();
                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView goods_image_exchange_item;
        TextView goods_name_exchange_item;
        TextView goods_model_exchange_item;
        TextView goods_Integral_exchange_item;
        TextView goods_inventory_exchange_item;
        Button bt_goods_exchange_item;


        public ViewHolder(View itemView) {
            super(itemView);
            goods_image_exchange_item = itemView.findViewById(R.id.goods_image_exchange_item);
            goods_name_exchange_item = itemView.findViewById(R.id.goods_name_exchange_item);
            goods_model_exchange_item = itemView.findViewById(R.id.goods_model_exchange_item);
            goods_Integral_exchange_item = itemView.findViewById(R.id.goods_Integral_exchange_item);
            goods_inventory_exchange_item = itemView.findViewById(R.id.goods_inventory_exchange_item);
            bt_goods_exchange_item = itemView.findViewById(R.id.bt_goods_exchange_item);
        }
    }
}
