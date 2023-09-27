package com.example.bankingapp.customizedSpinner;

import java.util.ArrayList;
import java.util.List;

public class DataFurnizor {
    public static List<Furnizor> getFurnizorList(){
        List<Furnizor> furnizorList = new ArrayList<>();

        Furnizor enel = new Furnizor();
        enel.setNume("Enel");
        furnizorList.add(enel);

        Furnizor digi = new Furnizor();
        digi.setNume("Digi");
        furnizorList.add(digi);

        Furnizor apaNova = new Furnizor();
        apaNova.setNume("ApaNova");
        furnizorList.add(apaNova);

        Furnizor orange = new Furnizor();
        orange.setNume("Orange");
        furnizorList.add(orange);

        Furnizor vodafone = new Furnizor();
        vodafone.setNume("Vodafone");
        furnizorList.add(vodafone);

        Furnizor engie = new Furnizor();
        engie.setNume("Engie");
        furnizorList.add(engie);

        return furnizorList;


    }
}
