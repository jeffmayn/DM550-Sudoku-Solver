import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class SudokuGUI extends Sudoku {

	public static String[][] getAnswer;
	static Object[][] solutionData = new Object [9][9];
	public static String getAnswerWithGrid;

	public static void Frame(String[] emptyCol, Object[][] loadData, Object[][] solutionData, File path) {
	
		// frame and panel
		JFrame frame = new JFrame("Sudoku Solver");
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
		
		// frame settings
		frame.setSize(270, 450);
		frame.setResizable(false);
		frame.add(panel);		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
			
		// buttons
		JButton solve = new JButton("        Start        ");
		JButton save = new JButton("    Save file    ");
		JButton load = new JButton("    Load file    ");
		
		// labels
		JLabel unsolved = new JLabel("         Unsolved         ");
		JLabel solution = new JLabel("          Solution          ");

		// add load button to panel, and add actionlistener
		panel.add(load);
		load.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileHandler load = new FileHandler();
				try {
					load.openFile(null);
					frame.dispose();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}				
			}			
		});
				
		// if nothing in first cell of solution-array (then JTable is empty), set save-button enable to false, else true.
		if (solutionData[0][0] == null) {
			
			panel.add(save);
			save.setEnabled(false);
		}
		else {
			panel.add(save);
			save.addActionListener(new ActionListener() {
						
				@Override
				public void actionPerformed(ActionEvent e) {
					
					FileHandler save = new FileHandler();
					save.saveFile(getAnswerWithGrid);				
				}			
			});
			save.setEnabled(true);
		}
		
		// add other components
		panel.add(unsolved);		
		paintInputTable(loadData, emptyCol, panel);
		
		panel.add(solution);
		paintOutputTable(solutionData, emptyCol, panel);
		
		// if nothing in first cell of loadedData-array (then JTable is empty), set solve-button enable to false, else
		// set solve-button to true, call solver from Field, and set save-button to true.
		if (loadData[0][0] == null || (loadData[0][0] != null && solutionData[0][0] != null)) {
			
			panel.add(solve);
			solve.setEnabled(false);
		}
		
		else {
			panel.add(solve);
			solve.setEnabled(true);		
			solve.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e2) {
					
					solver(path, loadData);
					frame.dispose();
				}			
			});
		}
		frame.setVisible(true);	
	}
	
	public static void main(String[] args) {
	
		String[] emptyCol = {"","","","","","","","","",};
		Object[][] loadData = new Object [9][9];
		File path = null;
		
		// call frame-method with emptyColums to jtable, empty 2D-string to loadData,
		// empty 2D-string to solutionsData, and empty path.
		Frame(emptyCol, loadData, solutionData, path);		
	}
	
	public static void paintInputTable(Object[][] loadData, String[] emptyCol, JPanel panel) {
	
		JTable input = new JTable(loadData, emptyCol);	
			input.setTableHeader(null);
			input.setPreferredScrollableViewportSize(new Dimension(150, 144));
			input.setFillsViewportHeight(true);
			
		JScrollPane jps = new JScrollPane(input);
		panel.add(jps);			
	}
	
	public static void paintOutputTable(Object[][] solutionData, String[] emptyCol, JPanel panel) {

		JTable output = new JTable(solutionData, emptyCol);
			output.setTableHeader(null);
			output.setPreferredScrollableViewportSize(new Dimension(150, 144));
			output.setFillsViewportHeight(true);	
			
		JScrollPane jps_2 = new JScrollPane(output);	
		panel.add(jps_2);		
	}
	
	public static void solver(File result, Object[][] loadData) {
			
			String path = result.toString();		
			Field field = new Field();
			
			    field.fromFile(path);
			    try {
			      solve(field, 0, 0);
				 
			    String[] emptyCol = {"","","","","","","","","",};    
			    Frame(emptyCol, loadData, getAnswer, null);
	      
			    } catch (SolvedException e) {}
	}
}