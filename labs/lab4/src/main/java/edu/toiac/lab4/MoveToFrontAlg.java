package edu.toiac.lab4;

import java.util.ArrayList;
import java.util.List;

public class MoveToFrontAlg {
	public static List<Integer> encode(String text, List<Character> alpha) {
		char[] cText = text.toCharArray();
		List<Integer> encoded = new ArrayList<Integer>();
		for (char c : cText) {
			int index = alpha.indexOf(c);
			encoded.add(index);
			alpha.add(0, alpha.remove(index));
		}
		return encoded;
	}
	
	public static String decode(List<Integer> encoded, List<Character> alpha) {
		StringBuilder sb = new StringBuilder();
		for (Integer index : encoded) {
			sb.append(alpha.get(index));
			alpha.add(0, alpha.remove((int) index));
		}
		
		return sb.toString();
	}
}
