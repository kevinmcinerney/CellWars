package cellwars.model;
	
public class CellandScore {
		
		double score;
	    Cell afterCell;
	    Cell beforeCell;

	    public double getScore() {
			return score;
		}

		public void setScore(double score) {
			this.score = score;
		}

		

		public Cell getAfterCell() {
			return afterCell;
		}

		public void setAfterCell(Cell afterCell) {
			this.afterCell = afterCell;
		}

		public Cell getBeforeCell() {
			return beforeCell;
		}

		public void setBeforeCell(Cell beforeCell) {
			this.beforeCell = beforeCell;
		}

		CellandScore(double score, Cell aftercell, Cell beforeCell) {
	        this.score = score;
	        this.afterCell = aftercell;
	        this.beforeCell = beforeCell;
	    }

	}

