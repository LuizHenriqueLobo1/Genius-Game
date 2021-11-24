package ui;

import javax.swing.ImageIcon;

public class Sprite {

	protected ImageIcon imgGreen;
	protected ImageIcon imgGreen2;
	protected ImageIcon imgBlue;
	protected ImageIcon imgBlue2;
	protected ImageIcon imgYellow;
	protected ImageIcon imgYellow2;
	protected ImageIcon imgRed;
	protected ImageIcon imgRed2;
	
	public Sprite() {
		this.imgGreen   = new ImageIcon("assets/green.png");
		this.imgGreen2  = new ImageIcon("assets/green2.png");
		this.imgBlue    = new ImageIcon("assets/blue.png");
		this.imgBlue2   = new ImageIcon("assets/blue2.png");
		this.imgYellow  = new ImageIcon("assets/yellow.png");
		this.imgYellow2 = new ImageIcon("assets/yellow2.png");
		this.imgRed     = new ImageIcon("assets/red.png");
		this.imgRed2    = new ImageIcon("assets/red2.png");
	}
	
}
