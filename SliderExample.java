import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class SliderExample {
    public static void main(String[] args) {
        
        //title of window
        JFrame frame = new JFrame("WinHacks Simulation Control");
        //program stops running when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //automatically arranges things
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 200);

        // Slider settings
        JSlider Slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        //little ticks in the line
        Slider.setMajorTickSpacing(10);
        Slider.setPaintTicks(true);
        Slider.setPaintLabels(true);
        //allows the slider to only stop at the ticks
        Slider.setSnapToTicks(true);

        //what happens when the slider is moved
        Slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                
                //only run this if the user has finished dragging
                if(!Slider.getValueIsAdjusting()){
                    //stores what the slider value is in a variable
                    int value = Slider.getValue();

                    System.out.println("Slider Value: " + value);
                }
      
                

                
            }
        });

        // Add to frame and show
        frame.add(new JLabel("Starting Energy:"));
        frame.add(Slider);
        frame.setVisible(true);
    }
}