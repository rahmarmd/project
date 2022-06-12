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

public class RegisterActivity extends AppCompatActivity {
    EditText txtFullName,txtEmail, txtPassword,txtNomorponsel,txtConfpass;
    TextView txtLogin;
    Button btnRegister, btnLogin;
    String fullname, username, email, password, no_telp, confpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtFullName=findViewById(R.id.txtname);
        txtEmail=findViewById(R.id.txtemail);
        txtPassword=findViewById(R.id.txtpass1);
        txtConfpass=findViewById(R.id.txtpass2);
        txtNomorponsel=findViewById(R.id.txtnoHP);
        btnRegister=findViewById(R.id.btnRegister);
        txtLogin=findViewById(R.id.textRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = txtFullName.getText().toString();
                email = txtEmail.getText().toString();
                no_telp = txtNomorponsel.getText().toString();
                password = txtPassword.getText().toString();
                confpass = txtConfpass.getText().toString();

                if (fullname.trim().equals("")) {
                    txtFullName.setError("Nama Lengkap tidak boleh kosong!");
                } else if (email.trim().equals("")) {
                    txtEmail.setError("Email tidak boleh kosong!");
                } else if (no_telp.trim().equals("")) {
                    txtNomorponsel.setError("Nomor Telepon tidak boleh kosong!");
                } else if (password.trim().equals("")) {
                    txtPassword.setError("Anda harus mengisi password");
                } else if (!password.equals(confpass)) {
                    txtConfpass.setError("Konfirmasi Password salah!");
                }else {
                    Register();
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fullname = txtFullName.getText().toString();
//                email = txtEmail.getText().toString();
//                no_telp = txtNomorponsel.getText().toString();
//                password = txtPassword.getText().toString();
//                confpass = txtConfpass.getText().toString();
//
//                if (fullname.trim().equals("")) {
//                    txtFullName.setError("Nama Lengkap tidak boleh kosong!");
//                } else if (email.trim().equals("")) {
//                    txtEmail.setError("Email tidak boleh kosong!");
//                } else if (no_telp.trim().equals("")) {
//                    txtNomorponsel.setError("Nomor Telepon tidak boleh kosong!");
//                } else if (password.trim().equals("")) {
//                    txtPassword.setError("Anda harus mengisi password");
//                } else if (!password.equals(confpass)) {
//                    txtConfpass.setError("Konfirmasi Password salah!");
//                }else {
//                    Register();
//                }
//            }
    }

    private  void Register() {
        APIRequestData ardData = RetroServer.koneksiRetrofit().create(APIRequestData.class); // menghubungkan class interface ke retrofit
        Call<ResponseUser> simpanData = ardData.userRegister(fullname, email, no_telp, password);

        simpanData.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                String pesan = response.body().getPesan();
                if (pesan.equals("Daftar berhasil!")){
                    Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                } else if (pesan.equals("Daftar gagal!")){
                    Toast.makeText(RegisterActivity.this, "Gagal melakukan registrasi", Toast.LENGTH_SHORT).show();
                } else if (pesan.equals("Email terdaftar")) {
                    txtEmail.setError("Email sudah terdaftar");
                    Toast.makeText(RegisterActivity.this, "Email sudah terdaftar!", Toast.LENGTH_SHORT).show();
                } else if (pesan.equals("Nomor telepon terdaftar")) {
                    txtNomorponsel.setError("Nomor telepon sudah terdaftar");
                    Toast.makeText(RegisterActivity.this, "Nomor telepon sudah terdaftar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText( RegisterActivity.this, "Terjadi kesalahan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
