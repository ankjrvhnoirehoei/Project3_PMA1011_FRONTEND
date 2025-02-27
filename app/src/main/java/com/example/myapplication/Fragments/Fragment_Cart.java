package com.example.myapplication.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.R;

public class Fragment_Cart extends Fragment {

    // Default constructor for the fragment
    public Fragment_Cart() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of this fragment using provided parameters
    public static Fragment_Cart newInstance(String param1, String param2) {
        Fragment_Cart fragment = new Fragment_Cart();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }


}
