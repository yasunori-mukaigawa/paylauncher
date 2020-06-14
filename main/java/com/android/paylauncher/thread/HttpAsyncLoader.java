package com.android.paylauncher.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.android.paylauncher.R;
import com.android.paylauncher.info.NewsDictionary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class HttpAsyncLoader extends AsyncTaskLoader<NewsDictionary> {
    // WebAPIのURL
    private final String mReqUrl;
    private final Context mContext;
    private int NEWSMAXNUM = 10;
    private final String TAG = "HttpAsyncLoader";
    private final String ARTICLE = "articles";
    private final String TITLE = "title";
    private final String URL_TO_IMAGE = "urlToImage";
    private final String URI = "url";

    public HttpAsyncLoader(@NonNull Context context, String string /* 検索word */) {
        super(context);
        mContext = context;
        Calendar cal = Calendar.getInstance();
        //1か月前から取得するので、MONTHは+1しない
        String day = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        String searchWord;
        if(string != null) {
            searchWord = string;
        } else {
            searchWord = mContext.getString(R.string.default_news);
        }
        mReqUrl = "http://newsapi.org/v2/everything?q=" + searchWord + "&from=" + day + "&sortBy=publishedAt&apiKey=9f1a1981a78941b6848b054c6d0cd651";
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public NewsDictionary loadInBackground() {
        StringBuilder stringBuilder = new StringBuilder();
        //URLからJSON形式でデータを受け取る
        try {
            URL url = new URL(mReqUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader
                    = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

            bufferedReader.close();
            urlConnection.disconnect();

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return parseObject(stringBuilder.toString());
    }

    private NewsDictionary parseObject(String data){
        NewsDictionary newsDictionary = new NewsDictionary();
        if(parseJson(data, newsDictionary) != null) {
            parseUriImage(newsDictionary);
        } else {
            newsDictionary = null;
            Log.i(TAG,"Could not get News.");
        }
        return newsDictionary;
    }

    private NewsDictionary parseJson(String data, NewsDictionary newsDictionary){
        //JSONファイルからデータをparseする
        try {
            JSONObject newsJSON = new JSONObject(data);
            JSONArray titleArray = newsJSON.getJSONArray(ARTICLE);

            //最大取得数は10だが、取得する数が10より小さい場合、
            //その数の分だけデータを保持する
            if(titleArray.length() < NEWSMAXNUM){
                NEWSMAXNUM = titleArray.length();
            }

            for (int i = 0; i < NEWSMAXNUM ; i++) {
                JSONObject jsonObject = titleArray.getJSONObject(i);
                // 名前・画像URL・URLを取得
                String title = jsonObject.getString(TITLE);
                String urlToImage = jsonObject.getString(URL_TO_IMAGE);
                String url = jsonObject.getString(URI);
                if(!title.startsWith("2020")) {
                    newsDictionary.newsTitle.add(title);
                    newsDictionary.urlToImage.add(urlToImage);
                    newsDictionary.url.add(url);
                }else{
                    NEWSMAXNUM++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return newsDictionary;
    }

    private void parseUriImage(NewsDictionary newsDictionary){
        //parseしたデータから画像ファイルを取得する
        URL imageUrl;
        Bitmap bitmap;
        for(int i = 0; i < newsDictionary.urlToImage.size() ; i++) {
            try {
                imageUrl = new URL(newsDictionary.urlToImage.get(i));

                // inputStreamで画像を読み込む
                InputStream inputstream = imageUrl.openStream();

                // inputSteramをbitmapに変換
                bitmap = BitmapFactory.decodeStream(inputstream);
                newsDictionary.imageList.add(bitmap);
            }
            //例外時はデフォルト画像をaddする
              catch (MalformedURLException e) {
                setDefaultDrawable(newsDictionary);
            } catch (IOException e) {
                setDefaultDrawable(newsDictionary);
            }
        }
    }

    private void setDefaultDrawable(NewsDictionary newsDictionary){
        Log.i(TAG,"Could not get News Image. So set default image.");
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_launch, null);
        newsDictionary.imageList.add(((BitmapDrawable) drawable).getBitmap());
    }
}
