package com.example.bankingapp;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bankingapp.database.BalanceDB;
import com.example.bankingapp.database.DepositDB;

import java.util.ArrayList;


public class DialogDeposit extends DialogFragment {
    public interface DialogInterface {
        void sendInput(NewDeposit newDeposit);
    }

    public DialogInterface dialogInterface;
    EditText etName, etAmount;
    TextView tvInterestRate, tvTimeLeft;
    Button btnAddDeposit, btnCancel;
    Spinner spinner;
    private static final String TAG = "MainFragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        btnCancel = view.findViewById(R.id.btnCancel);
        btnAddDeposit = view.findViewById(R.id.btnAddDeposit);
        tvInterestRate = view.findViewById(R.id.interestRateField);
        tvTimeLeft = view.findViewById(R.id.timeLeftField);
        etName = view.findViewById(R.id.etNameDialog);
        etAmount = view.findViewById(R.id.etAmountDialog);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(), R.array.period_deposit, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        validate();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnAddDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewDeposit newDeposit = createDeposit();
                insertDepositInDB(newDeposit);
                dialogInterface.sendInput(newDeposit);
                getDialog().dismiss();


            }
        });


        return view;


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogInterface = (DialogInterface) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException " + e.getMessage());
        }
    }

    private NewDeposit createDeposit() {

        String name = etName.getText().toString();
        double ammount = Double.parseDouble(etAmount.getText().toString());
        String period = spinner.getSelectedItem().toString();
        String timeLeft = tvTimeLeft.getText().toString();
        String interestRate = tvInterestRate.getText().toString();
        return new NewDeposit(name, ammount, period, interestRate, timeLeft);

    }

    private void insertDepositInDB(NewDeposit newDeposit) {
        DepositDB depositDB = DepositDB.getInstance(getContext());
        depositDB.getNewDepositDao().addDeposit(newDeposit);
    }

    public void validate() {
        validateOtherFields();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItemPosition() == 0) {
                    tvInterestRate.setText("0.1");
                    tvTimeLeft.setText("1 month");
                }
                if (spinner.getSelectedItemPosition() == 1) {
                    tvInterestRate.setText("0.3");
                    tvTimeLeft.setText("6 months");
                }
                if (spinner.getSelectedItemPosition() == 2) {
                    tvInterestRate.setText("0.6");
                    tvTimeLeft.setText("8 months");
                }
                if (spinner.getSelectedItemPosition() == 3) {
                    tvInterestRate.setText("0.9");
                    tvTimeLeft.setText("12 months");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvInterestRate.setText("0");
                tvTimeLeft.setText("No time displayed");

            }
        });
    }



    public void validateOtherFields() {
        if (etName.length() == 0) {
            etName.setError("Campul nu poate fi gol");
        }
        if(etAmount.length()==0){
            etAmount.setError("Campul nu poate fi gol");
        }

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                BalanceDB balanceDB = BalanceDB.getInstance(getContext());

                double limit = balanceDB.getBalanceDao().getLastBalance();





                String text = s.toString().trim();
                if (!TextUtils.isEmpty(text) && !TextUtils.isDigitsOnly(text) && !text.contains(".")) {
                    etAmount.setError("Trebuie sa introduceti o suma.");
                } else if(!TextUtils.isEmpty(text) && Double.parseDouble(text) > limit && !text.contains(".")){
                        etAmount.setError("Suma adaugata nu poate depasi soldul curent.");

                }else{
                    etAmount.setError(null);
                }
            }
        });

    }
}


