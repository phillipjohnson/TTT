package ttt;

import java.util.Arrays;


/**
 * Enum that stores a unique player designation for the whole game
 * @author Phillip Johnson
 */
enum playerRank
{
    ALPHA, BETA;
}
/**
 * The class that represents a TTT player.
 * @author Phillip Johnson
 */
public abstract class Player
{
    String symbol;
    public static final BoardStateChecker boardStateChecker = new BoardStateChecker();
    playerRank rank;
    
    /**
     * Constructor of a Player
     */
    public Player(playerRank rank)
    {
        this.rank = rank;
    }
    /**
     * Sets the visual token to be used for this player's plays on the board.
     * @param symbol The visual token representing this player
     */
    public void setPlayerSymbol(String symbol)
    {
        //Should add code here to restrict what symbols can be used
        //(i.e. X or O)
        this.symbol = symbol;
    }

    /**
     * Returns the appropriate number to use for the backend array.
     * @return The numerical representation of the player's symbol.
     */
    public byte makeYerMark()
    {
        byte mark = 0;
        if(symbol.equalsIgnoreCase("X"))
            mark = 1;
        else
            mark = -1;
        return mark;
    }
    
    /**
     * Returns the matching board state.
     * 
     * @param bs    The board state to find of match of.
     * @return      The matching state, based on ID.
     */
    public BoardState retrieveKnownBoardState(BoardState bs)
    {
        return BoardStateChecker.getKnownBoardStates().get(bs.getID());
    }
    /**
     * Transforms a play location to the correct backend location and 
     * vice-versa.
     * 
     * Because there are eight possible views for the same TTT board, 
     * it is necessary to account for the current view being displayed to user.
     * This method works to find a match, calculate the steps to get to that 
     * match, then apply those same steps to the specific playLocation passed.
     * 
     * @param bs                The board state to compare to
     * @param playLocation      The location to convert
     * @param currentDisplay    The array representation of the current plays
     * @param direction         <code>true</code> to convert to a 
     *                          backend BoardState
     *                          <code>false</code> to convert to a 
     *                          frontend display
     * @return                  The converted play location
     */
    public int convertPlayLocation(BoardState bs, int playLocation, byte[] currentDisplay, boolean direction)
    {
        
        byte[] displayToAlter = Arrays.copyOf(currentDisplay, 9); //copy values
        byte[] backendLayout = Arrays.copyOf(bs.getStateValues(),9);
        if(!Arrays.equals(backendLayout,displayToAlter)) //The arrays already match
        {
            
            int shift = 0;
            int flip = 0;
            
            outer:
            for(int i = 0 ; i < 2; i++)
            {
                displayToAlter = BoardStateChecker.flip(displayToAlter);
                for(int j = 0 ; j < 4 ; j++)
                {
                    displayToAlter = BoardStateChecker.rotate(displayToAlter);
                    if(Arrays.equals(displayToAlter,backendLayout))
                    {
                        flip = i + 1;
                        shift = j + 1;
                        break outer;
                    }
                }
            }
            
            //!direction is clockwise, extra shifts are necessary 
            //when rotating clockwise. One counter-clockwise rotation is
            //the same as three clockwise rotations, for example.
            if(!direction) 
            {
                switch(shift)
                {
                    case 0:
                        shift = 0;
                        break;
                    case 1:
                        shift = 3;
                        break;
                    case 2:
                        shift = 2;
                        break;
                    case 3:
                        shift = 1;
                        break;
                }
                
                //Rotate then flip
                for(int i = 0; i < shift; i++)
                {
                    playLocation = rotatePlayLocation(playLocation);
                }
                
                if(flip==1)
                {
                    playLocation = flipPlayLocation(playLocation);
                }
                
            }
            else
            {
                //Flip then rotate
                if(flip==1)
                {
                    playLocation = flipPlayLocation(playLocation);
                }

                for(int i = 0; i < shift; i++)
                {
                    playLocation = rotatePlayLocation(playLocation);
                }
            }
            
        }
        
        return playLocation;
    }
    
    private int rotatePlayLocation(int playLocation)
    {
        int rotatedLocation = -1;
        switch(playLocation)
        {
            case 2:
                rotatedLocation = 0;
                break;
            case 5:
                rotatedLocation = 1;
                break;
            case 8:
                rotatedLocation = 2;
                break;
            case 1:
                rotatedLocation = 3;
                break;
            case 4:
                rotatedLocation = 4;
                break;
            case 7:
                rotatedLocation = 5;
                break;
            case 0:
                rotatedLocation = 6;
                break;
            case 3:
                rotatedLocation = 7;
                break;
            case 6:
                rotatedLocation = 8;
                break;
        }
           
        return rotatedLocation;    
    }
    
    private int flipPlayLocation(int playLocation)
    {
        int flippedLocation = -1;
        switch(playLocation)
        {
            case 0:
                flippedLocation = 6;
                break;
            case 1:
                flippedLocation = 7;
                break;
            case 2:
                flippedLocation = 8;
                break;
            case 3:
                flippedLocation = 3;
                break;
            case 4:
                flippedLocation = 4;
                break;
            case 5:
                flippedLocation = 5;
                break;
            case 6:
                flippedLocation = 0;
                break;
            case 7:
                flippedLocation = 1;
                break;
            case 8:
                flippedLocation = 2;
                break;
        }
           
        return flippedLocation;    
    }
    /**
     * Checks to see if a given play is valid based on the BoardState
     * @param bs            The BoardState to check
     * @param playLocation  The location to check
     * @return              <code>true</code> if the play is valid
     *                      <code>false</code> if invalid
     */
    public final boolean checkPlayAvailable(BoardState bs, int playLocation)
    {
        boolean locationAvailable;

        byte[] currentPlays = bs.getStateValues();
        if(currentPlays[playLocation]!=0)
        {
            locationAvailable = false;
            //mark the play as invalid for future
            bs.getPlayerAlphaLogicCounter()[playLocation] = 0;
            bs.getPlayerBetaLogicCounter()[playLocation] = 0; 
        }
        else
            locationAvailable = true;
        
        return locationAvailable;
    }
    /**
     * The method used to process the turn for the player.
     * 
     * @param bs                The board state to use in determining the
     *                          play location
     * @param currentDisplay    The array representing the frontend 
     *                          display of the  board
     * @return                  The location to play
     */
    public abstract int takeTurn(BoardState bs, byte[] currentDisplay);
    
}
