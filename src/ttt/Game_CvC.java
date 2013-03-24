/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt;

/**
 *
 * @author Phillip
 */
public class Game_CvC extends Game
{
    private ComputerPlayer player1;
    private ComputerPlayer player2;
    
    
    public Game_CvC(ComputerLogic player1logic, ComputerLogic player2logic)
    {
        player1 = new ComputerPlayer(player1logic, playerRank.ALPHA);
        player2 = new ComputerPlayer(player2logic, playerRank.BETA);
    }
    
    public void play(){};
    
    /**
     * Gets the first player
     * @return the first computer player instance
     */
    public ComputerPlayer getPlayerOne()
    {
        return player1;
    }
    /**
     * Gets the seconds player
     * @return the second computer player instance
     */
    public ComputerPlayer getPlayerTwo()
    {
        return player2;
    }
    
}


