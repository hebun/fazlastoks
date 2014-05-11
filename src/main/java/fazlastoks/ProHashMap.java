package fazlastoks;

import java.util.HashMap;

public class ProHashMap extends HashMap<Integer, Boolean> {

	public int getTrueCount() {
		int k = 0;
		for (boolean b : this.values()) {
			if (b)
				k++;
		}
		return k;

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1861912378987365657L;

}