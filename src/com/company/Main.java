package com.company;

import com.company.com.company.KalmanFilter.ArrayParse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayParse arrayParse = new ArrayParse();


       for (int i = 0; i < arrayParse.arrayEmptyCells().size(); i++) {
            System.out.println(arrayParse.arrayEmptyCells().get(i));
        }

        for (int i = 0; i < arrayParse.arrayEmptyCells().size();i++){
            for(int k = 0; k < arrayParse.arrayEmptyCells().get(i);k++){
                System.out.println(arrayParse.createListOfLists().get(k));
            }
        }

       /* FileInputStream fis = new FileInputStream("C:/Users/UrBrain/Desktop/excel/Dannye_axelerometr_giroskop.xls");
        Workbook wb = new HSSFWorkbook(fis);
        ArrayList<Double> list = new ArrayList<>();
        fis.close();
        System.out.println(wb.getSheetAt(0).getLastRowNum());
*/
    }
}
