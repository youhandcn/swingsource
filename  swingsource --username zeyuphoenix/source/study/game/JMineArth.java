package game;

/**
 * This program is written by Jerry Shen(Shen Ji Feng) use the technology of
 * SWING GUI and the OO design
 * 
 * @author Jerry Shen all rights reserved.
 * Email:jerry.shen@cognizant.com; jerry_shen_sjf@yahoo.com.cn
 * Please report bug to these emails.
 * Open source under GPLv3
 * 
 * version 1.2
 */

public class JMineArth {
	public int [][] mine;
	private boolean fMineSet;

	JMineArth(int mineNum, int row, int col) {
		mine = new int[10][10];
		setMine(mineNum, row, col);
		setMineNum();
	}

	private void setMine(int mineNum, int Outrow, int Outcol) {
		int col=0, row = 0, i=0;
		//Math.srand(now);
		while (i < mineNum) {
			col = (int)(Math.random()*100)%10;
			row = (int)(Math.random()*100)%10;
			if (mine[col][row]==0 && (row!=Outrow || col!=Outcol || Outrow==10 )) {
				mine[row][col]=9;
				i++;
			}
		}
	}


	private void setMineNum() {
		for ( int i=0 ; i <10; i++) {
			for (int j=0; j < 10; j++) {
				mine[i][j]=mine[i][j]==9?9:checkMineNum(i,j);
			}
		}
		fMineSet = true;
	}

	private int checkMineNum(int ii,int jj) {
		int top,bottom, left, right, count=0;
		top=ii-1>0?ii-1:0;
		bottom=ii+1<10?ii+1:9;
		left=jj-1>0?jj-1:0;
		right=jj+1<10?jj+1:9;
		for (int i=top; i<=bottom; i++) {
			for(int j=left; j<= right; j++) {
				if (mine[i][j]==9) count++;
			}
		}
		return(count);
	}

	public void printMine() {
		for (int i = 0; i < 10; i++) {
			for (int j=0; j < 10; j++) {
				System.out.print(this.mine[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		JMineArth mine = new JMineArth(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		mine.printMine();
	}
}
