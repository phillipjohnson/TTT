
package ttt;

import java.util.Arrays;

/**
 * A computer that learns using the MENACE method
 * @author Phillip Johnson
 */
public class ComputerPlayer_Learner extends ComputerPlayer
{

    public ComputerPlayer_Learner(playerRank rank)
    {
        super(rank);
    }
    
    @Override
    public int takeTurn(BoardState bs, byte[] currentDisplay)
    {
        int myPlay = -1;

        myPlay = bestPlay(bs);
        
        return myPlay;
    }
    
    private int bestPlay(BoardState bs)
    {
        int optimalPlay = retrieveOptimalPlayLocation(bs);
        return optimalPlay;
    }
    
    private int retrieveOptimalPlayLocation(BoardState bs)
    {
        //Find matching board state
        BoardState matchingState = retrieveKnownBoardState(bs);
        //Find the highest value in the array
        int[] bsCounter = null;
        
        if(rank == playerRank.ALPHA)
        {
            bsCounter = Arrays.copyOf(matchingState.getPlayerAlphaLogicCounter(),9);
        }
        else if(rank == playerRank.BETA)
        {
            bsCounter = Arrays.copyOf(matchingState.getPlayerBetaLogicCounter(),9);
        }
        Arrays.sort(bsCounter);
        int optimalPlayValue = bsCounter[8];
        
        //Find a play with the highest value
        if(rank == playerRank.ALPHA)
        {
            bsCounter = matchingState.getPlayerAlphaLogicCounter();
        }
        else if(rank == playerRank.BETA)
        {
            bsCounter = matchingState.getPlayerBetaLogicCounter();
        }

        int optimalPlayLocation = -1;
        boolean bestPlayFound = false;
        
        //It's possible that multiple plays are equal. 
        //This picks a random best play.
        while(!bestPlayFound)
        {
            int randomEntry = r.nextInt(9);
            int playValueToCheck = bsCounter[randomEntry]; //Select a random play
            if(playValueToCheck == optimalPlayValue && checkPlayAvailable(bs, randomEntry))
            {
                optimalPlayLocation = randomEntry;
                bestPlayFound = true;
            }
                
        }
        return optimalPlayLocation;
    } 
    
}
