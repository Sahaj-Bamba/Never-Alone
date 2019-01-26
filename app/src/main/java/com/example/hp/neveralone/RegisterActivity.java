package com.example.hp.neveralone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username,email,password,description,name,address,institute;
    Button btn_register;

    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.input_username);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_signup);

        description = findViewById(R.id.input_description);
        name = findViewById(R.id.input_name);
        address = findViewById(R.id.input_address);
        institute = findViewById(R.id.input_institute);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_description = description.getText().toString();
                String txt_name = name.getText().toString();
                String txt_address = address.getText().toString();
                String txt_institute = institute.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)|| TextUtils.isEmpty(txt_description)|| TextUtils.isEmpty(txt_institute)|| TextUtils.isEmpty(txt_address)|| TextUtils.isEmpty(txt_name)){
                    Toast.makeText(RegisterActivity.this,"All fields are required ",Toast.LENGTH_SHORT).show();
                } else if(txt_password.length() <6 ){
                    Toast.makeText(RegisterActivity.this,"Password must be at least6 characters ",Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email , txt_password,txt_description,txt_name,txt_address,txt_institute);
                }
            }
        });


    }

    private void register (final String username , final String email , String password, final String description, final String name,final  String address,final String institute){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String > hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL","default");
                            hashMap.put("search",name.toLowerCase());
                            hashMap.put("description", description);
                            hashMap.put("name", name);
                            hashMap.put("address", address);
                            hashMap.put("institute", institute);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        if((firebaseUser.isEmailVerified())) {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            firebaseUser.sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                                Toast.makeText(RegisterActivity.this,"Verification email is sent to your email id "+firebaseUser.getEmail(),Toast.LENGTH_SHORT).show();
                                                            else
                                                                Toast.makeText(RegisterActivity.this,"Failed to send Verification email ",Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                            if(firebaseUser.isEmailVerified()){
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password. This email already exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
