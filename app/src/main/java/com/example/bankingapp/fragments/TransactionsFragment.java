package com.example.bankingapp.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bankingapp.CustomDepositAdaptor;
import com.example.bankingapp.DateConverter;
import com.example.bankingapp.DialogExtras;
import com.example.bankingapp.NewDeposit;
import com.example.bankingapp.NewTransfer;
import com.example.bankingapp.R;
import com.example.bankingapp.RecycleviewAdapter;
import com.example.bankingapp.database.DepositDB;
import com.example.bankingapp.database.NewTransferDP;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class TransactionsFragment extends Fragment implements View.OnClickListener{
    Button btnFilter, btnExtras, btnSort, btnToate, btnFacturi, btnData,
    btnVenituri, btnCheltuieli, btnCrescator, btnDescrescator;
    SearchView searchView;
    RecyclerView recyclerView;
    List<NewTransfer> newTransferList = new ArrayList<>();
    Date startDate;

    RecycleviewAdapter recycleviewAdapter;
    boolean sorted = true;
    private static final String TAG = "transactipns";
    ItemTouchHelper itemTouchHelper;

    String deletedTransfer=null;
    NewTransfer newTransfer1 = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
        btnFilter = view.findViewById(R.id.btnFilter);
        btnExtras = view.findViewById(R.id.btnExtras);
        btnSort = view.findViewById(R.id.btnSort);
        btnToate = view.findViewById(R.id.btnToate);
        btnFacturi = view.findViewById(R.id.btnFacturi);
        btnData = view.findViewById(R.id.btnData);
        btnVenituri = view.findViewById(R.id.btnVenituri);
        btnCheltuieli = view.findViewById(R.id.btnCheltuieli);
        btnCrescator = view.findViewById(R.id.btnCrescator);
        btnDescrescator = view.findViewById(R.id.btnDescrescator);
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recycleviewAdapter = new RecycleviewAdapter(newTransferList, getContext());
        recyclerView.setAdapter(recycleviewAdapter);



        loadFromDB();
        //deleteALL();

//        newTransferList.add(new NewTransfer(150,"CasaAlina","RO0023456","Restaurant", "25-05-2023", false, false, "Alimentar"));
//        newTransferList.add(new NewTransfer(25,"Andrei Costin","RO453456","Uber", "15-02-2023",false,false,"Transport"));
//        newTransferList.add(new NewTransfer(6.20,"METROREX","RO44123456","Transport", "22-01-2023",false,false,"Transport"));
//        newTransferList.add(new NewTransfer(365.95,"MegaImage","RO04123456","Cumparaturi alimente", "12-05-2023",false,false,"Alimentar"));
//        newTransferList.add(new NewTransfer(125.53,"Bershka","RO04123456","Cumparaturi haine", "17-04-2023",false,false,"Cumparaturi"));
//        newTransferList.add(new NewTransfer(3500,"Vasii Carmen","RO55123452","Salariu", "15-04-2023",true,false,"Venit"));
//        newTransferList.add(new NewTransfer(55.20,"CFR","RO53123456","Transport CFR", "09-03-2023",false,false,"Transport"));
//        newTransferList.add(new NewTransfer(100,"Vasii Carmen","RO55123452","Transfer primt", "09-06-2023",true,false,"Venit"));
//        newTransferList.add(new NewTransfer(465.22,"MegaMall","RO55123456","Cumparaturi cadouri", "29-06-2023",false,false,"Cumparaturi"));
//        newTransferList.add(new NewTransfer(32,"Matei Popescu","RO45123456","Uber", "19-05-2023",false,false,"Transport"));
//        newTransferList.add(new NewTransfer(24,"Dumitru Stefan","RO11123456","Bolt", "24-06-2023",false,false,"Transport"));
//        newTransferList.add(new NewTransfer(25,"Vasii Carmen","RO55123452","Transfer primit", "14-05-2023",true,false,"Venit"));
//        newTransferList.add(new NewTransfer(39.89,"KebabSocului","RO55123456","Restaurant", "11-03-2023",false,false,"Alimentar"));
//        newTransferList.add(new NewTransfer(3600,"Vasii Carmen","RO55123452","Salariu", "15-03-2023",true,false,"Venit"));
//        newTransferList.add(new NewTransfer(59.4,"Vasii Carmen","RO55123452","Cadou", "21-06-2023",true,false,"Cumparaturi"));
//        newTransferList.add(new NewTransfer(550,"Vasii Carmen","RO55123452","Factura telefon", "21-06-2023",false,true,"Factura"));
//        newTransferList.add(new NewTransfer(520.6,"Vasii Carmen","RO55123452","Factura enel", "25-05-2023",false,true,"Factura"));
//        newTransferList.add(new NewTransfer(562,"Vasii Carmen","RO55123452","Factura enel", "2-06-2023",false,true,"Factura"));
//        newTransferList.add(new NewTransfer(70,"Vasii Carmen","RO55123452","Factura enel", "6-06-2023",false,true,"Factura"));
//
//
//        newTransferList.add(new NewTransfer(245,"Andrei Matache","RO0023456","Chirie", "25-02-2023", false, false, "Utilitati"));
//        newTransferList.add(new NewTransfer(300,"Andrei Matache","RO0023456","Chirie", "25-03-2023", false, false, "Utilitati"));
//        newTransferList.add(new NewTransfer(125,"Andrei Matache","RO0023456","Chirie", "25-04-2023", false, false, "Utilitati"));
//        newTransferList.add(new NewTransfer(369.96,"Andrei Matache","RO0023456","Chirie", "25-05-2023", false, false, "Utilitati"));
//        newTransferList.add(new NewTransfer(89.5,"Andrei Matache","RO0023456","Chirie", "25-06-2023", false, false, "Utilitati"));
//        insertTransferInDB(newTransferList);



        recycleviewAdapter.notifyDataSetChanged();

        //crescator si descrescator nu merg impreuna
        //TODO: facturi -> crescator sau descrescator
        //venituri-> crescator sau descrescator
        //cheltuieli->crescator sau descrescator
        //alfabetic-> crescator sau descrescator
        // veniturile nu pot sa fie si cheltuieli
        // la data mai vedem

//        Date newNewDate = new Date();
//        Log.e(TAG, "dataCurenta" + newNewDate);
//
//        String data = DateConverter.fromDate(newNewDate);
//        Log.e(TAG, "datatring" + data);

        List<NewTransfer> listaGoala = new ArrayList<>();
        List<NewTransfer> copieNewTransferList=new ArrayList<>(newTransferList);



        recycleviewAdapter.notifyDataSetChanged();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            NewTransfer newTransfer = (NewTransfer) bundle.getSerializable("key");
           // newTransfer.setDataTranzactie(data);
            newTransferList.add(newTransfer);
            insertInDB(newTransfer);
            recycleviewAdapter.notifyDataSetChanged();

        }




        btnExtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogExtras dialogExtras = new DialogExtras();
                dialogExtras.show(getActivity().getSupportFragmentManager(),"AnotherTag");
            }
        });



        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);




        btnFilter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(searchView.getVisibility() == View.GONE && btnToate.getVisibility() == View.GONE && btnFacturi.getVisibility() == View.GONE && btnData.getVisibility() == View.GONE && btnVenituri.getVisibility() == View.GONE && btnCheltuieli.getVisibility() == View.GONE){
                    searchView.setVisibility(View.VISIBLE);
                    btnToate.setVisibility(View.VISIBLE);
                    btnFacturi.setVisibility(View.VISIBLE);
                    btnData.setVisibility(View.VISIBLE);
                    btnVenituri.setVisibility(View.VISIBLE);
                    btnCheltuieli.setVisibility(View.VISIBLE);
                    btnFilter.setText("Hide");

                }else{
                    searchView.setVisibility(View.GONE);
                    btnToate.setVisibility(View.GONE);
                    btnFacturi.setVisibility(View.GONE);
                    btnData.setVisibility(View.GONE);
                    btnVenituri.setVisibility(View.GONE);
                    btnCheltuieli.setVisibility(View.GONE);
                    btnFilter.setText("Filter");
                }
            }
        });

        btnToate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnToate.getCurrentTextColor() == Color.WHITE){
                    btnToate.setBackgroundColor(Color.WHITE);
                    btnToate.setTextColor(Color.rgb(125,141,255));
                    filterAlfabetic();
                    recycleviewAdapter.notifyDataSetChanged();
                }else{
                    btnToate.setBackgroundColor(Color.rgb(125,141,255));
                    btnToate.setTextColor(Color.WHITE);
                    recycleviewAdapter.setFilteredList(copieNewTransferList);
                    recycleviewAdapter.notifyDataSetChanged();
                }
            }
        });


        btnFacturi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFacturi.getCurrentTextColor() == Color.WHITE){
                    btnFacturi.setBackgroundColor(Color.WHITE);
                    btnFacturi.setTextColor(Color.rgb(125,141,255));
                    filterFactura();
                    recycleviewAdapter.notifyDataSetChanged();
                }else{
                    btnFacturi.setBackgroundColor(Color.rgb(125,141,255));
                    btnFacturi.setTextColor(Color.WHITE);
                    recycleviewAdapter.setFilteredList(copieNewTransferList);
                    recycleviewAdapter.notifyDataSetChanged();
                }

            }
        });

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnData.getCurrentTextColor() == Color.WHITE){
                    btnData.setBackgroundColor(Color.WHITE);
                    btnData.setTextColor(Color.rgb(125,141,255));
                    showDatePickerDialog();
                    recycleviewAdapter.notifyDataSetChanged();

                }else{
                    btnData.setBackgroundColor(Color.rgb(125,141,255));
                    btnData.setTextColor(Color.WHITE);
                    recycleviewAdapter.setFilteredList(copieNewTransferList);
                    recycleviewAdapter.notifyDataSetChanged();

                }

            }
        });

        btnVenituri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnVenituri.getCurrentTextColor() == Color.WHITE){
                    btnVenituri.setBackgroundColor(Color.WHITE);
                    btnVenituri.setTextColor(Color.rgb(125,141,255));
                    filterVenit();
                    recycleviewAdapter.notifyDataSetChanged();

                    btnCrescator.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(btnCrescator.getCurrentTextColor() == Color.WHITE){
                                btnCrescator.setBackgroundColor(Color.WHITE);
                                btnCrescator.setTextColor(Color.rgb(125,141,255));
                                if(sorted){
                                    filterCrescatorVenit(filterVenit());
                                    recycleviewAdapter.notifyDataSetChanged();
                                }
                                sorted = false;

                            }else {
                                btnCrescator.setBackgroundColor(Color.rgb(125, 141, 255));
                                btnCrescator.setTextColor(Color.WHITE);
                                if(!sorted){
                                    recycleviewAdapter.setFilteredList(filterVenit());
                                    recycleviewAdapter.notifyDataSetChanged();
                                }
                                sorted = true;

                            }
                        }
                    });

                    btnDescrescator.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(btnDescrescator.getCurrentTextColor() == Color.WHITE){
                                btnDescrescator.setBackgroundColor(Color.WHITE);
                                btnDescrescator.setTextColor(Color.rgb(125,141,255));
                                filterDescrescatorVenit(filterVenit());
                                recycleviewAdapter.notifyDataSetChanged();
                            }else {
                                btnDescrescator.setBackgroundColor(Color.rgb(125, 141, 255));
                                btnDescrescator.setTextColor(Color.WHITE);
                                recycleviewAdapter.setFilteredList(filterVenit());
                                recycleviewAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                    //

                    btnCheltuieli.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(btnCheltuieli.getCurrentTextColor() == Color.WHITE && btnVenituri.getCurrentTextColor()!=Color.WHITE) {
                                btnCheltuieli.setBackgroundColor(Color.WHITE); //apasam si pe venituri si pe cheltuieli
                                btnCheltuieli.setTextColor(Color.rgb(125, 141, 255));

                                btnVenituri.setBackgroundColor(Color.WHITE);
                                btnVenituri.setTextColor(Color.rgb(125, 141, 255));
                                recycleviewAdapter.setFilteredList(listaGoala);
                                recycleviewAdapter.notifyDataSetChanged();


                            }else{
                                btnCheltuieli.setBackgroundColor(Color.rgb(125,141,255)); //venit deselectat, cheltuiala selectata (alb in spate, text albastru)
                                btnCheltuieli.setTextColor(Color.WHITE);

                                recycleviewAdapter.setFilteredList(filterVenit());
                                recycleviewAdapter.notifyDataSetChanged();

                                //si venit si cheltuiala sunt deselectate, afisam lista originala

                            }


                        }
                    });



                }else if(btnVenituri.getCurrentTextColor()==Color.WHITE && btnCheltuieli.getCurrentTextColor()==Color.WHITE) {
                    btnVenituri.setBackgroundColor(Color.rgb(125, 141, 255));
                    btnVenituri.setTextColor(Color.WHITE);
                    filterCheltuiala();
                    Toast.makeText(getContext(), "s-a apasat",Toast.LENGTH_SHORT).show();
                    recycleviewAdapter.notifyDataSetChanged();


                }else{
                btnVenituri.setBackgroundColor(Color.rgb(125,141,255));
                btnVenituri.setTextColor(Color.WHITE);
                recycleviewAdapter.setFilteredList(copieNewTransferList);
                recycleviewAdapter.notifyDataSetChanged();

                }


            }
        });


        btnCheltuieli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCheltuieli.getCurrentTextColor() == Color.WHITE){
                    btnCheltuieli.setBackgroundColor(Color.WHITE);
                    btnCheltuieli.setTextColor(Color.rgb(125,141,255));
                    filterCheltuiala();
                    recycleviewAdapter.notifyDataSetChanged();

                    btnCrescator.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(btnCrescator.getCurrentTextColor() == Color.WHITE){
                                btnCrescator.setBackgroundColor(Color.WHITE);
                                btnCrescator.setTextColor(Color.rgb(125,141,255));
                                if(sorted){
                                    filterCrescatorVenit(filterCheltuiala());
                                    recycleviewAdapter.notifyDataSetChanged();
                                }
                                sorted = false;

                            }else {
                                btnCrescator.setBackgroundColor(Color.rgb(125, 141, 255));
                                btnCrescator.setTextColor(Color.WHITE);
                                if(!sorted){
                                    recycleviewAdapter.setFilteredList(filterCheltuiala());
                                    recycleviewAdapter.notifyDataSetChanged();
                                }
                                sorted = true;

                            }
                        }
                    });

                    btnDescrescator.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(btnDescrescator.getCurrentTextColor() == Color.WHITE){
                                btnDescrescator.setBackgroundColor(Color.WHITE);
                                btnDescrescator.setTextColor(Color.rgb(125,141,255));
                                filterDescrescatorVenit(filterCheltuiala());
                                recycleviewAdapter.notifyDataSetChanged();
                            }else {
                                btnDescrescator.setBackgroundColor(Color.rgb(125, 141, 255));
                                btnDescrescator.setTextColor(Color.WHITE);
                                recycleviewAdapter.setFilteredList(filterCheltuiala());
                                recycleviewAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }else{
                    btnCheltuieli.setBackgroundColor(Color.rgb(125,141,255));
                    btnCheltuieli.setTextColor(Color.WHITE);
                    recycleviewAdapter.setFilteredList(copieNewTransferList);
                    recycleviewAdapter.notifyDataSetChanged();

                }



            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListSearchView(newText);

                return true;
            }
        });



        btnCrescator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnCrescator.getCurrentTextColor() == Color.WHITE){
                    btnCrescator.setBackgroundColor(Color.WHITE);
                    btnCrescator.setTextColor(Color.rgb(125,141,255));
                    if(sorted){
                        filterListCrescator();
                        recycleviewAdapter.notifyDataSetChanged();
                    }
                    sorted = false;

                }else {
                    btnCrescator.setBackgroundColor(Color.rgb(125, 141, 255));
                    btnCrescator.setTextColor(Color.WHITE);
                    if(!sorted){
                        recycleviewAdapter.setFilteredList(copieNewTransferList);
                        recycleviewAdapter.notifyDataSetChanged();
                    }
                    sorted = true;

                }
            }
        });

        btnDescrescator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDescrescator.getCurrentTextColor() == Color.WHITE){
                    btnDescrescator.setBackgroundColor(Color.WHITE);
                    btnDescrescator.setTextColor(Color.rgb(125,141,255));
                    filterListDescrescator();
                    recycleviewAdapter.notifyDataSetChanged();
                }else {
                    btnDescrescator.setBackgroundColor(Color.rgb(125, 141, 255));
                    btnDescrescator.setTextColor(Color.WHITE);
                    recycleviewAdapter.setFilteredList(copieNewTransferList);
                    recycleviewAdapter.notifyDataSetChanged();
                }
            }
        });


        btnSort.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(btnCrescator.getVisibility()==View.GONE && btnDescrescator.getVisibility() == View.GONE){
                    btnCrescator.setVisibility(View.VISIBLE);
                    btnDescrescator.setVisibility(View.VISIBLE);
                    btnSort.setText("Hide");
                }else{
                    btnCrescator.setVisibility(View.GONE);
                    btnDescrescator.setVisibility(View.GONE);
                    btnSort.setText("Sort");
                }
            }
        });


        return view;
    }


    public void showDatePickerDialog(){
        DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.customDatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                startDate=cal.getTime();
                Log.e(TAG, "startDate " + DateConverter.fromDate(startDate));
                filterDate(startDate);
            }
        }, 2023, 0, 15);

        dialog.show();
    }
    public void filterDate(Date dataAleasa){
        List<NewTransfer> filteredList = new ArrayList<>();
        for(NewTransfer newTransfer:newTransferList){
            Log.e(TAG, "newTransfer " + newTransfer.getDataTranzactie());
            if(Objects.equals(DateConverter.fromDate(dataAleasa), newTransfer.getDataTranzactie())){
                filteredList.add(newTransfer);
            }

        }
        recycleviewAdapter.setFilteredList(filteredList);
    }

    private void filterFactura(){
        List<NewTransfer> filteredList = new ArrayList<>();
        for(NewTransfer newTransfer:newTransferList){
            if(newTransfer.isFactura()){
                filteredList.add(newTransfer);
            }

        }
        recycleviewAdapter.setFilteredList(filteredList);
    }

    private List<NewTransfer> filterCheltuiala(){
        List<NewTransfer> filteredList = new ArrayList<>();
        for(NewTransfer newTransfer:newTransferList){
            if(!newTransfer.isVenit()){
                filteredList.add(newTransfer);
            }

        }
        recycleviewAdapter.setFilteredList(filteredList);
        return filteredList;

    }

    private List<NewTransfer> filterVenit(){
        List<NewTransfer> filteredList = new ArrayList<>();
        for(NewTransfer newTransfer:newTransferList){
            if(newTransfer.isVenit()){
                filteredList.add(newTransfer);
            }

        }
        recycleviewAdapter.setFilteredList(filteredList);
        return filteredList;

    }

    private void filterAlfabetic(){
        List<NewTransfer> filteredList = new ArrayList<>();
        filteredList = newTransferList;
        Collections.sort(filteredList, new Comparator<NewTransfer>() {
            @Override
            public int compare(NewTransfer o1, NewTransfer o2) {
                return o1.getDescriere().compareToIgnoreCase(o2.getDescriere());

            }
        });
        recycleviewAdapter.setFilteredList(filteredList);
    }

    private void filterCrescatorVenit(List<NewTransfer> filteredList){
        Collections.sort(filteredList, new Comparator<NewTransfer>() {
            @Override
            public int compare(NewTransfer o1, NewTransfer o2) {
                return Double.compare(o1.getSuma(), o2.getSuma());

            }
        });
        recycleviewAdapter.setFilteredList(filteredList);
    }

    private void filterDescrescatorVenit(List<NewTransfer> filteredList){
        Collections.sort(filteredList, new Comparator<NewTransfer>() {
            @Override
            public int compare(NewTransfer o1, NewTransfer o2) {
                return Double.compare(o2.getSuma(), o1.getSuma());

            }
        });
        recycleviewAdapter.setFilteredList(filteredList);
    }


    private void filterListCrescator(){
        List<NewTransfer> filteredList = new ArrayList<>();
        filteredList = newTransferList;
        Collections.sort(filteredList, new Comparator<NewTransfer>() {
            @Override
            public int compare(NewTransfer o1, NewTransfer o2) {
                return Double.compare(o1.getSuma(), o2.getSuma());

            }
        });
        recycleviewAdapter.setFilteredList(filteredList);
    }

    private void filterListDescrescator(){
        List<NewTransfer> filteredList = new ArrayList<>();
        filteredList = newTransferList;
        Collections.sort(filteredList, new Comparator<NewTransfer>() {
            @Override
            public int compare(NewTransfer o1, NewTransfer o2) {
                return Double.compare(o2.getSuma(), o1.getSuma());

            }
        });
        recycleviewAdapter.setFilteredList(filteredList);
    }


    private void filterListSearchView(String text){
        List<NewTransfer> filteredList = new ArrayList<>();
        for(NewTransfer newTransfer:newTransferList){
            if(newTransfer.getDescriere().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(newTransfer);
            }

        }
        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
        }else{
            recycleviewAdapter.setFilteredList(filteredList);

        }


    }

//    public List<NewTransfer> getList(){
//
//        if(newTransferList.size()!=0){
//            Log.d("verif","ARE ELEM"+newTransferList);
//        }
//        return newTransferList;
//    }

    private void insertTransferInDB(List<NewTransfer> newTransferList) {

        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());

        for(NewTransfer n: newTransferList){
            newTransferDP.getNewTransferDAO().addTransfer(n);
        }


    }


    private void insertInDB(NewTransfer newTransfer){
        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        newTransferDP.getNewTransferDAO().addTransfer(newTransfer);
    }

    public void loadFromDB(){
        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        List<NewTransfer>transferFromDB = newTransferDP.getNewTransferDAO().getAllTransfers();
        for(NewTransfer n:transferFromDB){
            newTransferList.add(n);
        }
        recycleviewAdapter.notifyDataSetChanged();
    }

    public void deleteFromDB(NewTransfer newTransfer){
        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        newTransferDP.getNewTransferDAO().deleteTransfer(newTransfer);

        recycleviewAdapter.notifyDataSetChanged();
    }

    public void deleteALL(){
        NewTransferDP newTransferDP = NewTransferDP.getInstance(getContext());
        newTransferDP.getNewTransferDAO().deleteAllTransfers();
    }






    @Override
    public void onClick(View v) {

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();


            if (direction == ItemTouchHelper.LEFT) {
                deletedTransfer = newTransferList.get(position).getDescriere();
                newTransfer1 = newTransferList.get(position);


                newTransferList.remove(position);
                recycleviewAdapter.notifyItemRemoved(position);

                Snackbar.make(recyclerView, deletedTransfer, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newTransferList.add(position, newTransfer1);
                        recycleviewAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };






//    @Override
//    public void sendInput(NewTransfer newTransfer) {
//        newTransferList.add(newTransfer);
//        RecycleviewAdapter adapter = (RecycleviewAdapter) recyclerView.getAdapter();
//        adapter.notifyDataSetChanged();
//    }
}

