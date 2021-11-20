package main;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	private ArrayList<String> sequence;
	private Random random;
	
	public Game() {
		this.sequence = new ArrayList<>();
		this.random   = new Random();
	}
	
	public void startSequence() {
		for(int i = 0; i < 4; i++) {
			String element = Integer.toString(this.random.nextInt(5 - 1) + 1);
			if(i >= 1) {
				if(element.equals(this.sequence.get(i-1))) {
					i--;
				} else {
					this.sequence.add(element);
				}
			} else {
				this.sequence.add(element);
			}
		}
	}
	
	public ArrayList<String> getSequence() {
		return this.sequence;
	}
	
	public void resetSequence() {
		this.sequence.clear();
	}
	
	public String getSequenceString() {
		String string = "";
		for(String element: this.sequence)
			string += element;
		return string;
	}
	
}
