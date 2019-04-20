package com.example.a3buttons.InternetPack;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InternetClass  {
    ConnectivityInterface connectivityInterface =null;
    ErrorPromptInterface errorPromptInterface = null;
    Context ctx;

    public InternetClass(ConnectivityInterface connectivityInterface, ErrorPromptInterface errorPromptInterface, Context context) {
        this.connectivityInterface = connectivityInterface;
        this.errorPromptInterface = errorPromptInterface;
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
                Toast.makeText(ctx, "" + error.getMessage() + " Volley", Toast.LENGTH_SHORT).show();
                errorPromptInterface.onNetworkError(error.getMessage());
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }


    public void postInsertData(String url, final String ...parameters){
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
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(parameters[0],parameters[1]);
                params.put(parameters[2],parameters[3]);
                params.put(parameters[4],parameters[5]);
                params.put(parameters[6],parameters[7]);
                params.put(parameters[8],parameters[9]);
                params.put(parameters[10],parameters[11]);
                params.put(parameters[12],parameters[13]);
                params.put(parameters[14],parameters[15]);
                params.put(parameters[16],parameters[17]);
				params.put(parameters[18],parameters[19]);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}
