package edu.toiac.lab2;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import edu.toiac.lab1.models.FrequencyTable;
import edu.toiac.lab1.services.TextProcessor;

public class Lab2Main {

	private static ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	private static TextProcessor txtProcessor = new TextProcessor();
	
	private static Double Vk = 182_480.0522;
	private static Double Hx = 4.596949076118038;
	public static void main(String[] args) {
		try (InputStream is = classloader.getResourceAsStream("text.txt")) {
			String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			FrequencyTable table = txtProcessor.calculateFrequencyForText(text);
			String cleanText = txtProcessor.cleanText(text);
			
			var Hyx = EntropyUtils.getEntropyOfInterference(cleanText, table);
			var Hy = EntropyUtils.getEntropyOfExit(table);
			var speed = EntropyUtils.getSpeedOfInformationTransmission(Vk, Hx, Hyx);
			var bandwith = EntropyUtils.getBandwith(Vk, Hy, Hyx);
			var k = EntropyUtils.getK(bandwith, speed);
			
			System.out.println("Ентропія входу каналу зв’язку: " + Hx);
			System.out.println("Ентропія виходу каналу зв’язку: " + Hy);
			System.out.println("Ентропію завад каналу зв’язку: " + Hyx);
			System.out.println("Швидкість передавання каналом зв’язку: " + speed);
			System.out.println("Пропускна здатність каналу зв’язку: " + bandwith);
			System.out.println("Коефіцієнт використання каналу: " + k);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
