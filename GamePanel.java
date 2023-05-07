// Prathik Kumar and Hrithik Mallireddy
// 5/4/2023
// Game.java (Explorador Español)
// Working on:
	// Week 2: Basic Start, Instructions, and Game panels with navigation CardLayout and null layout complete
		// Navigation between panels are all working well, and we need to fill in the template we have laid out
	// Hrithik was absent for most of Week 1 so Prathik did most of the program so far.
// Practicing: ImageIO, Components(JButtons, MenuBar), and different layouts, like border,
// grid, and flow layout. Practicing using handler classes as well

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.util.Scanner;
import java.awt.Dimension;

class GamePanel extends JPanel implements KeyListener, MouseListener, ActionListener
{
    private Image boyDown1; // boy down version 1
    private Image boyDown2; // boy down version 2

    private Image boyLeft1; // boy left version 1
    private Image boyLeft2; // boy left version 2

    private Image boyRight1; // boy right version 1
    private Image boyRight2; // boy right version 2

    private Image boyUp1; // boy up version 1
    private Image boyUp2; // boy up version 2

    private Image character; // holds character

    private Image grassBG; //Holds Background Image
    private int xPos; // xPos of image
    private int yPos; // yPos of image

    public int worldX, worldY;

    public final int originalTileSize = 16; //16x16, default map tile size
    public final int scale = 3; //We need to scale it for the tiles display with better resolution in the screen
    public final int tileSize = originalTileSize * scale; //48x48 tile, after scaling
    //Number of iles we can display on the screen both horizontally and vertically
    public final int maxScreenCol = 16; //16 tiles horizontally
    public final int maxScreenRow = 12; //12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    public int screenX, screenY;
    private boolean leftChecker;
    private boolean upChecker;
    private boolean rightChecker;
    private boolean downChecker;

    private int velocity; // used for sprint

    private Timer timer = null;

    private int spriteNum;
    private int spriteCounter;
    private String direction;

    TileManager tileM = new TileManager(this);

    public GamePanel(CardLayout cardsIn, CardHolder panelHolderIn)
    {
        leftChecker = false;
        xPos = yPos = 0;
        //Screen X and Y values don't change throught the game. Players screen postion don't change and we
        screenX = screenWidth/2 - tileSize; //This returns half way point in the screen
        screenY = screenWidth/2 - tileSize; //This returns half way point in the screen
        worldX = tileSize * 23; //player position in the beginning.
        worldY = tileSize * 21;
        spriteNum = 1;
        spriteCounter = 0;
        direction = "";

		setLayout(new BorderLayout());
		JButton settings = new JButton();
		JPanel buttone = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttone.add(settings);
		add(buttone, BorderLayout.SOUTH);
		
        CardLayout cards = cardsIn;
        CardHolder panelHolder = panelHolderIn;

        boyUp1 = getImage("Player_Up1.png");
        boyUp2 = getImage("Player_Up2.png");

        boyDown1 = getImage("Player_Down1.png");
        boyDown2 = getImage("Player_Down2.png");

        boyLeft1 = getImage("Player_Left1.png");
        boyLeft2 = getImage("Player_Left2.png");

        boyRight1 = getImage("Player_Right1.png");
        boyRight2 = getImage("Player_Right2.png");
        character = boyUp2;
        
        Image boyUp3 = getImage("Player_Up3.png");
        Image boyDown3 = getImage("Player_Down3.png");

        addKeyListener(this);
        addMouseListener(this);
        
        int downSwitcher = 0;
        int upSwitcher = 0;

        timer = new Timer(250, new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (leftChecker)
                {
                    character = boyLeft2;
                    leftChecker = false;
                }
                if (downChecker)
                {
                    character = boyDown1;
                    downChecker = false;
                }
                if (upChecker)
                {
                    character = boyUp2;
                    upChecker = false;
                }
                if (rightChecker)
                {
                    character = boyRight2;
                    rightChecker = false;
                }
                repaint();
            }
        });
    }

    public Image getImage(String fileName)
    {
        Image picture = null;
        File pictFile = new File(fileName);
        try
        {
            picture = ImageIO.read(pictFile);
        }
        catch(IOException e)
        {
            System.err.println("/n/nERROR: " + fileName + " can't be found./n/n");
            e.printStackTrace();
        }
        return picture;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.GRAY);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);//Draw tiles and the player first, not to hide character
        //g.drawImage(character, xPos, yPos, 100, 100, this); //Default Image
        g2.drawImage(character, screenX, screenY, tileSize+20, tileSize+20, null); //Default Image
        g2.dispose();

    }

    public void keyPressed(KeyEvent evt)
    {
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_SHIFT)
        {
            if (evt.isShiftDown())
            {
                velocity = 5;
            }
            else
                velocity = 0;
        }

        else if (key == KeyEvent.VK_UP)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            yPos -= 8;
            worldY -= 8;
            direction = "up";
            yPos -= velocity;
            worldY -= velocity;
            repaint();
        }
        else if (key == KeyEvent.VK_RIGHT)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            xPos += 8;
            worldX +=8;

            xPos += velocity;
            worldX += velocity;
            direction = "right";

            repaint();
        }
        else if (key == KeyEvent.VK_DOWN)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            yPos += 8;
            yPos += velocity;
            worldY += 8;
            worldY += velocity;
            direction = "down";

            repaint();
        }
        else if (key == KeyEvent.VK_LEFT)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            xPos -= 8;
            worldX -= 8;
            xPos -= velocity;
            worldX -= velocity;
            direction = "left";

            repaint();
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP ||
                key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT)
        {
            if (direction.equals("left"))
            {
                leftChecker = true;
                character = boyLeft1; //new logic
                timer.start();        //new logic
                repaint();
            }
            if (direction.equals("right"))
            {
                rightChecker = true;
                character = boyRight1; //new logic
                timer.start();        //new logic
                repaint();
            }
            if (direction.equals("up"))
            {
                upChecker = true;
                character = boyUp1; //new logic
                timer.start();        //new logic
                repaint();
            }
            if (direction.equals("down"))
            {
                downChecker = true;
                character = boyDown2; //new logic
                timer.start();        //new logic
                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent ke) {}

    public void keyTyped(KeyEvent evt)
    {
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_SHIFT)
        {
            if (evt.isShiftDown())
            {
                velocity = 5;
            }
            else
                velocity = 0;
        }

        else if (key == KeyEvent.VK_UP)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            yPos -= 5;
            worldY -= 5;
            direction = "up";
            yPos -= velocity;
            worldY -= velocity;
            repaint();
        }
        else if (key == KeyEvent.VK_RIGHT)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            xPos += 5;
            worldX += 5;
            xPos += velocity;
            worldX += velocity;
            direction = "right";

            repaint();
        }
        else if (key == KeyEvent.VK_DOWN)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            yPos += 5;
            yPos += velocity;
            worldY += 5;
            worldY += velocity;
            direction = "down";

            repaint();
        }
        else if (key == KeyEvent.VK_LEFT)
        {
            if (!evt.isShiftDown())
                velocity = 0;
            xPos -= 5;
            xPos -= velocity;
            worldX -= 5;
            worldX -= velocity;
            direction = "left";

            repaint();
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP ||
                key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT)
        {
            //System.out.println(direction);
            if (direction.equals("left"))
            {
                leftChecker = true;
                character = boyLeft1;
                timer.start();
                repaint();
            }
            if (direction.equals("right"))
            {
                rightChecker = true;
                character = boyRight1;
                timer.start();
                repaint();
            }
            if (direction.equals("up"))
            {
                upChecker = true;
                character = boyUp1;
                timer.start();
                repaint();
            }
            if (direction.equals("down"))
            {
                downChecker = true;
                character = boyDown2;
                timer.start();
                repaint();
            }
        }
    }

    public void mousePressed(MouseEvent me)
    {
        requestFocusInWindow();
    }

    public void mouseExited(MouseEvent me) {}
    public void mouseClicked(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void actionPerformed(ActionEvent evt) {}
}
