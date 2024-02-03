package edu.toiac.lab3;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {
	public static Map<Character, Integer> calculateFrequencies(String message) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : message.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencies) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode mergedNode = new HuffmanNode('\0', left.frequency + right.frequency);
            mergedNode.left = left;
            mergedNode.right = right;

            priorityQueue.add(mergedNode);
        }

        return priorityQueue.poll();
    }

    public static Map<Character, String> generateHuffmanCodes(HuffmanNode root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodesRecursive(root, "", huffmanCodes);
        return huffmanCodes;
    }

    public static void generateHuffmanCodesRecursive(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) return;

        if (node.data != '\0') {
            huffmanCodes.put(node.data, code);
        }

        generateHuffmanCodesRecursive(node.left, code + "0", huffmanCodes);
        generateHuffmanCodesRecursive(node.right, code + "1", huffmanCodes);
    }

    public static String encodeMessage(String message, Map<Character, String> huffmanCodes) {
        StringBuilder encodedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            encodedMessage.append(huffmanCodes.get(c));
        }
        return encodedMessage.toString();
    }

    public static String decodeMessage(String encodedMessage, HuffmanNode root) {
        StringBuilder decodedMessage = new StringBuilder();
        HuffmanNode current = root;

        for (char bit : encodedMessage.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }

            if (current.data != '\0') {
                decodedMessage.append(current.data);
                current = root;
            }
        }

        return decodedMessage.toString();
    }
}
