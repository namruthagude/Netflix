package com.example.netflix.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.netflix.Adapters.ViewPageAdapter;
import com.example.netflix.R;

public class SwipeScreen extends AppCompatActivity {
    TextView signin,privacy,help;
    Button getstarted;
    ViewPager viewPager;
    LinearLayout sliderdots;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_screen);
        getSupportActionBar().hide();
        signin = findViewById(R.id.signin);
        privacy = findViewById(R.id.privacy);
        help = findViewById(R.id.help);
        getstarted = findViewById(R.id.getstarted);
        sliderdots = findViewById(R.id.slidedots);
        viewPager = findViewById(R.id.viewpageswipescreen);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);
        dotscount = viewPageAdapter.getCount();
        dots = new ImageView[dotscount];
        for(int i=0;i<dotscount;i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactivedots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            sliderdots.addView(dots[i],params);
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.activedots));
        }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for(int i=0;i<dotscount;i++){

                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactivedots));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.activedots));


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1= new Intent(SwipeScreen.this,SignInActivity.class);
                    startActivity(i1);
                }
            });
            getstarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1 = new Intent(SwipeScreen.this,StepOne.class);
                    startActivity(i1);
                }
            });
            privacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://help.netflix.com/en/node/100628"));
                    startActivity(i1);
                }
            });
            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i1 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://help.netflix.com/en/"));
                    startActivity(i1);
                }
            });
        }


    }


