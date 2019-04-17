package com.example.a3buttons;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a3buttons.InternetPack.ConnectivityInterface;
import com.example.a3buttons.InternetPack.ConstantClass;
import com.example.a3buttons.InternetPack.GetConnectionClass;
import com.example.a3buttons.SearchData.ItemListClass;
import com.example.a3buttons.SearchData.ItemListRecyclerData;
import com.example.a3buttons.SearchData.RecyclerDataAdapter;
import com.example.a3buttons.SearchData.SearchDataAdapter;
import com.example.a3buttons.UserData.UserDataClass;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment implements ConnectivityInterface {
    View mainview;
    private List<ItemListClass> dataList;
    AutoCompleteTextView autoCompleteTextView;
    RecyclerView recyclerView;
    RecyclerDataAdapter adapter;
    AVLoadingIndicatorView avLoadingIndicatorView;
    ArrayList<ItemListRecyclerData> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mainview = rootView;
        fillCountryList();
        autoCompleteTextView = (AutoCompleteTextView)rootView.findViewById(R.id.autocompletesearch);
        SearchDataAdapter adapter = new SearchDataAdapter(getActivity(),dataList);
        autoCompleteTextView.setAdapter(adapter);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.searchrecyclerview);

        avLoadingIndicatorView = (AVLoadingIndicatorView)rootView.findViewById(R.id.progressanimation);

        ImageView searchView = (ImageView)rootView.findViewById(R.id.search_img);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                avLoadingIndicatorView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                searchRelatedData(context);
            }
        });

        return rootView;
    }

    private void searchRelatedData(Context ctx){
        String data = autoCompleteTextView.getText().toString();
        String url = ConstantClass.Search_record+"user_id="+ UserDataClass.getUser_id().toString()+"&search_data="+data;
        GetConnectionClass getConnectionClass = new GetConnectionClass( this);
        getConnectionClass.execute(url);
    }


    private void fillCountryList(){
        dataList = new ArrayList<>();

        dataList.add(new ItemListClass("Data1",R.drawable.search));
        dataList.add(new ItemListClass("Bata",R.drawable.search));
        dataList.add(new ItemListClass("Paragon",R.drawable.search));
        dataList.add(new ItemListClass("Generation",R.drawable.search));
        dataList.add(new ItemListClass("Generator",R.drawable.search));
        dataList.add(new ItemListClass("Power",R.drawable.search));
        dataList.add(new ItemListClass("Energy",R.drawable.search));
        dataList.add(new ItemListClass("Sources",R.drawable.search));
        dataList.add(new ItemListClass("1253",R.drawable.search));
        dataList.add(new ItemListClass("43256",R.drawable.search));
        dataList.add(new ItemListClass("336723",R.drawable.search));
        dataList.add(new ItemListClass("5767568123",R.drawable.search));
        dataList.add(new ItemListClass("0u87495",R.drawable.search));

    }

    private void showRecycler(){
        avLoadingIndicatorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onResultComplete(String Output) {
        try {
            JSONObject object = new JSONObject(Output);
            if(object.getBoolean("error")){
                Snackbar.make(getActivity().findViewById(android.R.id.content),object.getString("message"),Snackbar.LENGTH_SHORT).show();
            }else{
                JSONArray jarray = object.getJSONArray("data");
                items = new ArrayList<>();
                for(int i=0; i<jarray.length();i++){
                    JSONObject obj = jarray.getJSONObject(i);
                    items.add(new ItemListRecyclerData(obj.getString("Customer_name"),obj.getString("policy_start_date"),
                            obj.getString("policy_end_date"),obj.getString("remain_amount"),obj.getString("policy_amount"),
                            obj.getString("policy_id"),obj.getString("policy_id"),resource(i)));
                }
            }

        }catch(JSONException e){Log.e("JSONEXCEPTION",""+e.getMessage());}

        adaptershowing();
    }

    private void adaptershowing(){
        recyclerView.setPadding(10,10,10,10);
        recyclerView.setHasFixedSize(true);
        RecyclerDataAdapter dataAdapter = new RecyclerDataAdapter(items);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showRecycler();
    }

    private int resource(int i){
        int x = i%5;
        if(x==1){
            return R.mipmap.a;
        }else if(x==2){
            return R.mipmap.b;
        }else if(x==3){
            return R.mipmap.c;
        }else if(x==4){
            return R.mipmap.d;
        }else{return R.mipmap.e;}
    }


}
