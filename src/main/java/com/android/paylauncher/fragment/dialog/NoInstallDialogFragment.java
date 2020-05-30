package com.android.paylauncher.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.android.paylauncher.R;
import com.android.paylauncher.adapter.LaunchAppsAdapter;
import com.android.paylauncher.util.Utilities;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NoInstallDialogFragment extends DialogFragment {
    private Context mContext;
    //押しているViewのポジション
    private int mPosition;
    //インストールされているかどうかの監視フラグ
    private boolean mFlag;

    public NoInstallDialogFragment(Context context, int position, boolean flag){
        mContext = context;
        mPosition = position;
        mFlag = flag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.no_install_dialog_fragment);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView text = dialog.findViewById(R.id.title);
        TextView message = dialog.findViewById(R.id.messages);

        text.setText(Utilities.mAppTitle.get(mPosition));
        if(!mFlag) {
            message.setText(R.string.no_install);
        }else{
            message.setText(R.string.still_install);
        }

        // OK ボタンのリスナ
        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        Utilities.mAppStoreURL.get(mPosition)));
                intent.setPackage(LaunchAppsAdapter.APPSTORE);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
                dismiss();
            }
        });

        // Close ボタンのリスナ
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog;
    }
}
