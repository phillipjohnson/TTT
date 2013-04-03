package ttt;

import java.util.Random;

/**
 *A game round in which two computers play each other
 * @author Phillip
 */
public class GameRound_Automated extends GameRound
{
    Random _r = new Random();


    public GameRound_Automated(Game game)
    {       
        super(game);
    }

    @Override
    public void playRound()
    {
        if(_r.nextBoolean())
        {
            player1 = ((Game_CvC)theGame).getPlayerAlpha();
            player2 = ((Game_CvC)theGame).getPlayerBeta();
        }
        else
        {
            player2 = ((Game_CvC)theGame).getPlayerAlpha();
            player1 = ((Game_CvC)theGame).getPlayerBeta();
        }
        
        player1.setPlayerSymbol("X");
        player2.setPlayerSymbol("Y");
               
        currentState = BoardStateChecker.getKnownBoardStates().get(15);
        display = new byte[]{0,0,0,0,0,0,0,0,0};
        
        super.processTurns(false);
        processEndOfRound();
        
    }
    
   @Override
    public void processEndOfRound()
    {
        

        Player lastToPlay;
        if(turns % 2==0)
            lastToPlay = player2;
        else
            lastToPlay = player1;

        BoardState boardToUpdate = null;
        int logicAmountChange_PlayerAlpha = 0;
        int logicAmountChange_PlayerBeta = 0;

        if(victory && lastToPlay.rank == playerRank.ALPHA) //Computer one wins
        {
            logicAmountChange_PlayerAlpha = 2;
            logicAmountChange_PlayerBeta = -1;
            ((Game_CvC)theGame).increaseWins(playerRank.ALPHA);
            
        }
        else if(victory && lastToPlay.rank == playerRank.BETA) //Computer two wins
        {
            logicAmountChange_PlayerAlpha = -1;
            logicAmountChange_PlayerBeta = 2;
            ((Game_CvC)theGame).increaseWins(playerRank.BETA);
        }
        else //Draw
        {
            logicAmountChange_PlayerAlpha = 1;
            logicAmountChange_PlayerBeta = 1;
            ((Game_CvC)theGame).increaseDraws();
        }
        
        for(int[] turn : moveHistory)
        {
            if(turn[0]!=0)
            {
                boardToUpdate = BoardStateChecker.getKnownBoardStates().get(turn[0]);
                boardToUpdate.changeLikelihood(turn[1],
                        logicAmountChange_PlayerAlpha, playerRank.ALPHA);
                boardToUpdate.changeLikelihood(turn[1],
                        logicAmountChange_PlayerBeta, playerRank.BETA);
            }
        }
        
       System.out.println(victory + "\t" + 
               lastToPlay.rank + "\t" + 
               turns + "\t");

    }   
    
}
