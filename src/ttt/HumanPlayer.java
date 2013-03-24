package ttt;

import java.util.Scanner;

/**
 * The class representation of a human player--the user.
 * @author Phillip
 */
public class HumanPlayer extends Player
{
    public HumanPlayer(playerRank rank)
    {
        super(rank);
    }
    
    @Override
    public int takeTurn(BoardState bs, byte[] currentDisplay)
    {
        Scanner sc = new Scanner(System.in);
        
        boolean validMove = false;
        int moveLocation = 0;
        
        while(!validMove)
        {
            char choice = '0';

            while(choice < 49 || choice > 57) //restrict plays to 1-9
            {
                System.out.print("Please enter your move: ");
                choice = sc.next().charAt(0);
            }

            switch(choice)
            {
                case '1':
                    moveLocation = 6;
                    break;
                case '2':
                    moveLocation = 7;
                    break;
                case '3':
                    moveLocation = 8;
                    break;
                case '4':
                    moveLocation = 3;
                    break;
                case '5':
                    moveLocation = 4;
                    break;
                case '6':
                    moveLocation = 5;
                    break;
                case '7':
                    moveLocation = 0;
                    break;
                case '8':
                    moveLocation = 1;
                    break;
                case '9':
                    moveLocation = 2;
                    break;    

            }
            
            //convert to backend
            moveLocation = convertPlayLocation(
                    bs, 
                    moveLocation, 
                    currentDisplay,
                    true);

            validMove = checkPlayAvailable(bs,moveLocation);
            
            if(!validMove)
            {
                System.out.println("Invalid move.");
            }
        }
        
        return moveLocation;
        
    }
    
}
