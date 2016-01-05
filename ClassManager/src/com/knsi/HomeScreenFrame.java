/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knsi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author K.N.S.I (20th December 2015)
 */
public class HomeScreenFrame {
    
    public static final String COMPANY_NAME="E-GURU LINK";
    public static final Color btnColor = new Color(241, 82, 116);
    
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame homeFrame = new JFrame("Home Screen");
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //this is the menu bar. color: #696773 .... currently it has not been added to the screen
        JMenuBar homeMenuBar = new JMenuBar();
        homeMenuBar.setOpaque(true);
        homeMenuBar.setBackground(new Color(105, 103, 115)); 
        homeMenuBar.setPreferredSize(new Dimension(600, 50));
        JLabel companyName=new JLabel("<html><div style=\"color: white;\">" +COMPANY_NAME + "</html>",JLabel.CENTER);
        companyName.setFont(new Font("Times New Roman",Font.PLAIN,20));
        homeMenuBar.add(companyName);
        
        
        /*  !!!!!!!!!!!! CAUTION: DO NOT CHANGE THE SET BOUND PROPERTY OF ANY 
        COMPONENT WITHOUT VERIFYING WHAT ITS CONSEQUENCE MIGHT BE TO THE FRAME SCREEN!!!!!!!!!!!!
        */
        
        //this is the home label which consists of the background image and forms layer 0 of the LayeredPane
        JLabel homeLabel = new JLabel();
        homeLabel.setIcon(new ImageIcon(getClass().getResource("/resources/background.jpg")));
        homeLabel.setBounds(0, 0, 600, 600);
                
        // this is the jpanel to store all the buttons in a grid layout 
        JPanel jp= new JPanel();
        GridLayout bl = new GridLayout(2, 3,10,15);
        jp.setLayout(bl);
        
        JButton b1= new JButton("<html><div style=\"color: white;\">" +"Register"+ "</html>");
        b1.setBackground(btnColor);
        b1.setFont(new Font("Lilly",Font.PLAIN,16));
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               // System.out.println("First button was clicked");
                RegisterStudentFrame obj = new RegisterStudentFrame();
                obj.createUI();
            }
        });
        
        JButton b2= new JButton("<html><div style=\"color: white;\">" +"Fee status"+ "</html>");
        b2.setBackground(btnColor);
        b2.setFont(new Font("Lilly",Font.PLAIN,16));
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Second button was clicked");
                
            }
        });
        
        JButton b3= new JButton("<html><div style=\"color: white;\">" +"Performance"+ "</html>");
        b3.setBackground(btnColor);
        b3.setFont(new Font("Lilly",Font.PLAIN,16));
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Third button was clicked");
                PerformanceFrame obj= new PerformanceFrame();
                obj.createUI();
            }
        });
        
        JButton b4= new JButton("<html><div style=\"color: white;\">" +"Question Papers"+ "</html>");
        b4.setBackground(btnColor);
        b4.setFont(new Font("Lilly",Font.PLAIN,16));
        
        JButton b5= new JButton("<html><div style=\"color: white; align-content=center; \">" +"Schedule"+ "</div>"+"</html>");
        b5.setBackground(btnColor);
        b5.setFont(new Font("Lilly",Font.PLAIN,16));
        
        JButton b6= new JButton("<html><div style=\"color: white; \">" +"Attendence"+ "</html>");
        b6.setBackground(btnColor);
        b6.setFont(new Font("Lilly",Font.PLAIN,16));
        
        jp.add(b1);    
        jp.add(b2);
        jp.add(b3);
        jp.add(b4);
        jp.add(b5);
        jp.add(b6);
        jp.setBounds(25, 350,550 ,150);
        jp.setOpaque(false);
        
        // this is the root layered pane that forms the front screen of the dock
        JLayeredPane lp=new JLayeredPane();
        lp.setPreferredSize(new Dimension(600, 600));
        lp.add(homeLabel,0,0);
        lp.add(jp, 1,0);
        
        //Set the menu bar if required and the layered pane and any other frame properties before packing into the content pane.
        homeFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/logo.png")));
        homeFrame.setResizable(false);
        //homeFrame.setJMenuBar(homeMenuBar);
        homeFrame.getContentPane().add(lp, BorderLayout.CENTER);
     
        
        

        //Display the window.
        homeFrame.pack();
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        
        // Set the look and feel of the application's base gui ...with nimbus
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HomeScreenFrame hScreen = new HomeScreenFrame();
                hScreen.createAndShowGUI();
            }
        });
    }
}
