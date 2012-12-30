package ttt;

import java.util.Arrays;
import java.util.Random;
/**
 * The class representation of a computer player.
 * @author Phillip Johnson
 */

public class ComputerPlayer extends Player
{
    private Random r;
    
    /**
     * Creates a new computer player.
     */
    public ComputerPlayer()
    {
        r = new Random();
    }
    
    @Override
    public int takeTurn(BoardState bs, byte[] currentDisplay)
    {
        int myPlay = -1;
        
        myPlay = bestPlay(bs, currentDisplay);
        
        return myPlay;
    }
    
    private int randomPlay()
    {
        return r.nextInt(9);
    }
    
    private int bestPlay(BoardState bs, byte[] currentDisplay)
    {
        int optimalPlay = retrieveOptimalPlayLocation(bs);
        return optimalPlay;
    }
    
    private int retrieveOptimalPlayLocation(BoardState bs)
    {
        //Find matching board state
        BoardState matchingState = retrieveKnownBoardState(bs);
        //Find the highest value in the array
        int[] logic = Arrays.copyOf(matchingState.getLogicCounter(),9);
        Arrays.sort(logic);
        int optimalPlayValue = logic[8];
        //Find a play with the highest value
        logic = matchingState.getLogicCounter();
        int optimalPlayLocation = -1;
        boolean bestPlayFound = false;
        
        //It's possible that multiple plays are equal. 
        //This picks a random best play.
        while(!bestPlayFound)
        {
            int randomEntry = r.nextInt(9);
            int playValueToCheck = logic[randomEntry]; //Select a random play
            if(playValueToCheck == optimalPlayValue && checkPlayAvailable(bs, randomEntry))
            {
                optimalPlayLocation = randomEntry;
                bestPlayFound = true;
            }
                
        }
        return optimalPlayLocation;
    } 
        
}
