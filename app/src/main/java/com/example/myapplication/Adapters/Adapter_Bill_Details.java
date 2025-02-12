package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_Bill_Details extends RecyclerView.Adapter<Adapter_Bill_Details.ViewHolder> {
    private final Context context;
    private ArrayList<ResBillDetails> listBillDetails;

    public Adapter_Bill_Details(Context context, ArrayList<ResBillDetails> listBillDetails) {
        this.context = context;
        this.listBillDetails = listBillDetails;
    }

    @NonNull
    @Override
    public Adapter_Bill_Details.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_bill_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Bill_Details.ViewHolder holder, int position) {
        ResBillDetails billDetails = listBillDetails.get(position);
        holder.txtBillID.setText("Bill ID: " + billDetails.getBillID());
        holder.txtPhoneID.setText("Phone ID: " + billDetails.getPhoneID());
        holder.txtQuantity.setText("Quantity: " + billDetails.getQuantity());
        holder.txtStatus.setText("Status: " + billDetails.getStatus());
        holder.txtDate.setText("Date created: " + billDetails.getDateCreated());
    }

    @Override
    public int getItemCount() {
        return listBillDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBillID, txtPhoneID, txtQuantity, txtStatus, txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBillID = itemView.findViewById(R.id.txtBillID);
            txtPhoneID = itemView.findViewById(R.id.txtPhoneID);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
