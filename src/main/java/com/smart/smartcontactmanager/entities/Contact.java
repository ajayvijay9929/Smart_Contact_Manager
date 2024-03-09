package com.smart.smartcontactmanager.entities;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cId;
    private String name;
    private String secondName;
    private String work;
    private String email;
    private String phone;
    private byte[] image;
    @Column(length = 500)
    private String description;

    @ManyToOne
    @JsonIgnore
    private User user;

    public int getcId() {
        return cId;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getWork() {
        return work;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact() {
    }

    public void setImage(byte[] imageBytes) {
        this.image = imageBytes;
    }

    public byte[] getImage() {
        return image;
    }

    public Contact(String name, String secondName, String work, String email, String phone, byte[] image,
            String description, User user) {
        this.cId = cId;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.description = description;
        this.user = user;
    }

    public void setAllContact(int cId , String name, String secondName, String work, String email, String phone,
            String description, User user) {
        this.cId = cId;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.user = user;
    }
}
