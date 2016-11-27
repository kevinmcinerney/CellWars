package cellwars.view;

import javax.swing.JFrame;

import cellwars.controller.CellWarsAppController;

public class CellWarsFrame extends JFrame {
	
	private CellWarsPanel basePanel;
	
	public CellWarsFrame(CellWarsAppController baseController){
		
		this.basePanel = new CellWarsPanel(baseController);
		setUpFrame();
	}
	
	private void setUpFrame(){
		this.setContentPane(basePanel);
		this.setSize(1100,640);
		this.setVisible(true);
		
	}
	
}
