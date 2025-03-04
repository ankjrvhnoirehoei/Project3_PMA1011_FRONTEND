package com.example.myapplication.Adapters;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.Activity_ProductDetail;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_Admin_All_Phones extends RecyclerView.Adapter<Adapter_Admin_All_Phones.ViewHolder>{

    private final Context c;
    private ArrayList<ResPhone> phonesList;

    public Adapter_Admin_All_Phones(Context c, ArrayList<ResPhone> phonesList) {
        this.c = c;
        this.phonesList = phonesList;
    }

    @NonNull
    @Override
    public Adapter_Admin_All_Phones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.rv_admin_all_phones, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResPhone phone = phonesList.get(position);
        holder.txtPhoneName.setText(phone.getPhoneName());
        holder.txtPhonePrice.setText(phone.getPhonePrice() + " $");
        holder.txtPhoneSold.setText(phone.getPhoneSold() + "");
        holder.txtPhoneStock.setText(phone.getPhoneStock() + "");
        holder.btnViewPhoneDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, Activity_ProductDetail.class);
                String phoneID = phonesList.get(position).getPhoneID();
                SharedPreferences sharedPreferences = c.getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("sharedPhoneID", phoneID);
                editor.apply();
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phonesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtPhoneName, txtPhonePrice, txtPhoneStock, txtPhoneSold, btnViewPhoneDetails, btnStopSelling;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPhoneName = itemView.findViewById(R.id.txtPhoneName);
            txtPhonePrice = itemView.findViewById(R.id.txtPhonePrice);
            txtPhoneStock = itemView.findViewById(R.id.txtPhoneStock);
            txtPhoneSold = itemView.findViewById(R.id.txtPhoneSold);
            btnViewPhoneDetails = itemView.findViewById(R.id.btnViewPhoneDetails);
            btnStopSelling = itemView.findViewById(R.id.btnStopSelling);
        }
    }
}
