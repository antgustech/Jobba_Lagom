package com.example.i7.jobbalagom.XLSManager;

import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;




/**
 * Should receive an xls file to read and add these to a... database?
 * Created by Anton Gustafsson on 2016-04-11.
 */
public class XlsToDatabase {

    private ArrayList taxTables = new ArrayList();
    private String xlsFilePath = "files/skatt16.xls";

    public XlsToDatabase(){
        Log.d("filereader","Startar" + "\t");
        XLSFileReader();

    }


    //Reads XLSX files by each cell.
        private void XLSFileReader(){
                        try {
                            FileInputStream file = new FileInputStream(new File(xlsFilePath));

                            //Create Workbook instance holding reference to .xlsx file
                            XSSFWorkbook workbook = new XSSFWorkbook(file);

                            //Get first/desired sheet from the workbook
                            XSSFSheet sheet = workbook.getSheetAt(0);

                            //Iterate through each rows one by one
                            Iterator<Row> rowIterator = sheet.iterator();
                            while (rowIterator.hasNext())
                            {
                                Row row = rowIterator.next();

                                //For each row, iterate through all the columns
                                Iterator<Cell> cellIterator = row.cellIterator();

                                while (cellIterator.hasNext())
                                {
                                    Cell cell = cellIterator.next();
                                    //Check the cell type and format accordingly
                                    switch (cell.getCellType())
                                    {
                        //Logs to the window to confirm that it works
                        case Cell.CELL_TYPE_NUMERIC:
                            Log.d("kolumn", cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            Log.d("rad",cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                System.out.println("");
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



}

}
