public class Board
{
   private int[][] board;
   Board()
   {
       resetBoard();
   }
   
   public void resetBoard()
   {
       int[][] b = new int[3][3];
       for(int r = 0; r<b.length; r++)
        for(int c = 0; c<b[0].length; c++)
          b[r][c] = 0;
          
       board = b;
   }
   
   public void playMove(Move m)
   {
       if(board[m.getR()][m.getC()]==0)
       {
           board[m.getR()][m.getC()] = m.getPlayerValue();
           System.out.println("move from: " + m.getPlayerValue());
       }
   }
   
   public String toString()
   {
       String b = "";
       for(int r = 0; r<board.length; r++)
       {
           for(int c = 0; c<board[0].length; c++)
           {
               switch (board[r][c])
               {
                   case 0: b+="   ";
                   break;
                   case 1: b+=" X ";
                   break;
                   case -1: b+=" O ";
               }
               if(c==0 || c==1)b+="|";
           }
           b+="\n";
           if(r==0||r==1) b+="-----------\n";
       }
       b+="\n Pieces on board: "+ numOfPieces();
       return b;
   }

    /**
     * @return player value of winner or 0 if no one has won.
     */
   public int hasWon()
   {
       int v = 0;
       int h = 0;
       for(int r = 0; r< board.length; r++)
       {
           for(int c = 0; c<board[0].length; c++)
           {
               h+=board[r][c];
               v+=board[c][r];
           }
           if(v == 3 || h == 3) return 1;
           else if(v == -3 || h == -3) return -1;
           v = 0;
           h = 0;
       }
       if(board[1][1] == 1 && (board[0][0]==1 && board[2][2]==1) || (board[0][2]==1 && board[2][0]==1) )
           return 1;
       if(board[1][1] == -1 && (board[0][0]==-1 && board[2][2]==-1) || (board[0][2]==-1 && board[2][0]==-1) )
           return -1;
       if(board[0][0]==board[1][1] && board[1][1]==board[2][2]) return board[1][1];
       if(board[2][0]==board[1][1] && board[1][1]==board[0][2]) return board[1][1];

       return 0;
   }

    public boolean isFreeSpot(int c, int r)
    {
        return board[r][c] == 0;
    }

    public int[][] getMatrix() {
        return board;
    }

    public boolean isFull()
    {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++)
                if(board[r][c]==0) return false;

        return true;
    }

    public int numOfPieces()
    {
        int num = 0;
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[0].length; c++)
            {
                if(board[r][c]!=0) num++;
            }
        }
        return num;
    }
    /**
    * @return AI Move to make b1 equal to b2 and null if there are
    * @precondition b2 has one more pieces than b1
    *
     */
    public static Move boardDifference(int[][] b1, int[][] b2)
    {
        for (int r = 0; r < b2.length; r++)
        {
            for (int c = 0; c<b2[0].length; c++)
                if(b2[r][c]!=0 && b1[r][c]==0)
                    return new Move(c,r, -1);
        }
        return null;
    }
}
