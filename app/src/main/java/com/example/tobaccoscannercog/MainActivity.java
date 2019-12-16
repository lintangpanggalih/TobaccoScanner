package com.example.tobaccoscannercog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonPanel;
    private Button buttonRiwayat;
    private Camera.PictureCallback mPicture;
    public static Bitmap bitmap;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mCamera = Camera.open();
//        mCamera.setDisplayOrientation(90);

        buttonPanel = findViewById(R.id.buttonPanel);
        buttonPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityScan();
            }
        });

        buttonRiwayat = findViewById(R.id.buttonRiwayat);
        buttonRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRiwayatActivity();
            }
        });
        //refresh();
    }

    public void openRiwayatActivity(){
        Intent intent = new Intent(this, RiwayatActivity.class);
        startActivity(intent);
    }

    public void openActivityScan(){
        Intent intent = new Intent(this, ActivityScan.class);
        startActivity(intent);
    }
}
