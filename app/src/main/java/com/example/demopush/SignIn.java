package com.example.demopush;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demopush.API.ApiClient;
import com.example.demopush.Login.LoginRequest;
import com.example.demopush.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private TextView goToSignUp;
    private EditText email, password;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        init();

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()))
                {
                    ShowDialogWindow("Пустые поля");
                }
                else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(email.getText().toString());
                    loginRequest.setPassword(password.getText().toString());
                    LoginUser(loginRequest);
                }
            }
        });
    }

    public void LoginUser(LoginRequest loginRequest)
    {
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                }
                else{
                    ShowDialogWindow("Что-то пошло не так, повторите попытку позже");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ShowDialogWindow(t.getLocalizedMessage());
            }
        });
    }

    public void ShowDialogWindow(String text){
        final AlertDialog alertDialog = new AlertDialog.Builder(SignIn.this).setMessage(text).setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }

    public void init(){
        email = findViewById(R.id.edEmailSignIn);
        password = findViewById(R.id.edPasswordSignIn);
        goToSignUp = findViewById(R.id.tvGoToSignUp);
        signIn = findViewById(R.id.btnSignIn);
    }
}