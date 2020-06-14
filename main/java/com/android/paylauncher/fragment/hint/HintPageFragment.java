package com.android.paylauncher.fragment.hint;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.paylauncher.R;

public class HintPageFragment extends Fragment {

    private final int mPosision;
    private ImageView mHintImage;
    private TextView mHintText;

    public HintPageFragment(int posision) {
        mPosision = posision;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.base_hint_fragment, container, false);
        setUpView(v);

        switch (mPosision) {
            case 0:
                setPage1();
                break;
            case 1:
                setPage2();
                break;
            case 2:
                setPage3();
                break;
        }
        return v;
    }

    private void setUpView(View v){
        mHintImage = v.findViewById(R.id.base_hint_img);
        mHintText = v.findViewById(R.id.base_hint_text);
    }

    private void setPage1(){
        mHintImage.setImageDrawable(getResources().getDrawable(R.drawable.hint_page1, null));
        mHintText.setText(getText(R.string.hint_page_text1));
    }

    private void setPage2(){
        mHintImage.setImageDrawable(getResources().getDrawable(R.drawable.hint_page2, null));
        mHintText.setText(getText(R.string.hint_page_text2));
    }

    private void setPage3(){
        mHintImage.setImageDrawable(getResources().getDrawable(R.drawable.hint_page3, null));
        mHintText.setText(getText(R.string.hint_page_text3));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear(){
        mHintImage = null;
        mHintText = null;
    }
}
