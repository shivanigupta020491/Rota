package com.shivani.rota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<String > dateList;
    List<String> shiftList;

    public RecyclerAdapter(Context context, List<String> shiftList, List<String>dateList) {
        this.context = context;
        this.dateList = dateList;
        this.shiftList = shiftList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        int dateName = dataList.get(position).getDateName();
//        String rotaName = dataList.get(position).getRotaName();
        holder.date.setText(dateList.get(position));
        holder.shift.setText(shiftList.get(position));

    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }


}

class ViewHolder extends RecyclerView.ViewHolder{

    public TextView date;
    public TextView shift;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.dateRota);
        shift = itemView.findViewById(R.id.shiftNameRota);
    }


}
