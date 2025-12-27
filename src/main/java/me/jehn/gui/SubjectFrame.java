package me.jehn.gui;

import me.jehn.gui.MainFrame;
import me.jehn.sql.SQLMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.awt.event.MouseEvent.BUTTON3;
import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public class SubjectFrame implements ActionListener, MouseListener {
    private final SQLMethods sql = new SQLMethods();
    private final MainFrame mFrame = new MainFrame("");
    private final JFrame sFrame = new JFrame();
    private final JPanel panel = new JPanel();
    private ArrayList<String> chapters = new ArrayList<>();
    private JButton addChapterButton;
    private String selectedSubject;
    private String chapterName;

    //constructor
    public SubjectFrame(){}
    public SubjectFrame(String selectedSubject) {
        this.selectedSubject = selectedSubject;

        addAllButtons();
        createSubjectGui(selectedSubject);
    }
    public void addAllButtons(){
        mFrame.addButton(panel, this, null, "BACK", Color.white);
        createExistingChaptersButtons();
        mFrame.addButton(panel, this, null, "ADD CHAPTER", Color.white);
    }
    //


    public void createSubjectGui(String subjectName){
        sFrame.setSize(500,250);
        sFrame.setResizable(false);
        sFrame.setTitle(subjectName+" CHAPTERS");
        sFrame.setLocationRelativeTo(null);
        sFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new FlowLayout());
        sFrame.add(panel);
        sFrame.setVisible(true);
    }


    public void createExistingChaptersButtons(){
        String SQLgetChapters = "SELECT chapter_name FROM "+selectedSubject.replaceAll(" ", "_")+";";
        String SQLcolumn = "chapter_name";
        chapters = sql.getSQL(SQLgetChapters, SQLcolumn);
        if(chapters!=null) {
            for (String chapter : chapters) {
                mFrame.addButton(panel, this,this, chapter, Color.pink);
            }
        }
    }


    public void addChapterInSystem(String newChapterName){
        if(newChapterName == null){}
        else if (!(newChapterName.isBlank())) {
            String chapterName = newChapterName.toUpperCase();
            //add subject to database
            String SQLgetChapters = "INSERT INTO "+selectedSubject.replaceAll(" ", "_")
                    +" VALUES ('"+chapterName+"');"
                    +"CREATE TABLE IF NOT EXISTS "+chapterName.replaceAll(" ", "_")+"(question_number INT PRIMARY KEY, question VARCHAR(MAX), choice1 VARCHAR(MAX), choice2 VARCHAR(MAX), choice3 VARCHAR(MAX), choice4 VARCHAR(MAX), correct_choice VARCHAR(7));";
            sql.updateSQL(SQLgetChapters);


            //reload buttons
            restart();
        } else mFrame.showMessage(panel, "ERROR: Chapter name cannot be blank.", "ERROR", WARNING_MESSAGE);
    }


    public void deleteChapter(String chapterClickedOn) {
        int delConfirm = JOptionPane.showConfirmDialog(sFrame, "Are you sure you want to delete "+chapterClickedOn+"?", "CONFIRMATION", YES_NO_OPTION, WARNING_MESSAGE);
        switch (delConfirm){
            case 0:
                //delete subject from database;
                String SQLdeleteSubject= "DELETE FROM "+selectedSubject.replaceAll(" ","_")+" "
                        +"WHERE chapter_name='"+chapterClickedOn+"';"
                        +"DROP TABLE "+chapterClickedOn.replaceAll(" ", "_")+";";
                sql.deleteSQL(SQLdeleteSubject);
                restart();
                break;
        }
    }
    public void renameChapter(String chapterName) {
        String input = JOptionPane.showInputDialog(sFrame, "Enter the new name for '"+chapterName+"':", "RENAME CHAPTER", PLAIN_MESSAGE);
        if(input == null){}
        else if (!(input.isBlank())) {
            String chapterNewName = input.toUpperCase();
            String SQLupdateName = "UPDATE "+selectedSubject.replaceAll(" ", "_")+" "
                    +"SET chapter_name = '"+chapterNewName+"' "
                    +"WHERE chapter_name = '"+chapterName+"';"
                    +"ALTER TABLE "+chapterName.replaceAll(" ", "_")+" " +
                    "RENAME TO "+chapterNewName.replaceAll(" ", "_")+";";
            sql.updateSQL(SQLupdateName);
            restart();
        } else mFrame.showMessage(panel, "ERROR: Chapter name cannot be blank.", "ERROR", WARNING_MESSAGE);
    }

    public void restart(){
        panel.removeAll();
        addAllButtons();
        panel.revalidate();
        panel.repaint();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("ADD CHAPTER")){

            chapterName = JOptionPane.showInputDialog(panel, "Enter chapter name:");

            addChapterInSystem(chapterName);

        } else if(e.getActionCommand().equalsIgnoreCase("BACK")){
            sFrame.dispose();
            new MainFrame();
        } else {
            String chapterClickedOn = e.getActionCommand();
            sFrame.dispose();
            new ChapterFrame(chapterClickedOn, selectedSubject);

        }
    }


    //MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == BUTTON3) {

            /*    YES_OPTION = 0;    NO_OPTION = 1;    CANCEL_OPTION = 2;    OK_OPTION = 0;    CLOSED_OPTION = -1;    */
            String chapterClickedOn = e.getComponent().getName();

            Object[] options = {"DELETE", "RENAME", "CANCEL"};
            int selectedOption = showOptionDialog(sFrame, "Choose whether you want to DELETE or RENAME '" + chapterClickedOn + "':", "ALTER CHAPTER", YES_NO_CANCEL_OPTION, PLAIN_MESSAGE, null, options, null);
            switch (selectedOption) {
                case 0:
                    deleteChapter(chapterClickedOn);
                    break;
                case 1:
                    renameChapter(chapterClickedOn);
                    break;
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