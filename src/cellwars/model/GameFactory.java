package cellwars.model;

import java.util.ArrayList;

public class GameFactory {

	Object[] uniqueXarr1, uniqueXarr2;
	private CellTeam player1;
	private CellTeam player2;
	private Grid realGrid;
	private Grid virtualGrid;
	private int plyDepth;
	CellTeam v1 = new CellTeam("VirtualP1", 5, 1);
	CellTeam v2 = new CellTeam("VirtualP2", 5, 2);
	CellandScore finalReturnedCS = new CellandScore(0,new Cell(0,0), new Cell(0,0));
	static Cell celly = new Cell(0,0);
	static double heuristic = 0;
	static int v1counter = 0;
	static int v2counter = 0;

	
	public static double getHeuristic() {
		return heuristic;
	}


	public static void setHeuristic(double heuristic) {
		GameFactory.heuristic = heuristic;
	}


	public static Cell getCelly() {
		return celly;
	}


	public static void setCelly(Cell celly) {
		GameFactory.celly = celly;
	}


	public CellandScore getFinalReturnedCS() {
		return finalReturnedCS;
	}


	public void setFinalReturnedCS(CellandScore finalReturnedCS) {
		this.finalReturnedCS = finalReturnedCS;
	}


	public GameFactory() {

		realGrid = new Grid(18);
		player1 = new CellTeam("Player 1", 5, 1);
		player2 = new CellTeam("Player 2", 5, 2);

		player1.createNuclei(1, realGrid.getGridSize());
		player2.createNuclei(2, realGrid.getGridSize());
		realGrid.putNucleiOnGrid(player1);
		realGrid.putNucleiOnGrid(player2);
		virtualGrid = new Grid(realGrid.getGridSize());
		virtualGrid.cloneGrid(realGrid);
		

	}
	
	
	public Grid getVirtualGrid() {
		return virtualGrid;
	}

	public void setVirtualGrid(Grid virtualGrid) {
		this.virtualGrid = virtualGrid;
	}

	
	public CellTeam getV1() {
		return v1;
	}

	public void setV1(CellTeam v1) {
		this.v1 = v1;
	}

	public CellTeam getV2() {
		return v2;
	}

	public void setV2(CellTeam v2) {
		this.v2 = v2;
	}


	
	public double miniMax(int opponent, int depth, int team, boolean max, double alpha, double beta){
		
		v1.setTeam(player1.getClone());
    	v2.setTeam(player2.getClone());
		
		System.out.println("Printing virtualGrid at start");
		virtualGrid.printGrid();
	
		virtualGrid.putNucleiOnGrid(v1);
		virtualGrid.putNucleiOnGrid(v2);
		
		for(Cell c: v2.getTeamsAvailableMoves()){
    		System.out.print(c.getxPosition() + "," + c.getyPosition() + " ");
    	}
		
		System.out.println("/*******************/");
		
		for(Cell c: v1.getTeamsAvailableMoves()){
    		System.out.print(c.getxPosition() + "," + c.getyPosition() + " ");
    	}
		
		return miniMax(virtualGrid, opponent, depth, team, max, alpha, beta);
	}
	
	
	Cell toMoveto = new Cell(0,0);
	Cell beforeMove = new Cell(0,0);
	double score = 0;
	
	public double miniMax(Grid grid, int opponent, int depth, int team, boolean max, double alpha, double beta) {
        int opponentMarker = getOpponentMarker(opponent);
        System.out.println("Is Game Over? " + grid.isGameOver());
        System.out.println("Depth is " + depth);
       
        
        System.out.println("counter ----------------------------------->   " + v2counter);
        if(grid.isGameOver() || depth == 10000 || v2counter >= v2.getTeamsAvailableMoves().size() || v1counter >= v1.getTeamsAvailableMoves().size()){ 
        	System.out.println("Returning heuristic value of " + grid.evaluateGrid());
        	return grid.evaluateGrid();
        }
      
        
        else if(max) {
        	System.out.println("In max else if of minimax");
        
 
        	//for(int cell = 0; cell < v2.getTeamsAvailableMoves().size(); cell++){
        		
        		System.out.println("Possible move is " + v2.getTeamsAvailableMoves().get(v2counter).getxPosition() + "," + v2.getTeamsAvailableMoves().get(v2counter).getyPosition());
        		
        		Cell parentCell = v2.getTeamsAvailableMoves().get(v2counter).getBelongsTo();
        		v2.getTeam().get(parentCell.getCellIdNum()).moveTo(v2.getTeamsAvailableMoves().get(v2counter));
        		Cell nextCell = v2.getTeamsAvailableMoves().get(v2counter+1);
        		//v2.getTeam().get(cell).setAvailableMoves();
        		//v2.getTeamsAvailableMoves().remove(v2counter);
        		
        		grid = new Grid(this.getRealGrid().getGridSize());
        		
        		grid.putNucleiOnGrid(v1);
        		grid.putNucleiOnGrid(v2);
        		//System.out.println("Following grid should NOT be empty");
        		//grid.printGrid();
    					
        		for(int j = 0; j < 5; j++){
        			for(int i = 1; i < 3; i++){
        				for(int x = 0; x < grid.getGridSize(); x++){
        					for(int y = 0; y < grid.getGridSize(); y++){
        						grid.fillOut(x, y, i);
        					}
        				}
        			}
        		}
        		
        		for(int m = 0; m < grid.getGridSize(); m++){
					for(int n = 0; n < grid.getGridSize(); n++){
						//System.out.println("In player1AttackListener, calling capture for x-" + m + " and y-" + n);
						v2.capture(v1, m, n, grid);
					}
				}
        		
        		//grid.putNucleiOnGrid(v2);
        		
        		for(int j = 0; j < 5; j++){
        			for(int i = 1; i < 3; i++){
        				for(int x = 0; x < grid.getGridSize(); x++){
        					for(int y = 0; y < grid.getGridSize(); y++){
        						grid.fillOut(x, y, i);
        					}
        				}
        			}
        		}
        		
        		v1.cleanSiblingCount();
				v2.cleanSiblingCount();
				v1.setSiblings(grid);
				v2.setSiblings(grid);
				v2counter++;
				alpha = Math.max(alpha, miniMax(grid, opponentMarker, depth + 1, -team, !max, alpha, beta));
				
            	//if(alpha >= beta) break;
        	//}
        	
            return alpha;
        } else {
        	
        	//for(int cell = 0; cell < v1.getTeamsAvailableMoves().size(); cell++){
        		
        		
        		System.out.println("Possible move is " + v1.getTeamsAvailableMoves().get(v1counter).getxPosition() + "," + v1.getTeamsAvailableMoves().get(v1counter).getyPosition());
        		
        		Cell parentCell = v1.getTeamsAvailableMoves().get(v1counter).getBelongsTo();
        		v1.getTeam().get(parentCell.getCellIdNum()).moveTo(v1.getTeamsAvailableMoves().get(v1counter));
        		Cell nextCell = v1.getTeamsAvailableMoves().get(v1counter+1);
        		//v1.getTeam().get(v1counter).setAvailableMoves();
        		//v1.getTeamsAvailableMoves().remove(v1counter);
        		
        		grid = new Grid(this.getRealGrid().getGridSize());
        		
        		grid.putNucleiOnGrid(v2);
        		grid.putNucleiOnGrid(v1);
        		//System.out.println("Following grid should NOT be empty");
        		//grid.printGrid();
    					
        		for(int j = 0; j < 5; j++){
        			for(int i = 1; i < 3; i++){
        				for(int x = 0; x < grid.getGridSize(); x++){
        					for(int y = 0; y < grid.getGridSize(); y++){
        						grid.fillOut(x, y, i);
        					}
        				}
        			}
        		}
        		
        		for(int m = 0; m < grid.getGridSize(); m++){
					for(int n = 0; n < grid.getGridSize(); n++){
						//System.out.println("In player1AttackListener, calling capture for x-" + m + " and y-" + n);
						v1.capture(v2, m, n, grid);
					}
				}
        		
        		//grid.putNucleiOnGrid(v2);
        		
        		for(int j = 0; j < 5; j++){
        			for(int i = 1; i < 3; i++){
        				for(int x = 0; x < grid.getGridSize(); x++){
        					for(int y = 0; y < grid.getGridSize(); y++){
        						grid.fillOut(x, y, i);
        					}
        				}
        			}
        		}
        		
        		v1.cleanSiblingCount();
				v2.cleanSiblingCount();
				v1.setSiblings(grid);
				v2.setSiblings(grid);
				v1counter++;
				beta = Math.min(beta, miniMax(grid, opponentMarker, depth + 1, -team, !max, alpha, beta));
	    		//v1.getTeam().get(cell).moveTo(beforeMove); // reverting
	    		//grid = saved_grid;
				
	    		//if(alpha >= beta) break;
        	//}
        	
        	

            return beta;
        }
    }
	
	public int getOpponentMarker(int opponent){
		
		if(opponent == 1){
			return 2;
		}
		else if(opponent == 2){
			return 1;
		}
		
		return 0;
		
	}
	

	public CellTeam getPlayer1() {
		return player1;
	}

	public void setPlayer1(CellTeam player1) {
		this.player1 = player1;
	}

	public CellTeam getPlayer2() {
		return player2;
	}

	public void setPlayer2(CellTeam player2) {
		this.player2 = player2;
	}

	public boolean isGameOver() {
		if (this.getPlayer1().getTeam().size() == 0
				|| this.getPlayer2().getTeam().size() == 0)
			return true;
		return false;
	}

	public Grid getRealGrid() {
		return realGrid;
	}

	public void setRealGrid(Grid realGrid) {
		this.realGrid = realGrid;
	}

}
