package main;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	private ArrayList<String> sequence;
	private ArrayList<String> mySequence;
	private int controller;
	private int roundNumber;
	private Random random;
	
	public Game() {
		this.sequence   = new ArrayList<>();
		this.mySequence = new ArrayList<>();
		this.controller = 4;
		this.roundNumber = 0;
		this.random     = new Random();
	}
	
	public void startSequence() {
		for(int i = 0; i < this.controller; i++) {
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
		this.updateRoundNumber();
	}
	
	public void resetSequence() {
		this.sequence.clear();
	}
	
	public ArrayList<String> getSequence() {
		return this.sequence;
	}
	
	public int makePlay(String element) {
		int status = 0;
		if(this.canPlay()) {
			this.mySequence.add(element);
			if(this.mySequence.size() == this.sequence.size())
				status = this.checkWin();
		} else
			status = -2;
		return status;
	}
	
	private boolean canPlay() {
		boolean canPlay = false;
		if(this.sequence.size() > 0) {
			if(this.mySequence.size() < this.sequence.size())
				canPlay = true;	
		}
		return canPlay;
	}
	
	public ArrayList<String> getMySequence() {
		return this.mySequence;
	}
	
	public void resetMySequence() {
		this.mySequence.clear();
	}
	
	private void updateRoundNumber() {
		this.roundNumber++;
	}
	
	public String getRoundNumberString() {
		return Integer.toString(this.roundNumber);
	}

	private int checkWin() {
		int winner = -1;
		int count  = 0;
		for(int i = 0; i < this.sequence.size(); i++) {
			if(this.sequence.get(i).equals(this.mySequence.get(i)))
				count++;
		}
		if(count == this.sequence.size())
			winner = 1;
		this.resetMySequence();
		return winner;
	}
	
}