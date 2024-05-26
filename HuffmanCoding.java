/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */

import java.util.*;

public class HuffmanCoding {
	private Node huffmanTree;
	private Map<Character, Integer> freq;
	private Map<Character, String> huffmanCode;

	private static class Node implements Comparable<Node> {
		private int frequency;
		private char character;
		private Node left;
		private Node right;

		public Node(char character, int frequency) {
			this.character = character;
			this.frequency = frequency;
		}

		@Override
		public int compareTo(Node other) {
			if (this.frequency == other.frequency) {
				return Character.compare(this.character, other.character);
			}
			return Integer.compare(this.frequency, other.frequency);
		}
	}

	public HuffmanCoding(String text) {
		freq = new HashMap<>();
		for (char c : text.toCharArray()) {
			freq.put(c, freq.getOrDefault(c, 0) + 1);
		}

		PriorityQueue<Node> nodes = new PriorityQueue<>();

		for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
			char character = entry.getKey();
			int frequency = entry.getValue();
			nodes.offer(new Node(character, frequency));
		}

		while (nodes.size() > 1) {
			Iterator<Node> iterator = nodes.iterator();
			while (iterator.hasNext()) {
				System.out.print(iterator.next().character + " ");
			}
			System.out.println();

			Node left = nodes.poll();
			Node right = nodes.poll();

			Node parent = new Node('-', left.frequency + right.frequency);
			parent.left = left;
			parent.right = right;

			nodes.offer(parent);
		}

		huffmanTree = nodes.poll();
		huffmanCode = new HashMap<>();
		buildHuffmanCode(huffmanTree, "");
	}

	private void buildHuffmanCode(Node node, String code) {
		if (node == null) {
			return;
		}

		if (node.left == null && node.right == null) {
			huffmanCode.put(node.character, code);
			return;
		}

		buildHuffmanCode(node.left, code + "0");
		buildHuffmanCode(node.right, code + "1");
	}

	public String encode(String text) {
		StringBuilder encodedString = new StringBuilder();
		for (char c : text.toCharArray()) {
			encodedString.append(huffmanCode.get(c));
		}
		return encodedString.toString();
	}

	public String decode(String encoded) {
		StringBuilder decodedString = new StringBuilder();
		Node currentNode = huffmanTree;

		for (char bit : encoded.toCharArray()) {
			if (bit == '0') {
				currentNode = currentNode.left;
			} else if (bit == '1') {
				currentNode = currentNode.right;
			}

			if (currentNode.left == null && currentNode.right == null) {
				decodedString.append(currentNode.character);
				currentNode = huffmanTree;
			}
		}

		return decodedString.toString();
	}

	public String getInformation() {
		//return printCode(huffmanTree, "");
		return "";
	}

	private String printCode(Node node, String code) {
		if (node == null) {
			return "";
		}

		if (node.left == null && node.right == null) {
			return node.character + " | " + code + " | " + node.frequency + "\n";
		}

		String leftCode = printCode(node.left, code + "0");
		String rightCode = printCode(node.right, code + "1");

		return leftCode + rightCode;
	}
}




