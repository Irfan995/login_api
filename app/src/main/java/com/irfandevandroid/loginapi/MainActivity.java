package com.irfandevandroid.loginapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UserClient userClient = retrofit.create(UserClient.class);

    private EditText username, password;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private String token;

    private void login() {
        Login login = new Login(username.getText().toString(), password.getText().toString());
        Call<Token> call = userClient.login(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d("IrfanLog", String.valueOf(response));
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "login Succesfull", Toast.LENGTH_SHORT).show();
                    Log.d("IrfanLog", String.valueOf(response.body()));
                } else {
                    Toast.makeText(MainActivity.this, response.code() + ":" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("IrfanLog", String.valueOf(t));
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}