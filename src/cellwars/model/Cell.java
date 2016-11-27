package cellwars.model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Cell {
	
	private int xPosition;
	private int yPosition;
	private int maxX, minX, maxY, minY;
	private int team;
	private int cellIdNum;  // 1 - 5
	private int numSiblings;
	private ArrayList<Cell> availableMoves; 
	private Cell belongsTo;

	public Cell getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Cell belongsTo) {
		this.belongsTo = belongsTo;
	}

	public Cell(int x, int y){
		this.xPosition = x;
		this.yPosition = y;
		this.maxX = x+1;
		this.minX =x-1;
		this.maxY =y+1;
		this.minY =y-1;
		this.numSiblings = 1;
		this.availableMoves = new ArrayList<Cell>();
		this.belongsTo = null;
	}
	
	public void setAvailableMoves(){
		
		for(int i = 1; i <= this.getNumSiblings(); i++){
			if((this.getyPosition()+i) < 18 && (this.getyPosition()+i) >= 0){
				Cell downCell = new Cell(this.getxPosition(), this.getyPosition()+i);
				availableMoves.add(downCell);
			}
			
			if((this.getyPosition()-i) < 18 && (this.getyPosition()-i) >= 0){
				Cell upCell = new Cell(this.getxPosition(), this.getyPosition()-i);
				availableMoves.add(upCell);
			}
			
			if((this.getxPosition()-i) < 18 && (this.getxPosition()-i) >= 0){
				Cell leftCell = new Cell(this.getxPosition()-i, this.getyPosition());
				availableMoves.add(leftCell);
			}
			
			if((this.getxPosition()+i) < 18 && (this.getxPosition()+i) >= 0){
				Cell rightCell = new Cell(this.getxPosition()+i, this.getyPosition());
				availableMoves.add(rightCell);
			}
			
		}
		
		//System.out.println("Size of available moves for the cell " + this.cellIdNum + " on team " + this.getTeam() + " is " + availableMoves.size());
		
	}
	
	public void moveTo(Cell newLocation){
		
		
		this.setxPosition(newLocation.getxPosition());
		this.setyPosition(newLocation.getyPosition());
		
	}

	
	public ArrayList<Cell> getAvailableMoves(){
		return availableMoves;
	}
	
	public void move(String direction, int amount, int gridSize, Cell[][] grid) {

		for (int a = 0; a < gridSize; a++) {
			for (int b = 0; b < gridSize; b++) {
				grid[a][b] = new Cell(a,b);
				grid[a][b].setTeam(0);
			}
		}

		if (direction == "up") {
			System.out.println("Going Up");
			if ((this.getyPosition() - amount) >= 0) {
				this.setyPosition(this.getyPosition() - amount);
				//this.setCellIdNum(cell);

			} else {

				JOptionPane.showMessageDialog(null,
						"You cannot move above the visible grid.",
						"Invalid Move", JOptionPane.WARNING_MESSAGE);

			}

		} else if (direction == "down") {
			System.out.println("Going Down");
			if (this.getyPosition() + amount <= (gridSize - 1)) {
				this.setyPosition(this.getyPosition() + amount);
				//this.setCellIdNum(cell);
			} else {

				JOptionPane.showMessageDialog(null,
						"You cannot move below the visible grid.",
						"Invalid Move", JOptionPane.WARNING_MESSAGE);

			}

		} else if (direction == "left") {
			System.out.println("Going Left");
			if (this.getxPosition() - amount >= 0) {
				this.setxPosition(this.getxPosition() - amount);
				//this.setCellIdNum(cell);
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"You cannot move outside the left region of the visible grid.",
								"Invalid Move", JOptionPane.WARNING_MESSAGE);

			}
		} else if (direction == "right") {
			System.out.println("Going Right");
			if (this.getxPosition() + amount <= (gridSize - 1)) {
				this.setxPosition(this.getxPosition() + amount);
				//this.setCellIdNum(cell);
			} else {

				JOptionPane
						.showMessageDialog(
								null,
								"You cannot move outside the right region of the visible grid.",
								"Invalid Move", JOptionPane.WARNING_MESSAGE);

			}
		}

		System.out.println(this.getxPosition() + "," + this.getyPosition());

	}
	
	public int getNumSiblings() {
		return numSiblings;
	}

	public void setNumSiblings(int numSiblings) {
		this.numSiblings = numSiblings;
	}

	private boolean isNucleus = false;
	
	public boolean isNucleus() {
		return isNucleus;
	}

	public void setNucleus(boolean isNucleus) {
		this.isNucleus = isNucleus;
	}
	
	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}
	
	public int getCellIdNum() {
		return cellIdNum;
	}

	public void setCellIdNum(int cellIdNum) {
		this.cellIdNum = cellIdNum;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}
	

}
