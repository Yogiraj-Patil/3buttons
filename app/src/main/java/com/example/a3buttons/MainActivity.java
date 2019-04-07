package com.example.a3buttons;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.a3buttons.InternerPack.ConnectionClass;
import com.example.a3buttons.InternerPack.ConnectivityInterface;
import com.example.a3buttons.InternerPack.ConstantClass;

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

        Log.i("Output",Output);

    }
}
