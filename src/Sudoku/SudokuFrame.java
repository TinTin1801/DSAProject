package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	private JPanel buttonSelectionPanel;
	private Sudoku.SudokuPanel sPanel;

	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800, 600));

		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");
		JMenu newGame = new JMenu("New Game");
		JMenuItem sixBySixGame = new JMenuItem("Six By Six Game");
		sixBySixGame.addActionListener(new NewListener(SudokuType.SIXBYSIX,30));
		JMenuItem nineByNineGame = new JMenuItem("Nine By Nine Game");
		nineByNineGame.addActionListener(new NewListener(SudokuType.NINEBYNINE,26));
		JMenuItem twelveByTwelveGame = new JMenuItem("Twelve By Twelve Game");
		twelveByTwelveGame.addActionListener(new NewListener(SudokuType.TWELVEBYTWELVE,20));


		newGame.add(sixBySixGame);
		newGame.add(nineByNineGame);
		newGame.add(twelveByTwelveGame);

		file.add(newGame);
		menuBar.add(file);
		this.setJMenuBar(menuBar);

		JPanel windowPanel = new JPanel();
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800, 600));

		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90, 500));

		sPanel = new Sudoku.SudokuPanel();

		windowPanel.add(sPanel);
		windowPanel.add(buttonSelectionPanel);
		this.add(windowPanel);

		rebuildInterface(SudokuType.NINEBYNINE,26);
	}

	public void rebuildInterface(SudokuType type, int fontSize) {
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(type);
		sPanel.newSudoku(generatedPuzzle);
		sPanel.setFontSize(fontSize);
		buttonSelectionPanel.removeAll();
		for (String value : generatedPuzzle.getValidValues()){
			JButton button = new JButton(value);
			button.setPreferredSize(new Dimension(40,40));
			button.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(button);
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}

	private class NewListener implements ActionListener {
		private SudokuType type;
		private int fontSize;

		public NewListener(SudokuType type, int fontSize) {
			this.type = type;
			this.fontSize = fontSize;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			rebuildInterface(type, fontSize);
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}

}
