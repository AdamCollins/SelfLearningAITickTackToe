import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/*Width = 300 height = 300*/
public class Game extends JApplet implements MouseListener
{
    private Board board;
    private Image xImg, oImg;
    private Graphics2D g;
    private AI ai;
    private int clicks = 0;
    private int windowSize = 800;
    private Graphics2D graphics2D;
    private boolean isPlaying;

    @Override
    public void init()
    {
        board = new Board();
        ai = new AI();
        addMouseListener(this);
        isPlaying = true;
    }

    @Override
    public void paint(Graphics _g)
    {
        int x = 100;
        g = (Graphics2D) _g;
        drawBoard();

    }

    //Updates window frame and printed board.
    public void drawBoard()
    {
        Graphics2D g = (Graphics2D) getGraphics();
        g.setStroke(new BasicStroke(3));
        g.drawLine(windowSize / 3, 0, windowSize / 3, windowSize);
        g.drawLine(windowSize * 2 / 3, 0, windowSize * 2 / 3, windowSize);
        g.drawLine(0, windowSize / 3, windowSize, windowSize / 3);
        g.drawLine(0, windowSize * 2 / 3, windowSize, windowSize * 2 / 3);

        int[][] b = Board.getMatrix();
        g.setFont(new Font("TimesRoman", Font.BOLD, 256));
        for (int r = 0; r < b.length; r++)
        {
            for (int c = 0; c < b.length; c++)
                if (b[r][c] == 1) g.drawString("X", c * windowSize / 3 + 50, r * windowSize / 3 + 220);
                else if (b[r][c] == -1) g.drawString("O", c * windowSize / 3 + 50, r * windowSize / 3 + 220);
        }
        if (Board.hasWon() != 0 || Board.isFull())
        {
            gameOverScreen(Board.hasWon());
            System.out.println(board);
            return;
        }
        if (Board.hasWon() != 0 || Board.isFull()) gameOverScreen(Board.hasWon());
    }


    private void resetGame()
    {
        Graphics2D g = (Graphics2D) getGraphics();
        Board.resetBoard();
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, windowSize, windowSize);
        drawBoard();
        isPlaying = true;
        System.out.println(board);
    }

    /*
        Called when board clicked. Plays m Move on board.
     */
    private void boardClicked(Move m)
    {
        if (Board.isFreeSpot(m.getR(), m.getC()))
        {
            Board.playMove(m);
            if (Board.hasWon() == 0 && !Board.isFull()) ai.playTurn();
        }
    }

    /*
        Displays winner and calls ai evolve method.
     */
    private void gameOverScreen(int winner)
    {
        Graphics2D g = (Graphics2D) getGraphics();
        g.setFont(new Font("TimesRoman", Font.BOLD, 128));
        g.setColor(new Color(231, 34, 14));
        String endMessage = "";
        if (winner == 1) endMessage = "YOU WIN";
        else if (winner == -1) endMessage = "COMP WIN";
        else
        {
            endMessage = "TIE GAME";
        }
        g.drawString(endMessage, 100, windowSize / 2);
        isPlaying = false;
        ai.evolve(winner);
    }

    /**
        @Precondition mouseReleased.
        Calls boardClicked passing Move correlating with mouse position relative to board.
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (isPlaying)
        {
            repaint(0, 0, windowSize, windowSize);
            boardClicked(new Move(e.getY() / (windowSize / 3), e.getX() / (windowSize / 3), 1));
        } else
        {
            resetGame();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }
    @Override
    public void mousePressed(MouseEvent e)
    {

    }
}