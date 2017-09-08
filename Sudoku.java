import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Sudoku extends JPanel {

	  public static void main(String[] args) {
	    Field field = new Field();
	    field.fromFile("src/test1.txt");
	    try {
	      solve(field, 0, 0);    
	    } catch (SolvedException e) {}
	    
	  }
	  
	  public static void solve(Field f, int i, int j) throws SolvedException {
		  
		  int SIZE = Field.SIZE;
		  int val = 1;
		  	  
		  // if first cell is empty, then call tryValue with val = 1 and check if its a valid value.
		  // if its valid, place the value in the cell, do a recursive call on the function and continue.
		  // if its not, iterate val with one, set the cell to empty, and try again, as long as the number is less than 9.

		  // But if the cell has a value other than empty, iterate i with one, and do it all again in the next row
		  // when i reaches 8 (all rows done), we iterate j with one (next colum), and set i = 0 (starts from the top).
		  // when 8th colum is reached, print out the Field f (the solution).
  
		  do {
				 if(f.model[i][j] == 0) {
					 
				 do {
					 
					 if(f.tryValue(val, i, j)) {
						 f.model[i][j] = val;
						 solve(f, i, j);					  
					  }
					 val++;
					 
				 } while(val <= SIZE);
				 
				 f.clear(i, j);
				 return; 
				 
				 } 
				 else if(i < 8) {
					 
					  i++;
				 }
				 else if((i == 8) && (j < 8))  {
					  
					  i = 0;
					  j++;
				  }
				  else {
					  
					  System.out.println(f);
					  
					  // converting the Field f-solution to strings
					  String solution = f.toString();
					  String solutionWithGrid = f.toString();
					  
					  // getting rid of everything but the numbers
					  solution = solution.replace("-", "").replace("+", "").replace("|", "").replace("\n", "").replace(" ", "");
					  
					  // replacing '\n' with '\r\n' so the FileHandler can output correctly. 
					  solutionWithGrid = solutionWithGrid.replace("\n", "\r\n");
					  
					  // creating arrays
					  String[] solutionArray1D = solution.split("");
					  String solutionArray2D[][] = new String[9][9];
					  
					  for(int n = 0; n < 9; n++) {
						    for(int m = 0; m < 9; m++) {
						    	solutionArray2D[n][m] = solutionArray1D[(n*9)+m];
						    }
					  };
						
					  SudokuGUI.getAnswer = solutionArray2D;
					  SudokuGUI.getAnswerWithGrid = solutionWithGrid;
					  break;
				  }
				 
			 } while(true);	
	}

}