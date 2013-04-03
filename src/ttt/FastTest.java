/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ttt;

/**
 *
 * @author Phillip
 */
public class FastTest
{
    public void go()
    {
        BoardState.idCount = 15;
        
        Game game;
        
        Player playerAlpha = new ComputerPlayer_Learner(playerRank.ALPHA);
        Player playerBeta = new ComputerPlayer_Optimal(playerRank.BETA);
        
        BoardState initial = new BoardState(new byte[]{0,0,0,0,0,0,0,0,0});
        playerAlpha.boardStateChecker.addNewState(initial);
        playerBeta.boardStateChecker.addNewState(initial);
        
        game = new Game_CvC((ComputerPlayer)playerAlpha,
                        (ComputerPlayer)playerBeta);
        
        game.play();
        
        BoardStateChecker.getKnownBoardStates().clear();
        
    }
    
}
