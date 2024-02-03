package edu.toiac.lab1.jar;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import edu.toiac.lab1.jar.utils.CsvUtils;
import edu.toiac.lab1.jar.utils.EntropyUtils;
import edu.toiac.lab1.models.FrequencyTable;
import edu.toiac.lab1.services.TextProcessor;

public class LabMain {

	private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	private static TextProcessor txtProcessor = new TextProcessor();
	private static String output = "C:/MyData/Workspaces/ToIaC/lab1/freq-report.csv";

	public static void main(String[] args) throws IOException {
		try (InputStream is = classloader.getResourceAsStream("text.txt")) {
			String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			FrequencyTable table = txtProcessor.calculateFrequencyForText(text);
			CsvUtils.save(output, table);
			System.out.println("Entropy value = " + EntropyUtils.calcEntropy(table));
			System.out.println("Max Entropy value = " + EntropyUtils.calcMaxEntropy(table));
			System.out
					.println("Надлишковість джерела повідомлень = " + EntropyUtils.calcMessageSourceRedundancy(table));
			List<Entry<Character, Long>> sorted = table.getDictionary().entrySet().stream()
					.sorted((o1, o2) -> o1.getValue().compareTo(o2.getValue())).collect(Collectors.toList());
			var max = sorted.get(sorted.size() - 1);
			var min = sorted.get(0);
			var middle = sorted.get(sorted.size() / 2);
			System.out.println("Кількість інформаціх для максимального " + max.getKey() + " = "
					+ EntropyUtils.calcAmountOfInformation(max.getValue() / (double) table.getTotal()));
			System.out.println("Кількість інформаціх для мінімального " + min.getKey() + " = "
					+ EntropyUtils.calcAmountOfInformation(min.getValue() / (double) table.getTotal()));
			System.out.println("Кількість інформаціх для середнього " + middle.getKey() + " = "
					+ EntropyUtils.calcAmountOfInformation(middle.getValue() / (double) table.getTotal()));

			FrequencyTable nameTable = txtProcessor.calculateFrequencyForText("Бабаєв Артем Ілліч");
			System.out.println(
					"Кількість інформації у повідомлені = " + EntropyUtils.calcAmoutOfInformationInMessage(nameTable));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
