import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class View extends JFrame{
	private JPanel frame;
	private JLabel[][] slots = new JLabel [5][5] ;
	private Dimension dim;
	private Position position = new Position(1, 2);
	
	public View(String [][] grid) throws IOException{
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width-dim.height/3)/2 , dim.height/3);
		setSize(new Dimension(dim.height/3,dim.height/3));
//		Dimension size = getBounds().getSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame = new JPanel ();
		frame.setPreferredSize(new Dimension(dim.height/3,dim.height/3));
		frame.setLayout(new GridLayout(5,5));
		frame.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		
		for (int i =0; i<5; i++){
			for(int j=0; j<5; j++){
				JLabel label = new JLabel();
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setText(grid[i][j]);
				label.setFont(new Font("Arial", Font.BOLD, 30));
				slots[i][j] = label;
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				frame.add(label);
			}
		}
		
		add(frame);
		setVisible(true);
	}
	
	public void killWarriors(Position p) {
		if(p.getX() != 0 && this.slots[p.getX()-1][p.getY()].getText().equals("W"))
			this.slots[p.getX()-1][p.getY()].setText("");
		if(p.getX() != this.slots.length-1 && this.slots[p.getX()+1][p.getY()].getText().equals("W"))
			this.slots[p.getX()+1][p.getY()].setText("");
		if(p.getY() != 0 && this.slots[p.getX()][p.getY()-1].getText().equals("W"))
			this.slots[p.getX()][p.getY()-1].setText("");
		if(p.getY() != this.slots[p.getX()].length - 1 && this.slots[p.getX()][p.getY()+1].getText().equals("W"))
			this.slots[p.getX()][p.getY()+1].setText("");
	}
	
	public void startGame(String moves) throws InterruptedException {
		moves = moves.substring(0, moves.indexOf(";"));
		String [] temp = moves.split(",");
		for(String t: temp) {
			TimeUnit.SECONDS.sleep(2);
			switch(t) {
			
			case "up": 
				slots[this.position.getX()-1][this.position.getY()].setText("I"+slots[this.position.getX()-1][this.position.getY()].getText());
				slots[this.position.getX()][this.position.getY()].setText(slots[this.position.getX()][this.position.getY()].getText().substring(1));
				this.position.setX(this.position.getX()-1);
				break;
				
			case "down":
				slots[this.position.getX()+1][this.position.getY()].setText("I"+slots[this.position.getX()+1][this.position.getY()].getText());
				slots[this.position.getX()][this.position.getY()].setText(slots[this.position.getX()][this.position.getY()].getText().substring(1));
				this.position.setX(this.position.getX()+1);
				break;
			
			case "right":
				slots[this.position.getX()][this.position.getY()+1].setText("I"+slots[this.position.getX()][this.position.getY()+1].getText());
				slots[this.position.getX()][this.position.getY()].setText(slots[this.position.getX()][this.position.getY()].getText().substring(1));
				this.position.setY(this.position.getY()+1);
				break;
			
			case "left":
				slots[this.position.getX()][this.position.getY()-1].setText("I"+slots[this.position.getX()][this.position.getY()-1].getText());
				slots[this.position.getX()][this.position.getY()].setText(slots[this.position.getX()][this.position.getY()].getText().substring(1));
				this.position.setY(this.position.getY()-1);
				break;
				
			case "collect": slots[this.position.getX()][this.position.getY()].setText("I"); break;
			
			case "kill": killWarriors(this.position);
				
			case "snap": slots[this.position.getX()][this.position.getY()].setText("I"); break;
			
			default: break;
			}
		}
	}
	//up,collect,left,down,collect,down,collect,right,collect,kill,down,down,left,
//	collect,left,collect,right,up,snap;63;52333
	public Dimension getDim() {
		return dim;
	}


	public JLabel[][] getSlots() {
		return slots;
	}

	public static void main (String [] args) throws IOException, InterruptedException {
		String [][] grid = {{"", "", "S", "W", ""},{"", "S", "I", "", ""},{"", "S", "S", "", ""},{"W", "T", "W", "", "W"},{"S", "S", "", "W", ""}};
		View a = new View(grid);
		TimeUnit.SECONDS.sleep(5);
		String game = "up,collect,left,down,collect,down,collect,right,collect,kill,down,down,left,collect,left,collect,right,up,snap;63;52333";
		a.startGame(game);
	}
}

