package com.roman.github.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roman.github.GitHubAPI.pojo.Repository;
import com.roman.github.R;
import com.roman.github.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anna on 28.05.2016.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.Holder> {

    private static final String TAG = RepositoryAdapter.class.getSimpleName();

    private List<Repository> mData;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Logger.d(TAG, "onCreateViewHolder, parent [" + parent + "], viewType [" + viewType + "]");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
//        Logger.d(TAG, "onBindViewHolder, holder [" + holder + "], position [" + position + "]");
        Repository item = mData.get(position);
        holder.tx.setText(item.name);
        holder.description.setText(item.description);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void addRespositories(List<Repository> list) {
        if(mData == null) {
            mData = new ArrayList<>(list);
        } else {
            mData.addAll(list);
        }
        notifyItemRangeInserted(mData.size() - list.size(), mData.size());
    }

    public void addRespository(Repository repo) {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(repo);
        notifyItemInserted(mData.size());
    }

    public static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView tx;
        @BindView(R.id.description)
        TextView description;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
