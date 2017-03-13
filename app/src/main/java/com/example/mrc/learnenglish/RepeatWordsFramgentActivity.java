package com.example.mrc.learnenglish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mrT on 26.01.2017.
 */

public class RepeatWordsFramgentActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_repeat_words_fragment,container,false);
            //super.onCreateView(inflater, container, savedInstanceState);

    }
}
