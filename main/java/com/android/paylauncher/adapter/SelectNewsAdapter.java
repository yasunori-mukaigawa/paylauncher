package com.android.paylauncher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.paylauncher.R;
import com.android.paylauncher.fragment.NewsListFragment;


import com.android.paylauncher.util.Utilities;


public class SelectNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int mLayoutId;
    private SelectNewsAdapter.ViewHolder viewHolder;
    private NewsListFragment mNewsListFragment;
    private Context mContext;

    public SelectNewsAdapter(NewsListFragment newsListFragment, int layoutId, Context context){
        mLayoutId = layoutId;
        mNewsListFragment = newsListFragment;
        mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.select_news_item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        viewHolder = new SelectNewsAdapter.ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(Utilities.mAppTitle.size() > position) {
            viewHolder.textView.setText(Utilities.mAppTitle.get(position));
        }else{
            viewHolder.textView.setText(mContext.getString(R.string.default_news));
        }
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord;
                if(Utilities.mAppTitle.size() > position) {
                    searchWord = Utilities.mAppTitle.get(position);
                }else{
                    searchWord = mContext.getString(R.string.default_news);
                }
                mNewsListFragment.refreshSearchWord(searchWord);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Utilities.mAppTitle.size() + 1;
    }

}
