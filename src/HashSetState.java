import java.util.HashSet;
import java.util.Iterator;


public class HashSetState extends HashSet<EndGameState>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean contains(EndGameState state) {
		
		Iterator iterator = this.iterator();
		
		while(iterator.hasNext()) {
			EndGameState states = (EndGameState) iterator.next();
			if(states.equals(state))
				return true;
		}
		
		return false;
	}
}
