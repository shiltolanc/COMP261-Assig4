import java.util.ArrayList;
import java.util.List;

/**
 * A new KMP instance is created for every substring search performed. Both the
 * pattern and the text are passed to the constructor and the search method. You
 * could, for example, use the constructor to create the match table and the
 * search method to perform the search itself.
 */
public class KMP {

	/**
	 * Perform KMP substring search on the given text with the given pattern.
	 * 
	 * This should return the starting index of the first substring match if it
	 * exists, or -1 if it doesn't.
	 */
	public static int search(String pattern, String text) {
		int k = 0;
		int i = 0;
		int n = text.length();
		List<Integer> M = M(pattern);

		while (k + i < n) {
			if(pattern.charAt(i) == text.charAt(k + i)){
				i = i + 1;
				if(i == pattern.length()){
					return k;
				}
			} else if (M.get(i) == -1) {
				k = k + i + 1;
				i = 0;

			} else {
				k = k + i - M.get(i);
				i = M.get(i);
			}
		}

		return -1;
	}


	public static List<Integer> M(String pattern) {
		int j = 0;
		int pos = 2;
		List<Integer> M = new ArrayList<>();
		M.add(-1);
		M.add(0);

		while (pos < pattern.length()) {
			if (pattern.charAt(pos - 1) == pattern.charAt(j)) {
				M.add(pos, j + 1);
				pos++;
				j++;
			} else if (j > 0) {
				j = M.get(j);
			} else {
				M.add(pos, 0);
				pos++;
			}
		}

		return M;
	}

}



