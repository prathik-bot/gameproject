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

public class Forest
{
	private boolean collision; 
	private GamePanel gp;
	private Image[] tile;
	
	public Forest(GamePanel gpIn)
	{
		gp = gpIn;
		tile = new Image[10];
		tile[0] = getBackImage("grass.png");	
		tile[1] = getBackImage("tree.png");
	}
	
	public Image getBackImage(String fileName)
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
        g.drawImage(tile[0], 0, 0, 10, 10);
    }
}
