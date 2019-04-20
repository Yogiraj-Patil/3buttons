package com.example.a3buttons;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.a3buttons.InternetPack.ErrorPromptInterface;
import com.example.a3buttons.InternetPack.GetConnectionClass;
import com.example.a3buttons.InternetPack.ConnectivityInterface;
import com.example.a3buttons.InternetPack.ConstantClass;
import com.example.a3buttons.InternetPack.InternetClass;
import com.example.a3buttons.SearchData.StorageClass;
import com.example.a3buttons.UserData.UserDataClass;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ConnectivityInterface, ErrorPromptInterface {

    TextInputEditText username,password;
    MaterialButton nextbtn;
    RelativeLayout progresslayout;
    AVLoadingIndicatorView progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextInputEditText)findViewById(R.id.username_edit_text);
        password = (TextInputEditText)findViewById(R.id.password_edit_text);
        nextbtn = (MaterialButton)findViewById(R.id.next_button);
        progresslayout = (RelativeLayout)findViewById(R.id.relativeprogressbar);
        progressbar = (AVLoadingIndicatorView)findViewById(R.id.progressanimation);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });

    }


    private boolean getLogin(){
        //GetConnectionClass connectionClass = new GetConnectionClass(this,this);
        String url = ConstantClass.Login+"id="+username.getText().toString()+"&pin="+password.getText().toString();
        //connectionClass.execute(url);
        InternetClass internetClass = new InternetClass(this, this, this);
        internetClass.getData(url);
        hideRelative();
        return true;
    }

    @Override
    public void onResultComplete(String Output) {
        SharedPreferences preferences = getSharedPreferences(ConstantClass.PREF, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        try {
            JSONObject jsonObject = new JSONObject(Output);
            if(jsonObject.getBoolean("error")){
                Snackbar.make(findViewById(android.R.id.content),jsonObject.getString("message"),Snackbar.LENGTH_INDEFINITE).show();
                showRelative();
            }
            if(!jsonObject.getBoolean("error")){
                UserDataClass.setName(jsonObject.getString("name"));
                UserDataClass.setEmail(jsonObject.getString("email"));
                UserDataClass.setUser_id(jsonObject.getString("id"));
                UserDataClass.setMobile(jsonObject.getString("mobile"));
                UserDataClass.setUser_type(jsonObject.getString("type"));
                UserDataClass.setUser_area(jsonObject.getString("area"));
                StorageClass.sortedEvent_data = jsonObject.getJSONArray("recent").toString();
                editor.putString("key", jsonObject.getJSONArray("search").toString());
                editor.apply();
                startActivity(new Intent(this,DashActivity.class));
                showRelative();
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        } catch (JSONException e) {
            Log.e("JSON Exception",e.getMessage());
        }

    }


    private void hideRelative(){
        progresslayout.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);

    }

    private void showRelative(){
        progresslayout.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkError(String message) {
        showRelative();
    }
}
