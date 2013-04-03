package ttt;

import java.util.Scanner;

/**
 * A game of Human vs. Computer
 * @author Phillip Johnson
 */
public class Game_HvC extends Game
{
    private Player computerPlayer;
    private Player humanPlayer;
    
    private int draws, computerVictories, humanVictories;
    
    /**
     * Creates a new game with a human and computer player
     */
    
    public Game_HvC(ComputerPlayer computerPlayer)
    {
        humanPlayer = new HumanPlayer(playerRank.ALPHA);
        this.computerPlayer = computerPlayer;
    }
    
    @Override
    public void play()
    {
        Scanner sc = new Scanner(System.in);
        
        boolean continuePlay = true;
        while(continuePlay)
        {
            GameRound round = new GameRound(this);
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
    
    public Player getHumanPlayer()
    {
        return humanPlayer;
    }
    
    public Player getComputerPlayer()
    {
        return computerPlayer;
    }
    
    
}
