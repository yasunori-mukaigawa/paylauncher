package com.android.paylauncher.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.paylauncher.MainMenuActivity;
import com.android.paylauncher.R;
import com.android.paylauncher.adapter.NewsListAdapter;
import com.android.paylauncher.fragment.dialog.SelectNewsDialogFragment;
import com.android.paylauncher.info.NewsDictionary;
import com.android.paylauncher.thread.HttpAsyncLoader;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<NewsDictionary>,SwipeRefreshLayout.OnRefreshListener {


    public NewsListFragment() {
        // Required empty public constructor
    }

    private RecyclerView mNewsList;
    private ArrayList<String> mNewsTitle;
    private ArrayList<String> mUrl;
    private ArrayList<Bitmap> mImageList;
    private String mSearchWord;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mView;
    private SelectNewsDialogFragment mDialog;
    private FrameLayout mSearchBar;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final NewsListFragment newsListFragment = this;
        mView = inflater.inflate(R.layout.news_list_fragment, container, false);
        setUpView();
        mSwipeRefreshLayout = mView.findViewById(R.id.swipelayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //更新アイコンをつける
        mSwipeRefreshLayout.setRefreshing(true);
        mSearchBar = mView.findViewById(R.id.search_bar);
        TextView textView = mView.findViewById(R.id.select_news);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new SelectNewsDialogFragment(newsListFragment);
                mDialog.show(MainMenuActivity.getMainManuActivity(getContext()).getSupportFragmentManager(),
                        SelectNewsDialogFragment.class.getName());
            }
        });
        return mView;
    }

    @NonNull
    @Override
    public Loader<NewsDictionary> onCreateLoader(int id, @Nullable Bundle args) {
        //Newsの情報を非同期で取得する
        HttpAsyncLoader httpAsyncLoader = new HttpAsyncLoader(getContext(), mSearchWord);
        return httpAsyncLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<NewsDictionary> loader, NewsDictionary data) {
        setNews(data);
        mNewsList.setAdapter(new NewsListAdapter(this, R.layout.news_list_item, mNewsTitle, mImageList, mUrl));
        //更新アイコンを消す
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<NewsDictionary> loader) {
        //do nothing
    }

    private void setUpView() {
        if (getContext() != null && getActivity() != null) {
            LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
            mNewsList = mView.findViewById(R.id.news_list);
            mNewsList.setHasFixedSize(true);
            mNewsList.setLayoutManager(linearLayout);
            //Dividerをニュースごとに設定する
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mNewsList.getContext(),
                    new LinearLayoutManager(getActivity()).getOrientation());
            mNewsList.addItemDecoration(dividerItemDecoration);
            dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getActivity(), R.drawable.divider)));
            mNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy <= 0) {
                        mSearchBar.setVisibility(View.VISIBLE);
                    } else {
                        mSearchBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void setNews(NewsDictionary data) {
        NewsDictionary diction = data;
        if(diction != null) {
            mNewsTitle = data.newsTitle;
            mUrl = diction.url;
            mImageList = diction.imageList;
        } else {
            mNewsTitle = null;
            mUrl = null;
            mImageList = null;
        }
    }

    public void refreshSearchWord(String string){
        mSearchWord = string;
        LoaderManager.getInstance(this).restartLoader(0, null, this);
        if(mDialog != null) {
            mDialog.dismiss();
        }
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        //Newsを更新する
        LoaderManager.getInstance(this).restartLoader(0, null, this);
    }
}
