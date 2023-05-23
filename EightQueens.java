package MyCode;

import java.util.*;

public class EightQueens {
	private final static int boardLimit = 8;
	private boolean deBoard[][]; // 無法放置的位置
	private int totalQueens = 0; // 現在皇后總數
	private byte board[][];
	private final byte queen = -1;

	EightQueens() {
		createBoard();
	}

	private void createBoard() {
		board = new byte[8][8];
		deBoard = new boolean[8][8];
	}

	/**
	 * Put down the first chess
	 */
	private void start() {
		Random ran = new Random();
		int x = ran.nextInt(8); //random 0-7
		int y = ran.nextInt(8); // random 0-7

		System.out.println("put down the first chess. X" + x + "Y" + y);

		putChess(x, y);
	}

	/**
	 * Manual movement
	 * 
	 * @param x
	 * @param y
	 */
	private void start(int x, int y) {
		if (x >= boardLimit || y >= boardLimit || x < 0 || y < 0) {
			System.out.println("error posation");
		}

		putChess(x, y);

	}
	
	private void calculate() {
		Stack<Integer> stageList = new Stack<Integer>();
		boolean rollback = false;
		for (int x = 0; x < boardLimit; x++) {
//			showBoard();
			ArrayList<Integer> y_stageList = new ArrayList<Integer>();
			y_stageList = getStageList(x);
			
			if(totalQueens == 8) {
				break;
			}
			
			if (y_stageList.size() == 0) {
//				當下無選擇，可是又沒有皇后放，回歸上一步
				if(checkQueen(x)) {
					continue;
				}
				rollback = true;
				rollback();
				x -= 2;
				continue;
			}

			int index = 0;

			//如果堆疊佇列等於皇后數，表示不是第一次進來
			if (rollback) {
				index = stageList.pop() + 1;
				totalQueens--;
			}

			// 表示上一步沒有可以有方法，需要再退回去
			if (y_stageList.size() < index + 1) {
				rollback();
				x -= 2;
				continue;
			} else {
				// 放置棋子
				putChess(x, y_stageList.get(index));
				stageList.push(index);
				rollback = false;
			}
		}
	}

	/**
	 * 回傳該行可以放置的位置
	 * 
	 * @param x
	 * @return
	 */
	private ArrayList<Integer> getStageList(int x) {
		ArrayList<Integer> resultList = new ArrayList<Integer>();

		for (int i = 0; i < boardLimit; i++) {
			if (board[x][i] == 0) {
				resultList.add(i);
			}else if(board[x][i] < 0) {
				//遇到有皇后就不用理
				resultList.clear();
				return resultList;
			}
		}
		return resultList;
	}

	/**
	 * 檢查該行有沒有皇后，用於手動下過皇后用
	 * @param x
	 * @return
	 */
	private boolean checkQueen(int x) {
		
		for (int i = 0; i < boardLimit; i++) {
			if (board[x][i] == queen) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean putChess(int x, int y) {
		if (board[x][y] > 0) {
			return false;
		}
		
		board[x][y] = queen;
		
		// 將皇后衝突的位置標上
		for (int i = 1; i < boardLimit; i++) {
			// 直線標記
			if (x - i >= 0) {
				board[x - i][y] = (byte) ((board[x - i][y] << 1) + 1);
				deBoard[x - i][y] = true;
			}
			if (x + i < 8) {
				board[x + i][y] = (byte) ((board[x + i][y] << 1) + 1);
				deBoard[x + i][y] = true;
			}
			// 橫線標記
			if (y - i >= 0) {
				board[x][y - i] = (byte) ((board[x][y - i] << 1) + 1);
				deBoard[x][y - i] = true;
			}
			if (y + i < 8) {
				board[x][y + i] = (byte) ((board[x][y + i] << 1) + 1);
				deBoard[x][y + i] = true;
			}

			// 斜線標記
			if (x - i >= 0 && y - i >= 0) {
				board[x - i][y - i] = (byte) ((board[x - i][y - i] << 1) + 1);
				deBoard[x - i][y - i] = true;
			}
			if (x + i < 8 && y + i < 8) {
				board[x + i][y + i] = (byte) ((board[x + i][y + i] << 1) + 1);
				deBoard[x + i][y + i] = true;
			}
			if (x + i < 8 && y - i >= 0) {
				board[x + i][y - i] = (byte) ((board[x + i][y - i] << 1) + 1);
				deBoard[x + i][y - i] = true;
			}
			if (x - i >= 0 && y + i < 8) {
				board[x - i][y + i] = (byte) ((board[x - i][y + i] << 1) + 1);
				deBoard[x - i][y + i] = true;
			}
		}

		//將剩餘沒有推到也跟著往左推一位元
		sortingBoard();
		totalQueens++;
		return true;
	}
	
	/**
	 * 將其餘沒有推到位元的也跟著推
	 */
	private void sortingBoard() {
		for (int i = 0; i < boardLimit; i++) {
			for (int j = 0; j < boardLimit; j++) {
				//將其餘沒有在這輪算到的也跟著加一次
				if (!deBoard[i][j] && board[i][j] >= 0) {
					board[i][j] = (byte) (board[i][j] << 1);
				}
			}
		}
		deBoard = new boolean[8][8];
	}
	
	/**
	 * 將棋子返回上一步
	 */
	private void rollback() {
		for (int i = 0; i < boardLimit; i++) {
			for (int j = 0; j < boardLimit; j++) {
				if(board[i][j] < 0) {
					board[i][j] = 0;
				}else {
					board[i][j] = (byte) (board[i][j] >> 1);
				}
			}
		}
	}

	/**
	 * 顯示棋盤
	 */
	private void showBoard() {
		System.out.println("---------------Board---------------");

		for (int x = 0; x < boardLimit; x++) {
			for (int y = 0; y < boardLimit; y++) {
				if (board[x][y] == 0) {
					System.out.print("X");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) throws Exception{
//		byte t = 1;
//		System.out.println(t << 2);
		EightQueens game = new EightQueens();
		game.start();
		game.calculate();
		game.showBoard();
		
		
		
	}
}
