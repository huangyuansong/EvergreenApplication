package com.example.pc.evergreen.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.utils.VolleyLoadPicture;
import java.util.ArrayList;

/**
 * Created by pc on 2018/3/19.
 * 老干部资料RecyclerView适配器
 */

public class UpdateCadresAdapter extends RecyclerView.Adapter<UpdateCadresAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserData> mData;

    public UpdateCadresAdapter(Context context,ArrayList<UserData> data) {
        this.mData = data;
        this.context = context;
    }

    public void updateData(ArrayList<UserData> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.update_cadres_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData user = mData.get(position);

        // 绑定数据
        //Volley加载网络图片
        VolleyLoadPicture vlp = new VolleyLoadPicture(context, holder.update_cadres_image);
        if (user.getPhoto()==null){
            holder.update_cadres_image.setImageResource(R.mipmap.icon_head);
        }else {
            vlp.getmImageLoader().get(user.getPhoto(), vlp.getOne_listener());
        }

        holder.update_cadres_name.setText(user.getName());
        holder.update_cadres_unit.setText( user.getUnit());
        holder.update_cadres_level.setText( user.getGrade());
        holder.update_cadres_retired.setText(user.getTreatment());
        holder.update_cadres_phone.setText(user.getPhone());

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView update_cadres_image;
        TextView update_cadres_name;
        TextView update_cadres_unit;
        TextView update_cadres_level;
        TextView update_cadres_retired;
        TextView update_cadres_phone;


        public ViewHolder(View itemView) {
            super(itemView);
            update_cadres_image = itemView.findViewById(R.id.update_cadres_image);
            update_cadres_name = itemView.findViewById(R.id.update_cadres_name);
            update_cadres_unit = itemView.findViewById(R.id.update_cadres_unit);
            update_cadres_level = itemView.findViewById(R.id.update_cadres_level);
            update_cadres_retired = itemView.findViewById(R.id.update_cadres_retired);
            update_cadres_phone = itemView.findViewById(R.id.update_cadres_phone);


        }
    }

}
