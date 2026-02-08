import java.awt.*;
import javax.swing.*;

public class CustomButton extends JButton {
    private String type; 

    public CustomButton(String type) {
        this.type = type.toLowerCase();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(60, 60));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Background Circle
        if (getModel().isArmed()) {
            g2.setColor(new Color(30, 130, 70));
        } else {
            g2.setColor(new Color(46, 204, 113));
        }
        g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);

        // 2. Draw Icons
        g2.setColor(Color.WHITE);
        int w = getWidth();
        int h = getHeight();
        int padding = w / 4;

        if (type.equals("play")) {
            int[] xPoints = {padding + 5, padding + 5, w - padding};
            int[] yPoints = {padding, h - padding, h / 2};
            g2.fillPolygon(xPoints, yPoints, 3);

        } else if (type.equals("pause")) {
            int barWidth = w / 10;
            g2.fillRect(padding + 5, padding, barWidth, h - (padding * 2));
            g2.fillRect(w - padding - barWidth - 5, padding, barWidth, h - (padding * 2));

        } else if (type.equals("reset")) {
            // Draw the curved arrow (the "circular" part)
            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            // drawArc(x, y, width, height, startAngle, arcAngle)
            g2.drawArc(padding, padding, w - (padding * 2), h - (padding * 2), 45, 270);

            // Draw the Arrow Head at the end of the arc
            int arrowSize = 10;
            int[] xArrow = {w / 2 + 6 , w / 2 + arrowSize + 6, w / 2 - arrowSize + 6};
            int[] yArrow = {padding - (arrowSize / 2), padding + arrowSize, padding + arrowSize};
            
            // Rotate the graphics slightly to align the arrow tip to the arc
            g2.rotate(Math.toRadians(40), w/2, h/2); 
            g2.fillPolygon(xArrow, yArrow, 3);
        }

        g2.dispose();
        super.paintComponent(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        CustomButton playBtn = new CustomButton("play");
        CustomButton pauseBtn = new CustomButton("pause");
        CustomButton resetBtn = new CustomButton("reset");

        // when play is pressed
        playBtn.addActionListener(e -> {
            System.out.println("Play button pressed");
        });

        //when paused is pressed
        pauseBtn.addActionListener(e -> {
            System.out.println("Pause button pressed ");
        });

        //when rest is presed
        resetBtn.addActionListener(e -> {
            System.out.println("Reset button pressed");
            //reset function goes here
        });

        // 3. Add to frame
        frame.add(playBtn);
        frame.add(pauseBtn);
        frame.add(resetBtn);

        frame.setSize(400, 200);
        frame.setVisible(true);
          
    }
}


