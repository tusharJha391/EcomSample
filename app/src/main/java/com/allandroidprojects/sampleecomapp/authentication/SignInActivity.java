package com.allandroidprojects.sampleecomapp.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {

    EditText editInputEmail,editInputPassword;
    Button loginButton;
    TextView linkSignUpText,forgetPasswordText;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseFirestore firebaseFirestore;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_layout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        editInputEmail = findViewById(R.id.input_email);
        editInputPassword = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        linkSignUpText = findViewById(R.id.link_signup);
        forgetPasswordText = findViewById(R.id.forget_pass);
        sp = getSharedPreferences("myPrefFireStore",MODE_PRIVATE);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser!=null) {
                    Toast.makeText(SignInActivity.this, "User logged in ", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(I);
                } else {
                    Toast.makeText(SignInActivity.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }

        };
        linkSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginMailDetail = editInputEmail.getText().toString();
                String loginPasswordDetail = editInputPassword.getText().toString();
                if (loginMailDetail.isEmpty()) {
                    editInputEmail.setError("Provide your Email first!");
                    editInputEmail.requestFocus();
                } else if (loginPasswordDetail.isEmpty()) {
                    editInputPassword.setError("Enter Password!");
                    editInputPassword.requestFocus();
                } else if (loginMailDetail.isEmpty() && loginPasswordDetail.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(loginMailDetail.isEmpty() && loginPasswordDetail.isEmpty())) {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );

                        firebaseAuth.signInWithEmailAndPassword(loginMailDetail,loginPasswordDetail).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    //firebaseFirestore.document("Users").get().isSuccessful();

                                    Toast.makeText(SignInActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    /*boolean getIsDataBaseAdded = sp.getBoolean("IsDataBaseAdded",false);
                                    if (!getIsDataBaseAdded) {
                                        SignUpActivity signUpActivity = new SignUpActivity();
                                        signUpActivity.putDataBase();
                                    }*/
                                    //firebaseFirestore.document("Users").get().isSuccessful();

                                    startActivity(new Intent(SignInActivity.this,MainActivity.class));

                                }
                            }
                        });

                } else {
                    Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth != null)
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
