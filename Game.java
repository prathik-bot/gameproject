// Prathik Kumar and Hrithik Mallireddy
// 4/24/2023
// Game.java (Explorador Español)
// Working on:
	// Week 1: Basic Start, Instructions, and Game panels with navigation CardLayout and null layout complete
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

// Game holds the main panel and calls the panel class, CardHolder.
/// Prathik Kumar wrote this class.
public class Game
{
	public static void main(String[] args) 
	{
		Game ss = new Game();
		ss.runIt();
	}
	
	public void runIt()
	{
		JFrame frame = new JFrame("Explorador Español");
		frame.setSize(750, 700);				
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); 
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		CardHolder ch = new CardHolder(); 		
		frame.getContentPane().add(ch);		
		frame.setVisible(true);	
	}
}

// CardHolder calls all the other constructors and passes the CardLayout
// and an instance of this class so that we can manuever to different panels
// from every class in this program.
/// Prathik Kumar wrote this class.
class CardHolder extends JPanel
{
	// Calls all the other classes/panels, and passes the card layout object
	// and an instance of the class so every class can move between the panels
	public CardHolder()
	{
		CardLayout cards = new CardLayout();
		setLayout(cards);
		
		HomeButton hButton = new HomeButton(cards, this);
		HomeBar hBar = new HomeBar(cards, this);
		Starting st = new Starting(cards, this);
		Setting set = new Setting(cards, this);
		Lessons less = new Lessons(cards, this, hButton, hBar);
		Instructions inst = new Instructions(cards, this);
		Leader lead = new Leader(cards, this);
		GamePanel game = new GamePanel(cards, this);
		Vocab vb = new Vocab(cards, this);
		Preterite pr = new Preterite(cards, this);
		Present pre = new Present(cards, this);
		Imperfect imp = new Imperfect(cards, this);
		
		add(st, "Home");
		add(set, "Settings");
		add(less, "Lessons");
		add(inst, "Instructions");
		add(lead, "Leaderboard");
		add(game, "Gameplay");
		add(vb, "Vocab");
		add(pr, "Preterite");
		add(pre, "Present");
		add(imp, "Imperfect");
	}
}

// Starting contains the home panel of our game. This panel contains all the
// other buttons that lead to the other panels in the program (settings, lessons
// instructions, leaderboard, play, quit). 
/// Prathik Kumar wrote most of the constructor but Hrithik Mallireddy wrote the
/// ImageIO method and paintComponent().
class Starting extends JPanel
{
	private CardLayout cards; // holds card layout
	private CardHolder panelHolder; // used to swap between panels in card layout
	private Image background; // background image
	
	// Creates a class with 6 buttons, where 5 of them go to different 
	// panels and one of them closes the program. Also creates labels
	// which will serve as the title of the game
	public Starting (CardLayout cardsIn, CardHolder panelHolderIn) 
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		setLayout(null);
		
		// Creating new font
		Font font = new Font("Georgia", Font.BOLD, 20);
		
		// Getting the image
		background = getImage("background.jpg");
		
		// Creating buttons
		JButton setting = new JButton("Settings");
		JButton lessons = new JButton("Lessons");
		JButton instruc = new JButton("Instructions");
		JButton lBoard = new JButton("Leaderboard");
		JButton play = new JButton("Play");
		JButton quit = new JButton("Quit");
		
		// Setting font
		setting.setFont(font);
		lessons.setFont(font);
		instruc.setFont(font);
		lBoard.setFont(font);
		play.setFont(font);
		quit.setFont(font);
		
		// Setting bounds and adding buttons
		setting.setBounds(15, 400, 235, 120);
		instruc.setBounds(255, 400, 235, 120);
		lessons.setBounds(15, 520, 235, 120);
		play.setBounds(255, 520, 235, 120);
		lBoard.setBounds(495, 400, 235, 120);
		quit.setBounds(495, 520, 235, 120);
		
		// Adding action listeners for each button and adding buttons
		ButtonHandler bh = new ButtonHandler();
		setting.addActionListener(bh);
		instruc.addActionListener(bh);
		lessons.addActionListener(bh);
		play.addActionListener(bh);
		lBoard.addActionListener(bh);
		quit.addActionListener(bh);
		add(setting);
		add(instruc);
		add(lessons);
		add(play);
		add(lBoard);
		add(quit);
		
		// font used for title labels
		Font titleFont = new Font("Georgia", Font.BOLD, 40);
		
		// Label for the explorador part of the title
		JLabel title = new JLabel("Explorador");
		title.setFont(titleFont);
		title.setForeground(Color.YELLOW);
		title.setBounds(252, 150, 246, 100);
		add(title);
		
		// Label for the espanol part
		JLabel secondTitle = new JLabel("Español");
		secondTitle.setFont(titleFont);
		secondTitle.setForeground(Color.YELLOW);
		secondTitle.setBounds(287, 222, 176, 60);
		add(secondTitle);

	}
	
	// getImage() uses ImageIO to load the image into a variable.
	/// Hrithik Mallireddy wrote this method.
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
	
	// This method is drawing the image
	/// Hrithik Mallireddy wrote this method.
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 750, 700, this);
	}
	
	// Button Handler is the handler class for the six buttons on the
	// home screen.
	/// Prathik Kumar wrote this class.
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Settings"))
				cards.show(panelHolder, "Settings");
			if (command.equals("Lessons"))
				cards.show(panelHolder, "Lessons");
			if (command.equals("Instructions"))
				cards.show(panelHolder, "Instructions");
			if (command.equals("Play"))
				cards.show(panelHolder, "Gameplay");
			if (command.equals("Leaderboard"))
				cards.show(panelHolder, "Leaderboard");
			if (command.equals("Quit"))
				System.exit(0);
		}
	}
}

// HomeBar 
// Creates menu bar containing the menu items home and settings
// In a sep class because it is more convenient to just call this class, and have the
// menu bar in all the classes
/// Prathik Kumar wrote this class.
class HomeBar extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	
	// Creates a menu bar with items home and settings
	public HomeBar(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		
		JMenuBar bar = new JMenuBar();
		Font font = new Font("Georgia", Font.BOLD, 22);
		JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		setLayout(new BorderLayout());
		JMenu name = new JMenu("Menu");
		JMenuItem home = new JMenuItem("Home");
		JMenuItem settings = new JMenuItem("Settings");
		name.add(home);
		home.setFont(font);
		name.setFont(font);
		settings.setFont(font);
		name.add(settings);
		bar.add(name);
		MenuHandler mh = new MenuHandler();
		home.addActionListener(mh);
		settings.addActionListener(mh);
		menuPanel.add(bar);
		menuPanel.setBackground(Color.YELLOW);
		add(menuPanel, BorderLayout.CENTER);
	}
	
	// Used to check which menu item the user clicked
	/// Prathik Kumar wrote this method.
	class MenuHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt) 
		{
			String command = evt.getActionCommand();
			if (command.equals("Home"))
				cards.show(panelHolder, "Home");
			if (command.equals("Settings"))
				cards.show(panelHolder, "Settings");
		}
	}
}
// Creates a home button, used for convience so we can call this class
// instead of having to make a new home button in every panel
/// Prathik Kumar wrote this class
class HomeButton extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	
	// Creates a home button
	public HomeButton(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		JButton home = new JButton("Home");
		ButtonHandler bh = new ButtonHandler();
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		setLayout(new BorderLayout());
		home.addActionListener(bh);
		buttonPanel.add(home);
		buttonPanel.setBackground(Color.YELLOW);
		add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	// Used to go to home panel if user clicked home button
	/// Prathik Kumar wrote this method
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			cards.show(panelHolder, "Home");
		}
	}
}

// Class settings contains a slider determining font size, and radio buttons
// determining which font the user can use
/// Prathik Kumar wrote this class.
class Setting extends JPanel
{	
	// Will have slider for font and let user pick between different font size, but for now
	// it just has the heading and the menu bar
	public Setting(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		CardLayout cards = cardsIn;
		CardHolder panelHolder = panelHolderIn;
		HomeBar hb = new HomeBar(cards, panelHolder);
		setLayout(new BorderLayout());
		add(hb, BorderLayout.WEST);
		
		JLabel settingLabel = new JLabel("Settings");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		settingLabel.setFont(font);
		labelPanel.add(settingLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
	}
}

// Lessons class holding the perterite, present tense, imperfect, and vocab buttons
// these buttons lead to different panels that contain text areas teaching different
// concepts
/// Prathik Kumar wrote this class.
class Lessons extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	private HomeButton hButton; //used to store the home button obj, so homebutton can be added to this panel
	
	// This page holds four different buttons for now that lead to different subtopics of the 
	// concept we are trying to teach. Each button leads to a different panel which
	// will have info on the concept
	public Lessons (CardLayout cardsIn, CardHolder panelHolderIn, HomeButton hButtonIn, HomeBar hBarIn)
	{
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
		cards = cardsIn;
		panelHolder = panelHolderIn;
		hButton = hButtonIn;
		
		setBackground(Color.YELLOW);
		
		add(hButton, BorderLayout.SOUTH);
		
		Font font = new Font("Georgia", Font.BOLD, 25);
		JLabel lessonHead = new JLabel("Lessons");
		JPanel centered = new JPanel(new FlowLayout(FlowLayout.CENTER));
		lessonHead.setFont(font);
		centered.add(lessonHead);
		centered.setBackground(Color.YELLOW);
		add(centered, BorderLayout.NORTH);

		JPanel lessonButtons = new JPanel(new GridLayout(2, 2, 30, 30));
		JButton vocab = new JButton("Vocab");
		JButton preterite = new JButton("Preterite");
		JButton imperfect = new JButton("Imperfect");
		JButton present = new JButton("Present");
		vocab.setFont(font);
		preterite.setFont(font);
		imperfect.setFont(font);
		present.setFont(font);
		ButtonHandler bh = new ButtonHandler();
		vocab.addActionListener(bh);
		preterite.addActionListener(bh);
		imperfect.addActionListener(bh);
		present.addActionListener(bh);
		
		lessonButtons.add(vocab);
		lessonButtons.add(preterite);
		lessonButtons.add(imperfect);
		lessonButtons.add(present);
		lessonButtons.setBackground(Color.YELLOW);
		add(lessonButtons, BorderLayout.CENTER);
	}
	
	// Handles the buttons, checks which button was clicked
	/// Prathik Kumar wrote this method
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Vocab"))
				cards.show(panelHolder, "Vocab");
			if (command.equals("Preterite"))
				cards.show(panelHolder, "Preterite");
			if (command.equals("Imperfect"))
				cards.show(panelHolder, "Imperfect");
			if (command.equals("Present"))
				cards.show(panelHolder, "Present");
		}
	}
}
// This is a lesson panel that will hold a text area with the lesson info
/// Prathik Kumar wrote this class
class Vocab extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	
	// Generic lesson page with a text area in the middle that has to be filled in later
	// We have a back to lessons page button as well
	public Vocab(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		
		setBackground(Color.YELLOW);
		JLabel vocLabel = new JLabel("Vocab");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		vocLabel.setFont(font);
		labelPanel.add(vocLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
		
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextArea ta = new JTextArea("This is a text area that will hold instructions later", 30, 50);
		JScrollPane sp = new JScrollPane();
		ta.add(sp);
		textPanel.setBackground(Color.YELLOW);
		textPanel.add(ta);
		add(textPanel, BorderLayout.CENTER);
		
		JButton lesson = new JButton("Back to Lessons");
		ButtonHandler bh = new ButtonHandler();
		lesson.addActionListener(bh);
		
		add(lesson, BorderLayout.SOUTH);
	}
	
	// Checks if the lesson page button was clicked
	/// Prathik Kumar wrote this method
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equalsIgnoreCase("Back to Lessons"))
			{
				cards.show(panelHolder, "Lessons");
			}
		}
	}
}

// This is a lesson panel that will hold a text area with the lesson info
// Lesson is about the Preterite tense in spanish, we will show patterns needed for it
/// Prathik Kumar wrote this class.
class Preterite extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	
	// Generic lesson page with a text area in the middle that has to be filled in later
	// We have a back to lessons page button as well
	public Preterite(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		
		setBackground(Color.YELLOW);
		JLabel pretLabel = new JLabel("Preterite");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		pretLabel.setFont(font);
		labelPanel.add(pretLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
		
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextArea ta = new JTextArea("This is a text area that will hold instructions later", 30, 50);
		JScrollPane sp = new JScrollPane();
		ta.add(sp);
		textPanel.setBackground(Color.YELLOW);
		textPanel.add(ta);
		add(textPanel, BorderLayout.CENTER);
		
		JButton lesson = new JButton("Back to Lessons");
		ButtonHandler bh = new ButtonHandler();
		lesson.addActionListener(bh);
		
		add(lesson, BorderLayout.SOUTH);
	}
	
	// Checks if the lesson page button was clicked
	/// Prathik Kumar wrote this method
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equalsIgnoreCase("Back to Lessons"))
			{
				cards.show(panelHolder, "Lessons");
			}
		}
	}
}

// This is a lesson panel that will hold a text area with the lesson info
// Lesson is about Imperfect tense in spanish, we will show patterns needed for it
/// Prathik Kumar wrote this class.
class Imperfect extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	
	// Generic lesson page with a text area in the middle that has to be filled in later
	// We have a back to lessons page button as well
	public Imperfect(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		
		setBackground(Color.YELLOW);
		JLabel impLabel = new JLabel("Imperfect");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		impLabel.setFont(font);
		labelPanel.add(impLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
		
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextArea ta = new JTextArea("This is a text area that will hold instructions later", 30, 50);
		JScrollPane sp = new JScrollPane();
		ta.add(sp);
		textPanel.setBackground(Color.YELLOW);
		textPanel.add(ta);
		add(textPanel, BorderLayout.CENTER);
		
		JButton lesson = new JButton("Back to Lessons");
		ButtonHandler bh = new ButtonHandler();
		lesson.addActionListener(bh);
		
		add(lesson, BorderLayout.SOUTH);
	}
	
	// Checks if the lesson page button was clicked
	/// Prathik Kumar wrote this method
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equalsIgnoreCase("Back to Lessons"))
			{
				cards.show(panelHolder, "Lessons");
			}
		}
	}
}

// This is a lesson panel that will hold a text area with the lesson info
// Lesson is about Present tense in spanish, we will show patterns needed for it
/// Prathik Kumar wrote this class.
class Present extends JPanel
{
	private CardLayout cards; //holds card layout
	private CardHolder panelHolder; //used to swap between panels in card layout
	
	// Generic lesson page with a text area in the middle that has to be filled in later
	// We have a back to lessons page button as well
	public Present(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		cards = cardsIn;
		panelHolder = panelHolderIn;
		
		setBackground(Color.YELLOW);
		JLabel presLabel = new JLabel("Present");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		presLabel.setFont(font);
		labelPanel.add(presLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
		
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextArea ta = new JTextArea("This is a text area that will hold instructions later", 30, 50);
		JScrollPane sp = new JScrollPane();
		ta.add(sp);
		textPanel.setBackground(Color.YELLOW);
		textPanel.add(ta);
		add(textPanel, BorderLayout.CENTER);
		
		JButton lesson = new JButton("Back to Lessons");
		ButtonHandler bh = new ButtonHandler();
		lesson.addActionListener(bh);
		
		add(lesson, BorderLayout.SOUTH);
	}
	
	// Checks if the lesson page button was clicked
	/// Prathik Kumar wrote this method
	class ButtonHandler extends JPanel implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equalsIgnoreCase("Back to Lessons"))
			{
				cards.show(panelHolder, "Lessons");
			}
		}
	}
}

// This class creates the Instructions panel.Instructions will use a getter and setter method (Information) 
// to recieve the instructions the user will read before playing the game. 
/// Prathik Kumar wrote this class.
class Instructions extends JPanel
{	
	// Creating Instructions JLabel as a heading, creating text area that will hold
	// instructions later, and adding menu bar
	public Instructions(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		CardLayout cards = cardsIn;
		CardHolder panelHolder = panelHolderIn;
		HomeBar hb = new HomeBar(cards, panelHolder);
		setLayout(new BorderLayout());
		add(hb, BorderLayout.WEST);
		
		JLabel instrucLabel = new JLabel("Instructions");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		instrucLabel.setFont(font);
		labelPanel.add(instrucLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
		
		JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JTextArea ta = new JTextArea("This is a text area that will hold instructions later", 30, 50);
		JScrollPane sp = new JScrollPane();
		ta.add(sp);
		textPanel.setBackground(Color.YELLOW);
		textPanel.add(ta);
		add(textPanel, BorderLayout.CENTER);
	}
}

// This is the leaderboard panel that will display the text file
// with all the leaderboard information. There is a JLabel with the title
// of the panel.
/// Prathik Kumar wrote this class.
class Leader extends JPanel
{	
	// Creating JLabel called Leaderboard as a heading for the Leaderboard panel
	// Also adding menu bar to panel
	public Leader(CardLayout cardsIn, CardHolder panelHolderIn)
	{
		CardLayout cards = cardsIn;
		CardHolder panelHolder = panelHolderIn;
		HomeBar hb = new HomeBar(cards, panelHolder);
		setLayout(new BorderLayout());
		add(hb, BorderLayout.WEST);
		
		JLabel leaderLabel = new JLabel("Leaderboard");
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Font font = new Font("Georgia", Font.BOLD, 45);
		leaderLabel.setFont(font);
		labelPanel.add(leaderLabel);
		labelPanel.setBackground(Color.YELLOW);
		add(labelPanel, BorderLayout.NORTH);
	}
}
