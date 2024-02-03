package edu.toiac.lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShannonFanoCoding {
	public static Map<Character, Integer> calculateFrequencies(String message) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : message.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }

    public static List<ShannonFanoNode> createNodes(Map<Character, Integer> frequencies) {
        List<ShannonFanoNode> nodes = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            nodes.add(new ShannonFanoNode(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    public static ShannonFanoNode buildShannonFanoTree(List<ShannonFanoNode> nodes) {
        if (nodes.size() == 1) {
            return nodes.get(0);
        }

        Collections.sort(nodes);

        int totalFrequency = nodes.stream().mapToInt(node -> node.frequency).sum();
        int halfFrequency = 0;
        int index = 0;

        for (int i = 0; i < nodes.size(); i++) {
            halfFrequency += nodes.get(i).frequency;
            if (halfFrequency >= totalFrequency / 2) {
                index = i;
                break;
            }
        }

        List<ShannonFanoNode> leftNodes = nodes.subList(0, index + 1);
        List<ShannonFanoNode> rightNodes = nodes.subList(index + 1, nodes.size());

        ShannonFanoNode leftSubtree = buildShannonFanoTree(leftNodes);
        ShannonFanoNode rightSubtree = buildShannonFanoTree(rightNodes);

        ShannonFanoNode root = new ShannonFanoNode('\0', totalFrequency);
        root.left = leftSubtree;
        root.right = rightSubtree;

        return root;
    }

    public static void generateShannonFanoCodes(ShannonFanoNode node) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            node.left.code = node.code + "0";
            generateShannonFanoCodes(node.left);
        }

        if (node.right != null) {
            node.right.code = node.code + "1";
            generateShannonFanoCodes(node.right);
        }
    }

    public static void collectCodes(ShannonFanoNode node, Map<Character, String> shannonFanoCodes) {
        if (node == null) {
            return;
        }

        if (node.data != '\0') {
            shannonFanoCodes.put(node.data, node.code);
        }

        collectCodes(node.left, shannonFanoCodes);
        collectCodes(node.right, shannonFanoCodes);
    }

    public static String encodeMessage(String message, Map<Character, String> shannonFanoCodes) {
        StringBuilder encodedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            encodedMessage.append(shannonFanoCodes.get(c));
        }
        return encodedMessage.toString();
    }

    public static String decodeMessage(String encodedMessage, ShannonFanoNode root) {
        StringBuilder decodedMessage = new StringBuilder();
        ShannonFanoNode current = root;

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
