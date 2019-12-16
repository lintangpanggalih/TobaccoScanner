package com.example.tobaccoscannercog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.MyViewHolder>{
    List<Riwayat> mRiwayatlist;

    public RiwayatAdapter(List <Riwayat> RiwayatList) {
        mRiwayatlist = RiwayatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(
                parent.getContext()).inflate
                (R.layout.daftar_riwayat, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (final MyViewHolder holder, final int position){
        holder.mTextViewId.setText("Id = " + mRiwayatlist.get(position).getId());
        holder.mTextViewFoto.setText("Nama\t= " + mRiwayatlist.get(position).getFoto());
        holder.mTextViewAkurasi.setText("Akurasi\t= " + mRiwayatlist.get(position).getAkurasi());
        holder.mTextViewIdLabel.setText("Grade = " + mRiwayatlist.get(position).getIdKualitas());
        holder.mTextViewLabel.setText("Kualitas\t= " + mRiwayatlist.get(position).getKualitas());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), RiwayatDetail.class);
                mIntent.putExtra("Id", mRiwayatlist.get(position).getId());
                mIntent.putExtra("Nama", mRiwayatlist.get(position).getFoto());
                mIntent.putExtra("Akurasi", mRiwayatlist.get(position).getAkurasi());
                mIntent.putExtra("Grade", mRiwayatlist.get(position).getIdKualitas());
                mIntent.putExtra("Kualitas", mRiwayatlist.get(position).getKualitas());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return mRiwayatlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewId, mTextViewFoto, mTextViewAkurasi, mTextViewLabel, mTextViewIdLabel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.tvId);
            mTextViewFoto = (TextView) itemView.findViewById(R.id.tvFoto);
            mTextViewAkurasi = (TextView) itemView.findViewById(R.id.tvAkurasi);
            mTextViewLabel = (TextView) itemView.findViewById(R.id.tvLabel);
            mTextViewIdLabel = (TextView) itemView.findViewById(R.id.tvId_Label);
        }
    }
}