package ui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import java.io.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import main.Game;
import main.Player;

public class Interface {

	public static final String SAVED_GAME_PATH = "savedGame";
	private JFrame frame;
	
	private JTabbedPane tabbedPane;
	private JPanel panelGame;
	private JPanel panelStart;
	private JPanel panelReport;
	
	private JTextField textName;
	private JTextField textNick;
	private JButton btnRegister;
	
	private JButton btnGreen;
	private JButton btnBlue;
	private JButton btnYellow;
	private JButton btnRed;
	private JButton btnStart;
	private JButton btnSave;
	private JButton btnLoadGame;
	private JButton btnAdvance;
	private JButton btnDifficulty;
	private JButton btnSpeed;
	private JLabel lblPlayer;
	
	private Game game;
	private Sprite sprites;
	private PlaySound playSound;
	
	private long timerStart  = 0;
	private long timerFinish = 0;
	
	private JLabel lblDate;
	private JLabel lblFirstPlayerStatus;
	private JLabel lblFirstPlayerPoints;
	private JTextField textFirstPlayerName;
	private JTextField textFirstPlayerNick;
	private JTextField textFirstPlayerTimes;
	private JTextField textFirstPlayerBestTime;
	private JLabel lblSecondPlayerStatus;
	private JLabel lblSecondPlayerPoints;
	private JTextField textSecondPlayerName;
	private JTextField textSecondPlayerNick;
	private JTextField textSecondPlayerTimes;
	private JTextField textSecondPlayerBestTime;
	private JButton btnRematch;
	private JButton btnRestart;
	
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
		
		// Início
		buildRegisterPanel();

		// Jogo
		buildMainGamePanel();

		// Relatório
		buildReportPanel();
	}

	private void buildRegisterPanel() {
		panelStart = new JPanel();
		tabbedPane.addTab("Inicio", null, panelStart, null);
		panelStart.setLayout(null);

		File f = new File(SAVED_GAME_PATH);
		if(f.isFile()) {
			showLoadGameButton();
		}

		JLabel lblPlayerName = new JLabel("Nome:");
		lblPlayerName.setBounds(134, 155, 46, 14);
		panelStart.add(lblPlayerName);

		textName = new JTextField();
		textName.setBounds(184, 152, 134, 20);
		panelStart.add(textName);
		textName.setColumns(10);

		JLabel lblNick = new JLabel("Apelido:");
		lblNick.setBounds(134, 191, 52, 14);
		panelStart.add(lblNick);

		textNick = new JTextField();
		textNick.setColumns(10);
		textNick.setBounds(184, 188, 134, 20);
		panelStart.add(textNick);
		btnRegister = new JButton("Cadastrar Jogador 1");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Objects.equals(System.getenv("DEBUG_MODE"), "true")){
					Player player1 = new Player("debug", "debug");
					Player player2 = new Player("mode", "mode");
					game.addPlayer(player1);
					game.addPlayer(player2);
					game.setDate();
					changePanel(0, 1);
				}else{
					if (textName.getText().length() <= 3 || textNick.getText().length() <= 2) {
						JOptionPane.showMessageDialog(null, "Preencha os campos com valores válidos!");
					} else {
						Player player = new Player(textName.getText(), textNick.getText());
						int statusAddPlayer = game.addPlayer(player);
						if (statusAddPlayer == 1) {
							btnRegister.setText("Cadastrar Jogador 2");
							textName.setText("");
							textNick.setText("");
						}
						if (game.getPlayers().size() == 2) {
							JOptionPane.showMessageDialog(null, "Continuando para o jogo...");
							game.setDate();
							changePanel(0, 1);
						}
					}
				}
			}
		});
		btnRegister.setBounds(134, 228, 184, 23);
		panelStart.add(btnRegister);

		JLabel lblTitle = new JLabel("Campeonato de Genius");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 32, 439, 50);
		panelStart.add(lblTitle);
	}

	private void showLoadGameButton() {
		btnLoadGame = new JButton("Carregar jogo salvo");
		btnLoadGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					FileInputStream fi = new FileInputStream(SAVED_GAME_PATH);
					ObjectInputStream oi = new ObjectInputStream(fi);
					game = (Game) oi.readObject();
					oi.close();
					fi.close();
					JOptionPane.showMessageDialog(null, "Continuando para o jogo...");
//					game.setDate();
					changePanel(0, 1);
//
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				System.out.println("Jogo carregado!");
			}
		});

		btnLoadGame.setBounds(10, 270, 439, 23);
		panelStart.add(btnLoadGame);
	}

	private void buildMainGamePanel() {
		panelGame = new JPanel();
		tabbedPane.addTab("Jogo", null, panelGame, null);
		tabbedPane.setEnabledAt(1, false);
		panelGame.setLayout(null);

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

		lblPlayer = new JLabel("");
		lblPlayer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer.setBounds(348, 372, 101, 14);
		panelGame.add(lblPlayer);

		btnGreen = new JButton(sprites.imgGreen);
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play("1", lblStatus) != -2) {
					changeSprite(btnGreen, sprites.imgGreen2, sprites.imgGreen);
					playSound.playSound("sounds/do.wav");
				}
			}
		});
		btnGreen.setBounds(10, 35, 176, 110);
		panelGame.add(btnGreen);

		btnBlue = new JButton(sprites.imgBlue);
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play("2", lblStatus) != -2) {
					changeSprite(btnBlue, sprites.imgBlue2, sprites.imgBlue);
					playSound.playSound("sounds/re.wav");
				}
			}
		});
		btnBlue.setBounds(273, 35, 176, 110);
		panelGame.add(btnBlue);

		btnYellow = new JButton(sprites.imgYellow);
		btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play("3", lblStatus) != -2) {
					changeSprite(btnYellow, sprites.imgYellow2, sprites.imgYellow);
					playSound.playSound("sounds/mi.wav");
				}
			}
		});
		btnYellow.setBounds(10, 156, 176, 110);
		panelGame.add(btnYellow);

		btnRed = new JButton(sprites.imgRed);
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(play("4", lblStatus) != -2) {
					changeSprite(btnRed, sprites.imgRed2, sprites.imgRed);
					playSound.playSound("sounds/fa.wav");
				}
			}
		});
		btnRed.setBounds(273, 156, 176, 110);
		panelGame.add(btnRed);

		btnSave = new JButton("Salvar o jogo");
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					FileOutputStream f = new FileOutputStream(new File(SAVED_GAME_PATH));
					ObjectOutputStream o = new ObjectOutputStream(f);
					o.writeObject(game);
					o.close();
					f.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Jogo salvo!");
			}
		});

		btnSave.setBounds(10, 270, 439, 23);
		panelGame.add(btnSave);

		btnStart = new JButton("Iniciar");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				game.resetSequence();
				if(game.startSequence() == 2)
					btnStart.setEnabled(false);

				lblPlayer.setText(game.getPlayerOfMatch().getNick());

				enableColorButtons();

				lblStatus.setText("");
				lblRoundNumber.setText("1");
				btnStart.setEnabled(false);
				btnAdvance.setEnabled(false);
				btnDifficulty.setEnabled(false);
				btnSpeed.setEnabled(false);

				runSequence(game.getSpeed());
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

				runSequence(game.getSpeed());
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

	private void buildReportPanel() {
		panelReport = new JPanel();
		tabbedPane.addTab("Relatorio", null, panelReport, null);
		tabbedPane.setEnabledAt(2, false);
		panelReport.setLayout(null);

		lblDate = new JLabel("DATA");
		lblDate.setBounds(10, 56, 72, 14);
		panelReport.add(lblDate);

		JLabel lblReportTitle = new JLabel("Relat\u00F3rio Final do Jogo");
		lblReportTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblReportTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblReportTitle.setBounds(10, 20, 439, 32);
		panelReport.add(lblReportTitle);

		JSeparator separator1 = new JSeparator();
		separator1.setBounds(10, 81, 439, 2);
		panelReport.add(separator1);

		JLabel lblFirstPlayer = new JLabel("Jogador 1");
		lblFirstPlayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFirstPlayer.setBounds(10, 94, 84, 32);
		panelReport.add(lblFirstPlayer);

		lblFirstPlayerStatus = new JLabel("STATUS");
		lblFirstPlayerStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstPlayerStatus.setBounds(365, 105, 84, 14);
		panelReport.add(lblFirstPlayerStatus);

		JLabel lblFirstPlayerName = new JLabel("Nome:");
		lblFirstPlayerName.setBounds(10, 140, 58, 14);
		panelReport.add(lblFirstPlayerName);

		textFirstPlayerName = new JTextField();
		textFirstPlayerName.setBounds(60, 137, 120, 20);
		panelReport.add(textFirstPlayerName);
		textFirstPlayerName.setColumns(10);
		textFirstPlayerName.setEditable(false);

		JLabel lblFirstPlayerNick = new JLabel("Apelido:");
		lblFirstPlayerNick.setBounds(10, 170, 46, 14);
		panelReport.add(lblFirstPlayerNick);

		textFirstPlayerNick = new JTextField();
		textFirstPlayerNick.setBounds(60, 165, 120, 20);
		panelReport.add(textFirstPlayerNick);
		textFirstPlayerNick.setColumns(10);
		textFirstPlayerNick.setEditable(false);

		JLabel lblFirstPlayerTimes = new JLabel("Tempos:");
		lblFirstPlayerTimes.setBounds(210, 140, 58, 14);
		panelReport.add(lblFirstPlayerTimes);

		textFirstPlayerTimes = new JTextField();
		textFirstPlayerTimes.setBounds(265, 137, 184, 20);
		panelReport.add(textFirstPlayerTimes);
		textFirstPlayerTimes.setColumns(10);
		textFirstPlayerTimes.setEditable(false);

		JLabel lblFirstPlayerBestTime = new JLabel("Melhor jogada:");
		lblFirstPlayerBestTime.setBounds(210, 170, 96, 14);
		panelReport.add(lblFirstPlayerBestTime);

		textFirstPlayerBestTime = new JTextField();
		textFirstPlayerBestTime.setBounds(299, 167, 150, 20);
		panelReport.add(textFirstPlayerBestTime);
		textFirstPlayerBestTime.setColumns(10);
		textFirstPlayerBestTime.setEditable(false);

		JSeparator separator2 = new JSeparator();
		separator2.setBounds(10, 216, 439, 2);
		panelReport.add(separator2);

		JLabel lblSecondPlayer = new JLabel("Jogador 2");
		lblSecondPlayer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSecondPlayer.setBounds(10, 229, 84, 32);
		panelReport.add(lblSecondPlayer);

		lblSecondPlayerStatus = new JLabel("STATUS");
		lblSecondPlayerStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSecondPlayerStatus.setBounds(365, 240, 84, 14);
		panelReport.add(lblSecondPlayerStatus);

		JLabel lblSecondPlayerName = new JLabel("Nome:");
		lblSecondPlayerName.setBounds(10, 275, 58, 14);
		panelReport.add(lblSecondPlayerName);

		textSecondPlayerName = new JTextField();
		textSecondPlayerName.setEditable(false);
		textSecondPlayerName.setColumns(10);
		textSecondPlayerName.setBounds(60, 272, 120, 20);
		panelReport.add(textSecondPlayerName);

		JLabel lblSecondPlayerNick = new JLabel("Apelido:");
		lblSecondPlayerNick.setBounds(10, 305, 46, 14);
		panelReport.add(lblSecondPlayerNick);

		textSecondPlayerNick = new JTextField();
		textSecondPlayerNick.setEditable(false);
		textSecondPlayerNick.setColumns(10);
		textSecondPlayerNick.setBounds(60, 300, 120, 20);
		panelReport.add(textSecondPlayerNick);

		JLabel lblSecondPlayerTimes = new JLabel("Tempos:");
		lblSecondPlayerTimes.setBounds(210, 275, 58, 14);
		panelReport.add(lblSecondPlayerTimes);

		textSecondPlayerTimes = new JTextField();
		textSecondPlayerTimes.setEditable(false);
		textSecondPlayerTimes.setColumns(10);
		textSecondPlayerTimes.setBounds(265, 272, 184, 20);
		panelReport.add(textSecondPlayerTimes);

		JLabel lblSecondPlayerBestTime = new JLabel("Melhor jogada:");
		lblSecondPlayerBestTime.setBounds(210, 305, 96, 14);
		panelReport.add(lblSecondPlayerBestTime);

		textSecondPlayerBestTime = new JTextField();
		textSecondPlayerBestTime.setEditable(false);
		textSecondPlayerBestTime.setColumns(10);
		textSecondPlayerBestTime.setBounds(299, 302, 150, 20);
		panelReport.add(textSecondPlayerBestTime);

		JSeparator separator3 = new JSeparator();
		separator3.setBounds(10, 351, 439, 2);
		panelReport.add(separator3);

		btnRematch = new JButton("Revanche");
		btnRematch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.rematch();
				btnStart.setEnabled(true);
				enableColorButtons();
				changePanel(2, 1);
			}
		});
		btnRematch.setBounds(60, 370, 156, 23);
		panelReport.add(btnRematch);

		btnRestart = new JButton("Mudar Jogadores");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.reset();
				btnStart.setEnabled(true);
				enableColorButtons();
				changePanel(2, 0);
			}
		});
		btnRestart.setBounds(238, 370, 156, 23);
		panelReport.add(btnRestart);

		lblFirstPlayerPoints = new JLabel("PONTOS");
		lblFirstPlayerPoints.setBounds(340, 105, 80, 14);
		panelReport.add(lblFirstPlayerPoints);

		lblSecondPlayerPoints = new JLabel("PONTOS");
		lblSecondPlayerPoints.setBounds(340, 240, 80, 14);
		panelReport.add(lblSecondPlayerPoints);
	}

	private int play(String element, JLabel lblStatus) {
		int play = game.makePlay(element);
		lblStatus.setText("");
		if(play == 1) {
			lblStatus.setText("ACERTOU");
			game.updatePlayerPoints();
			addPlayTime();
			btnAdvance.setEnabled(true);
		} else if(play == -1) {
			lblStatus.setText("ERROU");
			game.addPlayerTimes(game.getTimes());
			game.resetTimes();
			disableColorButtons();
			if(game.getMatchNumber() < 2) {
				btnStart.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "O jogo acabou! Confirme para verificar os resultados.");
				lblStatus.setText("");
				lblPlayer.setText("");
				lblDate.setText(game.getDate());
				btnDifficulty.setEnabled(true);
				btnSpeed.setEnabled(true);
				changePanel(1, 2);
				loadReport();
			}
		} else if(play == -2) {
			JOptionPane.showMessageDialog(null, "Você não pode jogar neste momento!");
		}
		return play;
	}
	
	private void runSequence(int speed) {
		Timer timer  = new Timer();
		TimerTask timerTask = new TimerTask() {
			int index = 0;
			public void run() {
				if(index < game.getSequence().size()) {
					switch(game.getSequence().get(index)) {
						case "1": {
							playSound.playSound("sounds/do.wav");
							changeSprite(btnGreen, sprites.imgGreen2, sprites.imgGreen);
							break;
						}
						case "2": {
							playSound.playSound("sounds/re.wav");
							changeSprite(btnBlue, sprites.imgBlue2, sprites.imgBlue);
							break;
						}
						case "3": {
							playSound.playSound("sounds/mi.wav");
							changeSprite(btnYellow, sprites.imgYellow2, sprites.imgYellow);
							break;
						}
						case "4": {
							playSound.playSound("sounds/fa.wav");
							changeSprite(btnRed, sprites.imgRed2, sprites.imgRed);
							break;
						}
					}
					index++;
				} else {
					timer.cancel();
					timerStart = System.currentTimeMillis();
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, speed);
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
		timer.scheduleAtFixedRate(timerTask, 0, 150);
	}
	
	private void addPlayTime() {
		timerFinish = System.currentTimeMillis();
		long timerTotal = (timerFinish - timerStart);
		game.addTime(timerTotal);
		timerStart  = 0;
		timerFinish = 0;
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
	
	private void loadReport() {
		Player firstPlayer = game.getPlayers().get(0);
		textFirstPlayerName.setText(firstPlayer.getName());
		textFirstPlayerNick.setText(firstPlayer.getNick());
		textFirstPlayerTimes.setText(firstPlayer.getTimesFormatted());
		textFirstPlayerBestTime.setText(firstPlayer.getBestTimeFormatted());
		lblFirstPlayerPoints.setText(firstPlayer.getPointsFormatted());
		Player secondPlayer = game.getPlayers().get(1);
		textSecondPlayerName.setText(secondPlayer.getName());
		textSecondPlayerNick.setText(secondPlayer.getNick());
		textSecondPlayerTimes.setText(secondPlayer.getTimesFormatted());
		textSecondPlayerBestTime.setText(secondPlayer.getBestTimeFormatted());
		lblSecondPlayerPoints.setText(secondPlayer.getPointsFormatted());
		if(game.playerWinner() == 1) {
			lblFirstPlayerStatus.setText("VENCEU");
			lblSecondPlayerStatus.setText("PERDEU");
		} else if(game.playerWinner() == 2) {
			lblFirstPlayerStatus.setText("PERDEU");
			lblSecondPlayerStatus.setText("VENCEU");
		} else {
			lblFirstPlayerStatus.setText("EMPATOU");
			lblSecondPlayerStatus.setText("EMPATOU");
		}
	}
	
	private void changePanel(int panelOrigin, int panelDestiny) {
		tabbedPane.setEnabledAt(panelOrigin, false);
		tabbedPane.setEnabledAt(panelDestiny, true);
		tabbedPane.setSelectedIndex(panelDestiny);
	}

}
