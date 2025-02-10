package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ResBill;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_User_All_Bills extends RecyclerView.Adapter<Adapter_User_All_Bills.ViewHolder> {

    private final Context context;
    private ArrayList<ResBill> listBills;

    public Adapter_User_All_Bills(Context context, ArrayList<ResBill> listBills) {
        this.context = context;
        this.listBills = listBills;
    }

    @NonNull
    @Override
    public Adapter_User_All_Bills.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_user_bills, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_All_Bills.ViewHolder holder, int position) {
        ResBill bill = listBills.get(position);
        holder.txtBillID.setText("Bill ID: " + bill.getBillID());
        holder.txtUserID.setText("User ID: " + bill.getUserID());
        holder.txtTotal.setText("Total: $" + bill.getTotal());
    }

    @Override
    public int getItemCount() {
        return listBills.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBillID, txtUserID, txtTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBillID = itemView.findViewById(R.id.txtBillID);
            txtUserID = itemView.findViewById(R.id.txtUserID);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }
    }
}
