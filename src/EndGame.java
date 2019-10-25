import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class EndGame extends Problem{
	private HashMap<String, Position> positions;
	private HashSetState visitedStates;
	private HashSet<String> visited;
	
	public HashSetState getVisitedStates() {
		return visitedStates;
	}

	public void setVisitedStates(HashSetState visitedStates) {
		this.visitedStates = visitedStates;
	}
	
	public EndGame(HashMap<String, Position> positions, String state) {
		visitedStates = new HashSetState();
		visited = new HashSet<String>();
		this.positions = positions;
		
		this.Operators = new ArrayList<Operator> ();
		this.Operators.add(new Operator("up"));
		this.Operators.add(new Operator("down"));
		this.Operators.add(new Operator("left"));
		this.Operators.add(new Operator("right"));
		this.Operators.add(new Operator("collect"));
		this.Operators.add(new Operator("kill"));
		this.Operators.add(new Operator("snap"));
		
		this.initialState = new EndGameState(state);
	}
	
	
	
	@Override
	public boolean goalTest(Node node) { //ASK Important
		// TODO Auto-generated method stub
		if(node.getOperator() == null)
			return false;
		
		return node.getOperator().getName().equals("snap")? true : false;
		
	}

	public int damage (EndGameState state) {
		int total = 0;
		
		for(String p: adjacentCells(state.getPlayerPosition())) {
			if(state.containsWarrior(p))
				total += 1;
			
			else if(this.positions.get("Thanos").equals(new Position(Integer.parseInt(p.split(",")[0]),
					Integer.parseInt(p.split(",")[1].substring(0, p.split(",")[1].length() - 1)))))
				total += 5;
		}
		
		return total;
	}
	

	// generic transition function
	public Node transitionFunction(Node node, Operator operator) {
		// TODO Auto-generated method stub
		EndGameState state = ((EndGameState)node.getState());
		String [] playerPosition = ((EndGameState)node.getState()).getPlayerPosition().split(",");
		int position_x = Integer.parseInt(playerPosition [0]);
		int position_y = Integer.parseInt(playerPosition [1]);
		String newState = state.getState();
		
		EndGameState tempState = new EndGameState(newState);
		
		
		switch(operator.getName()) {
		case "up":
			tempState.setPlayerPosition(position_x - 1, position_y);
			
			if(visited.contains(tempState.getState()))
				return null;
			
			else 
				return new Node(tempState, node, new Operator("up"), node.getDepth() + 1, node.getPathCost() + damage(tempState)); 
			
		case "down":
			tempState.setPlayerPosition(position_x + 1, position_y);

			if(visited.contains(tempState.getState()))
				return null;
			
			else
				return new Node(tempState, node, new Operator("down"), node.getDepth() + 1, node.getPathCost() + damage(tempState));
			
		case "left":

			tempState.setPlayerPosition(position_x, position_y - 1);

			if(visited.contains(tempState.getState()))
				return null;
			
			else
				return new Node(tempState, node, new Operator("left"), node.getDepth() + 1, node.getPathCost() + damage(tempState));
		
		case "right":
			tempState.setPlayerPosition(position_x, position_y + 1);
			
			if(visited.contains(tempState.getState()))
				return null;
			
			else
				return new Node(tempState, node, new Operator("right"), node.getDepth() + 1, node.getPathCost() + damage(tempState));
			
		case "collect":
			tempState.removeStone(position_x + "," + position_y + ";");
			
			if(visited.contains(tempState.getState()))
				return null;
			
			else
				return new Node(tempState, node, new Operator("collect"), node.getDepth() + 1, node.getPathCost() + 3 + damage(tempState));
			
		case "kill":
			int total = 0;
			for(String p: adjacentCells(position_x + "," + position_y)) {
				if(tempState.containsWarrior(p)){
					total += 2;
					tempState.removeWarrior(p);
				}
			}
			
			if(visited.contains(tempState.getState()))
				return null;
			
			else
				return new Node(tempState, node, new Operator("kill"), node.getDepth() + 1, node.getPathCost() + total + damage(tempState));
		case "snap": 
				return new Node(tempState, node, new Operator("snap"), node.getDepth() + 1, node.getPathCost());
		
			
		default: break;
		}
		
		return null;
		
	}
	
		
	
	public ArrayList<String> adjacentCells(String position) {
		ArrayList<String> positions = new ArrayList<String> ();
		
		String [] playerPosition = position.split(",");
		int position_x = Integer.parseInt(playerPosition [0]);
		int position_y = Integer.parseInt(playerPosition [1]);
		
		if(position_x != 0)
			positions.add((position_x - 1) + "," + position_y + ";");
		if(position_x != this.positions.get("Grid").getX() - 1)
			positions.add((position_x + 1) + "," + position_y + ";");
		if(position_y != 0)
			positions.add(position_x + "," + (position_y - 1) + ";");
		if(position_y != this.positions.get("Grid").getY() - 1)
			positions.add(position_x + "," + (position_y + 1) + ";");
		
		return positions;
	}

	@Override
	public int pathCost(Node node) {
		// TODO Auto-generated method stub
		return node.getPathCost();
	}
	
	@Override
	public ArrayList<Node> expand(Node node, ArrayList<Operator> operators) {
		// TODO Auto-generated method stub
		String [] playerPosition = ((EndGameState)node.getState()).getPlayerPosition().split(",");
		int position_x = Integer.parseInt(playerPosition [0]);
		int position_y = Integer.parseInt(playerPosition [1]);
		int grid_x = this.positions.get("Grid").getX();
		int grid_y = this.positions.get("Grid").getY();
		
		ArrayList<Node> nodes = new ArrayList<Node> ();
		Iterator values = operators.iterator();
		while(values.hasNext()) {
			Operator operator = (Operator) values.next();
			Node n = null;
			String ss = ((EndGameState) node.getState()).getState();
			switch(operator.getName()) {
			case "up": 
				if( (position_x != 0) &&
				(!((EndGameState) node.getState()).containsWarrior((position_x - 1) + "," + position_y + ";")) &&
				((!this.positions.get("Thanos").equals(new Position(position_x - 1, position_y))) || 
				(this.positions.get("Thanos").equals(new Position(position_x - 1, position_y)) && 
				node.getPathCost() < 100 && ((EndGameState)node.getState()).getState().contains("SW"))))
//				!(this.positions.get("Thanos").equals(new Position(position_x - 1, position_y)) &&
//				(!((EndGameState)node.getState()).getRemainingStones().isEmpty() || node.getPathCost() > 100)))
					n = this.transitionFunction(node, operator);
						break;
				
			case "down":
				if( position_x != grid_x - 1 && 
				!((EndGameState) node.getState()).containsWarrior((position_x + 1) + "," + position_y + ";") &&
				((! this.positions.get("Thanos").equals(new Position(position_x + 1, position_y))) || 
				( this.positions.get("Thanos").equals(new Position(position_x + 1, position_y)) && 
				node.getPathCost() < 100 && ((EndGameState)node.getState()).getState().contains("SW"))))
//				!(this.positions.get("Thanos").equals(new Position(position_x + 1, position_y)) &&
//				(!((EndGameState)node.getState()).getRemainingStones().isEmpty() || node.getPathCost() > 100)))
					n = this.transitionFunction(node, operator);
						break;
			
			case "right":
				
				if( position_y != grid_y - 1 && 
				! ((EndGameState) node.getState()).containsWarrior(position_x + "," + (position_y + 1) + ";") &&
				((! this.positions.get("Thanos").equals(new Position(position_x, position_y + 1))) || 
				( this.positions.get("Thanos").equals(new Position(position_x, position_y + 1)) && 
				node.getPathCost() < 100 && ((EndGameState)node.getState()).getState().contains("SW"))))
//				!(this.positions.get("Thanos").equals(new Position(position_x, position_y + 1)) &&
//				(!((EndGameState)node.getState()).getRemainingStones().isEmpty() || node.getPathCost() > 100)))
					n = this.transitionFunction(node, operator);
						break;
			
			case "left":
				
				if( position_y != 0 && 
				! ((EndGameState) node.getState()).containsWarrior(position_x + "," + (position_y - 1) + ";") &&
				((! this.positions.get("Thanos").equals(new Position(position_x, position_y - 1))) || 
				( this.positions.get("Thanos").equals(new Position(position_x, position_y - 1)) && 
				node.getPathCost() < 100 && ((EndGameState)node.getState()).getState().contains("SW"))))
//				!(this.positions.get("Thanos").equals(new Position(position_x, position_y - 1)) &&
//				(!((EndGameState)node.getState()).getRemainingStones().isEmpty() || node.getPathCost() > 100)))
					n = this.transitionFunction(node, operator);
						break;
				
			case "collect": 
				
				if(((EndGameState) node.getState()).containsStone(position_x + "," + position_y + ";"))
						n = this.transitionFunction(node, operator);
					break;
			
			case "kill": 
				ArrayList<String> positions = adjacentCells(position_x + "," + position_y);
				for(String p: positions) {
					if(((EndGameState) node.getState()).containsWarrior(p)) {
						n = this.transitionFunction(node, operator);
						break;
					}
				}
				break;
				
			case "snap": 
				if(this.positions.get("Thanos").equals(new Position(position_x, position_y)) && node.getPathCost() < 100)
					 n = this.transitionFunction(node, operator);
				break;
				
			default: break;
			}
			if(n!=null)
				nodes.add(n);
		}
		return nodes;
	}

	public HashSet<String> getVisited() {
		return visited;
	}

	public void setVisited(HashSet<String> visited) {
		this.visited = visited;
	}

}