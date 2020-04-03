package com.soulsurfer.android.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private final List<String> pageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        pageUrls.add("https://medium.com/androiddevelopers/inline-functions-under-the-hood-12ddcc0b3a56");
        pageUrls.add("https://www.youtube.com/watch?v=R1CXG4bdWv4");
        pageUrls.add("https://android-developers.googleblog.com/2020/03/meet-finalists-of-google-play-indie.html");
        pageUrls.add("https://www.bbc.com/news/technology-48334739");
        pageUrls.add("https://qz.com/1819651/local-farms-in-hong-kong-are-thriving-because-of-coronavirus/?utm_source=YPL&yptr=yahoo");
        pageUrls.add("https://developers.facebook.com/blog/post/2020/03/25/winners-our-first-facebook-0nline-hackathon-announced/");
        pageUrls.add("https://www.digitaltrends.com/cars/what-is-android-auto/");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        PagesRecyclerViewAdapter adapter = new PagesRecyclerViewAdapter(pageUrls);
        recyclerView.setAdapter(adapter);
    }
}
