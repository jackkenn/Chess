package board;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static void print(String s) {
		System.out.print(s);
	}
	public static void main(String[] args) {
		Board b = new Board();
		//print(new String(b.getSpot("A1").toString()+"."));
		print(b.toString());
		Scanner scan = new Scanner(System.in);
		String input = new String();
		ArrayList<String> inputList = new ArrayList<String>();
		b.getSpot("B7").movePiece("B6");
		print("\n" + b.toString());
		
		/*
		while(!input.contentEquals("done")) {
			if(scan.hasNext()) {
				input = scan.next();
				String s = new String(input);
				Piece p = b.getPiece(input);
				if(scan.hasNext()) {
					input = scan.next();
					s += "," + input;
					inputList.add(s);
					b.getSpot(input).movePiece(input);
					print(b.toString());
				}
			}
		}*/
		print("\ndone");
	}
}
