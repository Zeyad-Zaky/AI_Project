
public class Node {
	private State state;
	private Node parentNode;
	private Operator operator;
	private int depth;
	private int pathCost;
	
	public Node(State state, Node parentNode, Operator operator, int depth, int pathCost) {
		this.state = state;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
	}
	
	public State getState() {
		return this.state;
	}

	public int getPathCost() {
		return pathCost;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public Operator getOperator() {
		return operator;
	}

	public int getDepth() {
		return depth;
	}

}

