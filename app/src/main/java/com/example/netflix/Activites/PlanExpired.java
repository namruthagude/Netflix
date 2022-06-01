package com.example.netflix.Activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.Activites.FinishUpActivity;
import com.example.netflix.Activites.SignInActivity;
import com.example.netflix.MainScreens.MainScreen;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class PlanExpired extends AppCompatActivity {
    TextView Signin;
    Button  paybutton;
    RadioButton radiobasic,radiostandard,radiopremium;
    String planname, plancost,planformatofcost,IntFirstname,IntLastname,IntEmail,IntContact,IntUid;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String TAG="Payment error";
    ProgressDialog progressDialog;
    Date today,valid_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_expired);
        getSupportActionBar().hide();
        Intent i = getIntent();
        IntFirstname = i.getStringExtra("firstname");
        IntLastname = i.getStringExtra("lastname");
        IntEmail = i.getStringExtra("email");
        IntContact = i.getStringExtra("contact");
        IntUid = i.getStringExtra("uid");
        Signin = findViewById(R.id.signin);
        paybutton = findViewById(R.id.paybutton);
        radiobasic = findViewById(R.id.radiobuttonforbasic);
        radiostandard = findViewById(R.id.radiobuttonforstandard);
        radiopremium = findViewById(R.id.radiobuttonforpremium);
        radiobasic.setOnCheckedChangeListener(new PlanExpired.Radio_check());
        radiostandard.setOnCheckedChangeListener(new PlanExpired.Radio_check());
        radiopremium.setOnCheckedChangeListener(new PlanExpired.Radio_check());
        radiopremium.setChecked(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Calendar c = Calendar.getInstance();
        today = c.getTime();
        c.add(Calendar.MONTH,1);
        valid_date = c.getTime();
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlanExpired.this, SignInActivity.class);
                startActivity(i);
            }
        });
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(PlanExpired.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                startPayment();
            }
        });
    }

    class Radio_check implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked){
                if(compoundButton.getId() == R.id.radiobuttonforbasic) {
                    planname = "Basic";
                    plancost = "349";
                    planformatofcost = "₹ 349/month";
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
    public void startPayment() {
        String name = IntFirstname+IntLastname;
        int total= Integer.parseInt(plancost);
        total = total *100;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_pWpZosvih2JuQT");

        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name",name);
            options.put("description", "APP PAYMENT");
            options.put("currency", "INR");
            options.put("amount",total);//pass amount in currency subunits
            options.put("prefill.email",IntEmail);
            options.put("prefill.contact",IntContact);
            checkout.open(activity, options);

        } catch(Exception e) {
            progressDialog.cancel();
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    public void onPaymentSuccess(String s) {

                    try {


                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(IntUid);
                        Map<String, Object> user = new HashMap<>();
                        user.put("Email", IntEmail);
                        user.put("First_Name", IntFirstname);
                        user.put("Last_Name", IntLastname);
                        user.put("Contact_Num", IntContact);
                        user.put("Plan_Cost", plancost);
                        user.put("Valid_date", valid_date);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent i = new Intent(PlanExpired.this, MainScreen.class);
                                progressDialog.cancel();
                                startActivity(i);

                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(PlanExpired.this, "Values not stored", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }catch (Exception e)

                    {
                        progressDialog.cancel();
                        Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                    }
    }

    public void onPaymentError(int i, String s) {
        progressDialog.cancel();
        Toast.makeText(this, "payment unsuccessful", Toast.LENGTH_SHORT).show();
    }
}




