package com.roman.github.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roman.github.R;
import com.roman.github.data.RepositoryData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anna on 28.05.2016.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.Holder> {

    private static final String TAG = RepositoryAdapter.class.getSimpleName();

    private List<RepositoryData> mData;

    public interface OnItemClickListener {
        void onItemClick(RepositoryData item);
    }

    private OnItemClickListener mListener;

    public RepositoryAdapter(OnItemClickListener listener) {
        mListener = listener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Logger.d(TAG, "onCreateViewHolder, parent [" + parent + "], viewType [" + viewType + "]");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_list_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
//        Logger.d(TAG, "onBindViewHolder, holder [" + holder + "], position [" + position + "]");
        holder.bind(mData.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void addRespositories(List<RepositoryData> list) {
        if(mData == null) {
            mData = new ArrayList<>(list);
        } else {
            mData.addAll(list);
        }
        notifyItemRangeInserted(mData.size() - list.size(), mData.size());
    }

    public void addRespository(RepositoryData repo) {
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

        void bind(final RepositoryData item, final OnItemClickListener listener) {
            tx.setText(item.getName());
            if(item.getDescription() == null || item.getDescription().isEmpty()) {
                description.setText(R.string.description_absent);
            } else {
                description.setText(item.getDescription());
            }
            if(listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });
            }
        }
    }
}
