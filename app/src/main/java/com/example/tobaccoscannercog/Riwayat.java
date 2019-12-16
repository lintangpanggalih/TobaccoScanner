package com.example.tobaccoscannercog;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Riwayat{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("foto")
    @Expose
    private String foto;

    @SerializedName("akurasi")
    @Expose
    private String akurasi;

    @SerializedName("kualitas")
    @Expose
    private String kualitas;

    @SerializedName("label_kualitas")
    @Expose
    private String id_label;

    public Riwayat(){}


    public Riwayat(String id, String foto, String akurasi,String kualitas, String id_label) {
        this.id = id;
        this.foto = foto;
        this.akurasi = akurasi;
        this.kualitas = kualitas;
        this.id_label = id_label;
    }

    //Setter Getter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAkurasi() {
        return akurasi;
    }

    public void setAkurasi(String akurasi) {
        this.akurasi = akurasi;
    }

    public String getKualitas() {
        return kualitas;
    }

    public void setKualitas(String kualitas) {
        this.kualitas = kualitas;
    }

    public String getIdKualitas() {
        return id_label;
    }

    public void setIdKualitas(String id_kualitas) {
        this.id_label = id_kualitas;
    }

}
