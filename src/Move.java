public class Move
{
    private int r;
    private int c;
    private int playerValue;
    public Move(int c, int r, int playerValue)
    {
        this.r = r;
        this.c = c;
        this.playerValue = playerValue;
    }
    
    public int getR()
    {
        return r;
    }
    
    public int getC()
    {
       return c;
    }
    
    public int getPlayerValue()
    {
        return playerValue;
    }

    public String toString()
    {
        return c + "," + r;
    }


}
