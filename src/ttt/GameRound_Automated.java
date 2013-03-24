package ttt;

import java.util.Arrays;

/**
 *
 * @author Phillip
 */
public class GameRound_Automated extends GameRound
{
    

    public GameRound_Automated(ComputerLogic player1logic, ComputerLogic player2Logic)
    {       
        theGame = new Game_CvC(player1logic,player2Logic);
    }

    @Override
    public void playRound()
    {
        player1 = ((Game_CvC)theGame).getPlayerOne();
        player2 = ((Game_CvC)theGame).getPlayerTwo();
        
        player1.setPlayerSymbol("X");
        player1.setPlayerSymbol("Y");
               
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

        BoardState boardToUpdate;
        int logicAmountChange_PlayerAlpha = 0;
        int logicAmountChange_PlayerBeta = 0;

        if(victory && lastToPlay.rank == playerRank.ALPHA) //Computer one wins
        {
            logicAmountChange_PlayerAlpha = 2;
            logicAmountChange_PlayerBeta = -1;
            
        }
        else if(victory && lastToPlay.rank == playerRank.BETA) //Computer two wins
        {
            logicAmountChange_PlayerAlpha = -1;
            logicAmountChange_PlayerBeta = 2;
        }
        else //Draw
        {
            logicAmountChange_PlayerAlpha = 1;
            logicAmountChange_PlayerBeta = 1;
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

    }   
    
}
