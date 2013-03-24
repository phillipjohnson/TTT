package ttt;

import java.util.Arrays;
import java.util.Random;
/**
 * The class representation of a computer player.
 * @author Phillip Johnson
 */

enum ComputerLogic
{
    RANDOM, LEARNER;
}

public class ComputerPlayer extends Player
{
    private Random r;
    private ComputerLogic logic;
    
    /**
     * Creates a new computer player.
     */
    public ComputerPlayer(ComputerLogic logic, playerRank rank)
    {
        super(rank);
        r = new Random();
        this.logic = logic;
    }
    
    @Override
    public int takeTurn(BoardState bs, byte[] currentDisplay)
    {
        int myPlay = -1;
        
        switch(logic)
        {
            case LEARNER:
                myPlay = bestPlay(bs);
                break;
            case RANDOM:
                myPlay = randomPlay(bs);
                break;
            default:
                assert(false): logic;
                break;

        }
        
        return myPlay;
    }
    
    private int randomPlay(BoardState bs)
    {
        int playLocation = -1;
        boolean validLocation = false;
        while(!validLocation)
        {
            playLocation = r.nextInt(9);
            validLocation = checkPlayAvailable(bs, playLocation);
        }
        
        return playLocation;
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
