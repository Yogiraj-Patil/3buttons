package com.example.a3buttons;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3buttons.InternetPack.ConnectivityInterface;
import com.example.a3buttons.InternetPack.ConstantClass;
import com.example.a3buttons.InternetPack.InternetClass;
import com.example.a3buttons.UserData.UserDataClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_Policy extends AppCompatActivity implements ConnectivityInterface {

    TextInputEditText policy_id, customer_name, mobile_no, company_nme, policy_tpe, amt, remainamt;
    TextView start_d, end_d;
    boolean ch;
    private DatePickerDialog.OnDateSetListener mDatesetListiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__policy);


        Toolbar toolbar = (Toolbar) findViewById(R.id.policytoolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);




        MaterialButton backbtn = (MaterialButton) findViewById(R.id.back_button);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        assignAllView();

        MaterialButton submit = (MaterialButton) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });


        start_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch = true;
                datePicker();
            }
        });


        end_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch = false;
                datePicker();
            }
        });


        mDatesetListiner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mon = month + 1;
                if (ch) {
                    start_d.setText(dayOfMonth + "/" + mon + "/" + year);
                } else {
                    end_d.setText(dayOfMonth + "/" + mon + "/" + year);
                }
            }
        };

    }

    private void datePicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDatesetListiner, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void assignAllView() {
        policy_id = (TextInputEditText) findViewById(R.id.policy_idtext);
        customer_name = (TextInputEditText) findViewById(R.id.nametext);
        mobile_no = (TextInputEditText) findViewById(R.id.mobiletext);
        start_d = (TextView) findViewById(R.id.startdatetext);
        end_d = (TextView) findViewById(R.id.enddatetext);
        company_nme = (TextInputEditText) findViewById(R.id.companeytext);
        policy_tpe = (TextInputEditText) findViewById(R.id.policy_typetext);
        amt = (TextInputEditText) findViewById(R.id.amounttext);
        remainamt = (TextInputEditText) findViewById(R.id.remainamounttext);

    }


    private boolean validateFields() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date s_date = null, e_date = null;
        try {
            s_date = sdf.parse(start_d.getText().toString());
            e_date = sdf.parse(end_d.getText().toString());
        } catch (ParseException e) {
            Log.e("DATE FORMAT", "Date Format Exception");
        }

        if (policy_id.getText().toString().length() < 5) {
            policy_id.setError("Enter Valid Lenght");
            return false;
        } else if (customer_name.getText().length() < 4) {
            customer_name.setError("Enter Valid Name");
            return false;
        } else if (mobile_no.getText().length() < 10 || mobile_no.getText().length() > 13) {
            mobile_no.setError("Enter Valid Number");
            return false;
        } else if (start_d.getText().length() < 9) {
            start_d.setError("Enter Valid start Date");
            Log.e("StartDate", "True");
            int a = start_d.getText().length();
            Log.e("Length", a + "");
            return false;
        } else if (end_d.getText().length() < 9) {
            end_d.setError("Enter Valid End Date");
            int a = end_d.getText().length();
            Log.e("Length", a + "");
            Log.e("EndDate", "True");
            return false;
        } else if (company_nme.getText().length() < 3) {
            company_nme.setError("Enter Valid Company Name");
            return false;
        } else if (policy_tpe.getText().length() < 2) {
            policy_tpe.setError("Enter valid Policy Type");
            return false;
        } else if (amt.getText().length() < 2) {
            amt.setError("Enter Valid Amount");
            return false;
        }else if (remainamt.getText().length() < 2) {
            remainamt.setError("Enter Valid Amount");
            return false;
        } else if (e_date.compareTo(s_date) < 0 || e_date.compareTo(s_date) == 0) {
            end_d.setError("Enter valid End Date");
            Log.e("EndDate", "Exception");
            return false;
        } else {
            //xyz@pqrSnackbar.make(findViewById(android.R.id.content), "All is well", Snackbar.LENGTH_LONG).show();
            callingDataInsert();
            return true;
        }

    }

    public void callingDataInsert(){
        String url = ConstantClass.Insert_record+"p_id="+policy_id.getText().toString()+"&c_name="+customer_name.getText().toString()+
                "&mobile="+mobile_no.getText().toString()+"&s_date="+start_d.getText().toString()+"&e_date="+end_d.getText().toString()+
                "&company_name="+company_nme.getText().toString()+"&p_type="+policy_tpe.getText().toString()+"&amount="+amt.getText().toString()+
                "&user_id="+ UserDataClass.getUser_id()+"&remain_amt="+remainamt.getText().toString();

        //GetConnectionClass connectionClass = new GetConnectionClass(this);
        //connectionClass.execute(url);

        InternetClass internetClass = new InternetClass(this,this);
        internetClass.postInsertData(url,"p_id",policy_id.getText().toString(),"c_name",customer_name.getText().toString(),
                "mobile",mobile_no.getText().toString(),"s_date",start_d.getText().toString(),"e_date",end_d.getText().toString(),
                "company_name",company_nme.getText().toString(),"p_type",policy_tpe.getText().toString(),"amount",amt.getText().toString(),
                "user_id",UserDataClass.getUser_id().toString(),"remain_amt",remainamt.getText().toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResultComplete(String Output) {
        //Log.e("String Data",""+Output);
        //Toast.makeText(this,Output,Toast.LENGTH_SHORT).show();
        try {
            JSONObject object = new JSONObject(Output);
            if (object.getBoolean("error")) {
                Toast.makeText(this, object.getString("message"), Toast.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(findViewById(android.R.id.content),object.getString("message"),Snackbar.LENGTH_LONG).show();
                resetAll();
            }
        }catch(JSONException e){Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}

    }

    private void resetAll(){
        policy_id.setText("");customer_name.setText("");mobile_no.setText("");start_d.setText("");end_d.setText("");
        company_nme.setText("");policy_tpe.setText("");amt.setText("");
        startActivity(new Intent(this,DashActivity.class));

    }
}
