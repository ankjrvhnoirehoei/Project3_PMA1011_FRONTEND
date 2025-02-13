package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderName.setText(order.getOrderName());
        holder.tvPrice.setText(order.getPrice() + "đ");
        holder.tvStatus.setText(order.getStatus());

        // Đổi màu trạng thái
        switch (order.getStatus()) {
            case "Đang vận chuyển":
                holder.tvStatus.setTextColor(0xFF007BFF); // Màu xanh
                break;
            case "Đã hủy":
                holder.tvStatus.setTextColor(0xFFFF0000); // Màu đỏ
                holder.itemView.setAlpha(0.5f); // Làm mờ đơn hàng bị hủy
                break;
            case "Đã nhận":
                holder.tvStatus.setTextColor(0xFF28A745); // Màu xanh lá
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderName, tvPrice, tvStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
