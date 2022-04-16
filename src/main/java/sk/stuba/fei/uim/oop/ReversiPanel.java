package sk.stuba.fei.uim.oop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 *
 * @author luugiathuy
 */
public class ReversiPanel extends javax.swing.JPanel{// implements Observer{

	private static final long serialVersionUID = -1506476411269777398L;
	/** the board */
	private static Board mBoard;
	
    /** Creates new form ReversiPanel */
    public ReversiPanel() {
        initComponents();
        
        init();
    }

    public Board initNewBoard(){
        mBoard = new Board();

        return mBoard;
    }
    
    /** Add board */
    private void init() {
        //Reversi.sBOARD_SIZE = Reversi.SAVE_BOARD_SIZE;
//    	mBoard = new Board();
//		this.add(mBoard, BorderLayout.CENTER);
        this.add(initNewBoard(), BorderLayout.CENTER);
		//Reversi.getInstance().addObserver(this);
		Reversi.getInstance().newGame();
    }
    
    /** Sets status message */
    public static void setMessage(String msg) {
    	lblStatus.setText(msg);
    }


    private void initComponents() {

        lblStatus = new JLabel();
        moveListPanel = new JPanel();
        boardSizeSlider = new JSlider(6,12,Reversi.sBOARD_SIZE);
        moveListScroller = new JScrollPane();
        jMoveList = new JList();
        lblMoveList = new JLabel();
        restartGameBtn = new JButton();

        restartGameBtn.setText("Start Game");
        restartGameBtn.setVisible(true);

        restartGameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartGameActionPerformed(evt);
            }
        });


        boardSizeSlider.addChangeListener(new ChangeListener() {

            @Override

            public void stateChanged(ChangeEvent e) {

                if(boardSizeSlider.getValue()/2 != 0){
                    boardSizeSlider.setValue(boardSizeSlider.getValue()/2*2);
                    System.out.println("SkiderValue: "+boardSizeSlider.getValue());
                }
                System.out.println("BoardSizeValue: "+Reversi.sBOARD_SIZE);
                Reversi.SAVE_BOARD_SIZE = boardSizeSlider.getValue();
                //new ReversiFrame().setVisible(true);


            }

        });


        setForeground(new Color(242, 17, 39));
        setLayout(new BorderLayout());

        lblStatus.setFont(new Font("Ubuntu", 1, 15)); // NOI18N
        lblStatus.setForeground(new Color(255, 0, 0));
        lblStatus.setText("Piece count: ");
        add(lblStatus, BorderLayout.SOUTH);

        moveListPanel.setLayout(new BorderLayout(5,0));

        moveListScroller.setPreferredSize(new Dimension(100, 130));

        jMoveList.setFont(new Font("Verdana", 0, 16));
        jMoveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moveListScroller.setViewportView(jMoveList);

        moveListPanel.add(moveListScroller, BorderLayout.EAST);

        lblMoveList.setText("  Move List:");
        moveListPanel.add(lblMoveList, BorderLayout.NORTH);


        add(moveListPanel, BorderLayout.EAST);
        add(boardSizeSlider, BorderLayout.WEST);
        add(restartGameBtn,BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRestartGameActionPerformed(java.awt.event.ActionEvent evt) {

        //this.add(initNewBoard(),BorderLayout.CENTER);
        Reversi.getInstance().newGame();

    }


    private JButton restartGameBtn;
    private JSlider boardSizeSlider;
    private static JList jMoveList;
    private JLabel lblMoveList;
    private static JLabel lblStatus;
    private JPanel moveListPanel;
    private JScrollPane moveListScroller;



    public static void update() {
        Reversi reversiGame = Reversi.getInstance();
        jMoveList.setListData(reversiGame.getMoveList());
        jMoveList.setSelectedIndex(reversiGame.getMoveList().size() - 1);

        switch (reversiGame.getGameState()) {
            case Reversi.PLAYING:
                setMessage("White " + reversiGame.getWhiteScore() + " - " + reversiGame.getBlackScore() + " Black");
                mBoard.repaint();
                break;
            case Reversi.ENDED:
                String msg = "";
                int blackScore = reversiGame.getBlackScore();
                int whiteScore = reversiGame.getWhiteScore();
                if (blackScore > whiteScore)
                    msg = "Black win: " + blackScore + " - " + whiteScore;
                else if (blackScore < whiteScore)
                    msg = "White win: " + whiteScore + " - " + blackScore;
                else
                    msg = "Draw: " + blackScore + " - " + whiteScore;
                JOptionPane.showMessageDialog(ReversiPanel.mBoard,msg);
                break;
        }

    }

}
