package edu.toiac.lab3;

public class ShannonFanoNode implements Comparable<ShannonFanoNode>{
    char data;
    int frequency;
    String code;

    ShannonFanoNode left, right;

    public ShannonFanoNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        left = right = null;
        code = "";
    }
    @Override
    public int compareTo(ShannonFanoNode node) {
        return Integer.compare(node.frequency, this.frequency);
    }
}
