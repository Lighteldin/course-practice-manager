package me.jehn.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChapterFrame implements ActionListener {
    private final MainFrame mFrame = new MainFrame("");
    private final JFrame cFrame = new JFrame();
    private final JPanel panel = new JPanel();

    private String selectedChapter;
    private String selectedSubject;
    public ChapterFrame(String selectedChapter, String selectedSubject){
        this.selectedChapter = selectedChapter;
        this.selectedSubject = selectedSubject;

        mFrame.addButton(panel, this, null, "BACK", Color.white);
        mFrame.addButton(panel, this, null, "ADD QUESTION", Color.white);
        mFrame.addButton(panel, this, null, "SOLVE", Color.white);
        createSubjectGui(selectedChapter);
    }
    public void createSubjectGui(String chapterName){
        cFrame.setSize(500,250);
        cFrame.setResizable(false);
        cFrame.setTitle(selectedChapter);
        cFrame.setLocationRelativeTo(null);
        cFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new FlowLayout());
        cFrame.add(panel);
        cFrame.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("BACK")) {
            cFrame.dispose();
            new SubjectFrame(selectedSubject);

        } else if( e.getActionCommand().equalsIgnoreCase("ADD QUESTION")) {
            cFrame.dispose();
            new AddQuestionFrame(selectedChapter, selectedSubject);

        } else if (e.getActionCommand().equalsIgnoreCase("SOLVE")) {
            cFrame.dispose();
            new SolveQuestionFrame(selectedChapter, selectedSubject, 1);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
