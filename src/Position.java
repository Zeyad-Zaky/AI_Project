

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Boolean equals(Position position){
		if( ! (this.getX() == position.getX() && this.getY()==position.getY() ) )
				return false;
		else return true;
	}
}
