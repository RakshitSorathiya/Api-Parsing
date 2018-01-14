package com.example.chintankotadia.apidemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.chintankotadia.apidemo.R;
import com.example.chintankotadia.apidemo.activity.MainActivity;
import com.example.chintankotadia.apidemo.model.Example;

import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<Example> list;
    private Context context;

    public MainAdapter(MainActivity context, List<Example> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Example example = list.get(position);

        holder.userId.setText(String.valueOf(example.getUserId()));
        holder.title.setText(example.getTitle());
        holder.body.setText(example.getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userId, title, body;

        public MyViewHolder(View itemView) {
            super(itemView);

            userId = itemView.findViewById(R.id.tv_userid_no);
            title = itemView.findViewById(R.id.tv_title_text);
            body = itemView.findViewById(R.id.tv_body_text);
        }
    }
}
