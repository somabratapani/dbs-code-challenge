package com.dbs.test;

import java.io.IOException;
import java.util.List;

import com.dbs.main.Extractor;

public class ExtractorTest {

	public static void main(String[] args) {
		List<String[]> dataFromCSV;
		try {
			dataFromCSV = Extractor.getDataFromCSV("target/inputfile.csv");
			String[] strArr = Extractor.calculateSpreadsheet(dataFromCSV).toArray(new String[Extractor.calculateSpreadsheet(dataFromCSV).size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
