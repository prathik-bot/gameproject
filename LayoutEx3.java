import javax.swing.*;
import java.awt.*;

public class LayoutEx3 {

    public static void main(String[] args) {
        LayoutEx3 layoutEx3 = new LayoutEx3();
        layoutEx3.makeIt();
    }

    public void makeIt() {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        PanelA panelA = new PanelA();
        frame.add(panelA);
        frame.setVisible(true);
    }
}

class PanelA extends JPanel {

    public PanelA() {
        setLayout(new BorderLayout(5, 5));
        PanelB x = new PanelB();
        PanelC y = new PanelC();
        JPanel z = new JPanel();
        PanelC a = new PanelC();
        add(x, BorderLayout.SOUTH);
        add(y, BorderLayout.NORTH);
        //add(z, BorderLayout.WEST);
        add(a, BorderLayout.CENTER);
    }

    public void paintComponent(Graphics g) {
        setBackground(Color.BLACK);
        super.paintComponent(g);
    }
}

class PanelB extends JPanel {

    public PanelB() {
        setLayout(new GridLayout(1, 2, 5, 5));
        add(new JButton("items"));
        add(new JTextArea("information", 25, 5));
    }

    public void paintComponent(Graphics g) {
        setBackground(Color.BLUE);
        super.paintComponent(g);
    }
}

class PanelC extends JPanel {

    public PanelC() {
        add(new JCheckBox("select"));
        add(new JScrollBar(JScrollBar.VERTICAL, 50, 10, 0, 100));
        add(new JRadioButton("choose"));
    }

    public void paintComponent(Graphics g) {
        setBackground(Color.RED);
        super.paintComponent(g);
    }
}
