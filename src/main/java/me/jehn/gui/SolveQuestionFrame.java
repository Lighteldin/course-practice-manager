package me.jehn.gui;

import me.jehn.sql.SQLMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SolveQuestionFrame implements ActionListener {
    private final SQLMethods sql = new SQLMethods();
    private final MainFrame mFrame = new MainFrame("");
    private final JFrame sFrame = new JFrame();
    private final JFrame aqFrame = new JFrame();
    private final JFrame sqFrame = new JFrame();
    private final JPanel panel = new JPanel();

    private JButton cancelButton;
    private JLabel questionLabel;
    private JToggleButton choice1;
    private JToggleButton choice2;
    private JToggleButton choice3;
    private JToggleButton choice4;

    private String selectedSubject;
    private String selectedChapter;
    private String chosenChoice;
    private String correctChoiceColumn;
    private String correctChoice;

    private int questionsAmount;
    private int questionNumber;
    private int i;
    private ArrayList<String> sqlQuestionWithChoices = new ArrayList<>();
    private ArrayList<Integer> sqlQuestionNumbers = new ArrayList<>();

    //constructor
    public SolveQuestionFrame(String selectedChapter, String selectedSubject, int i){
        this.selectedChapter = selectedChapter;
        this.selectedSubject = selectedSubject;
        this.i = i;
        try{
            store();
            addContent();
            createGUI();
        } catch(IndexOutOfBoundsException e){}
    }

    public void createGUI(){
        sqFrame.setResizable(false);
        sqFrame.setSize(515, 287);
        sqFrame.setTitle("SOLVING");
        sqFrame.setLocationRelativeTo(null);
        sqFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sqFrame.setLayout(null);

        panel.setBackground(Color.white);
        panel.setSize(500,250);
        panel.setLayout(new GridLayout(6, 1));

        sqFrame.add(panel);
        sqFrame.setVisible(true);
    }
    private void addContent() {
        cancelButton = new JButton();
        cancelButton.setText("CANCEL");
        cancelButton.setBackground(Color.white);
        cancelButton.setFocusable(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        questionLabel = new JLabel();
        questionLabel.setText(sqlQuestionWithChoices.get(0));
        panel.add(questionLabel);

        choice1 = new JToggleButton();
        choice1.setText(sqlQuestionWithChoices.get(1));
        choice1.setName("choice1");
        choice1.setFocusable(false);
        choice1.setBackground(Color.pink);
        choice1.addActionListener(this);
        panel.add(choice1);

        choice2 = new JToggleButton();
        choice2.setText(sqlQuestionWithChoices.get(2));
        choice2.setName("choice2");
        choice2.setFocusable(false);
        choice2.setBackground(Color.pink);
        choice2.addActionListener(this);
        panel.add(choice2);

        choice3 = new JToggleButton();
        choice3.setText(sqlQuestionWithChoices.get(3));
        choice3.setName("choice3");
        choice3.setFocusable(false);
        choice3.setBackground(Color.pink);
        choice3.addActionListener(this);
        panel.add(choice3);

        choice4 = new JToggleButton();
        choice4.setText(sqlQuestionWithChoices.get(4));
        choice4.setName("choice4");
        choice4.setFocusable(false);
        choice4.setBackground(Color.pink);
        choice4.addActionListener(this);
        panel.add(choice4);
    }
    public void store(){
        //how many questions
        String countStr = "SELECT COUNT(*) FROM "+selectedChapter.replaceAll(" ", "_")+";";
        questionsAmount = sql.methodSQL(countStr);

        //store question numbers in array
        String str = "SELECT question_number FROM "+selectedChapter.replaceAll(" ", "_")+";";
        sqlQuestionNumbers = storeQuestionNumbers(str, "question_number");

        //according to i
        try {
            questionNumber = sqlQuestionNumbers.get((i - 1));
        } catch (IndexOutOfBoundsException e) {
            mFrame.showMessage(panel, "END OF QUESTIONS!", "INFORMATION", JOptionPane.PLAIN_MESSAGE);
            sqFrame.dispose();
            new ChapterFrame(selectedChapter, selectedSubject);
        }
        //store question along side the choices
        storeQuestion();

        //correct choice
        storeCorrectChoiceColumn();
        storeCorrectChoice();


    }

    public void storeQuestion(){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            ResultSet resultset = stmt.executeQuery("SELECT question, choice1, choice2, choice3, choice4 FROM "+selectedChapter.replaceAll(" ", "_")
                    +" WHERE question_number = "+questionNumber+";");
            while (resultset.next()){
                sqlQuestionWithChoices.add(resultset.getString("question"));
                sqlQuestionWithChoices.add(resultset.getString("choice1"));
                sqlQuestionWithChoices.add(resultset.getString("choice2"));
                sqlQuestionWithChoices.add(resultset.getString("choice3"));
                sqlQuestionWithChoices.add(resultset.getString("choice4"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void storeCorrectChoiceColumn(){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            ResultSet resultset = stmt.executeQuery("SELECT correct_choice FROM "+selectedChapter.replaceAll(" ", "_")+" WHERE question_number = "+questionNumber+";");
            while (resultset.next()){
                correctChoiceColumn = resultset.getString("correct_choice");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void storeCorrectChoice(){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            ResultSet resultset = stmt.executeQuery("SELECT "+correctChoiceColumn+" FROM "+selectedChapter.replaceAll(" ", "_")+" WHERE question_number = "+questionNumber+";");
            while (resultset.next()){
                correctChoice = resultset.getString(correctChoiceColumn);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList storeQuestionNumbers(String statement, String column){
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/subject", "sa", "");
            Statement stmt = connection.createStatement();
            //
            ResultSet resultset = stmt.executeQuery(statement);
            ArrayList<Integer> list = new ArrayList<>();
            while (resultset.next()){
                list.add(resultset.getInt(column));
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("CANCEL")) {
            sqFrame.dispose();
            new ChapterFrame(selectedChapter, selectedSubject);
        }
        for (JToggleButton i : new JToggleButton[] {choice1, choice2, choice3, choice4}){
            if(!e.getSource().equals(i))
                i.setSelected(false);
            if(e.getSource().equals(i)) {
                chosenChoice = e.getActionCommand();
                if (chosenChoice == correctChoice) {
                    mFrame.showMessage(panel, "Correct answer!", "CORRECT ANSWER", JOptionPane.PLAIN_MESSAGE);
                    //clear the array
                    sqlQuestionWithChoices.clear();

                    this.i++;
                    sqFrame.dispose();
                    new SolveQuestionFrame(selectedChapter, selectedSubject, this.i);

                }
                else mFrame.showMessage(panel, "Wrong answer! "+correctChoiceColumn+" was the correct answer.", "WRONG ANSWER", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}