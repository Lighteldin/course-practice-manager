package me.jehn;

import me.jehn.gui.MainFrame;
import me.jehn.sql.SQLMethods;

import javax.swing.*;


public class Main {
    public static void main(String[] args){
        //create database
        new SQLMethods();
        //gui runner
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}