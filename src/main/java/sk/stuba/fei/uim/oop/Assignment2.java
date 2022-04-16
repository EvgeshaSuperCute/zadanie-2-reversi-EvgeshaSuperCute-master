package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.GIU.ReversiFrame;

import javax.swing.UIManager;

public class Assignment2 {
    private static void startGame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new ReversiFrame().setVisible(true));

    }

    public static void main(String[] args) {
        startGame();
    }
}
