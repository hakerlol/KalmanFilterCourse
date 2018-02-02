package com.company;

import com.company.com.company.KalmanFilter.Reader;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        Reader reader = new Reader("Прога");
        reader.pack();

        reader.setVisible(true);
        reader.setSize(400, 300);
        reader.setResizable(false);
        reader.setLocationRelativeTo(null);


     /*   for (int i = 0; i < arrayParse.arrayEmptyCells().length; i++) {
            System.out.println(arrayParse.arrayEmptyCells()[i]);
        }*/

       /* for (int i = 0; i < arrayParse.arrayEmptyCells().size();i++){
            for(int k = 0; k < arrayParse.arrayEmptyCells().get(i);k++){
                System.out.println(arrayParse.createListOfLists().get(k));
            }
        }*/

     /*   FileInputStream fis = new FileInputStream("C:/Users/UrBrain/Desktop/excel/Dannye_axelerometr_giroskop.xls");
        Workbook wb = new HSSFWorkbook(fis);
        ArrayList<Double> list = new ArrayList<>();
        fis.close();
        System.out.println(wb.getSheetAt(0).getLastRowNum());*/
    }
}
