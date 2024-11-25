package com.fachter.backend.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class Cliente{

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String latitude;
    private String longitude;
    private String addressDescription;
    private int marmitasQuantity;
    private String suggestedTimeLabel;
    private String suggestedTimeValue;

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuggestedTimeValue() {
        return suggestedTimeValue;
    }

    public void setSuggestedTimeValue(String suggestedTimeValue) {
        this.suggestedTimeValue = suggestedTimeValue;
    }

    public String getSuggestedTimeLabel() {
        return suggestedTimeLabel;
    }

    public void setSuggestedTimeLabel(String suggestedTimeLabel) {
        this.suggestedTimeLabel = suggestedTimeLabel;
    }

    public int getMarmitasQuantity() {
        return marmitasQuantity;
    }

    public void setMarmitasQuantity(int marmitasQuantity) {
        this.marmitasQuantity = marmitasQuantity;
    }

    public String getAddressDescription() {
        return addressDescription;
    }

    public void setAddressDescription(String addressDescription) {
        this.addressDescription = addressDescription;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
