package com.destiny.e_bengkelmitra.Actiivty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.destiny.e_bengkelmitra.R;
import com.destiny.e_bengkelmitra.SharedPreferance.DB_Helper;

public class MainActivity extends AppCompatActivity {
    private DB_Helper dbHelper;
    String User,Person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DB_Helper(this);
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                User = cursor.getString(0);
                Person = cursor.getString(1);
            }
        }
        if (User != null){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    changeActivity();
                    finish();
                }
            }, 3000); //3000 L = 3 detik
        }
    }

    private void changeActivity(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
