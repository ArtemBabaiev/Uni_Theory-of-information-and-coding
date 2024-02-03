package edu.toiac.lab1.jar.utils;

import java.util.Map;

import edu.toiac.lab1.models.FrequencyTable;

public class EntropyUtils {
	public static double calcEntropy(FrequencyTable table) {
		Map<Character, Long> map = table.getDictionary();
		Double sum = 0.0;
		for (var pair : map.entrySet()) {
			Double relative = pair.getValue() / (double) table.getTotal();
			sum += relative * customLog(2, relative);
		}
		return -sum;
	}
	
	public static double calcMaxEntropy(FrequencyTable table) {
		return customLog(2, table.getDictionary().size());
	}
	
	public static double calcMessageSourceRedundancy(FrequencyTable table) {
		return 1 - calcEntropy(table)/calcMaxEntropy(table);
	}
	
	public static double calcAmountOfInformation(Double possibility) {
		return -customLog(2, possibility);
	}
	
	public static double calcAmoutOfInformationInMessage(FrequencyTable table) {
		return table.getTotal() * calcEntropy(table);
	}
	
	private static double customLog(double base, double logNumber) {
	    return Math.log(logNumber) / Math.log(base);
	}
}
