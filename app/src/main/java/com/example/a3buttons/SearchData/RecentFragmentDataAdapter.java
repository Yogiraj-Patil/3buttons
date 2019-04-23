package com.example.a3buttons.SearchData;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3buttons.DetailActivity;
import com.example.a3buttons.R;

import java.util.ArrayList;

public class RecentFragmentDataAdapter extends RecyclerView.Adapter<RecentFragmentDataAdapter.ViewHolderClass> {
    StorageClass storageClass;
    private ArrayList<ItemListRecyclerData> list;


    public RecentFragmentDataAdapter(ArrayList<ItemListRecyclerData> list) {
        this.list = list;
        storageClass = new StorageClass();
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_recent_view, viewGroup, false);
        RecentFragmentDataAdapter.ViewHolderClass vh = new RecentFragmentDataAdapter.ViewHolderClass(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass viewHolderClass, final int i) {
        final ItemListRecyclerData data = list.get(i);
        viewHolderClass.nametext.setText(storageClass.capitalizeText(data.getName()));
        viewHolderClass.policy_name.setText(storageClass.capitalizeText(data.getPolicy_type()));
        viewHolderClass.policy_amount.setText("Amount: " + data.getAmt());
        viewHolderClass.end_date.setText("Expiry: " + data.getE_date());

        viewHolderClass.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                StorageClass.data = data;
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, i);
                context.startActivity(intent);
            }
        });

        viewHolderClass.sms_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + data.getContact());
                Intent it = new Intent(Intent.ACTION_SEND, uri);
                it.putExtra("sms_body", "This is SMS Text for Sending");
                view.getContext().startActivity(it);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        public TextView nametext, policy_name, end_date, policy_amount;
        public ImageView sms_icon;
        public CardView cv;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardviewrecent);
            nametext = (TextView) itemView.findViewById(R.id.name_text);
            policy_name = (TextView) itemView.findViewById(R.id.policytype);
            end_date = (TextView) itemView.findViewById(R.id.end_dates);
            policy_amount = (TextView) itemView.findViewById(R.id.amts);
            sms_icon = (ImageView) itemView.findViewById(R.id.sms_icon);
        }
    }


}
