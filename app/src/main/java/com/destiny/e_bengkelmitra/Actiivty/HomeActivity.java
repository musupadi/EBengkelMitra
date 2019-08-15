package com.destiny.e_bengkelmitra.Actiivty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.destiny.e_bengkelmitra.R;
import com.destiny.e_bengkelmitra.SharedPreferance.DB_Helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    DB_Helper dbHelper;
    String User="";
    private long backPressedTime = 0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dompet:
                    mTextMessage.setText(R.string.title_dompet);
                    return true;
                case R.id.navigation_riwayat:
                    mTextMessage.setText(R.string.title_riwayat);
                    return true;
                case R.id.navigation_lainnya:
                    mTextMessage.setText(R.string.title_lainnya);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Yakin ingin Logout ?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        dbHelper = new DB_Helper(HomeActivity.this);
                        dbHelper.userLogout(User,HomeActivity.this);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_close_black_24dp);
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Tekan Lagi Untuk Logout",
                    Toast.LENGTH_SHORT).show();

        } else {
            logout();
        }
    }
}
