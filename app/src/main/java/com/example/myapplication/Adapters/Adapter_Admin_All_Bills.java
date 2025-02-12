package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.Activity_Admin_All_BillDetails;
import com.example.myapplication.Activities.Activity_User_Bills_Full;
import com.example.myapplication.Models.ResBill;
import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.Models.ResBillsFull;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Admin_All_Bills extends RecyclerView.Adapter<Adapter_Admin_All_Bills.ViewHolder> {

    private final Context c;
    private ArrayList<ResBill> listDetails;

    public Adapter_Admin_All_Bills(Context c, ArrayList<ResBill> listDetails) {
        this.c = c;
        this.listDetails = listDetails;
    }

    @NonNull
    @Override
    public Adapter_Admin_All_Bills.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.rv_admin_all_bills, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Admin_All_Bills.ViewHolder holder, int position) {
        ResBill resBill = listDetails.get(position);
        holder.txtBillID.setText(resBill.getBillID());
        holder.txtUsername.setText(resBill.getUserID());
        holder.txtBillTotal.setText(resBill.getTotal());
    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtBillID, txtUsername, txtBillTotal, btnViewBillDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBillID = itemView.findViewById(R.id.txtBillID);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtBillTotal = itemView.findViewById(R.id.txtBillTotal);
            btnViewBillDetails = itemView.findViewById(R.id.btnViewBillDetails);

            btnViewBillDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                ResBill resBill = listDetails.get(position);

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

                if (v.getId() == btnViewBillDetails.getId()) {
                    // Call the API to get all bills from a user
                    Call<List<ResBillDetails>> call = apiService.getBillDetails(token, resBill.getBillID());
                    call.enqueue(new Callback<List<ResBillDetails>>() {
                        @Override
                        public void onResponse(Call<List<ResBillDetails>> call, Response<List<ResBillDetails>> response) {
                            if (response.isSuccessful()) {
                                List<ResBillDetails> resBillDetails = response.body();
                                if (resBillDetails != null) {
                                    List<ResBillDetails> billList = response.body();
                                    if (billList != null && billList.size() > 0) {
                                        // Handle the case where the response is successful
                                        Log.d("API_SUCCESS", "Fetched bills: " + billList.size());
                                        // Pass the list of bills to the next activity
                                        Intent intent = new Intent(c, Activity_Admin_All_BillDetails.class);
                                        intent.putExtra("billDetails", (Serializable) billList);  // Assuming getBills() returns a list
                                        c.startActivity(intent);
                                    } else {
                                        // Handle the case where the response is empty
                                        Log.e("API_SUCCESS", "No bills found!");
                                    }
                                }
                            } else {
                                // Handle the case where the response is unsuccessful
                                Toast.makeText(c, "Failed to get bill details!", Toast.LENGTH_SHORT).show();
                                Log.d("Failed to fetch bill details: ", response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ResBillDetails>> call, Throwable t) {
                            // Handle the case where the API call fails
                            Toast.makeText(c, "Failed to get bill details!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
