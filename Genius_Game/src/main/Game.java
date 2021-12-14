package main;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private ArrayList<String> sequence;
	private ArrayList<String> mySequence;
	private int matchNumber;
	private int controller;
	private int roundNumber;
	private int difficulty;
	private int speedLevel;
	private int speed;
	private ArrayList<Long> times;
	private ArrayList<Player> players;
	private String date;
	private Random random;
	
	public Game() {
		this.sequence    = new ArrayList<>();
		this.mySequence  = new ArrayList<>();
		this.matchNumber = 0;
		this.controller  = 3;
		this.roundNumber = 1;
		this.difficulty  = 1;
		this.speedLevel  = 1;
		this.speed       = 800;
		this.times       = new ArrayList<>();
		this.players     = new ArrayList<>();
		this.date        = "";
		this.random      = new Random();
	}
	
	public int startSequence() {
		int status = 1;
		if(this.matchNumber == 2) {
			status = 2;
		} else {
			this.matchNumber++;
		}
		this.resetMySequence();
		this.updateDifficulty();
		this.updateSpeedLevel();
		this.roundNumber = 1;
		for(int i = 0; i < this.controller; i++) {
			String element = Integer.toString(this.random.nextInt(5 - 1) + 1);
			this.sequence.add(element);
		}
		return status;
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
	
	public int addPlayer(Player player) {
		int status = 0;
		if(this.players.size() < 2) {
			this.players.add(player);
			status = 1;
		}
		return status;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	public void setDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.date = dtf.format(LocalDateTime.now());
	}
	
	public String getDate() {
		return this.date;
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

	public int getMatchNumber() {
		return this.matchNumber;
	}
	
	public Player getPlayerOfMatch() {
		Player player;
		if(this.matchNumber % 2 != 0) {
			player = this.players.get(0);
		} else {
			player = this.players.get(1);
		}
		return player;
	}
	
	public void addPlayerTimes(ArrayList<Long> times) {
		this.getPlayerOfMatch().setTimes(times);
		this.getPlayerOfMatch().setBestTime();
	}

	public void updatePlayerPoints() {
		this.getPlayerOfMatch().updatePoints();
	}
	
	public int playerWinner() {
		int playerWinner;
		if(this.players.get(0).getPoints() == this.players.get(1).getPoints()) {
			if(analyzePlayersTime() == 1) {
				playerWinner = 1;
			} else if(analyzePlayersTime() == 2) {
				playerWinner = 2;
			} else {
				playerWinner = 3;
			}
		} else if(this.players.get(0).getPoints() > this.players.get(1).getPoints()) {
			playerWinner = 1;
		} else {
			playerWinner = 2;
		}
		return playerWinner;
	}
	
	private int analyzePlayersTime() {
		int bestPlayer;
		if(getSumPlayerTime(this.players.get(0)) < getSumPlayerTime(this.players.get(1))) {
			bestPlayer = 1;
		} else if(getSumPlayerTime(this.players.get(0)) > getSumPlayerTime(this.players.get(1))) {
			bestPlayer = 2;
		} else {
			bestPlayer = 3;
		}
		return bestPlayer;
	}
	
	private long getSumPlayerTime(Player player) {
		long sumPlayerTime = 0;
		for(long time: player.getTimes())
			sumPlayerTime += time;
		return sumPlayerTime;
	}
	
	public void rematch() {
		this.matchNumber = 0;
		for(Player player: this.players) {
			player.resetTimes();
			player.resetPoints();
		}
	}
	
	public void reset() {
		this.matchNumber = 0;
		this.players.clear();
	}
	
}
