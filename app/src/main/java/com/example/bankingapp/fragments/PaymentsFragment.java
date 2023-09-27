package com.example.bankingapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bankingapp.Balance;
import com.example.bankingapp.DateConverter;
import com.example.bankingapp.DialogDeposit;
import com.example.bankingapp.NewDeposit;
import com.example.bankingapp.NewTransfer;
import com.example.bankingapp.R;
import com.example.bankingapp.customizedSpinner.AdaptorSpinner;
import com.example.bankingapp.customizedSpinner.DataFurnizor;
import com.example.bankingapp.database.BalanceDB;
import com.example.bankingapp.database.NewTransferDP;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentsFragment extends Fragment {
//    public interface TransferInterface {
//        void sendInput(NewTransfer newTransfer);
//    }

    //public TransferInterface transferInterface;

    Spinner spinnerFurnizor;
    AdaptorSpinner adaptorSpinner;
    Switch mySwich;
    EditText etSuma, etBeneficiar, etIban, etDescriere;
    Button btnSend;
    private static final String TAG = "TransferFragment";
    Date newNewDate = new Date();
    String data = DateConverter.fromDate(newNewDate);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payments, container, false);
        spinnerFurnizor = view.findViewById(R.id.spinnerFurnizor);
        etSuma = view.findViewById(R.id.etSuma);
        etBeneficiar = view.findViewById(R.id.etBeneficiar);
        etIban = view.findViewById(R.id.etIban);
        etDescriere = view.findViewById(R.id.etDescriere);
        btnSend = view.findViewById(R.id.btnSend);



        adaptorSpinner = new AdaptorSpinner(getContext(), DataFurnizor.getFurnizorList());

        mySwich = view.findViewById(R.id.mySwich);
        spinnerFurnizor.setAdapter(adaptorSpinner);
        spinnerFurnizor.setEnabled(false);
        adaptorSpinner.setSwitchState(false);

        validateOtherFields();




        mySwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spinnerFurnizor.setEnabled(true);
                    adaptorSpinner.setSwitchState(true); // Show first item
                    factura();


                }else{
                    spinnerFurnizor.setEnabled(false);
                    adaptorSpinner.setSwitchState(false); // hide first item
                    etBeneficiar.setText("");
                    etIban.setText("");
                    etDescriere.setText("");


                }

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transferInterface = (TransferInterface) getTargetFragment();

                if(validateObject()){
                    NewTransfer newTransfer = createTransfer();
                    Log.e(TAG, "newTransfer " + newTransfer);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("key", newTransfer);
                    TransactionsFragment transactionsFragment = new TransactionsFragment();
                    transactionsFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, transactionsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    //insertInDB(newTransfer);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Nu puteti efectua o plata, daca nu ati completat toate campurile.")
                            .setPositiveButton("Close", null)
                            .create()
                            .show();
                    return;
                }






               

//                transferInterface.sendInput(newTransfer);

            }
        });



        return view;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//
//        } catch (ClassCastException e) {
//            Log.e(TAG, "onAttach: ClassCastException " + e.getMessage());
//        }
//    }

    private NewTransfer createTransfer() {

        double suma = Double.parseDouble(etSuma.getText().toString());
        String beneficiar = etBeneficiar.getText().toString();
        String IBAN = etIban.getText().toString();
        String descriere = etDescriere.getText().toString();

        substractBalance(suma);

        String newDate = data;

        if(descriere.toLowerCase().contains("Factura".toLowerCase())){
            return new NewTransfer(suma, beneficiar, IBAN, descriere, newDate,false, true, "Facturi");
        }else if((descriere.toLowerCase().contains("Bolt".toLowerCase())) || (descriere.toLowerCase().contains("Transport".toLowerCase()))
            || (descriere.toLowerCase().contains("Metrorex".toLowerCase()))){
            return new NewTransfer(suma, beneficiar, IBAN, descriere, newDate,false, false, "Transport");
        }else if((descriere.toLowerCase().contains("Restaurant".toLowerCase())) || (descriere.toLowerCase().contains("Terasa".toLowerCase())) ||
                (descriere.toLowerCase().contains("Fastfood".toLowerCase()))){
            return new NewTransfer(suma, beneficiar, IBAN, descriere, newDate,false, false, "Alimentar");

        }else{
            return new NewTransfer(suma, beneficiar, IBAN, descriere, newDate,false, false, "Cumparaturi");
        }

    }

    public boolean validateObject (){
        if(etSuma.getText() == null || etSuma.getText().toString().trim().isEmpty()) {
            return false;
        }
        if(etBeneficiar.getText() == null || etBeneficiar.getText().toString().trim().isEmpty()) {
            return false;
        }
        if(etIban.getText() == null || etIban.getText().toString().trim().isEmpty()) {
            return false;
        }
        if(etDescriere.getText() == null || etDescriere.getText().toString().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    public void validateOtherFields() {
        if (etBeneficiar.length() == 0) {
            etBeneficiar.setError("Campul nu poate fi gol");
        }

        if(etIban.length()==0){
            etIban.setError("Campul nu poate fi gol");
        }

        if (etSuma.length() == 0) {
            etSuma.setError("Campul nu poate fi gol");
        }

        if(etDescriere.length()==0){
            etDescriere.setError("Campul nu poate fi gol");
        }

        etIban.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etIban.getText().toString().equals("")){
                    etIban.setError(null);
                }else{
                    etIban.setError("Campul nu poate fi gol");
                }

            }
        });

        etBeneficiar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etBeneficiar.getText().toString().equals("")){
                    etBeneficiar.setError(null);
                }else{
                    etBeneficiar.setError("Campul nu poate fi gol");
                }
            }
        });

        etDescriere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!etDescriere.getText().toString().equals("")){
                    etDescriere.setError(null);
                }else{
                    etDescriere.setError("Campul nu poate fi gol");
                }
            }
        });



        etSuma.addTextChangedListener(new TextWatcher() {
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
                    etSuma.setError("Trebuie sa introduceti o suma.");
                } else if(!TextUtils.isEmpty(text) && Double.parseDouble(text) > limit && !text.contains(".")){
                    etSuma.setError("Suma adaugata nu poate depasi soldul curent.");

                }else{
                    etSuma.setError(null);
                }
            }
        });


    }

    public void insertInDB(NewTransfer newTransfer){
        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        newTransferDP.getNewTransferDAO().addTransfer(newTransfer);
    }

    public void substractBalance(double suma){
        BalanceDB balanceDB = BalanceDB.getInstance(getContext());
        balanceDB.getBalanceDao().substractFromLastBalance(suma);
    }

    private void factura(){

        spinnerFurnizor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    etBeneficiar.setText("Enel");
                    etIban.setText("RO000103011233");
                    etDescriere.setText("Factura enel");
                }
                if(position==1){
                    etBeneficiar.setText("Digi");
                    etIban.setText("RO110010301243");
                    etDescriere.setText("Factura digi");
                }
                if(position==2){
                    etBeneficiar.setText("ApaNova");
                    etIban.setText("RO709103011233");
                    etDescriere.setText("Factura apa");
                }
                if(position==3){
                    etBeneficiar.setText("Orange");
                    etIban.setText("RO12778953");
                    etDescriere.setText("Factura orange");
                }
                if(position==4){
                    etBeneficiar.setText("Vodafone");
                    etIban.setText("RO33568004852");
                    etDescriere.setText("Factura vodafone");
                }
                if(position==5){
                    etBeneficiar.setText("Engie");
                    etIban.setText("RO8800103033");
                    etDescriere.setText("Factura engie");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}