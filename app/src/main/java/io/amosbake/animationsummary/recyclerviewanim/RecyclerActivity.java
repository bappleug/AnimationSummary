package io.amosbake.animationsummary.recyclerviewanim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.amosbake.animationsummary.R;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Created by Ray on 2017/5/31.
 */

public class RecyclerActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnRemove)
    Button btnRemove;

    List<DemoModel> data;
    List<DemoModel> dataPool;

    {
        data = new ArrayList<>();
        dataPool = new ArrayList<>();
        dataPool.add(new DemoModel("123", "12lsknjlskf"));
        dataPool.add(new DemoModel("124", "氨基酸饭卡健身房哈会计师费来看"));
        dataPool.add(new DemoModel("12421", "时空裂缝哈市反馈"));
        dataPool.add(new DemoModel("124", "发了康师傅就拉开副教授拉就算了"));
        dataPool.add(new DemoModel("1542", "爱丽丝疯狂就暗示法了"));
        dataPool.add(new DemoModel("1541", "广东省高"));
        dataPool.add(new DemoModel("123", "按时发生"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        mRecyclerView.setAdapter(new RecyclerView.Adapter<CustomViewHolder>() {
            @Override
            public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View root = getLayoutInflater().inflate(R.layout.item_recycler, parent, false);
                return new CustomViewHolder(root);
            }

            @Override
            public void onBindViewHolder(CustomViewHolder holder, int position) {
                holder.setText(R.id.name, data.get(position).name);
                holder.setText(R.id.desc, data.get(position).desc);
            }

            @Override
            public int getItemCount() {
                return data == null ? 0 : data.size();
            }

        });
    }

    @OnClick(R.id.btnAdd)
    void addMockData() {
        data.add(dataPool.get(data.size() % dataPool.size()));
        mRecyclerView.scrollToPosition(data.size() - 1);
        mRecyclerView.getAdapter().notifyItemInserted(data.size() - 1);
    }

    @OnClick(R.id.btnRemove)
    void removeMockData() {
        if(data.size() > 0){
            data.remove(data.size() - 1);
            mRecyclerView.scrollToPosition(data.size() - 1);
            mRecyclerView.getAdapter().notifyItemRemoved(data.size());
        }
    }

    @OnClick(R.id.btnInsert)
    void insertFirst(){
        data.add(0, dataPool.get((int) (Math.random() * dataPool.size())));
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.getAdapter().notifyItemInserted(0);
    }

    @OnClick(R.id.btnSwipe)
    void swipeFirst(){
        if(data.size() > 0){
            data.remove(0);
            mRecyclerView.scrollToPosition(0);
            mRecyclerView.getAdapter().notifyItemRemoved(0);
        }
    }

    @OnClick(R.id.btnAddRange)
    void addRange(){
        data.addAll(0, dataPool);
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.getAdapter().notifyItemRangeInserted(0, dataPool.size());
    }

    @OnClick(R.id.btnRemoveAll)
    void removeAll(){
        if(data.size() > 0){
            int size = data.size();
            data.clear();
            mRecyclerView.scrollToPosition(0);
            mRecyclerView.getAdapter().notifyItemRangeRemoved(0, size);
        }
    }
}
