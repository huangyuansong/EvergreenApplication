package com.example.pc.evergreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.ItemRecyclerViewAdapter;
import com.example.pc.evergreen.utils.DummyContent;


//自定义主内容区的fragment
public class PageFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount;
    private OnListFragmentInteractionListener mListener;

   
    public PageFragment() {
    }

    public static PageFragment newInstance(int columnCount) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            switch (mColumnCount){
                case 1:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    recyclerView.setAdapter(new ItemRecyclerViewAdapter(DummyContent.ITEMS_1, mListener));
                    break;
                case 2:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    recyclerView.setAdapter(new ItemRecyclerViewAdapter(DummyContent.ITEMS_2, mListener));
                    break;
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //点击item的监听接口，让实现类去实现（必须实现）
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);


    }
}
