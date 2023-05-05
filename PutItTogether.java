import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PutItTogether
{    
    public PutItTogether()
    {
    }
   
    public static void main(String [] args)
    {
        PutItTogether pit = new PutItTogether();
        pit.run();
    }
   
    public void run()
    {
        JFrame frame = new JFrame("PutItTogether");
        frame.setSize( 800, 700);                
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocation(0,0);
        frame.setResizable(true);
        PutItTogetherHolder pith = new PutItTogetherHolder();        
        frame.getContentPane().add( pith );        
        frame.setVisible(true);        
    }
}

// this panel holds the main cards/panel
class PutItTogetherHolder extends JPanel
{
    public PutItTogetherHolder()
    {
        setBackground(Color.CYAN);

        CardLayout cards = new CardLayout();
        setLayout(cards);
       
        Information info = new Information();    
            
        FixedPanelHolder fph = new FixedPanelHolder(this, cards, info);
        FirstPagePanel fpp = new FirstPagePanel(this, cards, info);
        add(fpp, "First");
        HomePanel hp = new HomePanel(this, cards, info);
        add(hp, "Home");
       
        HomePanelHolder hph = new HomePanelHolder("tennis.jpg", cards, info);        
        BothPictPanel bpp = new BothPictPanel(this, hph, fph);
        add(bpp, "BothPictPanel");   
       
        fph = new FixedPanelHolder(this, cards, info);
        MyPictPanel mpp = new MyPictPanel(this, hph, fph);  
        add(mpp, "MyPictPanel");       
       
        fph = new FixedPanelHolder(this, cards, info);
        FriendPictPanel fepp = new FriendPictPanel(this, hph, fph);
        add(fepp, "FriendPictPanel");
       
        fph = new FixedPanelHolder(this, cards, info);
       
        //cards.show(this, "First");
    }
}

// First page to show up.  Gives information, asks for name.  Goes to Home page.
class FirstPagePanel extends JPanel
{
    private PutItTogetherHolder panelCards;
    private CardLayout cards;
    private Information info;
    private JTextField tfName;
    PutItTogetherHolder pth;
   
    public FirstPagePanel(PutItTogetherHolder panelCardsIn, CardLayout cardsIn, Information infoIn)
    {
       setLayout(null);
       panelCards = panelCardsIn;
       cards = cardsIn;
       info = infoIn;
       
       setBackground(Color.CYAN);
       
       Font font = new Font("Serif", Font.BOLD, 24);
       JTextArea jta = new JTextArea(info.getInfo(), 1, 1);
       jta.setFont(font);
       tfName = new JTextField("What's your name?");
       tfName.setBounds(250, 600, 200, 20);
       jta.setBounds(120, 40, 550, 300);
       JScrollPane sp = new JScrollPane(jta);
       sp.setBounds(120, 40, 550, 300);
       add(sp);
       add(tfName);
       
       JCheckBox check = new JCheckBox("I understand the terms.");
       check.setBounds(600, 600, 100, 100);
       check.setBackground(Color.CYAN);
       add(check);
       
       CheckBoxHandler cbh = new CheckBoxHandler();
       check.addActionListener(cbh);
       check.setBackground(Color.CYAN);
       add(check);
    }  
   
    class TextFieldHandler extends JPanel implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
			String text = tfName.getText();
            info.setName(text);
        }
    }
   
    class CheckBoxHandler extends JPanel implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(panelCards, "Home");
        }
    }
}

class FixedPanelHolder extends JPanel
{
    private Information info;
    private JButton homeButton;    
    private CardLayout cards;
    PutItTogetherHolder pth;
   
    public FixedPanelHolder(PutItTogetherHolder panelCardsIn, CardLayout cardsIn, Information infoIn)
    {
        setLayout(new BorderLayout());
        homeButton = new JButton("Home");
        JButtonHandler jbh = new JButtonHandler();
        homeButton.addActionListener(jbh);
        add(homeButton, BorderLayout.SOUTH);
       
        pth = panelCardsIn;
        cards = cardsIn;
    }  
   
    class JButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(pth, "Home");
        }
    }
}

class HomePanelHolder extends JPanel
{
    private Image picture;
    private Information info;
    private String pictName;
    private CardLayout cards;
   
    public HomePanelHolder (String pictNameIn, CardLayout cardsIn, Information infoIn)
    {
        info = infoIn;
        pictName = pictNameIn;
        cards = cardsIn;
    }

    public Image getImage()
    {
        try
        {
            File pathToFile = new File(pictName);
            picture = ImageIO.read(pathToFile);
        } catch(IOException e)
        {
            System.out.println("Error: "+ e);
            e.printStackTrace();
        }        
        return picture;
    }
   
    public CardLayout getCardLayout()
    {
        return cards;
    }
}

class HomePanel extends JPanel
{
    private JRadioButton picPage, drawPage; //pic page is to go to picture, draw page is to go to settings
    private JButton page1Button, page2Button;
    private CardLayout cards; //everything is on card layout
    private PutItTogetherHolder pth; //panel holder holding the card layout
    
    public HomePanel(PutItTogetherHolder panelCardsIn, CardLayout cardsIn, Information infoIn)
    {   
        setLayout(new BorderLayout());
        
        setBackground(Color.GREEN);
        
        Information info = infoIn;
        pth = panelCardsIn;
        cards = cardsIn;
        
        JPanel gr = new JPanel();
        gr.setLayout(new GridLayout(4, 1));
        add(gr, BorderLayout.WEST);
        JRadioButton picPage = new JRadioButton("Click on this to see friends");
        gr.setBackground(Color.GREEN);
        picPage.setBackground(Color.GREEN);
        gr.add(picPage);
        JRadioButton drawPage = new JRadioButton("Click on this to go to settings");
        drawPage.setBackground(Color.GREEN);
        gr.add(drawPage);
        picPage.addActionListener(new RadioButtonHandler());
        drawPage.addActionListener(new RadioButtonHandler());
        
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout());
        Font font = new Font("Serif", Font.BOLD, 24);
        JLabel welcome = new JLabel("Welcome" + info.getName());
        welcome.setFont(font);
        JPanel centerWelcome = new JPanel();
        centerWelcome.setBackground(Color.GREEN);
        add(centerWelcome, BorderLayout.NORTH);
        centerWelcome.setLayout(new FlowLayout());
        centerWelcome.add(welcome);
        
        JTextArea jta = new JTextArea(infoIn.getInfo(), 1, 1);
        jta.setFont(font);
        jta.setBackground(Color.GREEN);
        add(jta, BorderLayout.CENTER);
    }

    class RadioButtonHandler extends JPanel implements ActionListener
    {       
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			if (command.equals("Click on this to go to settings"))
				cards.show(pth, "DrawPanel");
			if (command.equals("Click on this to see friends"))
				cards.show(pth, "BothPictPanel");
		}
    }
       
    // Since the label for the name was created when the classes constructor was called
    // it needs to be updated after the user types in the name into the text field.
    // Update that label in paintComponent.
    public void paintComponent(Graphics g)
    {
    }
   
   
}

class BothPictPanel extends JPanel implements MouseListener
{
    private Image picture;
    private CardLayout cards;    
    private PutItTogetherHolder pth;
    public BothPictPanel(PutItTogetherHolder panelCardsIn, HomePanelHolder hph, FixedPanelHolder fph)
    {
        setBackground(Color.MAGENTA);
        setLayout(new BorderLayout());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER, 400, 10));
        south.setBackground(Color.MAGENTA);
        south.setPreferredSize(new Dimension(800, 150));
       
        JTextArea instructions = new JTextArea("Click on their heads to view information about them", 2,5);
        instructions.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        //instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       
        south.add(instructions);
        south.add(fph);        
        add(south, BorderLayout.SOUTH);
       
        picture = hph.getImage();        
        cards = hph.getCardLayout();
        pth = panelCardsIn;
        addMouseListener(this);
    }
   
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(picture, 200, 20, 500, 400, this);
    }
   
    public void mousePressed(MouseEvent evt)
    {
		int xPos = evt.getX();
		int yPos = evt.getY();
		
        if (xPos > 140 && xPos < 280 && yPos > 40 && yPos < 365)
            cards.show(pth, "MyPictPanel");  
        else if (xPos > 250 && xPos < 405 && yPos > 40 && yPos < 365)          
            cards.show(pth, "FriendPictPanel");
	}  

    public void mouseClicked(MouseEvent evt){}
    public void mouseReleased(MouseEvent evt){}         //This method is complete.
    public void mouseEntered(MouseEvent evt){}        //This method is complete.
    public void mouseExited(MouseEvent evt){}    
}

class MyPictPanel extends JPanel
{
    private Image img; // holds the image
    private PutItTogetherHolder pth; // used to switch to next card in card layout
    private CardLayout cards; // used for card layout
    
    public MyPictPanel(PutItTogetherHolder panelCardsIn, HomePanelHolder hph, FixedPanelHolder fph)//, CardLayout cardsIn, Information infoIn)
    {      
        setBackground(Color.PINK);
        img = hph.getImage();
        setLayout(new BorderLayout());
        FlowLayout flowForImage = new FlowLayout(FlowLayout.CENTER, 40, 20);
        JPanel buttons = new JPanel(flowForImage);
        Dimension dim = new Dimension(500, 200);
        buttons.setPreferredSize(dim);
        buttons.add(fph);
        buttons.setBackground(Color.PINK);
        buttons.add(fph);
        
       
        JButton seeMore = new JButton("<html> <center> See info for<br>" +
            "the other person </center> </html>");
        seeMore.addActionListener(new JButtonHandler());
        buttons.add(seeMore); 
       
        String label = "Name: Serena Williams, Date of Birth: Septemper 26, 1981, "
			+ "Profession: Tennis. ";
		String accomplishment = "Serena Williams is largely considered to be the greatest"
			+ " womens tennis player ever.";
		
        JLabel pictLabel = new JLabel(label);
        JLabel extraLabel = new JLabel(accomplishment);
        Font font = new Font("Serif", Font.PLAIN, 20);
        pictLabel.setFont(font);
        extraLabel.setFont(font);
        buttons.add(pictLabel);
        buttons.add(extraLabel);
        
        add(buttons, BorderLayout.SOUTH);      
       
        cards = hph.getCardLayout();      
        pth = panelCardsIn;
    }
   
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 40, 40, 380, 500, 190, 0, 600, 500, this); 
    }
   
    class JButtonHandler extends JPanel implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
			String cmd = evt.getActionCommand();
			if (cmd.equals("<html> <center> See info for<br>" +
            "the other person </center> </html>"))
				cards.show(pth, "FriendPictPanel");
			if (cmd.equals("Home"))
				cards.show(pth, "Home");
		}
    }
}

// This class shows the image of the friend. In the panel, it gives info about the friend.
class FriendPictPanel extends JPanel
{
    private Image img; // holds the image
    private PutItTogetherHolder pth; // used to switch to next card in card layout
    private CardLayout cards; // used for card layout
    
    public FriendPictPanel(PutItTogetherHolder panelCardsIn, HomePanelHolder hph, FixedPanelHolder fph)
    {
        setBackground(Color.PINK);
        img = hph.getImage();
        setLayout(new BorderLayout());
        FlowLayout flowForImage = new FlowLayout(FlowLayout.CENTER, 40, 20);
        JPanel buttons = new JPanel(flowForImage);
        Dimension dim = new Dimension(500, 200);
        buttons.setPreferredSize(dim);
        buttons.add(fph);
        buttons.setBackground(Color.PINK);
       
        JButton seeMore = new JButton("<html> <center> See info for<br>" +
            "the other person </center> </html>");
        seeMore.addActionListener(new JButtonHandler());
        buttons.add(seeMore);
       
        String label = "Birth Date: August 8, 1981, Age: 41, Name: Roger Federer";
        String more = "One of the greatest tennis players of all time, was ranked number one" 
			+ " for 310 weeks.";
        JLabel pictLabel = new JLabel(label);
        JLabel profession = new JLabel(more);
        
        Font font = new Font("Serif", Font.PLAIN, 20);
        profession.setFont(font);
        pictLabel.setFont(font);
        buttons.add(pictLabel);
		buttons.add(profession);
       
		JButton next = new JButton("<html> <center> See info for<br>" +
            "the other person </center> </html>");
        next.addActionListener(new JButtonHandler());
        
       
        add(buttons, BorderLayout.SOUTH);      
       
        cards = hph.getCardLayout();      
        pth = panelCardsIn;
    }
   
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(img, 20, 20, 400, 500, 500, 0, 1000, 500, this);
    }
   
    class JButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
			String cmd = evt.getActionCommand();
			if (cmd.equals("<html> <center> See info for<br>" +
            "the other person </center> </html>"))
				cards.show(pth, "MyPictPanel");
			if (cmd.equals("Home"))
				cards.show(pth, "Home");
		}
    }
}


class DrawPanel extends JPanel implements ActionListener
{
      public void actionPerformed(ActionEvent evt) {}
}

class Information
{
    private String name;
   
    public Information()
    {
       
    }
   
    public String getName()
    {
        return name;
    }
   
    public void setName(String nameIn)
    {
        name = nameIn;
    }
    
    public String getInfo()
    {
		String name = "Welcome to PutItTogether.java. \nEnter your name in the text \nfield" 
			+ "and click I understand \nto proceed to the next panel.\n In the next panel"
			+ "we \nhave two buttons to go \nbetween two people and to \nthe settings panel"
			+ "In the \ntwo people panel, you \ncan click on them \nto get information about them.";
		return name;
	}
}
