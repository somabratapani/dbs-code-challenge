package com.dbs.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dbs.model.Cell;
import com.dbs.util.Utility;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Extractor {
	
	public static void main(String[] args) {
		
		//String currentDirectory = new File("").getAbsolutePath();
		List<String[]> dataFromCSV;
		try {
			if (!args[0].equals("-i")) {
				throw new IllegalArgumentException();
			}
			else if (!args[1].equals("inputfile.csv")) {
				throw new IllegalArgumentException();
			}
			else if (!args[2].equals("-o")) {
				throw new IllegalArgumentException();
			}
			else if (!args[3].equals("outputfile.csv")) {
				throw new IllegalArgumentException();
			} else {
				System.out.println("Job started: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				dataFromCSV = getDataFromCSV(args[1]);//get the input file name from commandline	
				String[] strArr = calculateSpreadsheet(dataFromCSV).toArray(new String[calculateSpreadsheet(dataFromCSV).size()]);
				List<String[]> list = Utility.splitArray(strArr, dataFromCSV.get(0).length);
				
				CSVWriter writer = new CSVWriter(new FileWriter(args[3]));//new CSVWriter(new FileWriter(currentDirectory + "\\output\\outputfile.csv"));
				writer.writeAll(list);			
				writer.close();
				System.out.println("Job ended: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				System.out.println("Job run successful. Result written to outputfile.csv");
			}
			
		} catch (IllegalArgumentException iae) {
			System.err.println("Invalid argument specified \nThe program should run with the following command: \njava –jar spreadsheet-jar-with-dependencies.jar –i inputfile.csv –o outputfile.csv");
			System.exit(1);
		} catch (FileNotFoundException ffe) {
			System.err.println("The system cannot find the file specified.\n" + ffe);
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Wrong data input - possibly cyclic reference" + e);
			System.exit(1);
		}
	}
	
	public static List<String> calculateSpreadsheet(List<String[]> data) {
		Map<String, Cell> spreadsheet = Utility.createSpreadsheet(data);
		List<String> calculatedSpreadsheet = spreadsheet.values().parallelStream().map(item -> item.getResult())
				.collect(Collectors.toList());
		return calculatedSpreadsheet;
	}
	
	public static List<String[]> getDataFromCSV(String inputFile) throws IOException {
		
		CSVReader csvReader = new CSVReader(new FileReader(inputFile));
		List<String[]> dataFromCSV = null;
		try {
			dataFromCSV = csvReader.readAll();
			return dataFromCSV;
		} catch (IOException e1) {
			System.err.println("Error while reading data from file");
			System.exit(1);
		} finally {
			csvReader.close();
		}
		return dataFromCSV;
	}
	
}
