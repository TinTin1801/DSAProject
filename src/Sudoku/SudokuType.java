package Sudoku;

public enum SudokuType {
	SIXBYSIX(6, 6, 3, 2, new String[]{"1", "2", "3", "4", "5", "6"}, "Six By Six Game"),
	NINEBYNINE(9, 9, 3, 3, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, "Nine By Nine Game"),
	TWELVEBYTWELVE(12, 12, 4, 3, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C"}, "Twelve By Twelve Game");


	private final int rows;
	private final int columns;
	private final int boxWidth;
	private final int boxHeight;
	private String[] validValues;
	private String desc;

	private SudokuType(int rows, int columns, int boxWidth, int boxHeight, String[] values, String desc) {
		this.rows = rows;
		this.columns = columns;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.validValues = values;
		this.desc = desc;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getBoxWidth() {
		return boxWidth;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public String[] getValidValues() {
		return validValues;
	}

	public String toStrings() {
		return desc;
	}

}
