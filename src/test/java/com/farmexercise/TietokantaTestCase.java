package com.farmexercise;

import javax.annotation.Resource;

import com.farmexercise.Repository.FarmRepository;
import com.farmexercise.Repository.FileObjectRepository;
import com.farmexercise.Repository.MeasurementRepository;

import org.junit.Test;

public class TietokantaTestCase {
    @Resource
    private FileObjectRepository fileObjectRepository;

    @Resource
    private FarmRepository farmRepository;

    @Resource
    private MeasurementRepository measurementRepository;
    
    // Onko tietokantaa luotu
    @Test
    public void onkoTietokantaLuotu() {
       
        
    }

    // Onko tietokantataulut luotu
    // Fileobject -taulu
    @Test
    public void onkoTietokantatauluFileobjectLuotu() {
        

    }

    // Measurement -taulu
    @Test
    public void onkoTietokantatauluMeasurementLuotu() {


    
        
    }

    // Farm -taulu
    @Test
    public void onkoTietokantatauluFarmLuotu() {


     
        
    }
}
