package ttt;

/**
 *A game of Computer vs. computer
 * @author Phillip Johnson
 */
public class Game_CvC extends Game
{
    private ComputerPlayer playerAlpha;
    private ComputerPlayer playerBeta;
    
    private int playerAlphaWins = 0;
    private int playerBetaWins = 0;
    private int draws = 0;
    
    public Game_CvC(ComputerPlayer playerAlpha, ComputerPlayer playerBeta)
    {
        this.playerAlpha = playerAlpha;
        this.playerBeta = playerBeta;
    }
    
    public void play()
    {
        for(int i = 0 ; i < 100; i++)
        {
            GameRound_Automated round = new GameRound_Automated(this);
            round.playRound();
        }
        
        printStats();
    
    }
    
    /**
     * Gets the first player
     * @return the first computer player instance
     */
    public ComputerPlayer getPlayerAlpha()
    {
        return playerAlpha;
    }
    /**
     * Gets the seconds player
     * @return the second computer player instance
     */
    public ComputerPlayer getPlayerBeta()
    {
        return playerBeta;
    }
    
    public void increaseDraws()
    {
        draws++;
    }
    
    public void increaseWins(playerRank rank)
    {
        if(rank == playerRank.ALPHA)
        {
            playerAlphaWins++;
        }
        else if(rank == playerRank.BETA)
        {
            playerBetaWins++;
        }
    }
    
    private void printStats()
    {
        System.out.println("Player Alpa Wins:\t" + playerAlphaWins);
        System.out.println("Player Beta Wins:\t" + playerBetaWins);
        System.out.println("Draws:\t\t\t" + draws);
        
    }
    
}


