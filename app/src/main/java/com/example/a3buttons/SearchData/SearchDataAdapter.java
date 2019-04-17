package com.example.a3buttons.SearchData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3buttons.R;

import java.util.ArrayList;
import java.util.List;

public class SearchDataAdapter extends ArrayAdapter<ItemListClass> {

    private List<ItemListClass> dataList;

    public SearchDataAdapter(@NonNull Context context,  @NonNull List<ItemListClass> objects) {
        super(context, 0, objects);

        dataList = new ArrayList<>(objects);
    }


    public Filter getFilter(){
        return dataFilter;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_view,parent,false);
        }

        TextView name = convertView.findViewById(R.id.text_name);
        ImageView imgs = convertView.findViewById(R.id.img_type);

        ItemListClass itemListClass = getItem(position);

        if(itemListClass != null){
            name.setText(itemListClass.getValueName());
            imgs.setImageResource(itemListClass.getImageId());
        }

        return convertView;
    }



    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults result = new FilterResults();
            List<ItemListClass> suggestions = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                suggestions.addAll(dataList);
            }else{
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(ItemListClass item : dataList){
                    if(item.getValueName().toLowerCase().contains(filterpattern))
                    {
                        suggestions.add(item);
                    }
                }
            }

            result.values = suggestions;
            result.count = suggestions.size();

            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ItemListClass)resultValue).getValueName();
        }
    };
}
