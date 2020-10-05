package board;

public class Cord implements Cloneable{
	public int row, column;
	public String cord;
	
	public Cord(int givenRow, int givenColumn) throws NullPointerException{
		row = givenRow;
		column = givenColumn;
		String s = new String((char)(column+65)+Integer.toString(row));
		cord = new String((char)(column+65-1)+Integer.toString(row));
		
	}
	
	public Cord(String s) {
		column = (int) s.toCharArray()[0]-65+1;
		row = (int) s.toCharArray()[1]-48;
		cord = new String(s);
	}
	
	public Cord(Cord GivenCord) {
		Cord temp = GivenCord.clone();
		row = temp.row;
		column = temp.column;
		cord = temp.cord;
	}
	
	@Override
	public Cord clone() {
		try {
			return (Cord) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}