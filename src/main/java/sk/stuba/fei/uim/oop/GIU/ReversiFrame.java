
package sk.stuba.fei.uim.oop.GIU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ReversiFrame extends javax.swing.JFrame implements KeyListener {



    private ReversiPanel mReversiPanel;

    public ReversiFrame() {
//btnRestartGameActionPerformed
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                setVisible(false);
            }

            @Override
            public boolean accept(Object sender) {
                return super.accept(sender);
            }
        });

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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
