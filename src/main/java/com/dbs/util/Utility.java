package com.dbs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dbs.model.Cell;

public class Utility {

	public static Map<String, Cell> createSpreadsheet(List<String[]> content) {
		int rows = content.size();
		int columns = content.get(0).length;
		Map<String, Cell> map = new LinkedHashMap<>(rows * columns);
		String row;
		for (int r = 1; r < rows + 1; r++) {
			row = getCharForNumber(r);
			for (int c = 0; c < columns; c++) {
				map.put(row + c, createCell(map, content.get(r-1)[c]));
			}
		}
		return map;
	}

	private static Cell createCell(Map<String, Cell> spreadsheet, String expression) {
		List<String> expressionAsList = Arrays.asList(expression.charAt(0) == '='
				? new InToPost(expression.substring(1, expression.length())).doTrans().trim().split(" ")
				: expression.split(" "));
		return new Cell(spreadsheet, expressionAsList);
	}

	private static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	public static List<String[]> splitArray(String[] array, int max) {

		int x = array.length / max;
		int r = (array.length % max); // remainder

		int lower = 0;
		int upper = 0;

		List<String[]> list = new ArrayList<String[]>();

		int i = 0;

		for (i = 0; i < x; i++) {
			upper += max;
			list.add(Arrays.copyOfRange(array, lower, upper));
			lower = upper;
		}

		if (r > 0) {
			list.add(Arrays.copyOfRange(array, lower, (lower + r)));
		}

		return list;
	}
}
