package com.example.netflix.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.Activites.FinishUpActivity;
import com.example.netflix.Activites.SignInActivity;
import com.example.netflix.R;

public class ChooseYourPlans extends AppCompatActivity {
    TextView Signin;
    Button  continuebutton;
    RadioButton radiobasic,radiostandard,radiopremium;
    String planname, plancost,planformatofcost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_plans);
        getSupportActionBar().hide();
        Signin=findViewById(R.id.signin);
        continuebutton=findViewById(R.id.continuebutton);
        radiobasic=findViewById(R.id.radiobuttonforbasic);
        radiostandard=findViewById(R.id.radiobuttonforstandard);
        radiopremium=findViewById(R.id.radiobuttonforpremium);
        radiobasic.setOnCheckedChangeListener(new Radio_check());
        radiostandard.setOnCheckedChangeListener(new Radio_check());
        radiopremium.setOnCheckedChangeListener(new Radio_check());
        radiopremium.setChecked(true);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ChooseYourPlans.this, SignInActivity.class);
                startActivity(i);
            }
        });
        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(ChooseYourPlans.this,FinishUpActivity.class);
                i1.putExtra("planname",planname);
                i1.putExtra("plancost",plancost);
                i1.putExtra("planCostFormat",planformatofcost);
                startActivity(i1);
            }
        });
    }
    class Radio_check implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked){
                if(compoundButton.getId() == R.id.radiobuttonforbasic){
                    planname="Basic";
                    plancost="349";
                    planformatofcost="₹ 349/month";
                    radiopremium.setChecked(false);
                    radiostandard.setChecked(false);

                }
                if(compoundButton.getId() == R.id.radiobuttonforstandard){
                    planname="Standard";
                    plancost="649";
                    planformatofcost="₹ 649/month";
                    radiopremium.setChecked(false);
                    radiobasic.setChecked(false);

                }
                if(compoundButton.getId() == R.id.radiobuttonforpremium){
                    planname="Premium";
                    plancost="799";
                    planformatofcost="₹ 799/month";
                    radiobasic.setChecked(false);
                    radiostandard.setChecked(false);

                }


            }
        }
    }
}