package ttt;

/**
 * The code to run the game.
 * <p>
 * I saw this concept in passing on Reddit, and found it fascinating as I 
 * read more. In the 1960s, Donald Mitchie described a system that would learn
 * to play Tic Tac Toe called the Matchbox Educable Noughts And Crosses Engine.
 * <p>
 * Each matchbox represents a current board state. Before each turn, the correct
 * matchbox is selected. Inside each matchbox are a number of 9 different kinds
 * of beans or beads. Each kind represents a specific location on the board
 * to play. The system makes the play with the current highest number of beans.
 * <p>
 * At the end of the round, if the system won, a bean for each play made is 
 * added to each corresponding matchbox. If it lost, a bean removed from each 
 * matchbox. In the case of a draw, no change is made.
 * <p>
 * After a sufficient number of rounds, the system "learns" to play by keeping
 * track of which plays resulted in favorable outcomes and which resulted
 * in unfavorable outcomes.
 * <p>
 * For more information, I recommend:
 * <ul>
 * <li><a href="http://shorttermmemoryloss.com/menace/">A New Theory of Awesomeness and Miracles</a></li>
 * <li><a href="http://blog.makezine.com/2009/11/02/mechanical-tic-tac-toe-computer/">Tic-Tac-Toe computer learns with beans</a></li>
 * </ul>
 * <p>
 * In this implementation, BoardState corresponds to a matchbox and the
 * logicCounter array keeps the "bean" count.
 * 
 * @author Phillip Johnson
 */
public class TTT
{    
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
        
    }
}
