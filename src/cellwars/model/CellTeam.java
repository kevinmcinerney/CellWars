package cellwars.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CellTeam {

	private int numCells; // default 5 each
	private String teamType; // H for human, R for robot
	private ArrayList<Cell> team;

	// Is it your turn - t or f
	private int plyDepth; // Depth of minimax tree
	private Boolean alphaBetaPruning; // Better performance
	private int teamSize;
	private int teamNum;

	public CellTeam(String teamName, int numCells, int teamNum) {
		this.numCells = numCells;
		this.teamSize = numCells;
		this.teamNum = teamNum;

		ArrayList<Cell> arr = new ArrayList<Cell>(numCells * 2);
		for (int i = 0; i < numCells; i++) {
			arr.add(i, new Cell(0, 0));
		}

		setTeam(arr);
	}
	
	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	public Cell findCell(int id) {
		Cell mover = null;

		for (Cell c : this.getTeam()) {
			if (c.getCellIdNum() == id) {
				mover = c;
				System.out.println("Found mover in team " + this.getTeam());
				break;
			}
		}

		return mover;
	}

	public void setSiblings(Grid grid) {
		
		this.cleanSiblingCount();
		

		for (int i = 0; i < this.getTeam().size(); i++) {
			int orig_x = this.getTeam().get(i).getxPosition();
			int orig_y = this.getTeam().get(i).getyPosition();
			for (int j = i + 1; j < this.getTeam().size(); j++) {
				int const_x = this.getTeam().get(i).getxPosition();
				int y1 = this.getTeam().get(i).getyPosition();
				int y2 = this.getTeam().get(j).getyPosition();
				if (y1 >= y2) {
					//System.out.println("y1 " + y1 + " and y2 is " + y2);
					while (y1 >= y2
							&& grid.getGrid()[const_x][y1].getTeam() != 0) {
						//System.out.println("Decrementing y1 to " + y1);
						//System.out.println("1");
						y1--;
					}
					y1++;
				} else if (y1 <= y2) {
					//System.out.println("y1 " + y1 + " and y2 is " + y2);
					while (y1 <= y2
							&& grid.getGrid()[const_x][y1].getTeam() != 0) {
						//System.out.println("Incrementing y1 to " + y1);
						//System.out.println("2");
						y1++;
					}
					y1--;
				}

				/* compare x-positions */
				int x1 = this.getTeam().get(i).getxPosition();
				int x2 = this.getTeam().get(j).getxPosition();
				if (x1 >= x2) {
					/*System.out.println("x1 " + x1 + " and x2 is " + x2
							+ " and y1 is " + y1);*/
					while (x1 >= x2 && grid.getGrid()[x1][y1].getTeam() != 0) {
						//System.out.println("Decrementing x1 to " + x1);
						//System.out.println("3");
						x1--;

						if (x1 == x2 && y1 == y2) {
							grid.getGrid()[orig_x][orig_y]
									.setNumSiblings(grid.getGrid()[orig_x][orig_y]
											.getNumSiblings() + 1);
							grid.getGrid()[x1][y1].setNumSiblings(grid
									.getGrid()[x1][y1].getNumSiblings() + 1);
						}
					}
				} else if (x1 <= x2) {
					/*System.out.println("x1 " + x1 + " and x2 is " + x2
							+ " and y1 is " + y1);*/
					while (x1 <= x2 && grid.getGrid()[x1][y1].getTeam() != 0) {
						//System.out.println("Incrementing x1 to " + x1);
						//System.out.println("4");
						x1++;

						if (x1 == x2 && y1 == y2) {
							grid.getGrid()[orig_x][orig_y]
									.setNumSiblings(grid.getGrid()[orig_x][orig_y]
											.getNumSiblings() + 1);
							grid.getGrid()[x1][y1].setNumSiblings(grid
									.getGrid()[x1][y1].getNumSiblings() + 1);
						}
					}
				}
			}
		}

		/*System.out.println("PRINTING NUM SIBLINGS");
		for (Cell c : this.getTeam()) {
			System.out.println("Player " + this.getTeamNum()
					+ " team cell num " + c.getCellIdNum() + " pos at "
					+ c.getxPosition() + ", " + c.getyPosition());
			System.out.println("Player " + this.getTeamNum()
					+ " team cell num " + c.getCellIdNum() + " has "
					+ c.getNumSiblings() + " siblings");
		}*/

	}

	public void cleanSiblingCount() {

		for (Cell c : this.getTeam()) {
			c.setNumSiblings(1);
		}

		
	}

	public void createNuclei(int teamNum, int gridSize) {
		int playerPoints[] = new int[numCells];
		if (teamNum == 1) {
			playerPoints = getUniquePoints(0, (gridSize / 2) - 2, gridSize);
		} else if (teamNum == 2) {
			playerPoints = getUniquePoints((gridSize / 2) + 1, gridSize - 1,
					gridSize);
		}

		int k = 0;
		int j = 0;
		for (int i = 0; i < playerPoints.length / 2; i++) {

			this.getTeam().get(i).setxPosition(playerPoints[k++]);
			this.getTeam().get(i).setyPosition(playerPoints[k++]);
			this.getTeam().get(i).setNucleus(true);
			this.getTeam().get(i).setTeam(teamNum);
			this.getTeam().get(i).setCellIdNum(i + 1);

		}
	}

	public int[] getUniquePoints(int xMin, int xMax, int gridSize) {

		int[][] uniqueCheck = new int[gridSize][gridSize];

		for (int[] row : uniqueCheck)
			Arrays.fill(row, -1);

		int[] uniquePoints = new int[10];

		for (int i = 0; i < 10; i += 2) {
			int x = randInt(xMin, xMax);
			int y = randInt(0, gridSize - 1);
			if (uniqueCheck[x][y] == -1) {
				uniqueCheck[x][y] = 1;
				uniquePoints[i] = x;
				uniquePoints[i + 1] = y;
			} else {
				i -= 2;
			}
		}

		return uniquePoints;
	}

	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public void capture(CellTeam attackedPlayer, int x, int y, Grid grid) {
		int attacker = this.getTeamNum();
		int attackee = attackedPlayer.getTeamNum();

		if (x >= 0 && (x < grid.getGridSize() - 1)
				&& grid.getGrid()[x][y].getTeam() != 0
				&& grid.getGrid()[x + 1][y].getTeam() != 0) {

			/* Capture case 1 for 2 players */
			if (grid.getGrid()[x][y].getTeam() == (attacker)
					&& grid.getGrid()[x + 1][y].getTeam() == attackee) {

				//System.out.println("Attacking team is " + attacker);
				Cell cur = grid.getGrid()[x][y];
				//System.out.println("Currently processing cur as "
				//		+ cur.getxPosition() + " ," + cur.getyPosition());

				Cell curRight = grid.getGrid()[x + 1][y];

				/*System.out.println("Currently processing curRight as "
						+ curRight.getxPosition() + " ,"
						+ curRight.getyPosition());
				System.out.println("Cur team is " + cur.getTeam());
				System.out.println("CurRight's team is " + curRight.getTeam());

				System.out.println("CurRight is a nucleus? "
						+ curRight.isNucleus());*/

				if (curRight.isNucleus()) {
					//System.out
					//		.println("Setting curRight team to attacking team");

					curRight.setTeam(attacker);

					/*System.out.println("curRight's new team is "
							+ curRight.getTeam());*/

					// store old id of captured cell
					int capturedCellOldId = attackedPlayer.getTeam().indexOf(
							curRight);
					// get size of attacking team

					// set captured player to have this cell id
					/*System.out.println("Before incrementing player1 team "
							+ this.getTeam().size());*/
					// add new cell to player 1 cell team

					this.getTeam().add(curRight);

					// remove this cell from other team
					attackedPlayer.getTeam().remove(capturedCellOldId);

					curRight.setCellIdNum(this.getTeam().size());

					/*System.out.println("Just incremented player1 + "
							+ this.getTeam().size());

					System.out.println("New cellIdNum is "
							+ curRight.getCellIdNum());*/
					// find captured player in other team

					// decrement size of losing team
					grid.createNucleiEdges(this.getTeamNum(),
							curRight.getxPosition(), curRight.getyPosition());

					/*for (Cell c : this.getTeam()) {
						System.out.println("Player " + this.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " pos at " + c.getxPosition() + ", "
								+ c.getyPosition());
						System.out.println("Player " + this.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " has " + c.getNumSiblings() + " siblings");
					}
					for (Cell c : attackedPlayer.getTeam()) {
						System.out.println("Player " + attackee
								+ " team cell num " + c.getCellIdNum()
								+ " pos at " + c.getxPosition() + ", "
								+ c.getyPosition());
						System.out.println("Player " + attackee
								+ " team cell num " + c.getCellIdNum()
								+ " has " + c.getNumSiblings() + " siblings");
					}*/

				}
			}

			/* Capture case 2 for 2 players */
			else if (grid.getGrid()[x][y].getTeam() == attackee
					&& grid.getGrid()[x + 1][y].getTeam() == attacker) {

				//System.out.println("Attacking team is " + this.getTeamNum());
				Cell cur = grid.getGrid()[x][y];
				/*System.out.println("Currently processing cur as "
						+ cur.getxPosition() + " ," + cur.getyPosition());*/
				Cell curRight = grid.getGrid()[x + 1][y];
				/*System.out.println("Currently processing curRight as "
						+ curRight.getxPosition() + " ,"
						+ curRight.getyPosition());
				System.out.println("Cur team is " + cur.getTeam());
				System.out.println("CurRight's team is " + curRight.getTeam());*/

				System.out.println("Cur is a nucleus? " + cur.isNucleus());
				if (cur.isNucleus()) {
					//System.out.println("Setting cur team to attacking team");
					cur.setTeam(this.getTeamNum());
					//System.out.println("cur's new team is " + cur.getTeam());

					// store old id of captured cell
					int capturedCellOldId = attackedPlayer.getTeam().indexOf(
							cur);
					// get size of attacking team

					// set captured player to have this cell id
					/*System.out.println("Before incrementing player1 team "
							+ this.getTeam().size());*/
					// add new cell to player 1 cell team

					this.getTeam().add(cur);

					// remove this cell from other team
					attackedPlayer.getTeam().remove(capturedCellOldId);

					cur.setCellIdNum(this.getTeam().size());

					grid.createNucleiEdges(this.getTeamNum(),
							cur.getxPosition(), cur.getyPosition());
				}

			}

		}

		if (y >= 0 && (y < grid.getGridSize() - 1)
				&& grid.getGrid()[x][y].getTeam() != 0
				&& grid.getGrid()[x][y + 1].getTeam() != 0) {

			/* Case 3 for 2 players */
			if (grid.getGrid()[x][y].getTeam() == (attacker)
					&& grid.getGrid()[x][y + 1].getTeam() == (attackee)) {

				//System.out.println("Attacking team is " + this.getTeamNum());
				Cell cur = grid.getGrid()[x][y];
				/*System.out.println("Currently processing cur as "
						+ cur.getxPosition() + " ," + cur.getyPosition());*/

				Cell curDown = grid.getGrid()[x][y + 1];

				/*System.out.println("Currently processing curDown as "
						+ curDown.getxPosition() + " ,"
						+ curDown.getyPosition());
				System.out.println("Cur team is " + cur.getTeam());
				System.out.println("CurDown's team is " + curDown.getTeam());

				System.out.println("CurDown is a nucleus? "
						+ curDown.isNucleus());*/

				if (curDown.isNucleus()) {
					/*System.out
							.println("Setting curDown team to attacking team");*/

					curDown.setTeam(this.getTeamNum());

					/*System.out.println("curDown's new team is "
							+ curDown.getTeam());*/

					// store old id of captured cell
					int capturedCellOldId = attackedPlayer.getTeam().indexOf(
							curDown);
					// get size of attacking team

					// set captured player to have this cell id
					/*System.out.println("Before incrementing player1 team "
							+ this.getTeam().size());*/
					// add new cell to player 1 cell team

					this.getTeam().add(curDown);

					// remove this cell from other team
					attackedPlayer.getTeam().remove(capturedCellOldId);

					curDown.setCellIdNum(this.getTeam().size());

					/*System.out.println("Just incremented player1 + "
							+ this.getTeam().size());

					System.out.println("New cellIdNum is "
							+ curDown.getCellIdNum());*/
					// find captured player in other team

					// decrement size of losing team
					grid.createNucleiEdges(this.getTeamNum(),
							curDown.getxPosition(), curDown.getyPosition());

					/*for (Cell c : this.getTeam()) {
						System.out.println("Player " + this.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " pos at " + c.getxPosition() + ", "
								+ c.getyPosition());
						System.out.println("Player " + this.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " has " + c.getNumSiblings() + " siblings");
					}
					for (Cell c : attackedPlayer.getTeam()) {
						System.out.println("Player "
								+ attackedPlayer.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " pos at " + c.getxPosition() + ", "
								+ c.getyPosition());
						System.out.println("Player "
								+ attackedPlayer.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " has " + c.getNumSiblings() + " siblings");
					}*/

				}

			}

			/* Case 4 for 2 players */
			else if (grid.getGrid()[x][y].getTeam() == (attackee)
					&& grid.getGrid()[x][y + 1].getTeam() == attacker) {

				//System.out.println("Attacking team is " + this.getTeamNum());
				Cell cur = grid.getGrid()[x][y];
				/*System.out.println("Currently processing cur as "
						+ cur.getxPosition() + " ," + cur.getyPosition());*/

				Cell curDown = grid.getGrid()[x][y + 1];

				/*System.out.println("Currently processing curDown as "
						+ curDown.getxPosition() + " ,"
						+ curDown.getyPosition());
				System.out.println("Cur team is " + cur.getTeam());
				System.out.println("CurDown's team is " + curDown.getTeam());

				System.out.println("Cur is a nucleus? " + cur.isNucleus());*/

				if (cur.isNucleus()) {
					//System.out.println("Setting cur team to attacking team");

					cur.setTeam(this.getTeamNum());

					//System.out.println("cur's new team is " + cur.getTeam());

					// store old id of captured cell
					int capturedCellOldId = attackedPlayer.getTeam().indexOf(
							cur);
					// get size of attacking team

					// set captured player to have this cell id
					/*System.out.println("Before incrementing player1 team "
							+ this.getTeam().size());*/
					// add new cell to player 1 cell team

					this.getTeam().add(cur);

					// remove this cell from other team
					attackedPlayer.getTeam().remove(capturedCellOldId);

					cur.setCellIdNum(this.getTeam().size());

					/*System.out.println("Just incremented player1 + "
							+ this.getTeam().size());

					System.out
							.println("New cellIdNum is " + cur.getCellIdNum());*/
					// find captured player in other team

					grid.createNucleiEdges(this.getTeamNum(),
							cur.getxPosition(), cur.getyPosition());

					/*for (Cell c : this.getTeam()) {
						System.out.println("Player " + this.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " pos at " + c.getxPosition() + ", "
								+ c.getyPosition());
						System.out.println("Player " + this.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " has " + c.getNumSiblings() + " siblings");
					}
					for (Cell c : attackedPlayer.getTeam()) {
						System.out.println("Player "
								+ attackedPlayer.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " pos at " + c.getxPosition() + ", "
								+ c.getyPosition());
						System.out.println("Player "
								+ attackedPlayer.getTeamNum()
								+ " team cell num " + c.getCellIdNum()
								+ " has " + c.getNumSiblings() + " siblings");
					}*/

				}

			}

		}

	}
	
	public ArrayList<Cell> getClone(){
		ArrayList<Cell> clonedTeam = (ArrayList<Cell>)this.getTeam().clone();
		
		return clonedTeam;
	}
	
	public ArrayList<Cell> getTeamsAvailableMoves(){
		
		ArrayList<Cell> teamsAvailMoves = new ArrayList<Cell>();
		for(Cell c: this.getTeam()){
			c.setAvailableMoves();
			for(Cell d: c.getAvailableMoves()){
				d.setBelongsTo(c);
				teamsAvailMoves.add(d);
			}
		}
		final ArrayList<Cell> bla = teamsAvailMoves;
		return bla;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public int getNumCells() {
		return numCells;
	}

	public void setNumCells(int numCells) {
		this.numCells = numCells;
	}

	public ArrayList<Cell> getTeam() {
		return team;
	}

	public void setTeam(ArrayList<Cell> team) {
		this.team = team;
	}

}
