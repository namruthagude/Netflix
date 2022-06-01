package com.example.netflix.MainScreens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.netflix.Activites.SignInActivity;
import com.example.netflix.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Settings extends AppCompatActivity {
    EditText currentpassword;
    TextView email,date,plan;
    Button resetpassword,signout;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    DocumentReference reference;
    ProgressDialog progressDialog;
    FirebaseUser user;
    String uid,emailString,planString;
    Date valid_date;
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent i = new Intent(Settings.this,MainScreen.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        currentpassword = findViewById(R.id.currentPasswordSettings);
        email = findViewById(R.id.emailSettings);
        date = findViewById(R.id.dateSettings);
        plan = findViewById(R.id.planSettings);
        resetpassword = findViewById(R.id.resetpasswordbutton);
        signout = findViewById(R.id.signoutSettingsbutton);
        progressDialog = new ProgressDialog(Settings.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        user = firebaseAuth.getInstance().getCurrentUser();

        uid = firebaseAuth.getCurrentUser().getUid();
        reference = firebaseFirestore.collection("Users").document(uid);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                        valid_date = documentSnapshot.getDate("Valid_date");
                        emailString = documentSnapshot.getString("Email");
                        planString = documentSnapshot.getString("Plan_Cost");
                        email.setText(emailString);
                        plan.setText("₹"+planString+"/month");
                        date.setText(valid_date.toString());
                        progressDialog.cancel();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if( e instanceof FirebaseNetworkException)
                {
                    Toast.makeText(Settings.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(Settings.this, "Error in data fecthing", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        });
       signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder SignOut = new AlertDialog.Builder(view.getContext());
                SignOut.setTitle("Sign out?");
                SignOut.setCancelable(false);
                SignOut.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.getInstance().signOut();
                        Intent j = new Intent(Settings.this, SignInActivity.class);
                        startActivity(j);
                    }
                });
                SignOut.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                finish();
            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Settings.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(currentpassword.getText().toString().length()>7)
                {
                    firebaseAuth.signInWithEmailAndPassword(emailString,currentpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            AlertDialog.Builder updatepassword = new AlertDialog.Builder(view.getContext());
                            updatepassword.setCancelable(false);
                            updatepassword.setTitle("change password?");
                            EditText newpassword = new EditText(view.getContext());
                            newpassword.setHint("New Password");
                            updatepassword.setView(newpassword);
                            updatepassword.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.show();
                                    String newpasswordString = newpassword.getText().toString();
                                    if(newpasswordString.length()>7)
                                    {
                                        user.updatePassword(newpasswordString).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.cancel();
                                                Toast.makeText(Settings.this, "password updated", Toast.LENGTH_SHORT).show();
                                                currentpassword.setText("");
                                                Toast.makeText(Settings.this, "Login again", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(Settings.this,SignInActivity.class);
                                                startActivity(i);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                if(e instanceof FirebaseNetworkException)
                                                {
                                                    Toast.makeText(Settings.this, "No internet connecting", Toast.LENGTH_SHORT).show();
                                                }
                                                Toast.makeText(Settings.this, "Password not updated", Toast.LENGTH_SHORT).show();
                                                progressDialog.cancel();
                                            }
                                        });
                                    }
                                    else
                                    {
                                        if(newpasswordString.length()==0)
                                        {
                                            newpassword.setError("Please fill newpassword to update password");
                                        }
                                        if(newpasswordString.length()<=7)
                                        {
                                            newpassword.setError("password too short");
                                        }
                                        progressDialog.cancel();
                                    }

                                }
                            });
                            updatepassword.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    currentpassword.setText("");
                                    progressDialog.cancel();
                                }
                            });
                            updatepassword.create().show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if( e instanceof FirebaseNetworkException)
                            {
                                Toast.makeText(Settings.this, "No internet connection", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            if(e instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                Toast.makeText(Settings.this, "Enter password correctly", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }
                    });

                }

                else
                {
                    if(currentpassword.getText().toString().length()==0)
                    {
                        currentpassword.setError("Please fill current to reset password");

                    }
                    if(currentpassword.getText().toString().length()<=7)
                    {
                        currentpassword.setError("password too short");
                    }
                    progressDialog.cancel();
                }
            }
        });
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homeicon:
                        Intent i = new Intent(Settings.this,MainScreen.class);
                        startActivity(i);
                        break;
                    case R.id.searchicon:
                        Intent j = new Intent(Settings.this,Search.class);
                        startActivity(j);
                        break;
                    case R.id.settingsicon:

                        break;

                }
                return false;
            }
        });
    }
}