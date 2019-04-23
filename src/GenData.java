import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class GenData extends JFrame implements Serializable{

    JButton[][] buttons = new JButton[3][3];
    JFrame frame = new JFrame("TicTacToe");                    //Global frame and grid button variables
    JButton reset = new JButton("Reset");             //Create reset button for game

    JButton storeOutput = new JButton("store output");
    JButton storeInput = new JButton("store input");
    JButton save = new JButton("save");

    int moveCounter = 9;
    boolean gameWon = false;
    int WhoseTurn = 1;

    ArrayList<Double> input;                        // game data
    ArrayList<Double> expectedOutput;
    String winner;

    HashMap<ArrayList<Double>, ArrayList<Double>> data;
    int lastButtonPressed;

    public GenData()                                        //Tic tac default constructor which adds and dimensions Jframe
    {
        super();
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);        //Setting dimension of Jframe and setting parameters
        frame.setVisible(true);
        frame.setResizable(false);

    }

    //storing training data from games

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

    ArrayList<Double> getOutput(){
        //initialize array of zeros
        ArrayList<Double> position = new ArrayList<>();
        for (int j = 0; j<9; j++){
            position.add(0.0);
        }
        // insert appropriate 1.0 from board
        position.set(lastButtonPressed, 1.0);

        return position;
    }

    private void checkWin()
    {
        if (buttons[0][2].getText()==buttons[1][2].getText()&& buttons[1][2].getText()==buttons[2][2].getText()&& buttons[2][2].getText()==buttons[0][2].getText()&& buttons[1][2].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][2].getText()+ " wins!!!");

        }
        if (buttons[0][1].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[2][1].getText()&& buttons[2][1].getText()==buttons[0][1].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

        }
        if (buttons[0][0].getText()==buttons[1][0].getText()&& buttons[1][0].getText()==buttons[2][0].getText()&& buttons[2][0].getText()==buttons[0][0].getText()&& buttons[1][0].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][0].getText()+ " wins!!!");

        }
        if (buttons[2][0].getText()==buttons[2][1].getText()&& buttons[2][1].getText()==buttons[2][2].getText()&& buttons[2][2].getText()==buttons[2][0].getText()&& buttons[2][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[2][1].getText()+ " wins!!!");

        }
        if (buttons[1][0].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[1][2].getText()&& buttons[1][2].getText()==buttons[1][0].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

        }
        if (buttons[0][0].getText()==buttons[0][1].getText()&& buttons[0][1].getText()==buttons[0][2].getText()&& buttons[0][2].getText()==buttons[0][0].getText()&& buttons[0][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[0][1].getText()+ " wins!!!");

        }
        if (buttons[0][0].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[2][2].getText()&& buttons[2][2].getText()==buttons[0][0].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

        }
        if (buttons[0][2].getText()==buttons[1][1].getText()&& buttons[1][1].getText()==buttons[2][0].getText()&& buttons[2][0].getText()==buttons[0][2].getText()&& buttons[1][1].getText()!="")
        {
            gameWon = true;
            WhoseTurn = 0;
            System.out.println(buttons[1][1].getText()+ " wins!!!");

        }
        if (moveCounter == 0)
        {                                               //exception not triggered by stalemate
            gameWon = true;
            WhoseTurn = 0;
            System.out.println("Stalemate");

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

        menu.add(reset, BorderLayout.NORTH);
        menu.add(storeInput, BorderLayout.WEST);
        menu.add(storeOutput, BorderLayout.EAST);
        menu.add(save, BorderLayout.CENTER);

        reset.addActionListener(new myActionListener());
        storeInput.addActionListener(new myActionListener());
        storeOutput.addActionListener(new myActionListener());
        save.addActionListener(new myActionListener());

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
        data = new HashMap<>();
        winner = "Stalemate";

    }

    private class myActionListener implements ActionListener, Serializable
    {      //Implementing action listener for buttons
        public void actionPerformed(ActionEvent a)
        {
            //Display X's or O's on the buttons
            if(gameWon == false)
            {
                if(a.getSource() == buttons[0][0])                  //Checking which button is pressed
                {
                    if (WhoseTurn == 1) {
                        buttons[0][0].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[0][0].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 0;
                    buttons[0][0].setEnabled(false);
                    moveCounter--;
                    checkWin();
                }
                else if(a.getSource() == buttons[0][1])
                {
                    if (WhoseTurn == 1) {
                        buttons[0][1].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[0][1].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 1;
                    buttons[0][1].setEnabled(false);
                    moveCounter--;
                    checkWin();

                }
                else if(a.getSource() == buttons[1][0])
                {
                    if (WhoseTurn == 1) {
                        buttons[1][0].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[1][0].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 3;
                    buttons[1][0].setEnabled(false);
                    moveCounter--;
                    checkWin();

                }
                else if(a.getSource() == buttons[1][1])
                {
                    if (WhoseTurn == 1) {
                        buttons[1][1].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[1][1].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 4;
                    buttons[1][1].setEnabled(false);
                    moveCounter--;
                    checkWin();
                }
                else if(a.getSource() == buttons[1][2])
                {
                    if (WhoseTurn == 1) {
                        buttons[1][2].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[1][2].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 5;
                    buttons[1][2].setEnabled(false);
                    moveCounter--;
                    checkWin();
                }
                else if(a.getSource() == buttons[2][2])
                {
                    if (WhoseTurn == 1) {
                        buttons[2][2].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[2][2].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 8;
                    buttons[2][2].setEnabled(false);
                    moveCounter--;
                    checkWin();
                }
                else if(a.getSource() == buttons[0][2])
                {
                    if (WhoseTurn == 1) {
                        buttons[0][2].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[0][2].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 2;
                    buttons[0][2].setEnabled(false);
                    moveCounter--;
                    checkWin();
                }
                else if(a.getSource() == buttons[2][1])
                {
                    if (WhoseTurn == 1) {
                        buttons[2][1].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[2][1].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 7;
                    buttons[2][1].setEnabled(false);
                    moveCounter--;
                    checkWin();
                }
                else if(a.getSource() == buttons[2][0])
                {
                    if (WhoseTurn == 1) {
                        buttons[2][0].setText("X");
                        WhoseTurn = 2;
                    } else {
                        buttons[2][0].setText("O");
                        WhoseTurn = 1;
                    }
                    lastButtonPressed = 6;
                    buttons[2][0].setEnabled(false);
                    moveCounter--;
                    checkWin();
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

            }
            if (a.getSource().equals(storeInput)){
                input = getPosition();
                System.out.println(input);
                System.out.println(data);
            }
            if (a.getSource().equals(storeOutput)){
                expectedOutput = getOutput();
                data.put(input, expectedOutput);
                System.out.println(expectedOutput);
            }
            if (a.getSource().equals(save)){
                try {
                    FileOutputStream fileStream = new FileOutputStream("trainingdata.ml");
                    ObjectOutputStream os = new ObjectOutputStream(fileStream);
                    os.writeObject(data);
                    os.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HashMap<ArrayList<Double>, ArrayList<Double>> getData(){
        return data;
    }

    public static void main(String[] args)
    {
        GenData game = new GenData();         //main method and instantiating tic tac object and calling initialize function
        game.initialize();
    }
}