package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.util.Timer;
import java.util.TimerTask;

import main.Game;

public class Interface {

	private JFrame frame;
	private JTextField textSequence;
	
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
		
		JButton btnGreen = new JButton("Verde");
		btnGreen.setBounds(10, 11, 176, 110);
		frame.getContentPane().add(btnGreen);
		
		JButton btnBlue = new JButton("Azul");
		btnBlue.setBounds(248, 11, 176, 110);
		frame.getContentPane().add(btnBlue);
		
		JButton btnYellow = new JButton("Amarelo");
		btnYellow.setBounds(10, 140, 176, 110);
		frame.getContentPane().add(btnYellow);
		
		JButton btnRed = new JButton("Vermelho");
		btnRed.setBounds(248, 140, 176, 110);
		frame.getContentPane().add(btnRed);
	
		textSequence = new JTextField();
		textSequence.setBounds(10, 308, 414, 20);
		frame.getContentPane().add(textSequence);
		textSequence.setColumns(10);
		textSequence.setEditable(false);
		
		JButton btnStart = new JButton("Rodar");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				game.startSequence();
				
				textSequence.setText("");
				textSequence.setText(game.getSequenceString());
				btnStart.setEnabled(false);
				
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
							System.out.println("Rodada encerrada!");
							game.resetSequence();
							btnStart.setEnabled(true);
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

}
