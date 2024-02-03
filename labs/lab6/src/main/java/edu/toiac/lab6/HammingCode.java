package edu.toiac.lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class HammingCode {
	public List<Integer> encode(Integer[] data) {
		List<HammingCell> cells = new ArrayList<>();
		cells.add(new HammingCell("00", null, BitType.PARITY));
		int toInsertPosition = 1;
		for (int i = 0; i < data.length; i++) {
			while (IsPowerOfTwo(toInsertPosition)) {
				cells.add(toInsertPosition,
						new HammingCell(Integer.toBinaryString(toInsertPosition), 0, BitType.CONTROL));
				toInsertPosition++;
			}
			cells.add(toInsertPosition,
					new HammingCell(Integer.toBinaryString(toInsertPosition), data[i], BitType.DATA));
			toInsertPosition++;
		}
		int binLength = Integer.toBinaryString(cells.size() - 1).length();
		cells.stream().forEach(c -> c.bin = StringUtils.leftPad(c.bin, binLength, "0"));

		var dataBits = cells.stream().filter(c -> c.type == BitType.DATA).toList();
		var conBits = cells.stream().filter(c -> c.type == BitType.CONTROL).toList();
		for (HammingCell bit : conBits) {
			var oneIndex = bit.bin.indexOf("1");
			dataBits.stream().filter(d -> d.bin.charAt(oneIndex) == '1').forEach(d -> bit.data = bit.data ^ d.data);
		}

		cells.get(0).data = cells.stream().filter(c -> c.type != BitType.PARITY).mapToInt(c -> c.data).sum() % 2;

		return cells.stream().map(c -> c.data).toList();
	}

	public List<Integer> decode(List<Integer> data) throws Exception {
		List<HammingCell> cells = new ArrayList<HammingCell>();
		
		for (int i = 0; i < data.size(); i++) {
			var cell = new HammingCell();
			cell.data = data.get(i);
			cell.bin = Integer.toBinaryString(i);
			if (i == 0) {
				cell.type = BitType.PARITY;
			} else if (IsPowerOfTwo(i)) {
				cell.type = BitType.CONTROL;
			} else {
				cell.type = BitType.DATA;
			}
			cells.add(cell);
		}
		int binLength = Integer.toBinaryString(cells.size() - 1).length();
		cells.stream().forEach(c -> c.bin = StringUtils.leftPad(c.bin, binLength, "0"));
		
		int[] synd = this.calcSyndrome(cells);
		int syndPosition = Integer.parseInt(Arrays.stream(synd).mapToObj(s -> s + "").collect(Collectors.joining("")),
				2);
		int totalParity = calcTotalParity(cells);

		if (totalParity > 0 || syndPosition > 0) {
			System.out.println("Error detected. Trying to repare");
			cells.get(syndPosition).data ^= 1;
		}
		synd = this.calcSyndrome(cells);
		syndPosition = Integer.parseInt(Arrays.stream(synd).mapToObj(s -> s + "").collect(Collectors.joining("")), 2);
		totalParity = calcTotalParity(cells);
		if (totalParity > 0 || syndPosition > 0) {
			throw new Exception("Input contains more than one mistake");
		}
		return cells.stream().filter(c -> c.type == BitType.DATA).map(c -> c.data).toList();
	}

	private int[] calcSyndrome(List<HammingCell> cells) {
		var positions = (int) cells.stream().filter(c -> c.type == BitType.CONTROL).count();
		int[] syndroms = new int[positions];
		for (int i = 0; i < positions; i++) {
			int bitIndex = i;
			cells.stream().filter(
					c -> c.bin.charAt(bitIndex) == '1'
					).forEach(c -> syndroms[bitIndex] ^= c.data);
		}
		return syndroms;
	}

	private int calcTotalParity(List<HammingCell> cells) {
		int parity = 0;
		for (var cell : cells) {
			parity ^= cell.data;
		}

		return parity;
	}

	public static boolean IsPowerOfTwo(int x) {
		return (x != 0) && ((x & (x - 1)) == 0);
	}
}
