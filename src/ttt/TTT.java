package ttt;

import java.util.Scanner;

/**
 * The code to run the game.
 * <p>
 * I saw this concept in passing on Reddit, and found it fascinating as I 
 * read more. In the 1960s, Donald Mitchie described a system that would learn
 * to play Tic Tac Toe called the Matchbox Educable Noughts And Crosses Engine.
 * <p>
 * Each matchbox represents a current board state. Before each turn, the correct
 * matchbox is selected. Inside each matchbox are a number of 9 different kinds
 * of beans or beads. Each kind represents a specific location on the board
 * to play. The system makes the play with the current highest number of beans.
 * <p>
 * At the end of the round, if the system won, a bean for each play made is 
 * added to each corresponding matchbox. If it lost, a bean removed from each 
 * matchbox. In the case of a draw, no change is made.
 * <p>
 * After a sufficient number of rounds, the system "learns" to play by keeping
 * track of which plays resulted in favorable outcomes and which resulted
 * in unfavorable outcomes.
 * <p>
 * For more information, I recommend:
 * <ul>
 * <li><a href="http://shorttermmemoryloss.com/menace/">A New Theory of Awesomeness and Miracles</a></li>
 * <li><a href="http://blog.makezine.com/2009/11/02/mechanical-tic-tac-toe-computer/">Tic-Tac-Toe computer learns with beans</a></li>
 * </ul>
 * <p>
 * In this implementation, BoardState corresponds to a matchbox and the
 * logicCounter array keeps the "bean" count.
 * 
 * @author Phillip Johnson
 */
public class TTT
{    
    public static void main(String[] args)
    {
        newGame();
        
    }
    
    private static void newGame()
    {
        Game game = null;
        
        System.out.println("Welcome to a MENACE-style Java implementation of" + 
                " Tic-Tac-Toe.\n");
        
        Player playerAlpha = getPlayerType(playerRank.ALPHA);
        Player playerBeta = getPlayerType(playerRank.BETA);
        
        //TODO: Need a better way to instantiate a Game
        if(playerAlpha instanceof HumanPlayer)
        {
            if(playerBeta instanceof HumanPlayer)
            {
                System.out.println("Play type not yet supported");
                System.exit(0);
            }
            else
            {
                game = new Game_HvC((ComputerPlayer)playerBeta);
            }
        }
        else
        {
            if(playerBeta instanceof HumanPlayer)
            {
                game = new Game_HvC((ComputerPlayer)playerAlpha);
            }
            else
            {
                game = new Game_CvC((ComputerPlayer)playerAlpha,
                        (ComputerPlayer)playerBeta);
            }
        }
        
        if(game != null)
        {
            game.play();
        }
    }
    /**
     * Determines what kind of player the user wants
     * @param rank  Designation to keep players separate
     * @return      The selected Player type
     */
    
    private static Player getPlayerType(playerRank rank)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select the player type: \n\n" +
                "(1) Human\n" +
                "(2) Random Computer\n" +
                "(3) Optimal Computer\n" +
                "(4) Learning Computer (MENACE)");
        
        int playerTypeChoice = -1;
        boolean validPlayerType = false;
        
        while(!validPlayerType)
        {
            if(!sc.hasNextInt())
            {
                System.out.println("Please enter a valid number.");
                validPlayerType = false;
            }
            else
            {
                playerTypeChoice = sc.nextInt();
                if(playerTypeChoice < 0 || playerTypeChoice > 4)
                {
                    System.out.println("Please enter a valid game type.");
                    validPlayerType = false;
                }
                else
                {
                    validPlayerType = true;
                }
            }
        }
        
        Player p = null;
        
        switch(playerTypeChoice)
        {
            case 1:
                p = new HumanPlayer(rank);
                break;
            case 2:
                p = new ComputerPlayer_Random(rank);
                break;
            case 3:
                p = new ComputerPlayer_Optimal(rank);
                break;
            case 4:
                p = new ComputerPlayer_Learner(rank);
                break;
            default:
               assert false: playerTypeChoice;
        }
        
        return p;
    }
}
