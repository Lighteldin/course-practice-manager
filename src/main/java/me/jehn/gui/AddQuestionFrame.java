package me.jehn.gui;

import me.jehn.sql.SQLMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddQuestionFrame extends JFrame implements ActionListener {
    private final SQLMethods sql = new SQLMethods();
    private final MainFrame mFrame = new MainFrame("");
    private final JFrame aqFrame = new JFrame();
    private final JPanel panel = new JPanel();
    private String selectedChapter;
    private String selectedSubject;


    private String questionNumberSave;
    private String questionSave;
    private String choice1Save;
    private String choice2Save;
    private String choice3Save;
    private String choice4Save;
    private String correctChoiceSave;


    private JTextField QNOTextField;
    private JTextField QTextField;
    private JTextField C1TextField;
    private JTextField C2TextField;
    private JTextField C3TextField;
    private JTextField C4TextField;
    private JToggleButton choice1;
    private JToggleButton choice2;
    private JToggleButton choice3;
    private JToggleButton choice4;



    //constructor
    public AddQuestionFrame(String selectedChapter, String selectedSubject){
        this.selectedChapter = selectedChapter;
        this.selectedSubject = selectedSubject;

        addContent();
        createGUI();
    }
    //


    public void createGUI(){
        aqFrame.setResizable(false);
        aqFrame.setSize(520, 300);
        aqFrame.setTitle("ADD QUESTION");
        aqFrame.setLocationRelativeTo(null);
        aqFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        aqFrame.setLayout(null);

        panel.setLayout(null);
        panel.setSize(500,250);

        aqFrame.add(panel);
        aqFrame.setVisible(true);
    }


    public void addContent(){
        createToggleButtons();
        createTextFields();

        createButton(panel, this, "CANCEL", Color.white, 10, 10, 80, 30);
        createButton(panel, this, "ADD", Color.white, 400, 10, 80, 30);

        createLabel(panel, "Question Number", 40, 50, 120, 20);
        createLabel(panel, "Question", 40, 80, 120, 20);
        createLabel(panel, "CHOICE 1", 40, 110, 120, 20);
        createLabel(panel, "CHOICE 2", 40, 140, 120, 20);
        createLabel(panel, "CHOICE 3", 40, 170, 120, 20);
        createLabel(panel, "CHOICE 4", 40, 200, 120, 20);
        createLabel(panel, "CORRECT CHOICE:", 370, 80, 120, 20);
    }


    public void createButton(Container container, ActionListener actionlistener, String name, Color color, int x, int y, int width, int height){
        JButton button = new JButton(name);
        button.setName(name);
        button.setBounds(x,y,width,height);
        button.setBackground(color);
        button.setFocusable(false);
        button.addActionListener(actionlistener);
        container.add(button);
    }


    public void createLabel(Container container, String name, int x, int y, int width, int height){
        JLabel label = new JLabel(name);
        label.setBounds(x,y,width,height);
        container.add(label);

    }


    public void createToggleButtons(){
        choice1 = new JToggleButton("CHOICE 1");
        choice1.setName("choice1");
        choice1.setBounds(360, 110, 120, 20);
        choice1.setBackground(Color.pink);
        choice1.setFocusable(false);
        choice1.addActionListener(this);
        panel.add(choice1);

        choice2 = new JToggleButton("CHOICE 2");
        choice2.setName("choice2");
        choice2.setBounds(360, 140, 120, 20);
        choice2.setBackground(Color.pink);
        choice2.setFocusable(false);
        choice2.addActionListener(this);
        panel.add(choice2);

        choice3 = new JToggleButton("CHOICE 3");
        choice3.setName("choice3");
        choice3.setBounds(360, 170, 120, 20);
        choice3.setBackground(Color.pink);
        choice3.setFocusable(false);
        choice3.addActionListener(this);
        panel.add(choice3);

        choice4 = new JToggleButton("CHOICE 4");
        choice4.setName("choice4");
        choice4.setBounds(360, 200, 120, 20);
        choice4.setBackground(Color.pink);
        choice4.setFocusable(false);
        choice4.addActionListener(this);
        panel.add(choice4);

    }


    public void createTextFields(){
        QNOTextField = new JTextField();
        QNOTextField.setName("QNOTextField");
        QNOTextField.setBounds(150, 50, 50, 20);
        QNOTextField.addActionListener(this);
        panel.add(QNOTextField);

        QTextField = new JTextField();
        QTextField.setName("QTextField");
        QTextField.setBounds(150, 80, 200, 20);
        QTextField.addActionListener(this);
        panel.add(QTextField);

        C1TextField = new JTextField();
        C1TextField.setName("C1TextField");
        C1TextField.setBounds(150, 110, 200, 20);
        C1TextField.addActionListener(this);
        panel.add(C1TextField);

        C2TextField = new JTextField();
        C2TextField.setName("C2TextField");
        C2TextField.setBounds(150, 140, 200, 20);
        C2TextField.addActionListener(this);
        panel.add(C2TextField);

        C3TextField = new JTextField();
        C3TextField.setName("C3TextField");
        C3TextField.setBounds(150, 170, 200, 20);
        C3TextField.addActionListener(this);
        panel.add(C3TextField);

        C4TextField = new JTextField();
        C4TextField.setName("C4TextField");
        C4TextField.setBounds(150, 200, 200, 20);
        C4TextField.addActionListener(this);
        panel.add(C4TextField);
    }


    public boolean hasError(){
        for(String i : new String[] {questionNumberSave,questionSave,choice1Save,choice2Save,choice3Save,choice4Save,correctChoiceSave}){
            if(i.equals(null) || i.isBlank()){
                return true;
            }
        }
        return false;
    }


    public void store(){
        //store question#
        questionNumberSave = QNOTextField.getText();

        //store question
        questionSave = QTextField.getText();

        //store choice1
        choice1Save = C1TextField.getText();

        //store choice2
        choice2Save = C2TextField.getText();

        //store choice3
        choice3Save = C3TextField.getText();

        //store choice4
        choice4Save = C4TextField.getText();

        //already stored correct choice
    }


    public void storeSQL(){
        String update = "INSERT INTO "+selectedChapter.replaceAll(" ", "_")
                +" VALUES('"+questionNumberSave+"','"+questionSave+"','"+choice1Save+"','"+choice2Save+"','"+choice3Save+"','"+choice4Save+"','"+correctChoiceSave+"');";
        sql.updateSQL(update);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("CANCEL")) {
            aqFrame.dispose();
            new ChapterFrame(selectedChapter, selectedSubject);
        } else if (e.getActionCommand().equalsIgnoreCase("ADD")){
            store();
            if(!hasError()) {
                storeSQL();
                aqFrame.dispose();
                new ChapterFrame(selectedChapter, selectedSubject);
                mFrame.showMessage(panel, "Question has been successfully added!", "SUCCESS", JOptionPane.PLAIN_MESSAGE);
            }else{
                mFrame.showMessage(panel, "ERROR: Please fill in everything!", "ERROR", JOptionPane.WARNING_MESSAGE);
            }

        }

        //Toggle off other buttons when 1 is chosen
        for (JToggleButton i : new JToggleButton[] {choice1, choice2, choice3, choice4}){
            if(!e.getSource().equals(i))
                i.setSelected(false);
            if(e.getSource().equals(i))
                correctChoiceSave = e.getActionCommand().toLowerCase().replaceAll(" ", "");
        }
        //
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}