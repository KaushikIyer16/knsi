/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knsi;


import static com.knsi.HomeScreenFrame.btnColor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author K.N.S.I (23th December 2015)
 */
public class RegisterStudentFrame extends JFrame {
    
    public static final Color colorPrimary = new Color(0xbf,0xc0,0xc0);
    public static final Color colorSecondary = new Color(0x69,0x73,0x77);
    
    String introby[]={"","Senior Student","Classmate","Friend\'s Parent","Pamphlets","Other Tuition teacher\'s"};
    String regfor[]={"","CBSE-12","CBSE-11","PUC-I","PUC-II","ICSE-10","ISC-11","ISC-12","UG"};
    String noofpayment[] ={"","1","2"};
    String pofsrc[]={"","Full Time","Part Time"};
    String pof=null;
    
    /* Below are the text fields which form the cells to be input into the .xls file t2,t4 are scroll */
     
     
     JComboBox<String> t2 = new JComboBox<>(introby);
     JComboBox<String> t4 = new JComboBox<>(regfor);
     JComboBox<String> t15 = new JComboBox<>(noofpayment);
     JComboBox<String> tpof = new JComboBox<>(pofsrc);
     
     JTextField[] values= new JTextField[15];
     
     JLabel validate= new JLabel("<html><div style=\"color: red;\">" +"<b>"+"Invalid details: Only Fields 7 and 8 are optional "
             + ". Fields 9 and/or 10 should be Filled"+ "</b>"+"</html>");
     
      
    JButton submitBtn= new JButton("<html><div style=\"color: white;\">" +"Register"+ "</html>");
     public RegisterStudentFrame()
    {
        validate.setFont(new Font("Lilly",Font.PLAIN,13));
        validate.setVisible(false);
    }
    
    public void createUI()
    {
        // jf stands for java form ...since we are using it for registration
        JDialog jf = new JDialog(this,"Registration Screen",true);
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
                .getResource("/resources/logo.png")));
        
        
        
        /* The hierarchy of the panels is like :
        regpanel(A tabbed pane)> reg(jpanel)>details(A Scroll Pane)> form(Another JPanel)
                               > del
        */
        
        
        JTabbedPane regpanel = new JTabbedPane();
        
        JPanel reg = new JPanel();
        
        JPanel del = new JPanel();
        regpanel.addTab("          Register          ", reg);
        //regpanel.addTab("          Delete            ", del);
        reg.setBackground(colorSecondary);
        del.setBackground(colorSecondary);
        
        setRegTab(reg);
        jf.add(regpanel,BorderLayout.CENTER);
        
        submitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isValidInput())
                {
                    validate.setText("<html><div style=\"color: red;\">" +"<b>"+
                            "Invalid details or Missing Details: Only Fields 7 and 8 are optional "
             + ". Either Father\'s (Guardian\'s) or Mother\'s Details should be filled"+ "</b>"+"</html>");
                    validate.setVisible(true);
                }
                else
                {
                    validate.setVisible(false);
                    try{
                        WriteDB.writeTo(values,pof);
                        /*for(int i=0;i<15;i++)
                        {
                            System.out.println(values[i].getText());
                        }*/
                        validate.setText("<html><div style=\"color: green;\">" +"<b>"+"Registration Succesfull </b>"+"</html>");
                        validate.setVisible(true);
                        for(int i=0;i<15;i++)
                        {
                            values[i].setText("");
                        }
                        t2.setSelectedIndex(0);
                        t4.setSelectedIndex(0);
                        t15.setSelectedIndex(0);
                        
                    }catch(Exception ee)
                    {
                        ee.printStackTrace();
                        validate.setText(ee.getMessage());
                        validate.setVisible(true);
                    }
                }
                
            }
            
        });
        jf.setSize(1080,720);
        jf.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        jf.setLocationRelativeTo(null);
        jf.setPreferredSize(new Dimension(600,600));
        jf.setVisible(true);
        setResizable(false);
        
        
    }

    private void setRegTab(JPanel reg) {
        
        
        reg.setLayout(new BorderLayout());
        
        JScrollPane details = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        details.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel form = new JPanel();
        
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(colorPrimary);
       
        /*BElow represents all the Labels that are fields in the form*/
        String labels[]={"Name:","School:","Introduced by:","Class:","Registering for:",
            "Address:","Landline Number:","Mobile Number:","Father\'s ( or Guardian\'s )Name:","Mother\'s Name:",
        "Father\'s or Guardian\'s Number:","Mother\'s Number","E-Mail:","Total fees to be paid:","No of Installments"
        ,"Type of course"};
        
       
        /*Below is the Action Listener for the combo boxes*/
        
        t2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox c = (JComboBox)e.getSource();
                values[2]=new JTextField(c.getSelectedItem().toString());
            }
        });
       
       
        t4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox c = (JComboBox)e.getSource();
                values[4]=new JTextField(c.getSelectedItem().toString());
               
            }
        });
        
        t15.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox c = (JComboBox)e.getSource();
                values[14]=new JTextField(c.getSelectedItem().toString());
            }
        });
        
        tpof.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox c = (JComboBox)e.getSource();
                pof=c.getSelectedItem().toString();
            }
        });
        
        for (int i=0;i<16;i++) {
            form.add(Box.createRigidArea(new Dimension(5,10)));
            JLabel temp = new JLabel((i+1)+" : "+labels[i]);
            temp.setFont(new Font("Lilly",Font.BOLD,12));
            form.add(temp,BorderLayout.LINE_START);
            form.add(Box.createRigidArea(new Dimension(5,10)));
            JPanel t= new JPanel();
            t.setLayout(new BoxLayout(t, BoxLayout.X_AXIS));
            if(i== 2)
            {
                t.add(t2);
            }
            else if(i == 4)
            {
                t.add(t4);
            }
            else if(i == 14)
            {
                t.add(t15);
            }
            else if(i == 15)
            {
                t.add(tpof);
            }
            else
            {
                
                values[i]= new JTextField();
                t.add(values[i]);
        
                t.setPreferredSize(new Dimension(50,50));
            }
            t.add(new JLabel("           "));
            
            t.setOpaque(false);
            form.add(t);
        }
        
        
        
        /* for a JScrollpane only way to make the pane visible is to set its view
        port to the panel that you want to show. Therefore add all your components to a 
        jPanel , here it is panel form and then set the view port to this particular panel
        then add the scrollbar to the main jpanel
        */
        
        // below is filler code for formatting ui with indent and also packing and displaying the ui
        
        submitBtn.setBackground(btnColor);
        submitBtn.setFont(new Font("Lilly",Font.PLAIN,16));
        JPanel tp2= new JPanel();
        tp2.setLayout(new BoxLayout(tp2, BoxLayout.X_AXIS));
        tp2.add(submitBtn);
        tp2.setOpaque(false);
        
        form.add(Box.createVerticalStrut(10));
        JPanel tp3= new JPanel();
        tp3.setLayout(new BoxLayout(tp3, BoxLayout.X_AXIS));
        tp3.setOpaque(false);
        tp3.add(validate);
        form.add(Box.createVerticalStrut(10));
        form.add(tp3,BorderLayout.CENTER);
        form.add(tp2,BorderLayout.CENTER);    
        form.add(Box.createRigidArea(new Dimension(5,10)));
        form.setSize(400, 400);
        
        details.setViewportView(form);
        
        details.setPreferredSize(new Dimension(600,600));
        reg.add(details,BorderLayout.CENTER);
        
        
        
    } 
    
    /* Below function is to check if the input entered by the user is valid or not*/
    public boolean isValidInput()
    {
        for(int i=0;i<=5;i++)
        {
           
            if(i==2 || i==4)
            {
                if(values[i] == null||values[i].getText().equalsIgnoreCase(""))
                {
                    
                    return false;
                }
            }
            else
            {
                if(values[i].getText().equalsIgnoreCase("") || values[i].getText().matches("^[1-9]\\d*$"))
                {
                    
                    return false;
                }
            }
            
        }
        
        if(!values[7].getText().equalsIgnoreCase("")&&!values[7].getText().matches("[0-9]{10}"))
        {
            return false;
        }
        if(!values[6].getText().equalsIgnoreCase("") &&!values[6].getText().matches("[0-9]{8}"))
        {
            return false;
        }
        if(values[8].getText().equalsIgnoreCase("")&&values[9].getText().equalsIgnoreCase(""))
        {
            return false;
        }
        if(values[10].getText().equalsIgnoreCase("")&&values[11].getText().equalsIgnoreCase("")||!values[10].getText().matches("[0-9]{10}") && !values[11].getText().matches("[0-9]{10}"))
        {
            return false;
        }
        if(values[12].getText().equalsIgnoreCase("") || values[13].getText().equalsIgnoreCase(""))
            return false;
        if(pof == null || pof.equalsIgnoreCase(""))
            return false;
        return true;
    }

    
}
