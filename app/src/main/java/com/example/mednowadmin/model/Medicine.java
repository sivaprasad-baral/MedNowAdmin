package com.example.mednowadmin.model;

public class Medicine {

    private String medName,medPrice,medId;

    public Medicine() {
    }

    public Medicine(String medName, String medPrice, String medId) {
        this.medName = medName;
        this.medPrice = medPrice;
        this.medId = medId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(String medPrice) {
        this.medPrice = medPrice;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }
}
