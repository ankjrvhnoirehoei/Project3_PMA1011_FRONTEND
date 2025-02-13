package com.example.myapplication.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.ResPhone;
import com.example.myapplication.R;

import java.util.List;

public class Activity_ProductAdapter extends RecyclerView.Adapter<Activity_ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<ResPhone> products;
    private OnItemClickListener listener;

    public Activity_ProductAdapter(Context context, List<ResPhone> products, OnItemClickListener listener) {
        this.context = context;
        this.products = products;
        this.listener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout item_product.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        // Lấy sản phẩm tại vị trí
        ResPhone product = products.get(position);

        // Gán tên sản phẩm vào TextView
        holder.productName.setText(product.getPhoneName());

        // Gán giá sản phẩm vào TextView
        holder.productPrice.setText(product.getPhonePrice() + " VND");

        // Nếu bạn không dùng Picasso, bạn có thể đặt một hình ảnh mặc định từ drawable
        holder.productImage.setImageResource(R.drawable.placeholder);  // Hình ảnh mặc định

        // Thiết lập sự kiện khi người dùng click vào item
        holder.itemView.setOnClickListener(v -> listener.onItemClick(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // Phương thức để cập nhật dữ liệu mới vào Adapter
    public void updateData(List<ResPhone> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();  // Làm mới RecyclerView
    }

    // Interface để xử lý sự kiện click item
    public interface OnItemClickListener {
        void onItemClick(ResPhone product);
    }

    // ViewHolder để giữ các tham chiếu đến các UI component trong mỗi item
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            // Khởi tạo các views trong layout item_product.xml
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
