package com.example.segundoauqui.savingdata;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.Blob;

/**
 * Created by segundoauqui on 8/8/17.
 */

public class MyContacs {
    private String etName;
    private String etPhoneNumber;
    private String etEmail;
    private String etCompany;
    private String etCity;
    byte[] b;
    private int id;

    public String getEtName() {
        return etName;
    }

    public void setEtName(String etName) {
        this.etName = etName;
    }

    public String getEtPhoneNumber() {
        return etPhoneNumber;
    }

    public void setEtPhoneNumber(String etPhoneNumber) {
        this.etPhoneNumber = etPhoneNumber;
    }

    public String getEtEmail() {
        return etEmail;
    }

    public void setEtEmail(String etEmail) {
        this.etEmail = etEmail;
    }

    public String getEtCompany() {
        return etCompany;
    }

    public void setEtCompany(String etCompany) {
        this.etCompany = etCompany;
    }

    public String getEtCity() {
        return etCity;
    }

    public void setEtCity(String etCity) {
        this.etCity = etCity;
    }

    public byte[] getB() {
        return b;
    }

    public void setB(byte[] b) {
        this.b = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyContacs(int id, String etName, String etPhoneNumber, String etEmail, String etCompany, String etCity, byte[] b) {
        this.etName = etName;
        this.etPhoneNumber = etPhoneNumber;
        this.etEmail = etEmail;
        this.etCompany = etCompany;
        this.etCity = etCity;
        this.b = b;
        this.id = id;
    }
}