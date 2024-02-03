package edu.toiac.lab5;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import edu.toiac.lab3.HuffmanCoding;
import edu.toiac.lab3.HuffmanNode;
import edu.toiac.lab3.ShannonFanoCoding;
import edu.toiac.lab3.ShannonFanoNode;
import edu.toiac.lab4.LevensteinAlg;
import edu.toiac.lab4.MoveToFrontAlg;

public class Lab5Main {
	private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	private static String[] files = new String[] {"text.txt", "text1.txt" ,"text2.txt"};
	public static void main(String[] args) {
		task1();
		//task2();
	}
	
	private static void task1() {
		
		List<Double> ks = new ArrayList<Double>();
		for (String file : files) {
			try (InputStream is = classloader.getResourceAsStream(file)) {
				String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				ks.add(perform(input));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(StringUtils.leftPad("", 25, "*"));
		}
		
		var avg =  ks.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);
		
		System.out.println("Average k = " + avg);
	}
	
	private static void task2() {
		huffman();
		shannon();
		mtfLevenstein();
		
	}
	
	private static void huffman() {
		System.out.println("*****Huffman*****");
		for (String file : files) {
			try (InputStream is = classloader.getResourceAsStream(file)) {
				String message = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				Map<Character, Integer> frequencies = HuffmanCoding.calculateFrequencies(message);

				HuffmanNode root = HuffmanCoding.buildHuffmanTree(frequencies);

				Map<Character, String> huffmanCodes = HuffmanCoding.generateHuffmanCodes(root);

				String encodedMessage = HuffmanCoding.encodeMessage(message, huffmanCodes);
				//System.out.println("I=" +message.length() *8 + "; E="+encodedMessage.length());
				System.out.println(calcK(2, message.length() * 8, encodedMessage.length()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void shannon() {
		System.out.println("*****Shannon-Fano*****");
		for (String file : files) {
			try (InputStream is = classloader.getResourceAsStream(file)) {
				String message = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				Map<Character, Integer> frequencies = ShannonFanoCoding.calculateFrequencies(message);

		        List<ShannonFanoNode> nodes = ShannonFanoCoding.createNodes(frequencies);

		        ShannonFanoNode root = ShannonFanoCoding.buildShannonFanoTree(nodes);

		        ShannonFanoCoding.generateShannonFanoCodes(root);

		        Map<Character, String> shannonFanoCodes = new HashMap<>();
		        ShannonFanoCoding.collectCodes(root, shannonFanoCodes);

		        String encodedMessage = ShannonFanoCoding.encodeMessage(message, shannonFanoCodes);
		        //System.out.println("I=" +message.length() * 8 + "; E="+encodedMessage.length());
				System.out.println(calcK(2, message.length() * 8, encodedMessage.length()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private static void mtfLevenstein() {
		System.out.println("*****Move-to-Front + Levenstein*****");
		List<Double> ks = new ArrayList<Double>();
		for (String file : files) {
			try (InputStream is = classloader.getResourceAsStream(file)) {
				String input = new String(is.readAllBytes(), StandardCharsets.UTF_8);
				var encoded = LevensteinAlg.encode(MoveToFrontAlg.encode(input, getAlphabet()));
				//System.out.println("I=" +input.length() + "; E="+String.join("", encoded).length());
				System.out.println(calcK(2, input.length() * 8, String.join("", encoded).length()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static double perform(String input) {
		var encoded = LzwAlg.encode(input);
		var decoded = LzwAlg.decode(encoded);
		int lOr = input.length()*8;
		int lEn = String.join("", encoded).length();
		System.out.println("Unencoded length: " + lOr);
		System.out.println("Encoded length: " + lEn);
		var k = calcK(2, lOr, lEn);
		System.out.println("K=" + k);
		//System.out.println(encoded.subList(0, 10));
		//System.out.println(decoded.substring(0, 100));
		//System.out.println(input.equals(decoded));
		return k;
	}
	
	private static double calcK(double power, int original, int result) {
		return (customLog(2, power) * original) / result;
	}
	
	private static double customLog(double base, double logNumber) {
	    return Math.log(logNumber) / Math.log(base);
	}
	
	private static List<Character> getAlphabet() {
		List<Character> table = new ArrayList<>();
		for (int i = 0; i <= 255; i++) {
			table.add((char) i);
		}

		return table;
	}
}
