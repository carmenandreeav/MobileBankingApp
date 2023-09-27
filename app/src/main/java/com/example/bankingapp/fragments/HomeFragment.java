package com.example.bankingapp.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankingapp.Balance;
import com.example.bankingapp.CustomDepositAdaptor;
import com.example.bankingapp.DialogDeposit;
import com.example.bankingapp.DialogWithdrawals;
import com.example.bankingapp.NewDeposit;
import com.example.bankingapp.R;
import com.example.bankingapp.database.BalanceDB;
import com.example.bankingapp.database.DepositDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment implements DialogDeposit.DialogInterface {
    FloatingActionButton fabAdd;
    FloatingActionButton fabHidden, fabArrowUp, fabArrowDown;
    ListView lvDeposits;
    TextView tvNameVisible, tvNameHidden, tvAccountVisible, tvAccountHidden, tvValidVisible, tvValidHidden, tvBalance;
    Button btnCopy;
    ImageView imgCard;
    private static final String TAG = "MainFragment";
    ArrayList<NewDeposit> depositList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fabAdd = view.findViewById(R.id.fabADD);
        lvDeposits = view.findViewById(R.id.lvDeposits);
        fabHidden = view.findViewById(R.id.fabHiddenDetails);
        tvNameVisible = view.findViewById(R.id.tvNameVisible);
        tvNameHidden = view.findViewById(R.id.tvNameHidden);
        tvAccountVisible = view.findViewById(R.id.tvAccountVisible);
        tvAccountHidden = view.findViewById(R.id.tvAccountHidden);
        tvValidVisible = view.findViewById(R.id.tvValidVisible);
        tvValidHidden = view.findViewById(R.id.tvValidHidden);
        btnCopy = view.findViewById(R.id.btnCopy);
        tvBalance = view.findViewById(R.id.tvBalance);
        fabArrowDown = view.findViewById(R.id.fabShow);
        fabArrowUp = view.findViewById(R.id.fabArrowUp);
        imgCard = view.findViewById(R.id.imgViewCard);

        CustomDepositAdaptor depositAdaptor = new CustomDepositAdaptor(getContext(),
                R.layout.lv_dialog_row, depositList, getLayoutInflater());
        lvDeposits.setAdapter(depositAdaptor);


        loadDepositList();
//        SaveFirstBalance();
        updateBalance();

        fabHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((tvNameVisible.getVisibility()== View.VISIBLE) && (tvAccountVisible.getVisibility()==View.VISIBLE) && (tvValidVisible
                        .getVisibility()==View.VISIBLE)){
                    tvNameVisible.setVisibility(View.GONE);
                    tvAccountVisible.setVisibility(View.GONE);
                    tvValidVisible.setVisibility(View.GONE);
                    tvNameHidden.setVisibility(View.VISIBLE);
                    tvAccountHidden.setVisibility(View.VISIBLE);
                    tvValidHidden.setVisibility(View.VISIBLE);
                }else{
                    tvNameVisible.setVisibility(View.VISIBLE);
                    tvAccountVisible.setVisibility(View.VISIBLE);
                    tvValidVisible.setVisibility(View.VISIBLE);
                    tvNameHidden.setVisibility(View.GONE);
                    tvAccountHidden.setVisibility(View.GONE);
                    tvValidHidden.setVisibility(View.GONE);
                }
            }
        });



        lvDeposits.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int listItem, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Do you want to remove this deposit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewDeposit newDeposit = (NewDeposit) parent.getItemAtPosition(listItem);
                                depositList.remove(listItem);
                                deleteDepositFromList(newDeposit);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CustomDepositAdaptor adapter = (CustomDepositAdaptor) lvDeposits.getAdapter();
                                        adapter.notifyDataSetChanged();
                                    }
                                });


                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                return false;

            }

        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                StringBuilder textMessage = new StringBuilder();
                textMessage.append("My banking info: ");
                textMessage.append(tvAccountVisible.getText().toString());
                textMessage.append(" , ");
                textMessage.append(tvNameVisible.getText().toString());
                textMessage.append(" , ");
                textMessage.append(tvValidVisible.getText().toString());

                ClipData clipData = ClipData.newPlainText("simple text", textMessage);
                clipboard.setPrimaryClip(clipData);

                Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeposit dialog = new DialogDeposit();
                dialog.setTargetFragment(HomeFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");
                //dialog.show();


            }
        });



        if(fabArrowDown.getVisibility() == View.GONE && lvDeposits.getVisibility()==View.VISIBLE && fabArrowUp.getVisibility()==View.VISIBLE){
            fabArrowUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lvDeposits.setVisibility(View.GONE);
                    fabArrowUp.setVisibility(View.GONE);
                    fabArrowDown.setVisibility(View.VISIBLE);
                }
            });
        }

        lvDeposits.setVisibility(View.GONE);
        fabArrowUp.setVisibility(View.GONE);
        fabArrowDown.setVisibility(View.VISIBLE);

        if(fabArrowUp.getVisibility() == View.GONE && lvDeposits.getVisibility()==View.GONE && fabArrowDown.getVisibility()==View.VISIBLE){
            fabArrowDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lvDeposits.setVisibility(View.VISIBLE);
                    fabArrowUp.setVisibility(View.VISIBLE);
                    fabArrowDown.setVisibility(View.GONE);
                }
            });
        }

        lvDeposits.setVisibility(View.VISIBLE);
        fabArrowUp.setVisibility(View.VISIBLE);
        fabArrowDown.setVisibility(View.GONE);


        imgCard.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDoubleTap(@NonNull MotionEvent e) {
                    Toast.makeText(getContext(), "DoubleTap", Toast.LENGTH_LONG);


                    DialogWithdrawals dialogWithdrawals = new DialogWithdrawals();

                    dialogWithdrawals.show(getActivity().getSupportFragmentManager(),"AnotherTag");
                    return super.onDoubleTap(e);
                }
            });
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        return view;
    }


    @Override
    public void sendInput(NewDeposit newDeposit) {
        Log.d(TAG, "sendInput: found incoming input: " + newDeposit);
        depositList.add(newDeposit);

        CustomDepositAdaptor adapter = (CustomDepositAdaptor) lvDeposits.getAdapter();
        adapter.notifyDataSetChanged();

    }


    private void loadDepositList(){
        DepositDB depositDB = DepositDB.getInstance(getContext());
        List<NewDeposit>depositsFromDB = depositDB.getNewDepositDao().getAllDeposits();
        for(NewDeposit n:depositsFromDB){
            depositList.add(n);
        }
        CustomDepositAdaptor adapter = (CustomDepositAdaptor) lvDeposits.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void deleteDepositFromList(NewDeposit newDeposit){
        DepositDB depositDB = DepositDB.getInstance(getContext());
        depositDB.getNewDepositDao().deleteDeposit(newDeposit);
        CustomDepositAdaptor adapter = (CustomDepositAdaptor) lvDeposits.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void SaveFirstBalance(){
        Balance balance = new Balance();
        balance.setBalance(50356.92);
        BalanceDB balanceDB = BalanceDB.getInstance(getContext());
        balanceDB.getBalanceDao().insert(balance);

        tvBalance.setText(String.valueOf(balance.getBalance()));
    }

    private void updateBalance(){
        BalanceDB balanceDB = BalanceDB.getInstance(getContext());

        tvBalance.setText(String.valueOf(balanceDB.getBalanceDao().getLastBalance()));

    }

}