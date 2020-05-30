package com.android.paylauncher.util;

import com.android.paylauncher.R;

import java.util.Arrays;
import java.util.ArrayList;

public class Utilities {
    //表示アプリ一覧
    public static final ArrayList<String> mDisplayPackageList = new ArrayList<String>(Arrays.asList(
            "jp.co.yahoo.android.paypayfleamarket",
            "com.kouzoh.mercari",
            "jp.quodigital.android.quocardpay",
            "jp.co.family.familymart_app",
            "com.nttdocomo.keitai.payment",
            "com.google.android.apps.walletnfcrel",
            "jp.co.rakuten.pointpartner.app",
            "com.linepaycorp.pos"
    ));

    //起動Acyivity名
    public static final ArrayList<String> mDisplayClassList = new ArrayList<String>(Arrays.asList(
            "jp.co.yahoo.android.sparkle.presentation.HomeActivity",
            "com.kouzoh.mercari.activity.SplashActivity",
            "jp.quodigital.android.quocardpay.presenter.launch.LaunchActivity",
            "jp.co.family.familymart.presentation.splash.SplashActivity",
            "com.nttdocomo.keitai.payment.activity.DPYSplashActivity",
            "com.google.commerce.tapandpay.android.cardlist.CardListActivity",
            "jp.co.rakuten.pointpartner.app.ui.launch.SplashScreenActivity",
            "com.linepaycorp.pos.splash.SplashActivity"
    ));

    //インストールされてない場合のPlayStoreのURL
    public static final ArrayList<String> mAppStoreURL = new ArrayList<String>(Arrays.asList(
            "https://play.google.com/store/apps/details?id=jp.co.yahoo.android.paypayfleamarket",
            "https://play.google.com/store/apps/details?id=com.kouzoh.mercari",
            "https://play.google.com/store/apps/details?id=jp.quodigital.android.quocardpay",
            "https://play.google.com/store/apps/details?id=jp.co.family.familymart_app",
            "https://play.google.com/store/apps/details?id=com.nttdocomo.keitai.payment",
            "https://play.google.com/store/apps/details?id=com.google.android.apps.walletnfcrel",
            "https://play.google.com/store/apps/details?id=jp.co.rakuten.android",
            "https://play.google.com/store/apps/details?id=com.linepaycorp.pos"
    ));

    //インストールするアプリ名一覧
    public static final ArrayList<String> mAppTitle = new ArrayList<String>(Arrays.asList(
            "PayPay","メルカリ","QuoカードPay",
            "ファミペイ","d払い","Google Pay","楽天ポイントカード","LINE POS"
    ));
}
