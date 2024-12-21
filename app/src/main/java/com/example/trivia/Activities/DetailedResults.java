package com.example.trivia.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.trivia.Objects.Question;
import com.example.trivia.R;
import com.example.trivia.Objects.Result;
import com.example.trivia.Adapters.ResultPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class DetailedResults extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_results);

        //getting resultList and questionList from ResultActivity
        @SuppressWarnings("unchecked")
        List<Result> resultList = (List<Result>) getIntent().getSerializableExtra("resultList");
        @SuppressWarnings("unchecked")
        List<Question> questionList = (List<Question>) getIntent().getSerializableExtra("questionList");

        //retrieves views
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        //attaching adapter
        ResultPagerAdapter adapter = new ResultPagerAdapter(this,questionList,resultList);
        viewPager2.setAdapter(adapter);

        // Attaching TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            // Set custom icon for each tab
            tab.setIcon(R.drawable.circle_tab);
        }).attach();
    }
}