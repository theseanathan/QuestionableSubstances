
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.lang.Math.*;

public class QS extends JFrame {

/*---------------------------Variables/Objects Begin-----------------------------------*/

   public static Random random = new Random();

   // Create all-encompassing frame
   public static QS frame = new QS(); 

   // Create game panel
   public static GamePanel gamePanel = new GamePanel();
 
   // Set screen dimensions
   public static final double SCREENWIDTH = 800;
   public static final double SCREENHEIGHT = 500;

   // Set player dimensions
   public static final double PLAYERWIDTH = 20;
   public static final double PLAYERHEIGHT = 20;

   // Set drug dimensions
   public static final double DRUGWIDTH = 20;
   public static final double DRUGHEIGHT = 20;

   // Set space between barriers
   public static double SPACE = 100;

   // Set barrier dimensions
   public static final double BARRIERWIDTH = 20;
   public static final double BARRIERHEIGHT = SCREENHEIGHT - SPACE;

   // Variable for barrier's x position
   public static double barrierXPosition;

   // Define constants for moving objects
   public static final double SLOW = 4;
   public static final double NORMAL = 9;
   public static final double FAST = 14;

   // Variable for speed of moving objects
   public static double oncomingSpeed = NORMAL;

   // Set player's x position
   public static final double PLAYERXPOSITION = 60;

   // Set player's (starting) y position
   public static double playerYPosition = (SCREENHEIGHT / 2) - (PLAYERHEIGHT / 2);

   // Variable for reversing controls
   public static boolean sober = true;

   // Make barrier
   public static Rectangle2D.Double barrierAbove;
   public static Rectangle2D.Double barrierBelow;

   // Initialize barrier
   public static void initializeBarrier() {
      barrierAbove = new Rectangle2D.Double(SCREENWIDTH, random.nextDouble() * BARRIERHEIGHT - BARRIERHEIGHT, BARRIERWIDTH, BARRIERHEIGHT);
      barrierBelow = new Rectangle2D.Double(SCREENWIDTH, barrierAbove.getMaxY() + SPACE, BARRIERWIDTH, BARRIERHEIGHT + 60);
   }

   // Make player
   public static Rectangle2D.Double player = new Rectangle2D.Double(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT);

   // Make marijuana
   public static Rectangle2D.Double marijuana, marijuana2;

   // Make cocaine
   public static Rectangle2D.Double cocaine, cocaine2;

   // Make alcohol
   public static Rectangle2D.Double alcohol, alcohol2;

   // Make drug timers
   public static Timer marijuanaTimer = new Timer(5000, new MarijuanaTimerListener());
   public static Timer cocaineTimer = new Timer(5000, new CocaineTimerListener());
   public static Timer alcoholTimer = new Timer(5000, new AlcoholTimerListener());

   /* Initialize drug timers -- note that delay is set to 5 seconds
   and each timer fires only once. When the contact method senses 
   contact first the effect gets implemented (slow/fast/reversed 
   controls), then the timer is started. 5 seconds later the timer 
   fires its lone action event, and the listener processes that 
   event by putting everything back to normal. */

   public static void initializeDrugTimers() {
      marijuanaTimer.setInitialDelay(5000);   
      marijuanaTimer.setRepeats(false);
      cocaineTimer.setInitialDelay(5000);   
      cocaineTimer.setRepeats(false);
      alcoholTimer.setInitialDelay(5000);   
      alcoholTimer.setRepeats(false);
      
   }

   // Initialize drugs
   public static void initializeDrugs() {
      marijuana = new Rectangle2D.Double(SCREENWIDTH + random.nextDouble() * SCREENWIDTH, random.nextDouble() * SCREENHEIGHT, DRUGWIDTH, DRUGHEIGHT);
      cocaine = new Rectangle2D.Double(SCREENWIDTH + random.nextDouble() * SCREENWIDTH, random.nextDouble() * SCREENHEIGHT, DRUGWIDTH, DRUGHEIGHT);
      alcohol = new Rectangle2D.Double(SCREENWIDTH + random.nextDouble() * SCREENWIDTH, random.nextDouble() * SCREENHEIGHT, DRUGWIDTH, DRUGHEIGHT);
   }

/*---------------------------Variables/Objects End-----------------------------------*/

/*---------------------------Player Motion Begins-----------------------------------*/

   // Define constants for player motion
   public static double UP = -10;
   public static double DOWN = 10;
   public static final double STATIONARY = 0;
   public static final double MOVEFAST = 18;
   public static final double MOVESLOW = 4;   
   public static final double MOVENORMAL = 10;

   // Variable for player speed
   public static double playerSpeed = STATIONARY;

   // Key listener for player motion
   public static class PlayerKeyListener implements KeyListener {
      @Override public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP)
         playerSpeed = UP;
      else if (e.getKeyCode() == KeyEvent.VK_DOWN)
         playerSpeed = DOWN;
      }
         
      @Override public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
         playerSpeed = STATIONARY;
      }

      @Override public void keyTyped(KeyEvent e) {
      }
   }  

   // Task class for player motion
   public static class PlayerMotionTask implements Runnable {
      @Override
      public void run() {
         try {
            while (true) {
               movePlayer();
               Thread.sleep(15);
            }
         }
         catch (InterruptedException ex) {
         }
      }
   }

   /* Method for player motion: if-statement checks whether 
   player is drunk. First part of if-statement is for sober 
   case, second part (following else) is for drunk case.  */

   public static void movePlayer() {
      if (sober == true) {
         if (playerYPosition > 0 && playerYPosition < (SCREENHEIGHT - PLAYERHEIGHT)) { /* Player is somewhere in the middle of screen*/
	         playerYPosition += playerSpeed;
	         player.setRect(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT);
	         gamePanel.repaintGamePanel();
         }
         if (playerYPosition <= 0 && playerSpeed == DOWN) {
	         playerYPosition += playerSpeed;
	         player.setRect(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT); /* Player is at the top of screen */
	         gamePanel.repaintGamePanel();
         }
         if (playerYPosition >= (SCREENHEIGHT - PLAYERHEIGHT) && playerSpeed == UP) { /* Player is at the bottom of screen*/
	         playerYPosition += playerSpeed;
	         player.setRect(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT);
	         gamePanel.repaintGamePanel();
         }
      }
      else {
         if (playerYPosition > 0 && playerYPosition < (SCREENHEIGHT - PLAYERHEIGHT)) {
        	 playerYPosition -= playerSpeed;
        	 player.setRect(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT);
        	 gamePanel.repaintGamePanel();
         }
         if (playerYPosition <= 0 && playerSpeed == UP) {
        	 playerYPosition -= playerSpeed;
        	 player.setRect(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT);
        	 gamePanel.repaintGamePanel();
         }
         if (playerYPosition >= (SCREENHEIGHT - PLAYERHEIGHT) && playerSpeed == DOWN) {
        	 playerYPosition -= playerSpeed;
        	 player.setRect(PLAYERXPOSITION, playerYPosition, PLAYERWIDTH, PLAYERHEIGHT);
        	 gamePanel.repaintGamePanel();
         }
      }
   }

   // Task object for player motion
   public static PlayerMotionTask playerMotionTask = new PlayerMotionTask();

   // Thread for player motion
   public static Thread playerMotionThread = new Thread(playerMotionTask);

/*---------------------------Player Motion Ends-----------------------------------*/

/*---------------------------Barriers Begin-----------------------------------*/

   // Task class for barrier
   public static class BarrierTask implements Runnable {
      @Override
      public void run() {
         try {
            while (true) {
               moveBarrier();
               Thread.sleep(15);
            }
         } 
         catch (InterruptedException ex) {
         }
      }
   }

   // Method for barrier motion
   public static void moveBarrier() {
      Double newXAbove = barrierAbove.getX() - oncomingSpeed;
      Double newXBelow = barrierBelow.getX() - oncomingSpeed;
      if (barrierAbove.getMaxX() >= 0) {
         barrierAbove.setRect(newXAbove, barrierAbove.getY(), BARRIERWIDTH, BARRIERHEIGHT);
         barrierBelow.setRect(newXBelow, barrierBelow.getY(), BARRIERWIDTH, BARRIERHEIGHT);
         gamePanel.repaintGamePanel();
      }

      if (barrierAbove.getMaxX() < 0) {
    	 if(SPACE >= 40){
    		 SPACE -= 10;
    		 if(DOWN >= 6){
	    		 UP += 1;
	    		 DOWN -= 1;
    		 }
    	 }	 
         initializeBarrier();
         gamePanel.repaintGamePanel();
      }

      if (barrierAbove.getMaxY() > 0 && barrierAbove.intersects(player.getX(), player.getY(), PLAYERWIDTH, PLAYERHEIGHT)) {
         System.out.println("Game Over");
         System.exit(0);
      }

      if (barrierBelow.getY() < SCREENHEIGHT && barrierBelow.intersects(player.getX(), player.getY(), PLAYERWIDTH, PLAYERHEIGHT)) {
         System.out.println("Game Over");
         System.exit(0);
      }
   }

   // Task object for barrier motion
   public static BarrierTask barrierTask = new BarrierTask();

   // Thread for barrier motion
   public static Thread barrierThread = new Thread(barrierTask);

/*---------------------------Barriers End-----------------------------------*/

/*---------------------------Marijuana Begins-----------------------------------*/

   // Task class for marijuana
   public static class MarijuanaTask implements Runnable {
      @Override
      public void run() {
         try {
            while (true) {
               moveMarijuana();
               Thread.sleep(15);
            }
         } 
         catch (InterruptedException ex) {
         }
      }
   }

   // Method for marijuana motion
   public static void moveMarijuana() {
      Double newX = marijuana.getX() - oncomingSpeed;
      if (marijuana.getMaxX() >= 0) {
         marijuana.setRect(newX, marijuana.getY(), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
      }
      if (marijuana.getMaxX() < 0) {
         marijuana.setRect(SCREENWIDTH + random.nextDouble() * SCREENWIDTH, (random.nextDouble() * SCREENHEIGHT), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
      }
      if (marijuana.intersects(player.getX(), player.getY(), PLAYERWIDTH, PLAYERHEIGHT)) {
         marijuana.setRect(SCREENWIDTH, (random.nextDouble() * SCREENHEIGHT), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
         marijuanaContact();
      }
   }

   // Task object for marijuana motion
   public static MarijuanaTask marijuanaTask = new MarijuanaTask();

   // Thread for marijuana motion
   public static Thread marijuanaThread = new Thread(marijuanaTask);

   // Method to slow down game when marijuana is consumed
   public static void marijuanaContact() {
      oncomingSpeed = SLOW;
      UP = MOVESLOW * -1;
      DOWN = MOVESLOW;
      //Run music
      if (cocaineTimer.isRunning())
         cocaineTimer.stop();
      if (marijuanaTimer.isRunning())
         marijuanaTimer.stop();
      marijuanaTimer.start();
   }

   // Listener class for marijuana timer
   public static class MarijuanaTimerListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         oncomingSpeed = NORMAL;
         UP = MOVENORMAL*-1;
         DOWN = MOVENORMAL;
      }
   }

/*---------------------------Marijuana Ends-----------------------------------*/

/*---------------------------Cocaine Begins-----------------------------------*/

   // Task class for cocaine
   public static class CocaineTask implements Runnable {
      @Override
      public void run() {
         try {
            while (true) {
               moveCocaine();
               Thread.sleep(15);
            }
         } 
         catch (InterruptedException ex) {
         }
      }
   }   

   // Method for cocaine motion
   public static void moveCocaine() {
      Double newX = cocaine.getX() - oncomingSpeed;
      if (cocaine.getMaxX() >= 0) {
         cocaine.setRect(newX, cocaine.getY(), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
      }
      if (cocaine.getMaxX() < 0) {
         cocaine.setRect(SCREENWIDTH + random.nextDouble() * SCREENWIDTH, (random.nextDouble() * SCREENHEIGHT), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
      }
      if (cocaine.intersects(player.getX(), player.getY(), PLAYERWIDTH, PLAYERHEIGHT)) {
         cocaine.setRect(SCREENWIDTH, (random.nextDouble() * SCREENHEIGHT), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
         cocaineContact();
      }
   }

   // Task object for cocaine motion
   public static CocaineTask cocaineTask = new CocaineTask();

   // Thread for cocaine motion
   public static Thread cocaineThread = new Thread(cocaineTask);

   // Method to speed up game when cocaine is consumed
   public static void cocaineContact() {
      oncomingSpeed = FAST;
      UP = MOVEFAST * -1;
      DOWN = MOVEFAST;
      if (marijuanaTimer.isRunning())
         marijuanaTimer.stop();
      if (cocaineTimer.isRunning())
         cocaineTimer.stop();
      cocaineTimer.start();
   }

   // Listener class for cocaine timer
   public static class CocaineTimerListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         oncomingSpeed = NORMAL;
         UP = MOVENORMAL*-1;
         DOWN = MOVENORMAL;
      }
   }

/*---------------------------Cocaine Ends-------------------------------------*/

/*---------------------------Alcohol Begins-----------------------------------*/

   // Task class for alcohol
   public static class AlcoholTask implements Runnable {
      @Override
      public void run() {
         try {
            while (true) {
               moveAlcohol();
               Thread.sleep(15);
            }
         } 
         catch (InterruptedException ex) {
         }
      }
   }   

   // Method for alcohol motion
   public static void moveAlcohol() {
      Double newX = alcohol.getX() - oncomingSpeed;
      if (alcohol.getMaxX() >= 0) {
         alcohol.setRect(newX, alcohol.getY(), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
      }
      if (alcohol.getMaxX() < 0) {
         alcohol.setRect(SCREENWIDTH + random.nextDouble() * SCREENWIDTH, (random.nextDouble() * SCREENHEIGHT), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
      }
      if (alcohol.intersects(player.getX(), player.getY(), PLAYERWIDTH, PLAYERHEIGHT)) {
         alcohol.setRect(SCREENWIDTH, (random.nextDouble() * SCREENHEIGHT), DRUGWIDTH, DRUGHEIGHT);
         gamePanel.repaintGamePanel();
         alcoholContact();
      }
   }

   // Task object for alcohol motion
   public static AlcoholTask alcoholTask = new AlcoholTask();

   // Thread for alcohol motion
   public static Thread alcoholThread = new Thread(alcoholTask);

   // Method to reverse controls when alcohol is consumed
   public static void alcoholContact() {
      sober = false;
      alcoholTimer.start();
   }

   // Listener class for alcohol timer
   public static class AlcoholTimerListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         sober = true;
      }
   }

/*---------------------------Alcohol Ends-----------------------------------*/

/*---------------------------Game Panel Begins-----------------------------------*/

   // Game panel
   public static class GamePanel extends JPanel {
      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);

         Graphics2D g2 = (Graphics2D)g;

         // Draw player
         g2.fill(player);

         // Draw barrier
         g2.fill(barrierAbove);
         g2.fill(barrierBelow);

         // Draw marijuana
         g2.setPaint(Color.GREEN);
         g2.fill(marijuana);

         // Draw cocaine
         g2.setPaint(Color.WHITE);
         g2.fill(cocaine);

         // Draw alcohol
         g2.setPaint(Color.ORANGE);
         g2.fill(alcohol);

         if (marijuana2 != null)
            g2.fill(marijuana2);

         if (cocaine2 != null)
            g2.fill(cocaine2);

         if (alcohol2 != null)
            g2.fill(alcohol2);
      }

      // Repaint game panel
      public synchronized void repaintGamePanel() {
         repaint();
      }
   }

   // Set up game panel
   public static synchronized void gamePanelHousekeeping() {
      gamePanel.setPreferredSize(new Dimension((int)SCREENWIDTH, (int)SCREENHEIGHT));
      gamePanel.setFocusable(true);
      PlayerKeyListener playerKeyListener = new PlayerKeyListener();
      gamePanel.addKeyListener(playerKeyListener);
      initializeDrugs();
      initializeBarrier();
      initializeDrugTimers();
      playerMotionThread.start();
      marijuanaThread.start();
      cocaineThread.start();
      alcoholThread.start();
      barrierThread.start();
   }

/*---------------------------Game Panel Ends-----------------------------------*/

   public static void main(String[] args) { 
      frame.setTitle("Questionable Substances");
      gamePanelHousekeeping();
      frame.add(gamePanel);  
      frame.pack();
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

}