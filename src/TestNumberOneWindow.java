import javax.swing.JFrame;

public class TestNumberOneWindow {
	public static void main(String[] args){
		JFrame f = new JFrame();
		TestNumberOne tester = new TestNumberOne(700);
		f.add(tester);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 700);
	}
}
