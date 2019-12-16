package com.example.tobaccoscannercog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class RiwayatDetail extends AppCompatActivity {
    TextView showID, showFoto, showAkurasi, showGrade, showKualitas;
    Button btUpdate, btDelete, btBack;
    RiwayatService mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_detail);

        Intent mIntent = getIntent();

        showID = (TextView) findViewById(R.id.showId);
        showFoto = (TextView) findViewById(R.id.showFoto);
        showAkurasi = (TextView) findViewById(R.id.showAkurasi);
        showGrade = (TextView) findViewById(R.id.showGrade);
        showKualitas = (TextView) findViewById(R.id.showLabel);

        showID.setText(mIntent.getStringExtra("Id"));
        showID.setTag(showID.getKeyListener());
        showID.setKeyListener(null);
        showFoto.setText(mIntent.getStringExtra("Nama"));
        showAkurasi.setText(mIntent.getStringExtra("Akurasi"));
        showGrade.setText(mIntent.getStringExtra("Grade"));
        showKualitas.setText(mIntent.getStringExtra("Kualitas"));
        Toast.makeText(getApplicationContext(), mIntent.getStringExtra("Nama") , Toast.LENGTH_SHORT).show();
        // Image link from internet
        DownloadImageFromInternet detail_gambar = new DownloadImageFromInternet((ImageView) findViewById(R.id.img_sampul));
//                .execute("http://e.pbf.ilkom.unej.ac.id/172410102076/api/image/tobacco_logo.png");
        detail_gambar.execute("http://e.pbf.ilkom.unej.ac.id/172410102076/api/image/"+mIntent.getStringExtra("Nama"));

//            else detail_gambar.execute("http://e.pbf.ilkom.unej.ac.id/172410102076/api/image/tobacco_logo.png");
        mApiInterface = APIClient.getRetrofit().create(RiwayatService.class);
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
//                System.out.println(imageURL);
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
                String url = "http://e.pbf.ilkom.unej.ac.id/172410102076/api/image/tobacco_logo.png";
                InputStream in = null;
                try {
                    in = new java.net.URL(url).openStream();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                bimage = BitmapFactory.decodeStream(in);
            }

            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
