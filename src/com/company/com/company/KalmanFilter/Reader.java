package com.company.com.company.KalmanFilter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.Thread.sleep;


public class Reader extends JFrame {
    class AX implements Runnable{

        @Override
        public void run() {
            int newStart = 0;
            try{
                sleep(100);
            }catch (InterruptedException ex){
                System.out.println("Somethg bad");
            }
            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum(); i++) {
                if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)))) {
                    newStart = i;
                    System.out.println(i);
                    break;
                }
            }
            elementsAX = new double[newStart];
            for (int i = 1; i < newStart; i++) {
                elementsAX[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)));
              //  System.out.println(elementsAX[i]);
            }

        }
    }

    class AY implements Runnable{

        @Override
        public void run() {
            int newStart = 0;

            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum(); i++) {
                if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(1)))) {
                    newStart = i;
                    break;
                }
            }
            elementsAY = new double[newStart];
            for (int i = 1; i < newStart; i++) {
                elementsAY[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(1)));
            }
        }
    }
    class AZ implements Runnable{

        @Override
        public void run() {
            int newStart = 0;

            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum(); i++) {
                if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(2)))) {
                    newStart = i;
                    break;
                }
            }
            elementsAZ = new double[newStart];
            for (int i = 1; i < newStart; i++) {
                elementsAZ[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(2)));
            }
        }
    }

    JButton b1;
    JButton b2;
    private double[] elementsAX;
    private double[] elementsAY;
    private double[] elementsAZ;
    private eHandler eHandler = new eHandler();
    private Workbook wb;
    int newStart = 0;


    public Reader(String s) throws IOException {
        super(s);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        setLayout(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
        b1 = new JButton("Получить данные из файла");
        b2 = new JButton("Получить данные после фильтрации");
        add(b1);
        add(b2);
        b2.addActionListener(eHandler);
        b1.addActionListener(eHandler);
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

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("ActionListener.actionPerformed : open");
                JFileChooser fileOpen = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Word & Excel", "xls");
                fileOpen.setFileFilter(filter);
                int ret = fileOpen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file1 = fileOpen.getSelectedFile();
                    try {
                        FileInputStream fis = new FileInputStream(file1);
                        wb = new HSSFWorkbook(fis);
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return file;
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

    private XYDataset createDataset() {
      /*  AX ax = new AX();
        Thread thread = new Thread(ax);
        thread.start();*/
        System.out.println("Start of method");
        arrayEmptyCells();
        System.out.println("The end of method");
        XYSeries beforeFilterAX = new XYSeries("AX");
        XYSeries beforeFilterAY = new XYSeries("AY");
        XYSeries beforeFilterAZ = new XYSeries("AZ");

        for(int i = 0; i < newStart; i++){
            System.out.println(elementsAZ[i]);
            beforeFilterAX.add(i, elementsAX[i]);
            beforeFilterAY.add(i, elementsAY[i]);
            beforeFilterAZ.add(i, elementsAZ[i]);

        }
        System.out.println("the end");
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(beforeFilterAX);
        System.out.println("AX");
        dataSet.addSeries(beforeFilterAY);
        System.out.println("AY");
        dataSet.addSeries(beforeFilterAZ);
        System.out.println("AZ");
        System.out.println("data is added");
        return dataSet;
    }

    public class eHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == b2) {

                }
                if (e.getSource() == b1) {
                    JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                            "Filtration" ,
                            "Номер измеренения" ,
                            "Величина измерения" ,
                            createDataset() ,
                            PlotOrientation.VERTICAL ,
                            true , true , false);
                    System.out.println("The start of gtaf making");
                    ChartPanel chartPanel = new ChartPanel( xyLineChart );
                    JFrame frame = new JFrame("График");
                    frame.getContentPane().add(chartPanel);
                    frame.setSize(1280,720);
                    frame.setVisible(true);
                   /* chartPanel.setPreferredSize( new java.awt.Dimension( 1200 , 700) );
                    final XYPlot plot = xylineChart.getXYPlot( );
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
                    renderer.setSeriesPaint( 0 , Color.RED );
                    renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
                    plot.setRenderer( renderer );
                    setContentPane( chartPanel );*/
                    System.out.println("absolute end");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Выберите файл");
            }
        }
    }
    public void arrayEmptyCells() {

        newStart = wb.getSheetAt(0).getLastRowNum();
        for (int i = 1; i <= wb.getSheetAt(0).getLastRowNum(); i++) {
            if (!isNumeric(getCellText(wb.getSheetAt(0).getRow(i).getCell(0))) )
            {
                newStart = i;
                break;
            }
        }
        System.out.println("create AX");
        elementsAX = new double[newStart];
        System.out.println("create AY");
        elementsAY = new double[newStart];
        System.out.println("create AZ");
        elementsAZ = new double[newStart];
        for (int i = 1; i < newStart; i++) {
            elementsAX[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(0)));
            elementsAY[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(1)));
            elementsAZ[i] = Double.parseDouble(getCellText(wb.getSheetAt(0).getRow(i).getCell(2)));

        }
      //  return elementsAX;
    }
    public static boolean isNumeric(String s) {
        if(s != null) {
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

