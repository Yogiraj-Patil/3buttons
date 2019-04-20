package com.example.a3buttons;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3buttons.SearchData.ItemListRecyclerData;

import com.example.a3buttons.SearchData.RecentFragmentDataAdapter;
import com.example.a3buttons.SearchData.StorageClass;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RecentFragment extends Fragment {

    RecyclerView recyclerView;

    AVLoadingIndicatorView avLoadingIndicatorView;
    ArrayList<ItemListRecyclerData> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_recent, container, false);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.expiringRecycler);
        avLoadingIndicatorView = (AVLoadingIndicatorView) rootview.findViewById(R.id.progressanimation);


        // Inflate the layout for this fragment
        get_data();
        return rootview;
    }

    private void showRecycler() {
        avLoadingIndicatorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


    private void get_data() {
        try {
            JSONArray array = new JSONArray(StorageClass.sortedEvent_data);
            items = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                items.add(new ItemListRecyclerData(obj.getString("Customer_name"), obj.getString("policy_start_date"),
                        obj.getString("policy_end_date"), obj.getString("remain_amount"), obj.getString("policy_amount"),
                        obj.getString("policy_id"), obj.getString("policy_type"), obj.getString("company_name"), obj.getString("mobileno"),
                        1));
            }
        } catch (JSONException e) {
            Log.d("JSONEXCEPTIOn", e.getMessage() + "");
        }

        showingAdapter();
    }

    private void showingAdapter() {
        recyclerView.setPadding(10, 10, 10, 10);
        recyclerView.setHasFixedSize(true);
        RecentFragmentDataAdapter adapter = new RecentFragmentDataAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showRecycler();
    }


}
