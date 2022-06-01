package com.example.netflix.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.netflix.R;

public class StepOne extends AppCompatActivity {
    TextView signin,textView;
    Button seeyourplansbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);
        getSupportActionBar().hide();
        signin = findViewById(R.id.signin);
        textView = findViewById(R.id.step1of3);
        seeyourplansbutton=findViewById(R.id.seeyourplans);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StepOne.this,SignInActivity.class);
                startActivity(i);
            }
        });
        seeyourplansbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StepOne.this,ChooseYourPlans.class);
                startActivity(i);
            }
        });
        SpannableString st = new SpannableString("STEP 1 OF 3");
        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1 = new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);


    }
}