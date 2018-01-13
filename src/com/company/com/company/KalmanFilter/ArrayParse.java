package com.company.com.company.KalmanFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayParse {
    ArrayList<Integer> list = new ArrayList<>();
    Workbook wb;

    public ArrayParse() throws IOException {
        FileInputStream fis = new FileInputStream("C:/Users/UrBrain/Desktop/excel/Dannye_axelerometr_giroskop.xls");
         wb = new HSSFWorkbook(fis);
        fis.close();
    }

    public ArrayList<Integer> arrayEmptyCells() {
        ArrayList<Integer> amountOfEmpties = new ArrayList<>();

        for (int i = 1; i < wb.getSheetAt(0).getLastRowNum(); i++) {
            if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)))) {
                amountOfEmpties.add(i);
            }
        }
        return amountOfEmpties;
    }

    public ArrayList createListOfLists() {
        ArrayList<ArrayList<Double>> arrayList = new ArrayList<>();
        for (int i = 0; i < arrayEmptyCells().size(); i++) {
            arrayList.add(new ArrayList<>());
        }

        for (int i = 0; i < arrayEmptyCells().size(); i++){
            for(int k = 0; k < arrayEmptyCells().get(i);k++){
                arrayList.get(i).add(Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(k).getCell(0))));
            }
        }

        return arrayList;
    }



    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getCellText(Cell cell) {
        String result = "";
        try {
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    result = (cell.getRichStringCellValue().getString());
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        result = cell.getDateCellValue().toString();
                    } else {
                        result = Double.toString(cell.getNumericCellValue());
                    }
                    break;
                default:
                    break;
            }
            return result;
        } catch (NullPointerException e) {
            return "";
        }
    }
}
