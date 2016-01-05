/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knsi;

import static com.knsi.HomeScreenFrame.btnColor;
import static com.knsi.RegisterStudentFrame.colorPrimary;
import static com.knsi.RegisterStudentFrame.colorSecondary;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author K.N.S.I (4th january 2016)
 */
public class PerformanceFrame extends JFrame{
    
    String regfor[]={"","CBSE-12","CBSE-11","PUC-I","PUC-II","ICSE-10","ISC-11","ISC-12","UG"};
    JComboBox<String> t4 = new JComboBox<>(regfor);
    JButton submitBtn= new JButton("<html><div style=\"color: white;\">" +"Create"+ "</html>");
    JButton NexttBtn= new JButton("<html><div style=\"color: white;\">" +"Start Entries"+ "</html>");
    
    JLabel hasStudents=new JLabel("<html><div style=\"color: red;\">" +"There no students in this batch"+ "</html>");
    JLabel studentid = new JLabel("          ");
    JLabel studentname = new JLabel("        ");
    
    JTextField tName=new JTextField();
    JTextField maxMarks=new JTextField();
    JTextField studentmarks=new JTextField();
    
    boolean isValid = false;
    int itercount=0;
    JLabel outof=new JLabel();
    ArrayList <String>marksList=new ArrayList<>();
    ArrayList <XSSFRow>nameList=new ArrayList<>();
    JLabel hasStudentmarks=new JLabel();
    
    public void createUI()
    {
        JDialog pf = new JDialog(this,"Performance",true);
        pf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
                .getResource("/resources/logo.png")));
        
        JTabbedPane perpanel = new JTabbedPane();
        
        JPanel addp = new JPanel();
        
        JPanel viewp = new JPanel();
        perpanel.addTab("          Add New Test Details          ", addp);
        //perpanel.addTab("          View Student Performance            ", viewp);
        addp.setBackground(colorSecondary);
        viewp.setBackground(colorSecondary);
         
        setaddTab(addp);
        
        submitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(t4.getSelectedItem());
                    hasStudentmarks.setVisible(false);
                    if(t4.getSelectedItem().equals(""))
                    {
                        hasStudents.setText("<html><div style=\"color: red;\">" +"No batch has been selected"+ "</html>");
                        hasStudents.setVisible(true);
                        
                    }
                    else if(tName.getText().equalsIgnoreCase(""))
                    {
                        hasStudents.setText("<html><div style=\"color: red;\">" +"Invalid or No Test Name Entered"+ "</html>");
                        hasStudents.setVisible(true);
                    }
                    else if(!maxMarks.getText().matches("^[1-9]\\d*$"))
                    {
                        hasStudents.setText("<html><div style=\"color: red;\">" +"Marks should only consist of digits"+ "</html>");
                        hasStudents.setVisible(true);
                    }
                    else if(isValid)
                    {
                        hasStudents.setText("<html><div style=\"color: red;\">" +"Cannot simultaneously create two performances"+ "</html>");
                        hasStudents.setVisible(true);
                    }
                    else if(fileHasValues((String)t4.getSelectedItem()))
                    {
                        hasStudents.setText("<html><div style=\"color: red;\">" +"No Students in this batch"+ "</html>");
                        hasStudents.setVisible(true);
                        System.out.println("");
                    }
                    else
                    {
                        isValid=true;
                        hasStudents.setText("<html><div style=\"color: green;\">" +"Performance created successfully"+ "</html>");
                        hasStudents.setVisible(true);
                        outof.setText(maxMarks.getText());
                        try
                        {
                            WritePerformance();
                        }catch(Exception ee)
                        {
                            ee.printStackTrace();
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PerformanceFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private boolean fileHasValues(String string) throws IOException {
                XSSFWorkbook details = new XSSFWorkbook(new FileInputStream(new File(string+".xlsx")));
                XSSFSheet spreadsheet1=details.getSheet("details");
                return spreadsheet1.getLastRowNum() == 0;
            }
        });
        
        NexttBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("the next student is:::");
                
                if(!isValid)
                {
                    hasStudentmarks.setText("<html><div style=\"color: red;\">" +"Please Create a Performance"+ "</html>");
                    hasStudentmarks.setVisible(true);
                    
                }
                
                else if(!studentmarks.getText().matches("^[1-9]\\d*$") || Integer.parseInt(studentmarks.getText()) > Integer.parseInt(maxMarks.getText()))
                {
                    hasStudentmarks.setText("<html><div style=\"color: red;\">" +"Invalid Entry"+ "</html>");
                    hasStudentmarks.setVisible(true);
                }
                else if(itercount==nameList.size())
                {
                    hasStudentmarks.setText("<html><div style=\"color: green;\">" +"All Student\'s Performance has been updated"+ "</html>");
                    hasStudentmarks.setVisible(true);
                    isValid=false;
                    itercount=0;
                }
                else
                {
                    NexttBtn.setText("<html><div style=\"color: white;\">" +"Next Student"+ "</html>");
                    studentname.setText(nameList.get(itercount).getCell(0).getStringCellValue());
                    studentid.setText(nameList.get(itercount).getCell(1).getStringCellValue());
                    marksList.add(studentmarks.getText());
                    itercount++;
                }
                
               /* for (XSSFRow row : nameList) {
                    System.out.println(row.getCell(0).getStringCellValue()+"     "+row.getCell(1).getStringCellValue());
                    System.out.println(nameList.get(itercount).getCell(0).getStringCellValue()+""+nameList.get(itercount).getCell(1).getStringCellValue());
                }*/
                
                
                WritePerformanceDB.WriteTo(marksList);
                
                
                
            }
        });
        
        pf.add(perpanel,BorderLayout.CENTER);
        pf.setSize(960, 680);
        //pf.setBounds(0,0,1060,720);
        pf.setLocationRelativeTo(null);
        pf.setVisible(true);
    }

    private void setaddTab(JPanel addp) {
        addp.setLayout(new BoxLayout(addp,BoxLayout.Y_AXIS));
        JScrollPane details = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        details.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel form = new JPanel();
        
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(colorPrimary);
        
        JPanel tp2= new JPanel();
        tp2.add(new JLabel("Coose a batch whoose details you would like to add: "),BorderLayout.LINE_START);
        tp2.setOpaque(false);
        form.add(tp2);
        form.add(Box.createVerticalStrut(25));
        
        JPanel t= new JPanel();
        t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
        t.add(Box.createHorizontalStrut(100));
        t.add(t4);
        t.add(Box.createHorizontalStrut(100));
        t.setOpaque(false);
        //t.setSize(1080, 50);
        form.add(t);
        form.add(new JLabel("                  "));
        form.add(Box.createVerticalStrut(50));
        
        JPanel t2= new JPanel();
        t2.setLayout(new BoxLayout(t2, BoxLayout.X_AXIS));
        t2.add(Box.createHorizontalStrut(10));
        t2.add(new JLabel("Enter the Name Of the test:"));
        t2.add(tName);
        t2.add(Box.createHorizontalStrut(30));
        t2.add(new JLabel("Enter the Maximum marks:"));
        t2.add(maxMarks);
        t2.add(Box.createHorizontalStrut(10));
       
        t2.setOpaque(false);
        //t2.setSize(1080,80);
        form.add(t2);
        form.add(new JLabel("                  "));
        form.add(Box.createVerticalStrut(50));
        
        JPanel tp= new JPanel();
        tp.add(hasStudents);
        form.add(tp);
        tp.setOpaque(false);
        hasStudents.setVisible(false);
        
        JPanel t3= new JPanel();
        submitBtn.setBackground(btnColor);
        submitBtn.setFont(new Font("Lilly",Font.PLAIN,16));    
        t3.add(Box.createHorizontalStrut(0));
        t3.add(submitBtn);
        t3.add(Box.createHorizontalStrut(0));
        //t3.setSize(1080, 50);
        t3.setOpaque(false);
        form.add(t3);
        form.add(new JLabel("                  "));
        form.add(Box.createVerticalStrut(25));
        
        JPanel blank= new JPanel();
        blank.setBackground(colorSecondary);
        blank.setSize(960, 10);
        form.add(blank);
        
        form.add(Box.createVerticalStrut(25));
        JPanel pt4= new JPanel();
        pt4.setLayout(new BoxLayout(pt4, BoxLayout.X_AXIS));
        pt4.add(new JLabel("Student I.D :"));
        pt4.add(studentid);
        pt4.add(Box.createHorizontalStrut(400));
        pt4.add(new JLabel("Name Of the Student:"));
        pt4.add(studentname);
        pt4.setOpaque(false);
        //tp4.setSize(1080,80);
        form.add(pt4);
        form.add(new JLabel("                  "));
        form.add(Box.createVerticalStrut(35));
        
        JPanel t5= new JPanel();
        t5.add(Box.createHorizontalStrut(100));
        t5.setLayout(new BoxLayout(t5,BoxLayout.X_AXIS));
        t5.add(new JLabel("Enter the Marks:"));
        t5.add(studentmarks);
        t5.add(new JLabel("      /      "));
        // insert maxMarks.getText() in the final version
        t5.add(outof);
        t5.add(Box.createHorizontalStrut(100));
        t5.setOpaque(false);
        form.add(t5);
        form.add(new JLabel("                  "));
        form.add(Box.createVerticalStrut(50));
        
        JPanel tp3= new JPanel();
        tp3.add(hasStudentmarks);
        form.add(tp3);
        tp3.setOpaque(false);
        hasStudentmarks.setVisible(false);
        
        JPanel t6= new JPanel();
        NexttBtn.setBackground(btnColor);
        NexttBtn.setFont(new Font("Lilly",Font.PLAIN,16));    
        t6.add(Box.createHorizontalStrut(0));
        t6.add(NexttBtn);
        t6.add(Box.createHorizontalStrut(0));
        //t3.setSize(1080, 50);
        t6.setOpaque(false);
        form.add(t6);
        form.add(new JLabel("                  "));
        //form.add(Box.createVerticalStrut(50));
        
        form.add(Box.createRigidArea(new Dimension(5,10)));
        //form.setPreferredSize(new Dimension(1060, 300));
        
        details.setViewportView(form);
        
        
        addp.add(details);
    }
    
    public void WritePerformance() throws Exception
    {
        /*just create a new cell at the end of each row at to that add values of the list*/
        
        XSSFWorkbook details = new XSSFWorkbook(new FileInputStream(new File(t4.getSelectedItem().toString()+".xlsx")));
        XSSFSheet spreadsheet1= details.getSheet("Performance");
        XSSFRow row;
        
        Iterator < Row > rowIterator = spreadsheet1.iterator();
        
        while (rowIterator.hasNext())
        {
            row = (XSSFRow) rowIterator.next();
            //Iterator < Cell > cellIterator = row.cellIterator();
            //System.out.println("the last column in this sheet is "+row.getLastCellNum());
            nameList.add(row);
            /*for(int i=0;i<2;i++)
            {
                Cell cell = cellIterator.next();
                if(i==0)
                {
                    studentid.setText(cell.getStringCellValue());
                }
                else
                {
                    studentname.setText(cell.getStringCellValue());
                }
                System .out.print(cell.getStringCellValue() + " \t\t " );
                
            }*/
            
            //System .out.println();
        }
        
    }
}
