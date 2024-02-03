package edu.toiac.lab1.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.toiac.lab1.models.FrequencyTable;

public class TextProcessor {
	private static Pattern nonLetter = Pattern.compile("[^\\p{L}]", Pattern.UNICODE_CHARACTER_CLASS);
	
	public FrequencyTable calculateFrequencyForText(String text) {
		FrequencyTable table = new FrequencyTable();
		String cleaned = this.cleanText(text);
		for (int i = 0; i < cleaned.length(); i++) {
			char c = cleaned.charAt(i);
			table.countLetter(c);
		}
		
		return table;
	}
	
	public String cleanText(String text) {
		String lcText = text.toLowerCase();
		Matcher m = nonLetter.matcher(lcText);
		String cleaned = m.replaceAll("");
		return cleaned;
	}
}
