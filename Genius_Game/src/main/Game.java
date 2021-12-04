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
	private int speed;
	private ArrayList<Long> times;
	private Random random;
	
	public Game() {
		this.sequence    = new ArrayList<>();
		this.mySequence  = new ArrayList<>();
		this.controller  = 3;
		this.roundNumber = 1;
		this.difficulty  = 1;
		this.speedLevel  = 1;
		this.speed       = 800;
		this.times       = new ArrayList<>();
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
			this.speed = 800;
		} else if(this.speedLevel == 2) {
			this.speed = 500;
		} else {
			this.speed = 200;
		}
	}
	
	public int getSpeedLevel() {
		return this.speedLevel;
	}
	
	public String getSpeedLevelString() {
		return Integer.toString(this.speedLevel);
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void addTime(long time) {
		this.times.add(time);
	}
	
	public void resetTimes() {
		this.times.clear();
	}
	
	public ArrayList<Long> getTimes() {
		return this.times;
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
