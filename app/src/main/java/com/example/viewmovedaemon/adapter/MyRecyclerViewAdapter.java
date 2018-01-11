package com.example.viewmovedaemon.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viewmovedaemon.R;

import java.util.List;

/**
 * Created by yuanpk on 2018/1/11.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    protected List<String> stringList;

    public MyRecyclerViewAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        inflater = LayoutInflater.from(context);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv01);
        }
    }

    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText(stringList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, position + "", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList == null ? 0 : stringList.size();
    }


}
