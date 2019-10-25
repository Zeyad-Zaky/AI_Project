import java.util.HashMap;
import java.util.HashSet;


public class Main {
	public static String solve(String grid, String strategy, boolean visualize) {
		HashMap<String, Position> positions = new HashMap<String, Position>();
		String state = parseGrid(grid, positions);
		
		EndGame endgame = new EndGame(positions, state);
		
		if(strategy.equals("ID"))
		{
			int count = 0;
			while(true){
				endgame = null;
				endgame = new EndGame(positions, state);
				String goal = endgame.generalSearch(endgame, strategy+count);
				if( goal != null)
					return goal;
				System.out.println(count);
				count++;
			}
		}
		
		return endgame.generalSearch(endgame, strategy);
	}
	
	public static String parseGrid(String grid, HashMap<String, Position> positions){
		
		String [] temp = grid.split(";");
		
		positions.put("Grid",new Position(Integer.parseInt(temp[0].substring(0,1)), Integer.parseInt(temp[0].substring(2,3))));
		positions.put("Player",new Position(Integer.parseInt(temp[1].substring(0,1)), Integer.parseInt(temp[1].substring(2,3))));
		positions.put("Thanos",new Position(Integer.parseInt(temp[2].substring(0,1)), Integer.parseInt(temp[2].substring(2,3))));
		
		String state = "I" + temp[1] + ";S";
		
		String [] stones = temp[3].split(",");
		for(int i = 0; i < stones.length; i = i+2)
			state += stones[i] + "," + stones[i+1] + ";";
		
		state += "W";
		
		String [] warriors = temp[4].split(",");
		for(int i = 0; i < warriors.length; i = i+2)
			state += warriors[i] + "," + warriors[i+1] + ";";
		
		return state;
			
	}
	
	public static void main(String[] args) {
		String grid = "3,3;0,0;1,1;0,1,2,0;1,0";
		String s = "2,2;0,0;1,0;0,1;1,1";
		String ss = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		String t = "3,2;0,0;0,1;2,0,2,1;1,0";
		System.out.println(solve(ss, "BF", false));
		
	}
}
