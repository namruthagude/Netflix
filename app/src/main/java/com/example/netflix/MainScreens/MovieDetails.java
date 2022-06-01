package com.example.netflix.MainScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.netflix.R;

public class MovieDetails extends AppCompatActivity {
    ImageView movieImage;
    TextView movieName;
    Button play;
    String image,name,fileurl,movieid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().hide();
        movieImage = findViewById(R.id.imageDetails);
        movieName = findViewById(R.id.movieName);
        play = findViewById(R.id.playbutton);
        Intent i =getIntent();
        image = i.getStringExtra("movieimageurl");
        name = i.getStringExtra("moviename");
        movieid = i.getStringExtra("movieid");
        fileurl = i.getStringExtra("moviefile");
        Glide.with(this).load(image).into(movieImage);
        movieName.setText(name);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetails.this,VideoPlayer.class);
                i.putExtra("fileurl",fileurl);
                startActivity(i);
            }
        });


    }
}