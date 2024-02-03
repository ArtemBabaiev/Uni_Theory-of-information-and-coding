package edu.toiac.lab5;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class LzwAlg {
	static Character termination = (char) 0;

	public static List<String> encode(String input) {
		List<String> encoded = new ArrayList<String>();
		Map<String, Integer> table = initDict();
		input += termination;
		char[] chars = input.toCharArray();

		String p = "", c = "";
		p += chars[0];

		var code = table.size();
		int codeWidth = Integer.toBinaryString(code - 1).length();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				c += chars[i + 1];
			}
			if (table.containsKey(p + c)) {
				p = p + c;
			} else {
				var bin = Integer.toBinaryString(table.get(p));
				encoded.add(StringUtils.leftPad(bin, codeWidth, "0"));
				table.put(p + c, code);
				codeWidth = Integer.toBinaryString(code).length();
				code++;
				p = c;
			}
			c = "";
		}
		encoded.add(StringUtils.leftPad("0", codeWidth, "0"));
		return encoded;
	}

	public static String decode(List<String> encoded) {
		Map<Integer, String> table = initRevDict();
		List<Integer> codes = encoded.stream().map(i -> Integer.parseInt(i, 2)).toList();
		StringBuilder sb = new StringBuilder();
		int count = table.size();
		String seq = table.get(codes.get(0));
		String conjecture = seq;
		sb.append(seq);
		for (int i = 1; i < codes.size(); i++) {
			if (table.containsKey(codes.get(i))) {
				var symb = table.get(codes.get(i));
				sb.append(symb);
				table.put(count, conjecture + symb.charAt(0));
				count++;
				conjecture = symb;
			} else {
				String v = conjecture + conjecture.charAt(0);
				table.put(count, v);
				count++;
				sb.append(v);
				conjecture = v;
			}
		}
		return sb.substring(0, sb.length()-1);
	}

	public static Map<String, Integer> initDict() {
		Map<String, Integer> table = new LinkedHashMap<>();
		for (int i = 0; i <= 255; i++) {
			String key = "" + (char) i;
			table.put(key, i);
		}
//		table.put("" + (char) 0, 0);
//		for (int i = 65; i <= 90; i++) {
//			table.put("" + (char) i, i - 64);
//		}
		return table;
	}

	public static Map<Integer, String> initRevDict() {
		Map<Integer, String> table = new LinkedHashMap<>();
		for (int i = 0; i <= 255; i++) {
			String value = "" + (char) i;
			table.put(i, value);
		}
//		table.put(0, "" + (char) 0);
//		for (int i = 65; i <= 90; i++) {
//			table.put(i - 64, "" + (char) i);
//		}
		return table;
	}

}
