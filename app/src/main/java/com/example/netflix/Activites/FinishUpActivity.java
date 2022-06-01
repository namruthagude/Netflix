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
import android.widget.Toast;

import com.example.netflix.R;

public class FinishUpActivity extends AppCompatActivity {

    String planname,plancost,plancostformat;
    TextView signin,textView;
    Button continuebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_up);
        getSupportActionBar().hide();
        Intent i1 = getIntent();
        planname=i1.getStringExtra("planname");
        plancost=i1.getStringExtra("plancost");
        plancostformat=i1.getStringExtra("planCostFormat");
        signin = findViewById(R.id.signin);
        textView=findViewById(R.id.step1of3finish);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinishUpActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });
        continuebutton = findViewById(R.id.continuebuttonFinish);
        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FinishUpActivity.this,StepTwo.class);
                i.putExtra("planname",planname);
                i.putExtra("plancost",plancost);
                i.putExtra("plancostformat",plancostformat);
                startActivity(i);
            }
        });
        SpannableString st = new SpannableString("STEP 1 OF 3");
        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1 = new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);
    }
}