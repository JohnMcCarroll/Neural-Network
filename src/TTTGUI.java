import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;

class TTTGUI extends JFrame
{

    JButton[][] buttons = new JButton[3][3];
    JFrame frame = new JFrame("TicTacToe");                    //Global frame and grid button variables
    JButton reset = new JButton("Reset");             //Create reset button for game

    JTextField learningRate;                     //text field for rapid adjustment of learning rate
    JLabel learningRateLabel = new JLabel("Learning Rate:");
    JButton enter = new JButton("Enter");

    JOptionPane turn;
    int moveCounter = 9;
    boolean gameWon = false;
    int WhoseTurn = 1;

    ArrayList<ArrayList<Double>> input;                        // game data
    ArrayList<ArrayList<Double>> expectedOutput;
    String winner;

    Network net;                                        //our champion, ladies and gentlemen
    //Double[] position;


    public TTTGUI()
    {
        super();
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        net = new Network(new int[0], 0.0).load("TTTC4.2");
    }

    ArrayList<Double> getPosition(){
        //initialize array of zeros
        ArrayList<Double> position = new ArrayList<>();
        for (int j = 0; j<19; j++){
            position.add(0.0);
        }
        // insert appropriate 1.0's from board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("X")){
                    position.set(3*i+j, 1.0);
                }
                if (buttons[i][j].getText().equals("O")){
                    position.set(3*i+j + 9, 1.0);
                }
                if (WhoseTurn == 1){
                    position.set(18, 1.0);
                }
            }
        }
        return position;
    }

    //storing training data from games

    void storeInput() {
        input.add(getPosition());
    }

    void storeExpectedOutput(int move, ArrayList<Double> output) {
        output.set(move, 1.0);
        expectedOutput.add(output);
    }

    void storeOutcome(){
        if (winner.equals("X")){                                                      //if we won game, we made the right moves
            for (ArrayList<Double> trial:expectedOutput){
                for (int i = 0; i < trial.size(); i++){
                    if (trial.get(i) != 1.0){
                        trial.set(i, 0.0);
                    }

                }
            }
        } else if (winner.equals("O")) {                                                            // if we lost game, disincentivize moves made
            for (ArrayList<Double> trial:expectedOutput){
                for (int i = 0; i < trial.size(); i++){
                    if (trial.get(i) == 1.0){
                        trial.set(i, 0.0);
                    }
                }
            }
        } else {}                                                                       // if stalemate, no change needed
    }


    private void checkWin(int row, int col)
    {
        if (buttons[0][2].getText()==buttons[1][2].getText()&& buttons[1][2].getText()==buttons[2][2].getText()&& buttons[2][2].getText()==buttons[0][2].getText()&& buttons[1][2].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][2].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (buttons[0][1].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[2][1].getText()&& buttons[2][1].getText()==buttons[0][1].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (buttons[0][0].getText()==buttons[1][0].getText()&& buttons[1][0].getText()==buttons[2][0].getText()&& buttons[2][0].getText()==buttons[0][0].getText()&& buttons[1][0].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][0].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (buttons[2][0].getText()==buttons[2][1].getText()&& buttons[2][1].getText()==buttons[2][2].getText()&& buttons[2][2].getText()==buttons[2][0].getText()&& buttons[2][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[2][1].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (buttons[1][0].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[1][2].getText()&& buttons[1][2].getText()==buttons[1][0].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (buttons[0][0].getText()==buttons[0][1].getText()&& buttons[0][1].getText()==buttons[0][2].getText()&& buttons[0][2].getText()==buttons[0][0].getText()&& buttons[0][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[0][1].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (buttons[0][0].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[2][2].getText()&& buttons[2][2].getText()==buttons[0][0].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();

        }
        if (buttons[0][2].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[2][0].getText()&& buttons[2][0].getText()==buttons[0][2].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

            // store who won
            winner = buttons[1][2].getText();
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
        if (moveCounter == 0)
        {                                               //exception not triggered by stalemate
            gameWon = true;
            WhoseTurn = 0;
            System.out.println("Stalemate");

            // store who won
            storeOutcome();

            // train
//                net.runBlock(input, expectedOutput);
            //net.save("TTTChamp1.0");

            //reset
            //reset.doClick();
        }
    }

    private void compTurn(ArrayList<Double> position)
    {
        if (moveCounter < 1){}

        else {
            // store input game state
            storeInput();

            // give game state to NN
            net.forwardFeed(position);
            ArrayList<Double> output = net.getOutput();
            while (WhoseTurn == 1){
                int move = output.indexOf(Collections.max(output));
                if (buttons[move/3][move%3].isEnabled()){
                    buttons[move/3][move%3].setText("X");
                    buttons[move/3][move%3].setEnabled(false);

                    // store output (modified)
                    storeExpectedOutput(move, output);

                    moveCounter--;
                    checkWin(move/3,move%3);
                    WhoseTurn = 2;
                } else {
                    output.set(move, 0.0);
                }
            }
        }
    }

    //random move for training test:
    void randomMove(){
        while (WhoseTurn == 2){
            int move = (int) Math.random()*9;
            if (buttons[move/3][move%3].isEnabled()) {
                buttons[move / 3][move % 3].doClick();
            }
        }
    }

    private void initialize()             //Initialize tic tac toe game board
    {
        JPanel mainPanel = new JPanel(new BorderLayout());         //create main panel container to put layer others on top
        JPanel menu = new JPanel(new BorderLayout());
        JPanel game = new JPanel(new GridLayout(3,3));                     //Create two more panels with layouts for buttons

        frame.add(mainPanel);                                         //add main container panel to frame

        mainPanel.setPreferredSize(new Dimension(325,425));
        menu.setPreferredSize(new Dimension(300,50));                     //Setting dimensions of panels
        game.setPreferredSize(new Dimension(400,400));

        mainPanel.add(menu, BorderLayout.NORTH);                   //Add two panels to the main container panel
        mainPanel.add(game, BorderLayout.SOUTH);

        //Add both enter/reset buttons to menu container panel
        learningRate = new JTextField(net.getLearningRate().toString());
        menu.add(reset, BorderLayout.NORTH);
        menu.add(learningRateLabel, BorderLayout.WEST);
        menu.add(learningRate, BorderLayout.CENTER);
        menu.add(enter, BorderLayout.EAST);

        reset.addActionListener(new myActionListener());
        enter.addActionListener(new myActionListener());

        for(int i = 0; i < 3; i++)                      //Create grid of buttons for tic tac toe game
        {
            for(int j = 0; j < 3; j++)
            {

                buttons[i][j] = new JButton();                //Instantiating buttons
                buttons[i][j].setText("");
                buttons[i][j].setVisible(true);

                game.add(buttons[i][j]);
                buttons[i][j].addActionListener(new myActionListener());        //Adding response event to buttons
            }
        }

        // initialize game data arraylists
        input = new ArrayList<>();
        expectedOutput = new ArrayList<>();
        winner = "Stalemate";

        // start game
        compTurn(getPosition());

    }

    private class myActionListener implements ActionListener
    {      //Implementing action listener for buttons
        public void actionPerformed(ActionEvent a)
        {
            //Display X's or O's on the buttons
            if(gameWon == false)
            {
                if(a.getSource() == buttons[0][0])                  //Checking which button is pressed
                {
                    buttons[0][0].setText("O");
                    buttons[0][0].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(0,0);
                    compTurn(getPosition());

                }
                else if(a.getSource() == buttons[0][1])
                {
                    buttons[0][1].setText("O");
                    buttons[0][1].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(0,1);
                    compTurn(getPosition());

                }
                else if(a.getSource() == buttons[1][0])
                {
                    buttons[1][0].setText("O");
                    buttons[1][0].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(1,0);
                    compTurn(getPosition());

                }
                else if(a.getSource() == buttons[1][1])
                {
                    buttons[1][1].setText("O");
                    buttons[1][1].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(1,1);
                    compTurn(getPosition());

                }
                else if(a.getSource() == buttons[1][2])
                {
                    buttons[1][2].setText("O");
                    buttons[1][2].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(1,2);
                    compTurn(getPosition());
                }
                else if(a.getSource() == buttons[2][2])
                {
                    buttons[2][2].setText("O");
                    buttons[2][2].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(2,2);
                    compTurn(getPosition());
                }
                else if(a.getSource() == buttons[0][2])
                {
                    buttons[0][2].setText("O");
                    buttons[0][2].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(0,2);
                    compTurn(getPosition());
                }
                else if(a.getSource() == buttons[2][1])
                {
                    buttons[2][1].setText("O");
                    buttons[2][1].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(2,1);
                    compTurn(getPosition());
                }
                else if(a.getSource() == buttons[2][0])
                {
                    buttons[2][0].setText("O");
                    buttons[2][0].setEnabled(false);
                    WhoseTurn = 1;
                    moveCounter--;
                    checkWin(2,0);
                    compTurn(getPosition());
                }
            }
            if(a.getSource() == reset)
            {
                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        gameWon = false;
                        buttons[i][j].setText("");
                        buttons[i][j].setEnabled(true);
                        moveCounter = 9;
                        WhoseTurn = 1;
                    }
                }
                // initialize game data arraylists
                input = new ArrayList<>();
                expectedOutput = new ArrayList<>();
                winner = "Stalemate";

                // start game
                compTurn(getPosition());
            }
            if (a.getSource().equals(enter)){
                Double lr = Double.parseDouble(learningRate.getText());
                net.setLearningRate(lr);
            }
        }
    }

    public static void main(String[] args)
    {
        TTTGUI game = new TTTGUI();         //main method and instantiating tic tac object and calling initialize function
        game.initialize();
    }
}