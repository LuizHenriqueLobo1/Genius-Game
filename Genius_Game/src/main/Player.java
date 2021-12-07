package main;

import java.util.ArrayList;

public class Player {

	private String name;
	private String nick;
	private int points;
	private ArrayList<Long> times;
	private long bestTime;
	
	public Player(String name, String nick) {
		this.name     = name;
		this.nick     = nick;
		this.points   = 0;
		this.times    = new ArrayList<>();
		this.bestTime = 0;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
	}

	public void updatePoints() {
		this.points++;
	}
	
	public void resetPoints() {
		this.points = 0;
	}
	
	public int getPoints() {
		return this.points;
	}

	public void setTimes(ArrayList<Long> times) {
		this.times.addAll(times);
	}
	
	public void resetTimes() {
		this.times.clear();
	}
	
	public ArrayList<Long> getTimes() {
		return this.times;
	}
	
	public String getTimesFormatted() {
		String times = "";
		if(this.times.size() > 0) {
			for(int i = 0; i < this.times.size(); i++) {
				if(i == (this.times.size() - 1)) {
					times += Integer.toString(Math.round(this.times.get(i))) + " ms";
				} else {
					times += Integer.toString(Math.round(this.times.get(i))) + " ms, ";
				}
				
			}
		} else {
			times = "Sem tempos de jogada";
		}
		return times;
	}
	
	public void setBestTime() {
		long lowerValue = 999999999;
		for(long time: this.times) {
			if(time < lowerValue)
				lowerValue = time;
		}
		this.bestTime = lowerValue;
	}
	
	public String getBestTimeFormatted() {
		String bestTime = "";
		if(this.times.size() > 0) {
			bestTime = Integer.toString(Math.round(this.bestTime)) + " milissegundos";
		} else {
			bestTime = "Sem melhor tempo";
		}
		return bestTime; 
	}
	
}
