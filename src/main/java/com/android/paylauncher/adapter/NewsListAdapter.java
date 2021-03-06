package com.android.paylauncher.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.paylauncher.R;
import com.android.paylauncher.fragment.NewsListFragment;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.news_title);
        }
    }

    private int mLayoutId;
    private ViewHolder viewHolder;
    private ArrayList<String> mTitleArray;
    private ArrayList<Bitmap> mImageList;
    private ArrayList<String> mUrl;
    private NewsListFragment mNewsListFragment;

    public NewsListAdapter(NewsListFragment newsListFragment, int layoutId, ArrayList<String> titleArray,
                           ArrayList<Bitmap> imageList, ArrayList<String> url){
        super();
        mLayoutId = layoutId;
        mTitleArray = titleArray;
        mImageList = imageList;
        mUrl = url;
        mNewsListFragment = newsListFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(mImageList != null && mTitleArray != null && mUrl != null){
            viewHolder.imageView.setImageBitmap(mImageList.get(position));
            viewHolder.textView.setText(mTitleArray.get(position));
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Newsが押されたら、chromeで対象ページに飛ぶ
                    Uri uri = Uri.parse(mUrl.get(position));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    mNewsListFragment.startActivity(intent);
                }
            });
        } else {
            viewHolder.imageView.setImageDrawable(mNewsListFragment.getResources().getDrawable(R.drawable.icon_launch, null));
            viewHolder.textView.setText(mNewsListFragment.getText(R.string.fail_load_news));
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewsListFragment.refreshSearchWord(null);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mTitleArray != null) {
            return mTitleArray.size();
        }else{
            return 1;
        }
    }
}
