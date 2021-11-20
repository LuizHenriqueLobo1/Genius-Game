package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.TimerTask;

import main.Game;

public class Interface {

	private JFrame frame;
	
	private Game game;
	
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
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setBounds(10, 381, 315, 14);
		frame.getContentPane().add(lblStatus);
		
		JButton btnGreen = new JButton("Verde");
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("1", lblStatus);
			}
		});
		btnGreen.setBounds(10, 11, 176, 110);
		frame.getContentPane().add(btnGreen);
		
		JButton btnBlue = new JButton("Azul");
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("2", lblStatus);
			}
		});
		btnBlue.setBounds(248, 11, 176, 110);
		frame.getContentPane().add(btnBlue);
		
		JButton btnYellow = new JButton("Amarelo");
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("3", lblStatus);
			}
		});
		btnYellow.setBounds(10, 140, 176, 110);
		frame.getContentPane().add(btnYellow);
		
		JButton btnRed = new JButton("Vermelho");
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play("4", lblStatus);
			}
		});
		btnRed.setBounds(248, 140, 176, 110);
		frame.getContentPane().add(btnRed);
	
		JTextField textSequence = new JTextField();
		textSequence.setBounds(10, 308, 414, 20);
		frame.getContentPane().add(textSequence);
		textSequence.setColumns(10);
		textSequence.setEditable(false);
		
		JButton btnStart = new JButton("Iniciar");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game.resetSequence();
				game.startSequence();
				
				lblStatus.setText("");
				textSequence.setText("");
				textSequence.setText(game.getSequenceString());
				
				btnStart.setEnabled(false);
				btnGreen.setEnabled(false);
				btnBlue.setEnabled(false);
				btnYellow.setEnabled(false);
				btnRed.setEnabled(false);
				
				Timer timer  = new Timer();
				TimerTask timerTask = new TimerTask() {
					int index = 0;
					public void run() {
						if(index < game.getSequence().size()) {
							System.out.println(game.getSequence().get(index));
							switch(game.getSequence().get(index)) {
								case "1": {
									changeButtonText(btnGreen, "Verde");
									break;
								}
								case "2": {
									changeButtonText(btnBlue, "Azul");
									break;
								}
								case "3": {
									changeButtonText(btnYellow, "Amarelo");
									break;
								}
								case "4": {
									changeButtonText(btnRed, "Vermelho");
									break;
								}
							}
							index++;
						} else {
							timer.cancel();
							System.out.println("Sequencia gerada!");
							btnStart.setEnabled(true);
							btnGreen.setEnabled(true);
							btnBlue.setEnabled(true);
							btnYellow.setEnabled(true);
							btnRed.setEnabled(true);
						}
					}
				};
				timer.scheduleAtFixedRate(timerTask, 0, 1000);
				
			}
		});
		btnStart.setBounds(10, 274, 414, 23);
		frame.getContentPane().add(btnStart);
		
	}
	
	private void changeButtonText(JButton button, String color) {
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			int count = 0;
			public void run() {
				if(count < 1) {
					button.setText("---");
					count++;
				} else {
					timer.cancel();
					button.setText(color);
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
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
	
}
