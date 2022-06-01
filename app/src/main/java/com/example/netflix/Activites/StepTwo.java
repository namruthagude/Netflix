package com.example.netflix.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class StepTwo extends AppCompatActivity {
    String planame,plancost,plancostformat;
    TextView signin,textView;
    Button continuebutton;
    EditText useremailId,userpassword;
    FirebaseAuth firebaseAuth;
    Boolean X;
    String email,password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_two);
        getSupportActionBar().hide();
        Intent i=getIntent();
        planame=i.getStringExtra("planname");
        plancost=i.getStringExtra("plancost");
        plancostformat=i.getStringExtra("plancostformat");
        signin = findViewById(R.id.signin);
        continuebutton=findViewById(R.id.continuebuttonStepTwo);
        useremailId = findViewById(R.id.emaileditTextSteptwo);
        textView = findViewById(R.id.step2of3);
        userpassword = findViewById(R.id.passwordStepTwo);
        firebaseAuth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StepTwo.this,SignInActivity.class);
                startActivity(i);
            }
        });
        continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = useremailId.getText().toString();
                password = userpassword.getText().toString();
                X = true;
                progressDialog = new ProgressDialog(StepTwo.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (email.length() < 8 || !email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                    if(email.length()==0)
                    {
                        useremailId.setError("This Feild cannot be empty");
                    }
                    else if(email.length()<8)
                    {
                        useremailId.setError("email must be greater than 8 characters");
                    }
                    else
                    {
                        useremailId.setError("Invalid email");
                    }
                    X=false;
                    progressDialog.cancel();

                }

                if (password.length() < 7) {

                    if(password.length()==0)
                    {
                        userpassword.setError("This feild cannot be empty");
                    }
                    else {
                        userpassword.setError("Password length must be greater than 8");
                    }
                    progressDialog.cancel();
                    X = false;
                }
                if (X) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                progressDialog.cancel();
                                Toast.makeText(StepTwo.this, "Already had an account, so please login", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(StepTwo.this, SignInActivity.class);
                                startActivity(i);
                            }
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                                if(e instanceof FirebaseNetworkException)
                                {
                                    Toast.makeText(StepTwo.this, "No internet connection", Toast.LENGTH_SHORT).show();
                                }
                                else if (e instanceof FirebaseAuthInvalidCredentialsException)
                                {
                                    Toast.makeText(StepTwo.this, "Account already exists if you forgot your password,click forgot password in signin page. ", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(StepTwo.this,SignInActivity.class);
                                    startActivity(i);
                                }
                                else {
                                    Intent i = new Intent(StepTwo.this, StepThree.class);
                                    i.putExtra("planname", planame);
                                    i.putExtra("plancost", plancost);
                                    i.putExtra("plancostformat", plancostformat);
                                    i.putExtra("email", useremailId.getText().toString());
                                    i.putExtra("password", userpassword.getText().toString());
                                    progressDialog.cancel();
                                    startActivity(i);

                                }

                                progressDialog.cancel();
                        }
                    });
                } else {
                    progressDialog.cancel();
                    Toast.makeText(StepTwo.this, "Enter valid email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
        SpannableString st = new SpannableString("STEP 2 OF 3");
        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldspan1= new StyleSpan(Typeface.BOLD);
        st.setSpan(boldspan,5,6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        st.setSpan(boldspan1,10,11,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(st);

    }

}