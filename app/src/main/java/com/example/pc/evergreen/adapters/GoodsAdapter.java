package com.example.pc.evergreen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.beans.Goods;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.utils.VolleyLoadPicture;

import java.util.ArrayList;

/**
 * Created by pc on 2018/3/26.
 * 商品管理的Adapter
 */

public class GoodsAdapter  extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Goods> mData;

    public GoodsAdapter(Context context,ArrayList<Goods> data) {
        this.mData = data;
        this.context = context;
    }

    public void updateData(ArrayList<Goods> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.goods_item, parent, false);
        // 实例化viewholder
        GoodsAdapter.ViewHolder viewHolder = new GoodsAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GoodsAdapter.ViewHolder holder, int position) {
        Goods goods = mData.get(position);

        // 绑定数据
        //Volley加载网络图片
        VolleyLoadPicture vlp = new VolleyLoadPicture(context,holder.goods_image_item);
        if (goods.getGoodsImage()==null){
            holder.goods_image_item.setImageResource(R.mipmap.noimage);
        }else {
            vlp.getmImageLoader().get(goods.getGoodsImage(), vlp.getOne_listener());
        }

        holder.goods_name_item.setText(goods.getGoodsName());
        holder.goods_model_item.setText( goods.getGoodsModel());
        holder.goods_goodsIntegral_item.setText(goods.getGoodsIntegral().toString());
        holder.goods_inventory_item.setText( goods.getInventory().toString());

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView goods_image_item;
        TextView goods_name_item;
        TextView goods_model_item;
        TextView goods_inventory_item;
        TextView goods_goodsIntegral_item;


        public ViewHolder(View itemView) {
            super(itemView);
            goods_image_item = itemView.findViewById(R.id.goods_image_item);
            goods_name_item = itemView.findViewById(R.id.goods_name_item);
            goods_model_item = itemView.findViewById(R.id.goods_model_item);
            goods_goodsIntegral_item = itemView.findViewById(R.id.goods_goodsIntegral_item);
            goods_inventory_item = itemView.findViewById(R.id.goods_inventory_item);


        }
    }

}
