import java.util.ArrayList;

public class AI
{
    //ArrayList of BoardPositions that have resulted in AI win or tie.
    ArrayList<BoardPosition> goodBoardPositions;
    //ArrayList of BoardPositions that were last AI move before defeat.
    ArrayList<BoardPosition> badBoardPositions;
    //Array of BoardPositions that have happened in that generation.
    ArrayList<BoardPosition> generationBoardPositions;

    public AI()
    {
        goodBoardPositions = new ArrayList<BoardPosition>();
        badBoardPositions = new ArrayList<BoardPosition>();
        generationBoardPositions = new ArrayList<BoardPosition>();

    }

    public void playTurn()
    {
        Move move = chooseMove();
        Board.playMove(move);
        generationBoardPositions.add(new BoardPosition(Board.getMatrix()));
        System.out.println("AI moved: " + move);

    }

    /**
     * @precondition playTurn() called
     * Decides move to play.
     * Either a goodBoardPosition that can be played or a random move that would not lead to
     * a BoardPosition in the badBoardPosition List.
     * @return Move to be played that is avalible.
     */
    private Move chooseMove()
    {
        BoardPosition currentBoardPosition = new BoardPosition(Board.getMatrix());

        for (BoardPosition bp : goodBoardPositions)
        {
            if (currentBoardPosition.isPossibleNextBoardPos(bp))
            {
                System.out.println("Choosen from list");
                return Board.boardDifference(currentBoardPosition.getBoardMatrix(), bp.getBoardMatrix());
            }
        }

        int r, c;
        do
        {
            r = (int) (Math.random() * 3);
            c = (int) (Math.random() * 3);

        } while (!Board.isFreeSpot(r, c));
        Move move = new Move(r, c, -1);
        System.out.println("choosen randomly");
        if (isBadMove(move))
        {
            System.out.println("Bad move, rechoosing.");
            try
            {
                return chooseMove();
            }catch (StackOverflowError e)
            {
                return move;
            }
        }
        return move;
    }

    /**
     *
     * @param move
     * @return true if playing move would but board in BoardPosition in badBoardPosition
     */
    private boolean isBadMove(Move move)
    {
        BoardPosition testBoard = new BoardPosition(testBoard(move));
        for (BoardPosition bbp : badBoardPositions)
            if (bbp.isEqual(testBoard)) return true;
        return false;
    }

    /**
       Creates a test matrix board based on Board if move was played.
     @return int[][] testBoard
     */
    private int[][] testBoard(Move move)
    {
        int[][] testMat = new int[3][3];
        int[][] boardMat = Board.getMatrix();
        for (int r = 0; r < testMat.length; r++)
            for (int c = 0; c < testMat[0].length; c++)
                testMat[r][c] = boardMat[r][c];

        testMat[move.getR()][move.getC()] = move.getPlayerValue();
        return testMat;
    }

    /**
     * @param winner
     * @precondition Game.gameOverScreen is called.
     * If AI won or tied generationBoardPositions are added to goodBoardPositions.
     * if AI lost last BoardPosition in generationBoardPositions is added to badBoardPositions.
     * generationBoardPositions is cleared through reinstantiation.
     */
    public void evolve(int winner)
    {
        System.out.println("evolve");
        System.out.println("gen BP:" + generationBoardPositions.size());
        if (winner != 1)
        {
            for (BoardPosition bp : generationBoardPositions)
            {
                goodBoardPositions.add(bp);
                System.out.println(bp);
            }
            goodBoardPositions.add(new BoardPosition(Board.getMatrix()));
        } else
        {
            badBoardPositions.add(generationBoardPositions.get(generationBoardPositions.size() - 1));
            System.out.println(generationBoardPositions.get(generationBoardPositions.size() - 1));
        }
        generationBoardPositions = new ArrayList<BoardPosition>();

    }
}

class BoardPosition
{
    private int[][] boardMatrix;

    public BoardPosition(int[][] b)
    {
        boardMatrix = new int[b.length][b[0].length];
        for (int r = 0; r < b.length; r++)
            for (int c = 0; c < b[0].length; c++)
                boardMatrix[r][c] = b[r][c];
    }

    /**
     *
     * @param b2
     * @return true if b2 is one piece more than this.
     */
    public boolean isPossibleNextBoardPos(BoardPosition b2)
    {
        for (int r = 0; r < this.boardMatrix.length; r++)
        {
            for (int c = 0; c < this.boardMatrix[0].length; c++)
            {
                if (this.boardMatrix[r][c] != 0 && this.boardMatrix[r][c] != b2.boardMatrix[r][c])
                    return false;
            }
        }

        if (this.numOfPieces() + 1 == b2.numOfPieces())
            return true;

        return false;
    }

    private int numOfPieces()
    {
        int numOfPieces = 0;
        for (int r = 0; r < boardMatrix.length; r++)
        {
            for (int c = 0; c < boardMatrix[0].length; c++)
            {
                if (boardMatrix[r][c] != 0) numOfPieces++;
            }
        }
        return numOfPieces;
    }

    public int[][] getBoardMatrix()
    {
        return boardMatrix;
    }

    public String toString()
    {
        String s = "";

        for (int r = 0; r < boardMatrix.length; r++)
        {
            for (int c = 0; c < boardMatrix[0].length; c++)
            {
                s += "[" + boardMatrix[r][c] + "]";
            }
            s += "\n";
        }
        return s;
    }

    /**
     *
     * @param b2
     * @return true if this.matrix is the same as b2.matrix
     */
    public boolean isEqual(BoardPosition b2)
    {
        int[][] b1Mat = getBoardMatrix();
        int[][] b2Mat = b2.getBoardMatrix();
        for (int r = 0; r < b1Mat.length; r++)
            for (int c = 0; c < b1Mat[0].length; c++)
                if (b1Mat[r][c] != b2Mat[r][c]) return false;

        return true;
    }
}