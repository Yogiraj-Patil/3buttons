package com.example.a3buttons;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a3buttons.InternerPack.ConnectionClass;
import com.example.a3buttons.InternerPack.ConnectivityInterface;
import com.example.a3buttons.InternerPack.ConstantClass;
import com.example.a3buttons.UserData.userDataClass;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ConnectivityInterface {

    TextInputEditText username,password;
    MaterialButton nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextInputEditText)findViewById(R.id.username_edit_text);
        password = (TextInputEditText)findViewById(R.id.password_edit_text);
        nextbtn = (MaterialButton)findViewById(R.id.next_button);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });

    }


    private boolean getLogin(){
        ConnectionClass connectionClass = new ConnectionClass(this);
        String url = ConstantClass.Login+"id="+username.getText().toString()+"&pin="+password.getText().toString();
        connectionClass.execute(url);
        return true;
    }

    @Override
    public void onResultComplete(String Output) {
        try {
            JSONObject jsonObject = new JSONObject(Output);
            if(jsonObject.getBoolean("error")){
                Snackbar.make(findViewById(android.R.id.content),jsonObject.getString("message"),Snackbar.LENGTH_INDEFINITE).show();
            }
            if(!jsonObject.getBoolean("error")){
                userDataClass.setName(jsonObject.getString("name"));
                userDataClass.setEmail(jsonObject.getString("email"));
                userDataClass.setUser_id(jsonObject.getString("id"));
                userDataClass.setMobile(jsonObject.getString("mobile"));
                userDataClass.setUser_type(jsonObject.getString("type"));
                userDataClass.setUser_area(jsonObject.getString("area"));
                startActivity(new Intent(this,DashActivity.class));
                overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                finish();
            }
        } catch (JSONException e) {
            Log.e("JSON Exception",e.getMessage());
        }

    }


    private void customeDialogbox(String message){

    }
}
