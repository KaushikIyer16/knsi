/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knsi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import javax.swing.JTextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author K.N.S.I (25th December 2015)
 */
public class WriteDB {
   public static String s=null; // s string is to be used only for debugging
    public static void writeTo(JTextField labels[],String pof) {
        try{
            File db = new File(labels[4].getText()+".xlsx");
            System.out.println(db.getAbsolutePath());
            
            Date d= new Date();
            System.out.println(d.toString());
            FileInputStream dbStream = new FileInputStream(db);
            XSSFWorkbook details = new XSSFWorkbook(dbStream);
            XSSFSheet spreadsheet1 = details.getSheet("details");
            XSSFSheet spreadsheet2 = details.getSheet("Fees");
            XSSFSheet spreadsheet3 = details.getSheet("Performance");
            XSSFSheet spreadsheet4 = details.getSheet("Attendence");
            
            System.out.println(spreadsheet1.getLastRowNum()+"------------"+spreadsheet2.getLastRowNum());
            //Create row object
            XSSFRow row1,row2,row3,row4;
            //This data needs to be written (Object[])
            
            String obj1[] = new String[14];
            int j=1;
            for(int i=0;i<=12;i++)
            {
                if(i == 4)
                {
                    continue;
                }
                if(labels[i].getText().equalsIgnoreCase(""))
                {
                    obj1[j]="N/A";
                }
                else
                {
                    obj1[j]=labels[i].getText();
                }
                j++;
            }
            //obj1[0]=Integer.toString(spreadsheet1.getLastRowNum()+1);
            obj1[0]=getRegId(obj1[0], Integer.toString(spreadsheet1.getLastRowNum()+1),labels[4].getText(),pof);
            obj1[13]=d.toString();
            System.out.println("The details for the details sheet is:");
            for(int i=0;i<14;i++)
            {
                System.out.println(obj1[i]);
            }
            
            String obj2[]=new String[4];
            obj2[0]=obj1[0];
            obj2[1]=labels[13].getText();
            obj2[2]=labels[14].getText();
            obj2[3]=labels[13].getText();
            
            System.out.println("The details for the fees sheet is:");
            for(int i=0;i<4;i++)
            {
                System.out.println(obj2[i]);
            }
            
            String obj3[]= new String[2];
            obj3[0]=obj1[0];
            obj3[1]=obj1[1];
            
            int rowid1 = spreadsheet1.getLastRowNum()+1;
            int rowid2 = spreadsheet2.getLastRowNum()+1;
            int rowid3 = spreadsheet3.getLastRowNum()+1;
            int rowid4 = spreadsheet4.getLastRowNum()+1;
            
            row1 = spreadsheet1.createRow(rowid1);
            row2 = spreadsheet2.createRow(rowid2);
            row3 = spreadsheet3.createRow(rowid3);
            row4 = spreadsheet4.createRow(rowid4);
            
            int cellid = 0;
            for (String obj : obj1)
            {
               Cell cell = row1.createCell(cellid++);
               cell.setCellValue(obj);
            }
            
            cellid=0;
            for (String obj : obj2)
            {
               Cell cell = row2.createCell(cellid++);
               cell.setCellValue(obj);
            }
            
            cellid=0;
            for (String obj : obj3)
            {
               Cell cell = row3.createCell(cellid);
               cell.setCellValue(obj);
               Cell cell2 = row4.createCell(cellid);
               cell2.setCellValue(obj);
               cellid++;
            }
            
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream( db);
            details.write(out);
            out.close();
            System.out.println( db+" written successfully" );
            s=db.getAbsolutePath();
        }catch(Exception ee)
        {
            s=ee.getMessage();
            System.out.println(s);
           
        }
        
    }
    
    public static String getRegId(String req,String number,String batch,String pof)
    {
       /*11cb p 0001 ... store the res in req.... */
       switch (batch) {
           case "CBSE-12":
           case "CBSE-11":
               req=batch.substring(batch.length()-2, batch.length())+"CB"+pof.charAt(0);
               for(int i=0;i<4-number.length();i++)
               {
                   req+="0";
               }  req+=number;
               System.out.println("the value of req is"+req);
               return req;
           case "PUC-I":
           case "PUC-II":
               if(batch.substring(batch.length()-2, batch.length()).equalsIgnoreCase("I"))
               {
                   req="11PU";
               }
               else
               {
                   req="12PU";
               }  req+=pof.charAt(0);
               for(int i=0;i<4-number.length();i++)
               {
                   req+="0";
                }  req+=number;
                System.out.println("the value of req is"+req);
               return req;
           case "ISC-11":
           case "ISC-12":
               req=batch.substring(batch.length()-2, batch.length())+"IS"+pof.charAt(0);
               for(int i=0;i<4-number.length();i++)
               {
                   req+="0";
            }  req+=number;
            System.out.println("the value of req is"+req);
               return req;
           case "ICSE-10":
               req=batch.substring(batch.length()-2, batch.length())+"IC"+pof.charAt(0);
               for(int i=0;i<4-number.length();i++)
               {
                req+="0";
            }  req+=number;
            System.out.println("the value of req is"+req);
            return req;
           default:
               Date d= new Date();
               req=d.toString().substring(d.toString().length()-2, d.toString().length())+"UG"+pof.charAt(0);
               for(int i=0;i<4-number.length();i++)
               {
                req+="0";
            }  req+=number;
            System.out.println("the value of req is"+req);
               return req;
       }
    }
    
}
