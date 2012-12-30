package ttt;

import java.util.Scanner;

/**
 * The class that controls the top-level code for the game and players.
 * 
 * @author Phillip
 */
public class Game
{
    private HumanPlayer human;
    private ComputerPlayer computer;
    
    private int draws, computerVictories, humanVictories;
    
    /**
     * Creates a new game with a human and computer player
     */
    
    public Game()
    {
        human = new HumanPlayer();
        computer = new ComputerPlayer();
    }
    
    //Add the blank board as a possible board state
    static{
        BoardState initial = new BoardState(new byte[]{0,0,0,0,0,0,0,0,0});
        ComputerPlayer.boardStateChecker.addNewState(initial);
    }
    
    /**
     * Processes the game loop
     */
    public void play()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to a MENACE-style Java implementation of" + 
                " Tic-Tac-Toe.");
        
        boolean continuePlay = true;
        while(continuePlay)
        {
            GameRound round = new GameRound();
            round.playRound();
            
            String choice = "";
        
            while(!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"))
            {
                System.out.println("Would you like to play again? (Y)es / (N)o");
                choice = sc.nextLine();
            }
            
            if(choice.equalsIgnoreCase("n"))
            {
                continuePlay = false;
            }
            else
            {
                continuePlay = true;
            }
        }
        
    }
    
   /**
    * Gets the instance of the human player
    * @return the human player instance.
    */
    public HumanPlayer getHumanPlayer()
    {
        return human;
    }
    /**
     * Gets the instance of the computer player
     * @return the computer player instance.
     */
    public ComputerPlayer getComputerPlayer()
    {
        return computer;
    }
    
}
