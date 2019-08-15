package com.destiny.e_bengkelmitra.Actiivty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.destiny.e_bengkelmitra.API.ApiRequest;
import com.destiny.e_bengkelmitra.API.RetroServer;
import com.destiny.e_bengkelmitra.Model.ResponseModel;
import com.destiny.e_bengkelmitra.R;
import com.destiny.e_bengkelmitra.SharedPreferance.DB_Helper;
import com.destiny.e_bengkelmitra.SharedPreferance.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button Login,Daftar;
    EditText username,password;
    private DB_Helper dbHelper;
    String daftarWA="https://api.whatsapp.com/send?phone=6281384215205&text=Nama%20%3A%20%0AAlamat%20%3A%20%0AJenis%0APekerjaan%20%3A%20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Login=(Button)findViewById(R.id.btnLogin);
        Daftar=(Button)findViewById(R.id.btnDaftar);
        username=(EditText)findViewById(R.id.etUsername);
        password=(EditText)findViewById(R.id.etPassword);
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(daftarWA);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest();
            }
        });
    }
    private void LoginRequest(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Sedang Mengambil Data Ke Server");
        pd.setCancelable(false);
        pd.show();
        final String Username = username.getText().toString();
        final String Password = password.getText().toString();
        if(Username.isEmpty() || Password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Username atau Password Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
            pd.hide();
        }else{
            ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
            Call<ResponseModel> login = api.login(Username,Password);
            login.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    pd.hide();
                    String ress = response.body().getResponse();
                    if(ress.equals("succes")){
                        SessionLoginSucces(Username);
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"Username Atau Password Salah",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    pd.hide();
                    Toast.makeText(LoginActivity.this,R.string.koneksi_gagal,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void SessionLoginSucces(String Username){
        String Person="Mitra";
        dbHelper = new DB_Helper(this);
        User user = new User(Username,Person);
        dbHelper.saveSession(user);
    }
}
