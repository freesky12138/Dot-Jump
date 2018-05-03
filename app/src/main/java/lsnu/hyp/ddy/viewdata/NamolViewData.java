package lsnu.hyp.ddy.viewdata;

import java.util.Random;

import lsnu.hyp.ddy.view.CrizyView;
import lsnu.hyp.ddy.view.NamolView;

import android.R.integer;
import android.graphics.Point;

public class NamolViewData {
	// 从不同的地方上去播放不同的音乐
	public static NamolViewData vData = new NamolViewData();

	public int floor = 0;
	private int yNum =7;
	private int xNum = 10;
	public int[][] Hash = new int[yNum][xNum + 2];

	private int[] speed = new int[yNum];
	private int[] now = new int[yNum];

	public Point stayPoint = new Point(5, 0);

	public int changeStar = 1;
	private Random random = new Random();

	public NamolViewData() {
		init();
	}

	private int[][] blackStyle = { { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
			 { 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 1 },
			 { 0, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 0, 0, 0, 0, 1 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 }, { 0, 0, 0, 1, 1, 0, 0, 0, 1, 1 },
			{ 0, 0, 0, 1, 0, 0, 1, 0, 0, 1 }, { 0, 0, 1, 0, 0, 0, 1, 1, 1, 1 },
			{ 0, 0, 1, 0, 0, 1, 0, 0, 1, 1 }, { 0, 0, 1, 0, 0, 1, 1, 1, 1, 1 },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 1, 1 }, { 0, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
	};
	
	int blackStyleSpeed=10;
	public void ChangePoint(int dir) {
		switch (dir) {
		case 1:
			if (NamolView.isMidel) {

				floor++;

				for (int i = 1; i < yNum; i++) {
					for (int j = 0; j <= xNum + 1; j++) {
						Hash[i - 1][j] = Hash[i][j];
					}
				}
				
				if(floor>0&& floor%10==0 && blackStyleSpeed<=16)
				{
					blackStyleSpeed++;
				}
				int tempStyle = random.nextInt(blackStyleSpeed);
				int changeStyle = random.nextInt(xNum);

				if (dirFlag == 0) {
					dirFlag = 1;
				} else {
					dirFlag = 0;
				}
				for (int j = 0; j < xNum; j++) {
					Hash[yNum - 1][j] = blackStyle[tempStyle][j];
						
				}
				Hash[yNum - 1][xNum + 1] = dirFlag;

				ChangeHash(yNum - 1, changeStyle, dirFlag);

			} else {
				stayPoint.y++;
				floor++;

			}
			break;
		case 2:

			floor--;
			if (stayPoint.y > 0) {
				stayPoint.y -= 2;
			}
			break;
		case 3:
			stayPoint.x = (xNum + stayPoint.x - 1) % xNum;
			break;
		case 4:
			stayPoint.x = (xNum + stayPoint.x + 1) % xNum;
			break;

		default:
			break;
		}

	}

	public void ChangeHash(int y, int depth, int flag) {

		int[] temp = new int[xNum];
		for (int i = 0; i < xNum; i++) {
			if (flag > 0) {
				temp[i] = Hash[y][(i + depth) % xNum];
			} else {
				temp[i] = Hash[y][((xNum + i - depth) % xNum)];
			}
		}
		for (int i = 0; i < xNum; i++) {
			Hash[y][i] = temp[i];
		}
	}

	public void haveToChange() {
		int flag;
		if (dirFlag == 1) {
			flag = 0;
		} else {
			flag = 1;
		}

		for (int i = changeStar; i < yNum; i++) {

			// if (i % 2 == 0) {
			now[i]++;
			if (flag == 0) {
				flag = 1;
			} else {
				flag = 0;
			}
			if (now[i] >= speed[i]) {
				ChangeHash(i, 1, flag);
				now[i] = 0;
			}

			// }

		}
	}

	int dirFlag = 1;

	/*
	 * public void changeDown(int d) { changeStar=0; for(int
	 * i=yNum-d*2;i<yNum;i+=2) { for(int j=0;j<xNum;j++) {
	 * Hash[i-2][j]=Hash[i][j]; } }
	 * 
	 * int tempStyle = random.nextInt(12); int changeStyle =
	 * random.nextInt(xNum);
	 * 
	 * if (dirFlag == 0) { dirFlag = 1; } else { dirFlag = 0; } for (int j = 0;
	 * j < xNum; j++) { Hash[yNum-2][j] = blackStyle[tempStyle][j];
	 * 
	 * }
	 * 
	 * ChangeHash(yNum-2, changeStyle, dirFlag);
	 * 
	 * 
	 * }
	 */
	public void init() {
		stayPoint.x = 5;
		stayPoint.y =0;
		blackStyleSpeed=10;
		NamolView.isMidel = false;
		NamolView.speed = 20;
		NamolView.closeOnclick=false;
		floor = 0;
		for (int i = 0; i < yNum; i++) {
			speed[i] = random.nextInt(3);
			now[i] = 0;
			for (int j = 0; j < xNum; j++) {
				Hash[i][j] = 0;
			}
		}

		for (int i = 1; i < yNum; i++) {
			/*
			 * if (i % 2 != 0) { for (int j = 0; j < xNum; j++) { Hash[i][j] =
			 * 1; } } else {
			 */
			int tempStyle = random.nextInt(10);
			int changeStyle = random.nextInt(xNum);

			if (dirFlag == 0) {
				dirFlag = 1;
			} else {
				dirFlag = 0;
			}
			Hash[i][xNum + 1] = dirFlag;
			
			for (int j = 0; j < xNum; j++) {
					Hash[i][j] = blackStyle[tempStyle][j];	
				
			}
			ChangeHash(i, changeStyle, dirFlag);
			// }

		}

	}

	public int getyNum() {
		return yNum;
	}

	public void setyNum(int yNum) {
		this.yNum = yNum;
	}

	public int getxNum() {
		return xNum;
	}

	public void setxNum(int xNum) {
		this.xNum = xNum;
	}

}
