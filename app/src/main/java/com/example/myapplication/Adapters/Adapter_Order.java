package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.ResBillDetails;
import com.example.myapplication.R;
import java.util.List;

public class Adapter_Order extends RecyclerView.Adapter<Adapter_Order.OrderViewHolder> {
    private List<ResBillDetails> orderList;
    private OnOrderActionListener listener;

    public Adapter_Order(List<ResBillDetails> orderList) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ResBillDetails order = orderList.get(position);
        holder.txtProductName.setText(order.getProductName());
        holder.txtOrderStatus.setText(order.getStatus());
        holder.txtPurchaseDate.setText("Ngày mua: " + order.getPurchaseDate());
        holder.txtQuantity.setText("Số lượng: " + order.getQuantity());

        // Xử lý trạng thái đơn hàng
        if (order.getStatus().equals("Đang vận chuyển")) {
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnWarranty.setVisibility(View.GONE);
            holder.itemView.setAlpha(1.0f);
        } else if (order.getStatus().equals("Đã hủy")) {
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnWarranty.setVisibility(View.GONE);
            holder.itemView.setAlpha(0.5f);
        } else if (order.getStatus().equals("Đã nhận")) {
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnWarranty.setVisibility(View.VISIBLE);
            holder.itemView.setAlpha(1.0f);
        }

        // Xử lý sự kiện hủy đơn
        holder.btnCancel.setOnClickListener(v -> {
            listener.onCancelOrder(order.getBillID());
            Toast.makeText(v.getContext(), "Đã gửi yêu cầu hủy đơn!", Toast.LENGTH_SHORT).show();
        });

        // Xử lý sự kiện bảo hành
        holder.btnWarranty.setOnClickListener(v -> {
            listener.onRequestWarranty(order.getBillID());
            Toast.makeText(v.getContext(), "Đã gửi yêu cầu bảo hành!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void updateList(List<ResBillDetails> newList) {
        orderList = newList;
        notifyDataSetChanged();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtOrderStatus, txtPurchaseDate, txtQuantity;
        Button btnCancel, btnWarranty;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtOrderStatus = itemView.findViewById(R.id.txt_order_status);
            txtPurchaseDate = itemView.findViewById(R.id.txt_purchase_date);
            txtQuantity = itemView.findViewById(R.id.txt_quantity);
            btnCancel = itemView.findViewById(R.id.btn_cancel_order);
            btnWarranty = itemView.findViewById(R.id.btn_warranty);
        }
    }

    public interface OnOrderActionListener {
        void onCancelOrder(String billID);
        void onRequestWarranty(String billID);
    }
}
