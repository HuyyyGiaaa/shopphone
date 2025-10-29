package com.example.shopphone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mausac")
public class MauSac {
    @Id
    private Integer idmau;
    private String mausac;

    public MauSac() {
    }

    public MauSac(Integer idmau, String mausac) {
        this.idmau = idmau;
        this.mausac = mausac;
    }

    public Integer getIdmau() {
        return idmau;
    }

    public void setIdmau(Integer idmau) {
        this.idmau = idmau;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }
}
