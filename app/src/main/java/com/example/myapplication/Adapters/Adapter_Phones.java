package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.R;

import java.util.List;

public class Adapter_Phones extends RecyclerView.Adapter<Adapter_Phones.PhoneViewHolder> {

    private List<ResPhone> phoneList;

    public Adapter_Phones(List<ResPhone> phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public PhoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhoneViewHolder holder, int position) {
        ResPhone phone = phoneList.get(position);
        holder.phoneName.setText(phone.getPhoneName());
        holder.phoneBrand.setText(phone.getPhoneBrand());
        holder.phonePrice.setText(String.format("$%.2f", phone.getPhonePrice()));
        holder.phoneSold.setText(String.valueOf(phone.getPhoneSold()));
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder {
        TextView phoneName, phoneBrand, phonePrice, phoneSold;

        public PhoneViewHolder(View itemView) {
            super(itemView);
            phoneName = itemView.findViewById(R.id.phoneName);
            phoneBrand = itemView.findViewById(R.id.phoneBrand);
            phonePrice = itemView.findViewById(R.id.phonePrice);
            phoneSold = itemView.findViewById(R.id.phoneSold);
        }
    }
}
