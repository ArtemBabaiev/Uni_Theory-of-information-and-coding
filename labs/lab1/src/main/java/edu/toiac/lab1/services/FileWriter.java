package edu.toiac.lab1.services;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class FileWriter {
	public static void writeToFile(String content, String destination){
		try (FileOutputStream outputStream = new FileOutputStream(destination)) {
			byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
		    outputStream.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
