package com.example.pc.evergreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.ItemRecyclerViewAdapterManager;
import com.example.pc.evergreen.utils.DummyContent;
import com.example.pc.evergreen.utils.DummyContentManager;

public class EditorFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount;
    private EditorFragment.OnListFragmentInteractionListener mListener;


    public EditorFragment() {
    }

    public static EditorFragment newInstance(int columnCount) {
        EditorFragment fragment = new EditorFragment();
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
        View view = inflater.inflate(R.layout.fragment_editor, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            switch (mColumnCount){
                case 1:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    recyclerView.setAdapter(new ItemRecyclerViewAdapterManager(DummyContentManager.EDITOR, mListener));
                    break;
                case 2:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    recyclerView.setAdapter(new ItemRecyclerViewAdapterManager(DummyContentManager.NOTICE, mListener));
                    break;
                case 3:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    recyclerView.setAdapter(new ItemRecyclerViewAdapterManager(DummyContentManager.INTEGRAL, mListener));
                    break;
                case 4:
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    recyclerView.setAdapter(new ItemRecyclerViewAdapterManager(DummyContentManager.STATISTICAL, mListener));
                    break;
            }
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditorFragment.OnListFragmentInteractionListener) {
            mListener = (EditorFragment.OnListFragmentInteractionListener) context;
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
        void onListFragmentInteraction(DummyContentManager.DummyItemManager item);


    }

}
