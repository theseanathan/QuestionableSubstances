import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class TestNumberOne extends JPanel implements ActionListener, KeyListener{

	Timer t = new Timer(2, this);
	
	double radius = 20;
	double xPos = 20;	
	double yPos = 0;
	double yMov = 0;
	boolean upPress = false;
	boolean downPress = false;
	double height = 0;
	
	public TestNumberOne(double height){
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.height = height;
		yPos = (height / 2) - radius;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D circle = new Ellipse2D.Double(xPos, yPos, 40, 40);
		g2.fill(circle);
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
		yMov = -2;
	}
	public void down(){
		yMov = 2;
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
