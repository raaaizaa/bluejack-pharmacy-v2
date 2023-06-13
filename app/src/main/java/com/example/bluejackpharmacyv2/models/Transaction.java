package com.example.bluejackpharmacyv2.models;

import java.util.Date;

public class Transaction {
    String medicineImage, medicineName, manufacturer;
    Integer transactionId, medicineId, userId, quantity, price;
    Date transactionDate;

    public Transaction(String medicineImage, String medicineName, String manufacturer, Integer transactionId, Integer price, Integer medicineId, Integer userId, Integer quantity, Date transactionDate) {
        this.medicineImage = medicineImage;
        this.medicineName = medicineName;
        this.manufacturer = manufacturer;
        this.transactionId = transactionId;
        this.price = price;
        this.medicineId = medicineId;
        this.userId = userId;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public String getMedicineImage() {
        return medicineImage;
    }

    public void setMedicineImage(String medicineImage) {
        this.medicineImage = medicineImage;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
