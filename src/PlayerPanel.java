import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PlayerPanel extends JPanel {

   int radius = 15;
   String stringChange = null;
   int intChange = 0;
   int playerIncrement = 2;

   public PlayerPanel() {
      setFocusable(true);
      Timer timer = new Timer(1, new TimerListener());

      addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
               stringChange = "up";
               timer.start();
            }

            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
               stringChange = "down";
               timer.start();
            }

            repaint();
         }

         public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP || 
                e.getKeyCode() == KeyEvent.VK_DOWN)
            timer.stop();
         }
      });
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int width = getWidth();
      int height = getHeight();
      g.setColor(Color.BLACK);
      
      int yPos = (int)((0.5 * height) - radius + intChange);

      g.fillOval((int)(0.1 * height), 
                 (yPos), 
                 (int)(2 * radius), 
                 (int)(2 * radius)); 
      System.out.println(intChange);
   }

   private class TimerListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {

//         if (stringChange == "up") { 
//            if (intChange == -0.5 * getHeight() + radius)
//                intChange = intChange;
//            else intChange--;
//         }
//         else if (stringChange == "down") {
//            if (intChange == 0.5 * getHeight() - radius)
//               intChange = intChange;
//            else intChange++;
//         }
    	  
    	  if(stringChange == "up"){
    		  if(yPos == radius){
    			  intChange = 0;
    		  }
    		  else intChange -= playerIncrement;
    	  }
    	  else if(stringChange == "down"){
    		  if(yPos == (getHeight() - radius)){
    			  intChange = 0;
    		  }
    		  intChange += playerIncrement;
    	  }
    	  
         repaint();
      }
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(1000, 500);
   }  
}