package com.example.netflix.Activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.MainScreens.MainScreen;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentGateway extends AppCompatActivity implements PaymentResultListener{
    EditText firstname,lastname,contactnum;
    TextView textView,costofplan,nameofplan,change,textUrl;
    CheckBox agree;
    Button startmembership;
    String planname,plancost,plancostformat,Useremail,password,firstName,lastName,contactNum,UserId;
    String TAG="payment error";
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Date today,valid_date;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);
        getSupportActionBar().hide();
        firstname =findViewById(R.id.firstnameedittext);
        lastname = findViewById(R.id.lastnameeditext);
        contactnum = findViewById(R.id.contactnumedittext);
        textView = findViewById(R.id.step3of3payment);
        costofplan = findViewById(R.id.costtoset);
        nameofplan = findViewById(R.id.plantoset);
        change = findViewById(R.id.change);
        agree = findViewById(R.id.iagree);
        textUrl=findViewById(R.id.toputurl);
        startmembership = findViewById(R.id.startmemebershipbutton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Calendar c= Calendar.getInstance();
        today = c.getTime();
        c.add(Calendar.MONTH,1);
        valid_date = c.getTime();
        Intent i = getIntent();
        planname = i.getStringExtra("planname");
        plancost = i.getStringExtra("plancost");
        plancostformat = i.getStringExtra("plancostformat");
        Useremail = i.getStringExtra("email");
        password = i.getStringExtra("password");
        nameofplan.setText(planname);
        costofplan.setText(plancostformat);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentGateway.this,ChooseYourPlans.class);
                startActivity(i);
            }
        });
        SpannableString st = new SpannableString("STEP 3 OF 3");
        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldspna1 = new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspna1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);
        SpannableString ss = new SpannableString("By checking the checkbox below, you agree to our Terms of Use, Privacy Statement, and that you are over 18. Netflix will automatically continue your membership and charge the monthly membership fee to your payment method until you cancel. You may cancel at any time to avoid future charges.\n");
        ClickableSpan cs =new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/legal/termofuse")));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://help.netflix.com/en/legal/privacy")));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ss.setSpan(cs,49,61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cs1,63,81,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textUrl.setText(ss);
        textUrl.setMovementMethod(LinkMovementMethod.getInstance());
        startmembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Boolean X=true;
                if(firstname.getText().toString().length()<=3)
                {
                    if(firstname.getText().toString().length()==0)
                    {
                        firstname.setError("This field cannot be empty ");
                    }
                    else if(!firstname.getText().toString().matches("[a-z A-Z]+"))
                    {
                        firstname.setError("firstname must contain only character ,other than that nothing are allowed");

                    }
                    else {
                        firstname.setError("firstname must be greater than 3 characters");
                    }
                    X=false;
                }

                if(lastname.getText().toString().length()<=3)
                {
                    if(lastname.getText().toString().length()==0)
                    {
                        lastname.setError("This feild cannot be empty");
                    }
                    else if(!lastname.getText().toString().matches("[a-z A-Z]+"))
                    {
                        lastname.setError("lastname must contain only character ,other than that nothing are allowed");

                    }
                    else {
                        lastname.setError("lastname must be greater than 3 characters");
                    }
                    X=false;
                }

                if(contactnum.getText().toString().length()!=10)
                {
                    if(contactnum.getText().toString().length()==0){
                        contactnum.setError("This feild cannot be empty");
                    }
                    else {
                        contactnum.setError("Contact number must be exactly 10 digits");
                    }
                    X=false;
                }
                if(!agree.isChecked())
                {
                    Toast.makeText(PaymentGateway.this, "please agree to terms and polocies", Toast.LENGTH_SHORT).show();
                    X=false;
                }
                if(firstname.getText().toString().length()>3 && firstname.getText().toString().matches("[a-z A-Z]+") && lastname.getText().toString().matches("[a-z A-Z]+") && lastname.getText().toString().length()>3 && contactnum.getText().toString().length() ==10 && agree.isChecked() && X) {
                    progressDialog = new ProgressDialog(PaymentGateway.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    startPayment();
                }
            }
        });
    }
    public void startPayment() {
        firstName = firstname.getText().toString();
        lastName = lastname.getText().toString();
        contactNum = contactnum.getText().toString();
        String name = firstName+lastName;
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
            options.put("prefill.email",Useremail);
            options.put("prefill.contact",contactNum);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
            progressDialog.cancel();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {

        firebaseAuth.createUserWithEmailAndPassword(Useremail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    UserId = firebaseAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = firebaseFirestore.collection("Users").document(UserId);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Email",Useremail);
                    user.put("First_Name",firstName);
                    user.put("Last_Name",lastName);
                    user.put("Contact_Num",contactNum);
                    user.put("Plan_Cost",plancost);
                    user.put("Valid_date",valid_date);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent i = new Intent(PaymentGateway.this, MainScreen.class);
                            startActivity(i);
                            progressDialog.cancel();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e instanceof FirebaseNetworkException)
                            {
                                Toast.makeText(PaymentGateway.this, "No internet connection", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(PaymentGateway.this, "Values not stored", Toast.LENGTH_SHORT).show();

                            }
                            progressDialog.cancel();
                        }
                    });


                }
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        progressDialog.cancel();
        Toast.makeText(this, "payment unsuccessful", Toast.LENGTH_SHORT).show();
    }
}