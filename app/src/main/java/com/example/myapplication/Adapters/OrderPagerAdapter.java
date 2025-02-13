package com.example.myapplication.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.Activities.Activity_OrderHistory;
import com.example.myapplication.Fragments.OrderListFragment;
import com.example.myapplication.Models.Order;
import java.util.List;

public class OrderPagerAdapter extends FragmentStateAdapter {
    private List<List<Order>> categorizedOrders;

    public OrderPagerAdapter(@NonNull Activity_OrderHistory fragment, List<List<Order>> categorizedOrders) {
        super(fragment);
        this.categorizedOrders = categorizedOrders;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OrderListFragment.newInstance(categorizedOrders.get(position));
    }

    @Override
    public int getItemCount() {
        return categorizedOrders.size();
    }
}
