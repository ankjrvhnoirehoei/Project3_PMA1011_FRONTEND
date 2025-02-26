package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Phones extends RecyclerView.Adapter<Adapter_Phones.PhoneViewHolder> {

    private List<ResPhone> phoneList;

    public Adapter_Phones(List<ResPhone> phoneList) {
        this.phoneList = (phoneList != null) ? phoneList : new ArrayList<>(); // Đảm bảo không null
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        ResPhone phone = phoneList.get(position);
        if (phone != null) {
            holder.phoneName.setText(phone.getPhoneName());
            holder.phoneBrand.setText(phone.getPhoneBrand());
            holder.phonePrice.setText(String.format("$%.2f", phone.getPhonePrice()));
            holder.phoneSold.setText(String.format("Sold: %d", phone.getPhoneSold()));
        }
    }

    @Override
    public int getItemCount() {
        return (phoneList != null) ? phoneList.size() : 0; // Tránh lỗi NullPointerException
    }

    // Cập nhật dữ liệu một cách tối ưu
    public void updateData(List<ResPhone> newPhoneList) {
        if (newPhoneList == null || newPhoneList.isEmpty()) {
            phoneList.clear();
            notifyDataSetChanged();
        } else {
            phoneList.clear();
            phoneList.addAll(newPhoneList);
            notifyItemRangeInserted(0, newPhoneList.size()); // Giúp hiệu suất tốt hơn
        }
    }

    public static class PhoneViewHolder extends RecyclerView.ViewHolder {
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
