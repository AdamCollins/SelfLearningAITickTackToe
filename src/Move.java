public class Move
{
    private int r;
    private int c;
    private int playerValue;

    public Move(int r, int c, int playerValue)
    {
        if(r<0 || r>2 || c<0 || c>2)
        {
            r = 0;
            c = 0;
        }
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