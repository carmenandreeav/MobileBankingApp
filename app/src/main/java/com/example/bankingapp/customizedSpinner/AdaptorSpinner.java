package com.example.bankingapp.customizedSpinner;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bankingapp.R;

import java.util.List;

public class AdaptorSpinner extends BaseAdapter {
    TextView tvNume;
    private Context context;
    private List<Furnizor> furnizorList;
    private boolean isSwitchOn = false;

    public AdaptorSpinner(Context context, List<Furnizor> furnizorList){
        this.context = context;
        this.furnizorList = furnizorList;
    }
    public void setSwitchState(boolean isSwitchOn) {
        this.isSwitchOn = isSwitchOn;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return furnizorList != null ? furnizorList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_furnizor, parent, false);



        tvNume = row.findViewById(R.id.tvNume);
        // Check switch state and hide or display the first item accordingly
        if (!isSwitchOn && position == 0) {
            tvNume.setVisibility(View.GONE);
        } else {
            tvNume.setVisibility(View.VISIBLE);
            tvNume.setText(furnizorList.get(position).getNume());
        }

        return row;
    }
}
