
package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;


public class ReversiFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = -389310768365918217L;
	
	private ReversiPanel mReversiPanel;
	
	/** Creates new form Reversi */
    public ReversiFrame() {
        initComponents();
        
        // add reversi panel
        mReversiPanel = new ReversiPanel();
        this.add(mReversiPanel, BorderLayout.CENTER);
        pack();
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reversi");
        setResizable(false);

        pack();
    }


    public static void startGame(){
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

    /**
    * param args the command line arguments
    */
//    public static void mmain(String[] args) {
//    	// set to user's look and feel
//        //startGame();
//    }

}
