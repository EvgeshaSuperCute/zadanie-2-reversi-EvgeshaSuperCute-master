package sk.stuba.fei.uim.oop.GIU;

import sk.stuba.fei.uim.oop.MECHANIC.Reversi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class ReversiPanel extends JPanel implements KeyListener {

    private static JList<String> MoveList;
    private JCheckBox btnCompFirst;
    private JButton restartGameBtn;
    private JSlider boardSizeSlider;
    private JScrollPane moveListScroller;
    private JLabel lableMoveList;
    private static JLabel Status;
    private JPanel moveListPanel, leftWrapper;



    private static sk.stuba.fei.uim.oop.GIU.Board Board;

    public void initNewBoard() {
        if (Board != null) {
            this.remove(Board);
            Board = null;
        }
        Board = new Board();
        add(Board, BorderLayout.CENTER);

    }


    public ReversiPanel() {

        initComponents();

        init();
    }




    private void init() {

        initNewBoard();
        Reversi.getInstance().newGame();
    }

    //Status messag

    public static void setMessage(String msg) {
        Status.setText(msg);
    }

    private void initComponents() {


        Status = new JLabel();
        moveListPanel = new JPanel();
        leftWrapper = new JPanel();
        boardSizeSlider = new JSlider(6, 12, 8);
        moveListScroller = new JScrollPane();
        MoveList = new JList<>();
        lableMoveList = new JLabel();
        restartGameBtn = new JButton();

        btnCompFirst = new JCheckBox();
        btnCompFirst.setText("Computer first");

        restartGameBtn.setText("Start Game");
        restartGameBtn.setVisible(true);

        restartGameBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                RestartGameActionPerformed(evt);
            }
        });

        boardSizeSlider.addChangeListener(new ChangeListener() {

            @Override

            public void stateChanged(ChangeEvent e) {

                if (boardSizeSlider.getValue() / 2 != 0) {
                    boardSizeSlider.setValue(boardSizeSlider.getValue() / 2 * 2);
                }else
                    boardSizeSlider.setValue(boardSizeSlider.getValue());



            }

        });

        setForeground(new Color(20, 145, 56));
        setLayout(new BorderLayout());

        Status.setFont(new Font("Ubuntu", 1, 15)); // NOI18N
        Status.setForeground(new Color(20, 145, 56));
        Status.setText("Piece count: ");
        add(Status, BorderLayout.SOUTH);

        moveListPanel.setLayout(new BorderLayout(5, 0));

        moveListScroller.setPreferredSize(new Dimension(100, 130));

        MoveList.setFont(new Font("Verdana", 0, 16));
        MoveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moveListScroller.setViewportView(MoveList);

        boardSizeSlider.setMajorTickSpacing(2);
        boardSizeSlider.setPaintTicks(true);
        boardSizeSlider.setPaintLabels(true);

        moveListPanel.add(moveListScroller, BorderLayout.EAST);

        lableMoveList.setText("  Move List:");
        moveListPanel.add(lableMoveList, BorderLayout.NORTH);
        add(moveListPanel, BorderLayout.EAST);
        add(leftWrapper, BorderLayout.WEST);
        leftWrapper.add(boardSizeSlider, BorderLayout.CENTER);
        leftWrapper.add(restartGameBtn, BorderLayout.SOUTH);
        //leftWrapper.add(btnCompFirst, BorderLayout.WEST);
    }


    private void RestartGameActionPerformed(ActionEvent evt) {
        moveLoop: for (int i = 0; i < Reversi.B_size; i++) {
            for (int j = 0; j < Reversi.B_size; j++) {
                if (Reversi.getInstance().movePiece(i, j))
                    break moveLoop;
            }
        }

        Reversi.B_size = boardSizeSlider.getValue();
        Board.changeBoardScale();
        initNewBoard();

        Reversi.getInstance().newGame();
    }



    public static void update() {
        Reversi reversiGame = Reversi.getInstance();
        MoveList.setListData(reversiGame.getMoveList());
        MoveList.setSelectedIndex(reversiGame.getMoveList().size() - 1);

        switch (reversiGame.getGameState()) {
            case Reversi.inProcess:
                setMessage(String.format("  White: %d, Black: %d", reversiGame.getWhiteScore(),
                        reversiGame.getBlackScore()));
                Board.repaint();
                break;
            case Reversi.endProcess:
                String msg;
                if (reversiGame.getBlackScore() > reversiGame.getWhiteScore())
                    msg = String.format("Black win: %d : %d", reversiGame.getBlackScore(), reversiGame.getWhiteScore());
                else if (reversiGame.getBlackScore() < reversiGame.getWhiteScore())
                    msg = String.format("Black win: %d : %d", reversiGame.getWhiteScore(), reversiGame.getBlackScore());
                else
                    msg = String.format("Draw: %d : %d", reversiGame.getBlackScore(), reversiGame.getBlackScore());
                JOptionPane.showMessageDialog(ReversiPanel.Board, msg);
                break;
        }

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
