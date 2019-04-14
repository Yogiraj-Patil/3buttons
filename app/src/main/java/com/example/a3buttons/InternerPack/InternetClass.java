package com.example.a3buttons.InternerPack;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.StringReader;

public class InternetClass  {
    ConnectivityInterface connectivityInterface =null;
    Context ctx;
    public InternetClass(ConnectivityInterface connectivityInterface, Context context){
        this.connectivityInterface = connectivityInterface;
        ctx = context;
    }

    public void getData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                connectivityInterface.onResultComplete(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx,""+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}
