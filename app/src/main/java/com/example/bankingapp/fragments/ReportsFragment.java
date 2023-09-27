package com.example.bankingapp.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.bankingapp.DateConverter;
import com.example.bankingapp.NewTransfer;
import com.example.bankingapp.R;
import com.example.bankingapp.database.NewTransferDP;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsFragment extends Fragment {

    PieChart pieChart;
    Button btnGeneral, btnAlegeLuna, btnPredict;
    List<String> categories = Arrays.asList("Transport", "Cumparaturi", "Factura", "Alimentar","Utilitati");
    TextView tvPrezice;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);


        pieChart = view.findViewById(R.id.pieChart);
        btnGeneral = view.findViewById(R.id.btnGeneral);
        btnAlegeLuna = view.findViewById(R.id.btnAlege);
        btnPredict = view. findViewById(R.id.btnPredict);
        tvPrezice = view.findViewById(R.id.tvPrezice);
        progressBar = view.findViewById(R.id.progBar);


        btnGeneral.setBackgroundColor(Color.WHITE);
        btnGeneral.setTextColor(Color.rgb(125,141,255));

        btnAlegeLuna.setBackgroundColor(Color.rgb(125,141,255));
        btnAlegeLuna.setTextColor(Color.WHITE);

        tvPrezice.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(getContext()));
        }


        Map<String, Double> categoryAmounts = new HashMap<>();
        for (String category : categories) {
            categoryAmounts.put(category, 0.0);
        }


        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        List<NewTransfer>transferFromDB = newTransferDP.getNewTransferDAO().getAllTransfers();
        for(NewTransfer n:transferFromDB){
            String category = n.getCategory();
            if (categoryAmounts.containsKey(category)) {
                double amount = categoryAmounts.get(category) + n.getSuma();
                categoryAmounts.put(category, amount);
            }
        }

        double totalAmount = 0.0;
        for (double amount : categoryAmounts.values()) {
            totalAmount += amount;
        }





        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for(String category: categories){
            double categoryAmount = categoryAmounts.get(category);
            float percentage = (float) ((categoryAmount / totalAmount) * 100);
            PieEntry pieEntry = new PieEntry(percentage, category);
            pieEntries.add(pieEntry);


        }



        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Categories");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18f);
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.animateXY(2500,2500);
        pieChart.getDescription().setEnabled(false);

        pieChart.setEntryLabelTextSize(20f);

        pieChart.setCenterText("Cheltuieli per categorie (%)");
        pieChart.setCenterTextSize(20f);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setCenterTextRadiusPercent(100f);

        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGeneral.setBackgroundColor(Color.WHITE);
                btnGeneral.setTextColor(Color.rgb(125,141,255));

                btnAlegeLuna.setBackgroundColor(Color.rgb(125,141,255));
                btnAlegeLuna.setTextColor(Color.WHITE);
                btnAlegeLuna.setText("ALEGE LUNA");


                Map<String, Double> categoryAmounts = new HashMap<>();
                for (String category : categories) {
                    categoryAmounts.put(category, 0.0);
                }


                NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
                List<NewTransfer>transferFromDB = newTransferDP.getNewTransferDAO().getAllTransfers();
                for(NewTransfer n:transferFromDB){
                    String category = n.getCategory();
                    if (categoryAmounts.containsKey(category)) {
                        double amount = categoryAmounts.get(category) + n.getSuma();
                        categoryAmounts.put(category, amount);
                    }
                }

                double totalAmount = 0.0;
                for (double amount : categoryAmounts.values()) {
                    totalAmount += amount;
                }





                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                for(String category: categories){
                    double categoryAmount = categoryAmounts.get(category);
                    float percentage = (float) ((categoryAmount / totalAmount) * 100);
                    PieEntry pieEntry = new PieEntry(percentage, category);
                    pieEntries.add(pieEntry);


                }



                PieDataSet pieDataSet = new PieDataSet(pieEntries, "Categories");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextSize(18f);
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.invalidate();
                pieChart.animateXY(2500,2500);
                pieChart.getDescription().setEnabled(false);

                pieChart.setEntryLabelTextSize(20f);

                pieChart.setCenterText("Cheltuieli per categorie (%)");
                pieChart.setCenterTextSize(20f);
                pieChart.setCenterTextColor(Color.BLACK);
                pieChart.setCenterTextRadiusPercent(100f);
            }
        });

        btnAlegeLuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthPickerDialog();
                btnAlegeLuna.setBackgroundColor(Color.WHITE);
                btnAlegeLuna.setTextColor(Color.rgb(125,141,255));

                btnGeneral.setBackgroundColor(Color.rgb(125,141,255));
                btnGeneral.setTextColor(Color.WHITE);



            }



        });

        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // pythonIntegration();

                Python py = Python.getInstance();
                PyObject pyObject = py.getModule("licenta"); // Înlocuiți "nume_modul_python" cu numele modulului Python utilizat în proiect
                PyObject resultObject = pyObject.callAttr("main"); // Înlocuiți "functie_previzionare" cu numele funcției din Python care returnează rezultatul previzionat


                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                // Delay hiding the progress bar and setting text after 5 seconds
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the progress bar
                        progressBar.setVisibility(View.INVISIBLE);
                        tvPrezice.setVisibility(View.VISIBLE);
                        // Set text to the TextView
                        tvPrezice.setText(resultObject.toString());
                    }
                }, 5000);



            }
        });

        return view;
    }

    private void showMonthPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(), new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                int month = selectedMonth +1;
                List<NewTransfer> transfersByMonth = filterTransfersByMonth(selectedMonth);
                btnAlegeLuna.setText(month+"/"+selectedYear);
                pieChart.animateXY(2500,2500);

                // Update the pie chart with the filtered data
                updatePieChart(transfersByMonth);

            }
        }, currentYear, Calendar.JANUARY);

        // Set the minimum and maximum selectable months
        builder.setTitle("Select a Month")
                .setActivatedMonth(Calendar.JULY)
                .setMaxYear(2023)
                .showMonthOnly()
                .setYearRange(1970, 2050);



        MonthPickerDialog dialog = builder.build();
        dialog.show();
    }

    private List<NewTransfer> filterTransfersByMonth(int selectedMonth) {
        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        List<NewTransfer>transferFromDB = newTransferDP.getNewTransferDAO().getAllTransfers();

        List<NewTransfer> filteredTransfers = new ArrayList<>();

        for (NewTransfer transfer : transferFromDB) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateConverter.fromString(transfer.getDataTranzactie()));
            int transferMonth = calendar.get(Calendar.MONTH);

            if (transferMonth == selectedMonth) {
                filteredTransfers.add(transfer);
            }
        }

        return filteredTransfers;
    }

    private void updatePieChart(List<NewTransfer> transfers) {
        // Calculate the category amounts for the filtered transfers
        Map<String, Double> categoryAmounts = new HashMap<>();
        for (String category : categories) {
            categoryAmounts.put(category, 0.0);
        }

        for (NewTransfer transfer : transfers) {
            String category = transfer.getCategory();
            if (categoryAmounts.containsKey(category)) {
                double amount = categoryAmounts.get(category) + transfer.getSuma();
                categoryAmounts.put(category, amount);
            }
        }

        double totalAmount = 0.0;
        for (double amount : categoryAmounts.values()) {
            totalAmount += amount;
        }

        // Update the pie chart with the new data
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        for (String category : categories) {
            double categoryAmount = categoryAmounts.get(category);
            float percentage = (float) ((categoryAmount / totalAmount) * 100);
            PieEntry pieEntry = new PieEntry(percentage, category);
            pieEntries.add(pieEntry);
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Categories");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18f);
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }

//    public void pythonIntegration(){
//        try {
//            // Execută scriptul Python
//            //Process process = Runtime.getRuntime().exec("C:\\Users\\andre\\OneDrive\\Desktop\\Proiect Retele\\");
//
//
//            // Obțineți rezultatele de la scriptul Python
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // Așteaptă ca procesul să se încheie și obțineți codul de ieșire
//            int exitCode = process.waitFor();
//            System.out.println("Codul de ieșire: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }



}


