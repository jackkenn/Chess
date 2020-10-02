package board;

public class Cord{
	public int row, column;
	public String cord;
	
	public Cord(int givenRow, int givenColumn) throws NullPointerException{
		row = givenRow;
		column = givenColumn;
		String s = new String((char)(column+65)+Integer.toString(row));
		cord = new String((char)(column+65)+Integer.toString(row));
		
	}
	
	public Cord(String s) {
		column = (int) s.toCharArray()[0]-65;
		row = (int) s.toCharArray()[1]-48;
		cord = new String(s);
	}
	
	public Cord(Cord GivenCord) {
		Cord temp;
		try {
			temp = (Cord) GivenCord.clone();
			row = temp.row;
			column = temp.column;
			cord = temp.cord;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
