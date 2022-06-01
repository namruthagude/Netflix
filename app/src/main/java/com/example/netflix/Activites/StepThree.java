package com.example.netflix.Activites;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.R;



public class StepThree extends AppCompatActivity {

    String planname,plancost,plancostformat,useremail,password;
    TextView signout,textView;
    LinearLayout paymentlinearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_three);
        getSupportActionBar().hide();
        Intent i = getIntent();
        planname = i.getStringExtra("planname");
        plancost = i.getStringExtra("plancost");
        plancostformat = i.getStringExtra("plancostformat");
        useremail = i.getStringExtra("email");
        password = i.getStringExtra("password");
        signout = findViewById(R.id.signout);
        textView = findViewById(R.id.step3of3);
        paymentlinearlayout = findViewById(R.id.paymentlinearlayout);
        signout.setOnClickListener(view -> {
            Intent i1 = new Intent(StepThree.this,SignInActivity.class);
            startActivity(i1);
        });
        paymentlinearlayout.setOnClickListener(view -> {
            Intent i12 = new Intent(StepThree.this, PaymentGateway.class);
            i12.putExtra("planname", planname);
            i12.putExtra("plancost", plancost);
            i12.putExtra("plancostformat", plancostformat);
            i12.putExtra("email", useremail);
            i12.putExtra("password", password);
            startActivity(i12);
        });
        SpannableString st = new SpannableString("STEP 3 OF 3");
        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1 = new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);
    }
}