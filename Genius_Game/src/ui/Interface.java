package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.TimerTask;

import main.Game;

public class Interface {

	private JFrame frame;
	
	private JButton btnGreen;
	private JButton btnBlue;
	private JButton btnYellow;
	private JButton btnRed;
	private JButton btnStart;
	private JButton btnRepeat;
	
	private Game game;
	private Sprite sprites;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Interface() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Genius");
		frame.getContentPane().setLayout(null);
		
		game = new Game();
		sprites = new Sprite();
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setBounds(10, 381, 315, 14);
		frame.getContentPane().add(lblStatus);
		
		btnGreen = new JButton(sprites.imgGreen);
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("1", lblStatus);
			}
		});
		btnGreen.setBounds(10, 11, 176, 110);
		frame.getContentPane().add(btnGreen);
		
		btnBlue = new JButton(sprites.imgBlue);
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("2", lblStatus);
			}
		});
		btnBlue.setBounds(248, 11, 176, 110);
		frame.getContentPane().add(btnBlue);
		
		btnYellow = new JButton(sprites.imgYellow);
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("3", lblStatus);
			}
		});
		btnYellow.setBounds(10, 140, 176, 110);
		frame.getContentPane().add(btnYellow);
		
		btnRed = new JButton(sprites.imgRed);
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("4", lblStatus);
			}
		});
		btnRed.setBounds(248, 140, 176, 110);
		frame.getContentPane().add(btnRed);
		
		btnStart = new JButton("Iniciar");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game.resetSequence();
				game.startSequence();
				
				lblStatus.setText("");
				btnStart.setEnabled(false);
				btnRepeat.setEnabled(false);
				
				runSequence();
			}
		});
		btnStart.setBounds(10, 274, 414, 23);
		frame.getContentPane().add(btnStart);
		
		btnRepeat = new JButton("Repetir");
		btnRepeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblStatus.setText("");
				btnStart.setEnabled(false);
				btnRepeat.setEnabled(false);
				
				runSequence();
			}
		});
		btnRepeat.setBounds(10, 308, 414, 23);
		frame.getContentPane().add(btnRepeat);
		
	}
	
	private void play(String element, JLabel label) {
		int play = game.makePlay(element);
		if(play == 1)
			label.setText("ACERTOU");
		else if(play == -1)
			label.setText("ERROU");
		else if(play == -2)
			JOptionPane.showMessageDialog(null, "O jogo precisa ser inicado.");
	}
	
	private void runSequence() {
		Timer timer  = new Timer();
		TimerTask timerTask = new TimerTask() {
			int index = 0;
			public void run() {
				if(index < game.getSequence().size()) {
					switch(game.getSequence().get(index)) {
						case "1": {
							changeSprite(btnGreen, sprites.imgGreen2, sprites.imgGreen);
							break;
						}
						case "2": {
							changeSprite(btnBlue, sprites.imgBlue2, sprites.imgBlue);
							break;
						}
						case "3": {
							changeSprite(btnYellow, sprites.imgYellow2, sprites.imgYellow);
							break;
						}
						case "4": {
							changeSprite(btnRed, sprites.imgRed2, sprites.imgRed);
							break;
						}
					}
					index++;
				} else {
					timer.cancel();
					btnStart.setEnabled(true);
					btnRepeat.setEnabled(true);
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
	private void changeSprite(JButton button, ImageIcon tempSprite, ImageIcon fixedSprite) {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			int count = 0;
			public void run() {
				if(count < 1) {
					button.setIcon(tempSprite);
					count++;
				} else {
					timer.cancel();
					button.setIcon(fixedSprite);
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
}
