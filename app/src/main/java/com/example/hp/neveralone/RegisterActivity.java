package com.example.hp.neveralone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    FirebaseAuth auth;
    EditText name  , email , address,college,desc,password;

    Button signup;
    DatabaseReference reference;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.input_email);
        name  = findViewById(R.id.input_name);
        password = findViewById(R.id.input_password);
        address = findViewById(R.id.input_address);
        college = findViewById(R.id.input_college);
        desc = findViewById(R.id.input_description);
        signup = findViewById(R.id.btn_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_name = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_address  = address.getText().toString();
                String txt_college = college.getText().toString();
                String txt_desc = desc.getText().toString();

                if(TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_college) || TextUtils.isEmpty(txt_address) || TextUtils.isEmpty(txt_desc)){
                    Toast.makeText(RegisterActivity.this,"All fields are required ",Toast.LENGTH_SHORT).show();
                } else if(txt_password.length() <6 ){
                    Toast.makeText(RegisterActivity.this,"Password must be at least6 characters ",Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_name, txt_email , txt_password,txt_address,txt_college,txt_desc);
                }
            }
        });
    }



    private void register (final String name , String email , String password,final String address, final String college , final String descr){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Inside auth",Toast.LENGTH_SHORT).show();
                            final FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String > hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("name", name);
                            hashMap.put("imageURL","default");
                            hashMap.put("address",address);
                            hashMap.put("college",college);
                            hashMap.put("desc",descr);
                            hashMap.put("search",name.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RegisterActivity.this,"Inside set value",Toast.LENGTH_SHORT).show();
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Inside tak ",Toast.LENGTH_SHORT).show();
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
                                                            if(task.isSuccessful()) {
                                                                Toast.makeText(RegisterActivity.this, "Verification email is sent to your email id " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                                                 startActivity(intent);
                                                                 finish();
                                                                }
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
