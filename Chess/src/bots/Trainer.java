package bots;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import game.GameLoop;

public class Trainer implements Runnable {
	public static int threads = 0;
	
	public Trainer() {
	}
	
	public void start() {
		Thread t = new Thread(this, new String("Trainer"));
		t.start();
		threads++;
	}
	
	@Override
	public void run() {
		String str[] = new String[] { "WeightList.txt", "", "WeightList.txt", "" };
		ArrayList<Integer> rngArray = new ArrayList<Integer>();
		if(rngArray.size()<2) {
			for (int i = 0; i < 40; i++) {
				rngArray.add(i);
			}
		}
		for(int i = 0; i<39; i+=2) {
			Random rand = new Random(System.nanoTime());
			GameLoop gl = new GameLoop();
			int temp = 0;
			int temp2 = 0;
			temp = rand.nextInt(40 - (i));
			temp2 = rand.nextInt(39 - (i));
			str[1] = rngArray.get(temp) + "";
			rngArray.remove(temp);
			str[3] = rngArray.get(temp2) + "";
			rngArray.remove(temp2);
			try {
				//gl.loop(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		run();
	}
	
	public static void main(String args[]) {
		if (false) {
			String str = new String("");
			for(int i=0;i<40; i++) {
				str += "3.0 0.12 0.02 0.5 0.75 1.25 1.0 1.0 3.0 0.95 0\n";
			}
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("WeightList.txt"));
				bw.write(str);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int j = 0;
		Trainer t = new Trainer();
		while (j < 1) {
			if(t.threads<1) {
				for(int i = 0; i<10000; i++) {};
				t.start();
				j++;
			}
		}
	}
}
