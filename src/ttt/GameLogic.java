package ttt;

/**
 * Stores the rules of the game.
 * 
 * Note that by design, ComputerPlayer has no access to this information.
 * It must learn to play optimally without knowing explicity how to win.
 * 
 * @author Phillip Johnson
 */
public class GameLogic
{   
    /**
     * Checks to see if the current board layout constitutes a win.
     * 
     * @param currentStateValues    An array representing the current plays 
     *                              on the board 
     * @return                      <code>true</code> if the game has been won
     *                              <code>false</code> if not
     */
    public static boolean checkWin(byte[] currentStateValues)
    {
        boolean victory = false;
        
        int[][] winStates = new int[][]{
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,4,8},
            {2,4,6},
            {0,3,6},
            {1,4,7},
            {2,5,8}
        };
        
        for(int[] win : winStates)
        {
            if(currentStateValues[win[0]] + currentStateValues[win[1]] + currentStateValues[win[2]] == 3)
                victory = true;
            else if(currentStateValues[win[0]] + currentStateValues[win[1]] + currentStateValues[win[2]] == -3)
                victory = true;
            
            if(victory)
                break;
        }
        
        return victory;
    }
    
}
