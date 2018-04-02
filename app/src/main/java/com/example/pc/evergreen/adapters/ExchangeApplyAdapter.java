package com.example.pc.evergreen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.activitys.ExchangeManagerActivity;
import com.example.pc.evergreen.activitys.MallManagerActivity;
import com.example.pc.evergreen.beans.Apply;
import com.example.pc.evergreen.beans.Goods;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.utils.VolleyLoadPicture;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by pc on 2018/3/29.
 * 兑换列表Adapter
 */

public class ExchangeApplyAdapter extends RecyclerView.Adapter<ExchangeApplyAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Apply> mApplyData;

    public ExchangeApplyAdapter(final Context context, ArrayList<Apply> data) {
        this.mApplyData = data;
        this.context = context;
    }

    public void updateData(ArrayList<Apply> data) {
        this.mApplyData = data;
        notifyDataSetChanged();
    }

    @Override
    public ExchangeApplyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.apply_item, parent, false);
        // 实例化viewholder
        ExchangeApplyAdapter.ViewHolder viewHolder = new ExchangeApplyAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ExchangeApplyAdapter.ViewHolder holder, int position) {
       final Apply apply = mApplyData.get(position);
       final String mUserDataId = apply.getApplyUserDataId();
       final String mGoodsDataId = apply.getApplyGoodsId();

        BmobQuery<UserData> userDataBmobQuery = new BmobQuery<>();
        userDataBmobQuery.getObject(context, mUserDataId, new GetListener<UserData>() {
            @Override
            public void onSuccess(final UserData userData) {
               // applyUserData = userData;
                BmobQuery<Goods> goodsBmobQuery = new BmobQuery<>();
                goodsBmobQuery.getObject(context, mGoodsDataId, new GetListener<Goods>() {
                    @Override
                    public void onSuccess(Goods goods) {
                         // 绑定数据
                        //Volley加载网络图片
                        VolleyLoadPicture vlp = new VolleyLoadPicture(context,holder.tv_apply_photo);

                        if (userData.getPhoto()==null){
                            holder.tv_apply_photo.setImageResource(R.mipmap.icon_head);
                        }else {
                            vlp.getmImageLoader().get(userData.getPhoto(), vlp.getOne_listener());
                        }

                        holder.et_apply_username.setText(userData.getName());
                        holder.et_apply_goods.setText( goods.getGoodsName());
                        holder.et_apply_model.setText(goods.getGoodsModel());
                        holder.et_apply_integral.setText( goods.getGoodsIntegral().toString());
                        holder.et_apply_date.setText( apply.getCreatedAt());
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        new MessageDialog(context,"操作失败！错误代码是："+i);

                    }
                });

            }

            @Override
            public void onFailure(int i, String s) {
                new MessageDialog(context,"操作失败！错误代码是："+i);

            }
        });




    }

    @Override
    public int getItemCount() {
        return mApplyData == null ? 0 : mApplyData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView tv_apply_photo;
        TextView et_apply_username;
        TextView et_apply_goods;
        TextView et_apply_model;
        TextView et_apply_integral;
        TextView et_apply_date;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_apply_photo = itemView.findViewById(R.id.tv_apply_photo);
            et_apply_username = itemView.findViewById(R.id.et_apply_username);
            et_apply_goods = itemView.findViewById(R.id.et_apply_goods);
            et_apply_model = itemView.findViewById(R.id.et_apply_model);
            et_apply_integral = itemView.findViewById(R.id.et_apply_integral);
            et_apply_date = itemView.findViewById(R.id.et_apply_date);
        }
    }

}
