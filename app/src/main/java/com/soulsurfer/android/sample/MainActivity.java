package com.soulsurfer.android.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.soulsurfer.android.PageInfo;
import com.soulsurfer.android.PageInfoListener;
import com.soulsurfer.android.SoulSurfer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoulSurfer.get("https://medium.com/nightingale/data-visualization-as-an-act-of-witnessing-33e346f5e437").load(new PageInfoListener() {
            @Override
            public void onPageInfoLoaded(PageInfo pageInfo) {
                Log.i("SoulSurferSample", pageInfo.toString());
            }

            @Override
            public void onError(String url) {
                Log.e("SoulSurferSample", "Error loading - "+ url);
            }
        });
    }
}
