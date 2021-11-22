package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

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
	private JButton btnAdvance;
	
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
		
		JLabel lblRound = new JLabel("Rodada:");
		lblRound.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblRound);
		
		JLabel lblRoundNumber = new JLabel("1");
		lblRoundNumber.setBounds(60, 11, 46, 14);
		frame.getContentPane().add(lblRoundNumber);
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(353, 11, 71, 14);
		frame.getContentPane().add(lblStatus);
		
		btnGreen = new JButton(sprites.imgGreen);
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("1", lblStatus);
			}
		});
		btnGreen.setBounds(10, 36, 176, 110);
		frame.getContentPane().add(btnGreen);
		
		btnBlue = new JButton(sprites.imgBlue);
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("2", lblStatus);
			}
		});
		btnBlue.setBounds(248, 36, 176, 110);
		frame.getContentPane().add(btnBlue);
		
		btnYellow = new JButton(sprites.imgYellow);
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("3", lblStatus);
			}
		});
		btnYellow.setBounds(10, 175, 176, 110);
		frame.getContentPane().add(btnYellow);
		
		btnRed = new JButton(sprites.imgRed);
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("4", lblStatus);
			}
		});
		btnRed.setBounds(248, 175, 176, 110);
		frame.getContentPane().add(btnRed);
		
		btnStart = new JButton("Iniciar");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game.resetSequence();
				game.startSequence();
				
				enableColorButtons();
				
				lblStatus.setText("");
				lblRoundNumber.setText("1");
				btnStart.setEnabled(false);
				btnAdvance.setEnabled(false);
				
				runSequence();
			}
		});
		btnStart.setBounds(10, 310, 414, 23);
		frame.getContentPane().add(btnStart);
		
		btnAdvance = new JButton("Avan\u00E7ar");
		btnAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game.updateSequence();
				
				lblStatus.setText("");
				btnStart.setEnabled(false);
				btnAdvance.setEnabled(false);
				
				lblRoundNumber.setText(game.getRoundNumberString());
				
				runSequence();
			}
		});
		btnAdvance.setBounds(10, 344, 414, 23);
		btnAdvance.setEnabled(false);
		frame.getContentPane().add(btnAdvance);
		
	}
	
	private void play(String element, JLabel label) {
		int play = game.makePlay(element);
		label.setText("");
		if(play == 1) {
			label.setText("ACERTOU");
			btnAdvance.setEnabled(true);
		} else if(play == -1) {
			label.setText("ERROU");
			disableColorButtons();
			btnStart.setEnabled(true);
		} else if(play == -2)
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
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, 800);
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
		timer.scheduleAtFixedRate(timerTask, 500, 500);
	}
	
	private void enableColorButtons() {
		btnGreen.setEnabled(true);
		btnBlue.setEnabled(true);
		btnYellow.setEnabled(true);
		btnRed.setEnabled(true);
	}
	
	private void disableColorButtons() {
		btnGreen.setEnabled(false);
		btnBlue.setEnabled(false);
		btnYellow.setEnabled(false);
		btnRed.setEnabled(false);
	}
	
}
