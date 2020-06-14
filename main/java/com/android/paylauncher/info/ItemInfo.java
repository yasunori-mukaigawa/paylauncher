package com.android.paylauncher.info;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.android.paylauncher.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.android.paylauncher.util.Utilities.mDisplayPackageList;

public class

ItemInfo extends ArrayList {
    public ArrayList<Drawable> iconDrawable = new ArrayList<Drawable>();
    private ArrayList<String> iconLavel = new ArrayList<String>();
    public ArrayList<String> mDispPackage = new ArrayList<String>(Arrays.asList("","","","","","","",""));
    public ArrayList<String> mDispClass = new ArrayList<String>(Arrays.asList("","","","","","","",""));

    private Context mContext;

    public ItemInfo(Context context){
        mContext = context;
        getPackageAndClass();
        getIconDrawable();
        getIconLabel();
    }

    private void getPackageAndClass(){
        PackageManager pm = mContext.getPackageManager();
        //インストール済パッケージ情報を取得する
        List<ApplicationInfo> appInfoList = pm.getInstalledApplications(BIND_AUTO_CREATE);
        for(ApplicationInfo ai : appInfoList){
            int i = 0;
            //表示するアプリのみ情報を保持する
            for (String str : mDisplayPackageList) {
                if(ai.packageName.equals(str)){
                    mDispPackage.set(i,ai.packageName);
                    Intent intent = pm.getLaunchIntentForPackage(ai.packageName);
                    if(intent != null && intent.getComponent() != null) {
                        mDispClass.set(i, intent.getComponent().getClassName());
                    }
                }
                i++;
            }
        }
    }

    //アイコンの情報を取得する
    private void getIconDrawable() {
        Drawable icon = null;
        PackageManager pm = mContext.getPackageManager();
        int i = 0;
        for (String str : mDispPackage) {
            if (mDispPackage.get(i).equals(str)) {
                try {
                    icon = pm.getApplicationIcon(str);
                } catch (PackageManager.NameNotFoundException e) {
                    icon = mContext.getResources().getDrawable(R.mipmap.icon_launch_round, null);
                }
            }
            iconDrawable.add(icon);
            i++;
        }
    }

    //タイトルの情報を取得する
    private void getIconLabel(){
        String lavel = null;
        //PackageManagerのオブジェクトを取得
        PackageManager pm = mContext.getPackageManager();

        int i = 0;
        for (String str : mDispPackage) {
            if (mDispPackage.get(i).equals(str)) {
                try {
                    ApplicationInfo info = pm.getApplicationInfo(str,0);
                    lavel = info.loadLabel(pm).toString();
                }catch (PackageManager.NameNotFoundException e){
                    lavel = mContext.getResources().getString(R.string.not_install);
                }
            }
            iconLavel.add(lavel);
            i++;
        }
    }
}
