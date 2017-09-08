import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {
	
	static Object[][] solutionData = new Object [9][9];
	
	public static void openFile(String title) throws FileNotFoundException {
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
	
		File filePath = null;
		JFileChooser chooser = new JFileChooser(new File("."));
		 
		    if (title != null)
		    	chooser.setDialogTitle(title);
		    	int retVal = chooser.showOpenDialog(null);
		    	
		    	if(retVal == JFileChooser.APPROVE_OPTION) {
			    	filePath = chooser.getSelectedFile();
					      
				Scanner input = new Scanner(filePath);
				Object[][] loadData = new Object [9][9];
						 	
					while(input.hasNext()) {
							    	
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 9; j++) {
							    			
						 loadData[i][j] = input.next();
							    }
							    System.out.println();
							   }					 		
						String[] emptyCol = {"","","","","","","","","",};
					SudokuGUI.Frame(emptyCol, loadData, solutionData, filePath);  
				}
			input.close();
		    		}   
	}

	public void saveFile(String getAnswerWithGrid) {
	
	JFileChooser fc = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
	fc.setFileFilter(filter);
	int rval = fc.showSaveDialog(fc);
	
		if (rval == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			try {
				
				
				BufferedWriter out = new BufferedWriter(new FileWriter(file + ".txt"));
				out.write(getAnswerWithGrid);
				out.flush();
				out.close();
			} catch(IOException e) {
				// error
			  }
		} 		
	}
}