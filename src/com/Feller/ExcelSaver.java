package com.Feller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelSaver implements Runnable {
    //  Workbook workbook = new HSSFWorkbook();
    // создание листа с названием "Просто лист"
    // Sheet sheet = workbook.createSheet("Просто лист");

    // заполняем список какими-то данными
    //  List<DataModel> dataList = fillData();

    // счетчик для строк

    // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
    private Workbook saveWorkbook = new HSSFWorkbook();

    public void createAndSaveData(Workbook workbook, int lastRowNum, double[] elementsAX, double[] elementsAY, double[] elementsAZ, double[] elementsGX, double[] elementsGY, double[] elementsGZ) {
        workbook.createSheet();
        SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
        Reader.DoingKalman doingKalman = new Reader.DoingKalman();
        Row row = workbook.getSheetAt(0).createRow(0);
        System.out.println("Началось создание файла");
        row.createCell(0).setCellValue("AX");
        row.createCell(1).setCellValue("AY");
        row.createCell(2).setCellValue("AZ");
        row.createCell(3).setCellValue("AX после фильтрации по Калману");
        row.createCell(4).setCellValue("AY после фильтрации по Калману");
        row.createCell(5).setCellValue("AZ после фильтрации по Калману");
        row.createCell(6).setCellValue("AX после фильтрации методом скользящего среднего с коэффициентом 3");
        row.createCell(7).setCellValue("AY после фильтрации методом скользящего среднего с коэффициентом 3");
        row.createCell(8).setCellValue("AZ после фильтрации методом скользящего среднего с коэффициентом 3");
        row.createCell(9).setCellValue("AX после фильтрации методом скользящего среднего с коэффициентом 5");
        row.createCell(10).setCellValue("AY после фильтрации методом скользящего среднего с коэффициентом 5");
        row.createCell(11).setCellValue("AZ после фильтрации методом скользящего среднего с коэффициентом 5");
        row.createCell(12).setCellValue("AX после фильтрации методом скользящего среднего с коэффициентом 7");
        row.createCell(13).setCellValue("AY после фильтрации методом скользящего среднего с коэффициентом 7");
        row.createCell(14).setCellValue("AZ после фильтрации методом скользящего среднего с коэффициентом 7");
        row.createCell(15).setCellValue("GX");
        row.createCell(16).setCellValue("GY");
        row.createCell(17).setCellValue("GZ");
        row.createCell(18).setCellValue("GX после фильтрации по Калману");
        row.createCell(19).setCellValue("GY после фильтрации по Калману");
        row.createCell(20).setCellValue("GZ после фильтрации по Калману");
        row.createCell(21).setCellValue("GX после фильтрации методом скользящего среднего с коэффициентом 3");
        row.createCell(22).setCellValue("GY после фильтрации методом скользящего среднего с коэффициентом 3");
        row.createCell(23).setCellValue("GZ после фильтрации методом скользящего среднего с коэффициентом 3");
        row.createCell(24).setCellValue("GX после фильтрации методом скользящего среднего с коэффициентом 5");
        row.createCell(25).setCellValue("GY после фильтрации методом скользящего среднего с коэффициентом 5");
        row.createCell(26).setCellValue("GZ после фильтрации методом скользящего среднего с коэффициентом 5");
        row.createCell(27).setCellValue("GX после фильтрации методом скользящего среднего с коэффициентом 7");
        row.createCell(28).setCellValue("GY после фильтрации методом скользящего среднего с коэффициентом 7");
        row.createCell(29).setCellValue("GZ после фильтрации методом скользящего среднего с коэффициентом 7");
        System.out.println("first rom is created");
        for (int i = 0; i < lastRowNum; i++) {
            Reader.elementsAXAfterKalman[i] = doingKalman.makeKalman(elementsAX)[i];
            Reader.elementsAYAfterKalman[i] = doingKalman.makeKalman(elementsAY)[i];
            Reader.elementsAZAfterKalman[i] = doingKalman.makeKalman(elementsAZ)[i];
            Reader.elementsGXAfterKalman[i] = doingKalman.makeKalman(elementsGX)[i];
            Reader.elementsGYAfterKalman[i] = doingKalman.makeKalman(elementsGY)[i];
            Reader.elementsGZAfterKalman[i] = doingKalman.makeKalman(elementsGZ)[i];

            Reader.elementsAXAfterSmooth3[i] = simpleMovingAverage.Smooth(elementsAX, 3)[i];
            Reader.elementsAYAfterSmooth3[i] = simpleMovingAverage.Smooth(elementsAY, 3)[i];
            Reader.elementsAZAfterSmooth3[i] = simpleMovingAverage.Smooth(elementsAZ, 3)[i];
            Reader.elementsGXAfterSmooth3[i] = simpleMovingAverage.Smooth(elementsGX, 3)[i];
            Reader.elementsGYAfterSmooth3[i] = simpleMovingAverage.Smooth(elementsGY, 3)[i];
            Reader.elementsGZAfterSmooth3[i] = simpleMovingAverage.Smooth(elementsGZ, 3)[i];

            Reader.elementsAXAfterSmooth5[i] = simpleMovingAverage.Smooth(elementsAX, 5)[i];
            Reader.elementsAYAfterSmooth5[i] = simpleMovingAverage.Smooth(elementsAY, 5)[i];
            Reader.elementsAZAfterSmooth5[i] = simpleMovingAverage.Smooth(elementsAZ, 5)[i];
            Reader.elementsGXAfterSmooth5[i] = simpleMovingAverage.Smooth(elementsGX, 5)[i];
            Reader.elementsGYAfterSmooth5[i] = simpleMovingAverage.Smooth(elementsGY, 5)[i];
            Reader.elementsGZAfterSmooth5[i] = simpleMovingAverage.Smooth(elementsGZ, 5)[i];

            Reader.elementsAXAfterSmooth7[i] = simpleMovingAverage.Smooth(elementsAX, 7)[i];
            Reader.elementsAYAfterSmooth7[i] = simpleMovingAverage.Smooth(elementsAY, 7)[i];
            Reader.elementsAZAfterSmooth7[i] = simpleMovingAverage.Smooth(elementsAZ, 7)[i];
            Reader.elementsGXAfterSmooth7[i] = simpleMovingAverage.Smooth(elementsGX, 7)[i];
            Reader.elementsGYAfterSmooth7[i] = simpleMovingAverage.Smooth(elementsGY, 7)[i];
            Reader.elementsGZAfterSmooth7[i] = simpleMovingAverage.Smooth(elementsGZ, 7)[i];
        }
        System.out.println("data is filtered");
        for (int i = 1; i < lastRowNum; i++) {
            row = workbook.getSheetAt(0).createRow(i);
            row.createCell(0).setCellValue(elementsAX[i - 1]);
            row.createCell(1).setCellValue(elementsAY[i - 1]);
            row.createCell(2).setCellValue(elementsAZ[i - 1]);
            row.createCell(3).setCellValue(Reader.elementsAXAfterKalman[i - 1]);
            row.createCell(4).setCellValue(Reader.elementsAYAfterKalman[i - 1]);
            row.createCell(5).setCellValue(Reader.elementsAZAfterKalman[i - 1]);
            row.createCell(6).setCellValue(Reader.elementsAXAfterSmooth3[i - 1]);
            row.createCell(7).setCellValue(Reader.elementsAYAfterSmooth3[i - 1]);
            row.createCell(8).setCellValue(Reader.elementsAZAfterSmooth3[i - 1]);
            row.createCell(9).setCellValue(Reader.elementsAXAfterSmooth5[i - 1]);
            row.createCell(10).setCellValue(Reader.elementsAYAfterSmooth5[i - 1]);
            row.createCell(11).setCellValue(Reader.elementsAZAfterSmooth5[i - 1]);
            row.createCell(12).setCellValue(Reader.elementsAZAfterSmooth7[i - 1]);
            row.createCell(13).setCellValue(Reader.elementsAZAfterSmooth7[i - 1]);
            row.createCell(14).setCellValue(Reader.elementsAZAfterSmooth7[i - 1]);
            row.createCell(15).setCellValue(elementsGX[i - 1]);
            row.createCell(16).setCellValue(elementsGY[i - 1]);
            row.createCell(17).setCellValue(elementsGZ[i - 1]);
            row.createCell(18).setCellValue(Reader.elementsGXAfterKalman[i - 1]);
            row.createCell(19).setCellValue(Reader.elementsGYAfterKalman[i - 1]);
            row.createCell(20).setCellValue(Reader.elementsGZAfterKalman[i - 1]);
            row.createCell(21).setCellValue(Reader.elementsGXAfterSmooth3[i - 1]);
            row.createCell(22).setCellValue(Reader.elementsGYAfterSmooth3[i - 1]);
            row.createCell(23).setCellValue(Reader.elementsGZAfterSmooth3[i - 1]);
            row.createCell(24).setCellValue(Reader.elementsGXAfterSmooth5[i - 1]);
            row.createCell(25).setCellValue(Reader.elementsGYAfterSmooth5[i - 1]);
            row.createCell(26).setCellValue(Reader.elementsGZAfterSmooth5[i - 1]);
            row.createCell(27).setCellValue(Reader.elementsGZAfterSmooth7[i - 1]);
            row.createCell(28).setCellValue(Reader.elementsGZAfterSmooth7[i - 1]);
            row.createCell(29).setCellValue(Reader.elementsGZAfterSmooth7[i - 1]);
        }
        System.out.println("table is filled");

    }

    @Override
    public void run() {
        createAndSaveData(Reader.saveWorkbook, Reader.lastRowNum, Reader.elementsAX, Reader.elementsAY, Reader.elementsAZ, Reader.elementsGX, Reader.elementsGY, Reader.elementsGZ);
    }
}
