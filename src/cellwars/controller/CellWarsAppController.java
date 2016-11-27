package cellwars.controller;

import java.util.Random;

import cellwars.model.Cell;
import cellwars.model.CellTeam;
import cellwars.model.GameFactory;
import cellwars.view.CellWarsFrame;

public class CellWarsAppController 
{
	private CellWarsFrame appFrame;
	private GameFactory appFactory;
	
	public CellWarsAppController(){
		this.appFactory = new GameFactory();
	}
	
	
	public CellWarsFrame getAppFrame() {
		return appFrame;
	}


	public void setAppFrame(CellWarsFrame appFrame) {
		this.appFrame = appFrame;
	}


	public GameFactory getAppFactory() {
		return appFactory;
	}


	public void setAppFactory(GameFactory appFactory) {
		this.appFactory = appFactory;
	}
	
	
	
	

	public void start(){
		appFrame = new CellWarsFrame(this);
	}
	
	
}
