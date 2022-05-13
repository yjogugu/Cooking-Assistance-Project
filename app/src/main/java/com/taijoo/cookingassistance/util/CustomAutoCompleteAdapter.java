package com.taijoo.cookingassistance.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taijoo.cookingassistance.R;
import com.taijoo.cookingassistance.data.model.MaterialData;
import com.taijoo.cookingassistance.data.model.SearchMaterialData;
import com.taijoo.cookingassistance.data.model.StorageMaterialData;

import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteAdapter extends ArrayAdapter<MaterialData> {

    private List<MaterialData> mItem;

    public CustomAutoCompleteAdapter(@NonNull Context context , @NonNull List<MaterialData> item){
        super(context,0,item);

        mItem = new ArrayList<>(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_arrayadapter, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.spinnerText);

        //getItem(position) 코드로 자동완성 될 아이템을 가져온다
        MaterialData countryItem = getItem(position);

        if (countryItem != null) {
            textView.setText(countryItem.getName());
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<MaterialData> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(mItem);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MaterialData item : mItem) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((MaterialData) resultValue).getName();
        }
    };
}
