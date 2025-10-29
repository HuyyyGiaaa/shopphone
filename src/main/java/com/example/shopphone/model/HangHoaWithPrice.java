package com.example.shopphone.model;

public class HangHoaWithPrice {
    private Integer mahh;
    private String tenhh;
    private String hinh;
    private Float dongia;


    public HangHoaWithPrice(Integer mahh, String tenhh, String hinh, Float dongia) {
        this.mahh = mahh;
        this.tenhh = tenhh;
        this.hinh = hinh;
        this.dongia = dongia;
    }

    public Integer getMahh() {
        return mahh;
    }

    public void setMahh(Integer mahh) {
        this.mahh = mahh;
    }

    public String getTenhh() {
        return tenhh;
    }

    public void setTenhh(String tenhh) {
        this.tenhh = tenhh;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Float getDongia() {
        return dongia;
    }

    public void setDongia(Float dongia) {
        this.dongia = dongia;
    }
}
