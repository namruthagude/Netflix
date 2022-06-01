package com.example.netflix.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class SignInActivity extends AppCompatActivity {
    ProgressBar pbar;
    TextView signup;
    TextView forgotpassword;
    Button signin;
    EditText Useremail,Userpassword;
    String reset,username,password,UserId,FireContact,FireEmail,FireFirstname,FireLastname;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Date today,valid_date;
    DocumentReference userReference;
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.GONE);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        forgotpassword= findViewById(R.id.forgotpassword);
        Useremail = findViewById(R.id.emaileditText);
        Userpassword = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Calendar c = Calendar.getInstance();
        today = c.getTime();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean X= true;
                username = Useremail.getText().toString();
                password = Userpassword.getText().toString();


                if(username.length()<8)
                {
                    if(username.length()==0)
                    {
                        Useremail.setError("Email cannot be empty");
                    }
                    else if(! username.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
                    {
                        Useremail.setError("Enter a valid emailId");
                    }
                    else {
                        Useremail.setError("Username must be greater than 8");
                    }
                    X=false;
                }
                if(password.length()<7)
                {
                    if(password.length()==0)
                    {
                        Userpassword.setError("Password cannot be empty");
                    }
                    else {
                        Userpassword.setError("password must be greater than 7");
                    }
                    X=false;

                }
                if(X)
                {
                    pbar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                UserId = firebaseAuth.getCurrentUser().getUid();
                                userReference = firebaseFirestore.collection("Users").document(UserId);
                                userReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        valid_date = documentSnapshot.getDate("Valid_date");
                                        FireContact = documentSnapshot.getString("Contact_Num");
                                        FireEmail = documentSnapshot.getString("Email");
                                        FireFirstname = documentSnapshot.getString("First_Name");
                                        FireLastname = documentSnapshot.getString("Last_Name");
                                        if(valid_date.compareTo(today)>=0)
                                        {
                                            Intent i = new Intent(SignInActivity.this, MainScreen.class);
                                            startActivity(i);
                                            pbar.setVisibility(View.GONE);
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        }
                                        else
                                        {
                                            Intent i = new Intent(SignInActivity.this,PlanExpired.class);
                                            i.putExtra("firstname",FireFirstname);
                                            i.putExtra("lastname",FireLastname);
                                            i.putExtra("email",FireEmail);
                                            i.putExtra("contact",FireContact);
                                            i.putExtra("uid",UserId);
                                            startActivity(i);
                                            pbar.setVisibility(View.GONE);
                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        }
                                    }
                                });

                            }
                            else
                            {
                                if(task.getException() instanceof FirebaseNetworkException)
                                {
                                    Toast.makeText(SignInActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                                }
                                if(task.getException() instanceof FirebaseAuthInvalidUserException)
                                {
                                    Toast.makeText(SignInActivity.this, "User does not exists", Toast.LENGTH_SHORT).show();
                                }
                                if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                {
                                    Toast.makeText(SignInActivity.this, "incorrect password", Toast.LENGTH_SHORT).show();
                                }
                                pbar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        }
                    });
                }


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Intent i2 = new Intent(SignInActivity.this,SwipeScreen.class);
                startActivity(i2);
                pbar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Useremail.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")&& Useremail.getText().toString().length()>8 && !Useremail.getText().toString().isEmpty())
                {
                    AlertDialog.Builder resetPassword = new AlertDialog.Builder(view.getContext());
                    resetPassword.setTitle("RESET PASSWORD");
                    resetPassword.setMessage("Press YES, to get link to reset your password");
                    resetPassword.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            reset = Useremail.getText().toString();
                            firebaseAuth.sendPasswordResetEmail(reset).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignInActivity.this, "Link to reset password has been sent", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if(e instanceof FirebaseAuthInvalidUserException)
                                    {

                                        Toast.makeText(SignInActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                                    }
                                    if( e instanceof  FirebaseNetworkException)
                                    {
                                        Toast.makeText(SignInActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                                    }
                                    if( e instanceof  FirebaseAuthInvalidUserException)
                                    {
                                        Toast.makeText(SignInActivity.this, "No user exists with this emailId", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                    resetPassword.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    resetPassword.create().show();
                }
                else
                {


                    if(Useremail.getText().toString().length()<=8)
                    {
                        if(Useremail.getText().toString().isEmpty())
                        {
                            Useremail.setError("Enter email Id to get link to reset password");
                        }


                        else {
                            Useremail.setError("Email Id must be greater than 8");
                        }
                    }
                    else if(!Useremail.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
                    {
                        Useremail.setError("Enter valid email Id");
                    }
                }
            }
        });
    }

}