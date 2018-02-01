package com.dbs.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dbs.util.ReversePolishNotationUtil;

public class Cell {

	private Map<String, Cell> spreadsheet;
	private List<String> expression;
	private Double result;

	public Cell(Map<String, Cell> spreadsheet, List<String> expression) {
		this.spreadsheet = spreadsheet;
		this.expression = expression;
		this.result = null;
	}
	
	public String getResult() {
		Double calculatedResult = ReversePolishNotationUtil.calculate(prepareExpression());
		return (result != null) ? result.toString() : calculatedResult.toString();
	}

	private List<String> prepareExpression() {
		List<String> preparedExpression = expression.parallelStream().map(item -> {
			if (spreadsheet.containsKey(item)) {
				item = String.valueOf(spreadsheet.get(item).getResult());
			}
			return item;
		}).collect(Collectors.toList());
		return preparedExpression;
	}
}
