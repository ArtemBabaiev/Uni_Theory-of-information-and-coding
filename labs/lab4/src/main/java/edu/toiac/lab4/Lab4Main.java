package edu.toiac.lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lab4Main {
	private static String ENG = "abcdefghijklmnopqrstuvwxyz";
	private static String UKR = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя";
	private static String SPEC = " ,./;'\"[]{}!@#$%^&*()_+=-:<>?`~";
	private static String input = "стопкакниглежаланастолі";
	public static void main(String[] args) {
		System.out.println("-----MTF-----");
		MTF();
		System.out.println("-----MTF+Levenstein-----");
		MTFL();
		//11110 0 00 0101
		//11110 0 00 0101
	}
	
	
	private static void MTF() {
		var encoded = MoveToFrontAlg.encode(input, getAlphabet());
		System.out.println(encoded);
		var decoded = MoveToFrontAlg.decode(encoded, getAlphabet());
		System.out.println(decoded);
		var temp = encoded.stream().map(i -> i.toString()).collect(Collectors.joining());
		System.out.println(temp.length());
		System.out.println("K = " + calcK(input, temp, getAlphabet().size()));
	}
	
	private static void MTFL() {
		var encoded = LevensteinAlg.encode(MoveToFrontAlg.encode(input, getAlphabet()));
		System.out.println(encoded);
		var decodedL = LevensteinAlg.decode(encoded);
		//System.out.println(decodedL);
		var decoded = MoveToFrontAlg.decode(decodedL, getAlphabet());
		System.out.println(decoded);
		var temp = String.join("", encoded);
		System.out.println(temp.length());
		System.out.println("K = " + calcKbit(input, temp, getAlphabet().size()));
	}
	
	private static List<Character> getAlphabet(){
		String str = UKR;
		//String str = (UKR + UKR.toUpperCase() + SPEC);
		List<Character> chars = new ArrayList<>(); 
		  
        for (char ch : str.toCharArray()) { 
  
            chars.add(ch); 
        } 
  
        return chars; 
	}

	
	private static double calcK(String original, String result, int power) {
		return (customLog(2, power) * original.length()) / result.length();
	}
	
	private static double calcKbit(String original, String result, int power) {
		return (customLog(2, power) * original.length() * 8) / result.length();
	}
	private static double customLog(double base, double logNumber) {
	    return Math.log(logNumber) / Math.log(base);
	}
}
