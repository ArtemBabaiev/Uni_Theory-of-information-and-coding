package edu.toiac.lab1.jar.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import edu.toiac.lab1.models.FrequencyTable;

public class CsvUtils {

	public static void save(String location, FrequencyTable table) throws IOException {
		File file = new File(location);
		Path path = Path.of(location);
		BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);

		String[] headers = new String[] { "letter", "count", "relative" };
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader(headers).build();

		try (final CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {
			table.getDictionary().forEach((letter, count) -> {
				try {
					printer.printRecord(letter, count, count / (double) table.getTotal());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			printer.printRecord("Total", table.getTotal());
		}
	}
}
