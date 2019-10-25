import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.sun.javafx.webkit.ThemeClientImpl;


public abstract class Problem {
	protected ArrayList<Operator> Operators;
	protected State initialState;
	
	public abstract ArrayList<Node> expand(Node node, ArrayList<Operator> Operators);
	public abstract Node transitionFunction(Node node, Operator operator);
	public abstract boolean goalTest(Node node);
	public abstract int pathCost(Node node);
	
	public String generalSearch(Problem problem, String QINGFUN) {
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.initialState, null, null, 0, 0));
		ArrayList<Node> nodeExpand = new ArrayList<Node>();
		
		int count = 0;
		
		if(QINGFUN.contains("ID")) {
			if(QINGFUN.length() > 2)
				count =Integer.parseInt(QINGFUN.substring(2));
			QINGFUN = "ID";
		}
		
		while(true) {
			nodeExpand.clear();
			if(nodes.isEmpty())
				return null;
			
			Node node = nodes.remove(0);
			
			if(problem.getClass().getName().equals("EndGame")) {
				if(!((EndGame)this).getVisited().contains(((EndGameState) node.getState()).getState())) {
					((EndGame)this).getVisited().add(((EndGameState) node.getState()).getState());
				}
			}
			
			if(problem.goalTest(node)) {
				System.out.println(node.getDepth());
				return printOperators(node) + ";" + node.getPathCost() + ";" + ((EndGame)this).getVisited().size();
			}
			if (!QINGFUN.contains("ID") || (QINGFUN.contains("ID") && node.getDepth() <= count))
				nodeExpand = this.expand(node, this.Operators);

			switch(QINGFUN) {
		
				case "BF": 
					Iterator currentNode = nodeExpand.iterator();
					while(currentNode.hasNext())
						nodes.add((Node) currentNode.next());
					break;
				case "DF":
						for(int i = nodeExpand.size() - 1; i > -1; i--)
							nodes.add(0, nodeExpand.get(i));
					break;
				case "ID":
						for(int i = nodeExpand.size() - 1; i > -1; i--)
							nodes.add(0, nodeExpand.get(i));
					break;
				case "UC":
					break;
				case "GR1":
					break;
				case "GR2":
					break;
				case "AS1":
					break;
				case "AS2":
					break;
				default:
					break;
			}
		}
		
	}
	
	public static String printOperators(Node node){
		if(node.getParentNode() == null)
			return "";
		return printOperators(node.getParentNode()) + node.getOperator().getName() + ",";
	}
}
