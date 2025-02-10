package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.Activity_User_Bills_Full;
import com.example.myapplication.Models.ResBillsFull;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Admin_All_Users extends RecyclerView.Adapter<Adapter_Admin_All_Users.ViewHolder> {

    private final Context c;
    private ArrayList<ResUser> listUser;

    public Adapter_Admin_All_Users(Context c, ArrayList<ResUser> listUser) {
        this.c = c;
        this.listUser = listUser;
    }
    @NonNull
    @Override
    public Adapter_Admin_All_Users.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.rv_admin_all_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Admin_All_Users.ViewHolder holder, int position) {
        ResUser user = listUser.get(position);
        String userAvatar = user.getAvatarImg();

        if (userAvatar != null && !userAvatar.isEmpty()) {
            // Get the first image, which is a base64 encoded string
            String base64Image = userAvatar;

            // Check if the base64 string contains metadata and strip it
            if (base64Image.startsWith("data:image")) {
                base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
            }

            // Convert the base64 string into a Bitmap
            try {
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                // Set the decoded Bitmap to the ImageView
                holder.imgAvatar.setImageBitmap(decodedBitmap);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                // If there's an error decoding the image, use the default "user.png"
                holder.imgAvatar.setImageResource(R.drawable.user);
            }
        } else {
            // If there are no images, use the default "user.png"
            holder.imgAvatar.setImageResource(R.drawable.user);
        }

        holder.txtUsername.setText(user.getUsername());
        holder.txtPassword.setText(user.getPassword());
        holder.txtPhoneNumber.setText(user.getPhoneNumber());
        holder.txtAddress.setText(user.getAddress());

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgAvatar;
        TextView txtUsername, txtPassword, txtPhoneNumber, txtAddress, btnViewSales, btnStarredUser, btnBanUser;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            btnViewSales = itemView.findViewById(R.id.btnViewSales);
            btnStarredUser = itemView.findViewById(R.id.btnStarredUser);
            btnBanUser = itemView.findViewById(R.id.btnBanUser);

            // Setting listeners for each button
            btnViewSales.setOnClickListener(this);
            btnStarredUser.setOnClickListener(this);
            btnBanUser.setOnClickListener(this);


//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                ResUser user = listUser.get(position);

                // Retrieve token from SharedPreferences
                SharedPreferences preferences = c.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                String token = preferences.getString("AuthToken", null);  // Retrieve the token

                if (token == null) {
                    // Handle case where token is missing or expired
                    Toast.makeText(c, "Authentication token not found!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Initialize Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RetrofitService.BASE_URL)  // Replace with your actual base URL
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // Create the API service
                RetrofitService apiService = retrofit.create(RetrofitService.class);

                // Handle different button clicks based on the view clicked
                if (v.getId() == btnViewSales.getId()) {
                    // Call the API to get the bills for the selected user
                    Log.d( "Fetching bills for user: ", user.getUserID());
                    Call<ResBillsFull> call = apiService.getUserBills("Bearer " + token, user.getUserID());
                    call.enqueue(new Callback<ResBillsFull>() {
                        @Override
                        public void onResponse(Call<ResBillsFull> call, Response<ResBillsFull> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ResBillsFull billList = response.body();
                                Log.d("API_SUCCESS", "Fetched bills: " + response.body().getTotalBill());

                                // Pass the list of bills to the next activity
                                Intent intent = new Intent(c, Activity_User_Bills_Full.class);
                                intent.putExtra("bills", billList.getBills());  // Assuming getBills() returns a list
                                c.startActivity(intent);
                            } else {
                                Toast.makeText(c, "Failed to fetch bills: " + response.message(), Toast.LENGTH_SHORT).show();
                                Log.d("Failed to fetch bills: ", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResBillsFull> call, Throwable t) {
                            Log.e("API_FAILURE", "Error fetching bills", t);
                            Toast.makeText(c, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (v.getId() == btnStarredUser.getId()) {
                    // Toggle starredUser field (1 if 0, 0 if 1)
                    int newStarredStatus = user.getStarredUser() == 0 ? 1 : 0;

                    // Prepare the partMap to update the starredUser field
                    Map<String, RequestBody> partMap = new HashMap<>();
                    partMap.put("userID", RequestBody.create(MediaType.parse("text/plain"), user.getUserID()));
                    partMap.put("starredUser", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(newStarredStatus)));

                    // Call the API
                    Call<ResUser> call = apiService.editUserInfo("Bearer " + token, partMap, null);
                    call.enqueue(new Callback<ResUser>() {
                        @Override
                        public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                            if (response.isSuccessful()) {
                                // Update the list and UI accordingly
                                listUser.get(position).setStarredUser(newStarredStatus);
                                notifyItemChanged(position);  // Notify the adapter to refresh UI
                                Toast.makeText(c, "User starred status updated!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(c, "Failed to update starred status!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResUser> call, Throwable t) {
                            Toast.makeText(c, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (v.getId() == btnBanUser.getId()) {
                    // Toggle starredUser between 0 and 1
                    int newBannedStatus = user.getBannedUser() == 0 ? 1 : 0;

                    // Prepare the partMap to update the bannedUser field
                    Map<String, RequestBody> partMap = new HashMap<>();
                    partMap.put("userID", RequestBody.create(MediaType.parse("text/plain"), user.getUserID()));
                    partMap.put("bannedUser", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(newBannedStatus)));

                    // Call the API
                    Call<ResUser> call = apiService.editUserInfo("Bearer " + token, partMap, null);
                    call.enqueue(new Callback<ResUser>() {
                        @Override
                        public void onResponse(Call<ResUser> call, Response<ResUser> response) {
                            if (response.isSuccessful()) {
                                // Update the list and UI accordingly
                                listUser.get(position).setBannedUser(newBannedStatus);
                                notifyItemChanged(position);  // Notify the adapter to refresh UI
                                Toast.makeText(c, "User banned status updated!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(c, "Failed to update banned status!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResUser> call, Throwable t) {
                            Toast.makeText(c, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }

    }
}
