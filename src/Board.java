public class Board {
    private static int[][] board;

    Board() {
        resetBoard();
    }

    public static void resetBoard() {
        int[][] b = new int[3][3];
        for (int r = 0; r < b.length; r++)
            for (int c = 0; c < b[0].length; c++)
                b[r][c] = 0;

        board = b;
    }

    public static void playMove(Move m) {
        if (board[m.getR()][m.getC()] == 0) {
            board[m.getR()][m.getC()] = m.getPlayerValue();
            System.out.println("move from: " + m.getPlayerValue() + "@" + m.getC()+ "," +m.getR());
        }
    }

    public String toString() {
        String b = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                switch (board[r][c]) {
                    case 0:
                        b += "   ";
                        break;
                    case 1:
                        b += " X ";
                        break;
                    case -1:
                        b += " O ";
                }
                if (c == 0 || c == 1) b += "|";
            }
            b += "\n";
            if (r == 0 || r == 1) b += "-----------\n";
        }
        b += "\n Pieces on board: " + numOfPieces();
        return b;
    }

    /**
     * @return player value of winner or 0 if no one has won.
     */
    public static int hasWon() {
        //Checks coloumns for 3 in a row
        for (int r = 0; r < board.length; r++) {
            int h = 0;
            for (int c = 0; c < board[0].length; c++) {
                h += board[r][c];
            }
            if (Math.abs(h) == 3) return h / 3;
        }

        //checks rows for 3 in a coloumn
        for (int c = 0; c < board[0].length; c++) {
            int v = 0;
            for (int r = 0; r < board.length; r++) {
                v += board[r][c];
            }
            if (Math.abs(v) == 3) return v / 3;
        }
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1]!=0)
                ||(board[2][0]==board[1][1] && board[1][1]==board[0][2] && board[1][1]!=0))
                return board[1][1];

        return 0;
    }

    public static boolean isFreeSpot(int r, int c) {
        return board[r][c] == 0;
    }

    public static int[][] getMatrix() {
        return board;
    }

    public static boolean isFull() {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++)
                if (board[r][c] == 0) return false;

        return true;
    }

    public static int numOfPieces() {
        int num = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] != 0) num++;
            }
        }
        return num;
    }

    /**
     * @return AI Move to make b1 equal to b2 and null if there are
     * @precondition b2 has one more pieces than b1
     */
    public static Move boardDifference(int[][] b1, int[][] b2) {
        for (int r = 0; r < b2.length; r++) {
            for (int c = 0; c < b2[0].length; c++)
                if (b2[r][c] != 0 && b1[r][c] == 0)
                    return new Move(r, c, -1);
        }
        return null;
    }
}
