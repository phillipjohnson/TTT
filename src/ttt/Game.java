package ttt;

import java.util.Scanner;

/**
 * The class that controls the top-level code for the game and players.
 * 
 * @author Phillip
 */
public abstract class Game
{
    //Add the blank board as a possible board state
    static{
        BoardState initial = new BoardState(new byte[]{0,0,0,0,0,0,0,0,0});
        ComputerPlayer.boardStateChecker.addNewState(initial);
    }
  
    
    /**
     * Processes the game loop
     */
    public abstract void play();

    
}
