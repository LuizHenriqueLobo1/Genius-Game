package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
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
	
	private JTabbedPane tabbedPane;
	private JPanel panelGame;
	private JPanel panelStart;
	private JPanel panelReport;
	
	private JButton btnGreen;
	private JButton btnBlue;
	private JButton btnYellow;
	private JButton btnRed;
	private JButton btnStart;
	private JButton btnAdvance;
	private JButton btnDifficulty;
	private JButton btnSpeed;
	
	private Game game;
	private Sprite sprites;
	private PlaySound playSound;
	
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
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Genius");
		frame.getContentPane().setLayout(null);
		
		game = new Game();
		sprites = new Sprite();
		playSound = new PlaySound();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 464, 439);
		frame.getContentPane().add(tabbedPane);
		
		panelStart = new JPanel();
		tabbedPane.addTab("Inicio", null, panelStart, null);
		panelStart.setLayout(null);
		
		panelGame = new JPanel();
		tabbedPane.addTab("Jogo", null, panelGame, null);
		panelGame.setLayout(null);
		
		panelReport = new JPanel();
		tabbedPane.addTab("Relatorio", null, panelReport, null);
		panelReport.setLayout(null);
		
		JLabel lblRound = new JLabel("Rodada:");
		lblRound.setBounds(10, 10, 60, 14);
		panelGame.add(lblRound);
		
		JLabel lblRoundNumber = new JLabel("1");
		lblRoundNumber.setBounds(60, 10, 20, 14);
		panelGame.add(lblRoundNumber);
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(389, 10, 60, 14);
		panelGame.add(lblStatus);
		
		btnGreen = new JButton(sprites.imgGreen);
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("1", lblStatus);
			}
		});
		btnGreen.setBounds(10, 35, 176, 110);
		panelGame.add(btnGreen);
		
		btnBlue = new JButton(sprites.imgBlue);
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("2", lblStatus);
			}
		});
		btnBlue.setBounds(273, 35, 176, 110);
		panelGame.add(btnBlue);
		
		btnYellow = new JButton(sprites.imgYellow);
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("3", lblStatus);
			}
		});
		btnYellow.setBounds(10, 156, 176, 110);
		panelGame.add(btnYellow);
		
		btnRed = new JButton(sprites.imgRed);
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("4", lblStatus);
			}
		});
		btnRed.setBounds(273, 156, 176, 110);
		panelGame.add(btnRed);
		
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
				btnDifficulty.setEnabled(false);
				btnSpeed.setEnabled(false);
				
				runSequence(game.getSpeeds());
			}
		});
		btnStart.setBounds(10, 294, 439, 23);
		panelGame.add(btnStart);
		
		btnAdvance = new JButton("Avan\u00E7ar");
		btnAdvance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game.updateSequence();
				
				lblStatus.setText("");
				btnStart.setEnabled(false);
				btnAdvance.setEnabled(false);
				
				lblRoundNumber.setText(game.getRoundNumberString());
				
				runSequence(game.getSpeeds());
			}
		});
		btnAdvance.setBounds(10, 328, 439, 23);
		btnAdvance.setEnabled(false);
		panelGame.add(btnAdvance);
		
		JLabel lblDifficulty = new JLabel("Dificuldade:");
		lblDifficulty.setBounds(10, 372, 76, 14);
		panelGame.add(lblDifficulty);
		
		btnDifficulty = new JButton("1");
		btnDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.changeValueOf("Difficulty");
				btnDifficulty.setText(game.getDifficultyString());
			}
		});
		btnDifficulty.setBounds(80, 368, 42, 23);
		panelGame.add(btnDifficulty);
		
		JLabel lblSpeed = new JLabel("Velocidade:");
		lblSpeed.setBounds(132, 372, 76, 14);
		panelGame.add(lblSpeed);
		
		btnSpeed = new JButton("1");
		btnSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.changeValueOf("Speed");
				btnSpeed.setText(game.getSpeedLevelString());
			}
		});
		btnSpeed.setBounds(202, 368, 42, 23);
		panelGame.add(btnSpeed);
		
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
			btnDifficulty.setEnabled(true);
			btnSpeed.setEnabled(true);
		} else if(play == -2)
			JOptionPane.showMessageDialog(null, "Voc� n�o pode jogar neste momento!");
	}
	
	private void runSequence(int speeds[]) {
		Timer timer  = new Timer();
		TimerTask timerTask = new TimerTask() {
			int index = 0;
			public void run() {
				if(index < game.getSequence().size()) {
					switch(game.getSequence().get(index)) {
						case "1": {
							changeSprite(btnGreen, sprites.imgGreen2, sprites.imgGreen, speeds);
							break;
						}
						case "2": {
							changeSprite(btnBlue, sprites.imgBlue2, sprites.imgBlue, speeds);
							break;
						}
						case "3": {
							changeSprite(btnYellow, sprites.imgYellow2, sprites.imgYellow, speeds);
							break;
						}
						case "4": {
							changeSprite(btnRed, sprites.imgRed2, sprites.imgRed, speeds);
							break;
						}
					}
					index++;
				} else {
					timer.cancel();
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, speeds[0]);
	}
	
	private void changeSprite(JButton button, ImageIcon tempSprite, ImageIcon fixedSprite, int speeds[]) {
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
		timer.scheduleAtFixedRate(timerTask, speeds[1], speeds[2]);
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
