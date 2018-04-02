package com.example.pc.evergreen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.fragments.EditorFragment;
import com.example.pc.evergreen.utils.DummyContentManager;

import java.util.List;

/**
 * Created by pc on 2018/3/19.
 */

public class ItemRecyclerViewAdapterManager extends RecyclerView.Adapter<ItemRecyclerViewAdapterManager.ViewHolder> {
    private final List<DummyContentManager.DummyItemManager> mValues;
    private final EditorFragment.OnListFragmentInteractionListener mListener;


    public ItemRecyclerViewAdapterManager(List<DummyContentManager.DummyItemManager> items, EditorFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ItemRecyclerViewAdapterManager.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new ItemRecyclerViewAdapterManager.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemRecyclerViewAdapterManager.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mService_image.setImageResource(mValues.get(position).service_image);
        holder.mService_title.setText(mValues.get(position).service_title);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mService_image;
        public final TextView mService_title;
        public DummyContentManager.DummyItemManager mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mService_image = (ImageView) view.findViewById(R.id.acImage);
            mService_title = (TextView) view.findViewById(R.id.acName);

        }

    }
}
