
public class EndGameState extends State{
	private String state;
	
	public EndGameState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}
	
	
	public String getPlayerPosition() {
		
		String s = state.split(";") [0];
		
		return s.substring(1);
	}
	
	public void setPlayerPosition(int x, int y) {
		state = "I" + x + "," + y + ";S" + state.split("S")[1];
	}
	
	public boolean equals(EndGameState state) {
		return this.state.equals(state.getState());
	}
	
	public boolean containsStone(String stone) {
		if(state.contains("SW"))
			return false;
		
		String [] s = state.split("S");
		String current = s[1].split("W")[0];
		
		return current.contains(stone);
	}
	
	public void removeStone(String stone) {
		String [] s = state.split("S");
		String [] SW = s[1].split("W");
		String currentStones = SW[0].replace(stone, "");
		
		String temp = s[0] + "S" + currentStones + "W";
		
		if(state.charAt(state.length() - 1) != 'W') //if warriors not empty
			temp += SW[1];
		
		state = temp;
	}
	
	public void removeWarrior(String warrior) {
		String [] s = state.split("S");
		String [] SW = s[1].split("W");
		String currentWarriors = SW[1].replace(warrior, "");
		
		state = s[0] + "S" + SW[0] + "W" + currentWarriors;
//		System.out.println(state);
//		System.out.println("--------");
	}
	
	public boolean containsWarrior(String warrior) {
		if(state.charAt(state.length() - 1) == 'W') //if warriors empty
			return false;
		
		String [] s = state.split("S");
		
		String current = s[1].split("W")[1];
		return current.contains(warrior);
	}
}
