package edu.toiac.lab6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lab6Main {
	static HammingCode hc = new HammingCode();
	public static void main(String[] args) throws Exception {
		//runMain();
		runFail();
	}
	
	private static void runMain() throws Exception {
		List<Integer[]> data = new ArrayList<>();
		data.add(new Integer[] {1,0,1,1,0,1,1,1,0,1,1});
		data.add(new Integer[] {0,1,0,1,1,1,0,1,0,1,0,1,1,0,0,1,1,0,1});
		data.add(new Integer[] {1,0,1,0,1,1,0,1,0,1,0,1,1,1,0,1,1,0,0,1,0,1,1,1,1,0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,1,1,1,0,0,0,1,1,0,1,0,1,0,1,0,1});
		for (Integer[] integers : data) {
			var encoded = hc.encode(integers);
			System.out.println(encoded);
			var decoded = hc.decode(encoded);
			System.out.println(decoded);
			System.out.println("Pen=" + calcP(encoded));
		}
	}
	
	private static void runFail() {
		var encoded = hc.encode(new Integer[] {1,0,0,1,0,0,1,1,0,1,1});
		var modified = new ArrayList<Integer>(encoded);
		modified.set(12, 0);
		modified.set(13, 1);
		try {
			System.out.println(modified);
			var decoded = hc.decode(modified);
			System.out.println(decoded);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static double calcP(List<Integer> data){
		double n = 0;
		int size = data.size();
		for (int i = 0; i < size; i++) {
			if (i == 0 || HammingCode.IsPowerOfTwo(i)) {
				n ++;
			}
		}
		return n/size;
	}
	private static double customLog(double base, double logNumber) {
	    return Math.log(logNumber) / Math.log(base);
	}
}
