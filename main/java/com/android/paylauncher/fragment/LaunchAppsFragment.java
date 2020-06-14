package com.android.paylauncher.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.paylauncher.MainMenuActivity;
import com.android.paylauncher.R;
import com.android.paylauncher.adapter.LaunchAppsAdapter;
import com.android.paylauncher.fragment.dialog.MenuDialogFragment;
import com.android.paylauncher.fragment.dialog.NoInstallDialogFragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class LaunchAppsFragment extends Fragment {

    private PackageReceiver packageReceiver;
    private LaunchAppsAdapter mLaunchAppsAdapter;
    private SharedPreferences mPref;
    private MainMenuActivity mMainMenuActivity;
    private GridView mLaunchAppGridAdapter;
    private ImageButton mMenuButton;
    private final String PREFNAME = "pref_name";
    private final String PREFBOOL = "pref_bool";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainMenuActivity = MainMenuActivity.getMainManuActivity(getContext());
        mPref = getContext().getSharedPreferences(PREFNAME,Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.launch_apps_fragment, container, false);

        setUpView(v);

        setAdView(v);

        registPackageReceiver();

        return v;
    }

    private void setUpView(View v){
        //GridViewをセットする
        mLaunchAppGridAdapter = v.findViewById(R.id.launch_apps_grid);
        if (getContext() != null) {
            mLaunchAppsAdapter = new LaunchAppsAdapter(getContext(), R.layout.grid_item);
            mLaunchAppGridAdapter.setAdapter(mLaunchAppsAdapter);
        }

        //メニューボタンの設置
        mMenuButton = v.findViewById(R.id.menu);
    }

    private void setAdView(View v){
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void registPackageReceiver(){
        //レシーバーを実装・登録
        IntentFilter filter
                = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        packageReceiver = new PackageReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                //インストール・アンインストールされたら、GridViewを更新する
                if (action != null &&
                        (action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REMOVED))) {
                    mLaunchAppsAdapter.refreshIconView();
                    mLaunchAppGridAdapter.setAdapter(mLaunchAppsAdapter);
                }
            }
        };
        getContext().registerReceiver(packageReceiver, filter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        setOnClickListener();
    }

    private void setOnClickListener(){
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = mMainMenuActivity.findViewById(R.id.main_drawer);
                drawer.openDrawer(GravityCompat.START);
                //初回のみチュートリアル的にダイアログを出す
                if(!mPref.getBoolean(PREFBOOL,false)) {
                    MenuDialogFragment dialogFragment = new MenuDialogFragment();
                    dialogFragment.show(mMainMenuActivity.getSupportFragmentManager(),
                            NoInstallDialogFragment.class.getName());
                    mPref.edit().putBoolean(PREFBOOL,true).apply();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear(){
        //使わなくなったら登録解除
        getContext().unregisterReceiver(packageReceiver);
        packageReceiver = null;
        mLaunchAppsAdapter = null;
        mPref = null;
        mMainMenuActivity = null;
        mLaunchAppGridAdapter = null;
        mMenuButton = null;
    }
}

class PackageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //do nothing
    }
}
