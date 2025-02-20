package com.example.myapplication.Adapters;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Comments;
import com.example.myapplication.Models.ResComment;
import com.example.myapplication.Models.ResUser;
import com.example.myapplication.Others.RetrofitService;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_All_Comments extends RecyclerView.Adapter<Adapter_All_Comments.Viewholder> {

    private final Context c;
    private ArrayList<Comments> commentList;

    public Adapter_All_Comments(Context c, ArrayList<Comments> commentList) {
        this.c = c;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public Adapter_All_Comments.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.rv_product_comment_section, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.tv_date.setText(commentList.get(position).getDateCreated());
        holder.tv_comment.setText(commentList.get(position).getCommentText());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // get token, phoneId and userID from SharedPreferences
        SharedPreferences sharedPreferences = c.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String token = "Bearer " + sharedPreferences.getString("AuthToken", null);
        String phoneID = sharedPreferences.getString("sharedPhoneID", null);
        String userID = sharedPreferences.getString("loginUserID", null);

        retrofitService.getAllUsers(token).enqueue(new Callback<List<ResUser>>() {
            @Override
            public void onResponse(Call<List<ResUser>> call, Response<List<ResUser>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ResUser> userList = response.body();
                    for(int i = 0; i<userList.size(); i++){
                        if(userList.get(i).getUserID().equals(commentList.get(position).getUserID())){
                            holder.tv_username.setText(userList.get(i).getUsername());
                            String userAvatar = userList.get(i).getAvatarImg();
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
                                    holder.img_account.setImageBitmap(decodedBitmap);
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                    // If there's an error decoding the image, use the default "user.png"
                                    holder.img_account.setImageResource(R.drawable.user);
                                }
                            } else {
                                // If there are no images, use the default "user.png"
                                holder.img_account.setImageResource(R.drawable.user);
                            }
                        }
                    }
                } else {
                    Log.e("Response", "Error: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ResUser>> call, Throwable throwable) {
                Log.e("API Failure", "Error: " + throwable.getMessage(), throwable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView img_account;
        TextView tv_username, tv_date, tv_comment;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            img_account = itemView.findViewById(R.id.img_account);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_comment = itemView.findViewById(R.id.tv_comment);
        }
    }
}
