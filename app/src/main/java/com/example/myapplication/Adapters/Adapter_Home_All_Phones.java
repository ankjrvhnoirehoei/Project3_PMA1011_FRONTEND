package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ResLogin;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_Home_All_Phones extends RecyclerView.Adapter<Adapter_Home_All_Phones.ViewHolder> {
    private final Context c;
    private ArrayList<ResPhone> listPhone;
    private final OnClickListener listener;

    public Adapter_Home_All_Phones(Context c, ArrayList<ResPhone> listPhone, OnClickListener listener) {
        this.c = c;
        this.listPhone = listPhone;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter_Home_All_Phones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.rv_home_all_phones, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Home_All_Phones.ViewHolder holder, int position) {
        ResPhone phone = listPhone.get(position);
        ArrayList<String> phoneImages = phone.getImage();

        if (phoneImages != null && !phoneImages.isEmpty()) {
            // Get the first image, which is a base64 encoded string
            String base64Image = phoneImages.get(0);

            // Check if the base64 string contains metadata and strip it
            if (base64Image.startsWith("data:image")) {
                base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
            }

            // Convert the base64 string into a Bitmap
            try {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                // Set the decoded Bitmap to the ImageView
                holder.imgPhone.setImageBitmap(decodedBitmap);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                // If there's an error decoding the image, use the default "no_image.png"
                holder.imgPhone.setImageResource(R.drawable.no_image);
            }
        } else {
            // If there are no images, use the default "no_image.png"
            holder.imgPhone.setImageResource(R.drawable.no_image);
        }

        holder.txtPhoneName.setText(phone.getPhoneName());
        holder.txtPhonePrice.setText(phone.getPhonePrice() + " VND");
    }


    @Override
    public int getItemCount() {
        return listPhone.size();
    }

    public interface OnClickListener{
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhone;
        TextView txtPhoneName, txtPhonePrice;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imgPhone = itemView.findViewById(R.id.imgPhone);
            txtPhoneName = itemView.findViewById(R.id.txtPhoneName);
            txtPhonePrice = itemView.findViewById(R.id.txtPhonePrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            if(position != RecyclerView.NO_POSITION){
//            }
            listener.onClick(v, getAdapterPosition());
        }
    }
}
