package com.example.bluejackpharmacyv2.models;

public class medicine {
    Integer medicineId, price;
    String medicineName, manufacturer, image, description;

    public medicine(Integer medicineId, Integer price, String medicineName, String manufacturer, String image, String description) {
        this.medicineId = medicineId;
        this.price = price;
        this.medicineName = medicineName;
        this.manufacturer = manufacturer;
        this.image = image;
        this.description = description;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
