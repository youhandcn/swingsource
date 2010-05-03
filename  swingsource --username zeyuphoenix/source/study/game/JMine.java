package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.util.Timer;
import java.util.TimerTask;

public class JMine extends JFrame implements MouseListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMineArth mine;

	private JMineButton[][] mineButton;

	private GridBagConstraints constraints;

	private JPanel pane;

	private GridBagLayout gridbag;

	private boolean gameStarted;

	private static JCounter mineCounter;

	private static JCounter timeCounter;

	private Timer timer;

	private Timer winTimer = new Timer();

	public int numMine;

	public int numFlaged;

	private JMenuBar mb;

	private JMenu mGame;

	private JMenuItem miEasy;

	private JMenuItem miMiddle;

	private JMenuItem miHard;

	private JMenuItem miExit;

	private JMenu mHelp;

	private JMenuItem miAbout;

	private JPanel controlPane;

	private JButton bTest;

	private AboutFrame about;

	private WinFrame winFrame;

	private ImageIcon[] mineNumIcon = {
			new ImageIcon(JMine.class.getResource("blank1.gif")),
			new ImageIcon(JMine.class.getResource("1.gif")),
			new ImageIcon(JMine.class.getResource("2.gif")),
			new ImageIcon(JMine.class.getResource("3.gif")),
			new ImageIcon(JMine.class.getResource("4.gif")),
			new ImageIcon(JMine.class.getResource("5.gif")),
			new ImageIcon(JMine.class.getResource("6.gif")),
			new ImageIcon(JMine.class.getResource("7.gif")),
			new ImageIcon(JMine.class.getResource("8.gif")),
			new ImageIcon(JMine.class.getResource("0.gif")) };

	private ImageIcon[] mineStatus = {
			new ImageIcon(JMine.class.getResource("blank1.gif")),
			new ImageIcon(JMine.class.getResource("flag.gif")),
			new ImageIcon(JMine.class.getResource("question.gif")) };

	private ImageIcon[] mineBombStatus = {
			new ImageIcon(JMine.class.getResource("0.gif")),
			new ImageIcon(JMine.class.getResource("mine.gif")),
			new ImageIcon(JMine.class.getResource("wrongmine.gif")),
			new ImageIcon(JMine.class.getResource("bomb.gif")) };

	private ImageIcon[] faceIcon = {
			new ImageIcon(JMine.class.getResource("smile.gif")),
			new ImageIcon(JMine.class.getResource("Ooo.gif")) };

	// You lose
	private void bomb(int row, int col) {
		try {
			// System.out.println("Bomb!");

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					mineButton[i][j].setIcon(mineBombStatus[0]);
					int toShow;
					toShow = mine.mine[i][j] != 9 ? 0 : 1;
					mineButton[i][j].setClickFlag(true);
					if (toShow == 1 && (i != row || j != col)) {
						mineButton[i][j].setIcon(mineBombStatus[toShow]);
						mineButton[i][j].setClickFlag(true);
					} else if (toShow == 1 && (i == row && j == col)) {
						mineButton[i][j].setIcon(mineBombStatus[3]);
						mineButton[i][j].setClickFlag(true);
					} else if (toShow == 0 && mineButton[i][j].getFlag() != 1) {
						mineButton[i][j].setEnabled(false);
					} else if (toShow == 0 && mineButton[i][j].getFlag() == 1) {
						mineButton[i][j].setIcon(mineBombStatus[2]);
						mineButton[i][j].setClickFlag(true);
					}
				}
			}

			timer.cancel();

		} catch (Exception e) {

		}
	}

	// check if you win() {
	private boolean isWin() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (mine.mine[i][j] == 9 && mineButton[i][j].getFlag() != 1) {
					return (false);
				}
				if (mine.mine[i][j] != 9 && mineButton[i][j].getFlag() == 1) {
					return (false);
				}
				if (mine.mine[i][j] != 9
						&& mineButton[i][j].getClickFlag() == false) {
					return (false);
				}
			}
		}
		return (true);
	}

	// You Win
	private void win() {
		timer.cancel();
		winFrame.setVisible(true);

		winTimer.schedule(new TimerTask() {
			public void run() {
				while (!winFrame.getWinOk()) {
				}
				numMine = winFrame.getMineNum();
				winFrame.setVisible(false);
				setNewGame(numMine);
				// System.out.println("Jerry Debug:"+numMine);
				this.cancel();
				winFrame.setWinOk(false);
			}
		}, 0L);

	}

	// Constructor of the game
	public JMine() {
		super("JMine Game");
		setSize(250, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Insets space = new Insets(0, 0, 0, 0);

		// Game vars
		gameStarted = false;
		numMine = 12;
		numFlaged = 0;

		ImageIcon myIcon = new ImageIcon(JMine.class.getResource("blank1.gif"));
		gridbag = new GridBagLayout();
		constraints = new GridBagConstraints();
		pane = new JPanel();
		pane.setLayout(gridbag);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;

		// Begin Menu Set
		mb = new JMenuBar();
		mGame = new JMenu("Game");
		miEasy = new JMenuItem("Easy");
		miEasy.addActionListener(this);
		miMiddle = new JMenuItem("Middle");
		miMiddle.addActionListener(this);
		miHard = new JMenuItem("Hard");
		miHard.addActionListener(this);
		miExit = new JMenuItem("Exit");
		miExit.addActionListener(this);
		mGame.add(miEasy);
		mGame.add(miMiddle);
		mGame.add(miHard);
		mGame.addSeparator();
		mGame.add(miExit);
		mb.add(mGame);

		mHelp = new JMenu("Help");
		miAbout = new JMenuItem("About...");
		mHelp.add(miAbout);
		miAbout.addActionListener(this);
		mb.add(mHelp);
		this.setJMenuBar(mb);
		// end of Menu Set

		// Control Panel
		controlPane = new JPanel();
		bTest = new JButton(faceIcon[0]);
		bTest.setSize(26, 27);
		bTest.setMargin(space);
		bTest.addMouseListener(this);
		bTest.setPressedIcon(faceIcon[1]);

		mineCounter = new JCounter(numMine);
		timeCounter = new JCounter();
		controlPane.add(mineCounter);
		controlPane.add(bTest);
		controlPane.add(timeCounter);
		buildConstraints(constraints, 0, 0, 10, 2, 100, 100);
		gridbag.setConstraints(controlPane, constraints);
		pane.add(controlPane);

		// Bottons
		mineButton = new JMineButton[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButton[i][j] = new JMineButton(i, j, myIcon);
				mineButton[i][j].addMouseListener(this);
				mineButton[i][j].setMargin(space);
				buildConstraints(constraints, j, i + 3, 1, 1, 100, 100);
				gridbag.setConstraints(mineButton[i][j], constraints);
				pane.add(mineButton[i][j]);
			}
		}

		// Content Pane
		setContentPane(pane);
		setLocation(200, 150);
		setVisible(true);

		// About Frame
		about = new AboutFrame("JMine About");
		winFrame = new WinFrame("You win!");
	}

	// Set the GUI objects positions
	void buildConstraints(GridBagConstraints gbc, int gx, int gy, int gw,
			int gh, int wx, int wy) {
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
	}

	// the methods to check if there were mines, to be nested
	void checkMine(int row, int col) {
		int i, j;
		i = row < 0 ? 0 : row;
		i = i > 9 ? 9 : i;
		j = col < 0 ? 0 : col;
		j = j > 9 ? 9 : j;
		// System.out.println("Check Mine row:"+i + ",col:" +j);
		if (mine.mine[i][j] == 9) {
			bomb(i, j);
		} else if (mine.mine[i][j] == 0
				&& mineButton[i][j].getClickFlag() == false) {
			mineButton[i][j].setClickFlag(true);
			showLabel(i, j);
			for (int ii = i - 1; ii <= i + 1; ii++)
				for (int jj = j - 1; jj <= j + 1; jj++)
					checkMine(ii, jj);

		} else {
			showLabel(i, j);
			mineButton[i][j].setClickFlag(true);
		}
		if (isWin()) {
			win();
		}
	}

	private void clearAll(int row, int col) {
		int top, bottom, left, right;
		top = row - 1 > 0 ? row - 1 : 0;
		bottom = row + 1 < 10 ? row + 1 : 9;
		left = col - 1 > 0 ? col - 1 : 0;
		right = col + 1 < 10 ? col + 1 : 9;
		for (int i = top; i <= bottom; i++) {
			for (int j = left; j <= right; j++) {
				if (mineButton[i][j].getFlag() != 1)
					checkMine(i, j);
			}
		}

	}

	private void resetAll() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButton[i][j].setFlag(0);
				mineButton[i][j].setClickFlag(false);
				mineButton[i][j].setIcon(mineStatus[0]);
				mineButton[i][j].setEnabled(true);
				mineButton[i][j].setVisible(true);
			}
		}
	}

	// to flag the mine you want to flag out
	void flagMine(int row, int col) {
		// System.out.println("Jerry Arrives here!");
		int i, j;
		i = row < 0 ? 0 : row;
		i = i > 9 ? 9 : i;
		j = col < 0 ? 0 : col;
		j = j > 9 ? 9 : j;
		if (mineButton[i][j].getFlag() == 0) {
			numFlaged++;
		} else if (mineButton[i][j].getFlag() == 1) {
			numFlaged--;
		}
		mineCounter.resetCounter(numMine - numFlaged >= 0 ? numMine - numFlaged
				: 0);
		mineButton[i][j].setFlag((mineButton[i][j].getFlag() + 1) % 3);
		showFlag(i, j);
		if (isWin()) {
			win();
		}
	}

	// show the numbers of the nearby mines
	void showLabel(int row, int col) {
		// System.out.println("ShowLabel row:" + row + ",col:" + col);
		int toShow;
		toShow = mine.mine[row][col];
		if (toShow != 0) {
			mineButton[row][col].setIcon(mineNumIcon[toShow]);
			mineButton[row][col].setClickFlag(true);
			// mineButton[row][col].setEnabled(false);
		} else {
			// mineButton[row][col].setIcon(mineNumIcon[0]);
			// mineButton[row][col].setClickFlag(true);
			mineButton[row][col].setEnabled(false);
		}
	}

	// circle the flag with blank, flaged, questioned
	void showFlag(int row, int col) {
		mineButton[row][col]
				.setIcon(mineStatus[mineButton[row][col].getFlag()]);
	}

	// the mouse events listener methods
	public void mouseEntered(MouseEvent e) {
		// System.out.println("Jerry Test");

	}

	// method to start the new game
	private void startNewGame(int num, int row, int col) {
		mine = new JMineArth(num, row, col);
		// mine.printMine();
		gameStarted = true;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timeCounter.counterAdd();
				// System.out.println(timeCounter.getCounterNum());
			}
		}, 1000, 1000);
	}

	public void setNewGame(int num) {
		resetAll();
		numMine = num;
		numFlaged = 0;
		gameStarted = false;
		mineCounter.resetCounter(numMine);
		timeCounter.resetCounter(0);
	}

	// the event handle to deal with the mouse click
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == bTest) {
			setNewGame(numMine);
			return;
		}
		int row, col;
		row = ((JMineButton) e.getSource()).getRow();
		col = ((JMineButton) e.getSource()).getCol();
		if (!gameStarted) {
			startNewGame(numMine, row, col);
		}

		if (e.getModifiers() == (InputEvent.BUTTON1_MASK + InputEvent.BUTTON3_MASK)) {
			// System.out.println("HA");
			clearAll(row, col);
		}
		if (!mineButton[row][col].getClickFlag()) {

			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				// System.out.println("LeftButton");
				if (mineButton[row][col].getFlag() == 1) {
					return;
				} else {
					checkMine(row, col);
				}
			} else if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
				// System.out.println("RightButton");
				flagMine(row, col);
			} else {
				// System.out.println("MiddleButton");
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// System.out.println("Jerry Press");

	}

	public void mouseReleased(MouseEvent e) {
		// System.out.println("Jerry Release");
	}

	public void mouseExited(MouseEvent e) {
		// System.out.println("Jerry Exited");

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == miEasy) {
				setNewGame(12);
				return;
			}
			if (e.getSource() == miMiddle) {
				setNewGame(24);
				return;
			}
			if (e.getSource() == miHard) {
				setNewGame(36);
				return;
			}
			if (e.getSource() == miExit) {
				System.exit(0);
			}
			if (e.getSource() == miAbout) {
				about.setVisible(true);
			}
		} catch (Exception ie) {
		}
	}

	public static void main(String[] args) {
		JMine jmine = new JMine();
		jmine.setVisible(true);
	}
}
