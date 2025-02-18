package com.example.myapplication.Models;

public class VoucherOwned {
    private String VoucherID;

    public VoucherOwned(String voucherID) {
        VoucherID = voucherID;
    }

    public String getVoucherID() {
        return VoucherID;
    }

    public void setVoucherID(String voucherID) {
        VoucherID = voucherID;
    }
}