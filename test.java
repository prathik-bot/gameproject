import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test extends JFrame implements KeyListener {
    private JLabel characterLabel;
    private ImageIcon standingImage;
    private ImageIcon steppingImage;
    private boolean stepping = false;
    private int stepCount = 0;

    public test() {
        setTitle("Pokemon Character");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(64, 64);

        // Load the two images
        standingImage = new ImageIcon("Player_Left1.png");
        steppingImage = new ImageIcon("Player_Left2.png");

        // Create a label to display the character
        characterLabel = new JLabel(standingImage);
        characterLabel.setHorizontalAlignment(JLabel.CENTER);
        characterLabel.setVerticalAlignment(JLabel.CENTER);
        add(characterLabel);

        // Add key listener to the frame
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

   @Override
	public void keyPressed(KeyEvent e) {
		if (stepping) {
			return;
		}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				moveCharacter(0, -1);
				break;
			case KeyEvent.VK_DOWN:
				moveCharacter(0, 1);
				break;
			case KeyEvent.VK_LEFT:
				moveCharacter(-1, 0);
				break;
			case KeyEvent.VK_RIGHT:
				moveCharacter(1, 0);
				break;
		}
	}



    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private void moveCharacter(int dx, int dy) {
        // Start the stepping animation
        if (stepping)
			return;
        stepping = true;
        stepCount = 0;
        Timer timer = new Timer(120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch between the two images
                if (stepCount % 2 == 0) {
                    characterLabel.setIcon(steppingImage);
                } else {
                    characterLabel.setIcon(standingImage);
                }
                stepCount++;

                // Move the character a fraction of the way to the next tile
                int newX = characterLabel.getX() + dx * 8;
                int newY = characterLabel.getY() + dy * 8;
                characterLabel.setLocation(newX, newY);

                // Stop the stepping animation after moving the full distance
                if (stepCount == 4) {
					stepping = false;
					((Timer) e.getSource()).stop();
					characterLabel.setIcon(standingImage); // Reset to standing image
				}

            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        test character = new test();
        character.setVisible(true);
    }
}
