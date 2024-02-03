package edu.toiac.lab1.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FrequencyTable {
	private Map<Character, Long> dictionary;
	private Long total;
	public FrequencyTable() {
		this.dictionary = new HashMap<Character, Long>();
		this.total = 0L;
	}
	
	public void countLetter(char letter) {
		if (dictionary.containsKey(letter)) {
			long value = dictionary.get(letter);
			dictionary.put(letter, value + 1);
		} else {
			dictionary.put(letter, 1L);
		}
		total++;
	}
	
	public Long calcTotal() {
		total = 0L;
		for (var pair : dictionary.entrySet()) {
			total+= pair.getValue();
		}
		return total;
	}
	
}
