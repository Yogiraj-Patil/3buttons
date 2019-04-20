package com.example.a3buttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3buttons.SearchData.ItemListRecyclerData;
import com.example.a3buttons.SearchData.StorageClass;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "position";
    ItemListRecyclerData list;
    TextView name, contact, p_number, p_end, p_start, p_amt, p_remain, p_type, p_companey;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        list = StorageClass.data;

        cardView = (CardView) findViewById(R.id.carddetails);
        name = (TextView) findViewById(R.id.username);
        contact = (TextView) findViewById(R.id.contact);
        p_number = (TextView) findViewById(R.id.p_number);
        p_end = (TextView) findViewById(R.id.p_end);
        p_amt = (TextView) findViewById(R.id.amts);
        p_companey = (TextView) findViewById(R.id.insu_comp);
        p_type = (TextView) findViewById(R.id.p_ype);
        p_remain = (TextView) findViewById(R.id.ramts);
        p_start = (TextView) findViewById(R.id.p_start);
        setTexts();
        animate();
    }

    private void animate() {
        Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_bounce);
        cardView.setAnimation(animation);
    }

    private void setTexts() {
        name.setText(list.getName());
        contact.setText(list.getContact());
        p_number.setText(list.getPolicy_id());
        p_type.setText(list.getPolicy_type());
        p_end.setText(list.getE_date());
        p_start.setText(list.getS_date());
        p_amt.setText(list.getAmt());
        p_remain.setText(list.getR_amt());
        p_companey.setText(list.getCompaney());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
