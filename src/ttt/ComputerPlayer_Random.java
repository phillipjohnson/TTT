package ttt;

/**
 *A computer player that makes random plays
 * @author Phillip
 */
public class ComputerPlayer_Random extends ComputerPlayer
{

    public ComputerPlayer_Random(playerRank rank)
    {
        super(rank);
    }
        
    
    @Override
    public int takeTurn(BoardState bs, byte[] currentDisplay)
    {
        int myPlay = -1;
        
        myPlay = randomPlay(bs);
        
        return myPlay;
    }
    
}
