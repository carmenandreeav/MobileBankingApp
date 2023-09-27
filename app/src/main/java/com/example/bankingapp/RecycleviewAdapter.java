package com.example.bankingapp;

import static java.sql.Types.NULL;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleviewAdapter extends RecyclerView.Adapter<RecycleviewAdapter.MyViewHolder> {
    List<NewTransfer> newTransferList;
    Context context;

    public RecycleviewAdapter(List<NewTransfer> newTransferList, Context context) {
        this.newTransferList = newTransferList;
        this.context = context;
    }

    public void setFilteredList(List<NewTransfer> filteredList){
        this.newTransferList = filteredList;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public RecycleviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleviewAdapter.MyViewHolder holder, int position) {
        NewTransfer newTransfer = newTransferList.get(position);
        holder.tvSuma.setText(String.valueOf(newTransfer.getSuma()));
        holder.tvDescriere.setText(newTransfer.getDescriere());
        holder.tvData.setText(newTransfer.getDataTranzactie());

        if(newTransfer.isVenit()){
            holder.tvSuma.setTextColor(Color.rgb(34,139,34));
        }else{
            holder.tvSuma.setTextColor(Color.rgb(220,20,60));
        }
    }

    @Override
    public int getItemCount() {
        if(newTransferList!=null){
            return newTransferList.size();
        }else{
            return 0;
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescriere, tvSuma, tvData;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescriere = itemView.findViewById(R.id.tvDescriere);
            tvData = itemView.findViewById(R.id.tvData);
            tvSuma = itemView.findViewById(R.id.tvSuma);
        }
    }
}
