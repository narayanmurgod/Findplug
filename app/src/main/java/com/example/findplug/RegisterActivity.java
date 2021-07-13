package com.example.findplug;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    TextView btn;
    Button Signup;
    private EditText inputUserName, inputUserEmail, inputUserVehicle, inputUserContact, inputUserPasswordReg;
    private FirebaseAuth mAUth;
    private ProgressDialog mLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);





        inputUserName = findViewById(R.id.UserEmailLog);
        inputUserEmail = findViewById(R.id.UserEmail);
        inputUserVehicle = findViewById(R.id.UserVehicle);
        inputUserContact = findViewById(R.id.UserContact);
        inputUserPasswordReg = findViewById(R.id.UserPasswordReg);
        mAUth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(RegisterActivity.this);



        Signup= findViewById(R.id.btnRegister);
        btn = findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener((v) ->
        {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        Signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mAUth.createUserWithEmailAndPassword(inputUserEmail.getText().toString(), inputUserPasswordReg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if (task.isSuccessful()) {
                           Toast.makeText(RegisterActivity.this, "Successfully Registration", Toast.LENGTH_SHORT).show();

                       } else {
                           Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                       }

                   }

               });

           }
       });



    }

    private void checkCrededentials() {

        String UserName = inputUserName.getText().toString();
        String UserEmail = inputUserEmail.getText().toString();
        String UserVehicle = inputUserVehicle.getText().toString();
        String UserContact = inputUserContact.getText().toString();
        String UserPasswordReg = inputUserPasswordReg.getText().toString();



        if (UserName.isEmpty() || UserName.length() < 0)
        {
            ShowError(inputUserName, "Your username i5 not valid!");
        } else if (UserEmail.isEmpty() || !UserEmail.contains("@"))
        {
            ShowError(inputUserEmail, "Email is not valid");
        } else if (UserVehicle.isEmpty() || UserVehicle.length() < 0)
        {
            ShowError(inputUserVehicle, "Enter valid model name");
        } else if (UserContact.isEmpty() || UserContact.length() < 10)
        {
            ShowError(inputUserContact, "Enter valid phone number");
        } else if (UserPasswordReg.isEmpty() || UserPasswordReg.length() < 7) {
            ShowError(inputUserPasswordReg, "Password must be 7 character");
        } else {
            mLoadingBar.setTitle("Registeration");
            mLoadingBar.setMessage("Please wait,while check your credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
        }
    }

    private void ShowError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}

