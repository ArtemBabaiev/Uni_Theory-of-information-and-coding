package edu.toiac.lab4;

import java.util.ArrayList;
import java.util.List;

public class LevensteinAlg {
	public static List<String> encode(List<Integer> input) {
		List<String> encoded = new ArrayList<String>();
		
		for (Integer integer : input) {
			encoded.add(encode(integer));
		}
		
		return encoded;
	}

	private static String encode(int num) {
		if (num == 0) {
			return "0";
		}
		StringBuilder code = new StringBuilder();
		int c = 0;
		do {
			String bin = Integer.toBinaryString(num).substring(1);
			code.insert(0, bin);
			int m = bin.length();
			num = m;
			c++;
		} while (num > 0);

		code.insert(0, "0");
		for (int i = 0; i < c; i++) {
			code.insert(0, "1");
		}
		return code.toString();
	}

	public static List<Integer> decode(List<String> input) {
		List<Integer> decoded = new ArrayList<Integer>();

		for (String string : input) {
			decoded.add(decode(string));
		}

		return decoded;
	}

	private static int decode(String strBits) {
		char[] bits = strBits.toCharArray();
		int counter = 0;
		for (char c : bits) {
			if (c == '1') {
				counter++;
			} else {
				break;
			}
		}

		if (counter == 0) {
			return 0;
		}

		strBits = strBits.substring(counter + 1);
		int n = 1;
		int p = counter - 1;
		while (p > 0) {
			String firstBits = strBits.substring(0, n);
			strBits = strBits.substring(n);
			firstBits = "1" + firstBits;
			n = Integer.parseInt(firstBits, 2);
			p--;
		}
		return n;
	}
}
