package edu.toiac.lab3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import edu.toiac.lab1.jar.utils.EntropyUtils;
import edu.toiac.lab1.models.FrequencyTable;

public class Lab3Main {
	private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	private static String directory = "C:\\MyData\\Workspaces\\ToIaC\\labs\\lab3";
	
	public static void main(String[] args) {
		try (InputStream is = classloader.getResourceAsStream("text.txt")) {
			String message = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			huffmanCoding(message);
			System.out.println("-".repeat(25));
			shannonCoding(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void huffmanCoding(String message) throws IOException {
		System.out.println("Huffman");
		Map<Character, Integer> frequencies = HuffmanCoding.calculateFrequencies(message);

		HuffmanNode root = HuffmanCoding.buildHuffmanTree(frequencies);

		Map<Character, String> huffmanCodes = HuffmanCoding.generateHuffmanCodes(root);

		String encodedMessage = HuffmanCoding.encodeMessage(message, huffmanCodes);
		//System.out.println("Encoded message: " + encodedMessage);
		FileUtils.writeStringToFile(new File(directory + "/huffman.txt"), encodedMessage, Charset.forName("UTF-8"));
		FileUtils.writeStringToFile(new File(directory + "/huffman-codes.txt"), huffmanCodes.toString(), Charset.forName("UTF-8"));
		System.out.println("Encoded length: " + encodedMessage.length());
		
		String decodedMessage = HuffmanCoding.decodeMessage(encodedMessage, root);
		//System.out.println("Decoded message: " + decodedMessage);
		System.out.println("Decoded length: " + decodedMessage.length());

		Map<Character, Long> convertedFrequency = new HashMap<Character, Long>();
		for (var pair : frequencies.entrySet()) {
			convertedFrequency.put(pair.getKey(), Long.valueOf(pair.getValue()));
		}
		FrequencyTable frTable = new FrequencyTable();
		frTable.setDictionary(convertedFrequency);
		frTable.calcTotal();

		var maxEntropy = EntropyUtils.calcMaxEntropy(frTable);
		var entropy = EntropyUtils.calcEntropy(frTable);
		var lmid = calcL(frTable, huffmanCodes);
		
		System.out.println("Середня кількість елементарних символів у кодовому слові: " + lmid);
		System.out.println("Коефіцієнт статистичного стиску: " + (maxEntropy / lmid));
		System.out.println("Коефіцієнт відносної ефективності: " + (entropy / lmid));
		
		//System.out.println(huffmanCodes.toString());
	}
	
	private static void shannonCoding(String message) throws IOException {
		System.out.println("Shannon fano");
		Map<Character, Integer> frequencies = ShannonFanoCoding.calculateFrequencies(message);

        List<ShannonFanoNode> nodes = ShannonFanoCoding.createNodes(frequencies);

        ShannonFanoNode root = ShannonFanoCoding.buildShannonFanoTree(nodes);

        ShannonFanoCoding.generateShannonFanoCodes(root);

        Map<Character, String> shannonFanoCodes = new HashMap<>();
        ShannonFanoCoding.collectCodes(root, shannonFanoCodes);

        String encodedMessage = ShannonFanoCoding.encodeMessage(message, shannonFanoCodes);
        //System.out.println("Encoded message: " + encodedMessage);
        System.out.println("Encoded length: " + encodedMessage.length());
        
        FileUtils.writeStringToFile(new File(directory + "/shannon.txt"), encodedMessage, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(directory + "/shannon-codes.txt"), shannonFanoCodes.toString(), Charset.forName("UTF-8"));

        String decodedMessage = ShannonFanoCoding.decodeMessage(encodedMessage, root);
        // System.out.println("Decoded message: " + decodedMessage);
      	System.out.println("Decoded length: " + decodedMessage.length());
        
        Map<Character, Long> convertedFrequency = new HashMap<Character, Long>();
		for (var pair : frequencies.entrySet()) {
			convertedFrequency.put(pair.getKey(), Long.valueOf(pair.getValue()));
		}
		FrequencyTable frTable = new FrequencyTable();
		frTable.setDictionary(convertedFrequency);
		frTable.calcTotal();

		var maxEntropy = EntropyUtils.calcMaxEntropy(frTable);
		var entropy = EntropyUtils.calcEntropy(frTable);
		var lmid = calcL(frTable, shannonFanoCodes);
		
		System.out.println("Середня кількість елементарних символів у кодовому слові: " + lmid);
		System.out.println("Коефіцієнт статистичного стиску: " + (maxEntropy / lmid));
		System.out.println("Коефіцієнт відносної ефективності: " + (entropy / lmid));
	}
	
	private static double calcL(FrequencyTable frTable, Map<Character, String> huffmanCodes) {
		double sum = 0L;
		double total = frTable.getTotal();
		for (var code : huffmanCodes.entrySet()) {
			var probability = frTable.getDictionary().get(code.getKey()) / total;
			sum += probability * code.getValue().length();
		}
		return sum;
	}
}
