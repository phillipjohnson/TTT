package ttt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * BoardStateChecker provides methods for comparing board states.
 * There are approximately 19000 unique possible board states for a game of TTT.
 * However, when considering symmetry, the number is reduced to 304. 
 * 
 * This class rotates and flips boards to find duplicate BoardStates. Although
 * this is not required for memory concerns, it improves the experience for the
 * user who knows a first play in the top left corner is essentially equal to a 
 * first play in any other corner.
 * 
 * @author Phillip Johnson
 */
public class BoardStateChecker
{
    private static final HashMap<Integer,BoardState> knownBoardStates = new HashMap<>();
    
    /**
     * The constructor for a new checker.
     */
    public BoardStateChecker()
    {
    }
    
    
    /**
     * Manipulates a given board layout and attempts to find a match in 
     * the current list of known layouts.
     * 
     * @param layoutToFind  An array representing the current plays 
     *                      on the board.
     * @return              The matching BoardState or a 
     *                      new BoardState is no match is found.
     */
    public BoardState findMatchingBoardState(byte[] layoutToFind)
    {
        BoardState foundBoardState = null;
        boolean newBoardState = true;
        byte[] layoutToFindCopy = layoutToFind.clone();
        
        if(newBoardState)
        {
            outer:
            for(Map.Entry<Integer,BoardState> entry : knownBoardStates.entrySet())
            {
                BoardState knownBoardState = entry.getValue();
                byte[] knownBoardStateValues = knownBoardState.getStateValues();

                if(Arrays.equals(layoutToFindCopy,knownBoardStateValues))
                {
                    newBoardState = false;
                    foundBoardState = knownBoardState;
                    break outer;
                }
                
                if(newBoardState)
                {
                    //There are two possible reflections of the board...
                    for(int i = 0 ; i < 2; i++)
                    {
                        layoutToFindCopy = flip(layoutToFindCopy);
                        //...each of which can have one of 
                        //four possible rotations.
                        for(int j = 0 ; j < 4 ; j++)
                        {
                            layoutToFindCopy = rotate(layoutToFindCopy);
                            if(Arrays.equals(layoutToFindCopy,knownBoardStateValues))
                            {
                                newBoardState = false;
                                foundBoardState = knownBoardState;
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        
        if(newBoardState)
        {
            foundBoardState = new BoardState(layoutToFind);
            addNewState(foundBoardState);
        }

        return foundBoardState;
        
    }
    
    /**
     * Rotates a board layout once counter-clockwise.
     * Each element of the array is shifted appropriately so that when
     * displayed in a 3x3 format, the grid is rotated.
     * 
     * @param layoutToAlter     An array representing the current plays 
     *                          on the board. 
     * @return                  A rotated layout.
     */
    public static byte[] rotate(byte[] layoutToAlter)
    {
        byte[] newLayout = new byte[9];
        
        newLayout[0] = layoutToAlter[2];
        newLayout[1] = layoutToAlter[5];
        newLayout[2] = layoutToAlter[8];
        newLayout[3] = layoutToAlter[1];
        newLayout[4] = layoutToAlter[4];
        newLayout[5] = layoutToAlter[7];
        newLayout[6] = layoutToAlter[0];
        newLayout[7] = layoutToAlter[3];
        newLayout[8] = layoutToAlter[6];
        
        //System.out.print("Rotated : ");
        //debugPrintArray(newLayout);
        
        return newLayout;
    }
    
    /**
     * Flips or reflects a board layout once vertically.
     * Each element of the array is shifted appropriately so that when
     * displayed in a 3x3 format, the grid is reflected in the Y-axis.
     * 
     * @param layoutToAlter An array representing the current plays 
     *                      on the board
     * @return              A flipped layout.
     */
    public static byte[] flip(byte[] layoutToAlter)
    {
        byte[] newLayout = new byte[9];
        
        newLayout[0] = layoutToAlter[6];
        newLayout[1] = layoutToAlter[7];
        newLayout[2] = layoutToAlter[8];
        newLayout[3] = layoutToAlter[3];
        newLayout[4] = layoutToAlter[4];
        newLayout[5] = layoutToAlter[5];
        newLayout[6] = layoutToAlter[0];
        newLayout[7] = layoutToAlter[1];
        newLayout[8] = layoutToAlter[2];
        
        //System.out.print("Fipped : ");
        //debugPrintArray(newLayout);
        
        return newLayout;
    }
 
    /**
     * Adds a board state to the list of known states.
     * @param bs The board state to add.
     */
    public void addNewState(BoardState bs)
    {
        knownBoardStates.put(bs.getID(), bs);
    }
    
    /**
     * Returns the list of known board states.
     * @return The list of known board states.
     */
    public static HashMap<Integer,BoardState> getKnownBoardStates()
    {
        return knownBoardStates;
    }
    
    /**
     * Utility method for creating a readable view of a <code>byte</code> array.
     * @param array The array to print.
     * @return      The readable view of the array.
     */
    public static String arrayToString(byte[] array)
    {
        String display = "";
        for(byte b : array)
        {
            display += b;
            display += " ";
        }
        
        return display;
    }
    
     /**
     * Utility method for creating a readable view of an <code>int</code> array.
     * @param array The array to print.
     * @return      The readable view of the array.
     */
    
    public static String arrayToString(int[] array)
    {
        String display = "";
        for(int i : array)
        {
            display += i;
            display += " ";
        }
        
        return display;
    }
    
}
