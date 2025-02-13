package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import org.json.JSONObject;

public class Activity_Product_Detail extends AppCompatActivity {

    private TextView productNameTextView;
    private TextView productBrandTextView;
    private TextView productTypeTextView;
    private TextView productDescriptionTextView;
    private ImageView productImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ các TextViews và ImageView
        productNameTextView = findViewById(R.id.productName);
        productBrandTextView = findViewById(R.id.productBrand);
        productTypeTextView = findViewById(R.id.productType);
        productDescriptionTextView = findViewById(R.id.productDescription);
        productImageView = findViewById(R.id.productImage);

        // Lấy productId từ Intent
        String productId = getIntent().getStringExtra("product_id");

        if (productId != null) {
            fetchProductDetails(productId);
        } else {
            Toast.makeText(this, "Product ID is missing", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchProductDetails(String productId) {
        String url = "http://your-server-url/products/" + productId;

        // Gửi yêu cầu GET tới server để lấy thông tin chi tiết sản phẩm
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Phân tích cú pháp JSON
                        JSONObject product = new JSONObject(response);
                        String name = product.getString("name");
                        String brand = product.getString("brand");
                        String type = product.getString("type");
                        String description = product.getString("description");
                        String imageUrl = product.getString("image");

                        // Hiển thị thông tin sản phẩm
                        productNameTextView.setText(name);
                        productBrandTextView.setText("Brand: " + brand);
                        productTypeTextView.setText("Type: " + type);
                        productDescriptionTextView.setText("Description: " + description);

                        // Tải hình ảnh sản phẩm từ URL
                        loadProductImage(imageUrl);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Activity_Product_Detail.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(Activity_Product_Detail.this, "Request failed", Toast.LENGTH_SHORT).show();
                });

        // Thêm yêu cầu vào hàng đợi của Volley
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Phương thức tải hình ảnh từ URL
    private void loadProductImage(String imageUrl) {
        // Sử dụng ImageRequest của Volley để tải hình ảnh
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                response -> productImageView.setImageBitmap(response),  // Đặt hình ảnh vào ImageView
                0, 0, null, null,
                error -> productImageView.setImageResource(R.drawable.error_image));  // Hình ảnh lỗi nếu có

        // Thêm yêu cầu hình ảnh vào hàng đợi của Volley
        Volley.newRequestQueue(this).add(imageRequest);
    }
}
