package com.Feller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Reader extends JFrame {

    static class DoingKalman {
        KalmanFilterSimple1D kalman = new KalmanFilterSimple1D(2, 15, 1, 1);

        public double[] makeKalman(double[] array) {
            double[] filtered = new double[array.length];
            kalman.SetState(array[0], 0.1); // Задаем начальные значение State и Covariance
            for (int i = 0; i < array.length; i++) {
                kalman.Correct(array[i]);
                filtered[i] = kalman.State;
            }
            return filtered;
        }

    }

    public static JButton b1;
    public static JButton b2;
    public static JButton b3;
    public static JButton b4;
    public static JButton b5;
    public static JCheckBoxMenuItem AX = new JCheckBoxMenuItem("AX");
    public static JCheckBoxMenuItem AY = new JCheckBoxMenuItem("AY");
    public static JCheckBoxMenuItem AZ = new JCheckBoxMenuItem("AZ");
    public static JCheckBoxMenuItem AXafterFiltration = new JCheckBoxMenuItem("AX отфильтрованный");
    public static JCheckBoxMenuItem AYafterFiltration = new JCheckBoxMenuItem("AY отфильтрованный");
    public static JCheckBoxMenuItem AZafterFiltration = new JCheckBoxMenuItem("AZ отфильтрованный");
    public static JCheckBoxMenuItem GX = new JCheckBoxMenuItem("GX");
    public static JCheckBoxMenuItem GY = new JCheckBoxMenuItem("GY");
    public static JCheckBoxMenuItem GZ = new JCheckBoxMenuItem("GZ");
    public static JCheckBoxMenuItem GXafterFiltration = new JCheckBoxMenuItem("GX отфильтрованный");
    public static JCheckBoxMenuItem GYafterFiltration = new JCheckBoxMenuItem("GY отфильтрованный");
    public static JCheckBoxMenuItem GZafterFiltration = new JCheckBoxMenuItem("GZ отфильтрованный");
    public static JCheckBoxMenuItem pointsAX = new JCheckBoxMenuItem("Точки AX");
    public static JCheckBoxMenuItem pointsAY = new JCheckBoxMenuItem("Точки AY");
    public static JCheckBoxMenuItem pointsAZ = new JCheckBoxMenuItem("Точки AZ");
    public static JCheckBoxMenuItem pointsAXafterFiltration = new JCheckBoxMenuItem("Точки AX после фильтрации");
    public static JCheckBoxMenuItem pointsAYafterFiltration = new JCheckBoxMenuItem("Точки AY после фильтрации");
    public static JCheckBoxMenuItem pointsAZafterFiltration = new JCheckBoxMenuItem("Точки AZ после фильтрации");
    public static JCheckBoxMenuItem pointsGX = new JCheckBoxMenuItem("Точки GX");
    public static JCheckBoxMenuItem pointsGY = new JCheckBoxMenuItem("Точки GY");
    public static JCheckBoxMenuItem pointsGZ = new JCheckBoxMenuItem("Точки GZ");
    public static JCheckBoxMenuItem pointsGXafterFiltration = new JCheckBoxMenuItem("Точки GX после фильтрации");
    public static JCheckBoxMenuItem pointsGYafterFiltration = new JCheckBoxMenuItem("Точки GY после фильтрации");
    public static JCheckBoxMenuItem pointsGZafterFiltration = new JCheckBoxMenuItem("Точки GZ после фильтрации");
    public static JComboBox<Integer> smoothWindowTypeA = new JComboBox<>();
    public static JComboBox<Integer> smoothWindowTypeG = new JComboBox<>();

    public static double[] elementsAX;
    public static double[] elementsAY;
    public static double[] elementsAZ;

    public static double[] elementsGX;
    public static double[] elementsGY;
    public static double[] elementsGZ;

    public static double[] elementsAXAfterKalman;
    public static double[] elementsAYAfterKalman;
    public static double[] elementsAZAfterKalman;

    public static double[] elementsGXAfterKalman;
    public static double[] elementsGYAfterKalman;
    public static double[] elementsGZAfterKalman;

    public static double[] elementsAXAfterSmooth3;
    public static double[] elementsAYAfterSmooth3;
    public static double[] elementsAZAfterSmooth3;

    public static double[] elementsAXAfterSmooth5;
    public static double[] elementsAYAfterSmooth5;
    public static double[] elementsAZAfterSmooth5;

    public static double[] elementsAXAfterSmooth7;
    public static double[] elementsAYAfterSmooth7;
    public static double[] elementsAZAfterSmooth7;

    public static double[] elementsGXAfterSmooth3;
    public static double[] elementsGYAfterSmooth3;
    public static double[] elementsGZAfterSmooth3;

    public static double[] elementsGXAfterSmooth5;
    public static double[] elementsGYAfterSmooth5;
    public static double[] elementsGZAfterSmooth5;

    public static double[] elementsGXAfterSmooth7;
    public static double[] elementsGYAfterSmooth7;
    public static double[] elementsGZAfterSmooth7;

    private boolean isOpen = false;
    private boolean isDataSaved = false;

    private Workbook wb;
    public static Workbook saveWorkbook = new XSSFWorkbook();
    public static int lastRowNum = 0;


    public Reader(String s) {
        super(s);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
        JLabel windowDescriptionA = new JLabel("Выберите размер окна фильтрации");
        JLabel windowDescriptionG = new JLabel("Выберите размер окна фильтрации");

        b1 = new JButton("Отфильтровать по Калману данные A()");
        b2 = new JButton("Отфильтровать по Калману данные G()");
        b3 = new JButton("Отфильтровать методом скользящего среднего данные А()");
        b4 = new JButton("Отфильтровать методом скользящего среднего данные G()");
        b5 = new JButton("Обработать и сохранить данные");

        for (int i = 3; i <= 7; ) {
            smoothWindowTypeA.addItem(i);
            smoothWindowTypeG.addItem(i);
            i += 2;
        }
        add(b5);
        add(b1);
        add(b2);
        add(b3);
        add(windowDescriptionA);
        add(smoothWindowTypeA);
        add(b4);
        add(windowDescriptionG);
        add(smoothWindowTypeG);
        Reader.eHandler eHandler = new eHandler();
        b2.addActionListener(eHandler);
        b1.addActionListener(eHandler);
        b3.addActionListener(eHandler);
        b4.addActionListener(eHandler);
        b5.addActionListener(eHandler);

    }


    private JMenu createFileMenu() {
        // Создание выпадающего меню
        JMenu file = new JMenu("Файл");

        // Пункт меню "Открыть" с изображением
        JMenuItem open = new JMenuItem("Открыть",
                new ImageIcon("images/open.png"));
        // Пункт меню из команды с выходом из программы
        JMenuItem exit = new JMenuItem(new ExitAction());
        // Добавление к пункту меню изображения
        exit.setIcon(new ImageIcon("images/exit.png"));
        // Добавим в меню пункта open
        file.add(open);
        // Добавление разделителя
        file.addSeparator();
        file.add(exit);

        open.addActionListener(arg0 -> {
            open();
        });
        return file;
    }

    private void open() {
        System.out.println("ActionListener.actionPerformed : open");
        JFileChooser fileOpen = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Word & Excel", "xlsx");
        fileOpen.setFileFilter(filter);
        int ret = fileOpen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file1 = fileOpen.getSelectedFile();
            try {
                FileInputStream fis = new FileInputStream(file1);
                wb = new XSSFWorkbook(fis);
                fis.close();
                readingData();
                JOptionPane.showMessageDialog(null, "Данные считаны");

                isOpen = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Файл содержит некорректные данные. Выберите другой файл.");
            }
        }
    }

    private JMenu createGraphAXMenu() {
        JMenu settings = new JMenu("Настройки");

        settings.add(AX);
        settings.addSeparator();
        settings.add(AY);
        settings.addSeparator();
        settings.add(AZ);
        settings.addSeparator();
        settings.add(AXafterFiltration);
        settings.addSeparator();
        settings.add(AYafterFiltration);
        settings.addSeparator();
        settings.add(AZafterFiltration);
        settings.addSeparator();
        settings.add(pointsAX);
        settings.addSeparator();
        settings.add(pointsAY);
        settings.addSeparator();
        settings.add(pointsAZ);
        settings.addSeparator();
        settings.add(pointsAXafterFiltration);
        settings.addSeparator();
        settings.add(pointsAYafterFiltration);
        settings.addSeparator();
        settings.add(pointsAZafterFiltration);

        return settings;
    }

    private JMenu createGraphGXMenu() {
        JMenu settings = new JMenu("Настройки");

        settings.add(GX);
        settings.addSeparator();
        settings.add(GY);
        settings.addSeparator();
        settings.add(GZ);
        settings.addSeparator();
        settings.add(GXafterFiltration);
        settings.addSeparator();
        settings.add(GYafterFiltration);
        settings.addSeparator();
        settings.add(GZafterFiltration);
        settings.addSeparator();
        settings.add(pointsGX);
        settings.addSeparator();
        settings.add(pointsGY);
        settings.addSeparator();
        settings.add(pointsGZ);
        settings.addSeparator();
        settings.add(pointsGXafterFiltration);
        settings.addSeparator();
        settings.add(pointsGYafterFiltration);
        settings.addSeparator();
        settings.add(pointsGZafterFiltration);

        return settings;
    }

    class ExitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        ExitAction() {
            putValue(NAME, "Выход");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private void readingData() {
        lastRowNum = wb.getSheetAt(0).getLastRowNum();
        for (int i = 1; i <= wb.getSheetAt(0).getLastRowNum(); i++) {
            if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)))) {
                lastRowNum = i - 1;
                break;
            }
        }
        System.out.println("create AX");
        elementsAX = new double[lastRowNum];
        System.out.println("create AY");
        elementsAY = new double[lastRowNum];
        System.out.println("create AZ");
        elementsAZ = new double[lastRowNum];
        elementsGX = new double[lastRowNum];
        elementsGY = new double[lastRowNum];
        elementsGZ = new double[lastRowNum];
        elementsAXAfterKalman = new double[lastRowNum];
        elementsAYAfterKalman = new double[lastRowNum];
        elementsAZAfterKalman = new double[lastRowNum];
        elementsGXAfterKalman = new double[lastRowNum];
        elementsGYAfterKalman = new double[lastRowNum];
        elementsGZAfterKalman = new double[lastRowNum];
        elementsAXAfterSmooth3 = new double[lastRowNum];
        elementsAYAfterSmooth3 = new double[lastRowNum];
        elementsAZAfterSmooth3 = new double[lastRowNum];
        elementsGXAfterSmooth3 = new double[lastRowNum];
        elementsGYAfterSmooth3 = new double[lastRowNum];
        elementsGZAfterSmooth3 = new double[lastRowNum];
        elementsAXAfterSmooth5 = new double[lastRowNum];
        elementsAYAfterSmooth5 = new double[lastRowNum];
        elementsAZAfterSmooth5 = new double[lastRowNum];
        elementsGXAfterSmooth5 = new double[lastRowNum];
        elementsGYAfterSmooth5 = new double[lastRowNum];
        elementsGZAfterSmooth5 = new double[lastRowNum];
        elementsAXAfterSmooth7 = new double[lastRowNum];
        elementsAYAfterSmooth7 = new double[lastRowNum];
        elementsAZAfterSmooth7 = new double[lastRowNum];
        elementsGXAfterSmooth7 = new double[lastRowNum];
        elementsGYAfterSmooth7 = new double[lastRowNum];
        elementsGZAfterSmooth7 = new double[lastRowNum];

        System.out.println("all arr war created");

        for (int i = 1; i <= lastRowNum; i++) {
            elementsAX[i - 1] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(0))) / 16384 * 9.8;
            elementsAY[i - 1] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(1))) / 16384 * 9.8;
            elementsAZ[i - 1] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(2))) / 16384 * 9.8;
            elementsGX[i - 1] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(3))) / 131;
            elementsGY[i - 1] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(4))) / 131;
            elementsGZ[i - 1] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(5))) / 131;
            System.out.println("data filled");
        }

    }

    private XYDataset createDatasetTypeA() {
        System.out.println("Start of A method");
        System.out.println("The end of method");
        final XYSeries AX = new XYSeries("AX");
        final XYSeries AY = new XYSeries("AY");
        final XYSeries AZ = new XYSeries("AZ");

        final XYSeries AXafterFiltration = new XYSeries("AX после фильтрации");
        final XYSeries AYafterFiltration = new XYSeries("AY после фильтрации");
        final XYSeries AZafterFiltration = new XYSeries("AZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            AX.add(i, elementsAX[i]);
            AY.add(i, elementsAY[i]);
            AZ.add(i, elementsAZ[i]);

            AXafterFiltration.add(i, elementsAXAfterKalman[i]);
            AYafterFiltration.add(i, elementsAYAfterKalman[i]);
            AZafterFiltration.add(i, elementsAZAfterKalman[i]);

        }

        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(AX);
        dataSet.addSeries(AY);
        dataSet.addSeries(AZ);

        dataSet.addSeries(AXafterFiltration);
        dataSet.addSeries(AYafterFiltration);
        dataSet.addSeries(AZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createDatasetTypeG() {
        System.out.println("Start of method");
        System.out.println("The end of method");

        final XYSeries GX = new XYSeries("GX");
        final XYSeries GY = new XYSeries("GY");
        final XYSeries GZ = new XYSeries("GZ");


        final XYSeries GXafterFiltration = new XYSeries("GX после фильтрации");
        final XYSeries GYafterFiltration = new XYSeries("GY после фильтрации");
        final XYSeries GZafterFiltration = new XYSeries("GZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {

            GX.add(i, elementsGX[i]);
            GY.add(i, elementsGY[i]);
            GZ.add(i, elementsGZ[i]);

            GXafterFiltration.add(i, elementsGXAfterKalman[i]);
            GYafterFiltration.add(i, elementsGYAfterKalman[i]);
            GZafterFiltration.add(i, elementsGZAfterKalman[i]);
        }

        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();

        dataSet.addSeries(GX);
        dataSet.addSeries(GY);
        dataSet.addSeries(GZ);

        dataSet.addSeries(GXafterFiltration);
        dataSet.addSeries(GYafterFiltration);
        dataSet.addSeries(GZafterFiltration);

        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createSmoothFilterDataTypeA3() {
        System.out.println("Start of method");
        System.out.println("The end of method");
        final XYSeries AX = new XYSeries("AX");
        final XYSeries AY = new XYSeries("AY");
        final XYSeries AZ = new XYSeries("AZ");

        final XYSeries AXafterFiltration = new XYSeries("AX после фильтрации");
        final XYSeries AYafterFiltration = new XYSeries("AY после фильтрации");
        final XYSeries AZafterFiltration = new XYSeries("AZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            AX.add(i, elementsAX[i]);
            AY.add(i, elementsAY[i]);
            AZ.add(i, elementsAZ[i]);

            AXafterFiltration.add(i, elementsAXAfterSmooth3[i]);
            AYafterFiltration.add(i, elementsAYAfterSmooth3[i]);
            AZafterFiltration.add(i, elementsAZAfterSmooth3[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(AX);
        dataSet.addSeries(AY);
        dataSet.addSeries(AZ);

        dataSet.addSeries(AXafterFiltration);
        dataSet.addSeries(AYafterFiltration);
        dataSet.addSeries(AZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createSmoothFilterDataTypeA5() {
        System.out.println("Start of method");
        System.out.println("The end of method");
        final XYSeries AX = new XYSeries("AX");
        final XYSeries AY = new XYSeries("AY");
        final XYSeries AZ = new XYSeries("AZ");

        final XYSeries AXafterFiltration = new XYSeries("AX после фильтрации");
        final XYSeries AYafterFiltration = new XYSeries("AY после фильтрации");
        final XYSeries AZafterFiltration = new XYSeries("AZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            AX.add(i, elementsAX[i]);
            AY.add(i, elementsAY[i]);
            AZ.add(i, elementsAZ[i]);

            AXafterFiltration.add(i, elementsAXAfterSmooth5[i]);
            AYafterFiltration.add(i, elementsAYAfterSmooth5[i]);
            AZafterFiltration.add(i, elementsAZAfterSmooth5[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(AX);
        dataSet.addSeries(AY);
        dataSet.addSeries(AZ);

        dataSet.addSeries(AXafterFiltration);
        dataSet.addSeries(AYafterFiltration);
        dataSet.addSeries(AZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createSmoothFilterDataTypeA7() {
        System.out.println("Start of method");
        System.out.println("The end of method");
        final XYSeries AX = new XYSeries("AX");
        final XYSeries AY = new XYSeries("AY");
        final XYSeries AZ = new XYSeries("AZ");

        final XYSeries AXafterFiltration = new XYSeries("AX после фильтрации");
        final XYSeries AYafterFiltration = new XYSeries("AY после фильтрации");
        final XYSeries AZafterFiltration = new XYSeries("AZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            AX.add(i, elementsAX[i]);
            AY.add(i, elementsAY[i]);
            AZ.add(i, elementsAZ[i]);

            AXafterFiltration.add(i, elementsAXAfterSmooth7[i]);
            AYafterFiltration.add(i, elementsAYAfterSmooth7[i]);
            AZafterFiltration.add(i, elementsAZAfterSmooth7[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(AX);
        dataSet.addSeries(AY);
        dataSet.addSeries(AZ);

        dataSet.addSeries(AXafterFiltration);
        dataSet.addSeries(AYafterFiltration);
        dataSet.addSeries(AZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createSmoothFilterDataTypeG3() {
        System.out.println("Start of method");
        System.out.println("The end of method");
        final XYSeries GX = new XYSeries("GX");
        final XYSeries GY = new XYSeries("GY");
        final XYSeries GZ = new XYSeries("GZ");

        final XYSeries GXafterFiltration = new XYSeries("GX после фильтрации");
        final XYSeries GYafterFiltration = new XYSeries("GY после фильтрации");
        final XYSeries GZafterFiltration = new XYSeries("GZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            GX.add(i, elementsGX[i]);
            GY.add(i, elementsGY[i]);
            GZ.add(i, elementsGZ[i]);

            GXafterFiltration.add(i, elementsGXAfterSmooth3[i]);
            GYafterFiltration.add(i, elementsGYAfterSmooth3[i]);
            GZafterFiltration.add(i, elementsGZAfterSmooth3[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(GX);
        dataSet.addSeries(GY);
        dataSet.addSeries(GZ);

        dataSet.addSeries(GXafterFiltration);
        dataSet.addSeries(GYafterFiltration);
        dataSet.addSeries(GZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createSmoothFilterDataTypeG5() {
        System.out.println("Start of method");
        System.out.println("The end of method");
        final XYSeries GX = new XYSeries("GX");
        final XYSeries GY = new XYSeries("GY");
        final XYSeries GZ = new XYSeries("GZ");

        final XYSeries GXafterFiltration = new XYSeries("GX после фильтрации");
        final XYSeries GYafterFiltration = new XYSeries("GY после фильтрации");
        final XYSeries GZafterFiltration = new XYSeries("GZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            GX.add(i, elementsGX[i]);
            GY.add(i, elementsGY[i]);
            GZ.add(i, elementsGZ[i]);

            GXafterFiltration.add(i, elementsGXAfterSmooth5[i]);
            GYafterFiltration.add(i, elementsGYAfterSmooth5[i]);
            GZafterFiltration.add(i, elementsGZAfterSmooth5[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(GX);
        dataSet.addSeries(GY);
        dataSet.addSeries(GZ);

        dataSet.addSeries(GXafterFiltration);
        dataSet.addSeries(GYafterFiltration);
        dataSet.addSeries(GZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private XYDataset createSmoothFilterDataTypeG7() {
        System.out.println("Start of method");
        System.out.println("The end of method");
        final XYSeries GX = new XYSeries("GX");
        final XYSeries GY = new XYSeries("GY");
        final XYSeries GZ = new XYSeries("GZ");

        final XYSeries GXafterFiltration = new XYSeries("GX после фильтрации");
        final XYSeries GYafterFiltration = new XYSeries("GY после фильтрации");
        final XYSeries GZafterFiltration = new XYSeries("GZ после фильтрации");

        for (int i = 0; i < lastRowNum; i++) {
            GX.add(i, elementsGX[i]);
            GY.add(i, elementsGY[i]);
            GZ.add(i, elementsGZ[i]);

            GXafterFiltration.add(i, elementsGXAfterSmooth7[i]);
            GYafterFiltration.add(i, elementsGYAfterSmooth7[i]);
            GZafterFiltration.add(i, elementsGZAfterSmooth7[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(GX);
        dataSet.addSeries(GY);
        dataSet.addSeries(GZ);

        dataSet.addSeries(GXafterFiltration);
        dataSet.addSeries(GYafterFiltration);
        dataSet.addSeries(GZafterFiltration);


        System.out.println("data is added");
        return dataSet;
    }

    private void setAFormsVisible() {
        AX.setState(true);
        AY.setState(true);
        AZ.setState(true);
        AXafterFiltration.setState(true);
        AYafterFiltration.setState(true);
        AZafterFiltration.setState(true);
        pointsAX.setState(false);
        pointsAY.setState(false);
        pointsAZ.setState(false);
        pointsAXafterFiltration.setState(false);
        pointsAYafterFiltration.setState(false);
        pointsAZafterFiltration.setState(false);
    }

    private void setGFormsVisible() {
        GX.setState(true);
        GY.setState(true);
        GZ.setState(true);
        GXafterFiltration.setState(true);
        GYafterFiltration.setState(true);
        GZafterFiltration.setState(true);
        pointsGX.setState(false);
        pointsGY.setState(false);
        pointsGZ.setState(false);
        pointsGXafterFiltration.setState(false);
        pointsGYafterFiltration.setState(false);
        pointsGZafterFiltration.setState(false);
    }

    private void setAActionListener(XYLineAndShapeRenderer renderer) {
        AX.addActionListener(e1 -> {
            if (GX.getState()) {
                renderer.setSeriesLinesVisible(0, true);
            } else {
                renderer.setSeriesLinesVisible(0, false);
            }
        });
        AY.addActionListener(e1 -> {
            if (GY.getState()) {
                renderer.setSeriesLinesVisible(1, true);
            } else {
                renderer.setSeriesLinesVisible(1, false);
            }
        });
        AZ.addActionListener(e1 -> {
            if (GZ.getState()) {
                renderer.setSeriesLinesVisible(2, true);
            } else {
                renderer.setSeriesLinesVisible(2, false);
            }
        });
        AXafterFiltration.addActionListener(e1 -> {
            if (GXafterFiltration.getState()) {
                renderer.setSeriesLinesVisible(3, true);
            } else {
                renderer.setSeriesLinesVisible(3, false);
            }
        });
        AYafterFiltration.addActionListener(e1 -> {
            if (GYafterFiltration.getState()) {
                renderer.setSeriesLinesVisible(4, true);
            } else {
                renderer.setSeriesLinesVisible(4, false);
            }
        });
        AZafterFiltration.addActionListener(e1 -> {
            if (GZafterFiltration.getState()) {
                renderer.setSeriesLinesVisible(5, true);

            } else {
                renderer.setSeriesLinesVisible(5, false);

            }
        });

        pointsAX.addActionListener(e1 -> {
            if (pointsAX.getState()) {
                renderer.setSeriesShapesVisible(0, true);

            } else {
                renderer.setSeriesShapesVisible(0, false);
            }
        });
        pointsAY.addActionListener(e1 -> {
            if (pointsAY.getState()) {
                renderer.setSeriesShapesVisible(1, true);

            } else {
                renderer.setSeriesShapesVisible(1, false);
            }
        });
        pointsAZ.addActionListener(e1 -> {
            if (pointsAZ.getState()) {
                renderer.setSeriesShapesVisible(2, true);

            } else {
                renderer.setSeriesShapesVisible(2, false);
            }
        });
        pointsAXafterFiltration.addActionListener(e1 -> {
            if (pointsAXafterFiltration.getState()) {
                renderer.setSeriesShapesVisible(3, true);

            } else {
                renderer.setSeriesShapesVisible(3, false);
            }
        });
        pointsAYafterFiltration.addActionListener(e1 -> {
            if (pointsAYafterFiltration.getState()) {
                renderer.setSeriesShapesVisible(4, true);

            } else {
                renderer.setSeriesShapesVisible(4, false);
            }
        });
        pointsAZafterFiltration.addActionListener(e1 -> {
            if (pointsAZafterFiltration.getState()) {
                renderer.setSeriesShapesVisible(5, true);

            } else {
                renderer.setSeriesShapesVisible(5, false);
            }
        });
    }

    private void setGActionListener(XYLineAndShapeRenderer renderer) {
        GX.addActionListener(e1 -> {
            if (GX.getState()) {
                renderer.setSeriesLinesVisible(0, true);
            } else {
                renderer.setSeriesLinesVisible(0, false);
            }
        });
        GY.addActionListener(e1 -> {
            if (GY.getState()) {
                renderer.setSeriesLinesVisible(1, true);
            } else {
                renderer.setSeriesLinesVisible(1, false);
            }
        });
        GZ.addActionListener(e1 -> {
            if (GZ.getState()) {
                renderer.setSeriesLinesVisible(2, true);
            } else {
                renderer.setSeriesLinesVisible(2, false);
            }
        });
        GXafterFiltration.addActionListener(e1 -> {
            if (GXafterFiltration.getState()) {
                renderer.setSeriesLinesVisible(3, true);
            } else {
                renderer.setSeriesLinesVisible(3, false);
            }
        });
        GYafterFiltration.addActionListener(e1 -> {
            if (GYafterFiltration.getState()) {
                renderer.setSeriesLinesVisible(4, true);
            } else {
                renderer.setSeriesLinesVisible(4, false);
            }
        });
        GZafterFiltration.addActionListener(e1 -> {
            if (GZafterFiltration.getState()) {
                renderer.setSeriesLinesVisible(5, true);

            } else {
                renderer.setSeriesLinesVisible(5, false);

            }
        });

        pointsGX.addActionListener(e1 -> {
            if (pointsGX.getState()) {
                renderer.setSeriesShapesVisible(0, true);

            } else {
                renderer.setSeriesShapesVisible(0, false);
            }
        });
        pointsGY.addActionListener(e1 -> {
            if (pointsGY.getState()) {
                renderer.setSeriesShapesVisible(1, true);

            } else {
                renderer.setSeriesShapesVisible(1, false);
            }
        });
        pointsGZ.addActionListener(e1 -> {
            if (pointsGZ.getState()) {
                renderer.setSeriesShapesVisible(2, true);

            } else {
                renderer.setSeriesShapesVisible(2, false);
            }
        });
        pointsGXafterFiltration.addActionListener(e1 -> {
            if (pointsGXafterFiltration.getState()) {
                renderer.setSeriesShapesVisible(3, true);

            } else {
                renderer.setSeriesShapesVisible(3, false);
            }
        });
        pointsGYafterFiltration.addActionListener(e1 -> {
            if (pointsGYafterFiltration.getState()) {
                renderer.setSeriesShapesVisible(4, true);

            } else {
                renderer.setSeriesShapesVisible(4, false);
            }
        });
        pointsGZafterFiltration.addActionListener(e1 -> {
            if (pointsGZafterFiltration.getState()) {
                renderer.setSeriesShapesVisible(5, true);

            } else {
                renderer.setSeriesShapesVisible(5, false);
            }
        });
    }

    public class eHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == b2) {
                    if (!isOpen) {
                        JOptionPane.showMessageDialog(null, "Выберите файл");
                        throw new FileNotFoundException("Выберите файл");
                    }
                    if (!isDataSaved) {
                        JOptionPane.showMessageDialog(null, "Сначала нужно обработать данные");
                        throw new FileNotFoundException("Обработайте данные");
                    }

                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                            "Фильтрация методом Калмана",
                            "Номер измеренения",
                            "град/сек",
                            createDatasetTypeG(),
                            PlotOrientation.VERTICAL,
                            true, true, false);

                    ChartPanel chartPanel = new ChartPanel(xyLineChart);
                    JFrame frame = new JFrame("График");
                    JMenuBar menuBar = new JMenuBar();
                    menuBar.add(createGraphGXMenu());
                    frame.setJMenuBar(menuBar);
                    frame.getContentPane().add(chartPanel);
                    frame.setSize(1280, 720);
                    frame.setVisible(true);

                    final XYPlot plot = xyLineChart.getXYPlot();
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setDefaultShapesVisible(false);
                    renderer.setSeriesPaint(0, Color.RED);
                    renderer.setSeriesPaint(1, Color.green);
                    renderer.setSeriesPaint(2, Color.black);
                    renderer.setSeriesPaint(3, Color.YELLOW);
                    renderer.setSeriesPaint(4, Color.ORANGE);
                    renderer.setSeriesPaint(5, Color.MAGENTA);

                    renderer.setSeriesStroke(0, new BasicStroke(1f));
                    renderer.setSeriesStroke(1, new BasicStroke(1f));
                    renderer.setSeriesStroke(2, new BasicStroke(1f));
                    renderer.setSeriesStroke(3, new BasicStroke(1f));
                    renderer.setSeriesStroke(4, new BasicStroke(1f));
                    renderer.setSeriesStroke(5, new BasicStroke(1f));

                    plot.setRenderer(renderer);
                    setGFormsVisible();
                    setGActionListener(renderer);

                    System.out.println("absolute end");
                }

                if (e.getSource() == b1) {
                    if (!isOpen) {
                        JOptionPane.showMessageDialog(null, "Выберите файл");
                        throw new FileNotFoundException("Выберите файл");
                    }
                    if (!isDataSaved) {
                        JOptionPane.showMessageDialog(null, "Сначала нужно обработать данные");
                        throw new FileNotFoundException("Обработайте данные");
                    }
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                            "Фильтрация методом Калмана",
                            "Номер измеренения",
                            "м/с^2",
                            createDatasetTypeA(),
                            PlotOrientation.VERTICAL,
                            true, true, false);
                    ChartPanel chartPanel = new ChartPanel(xyLineChart);
                    JFrame frame = new JFrame("График");
                    JMenuBar menuBar = new JMenuBar();
                    menuBar.add(createGraphAXMenu());
                    frame.setJMenuBar(menuBar);
                    frame.getContentPane().add(chartPanel);
                    frame.setSize(1280, 720);
                    frame.setVisible(true);

                    final XYPlot plot = xyLineChart.getXYPlot();
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setDefaultShapesVisible(false);
                    renderer.setSeriesPaint(0, Color.RED);
                    renderer.setSeriesPaint(1, Color.green);
                    renderer.setSeriesPaint(2, Color.black);
                    renderer.setSeriesPaint(3, Color.YELLOW);
                    renderer.setSeriesPaint(4, Color.ORANGE);
                    renderer.setSeriesPaint(5, Color.MAGENTA);

                    renderer.setSeriesStroke(0, new BasicStroke(1f));
                    renderer.setSeriesStroke(1, new BasicStroke(1f));
                    renderer.setSeriesStroke(2, new BasicStroke(1f));
                    renderer.setSeriesStroke(3, new BasicStroke(1f));
                    renderer.setSeriesStroke(4, new BasicStroke(1f));
                    renderer.setSeriesStroke(5, new BasicStroke(1f));

                    plot.setRenderer(renderer);

                    setAFormsVisible();
                    setAActionListener(renderer);

                    System.out.println("absolute end");
                }
                if (e.getSource() == b3) {
                    if (!isOpen) {
                        JOptionPane.showMessageDialog(null, "Выберите файл");
                        throw new FileNotFoundException("Выберите файл");
                    }
                    if (!isDataSaved) {
                        JOptionPane.showMessageDialog(null, "Сначала нужно обработать данные");
                        throw new FileNotFoundException("Обработайте данные");
                    }

                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    JFreeChart xyLineChart;
                    if ((int) smoothWindowTypeA.getSelectedItem() == 3) {
                        xyLineChart = ChartFactory.createXYLineChart(
                                "Фильтрация методом скользящего среднего",
                                "Номер измеренения",
                                "м/с^2",
                                createSmoothFilterDataTypeA3(),
                                PlotOrientation.VERTICAL,
                                true, true, false);
                    } else if ((int) smoothWindowTypeA.getSelectedItem() == 5) {
                        xyLineChart = ChartFactory.createXYLineChart(
                                "Фильтрация методом скользящего среднего",
                                "Номер измеренения",
                                "м/с^2",
                                createSmoothFilterDataTypeA5(),
                                PlotOrientation.VERTICAL,
                                true, true, false);
                    } else {
                        xyLineChart = ChartFactory.createXYLineChart(
                                "Фильтрация методом скользящего среднего",
                                "Номер измеренения",
                                "м/с^2",
                                createSmoothFilterDataTypeA7(),
                                PlotOrientation.VERTICAL,
                                true, true, false);
                    }
                    ChartPanel chartPanel = new ChartPanel(xyLineChart);
                    JFrame frame = new JFrame("График");
                    JMenuBar menuBar = new JMenuBar();
                    menuBar.add(createGraphAXMenu());
                    frame.setJMenuBar(menuBar);
                    frame.getContentPane().add(chartPanel);
                    frame.setSize(1280, 720);
                    frame.setVisible(true);

                    final XYPlot plot = xyLineChart.getXYPlot();
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setDefaultShapesVisible(false);
                    renderer.setSeriesPaint(0, Color.RED);
                    renderer.setSeriesPaint(1, Color.green);
                    renderer.setSeriesPaint(2, Color.black);
                    renderer.setSeriesPaint(3, Color.YELLOW);
                    renderer.setSeriesPaint(4, Color.ORANGE);
                    renderer.setSeriesPaint(5, Color.MAGENTA);

                    renderer.setSeriesStroke(0, new BasicStroke(1f));
                    renderer.setSeriesStroke(1, new BasicStroke(1f));
                    renderer.setSeriesStroke(2, new BasicStroke(1f));
                    renderer.setSeriesStroke(3, new BasicStroke(1f));
                    renderer.setSeriesStroke(4, new BasicStroke(1f));
                    renderer.setSeriesStroke(5, new BasicStroke(1f));

                    plot.setRenderer(renderer);
                    setAFormsVisible();
                    setAActionListener(renderer);

                    System.out.println("absolute end");
                }
                if (e.getSource() == b4) {
                    if (!isOpen) {
                        JOptionPane.showMessageDialog(null, "Выберите файл");
                        throw new FileNotFoundException("Выберите файл");
                    }
                    if (!isDataSaved) {
                        JOptionPane.showMessageDialog(null, "Сначала нужно обработать данные");
                        throw new FileNotFoundException("Обработайте данные");
                    }
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    JFreeChart xyLineChart;
                    if ((int) smoothWindowTypeG.getSelectedItem() == 3) {
                        xyLineChart = ChartFactory.createXYLineChart(
                                "Фильтрация методом скользящего среднего с окном 3",
                                "Номер измеренения",
                                "град/сек",
                                createSmoothFilterDataTypeG3(),
                                PlotOrientation.VERTICAL,
                                true, true, false);
                    } else if ((int) smoothWindowTypeG.getSelectedItem() == 5) {
                        xyLineChart = ChartFactory.createXYLineChart(
                                "Фильтрация методом скользящего среднего с окном 5",
                                "Номер измеренения",
                                "град/сек",
                                createSmoothFilterDataTypeG5(),
                                PlotOrientation.VERTICAL,
                                true, true, false);
                    } else {
                        xyLineChart = ChartFactory.createXYLineChart(
                                "Фильтрация методом скользящего среднего с окном 7",
                                "Номер измеренения",
                                "град/сек",
                                createSmoothFilterDataTypeG7(),
                                PlotOrientation.VERTICAL,
                                true, true, false);
                    }
                    ChartPanel chartPanel = new ChartPanel(xyLineChart);
                    JFrame frame = new JFrame("График");
                    JMenuBar menuBar = new JMenuBar();
                    menuBar.add(createGraphGXMenu());
                    frame.setJMenuBar(menuBar);
                    frame.getContentPane().add(chartPanel);
                    frame.setSize(1280, 720);
                    frame.setVisible(true);

                    final XYPlot plot = xyLineChart.getXYPlot();
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                    renderer.setDefaultShapesVisible(false);
                    renderer.setSeriesPaint(0, Color.RED);
                    renderer.setSeriesPaint(1, Color.green);
                    renderer.setSeriesPaint(2, Color.black);
                    renderer.setSeriesPaint(3, Color.YELLOW);
                    renderer.setSeriesPaint(4, Color.ORANGE);
                    renderer.setSeriesPaint(5, Color.MAGENTA);

                    renderer.setSeriesStroke(0, new BasicStroke(1f));
                    renderer.setSeriesStroke(1, new BasicStroke(1f));
                    renderer.setSeriesStroke(2, new BasicStroke(1f));
                    renderer.setSeriesStroke(3, new BasicStroke(1f));
                    renderer.setSeriesStroke(4, new BasicStroke(1f));
                    renderer.setSeriesStroke(5, new BasicStroke(1f));

                    plot.setRenderer(renderer);
                    setGFormsVisible();
                    setGActionListener(renderer);

                    System.out.println("absolute end");
                }
                if (e.getSource() == b5) {
                    if (!isOpen) {
                        JOptionPane.showMessageDialog(null, "Выберите файл");
                        throw new FileNotFoundException("Выберите файл");
                    }
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "Word & Excel", "xlsx");
                    JFileChooser fc = new JFileChooser();
                    fc.setFileFilter(filter);
                    ExcelSaver excelSaver = new ExcelSaver();
                    String extension = ".xlsx";
                    if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        try {
                            excelSaver.createAndSaveData(saveWorkbook, lastRowNum, elementsAX, elementsAY, elementsAZ, elementsGX, elementsGY, elementsGZ);
                            JOptionPane.showMessageDialog(null, "Все данные успешно обработаны");
                            FileOutputStream fw = new FileOutputStream(fc.getSelectedFile() + extension);
                            saveWorkbook.write(fw);
                        } catch (IOException ex) {
                            System.out.println("Всё погибло!");
                        }

                        isDataSaved = true;
                        System.out.println("absolute end");
                    }
                }

            } catch (Exception ex) {
                // JOptionPane.showMessageDialog(null, "Выберите файл");
            }
        }
    }


    private static boolean isNumeric(String s) {
        if (s != null) {
            try {
                Double.parseDouble(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }


    private static String getCellText(Cell cell) {
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

