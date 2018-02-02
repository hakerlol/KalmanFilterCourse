package com.company.com.company.KalmanFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ArrayParse {

    private double[] elements;
    private Workbook wb;

    public ArrayParse() throws IOException {
        FileInputStream fis = new FileInputStream("C:/Users/UrBrain/Desktop/excel/Dannye_axelerometr_giroskop.xls");
        wb = new HSSFWorkbook(fis);
        fis.close();
    }

    public double[] arrayEmptyCells() {
        int newStart = 0;

        for (int i = 1; i < wb.getSheetAt(0).getLastRowNum(); i++) {
            if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)))) {
                newStart = i;
                break;
            }
        }
        elements = new double[newStart];
        for (int i = 1; i < newStart; i++) {
            elements[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)));
        }
        return elements;
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
    @Override
    public String toString() {
        return "{" + Arrays.toString(elements) + "}";
    }
}
