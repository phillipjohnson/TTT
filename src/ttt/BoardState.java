package ttt;

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
    private int[] logicCounter;
    
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
        logicCounter = new int[]{10,10,10,10,10,10,10,10,10};
        
        for(int i = 0; i < layout.length; i++)
        {
            if(layout[i]!=0) //Unavailable play
            {
                logicCounter[i] = 0; 
            }
        }
    }
    /**
     * Returns the logic counter of the board state.
     * The array stores values for each possible play based on previous game
     * outcomes. A higher number means the play has been more favorable and a
     * lower number means the play has been less favorable. 
     * An invalid play has the value of 0.
     * The lowest value a valid play can contain is 1.
     * There is no upper bound limit for the value of a valid play.
     * @return  the array containing the counts for 
     *          each possible play
     */
    
    public int[] getLogicCounter()
    {
        return logicCounter;
    }
    
    /**
     * Updates the logic counter for the board state.
     * @param location  The board location of the play to update.
     * @param amount    The amount to change the value of the logic counter by.
     */
       
    public void changeLikelihood(int location, int amount)
    {
        //Plays should remain valid, even if bad.
        //Only modify if the result will be at least 1.
        if(logicCounter[location] + amount >= 1) 
            logicCounter[location] += amount;
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
