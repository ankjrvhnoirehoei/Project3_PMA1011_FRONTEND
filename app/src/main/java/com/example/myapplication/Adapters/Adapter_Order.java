package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.Order;
import com.example.myapplication.R;
import java.util.List;

public class Adapter_Order extends RecyclerView.Adapter<Adapter_Order.OrderViewHolder> {
    private List<Order> orderList;

    public Adapter_Order(List<Order> orderList) {
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
        holder.txtProductName.setText(order.getProductName());
        holder.txtOrderStatus.setText(order.getStatus());
        holder.txtPurchaseDate.setText("Ngày mua: " + order.getPurchaseDate());
        holder.txtQuantity.setText("Số lượng: " + order.getQuantity());

        // Kiểm tra trạng thái đơn hàng để hiển thị nút phù hợp
        if (order.getStatus().equals("Đang vận chuyển")) {
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnConfirm.setVisibility(View.GONE);
            holder.itemView.setAlpha(1.0f);
        } else if (order.getStatus().equals("Đã hủy")) {
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnConfirm.setVisibility(View.GONE);
            holder.itemView.setAlpha(0.5f); // Làm mờ đơn hàng
        } else if (order.getStatus().equals("Đã nhận")) {
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateList(List<Order> newList) {
        orderList = newList;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtOrderStatus, txtPurchaseDate, txtQuantity;
        Button btnCancel, btnConfirm;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtOrderStatus = itemView.findViewById(R.id.txt_order_status);
            txtPurchaseDate = itemView.findViewById(R.id.txt_purchase_date);
            txtQuantity = itemView.findViewById(R.id.txt_quantity);
            btnCancel = itemView.findViewById(R.id.btn_cancel_order);
            btnConfirm = itemView.findViewById(R.id.btn_confirm_order);
        }
    }
}
