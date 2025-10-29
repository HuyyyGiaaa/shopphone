package com.example.shopphone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dongmay")
public class DongMay {
    @Id
    private Integer idmay; // id dòng máy
    private String tenmay; // tên dòng máy, ví dụ "iPhone 6 Series"

    public DongMay() {
    }

    public DongMay(Integer idmay, String tenmay) {
        this.idmay = idmay;
        this.tenmay = tenmay;
    }

    public Integer getIdmay() {
        return idmay;
    }

    public void setIdmay(Integer idmay) {
        this.idmay = idmay;
    }

    public String getTenmay() {
        return tenmay;
    }

    public void setTenmay(String tenmay) {
        this.tenmay = tenmay;
    }
}
