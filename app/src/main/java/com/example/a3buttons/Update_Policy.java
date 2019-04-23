package com.example.a3buttons;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3buttons.InternetPack.ConnectivityInterface;
import com.example.a3buttons.InternetPack.ConstantClass;
import com.example.a3buttons.InternetPack.ErrorPromptInterface;
import com.example.a3buttons.InternetPack.InternetClass;
import com.example.a3buttons.SearchData.ItemListRecyclerData;
import com.example.a3buttons.SearchData.StorageClass;
import com.example.a3buttons.UserData.UserDataClass;
import com.wang.avi.AVLoadingIndicatorView;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Update_Policy extends AppCompatActivity implements ConnectivityInterface, ErrorPromptInterface {

    private final int requestCODE = 1;
    ItemListRecyclerData list;
    TextInputEditText policy_id, customer_name, mobile_no, company_nme, policy_tpe, amt, remainamt;
    TextInputLayout policy_idlayout;
    TextView start_d, end_d;
    ScrollView scrollView;
    boolean ch;
    AVLoadingIndicatorView loadingIndicatorView;
    ImageView attachemntView;
    long time = System.currentTimeMillis();
    private DatePickerDialog.OnDateSetListener mDatesetListiner;
    private Uri uri = null;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__policy);


        Toolbar toolbar = (Toolbar) findViewById(R.id.policytoolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Update Policy Details");

        AppCompatTextView textView = (AppCompatTextView) findViewById(R.id.fields);
        textView.setText("Update Your Fields");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);


        list = StorageClass.data;


        MaterialButton backbtn = (MaterialButton) findViewById(R.id.back_button);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        assignAllView();
        setAllData();

        MaterialButton submit = (MaterialButton) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

        submit.setText("Update");


        start_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch = true;
                hideSoftKeyboard(start_d);
                datePicker();
            }
        });


        end_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch = false;
                hideSoftKeyboard(end_d);
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

        attachemntView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askpermissions();
            }
        });


    }


    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void askpermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openSelectBox();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openSelectBox();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Please Allow Permission to upload Recipt", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void openSelectBox() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCODE == requestCode && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                attachemntView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getPath(Uri uri) {
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String doc_id = cursor.getString(0);
            doc_id = doc_id.substring(doc_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Images.Media._ID + " = ? ", new String[]{doc_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return path;
        } else {
            return null;
        }
    }


    private void datePicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //Theme_Holo_Light_Dialog_MinWidth
        DatePickerDialog dialog = new DatePickerDialog(this, mDatesetListiner, year, month, day);
        dialog.getDatePicker().setSpinnersShown(true);
        dialog.getDatePicker().setCalendarViewShown(false);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    private void assignAllView() {
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        loadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.progressanimation);
        policy_id = (TextInputEditText) findViewById(R.id.policy_idtext);
        customer_name = (TextInputEditText) findViewById(R.id.nametext);
        mobile_no = (TextInputEditText) findViewById(R.id.mobiletext);
        start_d = (TextView) findViewById(R.id.startdatetext);
        end_d = (TextView) findViewById(R.id.enddatetext);
        company_nme = (TextInputEditText) findViewById(R.id.companeytext);
        policy_tpe = (TextInputEditText) findViewById(R.id.policy_typetext);
        amt = (TextInputEditText) findViewById(R.id.amounttext);
        remainamt = (TextInputEditText) findViewById(R.id.remainamounttext);
        attachemntView = (ImageView) findViewById(R.id.attachmentView);


        //setDate();
        policy_idlayout = (TextInputLayout) findViewById(R.id.policy_idTextInput);

    }

    private void setAllData() {
        policy_id.setText(list.getPolicy_id().trim());
        customer_name.setText(list.getName().trim());
        mobile_no.setText(list.getContact().trim());
        company_nme.setText(list.getCompaney().trim());
        policy_tpe.setText(list.getPolicy_type().trim());
        amt.setText(list.getAmt().trim());
        remainamt.setText(list.getR_amt().trim());
        start_d.setText(getDate(list.getS_date().trim()));
        end_d.setText(getDate(list.getE_date().trim()));


    }

    private String getDate(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dte = sdf.format(newDate);
        return dte;
    }

    private void setDate(Date date) {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        start_d.setText(sdf.format(c));
        end_d.setText(sdf.format(c));
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
            //policy_id.setError("Enter Valid Lenght");
            //policy_id.requestFocus();
            policy_idlayout.setError("Enter Valid Lenght");
            policy_idlayout.requestFocus();
            return false;
        } else if (customer_name.getText().length() < 4) {
            customer_name.setError("Enter Valid Name");
            customer_name.requestFocus();
            return false;
        } else if (mobile_no.getText().length() < 10 || mobile_no.getText().length() > 13) {
            mobile_no.setError("Enter Valid Number");
            mobile_no.requestFocus();
            return false;
        } else if (start_d.getText().length() < 9) {
            start_d.setError("Enter Valid start Date");
            start_d.requestFocus();
            Log.e("StartDate", "True");
            int a = start_d.getText().length();
            Log.e("Length", a + "");
            return false;
        } else if (end_d.getText().length() < 9) {
            end_d.setError("Enter Valid End Date");
            end_d.requestFocus();
            int a = end_d.getText().length();
            Log.e("Length", a + "");
            Log.e("EndDate", "True");
            return false;
        } else if (company_nme.getText().length() < 3) {
            company_nme.setError("Enter Valid Company Name");
            company_nme.requestFocus();
            return false;
        } else if (policy_tpe.getText().length() < 2) {
            policy_tpe.setError("Enter valid Policy Type");
            policy_tpe.requestFocus();
            return false;
        } else if (amt.getText().length() < 2) {
            amt.setError("Enter Valid Amount");
            amt.requestFocus();
            return false;
        } else if (remainamt.getText().length() < 1) {
            remainamt.setError("Enter Valid Amount");
            remainamt.requestFocus();
            return false;
        } else if (e_date.compareTo(s_date) < 0 || e_date.compareTo(s_date) == 0) {
            end_d.setError("Enter valid End Date");
            end_d.requestFocus();
            Log.e("EndDate", "Exception");
            return false;
        } else {
            loadingIndicatorView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            //xyz@pqrSnackbar.make(findViewById(android.R.id.content), "All is well", Snackbar.LENGTH_LONG).show();
            callingDataInsert();
            return true;
        }

    }

    public void callingDataInsert() {
        String url = ConstantClass.Insert_record + "p_id=" + policy_id.getText().toString() + "&c_name=" + customer_name.getText().toString() +
                "&mobile=" + mobile_no.getText().toString() + "&s_date=" + start_d.getText().toString() + "&e_date=" + end_d.getText().toString() +
                "&company_name=" + company_nme.getText().toString() + "&p_type=" + policy_tpe.getText().toString() + "&amount=" + amt.getText().toString() +
                "&user_id=" + UserDataClass.getUser_id() + "&remain_amt=" + remainamt.getText().toString();

        //GetConnectionClass connectionClass = new GetConnectionClass(this);
        //connectionClass.execute(url);


        String path = getPath(uri);
        if (path == null) {
            Log.e("Path", "It is Null");
            InternetClass internetClass = new InternetClass(this, this, this);
            /*internetClass.postInsertData(url, "p_id", policy_id.getText().toString(), "c_name", customer_name.getText().toString(),
                    "mobile",mobile_no.getText().toString(),"s_date",start_d.getText().toString(),"e_date",end_d.getText().toString(),
                    "company_name",company_nme.getText().toString(),"p_type",policy_tpe.getText().toString(),"amount",amt.getText().toString(),
                    "user_id",UserDataClass.getUser_id().toString(),"remain_amt",remainamt.getText().toString());
*/
        } else {
            Log.e("Path", "It is Not Null");
            //uploadData(path);
        }

        resetAll();
        showToast("Data Updated Successfully");
    }


    private void uploadData(String path) {
        String name = String.valueOf(time);

        try {
            String uploadid = UUID.randomUUID().toString();
            new MultipartUploadRequest(this, uploadid, ConstantClass.Insert_record_with_images)
                    .addFileToUpload(path, "image")
                    .addArrayParameter("c_name", customer_name.getText().toString().trim())
                    .addArrayParameter("mobile", mobile_no.getText().toString().trim())
                    .addArrayParameter("s_date", start_d.getText().toString().trim())
                    .addArrayParameter("e_date", end_d.getText().toString().trim())
                    .addArrayParameter("company_name", company_nme.getText().toString().trim())
                    .addArrayParameter("p_type", policy_tpe.getText().toString().trim())
                    .addArrayParameter("amount", amt.getText().toString().trim())
                    .addArrayParameter("user_id", UserDataClass.getUser_id().toString().trim())
                    .addArrayParameter("remain_amt", remainamt.getText().toString().trim())
                    .addArrayParameter("p_id", policy_id.getText().toString().trim())
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            loadingIndicatorView.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                            showToast("Please Check your Connection " + exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            loadingIndicatorView.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);

                            try {
                                JSONObject object = new JSONObject(serverResponse.getBodyAsString());
                                if (object.getBoolean("error")) {
                                    showToast(object.getString("message"));
                                } else {
                                    showToast(object.getString("message"));
                                    resetAll();
                                }
                            } catch (JSONException e) {
                                showToast(e.getMessage());
                            }


                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            loadingIndicatorView.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                            showToast("Request Stope By User");
                        }
                    })
                    .startUpload();
        } catch (FileNotFoundException e) {
            e.fillInStackTrace();
        } catch (MalformedURLException e) {
            e.fillInStackTrace();
        }
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
            } else {
                Snackbar.make(findViewById(android.R.id.content), object.getString("message"), Snackbar.LENGTH_LONG).show();
                resetAll();
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void resetAll() {
        loadingIndicatorView.setVisibility(View.GONE);

        policy_id.setText(" ");
        customer_name.setText(" ");
        mobile_no.setText(" ");
        start_d.setText(" ");
        end_d.setText(" ");
        company_nme.setText(" ");
        policy_tpe.setText(" ");
        amt.setText(" ");
        scrollView.setVisibility(View.VISIBLE);
        startActivity(new Intent(this, DashActivity.class));

    }

    @Override
    public void onNetworkError(String message) {

    }


    private void showSnackbar(String msg) {
        Snackbar.make(findViewById(android.R.id.content), "" + msg, Snackbar.LENGTH_LONG).show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_LONG).show();
    }


}
