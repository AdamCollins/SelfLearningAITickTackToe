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
public class Game extends JApplet implements MouseListener{
    private Board board;
    private Image xImg, oImg;
    private Graphics2D g;
    private AI ai;
    private int clicks = 0;
    private int windowSize = 800;
    private Graphics2D graphics2D;
    private boolean isPlaying;

    @Override
    public void init() {
        board = new Board();
        ai = new AI(board);
        addMouseListener(this);
        System.out.println(board);
        isPlaying = true;
    }

    public void paint(Graphics _g) {
        int x = 100;
        g = (Graphics2D) _g;
        drawBoard();
        System.out.println("painted");

    }

    public void drawBoard()
    {
        Graphics2D g = (Graphics2D)getGraphics();
        g.setStroke(new BasicStroke(3));
        g.drawLine(windowSize/3, 0, windowSize/3, windowSize);
        g.drawLine(windowSize*2/3, 0, windowSize*2/3, windowSize);
        g.drawLine(0, windowSize/3, windowSize, windowSize/3);
        g.drawLine(0, windowSize*2/3, windowSize, windowSize*2/3);

        int[][] b = board.getMatrix();
        g.setFont(new Font("TimesRoman",Font.BOLD ,256));
        for(int r = 0; r<b.length; r++)
        {
            for(int c = 0; c<b.length; c++)
                if(b[r][c]==1) g.drawString("X",c*windowSize/3+50, r*windowSize/3+220);
                else if(b[r][c]==-1) g.drawString("O", c*windowSize/3+50, r*windowSize/3+220);
            System.out.println("Board Drawn");
        }

        if(board.hasWon()!=0 || board.isFull()) gameOverScreen(board.hasWon());
        if(isPlaying)ai.playTurn();
        System.out.println(board);
        if(board.hasWon()!=0 || board.isFull()) gameOverScreen(board.hasWon());
    }

    private void resetGame()
    {
        Graphics2D g = (Graphics2D)getGraphics();
        board.resetBoard();
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0,windowSize,windowSize);
        drawBoard();
        isPlaying=true;
        System.out.println(board);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    private void boardClicked(Move m)
    {
        if(board.isFreeSpot(m.getC(), m.getR())) {
            board.playMove(m);
            System.out.println(board);
            System.out.println("Finished boardClicked");
        }
    }

    private void gameOverScreen(int winner)
    {
        Graphics2D g = (Graphics2D) getGraphics();
        g.setFont(new Font("TimesRoman",Font.BOLD ,128));
        g.setColor(new Color(231, 34, 14));
        String endMessage = "";
        if(winner == 1)endMessage = "YOU WIN";
        else if(winner == -1) endMessage = "COMP WIN";
        else
        {
            endMessage = "TIE GAME";
        }
        g.drawString(endMessage, 100,windowSize/2);
        isPlaying = false;
        ai.evolve(winner);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        System.out.println("Mouse Clicked");
        if(isPlaying) {
            System.out.println(board);
            repaint(0, 0, windowSize, windowSize);
            boardClicked(new Move(e.getX() / (windowSize / 3), e.getY() / (windowSize / 3), 1));
        }
        else
        {
            resetGame();
            System.out.println("Reset");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}