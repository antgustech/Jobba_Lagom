package com.example.i7.jobbalagom.local;

import java.util.ArrayList;

/**
 * Should receive an xs file from controller to read and convert to a list.
 * Created by i7 on 2016-04-11.
 */
public class XLSReader {

    private ArrayList taxTables = new ArrayList();
    private XLSObject xlsFile;

    public XLSReader(XLSObject xlsFile){
        this.xlsFile = xlsFile;
        converter(xlsFile);
    }
    public void converter( XLSObject xlsFile){
        //Convert XLSObject into a list....
    }
}
