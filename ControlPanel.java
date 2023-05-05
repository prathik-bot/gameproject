import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel
{
  public static void main(String[] args)
  {
    ControlPanel ce = new ControlPanel();
    ce.run();
  }

  public void run()
  {
    JFrame frame = new JFrame ("Control Panel for Picture");
    frame.setSize(800, 600);
    frame.setLocation(10, 0);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    CpPanelHolder cph = new CpPanelHolder();
    frame.getContentPane().add(cph);
    frame.setVisible(true);
  }
}

class CpPanelHolder extends JPanel {

  private int selected;  // the index for the picture selected to draw
  private JTextArea tAComponentInfo;  // text area in the PictPanel, but changed in RightControlPanel2
  private JLabel welcome;  // label in the PictPanel, but changed in RightControlPanel2
  private Font font;  // most fonts are the same, so there is one
  private PictPanel pp; // the variables in the RightControlPanel2 need access to use repaint
  private int val = 0; // value of the slider to change the picture size
  private int width;
  private int height;
  private int[] widthOfImages;  // stores the width of each image
  private int[] heightOfImages;  // stores the height of each image

  public CpPanelHolder()
  {
    setLayout(new BorderLayout());

    pp = new PictPanel();
    add(pp, BorderLayout.CENTER);

    RightControlPanel rcp = new RightControlPanel();
    rcp.setPreferredSize(new Dimension(300, 300));
    add(rcp, BorderLayout.EAST);

    rcp.setBackground(Color.CYAN);
    rcp.setForeground(Color.CYAN);
  }


  /* PictPanel, which has a border layout,  has a label and a text area, both declared above.
   *  most of the code for loading the images is given.  add the rest for the images
   *  plus add the code for the text area, label and font (not necessarily in that order).
   *  the fonts, unless otherwise stated are size 20, bold and Serif.
   */
  class PictPanel extends JPanel {

    private String[] names;  // the names of the pictures
    private Image[] images;  // array of images to be drawn

    public PictPanel()
    {
      setLayout(new BorderLayout());
      setBackground(Color.LIGHT_GRAY);
      welcome = new JLabel("Welcome");
      welcome.setFont(new Font("Serif", Font.BOLD, 20));
      welcome.setHorizontalAlignment(SwingConstants.CENTER);
      add(welcome, BorderLayout.NORTH);
      tAComponentInfo = new JTextArea("Write the component changed will show here", 10, 34);
      add(tAComponentInfo, BorderLayout.AFTER_LAST_LINE);
      names = new String[]{"mountains.jpg", "shanghai.jpg", "trees.jpg", "water.jpg"};
      images = new Image[names.length];
      widthOfImages = new int[names.length];
      heightOfImages = new int[names.length]; // create the array for the heights

      // load all the images
      for (int i = 0; i < names.length; i++)
      {
        images[i] = getMyImage("Pictures/" + names[i]);
        widthOfImages[i] = images[i].getWidth(this);
        // find the heights of each picture
        heightOfImages[i] = images[i].getHeight(this);
      };
    }

    // this has been started for you
    public Image getMyImage(String pictName)
    {
      Image picture = null;
      File imageFile = new File(pictName);
      try
      {
        picture = ImageIO.read(imageFile);
      }
      catch (IOException e)
      {
        System.out.print("\n\n\n" + pictName + " can't be found.\n\n\n");
        e.printStackTrace();
      }

      return picture;
    }


    // draw the image on a blank screen with the top left corner at (20,20)
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      g.setFont(font);
      if (val == 0) {
        width = 100;
        height = 40;
      }
      g.drawImage(images[selected], 10, 30, width, height,
          this);
    }
  }

/* Make all panels on the right be cyan. RightControlPanel has a border layout.
On this panel are: label, which font size already done, the text field, the menu,
the radio buttons and the slider. You will have to determine the layouts in order to make
* them show up like the sample run provided.
*/

  class RightControlPanel extends JPanel {
    private JTextField tfName; // text field for user to type in their name
    private ButtonGroup bg; // to select the color so only one is selected
    private JRadioButton color1, color2, color3; // color choices
    private JSlider sSize; // slider for changing the size of the picture

    public RightControlPanel()
    {

      BorderLayout b = new BorderLayout();
      setLayout(b);
      setBackground(Color.CYAN);
      setForeground(Color.CYAN);
      //GridLayout f = new GridLayout(15, 15);
      JPanel northPanel = new JPanel();
      setBackground(Color.CYAN);

      JPanel cptPanel = new JPanel(); //Panel for Control Label and Text.
      cptPanel.setLayout(new GridLayout(2, 2));
      JLabel control = new JLabel("Control Panel");
      control.setFont(new Font("Times", Font.BOLD, 20));
      control.setHorizontalAlignment(SwingConstants.CENTER);
      tfName = new JTextField("Enter Your Name");
      tfName.setFont(new Font("Times", Font.PLAIN, 12));
      tfName.setHorizontalAlignment(SwingConstants.CENTER);
      tfName.setSize(1,1);
      TextChangeListener tchg = new TextChangeListener();
      tfName.addActionListener(tchg);
      cptPanel.add(control);
      cptPanel.add(tfName);

      cptPanel.setBackground(Color.CYAN);
      add(cptPanel, BorderLayout.NORTH);

      JPanel menuPanel = new JPanel();
      menuPanel.add(makePictureMenuBar());
      menuPanel.setBackground(Color.CYAN);
      northPanel.add(menuPanel, BorderLayout.CENTER);

      JPanel rbPanel = new JPanel();
      rbPanel.setLayout(new GridLayout(3, 2));
      bg = new ButtonGroup();
      JLabel question1 = new JLabel("Select color of label ");
      question1.setFont(new Font("Serif", Font.BOLD, 15));

      rbPanel.add(question1);
      ColorChangeListener chd = new ColorChangeListener();
      rbPanel.setLayout(new GridLayout(4, 4));
      color1 = new JRadioButton("Red");   // construct button
      bg.add(color1);                 // add button to panel
      color1.setActionCommand("Red");
      color1.addActionListener(chd);  // add listener to JRadioButton
      rbPanel.add(color1);           // add JRadioButton to Panel


      color2 = new JRadioButton("Blue");  // construct button
      bg.add(color2);// add button to panel
      color2.setActionCommand("Blue");
      color2.addActionListener(chd);      // add listener to button
      rbPanel.add(color2);

      color3 = new JRadioButton("Magenta");   // construct button
      bg.add(color3);       // add button to panel
      color3.setActionCommand("Magenta");
      color3.addActionListener(chd);  // add listener to button
      rbPanel.add(color3);
      rbPanel.setBackground(Color.cyan);
      //add(rbPanel, BorderLayout.EAST);
      add(rbPanel, BorderLayout.EAST);
      //rbPanel.setSize(new Dimension(400, 400));


      // JSlider
      JPanel sliderPanel = new JPanel();
      sliderPanel.setLayout(new GridLayout(4,4));
      sSize = new JSlider(0, 200, 0);
      //sSize.setLayout(new GridLayout(1, 1));
      sSize.setMajorTickSpacing(20);  // create tick marks on slider every 5 units
      sSize.setPaintTicks(true);
      sSize.setLabelTable(sSize.createStandardLabels(20)); // create labels on tick marks
      sSize.setPaintLabels(true);
      sSize.setOrientation(JSlider.HORIZONTAL);
      SliderListener sListener = new SliderListener();
      sSize.addChangeListener(sListener);
      sliderPanel.add(sSize);
      sliderPanel.setBackground(Color.cyan);
      add(sliderPanel, BorderLayout.SOUTH);

      northPanel.setBackground(Color.CYAN);
      add(northPanel, BorderLayout.CENTER);

    }

    public JMenuBar makePictureMenuBar()
    {

      //menuPanel.setPreferredSize(new Dimension(1,1));
      JMenuBar bar = new JMenuBar();
      bar.setMaximumSize(new Dimension(5, 5));
      bar.setBounds(0, 0, 60, 20);
      JMenu picture = new JMenu("Picture");
      //picture.setBounds(10, 10, 1024, 25);
      picture.setSize(10,10);
      JMenuItem pict1 = new JMenuItem("mountains.jpg");
      JMenuItem pict2 = new JMenuItem("shanghai.jpg");
      JMenuItem pict3 = new JMenuItem("trees.jpg");
      JMenuItem pict4 = new JMenuItem("water.jpg");

      PictureMenuHandler pmh = new PictureMenuHandler();
      pict1.addActionListener(pmh);
      pict2.addActionListener(pmh);
      pict3.addActionListener(pmh);
      pict4.addActionListener(pmh);

      picture.add(pict1);
      picture.add(pict2);
      picture.add(pict3);
      picture.add(pict4);

      bar.add(picture);
      bar.setAlignmentX(10);
      bar.setAlignmentY(10);
      bar.setSize(10,10);

      return bar;
    }

    public class PictureMenuHandler implements ActionListener
    {
      public void actionPerformed(ActionEvent evt)
      {
        String name = "";
        String command = evt.getActionCommand();
        if (command.equals("water.jpg"))
        {
          name = "water";
          selected = 3;
        }
        else if (command.equals("mountains.jpg"))
        {
          name = "mountains";
          selected = 0;
        }
        else if (command.equals("trees.jpg"))
        {
          name = "trees";
          selected = 2;
        }
        else if (command.equals("shanghai.jpg"))
        {
          name = "shanghai";
          selected = 1;
        }
        //tAComponentInfo.setText(""); //Clears older text.
        tAComponentInfo.append("\nThe picture " + name + " was selected.");

        pp.getMyImage(name+ ".jpg");
        pp.repaint();
      }
    }

    // write the Listener/Handler class for the slider
    class SliderListener implements ChangeListener
    {
      public void stateChanged(ChangeEvent evt)
      {
        String phrase = "";
        val = sSize.getValue(); // get the value of the slider
        //sSize.add(val);
        // Use width and height
        int maxWidth = 0;
        int maxHeight = 0;
        maxWidth = pp.getWidth() - 20;
        maxHeight = pp.getHeight() - 250;
        width = pp.getWidth() / 4;
        height = pp.getHeight() / 4;

        tAComponentInfo.setText("The size of the picture was changed by " + val);
        Graphics g = pp.getGraphics();
        Image[] images = pp.images;

        width = width + val*2;
        height = height + val*2;
        if (height > maxHeight)
          height = maxHeight;
        if (width > maxWidth)
          width = maxWidth;

        if (val == 0) {
          width = pp.getWidth();
          height = pp.getHeight();
        }

        g.drawImage(images[selected], 10, 20, width/4, height/4, null);
        pp.repaint();
      }
    }

    // write Listener/Handler class for the JRadioButtons
    private class ColorChangeListener implements ActionListener {

      public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();

        String phrase = "";

        if (command.equals("Red"))
          welcome.setForeground(Color.RED);
        else if (command.equals("Blue"))
          welcome.setForeground(Color.BLUE);
        else if (command.equals("Magenta"))
          welcome.setForeground(Color.MAGENTA);

        repaint();

      }
    }

    class TextChangeListener extends JPanel implements ActionListener
    {
      public void actionPerformed(ActionEvent evt)
      {
        String command = evt.getActionCommand();
        welcome.setFont(new Font("Serif", Font.BOLD, 20));
        welcome.setText("Welcome " + command);
        welcome.setFont(new Font("Serif", Font.BOLD, 20));
        repaint();
      }
    }
  }
}

