package com.example.bluejackpharmacyv2.models;

import java.util.Date;

public class Transaction {
    Integer transactionId, medicineId, userId, quantity;
    Date transactionDate;

    public Transaction(Integer transactionId, Integer medicineId, Integer userId, Integer quantity, Date transactionDate) {
        this.transactionId = transactionId;
        this.medicineId = medicineId;
        this.userId = userId;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
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
