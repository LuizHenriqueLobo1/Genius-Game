package main;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	private ArrayList<String> sequence;
	private ArrayList<String> mySequence;
	private int controller;
	private int roundNumber;
	private int difficulty;
	private int speedLevel;
	private int[] speeds;
	private Random random;
	
	public Game() {
		this.sequence    = new ArrayList<>();
		this.mySequence  = new ArrayList<>();
		this.controller  = 3;
		this.roundNumber = 1;
		this.difficulty  = 1;
		this.speedLevel  = 1;
		this.speeds 	 = new int[3];
		this.random      = new Random();
	}
	
	public void startSequence() {
		this.resetMySequence();
		this.updateDifficulty();
		this.updateSpeedLevel();
		this.roundNumber = 1;
		for(int i = 0; i < this.controller; i++) {
			String element = Integer.toString(this.random.nextInt(5 - 1) + 1);
			this.sequence.add(element);
		}
	}
	
	public void updateSequence() {
		String element = Integer.toString(this.random.nextInt(5 - 1) + 1);
		this.sequence.add(element);
		this.controller++;
		this.updateRoundNumber();
		this.resetMySequence();
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
			if(this.mySequence.size() == this.sequence.size()) {
				status = this.checkWin();
			} else {
				if(!element.equals(this.sequence.get(this.mySequence.size()-1)))
					status = -1;
			}
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
	
	private void resetMySequence() {
		this.mySequence.clear();
	}
	
	private void updateRoundNumber() {
		this.roundNumber++;
	}
	
	public String getRoundNumberString() {
		return Integer.toString(this.roundNumber);
	}
	
	private void updateDifficulty() {
		if(this.difficulty == 1)
			this.controller = 3;
		else if(this.difficulty == 2)
			this.controller = 4;
		else
			this.controller = 5;
	}
	
	public int getDifficulty() {
		return this.difficulty;
	}
	
	public String getDifficultyString() {
		return Integer.toString(this.difficulty);
	}
	
	private void updateSpeedLevel() {
		if(this.speedLevel == 1) {
			this.speeds[0] = 800;
			this.speeds[1] = 150; // 500
			this.speeds[2] = 150; // 500
		} else if(this.speedLevel == 2) {
			this.speeds[0] = 500;
			this.speeds[1] = 150; // 250
			this.speeds[2] = 150; // 250
		} else {
			this.speeds[0] = 200;
			this.speeds[1] = 150; // 100
			this.speeds[2] = 150; // 100
		}
	}
	
	public int getSpeedLevel() {
		return this.speedLevel;
	}
	
	public String getSpeedLevelString() {
		return Integer.toString(this.speedLevel);
	}
	
	public int[] getSpeeds() {
		return this.speeds;
	}
	
	public void changeValueOf(String element) {
		if(element.equals("Difficulty")) {
			if(this.difficulty >= 1 && this.difficulty < 3)
				this.difficulty++;
			else
				this.difficulty = 1;
		} else {
			if(this.speedLevel >= 1 && this.speedLevel < 3)
				this.speedLevel++;
			else
				this.speedLevel = 1;
		}
	}
	
	private int checkWin() {
		int winner = -1;
		int count  =  0;
		for(int i = 0; i < this.sequence.size(); i++) {
			if(this.sequence.get(i).equals(this.mySequence.get(i)))
				count++;
		}
		if(count == this.sequence.size())
			winner = 1;
		return winner;
	}
	
}
