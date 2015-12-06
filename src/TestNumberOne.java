import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Random;

public class TestNumberOne extends JPanel implements ActionListener, KeyListener{

	//Player ellips
	Ellipse2D player;
	
	//Player timer
	Timer t = new Timer(1, this);
	
	//Movement Timer
	Timer drugTimer = new Timer(1, new TimerListener());
	
	//Drug rectangles
	Rectangle2D drug;	
	Rectangle2D drug2;
	Rectangle2D drug3;
	
	//Barrier rectangles
	Rectangle2D barrierTop, barrierBot;
	
	//Randomizer
	Random rand = new Random();
	
	double screenWidth;
	
	//Player specs
	double radius = 20;
	double xPos = 20;	
	double yPos = 0;
	
	//Drug specs
	double drugX, drug2X, drug3X;
	double drugY, drug2Y, drug3Y;
	
	//Barrier specs
	double bTopX, bBotX;
	double bTopY, bBotY;
	double barrierHeight;
	double barrierWidth = 40;
	
	//Variables for player timer
	double yMov = 0;
	boolean upPress = false;
	boolean downPress = false;
	double height = 0;
	
	//Constructor method
	public TestNumberOne(double height){
		//Starts player timer
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		//Determines height of screen
		this.height = height;
		
		//Initial position of player
		yPos = (height / 2) - radius;
		
		screenWidth =  height;
		
		//Sets initial position of drugs
		drugX = screenWidth;
		drug2X = screenWidth + 1000;
		drug2X = screenWidth + 2000;
		drugY = rand.nextDouble()*height;
		drug2Y = rand.nextDouble()*height;
		drug3Y = rand.nextDouble()*height;
		
		barrierHeight = height - 150;
		//Sets initial positions of barriers
		bTopX = screenWidth + 700;
		bBotX = screenWidth + 700;
		bTopY = -barrierHeight*rand.nextDouble();
		bBotY = bTopY + barrierHeight + 150;
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Player Ellipse
		player = new Ellipse2D.Double(xPos, yPos, 40, 40);
		g2.fill(player);
		
		//Drug rectangles
		drug = new Rectangle2D.Double(drugX, drugY, 40, 40);
		drug2 = new Rectangle2D.Double(drug2X, drug2Y, 40, 40);
		drug3 = new Rectangle2D.Double(drug3X, drug3Y, 40, 40);
		g2.fill(drug);
		g2.setColor(Color.blue);
		g2.fill(drug2);
		g2.setColor(Color.red);
		g2.fill(drug3);
		
		//Barrier rectangles
		barrierTop = new Rectangle2D.Double(bTopX, bTopY, barrierWidth, barrierHeight);
		barrierBot = new Rectangle2D.Double(bBotX, bBotY, barrierWidth, barrierHeight);
		g2.setColor(Color.black);
		g2.fill(barrierTop);
		g2.setColor(Color.green);
		g2.fill(barrierBot);
		
		//Drug timer that makes it move
		drugTimer.start();
	}
	public void addNewDrug(){
		if(drugX <= -40 && drug2X <= -40 && drug3X <= -40){
			//Sets drug1 position
			drugX = screenWidth;
			drugY = rand.nextDouble()*height;
			
			//Sets drug2 position
			drug2X = screenWidth + 1000;
			drug2Y = rand.nextDouble()*height;
			
			//Sets drug3 position
			drug3X = screenWidth + 2000;
			drug3Y = rand.nextDouble()*height;
			
			repaint();
		}
		if(bTopX <= -40 && bBotX <= -40){
			bTopX = screenWidth + 700;
			bBotX = screenWidth + 700;
			bTopY = -barrierHeight*rand.nextDouble();
			bBotY = bTopY + barrierHeight + 150;
			
			repaint();
		}
	}
	
	public class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//Tests intersection with drug1, if so disappears (or moves position to +2000)
			if(player.intersects(drug)){
				drugX = screenWidth + 2000;
			}
			//Tests intersection with drug2, if so disappears (or moves position to +2000)
			if(player.intersects(drug2)){
				drug2X = screenWidth + 2000;
			}
			//Tests intersection with drug3, if so disappears (or moves position to +2000)
			if(player.intersects(drug3)){
				drug3X = screenWidth + 2000;
			}	
			
			if(player.intersects(barrierTop) || player.intersects(barrierBot)){
				System.exit(0);
			}
			
			//Moves drugX positions left 3
			drugX-=3;
			drug2X-=3;
			drug3X-=3;
			
			//Moves barrier left
			bTopX-=3;
			bBotX-=3;
			
			repaint();
			addNewDrug();
		}
	}
	
	public void actionPerformed(ActionEvent e){
		repaint();
		
		if((yPos == 0 || yPos < 0) && downPress == false){
			yMov = 0;
		}else if((yPos == getHeight() - (2*radius) || yPos > getHeight() - (2*radius)) && upPress == false){
			yMov = 0;
		}else{ 
			yPos += yMov;
		}
		
	}
	
	
	public void up(){
		yMov = -3;
	}
	public void down(){
		yMov = 3;
	}
	
	
	public void keyPressed(KeyEvent e){
		
		int keyPressed = e.getKeyCode();
		
		if(keyPressed == KeyEvent.VK_UP){
			upPress = true;
			up();
		}
		if(keyPressed == KeyEvent.VK_DOWN){
			downPress = true;
			down();
		}
		if(keyPressed == KeyEvent.VK_ESCAPE){
			yPos = height / 2 - radius;
		}
		
		System.out.println(yPos);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {	
		int keyReleased = e.getKeyCode();
		if(keyReleased == KeyEvent.VK_UP){
			upPress = false;
			yMov = 0;
		}
		if(keyReleased == KeyEvent.VK_DOWN){
			downPress = false;
			yMov = 0;
		}
	}
	
}
