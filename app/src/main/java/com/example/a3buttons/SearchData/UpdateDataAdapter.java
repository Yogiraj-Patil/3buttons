package com.example.a3buttons.SearchData;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3buttons.Add_Policy;
import com.example.a3buttons.DetailActivity;
import com.example.a3buttons.R;
import com.example.a3buttons.Update_Policy;

import java.util.ArrayList;


public class UpdateDataAdapter extends RecyclerView.Adapter<UpdateDataAdapter.ViewHolder> {

    private ArrayList<ItemListRecyclerData> list;

    public UpdateDataAdapter(ArrayList<ItemListRecyclerData> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ItemListRecyclerData itemList = list.get(i);

        viewHolder.card_image.setImageResource(itemList.getImgResource());
        viewHolder.names.setText(itemList.getName());
        viewHolder.policy_id.setText(itemList.getPolicy_id());
        viewHolder.s_date.setText("S Date: " + itemList.getS_date());
        viewHolder.e_date.setText("E Date: " + itemList.getE_date());
        viewHolder.remain_amt.setText("Remain amt: " + itemList.getR_amt());
        viewHolder.amt.setText("Policy Amt: " + itemList.getAmt());

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                StorageClass.data = itemList;
                Intent intent = new Intent(context, Update_Policy.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, i);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_card_layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView card_image;
        public TextView names, policy_id, s_date, e_date, remain_amt, amt;
        public CardView cv;


        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardsView);
            card_image = (ImageView) itemView.findViewById(R.id.card_image);
            names = (TextView) itemView.findViewById(R.id.title_name);
            policy_id = (TextView) itemView.findViewById(R.id.policy_ids);
            s_date = (TextView) itemView.findViewById(R.id.start_date);
            e_date = (TextView) itemView.findViewById(R.id.end_date);
            remain_amt = (TextView) itemView.findViewById(R.id.remainamounts);
            amt = (TextView) itemView.findViewById(R.id.amounts);


        }
    }
}
