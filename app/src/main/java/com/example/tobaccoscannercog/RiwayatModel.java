package com.example.tobaccoscannercog;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RiwayatModel {
        @SerializedName("status")
        String status;
        @SerializedName("riwayat")
        List<Riwayat> listDataRiwayat;
        @SerializedName("message")
        String message;

        public String getStatus() {
                return status;
        }
        public void setStatus(String status) {
                this.status = status;
        }
        public String getMessage() {
                return message;
        }
        public void setMessage(String message) {
                this.message = message;
        }
        public List<Riwayat> getListDataRiwayat() {
                return listDataRiwayat;
        }
        public void setListDataRiwayat(List<Riwayat> listDataRiwayat) {
                this.listDataRiwayat = listDataRiwayat;
        }
}
