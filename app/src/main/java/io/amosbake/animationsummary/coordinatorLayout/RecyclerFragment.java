package io.amosbake.animationsummary.coordinatorLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lexing.lrecyclerview.adapter.BaseRecyclerAdapter;
import com.lexing.lrecyclerview.adapter.RecyclerHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.amosbake.animationsummary.R;

/**
 * Created by Ray on 2017/1/3.
 */

public class RecyclerFragment extends Fragment {

    RecyclerView mRecyclerView;
    private static final String KEY="key";
    private String title="测试";

    List<String> mDatas=new ArrayList<>();
    private BaseRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recyclerview, container);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        for(int i=0;i<30;i++){
            String s = String.format("我是第%d个" + title, i);
            mDatas.add(s);
        }

        mAdapter = new ItemAdapter(mRecyclerView, mDatas, android.R.layout.simple_list_item_1);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    private class ItemAdapter extends BaseRecyclerAdapter<String>{

        ItemAdapter(RecyclerView v, Collection<String> datas, int itemLayoutId) {
            super(v, datas, itemLayoutId);
        }

        @Override
        protected void convert(RecyclerHolder holder, String item, int position, boolean isScrolling) {
            holder.setText(android.R.id.text1, item);
        }
    }
}
