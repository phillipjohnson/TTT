package ttt;

import java.util.Arrays;
import java.util.Scanner;

/**
 * A single round of TTT.
 * @author Phillip
 */
public class GameRound
{
    private Player player1, player2;
    private BoardState currentState;
    
    private boolean victory;
    private int turns;
    
    private int[][] moveHistory = new int[9][2];

    private static Game theGame = new Game();
    private GameLogic gameLogic;
    private static BoardStateChecker computerLogic;
    
    byte[] display = new byte[9];
    
    /**
     * Creates a new round.
     */
    public GameRound()
    {       
        gameLogic = new GameLogic();
        computerLogic = ComputerPlayer.boardStateChecker;
    }
    /**
     * Sets up the details of the players for the round.
     */
    public void playRound()
    {
        Scanner sc = new Scanner(System.in);
        
        String choice = "";
        
        while(!choice.equalsIgnoreCase("X") && !choice.equalsIgnoreCase("O"))
        {
            System.out.println("Would you like to play as X or O? " + 
                    "(X plays first)");
            choice = sc.nextLine();
        }
        
        theGame.getHumanPlayer().setPlayerSymbol(choice);
        
        if(choice.equalsIgnoreCase("X"))
        {
            theGame.getComputerPlayer().setPlayerSymbol("O");
            player1 = theGame.getHumanPlayer();
            player2 = theGame.getComputerPlayer();
        }
        else
        {
            theGame.getComputerPlayer().setPlayerSymbol("X");
            player1 = theGame.getComputerPlayer();
            player2 = theGame.getHumanPlayer();
        }
        
        System.out.println("Use the numpad to make your plays. " + 
                "E.g. '7' is the top-left corner.");
        
        currentState = BoardStateChecker.getKnownBoardStates().get(15);
        display = new byte[]{0,0,0,0,0,0,0,0,0};
        
        processTurns();
        processEndOfRound();
        
    }
    /**
     * Loops through the turns of the round.
     */
    public void processTurns()
    {
        turns = 0;
        victory = false;
        
        while(turns != 9 && !victory)
        {
            Player currentPlayer;
            int playLocation = 0;
            if(turns % 2==0)
                currentPlayer = player1;
            else
                currentPlayer = player2;
            
            //backend playLocation
            playLocation = currentPlayer.takeTurn(currentState, display);
            //front end playLocation
            int displayPlayLocation = currentPlayer.convertPlayLocation(currentState, playLocation, display, false);
            
            //Update backend boardstate
            byte[] newLayout = Arrays.copyOf(currentState.getStateValues(),9);
            newLayout[playLocation] = currentPlayer.makeYerMark();
            
            
            if(currentPlayer instanceof ComputerPlayer)
            {
                //The computer updates the logic for the 
                //board state before they played
                updateMoveHistory(currentState, playLocation);
            }
            
            //Update the current state with the new play
            currentState = computerLogic.findMatchingBoardState(newLayout);
            
            //Update frontend display state
            
            display[displayPlayLocation] = currentPlayer.makeYerMark();
            
            printBoard();
            victory = gameLogic.checkWin(display);
            turns++;
        }
        
    }
    
    /**
     * Saves a record for the play made on a single turn.
     * 
     * @param bs        The BoardState of the game before the play was made
     * @param location  The location where the play was made.
     */
    public void updateMoveHistory(BoardState bs, int location)
    {
        moveHistory[turns] = new int[]{bs.getID(),location};
    }
    
    /**
     * Process the results of the round and updates logic counter.
     * 
     * The logicAmountChange values can be tweaked as desired. The classic
     * MENACE implementation makes no change for a draw, but two optimal TTT
     * players will always force a draw. Therefore, I felt the computer should
     * get some credit if it can force a draw. For obvious reasons, a loss
     * should always be less valuable than a win.
     */
    public void processEndOfRound()
    {

        Player lastToPlay;
        if(turns % 2==0)
            lastToPlay = player2;
        else
            lastToPlay = player1;

        BoardState boardToUpdate;
        int logicAmountChange = 0;

        if(victory && lastToPlay instanceof HumanPlayer) //Human wins
        {
            System.out.println("You won in " + turns + " moves!");
            logicAmountChange = -1;
            
        }
        else if(victory && lastToPlay instanceof ComputerPlayer) //Computer wins
        {
            System.out.println("The computer won in " + turns + " moves!");
            logicAmountChange = 2;
        }
        else //Draw
        {
            System.out.println("The game ended in a draw.");
            logicAmountChange = 1;
        }
        
        for(int[] turn : moveHistory)
        {
            if(turn[0]!=0)
            {
                boardToUpdate = BoardStateChecker.getKnownBoardStates().get(turn[0]);
                boardToUpdate.changeLikelihood(turn[1],logicAmountChange);
            }
        }

    }
    /**
     * Displays the current layout for the user
     */
    public void printBoard()
    {
        
        char[] boardToShow = new char[9];
        for(int i = 0; i < display.length; i++)
        {
            if(display[i] == 1)
                boardToShow[i] = 'X';
            else if(display[i] == -1)
                boardToShow[i] = 'O';
            else
                boardToShow[i]=' ';
        }
        
        System.out.println("+---+---+---+");
        System.out.println("| " + boardToShow[0] + " | " + boardToShow[1]
                + " | " + boardToShow[2] + " |");
        System.out.println("+---+---+---+");
        System.out.println("| " + boardToShow[3] + " | " + boardToShow[4] 
                + " | " + boardToShow[5] + " |");
        System.out.println("+---+---+---+");
        System.out.println("| " + boardToShow[6] + " | " + boardToShow[7] 
                + " | " + boardToShow[8] + " |");
        System.out.println("+---+---+---+");
        
    }
    
    /**
     * Returns the current BoardState of the round.
     * 
     * @return The current BoardState.
     */
    public BoardState getBoardState()
    {
        return currentState;
    }
    
}
