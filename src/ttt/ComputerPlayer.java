package ttt;

import java.util.Random;
/**
 * The class representation of a computer player.
 * @author Phillip Johnson
 */

public abstract class ComputerPlayer extends Player
{
    public Random r;
    
    /**
     * Creates a new computer player.
     */
    public ComputerPlayer(playerRank rank)
    {
        super(rank);
        r = new Random();
    }
    
    public int randomPlay(BoardState bs)
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
    
    
        
}
