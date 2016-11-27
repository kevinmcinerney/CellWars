package cellwars.model;

import java.util.ArrayList;

public class Grid {

	private int gridSize;
	private Cell grid[][];

	public Grid(int gridSize) {

		this.setGridSize(18);
		this.grid = new Cell[gridSize][gridSize];
		//System.out.println("this.length: " + this.grid.length);
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid.length; y++) {
				this.grid[x][y] = new Cell(x, y);
				this.grid[x][y].setTeam(0);
			}
		}
	}

	// create and updates surrounding 8-region of nucleus
	public void createNucleiEdges(int player, int x, int y) {

		// left
		if (x > 0) {
			if (this.getGrid()[x - 1][y].getTeam() == 0
					|| (!this.getGrid()[x - 1][y].isNucleus())) {
				this.getGrid()[x - 1][y] = new Cell(x - 1, y);
				if (player == 1) {
					this.getGrid()[x - 1][y].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x - 1][y].setTeam(2);
				}
			}

		}
		// right
		if (x < this.getGridSize() - 1) {
			if (this.getGrid()[x + 1][y].getTeam() == 0
					|| (!this.getGrid()[x + 1][y].isNucleus())) {
				this.getGrid()[x + 1][y] = new Cell(x + 1, y);
				if (player == 1) {
					this.getGrid()[x + 1][y].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x + 1][y].setTeam(2);
				}
			}

		}
		// bottom
		if (y >= 0 && y < this.getGridSize() - 1) {
			if (this.getGrid()[x][y + 1].getTeam() == 0
					|| !this.getGrid()[x][y + 1].isNucleus()) {
				this.getGrid()[x][y + 1] = new Cell(x, y + 1);
				if (player == 1) {
					this.getGrid()[x][y + 1].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x][y + 1].setTeam(2);
				}
			}

		}
		// top
		if (y > 0) {
			if (this.getGrid()[x][y - 1].getTeam() == 0
					|| (!this.getGrid()[x][y - 1].isNucleus())) {
				this.getGrid()[x][y - 1] = new Cell(x, y - 1);
				if (player == 1) {
					this.getGrid()[x][y - 1].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x][y - 1].setTeam(2);
				}
			}
		}
		// top left
		if (y < this.getGridSize() - 1 && x > 0 && y > 0) {
			if (this.getGrid()[x - 1][y - 1].getTeam() == 0
					|| (!this.getGrid()[x - 1][y - 1].isNucleus())) {
				this.getGrid()[x - 1][y - 1] = new Cell(x - 1, y - 1);
				if (player == 1) {
					this.getGrid()[x - 1][y - 1].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x - 1][y - 1].setTeam(2);
				}
			}
		}
		// top right
		if (y < this.getGridSize() - 1 && x < this.getGridSize() - 1 && y > 0
				&& x > 0) {
			if (this.getGrid()[x + 1][y - 1].getTeam() == 0
					|| (!this.getGrid()[x + 1][y - 1].isNucleus())) {
				this.getGrid()[x + 1][y - 1] = new Cell(x - 1, y - 1);
				if (player == 1) {
					this.getGrid()[x + 1][y - 1].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x + 1][y - 1].setTeam(2);
				}
			}
		}
		// bottom left
		if (x > 0 && y < this.getGridSize() - 1) {
			if (this.getGrid()[x - 1][y + 1].getTeam() == 0
					|| (!this.getGrid()[x - 1][y + 1].isNucleus())) {
				this.getGrid()[x - 1][y + 1] = new Cell(x - 1, y + 1);
				if (player == 1) {
					this.getGrid()[x - 1][y + 1].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x - 1][y + 1].setTeam(2);
				}
			}
		}
		// bottom right
		if (y >= 0 && x < this.getGridSize() - 1 && y < this.getGridSize() - 1) {
			if (this.getGrid()[x + 1][y + 1].getTeam() == 0
					|| (!this.getGrid()[x + 1][y + 1].isNucleus())) {
				this.getGrid()[x + 1][y + 1] = new Cell(x + 1, y + 1);
				if (player == 1) {
					this.getGrid()[x + 1][y + 1].setTeam(1);
				} else if (player == 2) {
					this.getGrid()[x + 1][y + 1].setTeam(2);
				}
			}
		}

	}

	public void putNucleiOnGrid(CellTeam player) {

		int x = 0;
		int y = 0;

		for (int i = 0; i < player.getTeam().size(); i++) {
			if (player.getTeam().get(i) != null) {
				x = player.getTeam().get(i).getxPosition();
				y = player.getTeam().get(i).getyPosition();
				this.getGrid()[x][y] = player.getTeam().get(i);
			}
			// add
			createNucleiEdges(player.getTeamNum(), x, y);
		}

	}

	public int isBalanced(int x, int y, int player) {

		int curRight = 0;
		int curDown = 0;
		int curDownRight = 0;

		if (x < this.getGridSize() - 1 && x >= 0 && y < this.getGridSize() - 1
				&& y >= 0) {
			if (this.getGrid()[x + 1][y].getTeam() == 0
					&& this.getGrid()[x][y].getTeam() == 0l) {
				// System.out.println("Cur and curRight cells of interest are null - EXIT isBalanced()");
				return -1;
			} else if (this.getGrid()[x][y + 1].getTeam() == 0
					&& this.getGrid()[x + 1][y + 1].getTeam() == 0) {
				// System.out.println("Bottom and bottomRight cells of interest are null - EXIT isBalanced()");
				return -1;
			} else {

				// System.out.println("One or more cells of interest is not null");
				if (this.getGrid()[x][y].getTeam() != 0
						&& this.getGrid()[x][y].getTeam() == player) {
					// System.out.println("Current cell 0 is " +
					// this.getGrid()[x][y].getxPosition()+ "," +
					// this.getGrid()[x][y].getyPosition());
					this.getGrid()[x][y].setTeam(player);
				}
				// Right
				if (x < this.getGridSize() - 1
						&& this.getGrid()[x + 1][y].getTeam() != 0
						&& this.getGrid()[x + 1][y].getTeam() == player) {
					// System.out.println("Setting curRight= " + player);
					curRight = player;
				}
				// Down
				if (this.getGrid()[x][y + 1].getTeam() != 0
						&& y < this.getGridSize()
						&& this.getGrid()[x][y + 1].getTeam() == player) {
					// System.out.println("Setting curDown= " + player);
					curDown = player;
				}
				// Right Down
				if (x < this.getGridSize() - 1 && y >= 0
						&& this.getGrid()[x + 1][y + 1].getTeam() != 0
						&& this.getGrid()[x + 1][y + 1].getTeam() == player) {
					// System.out.println("Setting curDownRight= " + player);
					curDownRight = player;
				}

				// Current
				if (this.getGrid()[x][y].getTeam() == 0) {
					if (curRight * curDown * curDownRight == (int) Math.pow(
							player, 3)) {
						return 0;
					}
				}
				// Right
				if (x < this.getGridSize() - 1
						&& this.getGrid()[x + 1][y].getTeam() == 0
						&& this.getGrid()[x][y].getTeam() != 0
						&& this.getGrid()[x][y].getTeam() == player) {
					if (curDown * curDownRight * this.getGrid()[x][y].getTeam() == (int) Math
							.pow(player, 3)) {
						return 1;
					}
				}

				// Down
				if (y > 0 && this.getGrid()[x][y + 1].getTeam() == 0
						&& this.getGrid()[x][y].getTeam() != 0
						&& this.getGrid()[x][y].getTeam() == player) {
					if (this.getGrid()[x][y].getTeam() * curDownRight
							* curRight == (int) Math.pow(player, 3)) {
						return 2;
					}
				}
				// Down Right
				if (x < this.getGridSize() - 1 && y >= 0
						&& this.getGrid()[x + 1][y + 1].getTeam() == 0
						&& this.getGrid()[x][y].getTeam() != 0
						&& this.getGrid()[x][y].getTeam() == player) {
					if (this.getGrid()[x][y].getTeam() * curRight * curDown == (int) Math
							.pow(player, 3)) {
						return 3;
					}
				}

				// case 1 for touching corners same player
				if (x < this.getGridSize() - 1
						&& this.getGrid()[x][y].getTeam() == 0
						&& this.getGrid()[x + 1][y + 1].getTeam() == 0) {
					if (this.getGrid()[x][y + 1].getTeam() != 0
							&& this.getGrid()[x + 1][y].getTeam() != 0
							&& this.getGrid()[x][y + 1].getTeam() == player
							&& this.getGrid()[x + 1][y].getTeam() == player) {
						if (curRight * curDown == (int) Math.pow(player, 2)) {
							return 4;
						}
					}
				}

				// case 2 for touching corners same player
				if (x < this.getGridSize() - 1
						&& this.getGrid()[x][y].getTeam() != 0
						&& this.getGrid()[x + 1][y + 1].getTeam() != 0
						&& this.getGrid()[x][y].getTeam() == player
						&& this.getGrid()[x + 1][y + 1].getTeam() == player) {
					if (this.getGrid()[x][y + 1].getTeam() == 0
							&& this.getGrid()[x + 1][y].getTeam() == 0) {
						if (this.getGrid()[x][y].getTeam() * curDownRight == (int) Math
								.pow(player, 2)) {
							return 5;
						}
					}
				}

			}
		}
		return -10;
	}

	public void fillOut(int x, int y, int player) {
		// System.out.println("Setup for player: " + player);
		// System.out.println("I'm on x-" + x+ " y-" + y);
		if (isBalanced(x, y, player) == -1) {
			// System.out.println(x+","+y + " base case");
			return;
		}
		if (isBalanced(x, y, player) == 0) {
			// System.out.println(x+","+y + " being filled");
			this.getGrid()[x][y] = new Cell(x, y);
			this.getGrid()[x][y].setTeam(player);
			if (y > 0) {
				fillOut(x, y - 1, player);
			}
			if (x > 0) {
				fillOut(x - 1, y, player);
			}
		} else if (isBalanced(x, y, player) == 1) {
			// System.out.println(x+","+y + " being filled");
			this.getGrid()[x + 1][y] = new Cell(x + 1, y);
			this.getGrid()[x + 1][y].setTeam(player);
			if (y > 0) {
				fillOut(x, y - 1, player);
			}
			fillOut(x + 1, y, player);
		} else if (isBalanced(x, y, player) == 2) {
			// System.out.println(x+","+y + " being filled");
			this.getGrid()[x][y + 1] = new Cell(x, y + 1);
			this.getGrid()[x][y + 1].setTeam(player);
			fillOut(x, y + 1, player);
			if (x > 0) {
				fillOut(x - 1, y, player);
			}
		} else if (isBalanced(x, y, player) == 3) {
			// System.out.println(x+","+y + " being filled");
			this.getGrid()[x + 1][y + 1] = new Cell(x + 1, y + 1);
			this.getGrid()[x + 1][y + 1].setTeam(player);
			fillOut(x, y + 1, player);
			if (x > 0) {
				fillOut(x - 1, y, player);
			}
		}

		else if (isBalanced(x, y, player) == 4) {
			// System.out.println(x + ", " + y + " is being filled (diagonal)");
			this.getGrid()[x][y] = new Cell(x, y);
			this.getGrid()[x][y].setTeam(player);
			fillOut(x, y - 1, player);
			fillOut(x - 1, y, player);

			this.getGrid()[x + 1][y + 1] = new Cell(x + 1, y + 1);
			this.getGrid()[x + 1][y + 1].setTeam(player);
			fillOut(x, y + 1, player);
			fillOut(x + 1, y, player);

		}

		else if (isBalanced(x, y, player) == 5) {
			// System.out.println(x + "," + y +
			// " is being filled (diagonal case2)");
			this.getGrid()[x + 1][y] = new Cell(x + 1, y);
			this.getGrid()[x + 1][y].setTeam(player);
			fillOut(x + 1, y, player); // go right
			fillOut(x, y - 1, player); // go up

			this.getGrid()[x][y + 1] = new Cell(x, y + 1);
			this.getGrid()[x][y + 1].setTeam(player);
			fillOut(x, y + 1, player);
			fillOut(x - 1, y, player);

		}
		
		

	}
	
	public boolean isGameOver(){
		boolean isAliveTeamOne = false;
		boolean isAliveTeamTwo = false;
		for(int i = 0; i < this.gridSize; i++){
			for(int j = 0; j < this.gridSize; j++){
				if(this.grid[i][j].getTeam() == 1){
					isAliveTeamOne = true;
				}
				if(this.grid[i][j].getTeam() == 2){
					isAliveTeamTwo = true;
				}
				if(isAliveTeamOne && isAliveTeamTwo){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public double evaluateGrid(){
		double p1 = 0, p2 = 0;
		
		for(int i = 0; i < this.gridSize; i++){
			for(int j = 0; j < this.gridSize; j++){
				if(this.grid[i][j].getTeam() == 1){
					if(this.grid[i][j].isNucleus()){
						p1 += 20;
					}
					else{
						p1++;
					}
				}
				else if(this.grid[i][j].getTeam() == 2){
					if(this.grid[i][j].isNucleus()){
						p2 += 20;
					}
					else{
						p2++;
					}
				}
			}
		}
		
		System.out.println("P1 count is " + p1);
		System.out.println("P2 count is " + p2);
		
		if(p1 == 0.0){
			p1 = 0.001;
		}
		return p2 / p1;
		
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public void setGrid(Cell[][] grid) {
		this.grid = grid;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
	
	public void printGrid(){
		
		/*for(int i = 0; i < this.gridSize; i++){
			for(int j = 0; j < this.gridSize; j++){
				System.out.print(this.grid[j][i].getxPosition() + "," + this.grid[j][i].getyPosition() +" ");
				
			}
			System.out.println("");
		}*/
		
		for(int i = 0; i < this.gridSize; i++){
			for(int j = 0; j < this.gridSize; j++){
				System.out.print(this.grid[j][i].getTeam() + " ");
				
			}
			System.out.println("");
		}
		
	}
	
	public void cloneGrid(Grid toBeCopied){
		
		for(int i = 0; i < toBeCopied.gridSize; i++){
			for(int j = 0; j < toBeCopied.gridSize; j++){
				this.grid[j][i] = toBeCopied.grid[j][i];
				
			}
			
		}
	}

}
