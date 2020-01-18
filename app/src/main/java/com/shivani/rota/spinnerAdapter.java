package com.shivani.rota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.LayoutInflaterFactory;

import java.util.List;

public class spinnerAdapter extends BaseAdapter {

    private Context context;
    private List<MyData> myData;

    public spinnerAdapter(Context context, List<MyData> myData) {
        this.context = context;
        this.myData = myData;
    }

    @Override
    public int getCount() {
        return myData.size();
    }

    @Override
    public Object getItem(int i) {
        return myData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.spinner_item,viewGroup);
        TextView textView = view.findViewById(R.id.textSpinner);
        ImageView imageView = view.findViewById(R.id.imageSpinner);

        textView.setText(myData.get(i).getText());
        imageView.setImageResource(myData.get(i).getImage());

        return null;
    }
}
