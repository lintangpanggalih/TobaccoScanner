package com.example.tobaccoscannercog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {
//    Button btIns;
    Spinner mySpinner;
    RiwayatService mRiwayatService;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static RiwayatActivity ra;
    String[] categories={"Semua","Baik","Buruk"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRiwayatService = APIClient.getRetrofit().create(RiwayatService.class);
        ra=this;
        //refresh();
        sortRiwayat();
    }

    public void refresh(final int id_label) {
        Call<RiwayatModel> riwayatCall = mRiwayatService.getRiwayat();
        Call<RiwayatModel> labelriwayatCall = mRiwayatService.getLabelRiwayat(id_label);
        if (id_label == 0){
            riwayatCall.enqueue(new Callback<RiwayatModel>() {
                @Override
                public void onResponse(Call<RiwayatModel> call, Response<RiwayatModel>
                        response) {
                    List<Riwayat> RiwayatList = response.body().getListDataRiwayat();
                    Log.d("Retrofit Get", "Jumlah data Riwayat: " +
                            String.valueOf(RiwayatList.size()));
                    mAdapter = new RiwayatAdapter(RiwayatList);
                    mRecyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onFailure(Call<RiwayatModel> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }

            });
        }else{
            labelriwayatCall.enqueue(new Callback<RiwayatModel>() {
                @Override
                public void onResponse(Call<RiwayatModel> call, Response<RiwayatModel>
                        response) {
                    List<Riwayat> RiwayatList = response.body().getListDataRiwayat();
                    Log.d("Retrofit Get", "Jumlah data Riwayat: " +
                            String.valueOf(RiwayatList.size()));
                    mAdapter = new RiwayatAdapter(RiwayatList);
                    mRecyclerView.setAdapter(mAdapter);
                }
                @Override
                public void onFailure(Call<RiwayatModel> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }
            });
        }
    }

    private void getSelectedCategoryData(final int id_label) {
            //arraylist to hold selected cosmic bodies
            if(id_label == 0)
            {
                refresh(0);
            }
            else if (id_label == 1){
                Call<RiwayatModel> riwayatCall = mRiwayatService.getLabelRiwayat(1);
                riwayatCall.enqueue(new Callback<RiwayatModel>() {
                    @Override
                    public void onResponse(Call<RiwayatModel> call, Response<RiwayatModel>
                            response) {
                        List<Riwayat> RiwayatList = response.body().getListDataRiwayat();
                        Log.d("Retrofit Get", "Jumlah data Riwayat: " +
                                String.valueOf(RiwayatList.size()));
                        mAdapter = new RiwayatAdapter(RiwayatList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    @Override
                    public void onFailure(Call<RiwayatModel> call, Throwable t) {
                        Log.e("Retrofit Get", t.toString());
                        Toast.makeText(RiwayatActivity.this, "Ora Enek Lurrr!", Toast.LENGTH_SHORT).show();
                    }

                });
            }else{
                Call<RiwayatModel> riwayatCall = mRiwayatService.getLabelRiwayat(2);
                riwayatCall.enqueue(new Callback<RiwayatModel>() {
                    @Override
                    public void onResponse(Call<RiwayatModel> call, Response<RiwayatModel>
                            response) {
                        List<Riwayat> RiwayatList = response.body().getListDataRiwayat();
                        Log.d("Retrofit Get", "Jumlah data Riwayat: " +
                                String.valueOf(RiwayatList.size()));
                        mAdapter = new RiwayatAdapter(RiwayatList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    @Override
                    public void onFailure(Call<RiwayatModel> call, Throwable t) {
                        Log.e("Retrofit Get", t.toString());
                        Toast.makeText(RiwayatActivity.this, "Ora Enek Lurrr!", Toast.LENGTH_SHORT).show();
                    }

                });
            }
    }

    public void sortRiwayat(){
        mySpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0 && position < categories.length) {
                    refresh(position);
                } else{
                    Toast.makeText(RiwayatActivity.this, "Ora Enek Lurrr!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private Thread.UncaughtExceptionHandler androidDefaultUEH;
    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e("TestApplication", "Uncaught exception is: ", ex);
            openRiwayatActivity();
            androidDefaultUEH.uncaughtException(thread, ex);
        }
    };

    public void openRiwayatActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




}