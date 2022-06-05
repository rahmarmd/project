package com.example.fbuttonnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbuttonnav.API.APIRequestData;
import com.example.fbuttonnav.API.RetroServer;
import com.example.fbuttonnav.Response.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    TextView txtRegister;
    Button btnLogin;
    String email, password;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail=findViewById(R.id.txtemail);
        txtPassword=findViewById(R.id.txtpass);
        btnLogin=findViewById(R.id.btnlogin);
        txtRegister=findViewById(R.id.textRegister);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();


                if (email.trim().equals("")) {
                    txtEmail.setError("Email tidak boleh kosong!");
                }else if (password.trim().equals("")) {
                    txtPassword.setError("Anda harus mengisi password");
                } else {
                    login();
                }
            }
        });


    }

    private  void login() {
        sessionManager =  new SessionManager(LoginActivity.this);
        APIRequestData ardData = RetroServer.koneksiRetrofit().create(APIRequestData.class); // menghubungkan class interface ke retrofit
        Call<ResponseUser> simpanData = ardData.checkLogin( email,password);

        simpanData.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                String pesan = response.body().getPesan();
                String id_user = response.body().getId();

                if (pesan.equals("BERHASIL")){
                    sessionManager.setLogin(true);
                    sessionManager.setSessionID(id_user);

                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                    login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    login.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(login);
                    finish();

                } else if (pesan.equals("WRONG")){
                    Toast.makeText(LoginActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                } else  {

                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText( LoginActivity.this, "Terjadi kesalahan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}