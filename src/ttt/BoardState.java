package ttt;

import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 * BoardState is a class that represents a snapshot of an in-progress
 * tic-tac-toe game.
 * 
 * @author  Phillip Johnson
 */

public class BoardState
{
    private static int idCount = 15;
    private int id;
            
    private byte[] state;
    private int[] logicCounter_PlayerAlpha;
    private int[] logicCounter_PlayerBeta;
    
    /**
     * Constructor for a new board state.
     * @param currentState  An array representing the current plays on the 
     *                      board.
     */
    
    public BoardState(byte[] currentState)
    {
        setID();
        state = currentState.clone();
        initializeLogic(currentState);
    }
    
    public byte[] getStateValues()
    {
        return state;
    }
    
    private void initializeLogic(byte[] layout)
    {
        logicCounter_PlayerAlpha = new int[]{10,10,10,10,10,10,10,10,10};
        logicCounter_PlayerBeta = new int[]{10,10,10,10,10,10,10,10,10};
        
        for(int i = 0; i < layout.length; i++)
        {
            if(layout[i]!=0) //Unavailable play
            {
                logicCounter_PlayerAlpha[i] = 0;
                logicCounter_PlayerBeta[i] = 0; 
            }
        }
    }
    /**
     * Returns Player One's logic counter of the board state.
     * The array stores values for each possible play based on previous game
     * outcomes. A higher number means the play has been more favorable and a
     * lower number means the play has been less favorable. 
     * An invalid play has the value of 0.
     * The lowest value a valid play can contain is 1.
     * There is no upper bound limit for the value of a valid play.
     * @return  the array containing the counts for 
     *          each possible play
     */
    
    public int[] getPlayerAlphaLogicCounter()
    {
        return logicCounter_PlayerAlpha;
    }
    
    /**
     * Returns Player Two's logic counter of the board state.
     * The array stores values for each possible play based on previous game
     * outcomes. A higher number means the play has been more favorable and a
     * lower number means the play has been less favorable. 
     * An invalid play has the value of 0.
     * The lowest value a valid play can contain is 1.
     * There is no upper bound limit for the value of a valid play.
     * @return  the array containing the counts for 
     *          each possible play
     */
    
    public int[] getPlayerBetaLogicCounter()
    {
        return logicCounter_PlayerBeta;
    }
    
    /**
     * Updates the logic counter for the board state.
     * @param location          The board location of the play to update.
     * @param amount            The amount to change the value of the logic counter by.
     * @param playerToUpdate    One for player one, two for player two.
     */
       
    public void changeLikelihood(int location, int amount, playerRank rank)
    {
        //Plays should remain valid, even if bad.
        //Only modify if the result will be at least 1.
        if(rank == playerRank.ALPHA)
        {
            if(logicCounter_PlayerAlpha[location] + amount >= 1) 
                logicCounter_PlayerAlpha[location] += amount;
        }
        else if(rank == playerRank.BETA)
        {
            if(logicCounter_PlayerBeta[location] + amount >= 1) 
                logicCounter_PlayerBeta[location] += amount;
        }
    }
    /**
     * Returns the unique ID of the board state.
     * @return  The unique ID of the board state.
     */
    
    public int getID()
    {
        return id;
    }
    
    private void setID()
    {
        id = idCount;
        idCount++;
    }
    @Override
    public String toString()
    {
        String layout="";
        for(int i = 0; i < state.length ; i++)
        {
            layout += state[i] + " ";
        }
        return("ID: " +  id + " Layout: [" + layout + "]");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final BoardState other = (BoardState) obj;
        if (!Arrays.equals(this.state, other.state))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Arrays.hashCode(this.state);
        return hash;
    }
    
}
