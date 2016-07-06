package com.just.firstapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Bean> list;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<Bean> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.foodPic.setImageResource(list.get(position).getSrc());
        holder.foodName.setText(list.get(position).getName());
        holder.evaluate.setText(list.get(position).getEvaluate());
        holder.foodNum.setText(list.get(position).getNum());
        holder.foodQua.setText(list.get(position).getQuality());

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
//        return ;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView foodPic;
    TextView foodName;
    TextView evaluate;
    TextView foodNum;
    TextView foodQua;

    public MyViewHolder(View parent) {
        super(parent);
        foodPic = (ImageView) parent.findViewById(R.id.id_food_pic);
        foodName = (TextView) parent.findViewById(R.id.id_subject_txt);
        evaluate = (TextView) parent.findViewById(R.id.id_txt_attr);
        foodNum = (TextView) parent.findViewById(R.id.id_txt_points);
        foodQua = (TextView) parent.findViewById(R.id.id_txt_test);
    }
}