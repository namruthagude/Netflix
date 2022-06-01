package com.example.netflix.MainScreens;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayer extends AppCompatActivity {
    private PlayerView styledPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        styledPlayerView = findViewById(R.id.exoplayer);
        progressBar = findViewById(R.id.progess);
        progressBar.setVisibility(View.VISIBLE);
        setUpExoPlayer(getIntent().getStringExtra("fileurl"));

    }

    private void setUpExoPlayer(String fileurl) {
        simpleExoPlayer= new SimpleExoPlayer.Builder(this).build();
        styledPlayerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this,"Netflix"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(fileurl)));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}