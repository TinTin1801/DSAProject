package Sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class SudokuPanel  extends JPanel {

	private SudokuPuzzle puzzle;
	private int curSelectedCol;
	private int curSelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;

	public SudokuPanel() {
		this.setPreferredSize(new Dimension(500, 500));
		this.addMouseListener(new MouseAdapter() );
		this.puzzle = puzzle;
		curSelectedCol = -1;
		curSelectedRow = -1;
		usedHeight = 0;
		usedWidth = 0;
		fontSize = 26;
	}

	public void newSudoku(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);

		int slotWidth = this.getWidth()/puzzle.getNumColumns();
		int slotHeight = this.getHeight()/puzzle.getNumRows();

		usedWidth = (this.getWidth()/puzzle.getNumColumns()*puzzle.getNumColumns());
		usedHeight = (this.getHeight()/puzzle.getNumRows()*puzzle.getNumRows());

		g2d.fillRect(0, 0,usedWidth,usedHeight);

		g2d.setColor(new Color(0.0f,0.0f,0.0f));
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}

		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}

		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		if(curSelectedCol != -1 && curSelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(curSelectedCol * slotWidth,curSelectedRow * slotHeight,slotWidth,slotHeight);
		}
	}
	public void messageFromNumActionListener(String buttonValue) {
		if(curSelectedCol != -1 && curSelectedRow != -1) {
			puzzle.makeMove(curSelectedRow, curSelectedCol, buttonValue, true);
			repaint();
		}
	}

	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromNumActionListener(((JButton) e.getSource()).getText());
		}
	}

	public void clearSelectedSlot() {
		if (curSelectedCol != -1 && curSelectedRow != -1) {
			if (puzzle.isSlotMutable(curSelectedRow, curSelectedCol)) {
				puzzle.makeSlotEmpty(curSelectedRow, curSelectedCol);
				repaint();
			} else {
				JOptionPane.showMessageDialog(this,
						"This cell can not be deleted.",
						"Clear Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this,
					"Please select a cell to delete.",
					"Clear Error",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private class MouseAdapter extends MouseInputAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				int slotWidth = usedWidth/puzzle.getNumColumns();
				int slotHeigth = usedHeight/puzzle.getNumRows();
				curSelectedCol = e.getX()/slotWidth;
				curSelectedRow = e.getY()/slotHeigth;
				e.getComponent().repaint();
			}
		}
	}
}
