package com.allandroidprojects.sampleecomapp.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.startup.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextMobileNumber;
    EditText editTextPassword;
    Button signUp;
    Button login;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser mfirebaseUser;
    String userName;
    String userEmail;
    String userPhone;
    String userPassword;
    public static boolean isDataAdded;
    private FirebaseAuth.AuthStateListener authStateListener;
    String TAG;
    SharedPreferences sp;

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        isDataAdded = false;
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sp= getSharedPreferences("myPrefFireStore",MODE_PRIVATE);
        editTextName = findViewById(R.id.editName);
        editTextEmail = findViewById(R.id.editEmail);
        editTextMobileNumber = findViewById(R.id.editPhone);
        editTextPassword = findViewById(R.id.editPassword);
        signUp = findViewById(R.id.buttonRegister);
        login = findViewById(R.id.buttonLogin);
        TAG=SignUpActivity.class.getName();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = editTextName.getText().toString();
                userEmail = editTextEmail.getText().toString();
                userPhone = editTextMobileNumber.getText().toString();
                userPassword = editTextPassword.getText().toString();
                if (userName.isEmpty()) {
                    editTextName.setError("Provide your email first!");
                    editTextName.requestFocus();
                } else if (userEmail.isEmpty()) {
                    editTextEmail.setError("Email cannot be empty!");
                    editTextEmail.requestFocus();
                } else if (userPassword.isEmpty()) {
                    editTextPassword.setError("Please set your password!");
                    editTextPassword.requestFocus();
                } else if (userPhone.isEmpty()) {
                    editTextMobileNumber.setError("Provide your phone number!");
                    editTextMobileNumber.requestFocus();
                } else if (userName.isEmpty() && userEmail.isEmpty() && userPassword.isEmpty() && userPhone.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,"Text fields are empty!",Toast.LENGTH_SHORT).show();
                } else if (!(userName.isEmpty() && userEmail.isEmpty() && userPassword.isEmpty() && userPhone.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(
                            SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this.getApplicationContext(),
                                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();

                                    } else {
                                        FirebaseUser userCheck = FirebaseAuth.getInstance().getCurrentUser();
                                        if (userCheck != null && !userCheck.isEmailVerified()) {
                                            userCheck.sendEmailVerification();
                                        }
                                        mfirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        putDataBase();



                                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                    }
                                }
                            }
                    );

                } else {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));

            }
        });



    }

    public void putDataBase() {
        if (mfirebaseUser != null) {
            //String user = mfirebaseUser.getUid();


            Map<String,Object> user =new HashMap<>();
            user.put("Name",userName);
            user.put("Email",userEmail);
            user.put("Phone",userPhone);
        db.collection("Users").document(mfirebaseUser.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        isDataAdded=true;
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("IsDataBaseAdded",isDataAdded);
                        editor.apply();
                        Log.d(TAG,"DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        }


    }
}
