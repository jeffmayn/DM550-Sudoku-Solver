import java.io.*;
import java.util.*;

/**
 * Abstract Data Type for Sudoku playing field
 */
public class Field {

  public static final int SIZE = 9;

  public int model[][] ;

  public Field() {
    // make new array of size SIZExSIZE
    this.model = new int[SIZE][SIZE];
    // initialize with empty cells
    init(SIZE-1, SIZE-1);
  }

  private void init(int i, int j) {
    if (i < 0) {
       // all rows done!
    } else if (j < 0) {
      // this row done - go to next!
      init(i-1, SIZE-1);
    } else {
      this.clear(i,j);
      init(i, j-1);
    }
  }

  public void fromFile(String fileName) {
    try {
      Scanner sc = new Scanner(new File(fileName));
      fromScanner(sc, 0, 0);
    } catch (FileNotFoundException e) {
      System.out.println("Invalid path for .txt file");
    }
  }

  private void fromScanner(Scanner sc, int i, int j) {
    if (i >= SIZE) {
      // all rows done!
    } else if (j >= SIZE) {
      // this row done - go to next!
      fromScanner(sc, i+1, 0);
    } else {
      try {
        int val = Integer.parseInt(sc.next());
        this.model[i][j] = val;
      } catch (NumberFormatException e) {
        // skip this cell
      }
      fromScanner(sc, i, j+1);
    }
  }

  public String toString() {
    StringBuffer res = new StringBuffer();
    for (int i = 0; i < SIZE; i++) {
      if (i % 3 == 0) {
        res.append("+-------+-------+-------+\n");
      }
      for (int j = 0; j < SIZE; j++) {
        if (j % 3 == 0) {
          res.append("| ");
        }
        int val = this.model[i][j];
        res.append(val > 0 ? val+" " : "  ");
      }
      res.append("|\n");
    }
    res.append("+-------+-------+-------+");
    return res.toString();
  }

  /** returns false if the value val cannot be placed at
   *  row i and column j. returns true and sets the cell
   *  to val otherwise.
   */
  public boolean tryValue(int val, int i, int j) {
    if (!checkRow(val, i)) {
      return false;
    }
    if (!checkCol(val, j)) {
      return false;
    }
    if (!checkBox(val, i, j)) {
      return false;
    }
    this.model[i][j] = val;
    return true;
  }

  /** checks if the cell at row i and column j is empty,
   *  i.e., whether it contains 0
   */
  public boolean isEmpty(int i, int j) {
	  
	  for(int n = 0; n < SIZE; n++) {
		  for (int m = 0; m < SIZE; m++) {
			  if(model[i][j] == 0) {
				 	return true;
			  }
		  }
	  }  
	return false;		
  }

  /** sets the cell at row i and column j to be empty, i.e.,
   *  to be 0
   */
  public void clear(int i, int j) {
	  
			  model[i][j] = 0;
  }

  /** checks if val is an acceptable value for the row i */
  private boolean checkRow(int val, int i) {
	 
	  for(int n = 0; n < SIZE; n++) {
		  if(model[i][n] == val) {
			  return false;
		  }
	  }
	return true;	
  }

  /** checks if val is an acceptable value for the column j */
  private boolean checkCol(int val, int j) {
	  
	  for(int n = 0; n < SIZE; n++) {
		 if(model[n][j] == val) {
			 return false;
		 }
	  }
	return true;	
  }

  /** checks if val is an acceptable value for the box around
   *  the cell at row i and column j
   */
  private boolean checkBox(int val, int i, int j) {
    
	  int iLow, iTop, jLow, jTop;
	  	
	  	if((i < 3) && (i >= 0)) {
	  		iLow = 0;
	  		iTop = 3;
	  	}
		  	else if((i < 6) && (i >= 3)) {
		  		iLow = 3;
		  		iTop = 6;
		  	}
			  	else {
			  		iLow = 6;
			  		iTop = 9;
			  	}
	
	  	if((j < 3) && (j >= 0)) {
	  		jLow = 0;
	  		jTop = 3;
	  	}
		  	else if((j < 6) && (j >= 3)) {
		  		jLow = 3;
		  		jTop = 6;
		  	}
			  	else {
			  		jLow = 6;
			  		jTop = 9;
			  	}
	  		  	
	  	for(int n = iLow; n < iTop; n++) {
	  		for (int m = jLow; m < jTop; m++) {
	  			if(model[n][m] == val) {
	  				return false;
	  			}
	  		}
	  	}
	  return true;	  	  
  }
}