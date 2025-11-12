package com.example.shopphone.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bonho")
public class BoNho {

    @Id
    private Long idbonho; // Set sẵn

    @Column(nullable = false)
    private String dungluong; // ví dụ: "256GB"

    // --- Constructors ---
    public BoNho() {
    }

    public BoNho(Long idbonho, String dungluong) {
        this.idbonho = idbonho;
        this.dungluong = dungluong;
    }

    // --- Getters & Setters ---
    public Long getIdbonho() {
        return idbonho;
    }

    public void setIdbonho(Long idbonho) {
        this.idbonho = idbonho;
    }

    public String getDungluong() {
        return dungluong;
    }

    public void setDungluong(String dungluong) {
        this.dungluong = dungluong;
    }
}
