package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Assignment2 {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReversiFrame().setVisible(true);
            }
        });

    }
}
