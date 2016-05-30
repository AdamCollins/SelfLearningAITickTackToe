import sun.swing.BakedArrayList;

import java.util.ArrayList;

/**
 * Created by Owner on 07/05/2016.
 */
public class AI {
    int generation;
    Board board;
    ArrayList<BoardPosition> boardPositions;
    ArrayList<BoardPosition> generationBoardpositions;

    public AI(Board b) {
        board = b;
        generation = 1;
        boardPositions = new ArrayList<BoardPosition>();
        generationBoardpositions = new ArrayList<BoardPosition>();
    }

    public Move playTurn()
    {
        generationBoardpositions.add(new BoardPosition(board.getMatrix()));
        Move m = chooseMove();
        System.out.println(m.getC() + " " + m.getR());
        board.playMove(m);
        System.out.println("AI(generation:" + generation+ ") played: " + m);
        return m;
    }

    private Move chooseMove() {
        Move m = null;
        BoardPosition currentBoardPos = new BoardPosition(board.getMatrix());
        boardPositions.add(currentBoardPos);
        for (BoardPosition b : boardPositions)
                if(currentBoardPos.isOnePieceDifference(b)) m = Board.boardDifference(board.getMatrix(), b.getMatrix());

        if (m == null) {
            int c, r;
            do {
                c = (int) (Math.random() * 3);
                r = (int) (Math.random() * 3);
            } while (!board.isFreeSpot(c, r));
            m = new Move(c, r, -1);
        }
        return m;
    }
    /*Called when game ends. Learns moves if AI was victorious*/
    public void evolve(int winner)
    {
        if(winner != 1) {
            for (BoardPosition gbp : generationBoardpositions)
                boardPositions.add(gbp);
            generation++;
            System.out.println("Evolving...");
        }
        else {
            System.out.println("Maybe my siblings won't fail like me.");
        }
    }

    class BoardPosition {
        int[][] board;

        int victories = 0;

        BoardPosition(int[][] board) {
            this.board = board;
        }

        public boolean isOnePieceDifference(BoardPosition b2) {
            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[0].length; c++)
                    if (board[r][c] != 0 && board[r][c] != b2.getMatrix()[r][c])
                        return false;

            if (this.numOfPiecesOnBoard() + 1 == b2.numOfPiecesOnBoard())
                return true;

            return false;
        }

        private int numOfPiecesOnBoard() {
            int n = 0;
            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[0].length; c++)
                    if (board[r][c] != 0) n++;

            return n;
        }

        public int[][] getMatrix() {
            return board;
        }

        public int getVictories() {
            return victories;
        }
    }

}
