package com.example.bankingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomDepositAdaptor extends ArrayAdapter<NewDeposit> {
    TextView tvName, tvAmount, tvPeriod, tvInterestRate;
    private int resource;
    private List<NewDeposit> objects;
    private LayoutInflater inflater;


    public CustomDepositAdaptor(@NonNull Context context, int resource, @NonNull List<NewDeposit> objects,
                                LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.objects = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.resource, parent, false);
        tvName = row.findViewById(R.id.tvNameRow);
        tvAmount = row.findViewById(R.id.tvAmountRow);
        tvPeriod = row.findViewById(R.id.tvTimeLeftRow);
        tvInterestRate = row.findViewById(R.id.tvInterestRateRow);

        NewDeposit newDeposit = objects.get(position);

        tvName.setText(newDeposit.getName());
        tvAmount.setText(newDeposit.getAmount() + " RON");
        tvPeriod.setText("Time left: "+ newDeposit.getTimeLeft());
        tvInterestRate.setText("Dobanda: " + newDeposit.getInterestRate());

        return row;
    }


}
