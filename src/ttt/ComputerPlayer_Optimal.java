package ttt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * A computer player that plays according to a specific rule set
 * @author Phillip Johnson
 */
public class ComputerPlayer_Optimal extends ComputerPlayer
{
    
    public ComputerPlayer_Optimal(playerRank rank)
    {
        super(rank);
    }
    
    @Override
    public int takeTurn(BoardState bs, byte[] currentDisplay)
    {
        int playLocation = -1;
        
        playLocation = playByTheRules(bs.getStateValues());
        
        return playLocation;
    }
    
    /**
     * Determines how many rounds have been taken this turn
     * @param positions
     * @return The number of rounds taken
     */
    private int currentTurns(byte[] positions)
    {
        int turns = 0;
        for(byte b : positions)
        {
            if(b != 0)
            {
                turns++;
            }
        }
        
        return turns;
    }
    
    /**
     * Returns all possible valid moves given the current board state
     * @param layout    An array representing the current board state
     * @return          A list of available moves
     */
    
    private List<Integer> getAvailableMoves(byte[] layout)
    {
        List<Integer> availableMoves = new ArrayList<>();
        for(int i = 0; i < layout.length ; i++)
        {
            if(layout[i]==0)
            {
                availableMoves.add(i);
            }
        }
        
        return availableMoves;
    }
    
    /**
     * Makes a turn based on a given rule set
     * @param layout An array representing the current board state
     * @return The optimal play
     */
    
    private int playByTheRules(byte[] layout)
    {
        List<Integer> bestPlays = getBestRulePlays(layout);
        int randomBestPlay = new Random().nextInt(bestPlays.size());
        
        return bestPlays.get(randomBestPlay);
    }
    
    /**
     * Returns a list of the best available plays
     * @param layout An array representing the current board state
     * @return The list of best available plays
     */
    private List<Integer> getBestRulePlays(byte[] layout)
    {
        List<Integer> bestPlays = new ArrayList<>();
        List<Integer> moves = getAvailableMoves(layout);
        
        int bestMove = -1;
        
        byte[] layoutCopy;
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkWinPossible(i,layoutCopy))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkBlockPossible(i,layoutCopy))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkForkPossible(i,layoutCopy, false))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        //Checks a special condition where a blocked fork is incorrect
        if(
            currentTurns(layout)==3
            && layout[4] == this.makeYerMark()
            && (
                (
                    (layout[0] == (byte) (this.makeYerMark() * (-1)))
                    && (layout[8] == (byte) (this.makeYerMark() * (-1)))
                )
                ||
                (
                    (layout[2] == (byte) (this.makeYerMark() * (-1)))
                    && (layout[6] == (byte) (this.makeYerMark() * (-1)))
                ) 
            )
        )
        {
            bestPlays.add(1);
            bestPlays.add(3);
            bestPlays.add(5);
            bestPlays.add(7);
            
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
                        
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkForkPossible(i,layoutCopy,true))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        //Play center
        if(moves.contains(4))
        {
            bestPlays.add(4);
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkOppositeCorner(i,layoutCopy))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkEmptyCorner(i,layoutCopy))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
        
        for(int i : moves)
        {
            layoutCopy = Arrays.copyOf(layout, 9);
            if(checkEmptySide(i,layoutCopy))
            {
                bestPlays.add(i);
            }
        }
        
        if(bestPlays.size() > 0)
        {
            return bestPlays;
        }
         
        assert bestPlays.size() > 0: bestPlays;
        return bestPlays;
    }
    
    /**
     * Checks if the computer can make a winning move
     * @param playToCheck   The location to check
     * @param layout        An array representing the current board state
     * @return              <code>true</code> if the play wins the game
     *                      <code>false</code> if not
     */
    private boolean checkWinPossible(int playToCheck, byte[] layout)
    {
        layout[playToCheck] = this.makeYerMark();
        return(GameLogic.checkWin(layout));
    }
    
    /**
     * Checks if the computer can block an opponent's winning move
     * @param playToCheck   The location to check
     * @param layout        An array representing the current board state
     * @return              <code>true</code> if the play blocks an opponent's
     *                         winning move
     *                      <code>false</code> if not
     */
    private boolean checkBlockPossible(int playToCheck, byte[] layout)
    {
        //Negate to check if opponent could win
        layout[playToCheck] = (byte) (this.makeYerMark() * (-1)); 
        return(GameLogic.checkWin(layout));
    }
    
    /**
     * Checks if the computer can make fork (dual winning possibilities)
     * @param playToCheck   The location to check
     * @param layout        An array representing the current board state
     * @return              <code>true</code> if the play makes a fork
     *                      <code>false</code> if not
     */
    private boolean checkForkPossible(int playToCheck, byte[] layout, boolean opponent)
    {
        boolean foundforkedVictory = false;
        int victories = 0;
        
        if(!opponent)
        {
            layout[playToCheck] = this.makeYerMark();
        }
        else
        {
            layout[playToCheck] = (byte) (this.makeYerMark() * (-1));
        }
        List<Integer> newMoves = getAvailableMoves(layout);

        for(int i : newMoves)
        {
            byte[] layoutCopy = Arrays.copyOf(layout, 9);
            if(!opponent)
            {
                if(checkWinPossible(i,layoutCopy))
                {
                    victories++;
                }
            }
            else
            {
                if(checkBlockPossible(i,layoutCopy))
                {
                    victories++;
                }
            }
        }
        
        if(victories > 1)
        {
            foundforkedVictory = true;
        }
        else
        {
            foundforkedVictory = false;
        }
        return foundforkedVictory;
    }
    
    /**
     * Checks if an opposite corner is available
     * @param playToCheck   The location to check
     * @param layout        An array representing the current board state
     * @return              <code>true</code> if an opposite corner is available
     *                      <code>false</code> if not
     */
    private boolean checkOppositeCorner(int playToCheck, byte[] layout)
    {
        boolean oppositeCornerAvailable = false;
        
        if(playToCheck != 0 
                && playToCheck != 2
                && playToCheck != 6
                && playToCheck != 8)
        {
            //the submitted move is not a corner
            return false;
        }
            
        
        int[][] oppositeCorners = new int[][]{
            {0,8},{2,6},{6,2},{8,0}
        };
        
        for(int[] corners : oppositeCorners)
        {
            //The first corner is the opponent
            //The second corner is unplayed
            if(layout[corners[0]] == (byte) (this.makeYerMark() * (-1))
                    && layout[corners[1]] == 0)
            {
                oppositeCornerAvailable = true;
            }
        }
        
        return oppositeCornerAvailable;
    }
    
    /**
     * Checks if an empty corner is available
     * @param playToCheck   The location to check
     * @param layout        An array representing the current board state
     * @return              <code>true</code> if the play is an empty corner
     *                      <code>false</code> if not
     */
    private boolean checkEmptyCorner(int playToCheck, byte[] layout)
    {
        boolean emptyCorner = false;
        int[] corners = new int[]{0,2,6,8};
        
        for(int corner : corners)
        {
            if(playToCheck == corner
                    && layout[corner] == 0)
            {
                emptyCorner = true;
            }
        }
        
        return emptyCorner;
    }
    
    /**
     * Checks if an empty side is available
     * @param playToCheck   The location to check
     * @param layout        An array representing the current board state
     * @return              <code>true</code> if the play is an empty side
     *                      <code>false</code> if not
     */
    private boolean checkEmptySide(int playToCheck, byte[] layout)
    {
        boolean emptySide = false;
        int[] sides = new int[]{1,3,5,7};
        
        for(int side : sides)
        {
            if(playToCheck == side
                    && layout[side] == 0)
            {
                emptySide = true;
            }
        }
        
        return emptySide;
    }
}
