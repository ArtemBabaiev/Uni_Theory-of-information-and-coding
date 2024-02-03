package edu.toiac.lab2;

import java.util.Map.Entry;
import java.util.regex.Pattern;

import edu.toiac.lab1.models.FrequencyTable;

public class EntropyUtils {
	public static double getEntropyOfExit(FrequencyTable table) {
		return customLog(2, table.getDictionary().size());
	}
	
	public static double getEntropyOfInterference(String text,FrequencyTable table) {
		var total = table.getTotal();
		double sum = 0.0;
		for (var yj : table.getDictionary().entrySet()) {
			for (var xi : table.getDictionary().entrySet()) {
				var conditionalProb = pa_b(yj, xi, total, text);
				if (conditionalProb == 0) {
					continue;
				}
				var xiProb = xi.getValue()/(double) total;
				 sum += xiProb * conditionalProb * customLog(2, conditionalProb);
			}
		}
		return -sum;
	}

	public static double getSpeedOfInformationTransmission(double Vk, double Hx, double Hyx) {
		return Vk * (Hx - Hyx);
	}

	public static double getBandwith(double Vk, double Hy, double Hyx) {
		return Vk * (Hy - Hyx);
	}
	
	public static double getK(double C, double R) {
		return R/C;
	}

	private static double customLog(double base, double logNumber) {
		return Math.log(logNumber) / Math.log(base);
	}

	private static double pa_b(Entry<Character, Long> a, Entry<Character, Long> b, Long total, String text) {
		var pa = a.getValue() / (double) total;
		var pb = b.getValue() / (double) total;
		var pab = calcAB(a.getKey(), b.getKey(), text) / (double) total;
		return pab / pb;
	}
	
	private static long calcAB(Character a, Character b, String text) {
		String ab = a.toString() + b.toString();
		var m = Pattern.compile(ab).matcher(text);
		long count = 0;
		while(m.find()) {
			count++;
		}
		return count;
	}
}
