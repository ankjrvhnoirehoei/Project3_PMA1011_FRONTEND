package com.example.myapplication.Models;

import java.util.ArrayList;

public class ResBillsFull {
    private int totalBill; // Represents the total number of bills
    private ArrayList<ResBill> bills; // Represents the list of bills

    public ResBillsFull(int totalBill, ArrayList<ResBill> bills) {
        this.totalBill = totalBill;
        this.bills = bills;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public ArrayList<ResBill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<ResBill> bills) {
        this.bills = bills;
    }
}
