package com.android.paylauncher.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
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

public class LaunchAppsFragment extends Fragment {

    private PackageReceiver packageReceiver;
    private LaunchAppsAdapter mLaunchAppsAdapter;
    private MainMenuActivity mMainMenuActivity;
    private SharedPreferences mPref;
    private String PREFNAME = "pref_name";
    private String PREFBOOL = "pref_bool";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainMenuActivity = MainMenuActivity.getMainManuActivity(getContext());
        mPref = getContext().getSharedPreferences(PREFNAME,Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.launch_apps_fragment, container, false);

        //GridViewをセットする
        final GridView launchAppGridAdapter = v.findViewById(R.id.launch_apps_grid);
        if (getContext() != null) {
            mLaunchAppsAdapter = new LaunchAppsAdapter(getContext(), R.layout.grid_item);
            launchAppGridAdapter.setAdapter(mLaunchAppsAdapter);
        }

        //メニューボタンの設置
        ImageButton menuButton = v.findViewById(R.id.menu);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = mMainMenuActivity.findViewById(R.id.main_drawer);
                drawer.openDrawer(Gravity.START);
                //初回のみチュートリアル的にダイアログを出す
                if(!mPref.getBoolean(PREFBOOL,false)) {
                    MenuDialogFragment dialogFragment = new MenuDialogFragment();
                    dialogFragment.show(mMainMenuActivity.getSupportFragmentManager(),
                            NoInstallDialogFragment.class.getName());
                    mPref.edit().putBoolean(PREFBOOL,true).apply();
                }
            }
        });

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
                if (action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
                    mLaunchAppsAdapter.refreshIconView();
                    launchAppGridAdapter.setAdapter(mLaunchAppsAdapter);
                }
            }
        };
        getContext().registerReceiver(packageReceiver, filter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //使わなくなったら登録解除
        getContext().unregisterReceiver(packageReceiver);
    }
}

class PackageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //do nothing
    }
}
