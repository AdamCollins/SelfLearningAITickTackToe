import java.util.ArrayList;

public class AI
{
    ArrayList<BoardPosition> boardPositions;
    ArrayList<BoardPosition> generationBoardPositions;
    public AI()
    {
        boardPositions = new ArrayList<BoardPosition>();
        generationBoardPositions = new ArrayList<BoardPosition>();
        int[][] temp = new int[3][3];
        temp[0][0] = 1;
        temp[2][0] = -1;
        int[][] temp2 = new int[3][3];
        temp2[0][0] = 1;
        temp2[2][0] = -1;
        temp2[2][2] = 1;
        temp2[1][1] = -1;
        //boardPositions.add(new BoardPosition(temp2));
        boardPositions.add(new BoardPosition(temp));

    }

    public void playTurn()
    {
        Move move = chooseMove();
        Board.playMove(move);
        generationBoardPositions.add(new BoardPosition(Board.getMatrix()));
        System.out.println("Moves to choose from: \n-----------------------");
        for (BoardPosition bp: boardPositions)
        {
            System.out.println(bp);
        }
        System.out.println("AI moved: "+move);

    }



    private Move chooseMove()
    {
        BoardPosition currentBoardPosition = new BoardPosition(Board.getMatrix());

        for (BoardPosition bp:boardPositions)
        {
            System.out.println(bp);
            if(currentBoardPosition.isPossibleNextBoardPos(bp))
            {
                System.out.println("Choosen from list");
                return Board.boardDifference(currentBoardPosition.getBoardMatrix(), bp.getBoardMatrix());
            }
        }

        int r,c;
        do
        {
            r = (int)(Math.random()*3);
            c = (int)(Math.random()*3);

        }while (!Board.isFreeSpot(r,c));
        System.out.println("choosen randomly");
        return new Move(r,c,-1);
    }

    public void evolve(int winner)
    {
        if(winner!=1)
        {
            for (BoardPosition bp: generationBoardPositions)
            {
                boardPositions.add(bp);
                System.out.println(bp);
            }
            boardPositions.add(new BoardPosition(Board.getMatrix()));
        }
        generationBoardPositions = new ArrayList<BoardPosition>();

    }
}

class BoardPosition
{
    private int[][] boardMatrix;
    BoardPosition(int[][] b)
    {
        boardMatrix = new int[b.length][b[0].length];
        for (int r = 0; r <b.length; r++)
            for (int c = 0; c < b[0].length; c++)
                boardMatrix[r][c] = b[r][c];
    }

    public boolean isPossibleNextBoardPos(BoardPosition b2)
    {
        for (int r = 0; r <this.boardMatrix.length; r++)
        {
            for (int c = 0; c<this.boardMatrix[0].length; c++)
            {
                if(this.boardMatrix[r][c]!=0 && this.boardMatrix[r][c]!=b2.boardMatrix[r][c])
                    return false;
            }
        }

        if(this.numOfPieces()+1==b2.numOfPieces())
            return true;

        return false;
    }

    private int numOfPieces()
    {
        int numOfPieces = 0;
        for (int r = 0; r <boardMatrix.length; r++)
        {
            for (int c = 0; c < boardMatrix[0].length; c++)
            {
                if (boardMatrix[r][c]!=0) numOfPieces ++;
            }
        }
        return numOfPieces;
    }

    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    public String toString()
    {
        String s = "";

        for (int r = 0; r < boardMatrix.length; r++)
        {
            for (int c = 0; c < boardMatrix[0].length; c++)
            {
                s+= "["+ boardMatrix[r][c] +"]";
            }
            s+="\n";
        }
        return s;
    }
}