package sweepmine;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class MineCountPanel extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MineCountPanel() {
		this(0);
	}

	public MineCountPanel(int mineCount) {
	}

	@Override
	public void update(Observable o, Object arg) {
		CodeConstants.TimeNumberImage.values();
	}
}
