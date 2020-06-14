package com.android.paylauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.paylauncher.MainMenuActivity;
import com.android.paylauncher.R;
import com.android.paylauncher.fragment.dialog.NoInstallDialogFragment;
import com.android.paylauncher.info.ItemInfo;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.android.paylauncher.util.Utilities.mAppTitle;

public class LaunchAppsAdapter extends BaseAdapter {

    class ViewHolder {
        ImageButton imageButton;
        TextView textView;
    }

    private Context mContext;
    private ArrayList<Drawable> mIconDrawable;
    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private ItemInfo itemInfo;
    public static String APPSTORE = "com.android.vending";

    public LaunchAppsAdapter(Context context, int layoutId){
        super();
        mContext = context;
        getItemInfo(mContext);
        viewHolder = new ViewHolder();
        mInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutId = layoutId;
    }

    private void getItemInfo(Context context){
        itemInfo = new ItemInfo(context);
        mIconDrawable = itemInfo.iconDrawable;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convertViewがnullならImageViewを新規に作成する
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, parent, false);
            viewHolder.imageButton = convertView.findViewById(R.id.image_button);
            viewHolder.textView = convertView.findViewById(R.id.text_view);
            convertView.setTag(viewHolder);
        } else { // convertViewがnullでない場合は再利用
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mAppTitle.get(position));
        viewHolder.textView.setTypeface(Typeface.DEFAULT_BOLD);
        viewHolder.imageButton.setImageDrawable(mIconDrawable.get(position));
        // ImageViewに画像ファイルを設定
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemInfo.mDispPackage.get(position).equals("")) {
                    Intent intent = new Intent();
                    intent.setClassName(itemInfo.mDispPackage.get(position), itemInfo.mDispClass.get(position));
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                }else{
                    NoInstallDialogFragment dialogFragment = new NoInstallDialogFragment(mContext, position, false);
                    dialogFragment.show(MainMenuActivity.getMainManuActivity(mContext).getSupportFragmentManager(),
                            NoInstallDialogFragment.class.getName());
                }
            }
        });

        //ロングクリック時にダイアログを出す
        viewHolder.imageButton.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                boolean flag = true;
                //アプリがインストールされてなかったら、インストール推奨ダイアログを出す
                if(itemInfo.mDispPackage.get(position).equals("")){
                    flag = false;
                }
                NoInstallDialogFragment dialogFragment = new NoInstallDialogFragment(mContext, position, flag);
                dialogFragment.show(MainMenuActivity.getMainManuActivity(mContext).getSupportFragmentManager(),
                        NoInstallDialogFragment.class.getName());
                return false;
            }
        });

        // ImageViewを返す
        return convertView;
    }

    @Override
    public int getCount() {
        // mThumbIdsの大きさを返す
        return mIconDrawable.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refreshIconView(){
        itemInfo.clear();
        getItemInfo(mContext);
        notifyDataSetChanged();
    }
}
