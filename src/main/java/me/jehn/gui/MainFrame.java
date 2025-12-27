package me.jehn.gui;

import me.jehn.sql.SQLMethods;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static java.awt.event.MouseEvent.*;
import static javax.swing.JOptionPane.*;

public class MainFrame implements ActionListener, MouseListener {
    //declaration
    private final SQLMethods sql = new SQLMethods();
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();

    private ArrayList<String> subjects = new ArrayList<>();
    private JButton addSubjectButton;
    private String subjectName;
    private String subjectClickedOn;


    //constructor
    public MainFrame(String empty){
    }
    public MainFrame() {
        createExistingSubjectsButtons();
        createAddSubjectButton();
        createGui();
    }

    //

    public void createGui(){

        frame.setSize(500,250);
        frame.setResizable(false);
        frame.setTitle("COLLEGE SUBJECTS");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new FlowLayout());
        frame.add(panel);
        frame.setVisible(true);
    }


    public void createAddSubjectButton(){
        //create button
        addSubjectButton = new JButton("ADD SUBJECT");
        addSubjectButton.setBackground(Color.white);
        addSubjectButton.setFocusable(false);
        addSubjectButton.addActionListener(this);
        //add to frame
        panel.add(addSubjectButton);
    }


    public void addSubjectInSystem(String newSubjectName){
        if(newSubjectName == null){}
        else if (!(newSubjectName.isBlank())) {
            String subjectName = newSubjectName.toUpperCase();
            //add subject to database
            String SQLaddSubject = "INSERT INTO subject "
                    + "VALUES ('"+subjectName+"');"
                    +"CREATE TABLE IF NOT EXISTS "+subjectName.replaceAll(" ","_")+"(chapter_name VARCHAR(40));";
            sql.updateSQL(SQLaddSubject);

            //reload buttons
            restart();
        } else showMessage(panel, "ERROR: Subject name cannot be blank.", "ERROR", WARNING_MESSAGE);
    }


    //method to create existing subjects buttons
    public void createExistingSubjectsButtons(){
        String SQLgetSubjects = "SELECT * FROM subject;";
        String SQLcolumn = "subject_name";
        subjects = sql.getSQL(SQLgetSubjects, SQLcolumn);
        if (subjects != null) {
            for (String subject : subjects) {
                addButton(panel, this, this,  subject, Color.lightGray);
            };
        }
    }


    //method to delete subject
    public void deleteSubject(String subjectName){
        int delConfirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete "+subjectName+"?", "CONFIRMATION", YES_NO_OPTION, WARNING_MESSAGE);
        switch (delConfirm){
            case 0:
                //delete chapters from subject's table
                ArrayList<String> chapters = new ArrayList<>();
                String str = "SELECT * FROM "+subjectName.replaceAll(" ", "_")+";";
                chapters = sql.getSQL(str, "chapter_name");
                if (chapters != null) {
                    for (String chapter : chapters) {
                        String SQLdeleteChapterTables = "DROP TABLE "+chapter.replaceAll(" ", "_")+"";
                        sql.deleteSQL(SQLdeleteChapterTables);
                    }
                }
                //delete subject from database;
                String SQLdeleteSubject= "DELETE FROM subject "
                        +"WHERE subject_name='"+subjectName+"';"
                        +"DROP TABLE "+subjectName.replaceAll(" ", "_")+";";
                sql.deleteSQL(SQLdeleteSubject);
                restart();
                break;
        }
    }


    //method to rename subject
    public void renameSubject(String subjectName){
        String input = JOptionPane.showInputDialog(frame, "Enter the new name for '"+subjectName+"':", "RENAME SUBJECT", PLAIN_MESSAGE);
        if(input == null){}
        else if (!(input.isBlank())) {
            String subjectNewName = input.toUpperCase();
            String SQLupdateName = "UPDATE subject "
                    +"SET subject_name = '"+subjectNewName+"' "
                    +"WHERE subject_name = '"+subjectName+"';"
                    +"ALTER TABLE "+subjectName.replaceAll(" ", "_")+" " +
                    "RENAME TO "+subjectNewName.replaceAll(" ", "_")+";";
            sql.updateSQL(SQLupdateName);
            restart();
        } else showMessage(panel, "ERROR: Subject name cannot be blank.", "ERROR", WARNING_MESSAGE);
    }


    //method to add "OK" button
    public void addButton(Container container, ActionListener actionlistener, MouseListener mouselistener, String name, Color color){
        JButton button = new JButton(name);
        button.setFocusable(false);
        button.setBackground(color);
        button.setName(name);
        button.addActionListener(actionlistener);
        button.addMouseListener(mouselistener);
        container.add(button);
    }


    //method to restart
    public void restart(){
        panel.removeAll();
        createExistingSubjectsButtons();
        createAddSubjectButton();
        panel.revalidate();
        panel.repaint();
    }


    //method to show quick message
    public void showMessage(Container container, String message, String title, int type){
        JOptionPane.showMessageDialog(container, message, title, type);
    }


    public String getSubjectClickedOn() {
        return subjectClickedOn;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addSubjectButton){
            subjectName = JOptionPane.showInputDialog(frame, "Enter subject name:", "ADD SUBJECT", PLAIN_MESSAGE);
            addSubjectInSystem(subjectName);
        } else {
            subjectClickedOn = e.getActionCommand();
            frame.dispose();
            new SubjectFrame(subjectClickedOn);
        }
    }


    //MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == BUTTON3){


            if (e.getSource() != addSubjectButton){
                /*    YES_OPTION = 0;    NO_OPTION = 1;    CANCEL_OPTION = 2;    OK_OPTION = 0;    CLOSED_OPTION = -1;    */
                String subjectClickedOn = e.getComponent().getName();

                Object[] options = {"DELETE","RENAME", "CANCEL"};
                int selectedOption = showOptionDialog(frame, "Choose whether you want to DELETE or RENAME '"+subjectClickedOn+"':", "ALTER SUBJECT", YES_NO_CANCEL_OPTION, PLAIN_MESSAGE, null, options, null);
                switch (selectedOption) {
                    case 0:
                        deleteSubject(subjectClickedOn);
                        break;
                    case 1:
                        renameSubject(subjectClickedOn);
                        break;
                }


            }


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}