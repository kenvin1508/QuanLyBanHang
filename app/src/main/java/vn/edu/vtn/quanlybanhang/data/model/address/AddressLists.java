package vn.edu.vtn.quanlybanhang.data.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressLists implements Serializable {
    @SerializedName("idDiaChiKhachHang")
    @Expose
    private Integer idDiaChiKhachHang;
    @SerializedName("tenDiaChi")
    @Expose
    private String tenDiaChi;
    @SerializedName("tenKhachHang")
    @Expose
    private String tenKhachHang;
    @SerializedName("soDt")
    @Expose
    private String soDt;
    @SerializedName("idKhachHang")
    @Expose
    private Integer idKhachHang;
    @SerializedName("loai")
    @Expose
    private Boolean loai;

    public Integer getIdDiaChiKhachHang() {
        return idDiaChiKhachHang;
    }

    public void setIdDiaChiKhachHang(Integer idDiaChiKhachHang) {
        this.idDiaChiKhachHang = idDiaChiKhachHang;
    }

    public String getTenDiaChi() {
        return tenDiaChi;
    }

    public void setTenDiaChi(String tenDiaChi) {
        this.tenDiaChi = tenDiaChi;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public Integer getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Boolean getLoai() {
        return loai;
    }

    public void setLoai(Boolean loai) {
        this.loai = loai;
    }
}
