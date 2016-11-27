package cellwars.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.MatteBorder;

import cellwars.controller.CellWarsAppController;
import cellwars.model.Cell;
import cellwars.model.CellTeam;

public class CellWarsPanel extends JPanel {
	
	
	private CellWarsAppController baseController;
	private JLabel player1Label, player2Label;  //Player 2
	private JLabel player1CellNumLabel, player2CellNumLabel; // Enter a Cell Number
	private JTextField player1CellNumField, player2CellNumField;
	private JRadioButton player2DownRadio, player1UpRadio, player2UpRadio, player1DownRadio, player2LeftRadio, player2RightRadio, player1LeftRadio, player1RightRadio; // Move down
	private SpringLayout baseLayout;
	private JTextField player2MoveByField, player1MoveByField;
	private JLabel player2MoveByLabel, player1MoveByLabel;
	private JButton player1AttackButton, player2AttackButton;
	private JPanel cells_panel;
	private JPanel[] gridColumns;
	private int gridSize;
	private JPanel panelGrid[][]; 
	public String p1CellNumChosen = "";
	public String p1MoveAmount = "";
	public String p1ActionChosen = "";
	public String p2CellNumChosen = "";
	public String p2MoveAmount = "";
	public String p2ActionChosen = "";
	
	
	public CellWarsPanel(CellWarsAppController baseController){
		
		this.baseController = baseController;
		
		this.gridSize = baseController.getAppFactory().getRealGrid().getGridSize();
		
		player1Label = new JLabel("Player 1");
		player1Label.setBounds(37, 9, 68, 23);
		
		player2Label = new JLabel("Player 2");
		player2Label.setBounds(850, 7, 68, 27);
		this.add(player2Label);
		
		player1CellNumLabel = new JLabel("Cell #");
		player1CellNumLabel.setBounds(20, 50, 41, 24);
		player1CellNumField = new JTextField(5);
		player1CellNumField.setBounds(20, 70, 37, 20);
		
		ButtonGroup bg_player1_controls = new ButtonGroup();
	
		player1DownRadio = new JRadioButton("down");
		player1DownRadio.setBounds(20, 160, 68, 23);
		player1UpRadio = new JRadioButton("up");
		player1UpRadio.setBounds(20, 135, 57, 23);
		player1LeftRadio = new JRadioButton("left");
		player1LeftRadio.setBounds(20, 110, 57, 23);
		player1RightRadio = new JRadioButton("right");
		player1RightRadio.setBounds(20, 90, 68, 23);
		player1RightRadio.setSelected(true);
		bg_player1_controls.add(player1DownRadio);
		bg_player1_controls.add(player1UpRadio);
		bg_player1_controls.add(player1LeftRadio);
		bg_player1_controls.add(player1RightRadio);
		
		ButtonGroup bg_player2_controls = new ButtonGroup();
		player2DownRadio = new JRadioButton("down");
		player2DownRadio.setBounds(850, 160, 68, 23);
		player2UpRadio = new JRadioButton("up");
		player2UpRadio.setBounds(850, 135, 68, 23);
		player2LeftRadio = new JRadioButton("left");
		player2LeftRadio.setBounds(850, 110, 68, 23);
		player2RightRadio = new JRadioButton("right");
		player2RightRadio.setBounds(850, 90, 68, 23);
		player2RightRadio.setSelected(true);
		bg_player2_controls.add(player2DownRadio);
		bg_player2_controls.add(player2UpRadio);
		bg_player2_controls.add(player2LeftRadio);
		bg_player2_controls.add(player2RightRadio);
		
		//should be player 1
		player1MoveByLabel = new JLabel("Move by");
		player1MoveByLabel.setBounds(37, 192, 57, 23);
		player1MoveByField = new JTextField("");
		player1MoveByField.setBounds(37, 226, 37, 20);
		
		player1AttackButton = new JButton("Attack");
		player1AttackButton.setBounds(8, 285, 86, 23);
		
		//should be player2
		player2MoveByLabel = new JLabel("Move by");
		player2MoveByLabel.setBounds(850, 194, 57, 19);
		player2MoveByField = new JTextField(5);
		player2MoveByField.setBounds(850, 226, 41, 20);
		
		player2CellNumLabel = new JLabel("Cell #");
		player2CellNumLabel.setBounds(850, 51, 55, 23);
		player2CellNumField = new JTextField(5);
		player2CellNumField.setBounds(850, 70, 39, 20);
		
		
		player2AttackButton = new JButton("Attack");
		player2AttackButton.setBounds(850, 285, 81, 23);
		
		
		
		//All the cells below
		cells_panel = new JPanel();
		cells_panel.setBounds(113, 9, 730, 550);
		add(cells_panel);
		cells_panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		//make this dynamic
		panelGrid = new JPanel[gridSize][gridSize];
		
		
		gridColumns = new JPanel[gridSize];
		
		for(int i = 0; i < gridSize; i++){
			gridColumns[i] = new JPanel();
			gridColumns[i].setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
			gridColumns[i].setBackground(Color.WHITE);
			cells_panel.add(gridColumns[i]);
			gridColumns[i].setLayout(new GridLayout(0, 1, 0, 0));
			for(int j = 0; j < gridSize; j++){
				panelGrid[i][j] = new JPanel();
				gridColumns[i].add(panelGrid[i][j]);
				panelGrid[i][j].setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				
			}
		}
		
		baseLayout = new SpringLayout();
		setupLayout();
		/**
		System.out.println(baseController.getAppFactory().getPlayer1().getTeam()[0].getxPosition());
		System.out.println(baseController.getAppFactory().getPlayer1().getTeam()[0].getyPosition());
		baseController.getAppFactory().move(0, 1, "left", 1);
		System.out.println(baseController.getAppFactory().getPlayer1().getTeam()[0].getxPosition());
		System.out.println(baseController.getAppFactory().getPlayer1().getTeam()[0].getyPosition());
		**/
		addButtonsFillOutPaint();
		setupListeners();
		
		
		
	}
	
	public void setupLayout(){
		baseLayout = new SpringLayout();

	}
	
	public void addButtonsFillOutPaint(){
		setLayout(null);
		
		this.add(player1UpRadio);
		this.add(player1Label);
		this.add(player1DownRadio);
		this.add(player2DownRadio);
		this.add(player2UpRadio);
		this.add(player1MoveByLabel);
		this.add(player1MoveByField);
		this.add(player2MoveByLabel);
		this.add(player2MoveByField);
		this.add(player1CellNumLabel);
		this.add(player1CellNumField);
		this.add(player1AttackButton);
		this.add(player1AttackButton);
		this.add(player2AttackButton);
		this.add(player2CellNumField);
		this.add(player2CellNumLabel);
		this.add(player1LeftRadio);
		this.add(player1RightRadio);
		this.add(player2LeftRadio);
		this.add(player2RightRadio);
		
		for(int j = 0; j < 5; j++){
			for(int i = 1; i < 3; i++){
				for(int x = 0; x < gridSize; x++){
					for(int y = 0; y < gridSize; y++){
						baseController.getAppFactory().getRealGrid().fillOut(x, y, i);
					}
				}
			}
		}
		
		
		paintGrid();
		
		
		
	}
	
	public void cleanAddNucleiFillOutPaint(CellTeam[] players_arr, int playerAttacker){
		
		for(int x = 0; x < gridSize; x++){
			for(int y = 0; y < gridSize; y++){
				panelGrid[x][y].removeAll();
				revalidate();
				repaint();	
			}
		}
		
		
		if(playerAttacker == 1){
			for(int j = 1; j < players_arr.length && j >= 0; j--){
				baseController.getAppFactory().getRealGrid().putNucleiOnGrid(players_arr[j]);
				paintGrid();
			}
		}
		else if(playerAttacker == 2){
			for(int j = 0; j < players_arr.length; j++){
				baseController.getAppFactory().getRealGrid().putNucleiOnGrid(players_arr[j]);
				paintGrid();
			}
		}
		
		
		for(int j = 0; j < 5; j++){
			for(int i = 1; i < 3; i++){
				for(int x = 0; x < gridSize; x++){
					for(int y = 0; y < gridSize; y++){
						baseController.getAppFactory().getRealGrid().fillOut(x, y, i);
					}
				}
			}
		}
		
		// last modified method 1
		for(int x = 0; x < gridSize; x++){
			for(int y = 0; y < gridSize; y++){
				panelGrid[x][y].removeAll();
				revalidate();
				repaint();	
			}
		}
		
		// last modified method 2
		for(int j = 0; j < 5; j++){
			for(int i = 1; i < 3; i++){
				for(int x = 0; x < gridSize; x++){
					for(int y = 0; y < gridSize; y++){
						baseController.getAppFactory().getRealGrid().fillOut(x, y, i);
					}
				}
			}
		}
		paintGrid();
		
	}
	
	public void paintGrid(){
		for(int x = 0; x < gridSize; x++){
			for(int y = 0; y < gridSize; y++){
				if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getTeam() != 0){
					if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getTeam() == 1){
						if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].isNucleus() == false){
							panelGrid[x][y].setBackground(Color.CYAN);
						}
						else if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].isNucleus()){
							System.out.println("got in nucleus color");
							panelGrid[x][y].setBackground(Color.BLUE);
							JLabel j = new JLabel(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getCellIdNum() + "");
							j.setForeground(Color.WHITE);
							panelGrid[x][y].add(j);
						}
					}
				}
				else if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getTeam() == 0){
					panelGrid[x][y].setBackground(Color.WHITE);
				}
			}
		}
		
		
		/* Playa 2 */
		for(int x = 0; x < gridSize; x++){
			for(int y = 0; y < gridSize; y++){
				if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getTeam() != 0){
					if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getTeam() == 2){
						if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].isNucleus() == false){
							panelGrid[x][y].setBackground(Color.PINK);
						}
						else if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].isNucleus()){
							panelGrid[x][y].setBackground(Color.RED);
							JLabel j = new JLabel(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getCellIdNum() + "");
							j.setForeground(Color.WHITE);
							panelGrid[x][y].add(j);
						}
					}
				}
				else if(baseController.getAppFactory().getRealGrid().getGrid()[x][y].getTeam() == 0){
					panelGrid[x][y].setBackground(Color.WHITE);
					JLabel m = new JLabel("");
					panelGrid[x][y].add(m);
				}
			}
		}
	}
	
	public void setupListeners(){
		player1AttackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (player1CellNumField != null) {
					p1CellNumChosen = player1CellNumField.getText();
				}

				/*System.out.println("Cell number chosen to move by P1: "
						+ p1CellNumChosen);

				System.out.println("Move By field P1 null? "
						+ (player1MoveByField.getText().equals("")));*/
				if (!player1MoveByField.getText().isEmpty()) {
					/*System.out.println("in here + value is "
							+ player1MoveByField.getText());*/
					p1MoveAmount = player1MoveByField.getText();
					//System.out.println("Move amount by P1: " + p1MoveAmount);
				}

				if (player1RightRadio.isSelected()) {
					p1ActionChosen = "right";
				} else if (player1LeftRadio.isSelected()) {
					p1ActionChosen = "left";
				} else if (player1UpRadio.isSelected()) {
					p1ActionChosen = "up";
				} else if (player1DownRadio.isSelected()) {
					p1ActionChosen = "down";
				}

				/*System.out.println("P1 chose to move in direction: "
						+ p1ActionChosen);*/
				
				Cell c = baseController.getAppFactory().getPlayer1().findCell(Integer.parseInt(p1CellNumChosen));
				c.move(p1ActionChosen, Integer.parseInt(p1MoveAmount), baseController.getAppFactory().getRealGrid().getGridSize(), baseController.getAppFactory().getRealGrid().getGrid());
				//int[] two_players = {1,2};
				CellTeam[] ps = {baseController.getAppFactory().getPlayer1(), baseController.getAppFactory().getPlayer2()};
				cleanAddNucleiFillOutPaint(ps, 1);
				
				for(int m = 0; m < gridSize; m++){
					for(int n = 0; n < gridSize; n++){
						//System.out.println("In player1AttackListener, calling capture for x-" + m + " and y-" + n);
						baseController.getAppFactory().getPlayer1().capture(baseController.getAppFactory().getPlayer2(), m, n, baseController.getAppFactory().getRealGrid());
					}
				}
				
				CellTeam[] one_player = {baseController.getAppFactory().getPlayer1()};
				cleanAddNucleiFillOutPaint(one_player, 1);
				
				baseController.getAppFactory().getPlayer1().setSiblings(baseController.getAppFactory().getRealGrid());
				baseController.getAppFactory().getPlayer2().setSiblings(baseController.getAppFactory().getRealGrid());
				baseController.getAppFactory().getV1().setSiblings(baseController.getAppFactory().getVirtualGrid());
				baseController.getAppFactory().getV2().setSiblings(baseController.getAppFactory().getVirtualGrid());
				
				
				System.out.println("/******************/");
				System.out.println("calling minimax");
				baseController.getAppFactory().miniMax(1, 0, -1, true, Double.MAX_VALUE, Double.MIN_VALUE);
				
				
				
				//System.out.println("Returned After Cell x-pos: " + baseController.getAppFactory().getFinalReturnedCS().getAfterCell().getxPosition());
				//System.out.println("Returned After Cell y-pos: " + baseController.getAppFactory().getFinalReturnedCS().getAfterCell().getyPosition());
				//System.out.println("Returned Before Cell x-pos: " + baseController.getAppFactory().getFinalReturnedCS().getBeforeCell().getxPosition());
				//System.out.println("Returned Before Cell y-pos: " + baseController.getAppFactory().getFinalReturnedCS().getBeforeCell().getyPosition());
				

			}
		});

		player2AttackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!player2CellNumField.getText().isEmpty()) {
					p2CellNumChosen = player2CellNumField.getText();
					/*System.out.println("Player 2 chose to move cell num: "
							+ p2CellNumChosen);*/
				}

				if (player2RightRadio.isSelected()) {
					p2ActionChosen = player2RightRadio.getText();
				} else if (player2LeftRadio.isSelected()) {
					p2ActionChosen = player2LeftRadio.getText();
				} else if (player2UpRadio.isSelected()) {
					p2ActionChosen = player2UpRadio.getText();
				} else if (player2DownRadio.isSelected()) {
					p2ActionChosen = player2DownRadio.getText();
				}

				/*System.out.println("Player 2 moving in direction "
						+ p2ActionChosen);*/

				if (!player2MoveByField.getText().isEmpty()) {
					p2MoveAmount = player2MoveByField.getText();
					//System.out.println("Player 2 move amount: " + p2MoveAmount);
				}
				
				Cell c = baseController.getAppFactory().getPlayer2().findCell(Integer.parseInt(p2CellNumChosen));
				c.move(p2ActionChosen, Integer.parseInt(p2MoveAmount), baseController.getAppFactory().getRealGrid().getGridSize(), baseController.getAppFactory().getRealGrid().getGrid());
				CellTeam[] two_players = {baseController.getAppFactory().getPlayer1(), baseController.getAppFactory().getPlayer2()};
				cleanAddNucleiFillOutPaint(two_players, 2);
				
				for(int m = 0; m < gridSize; m++){
					for(int n = 0; n < gridSize; n++){
						//System.out.println("In player1AttackListener, calling capture for x-" + m + " and y-" + n);
						baseController.getAppFactory().getPlayer2().capture(baseController.getAppFactory().getPlayer1(), m, n, baseController.getAppFactory().getRealGrid());
					}
				}
				
				CellTeam[] one_player = {baseController.getAppFactory().getPlayer2()};
				cleanAddNucleiFillOutPaint(one_player, 2);
				
				baseController.getAppFactory().getPlayer1().setSiblings(baseController.getAppFactory().getRealGrid());
				baseController.getAppFactory().getPlayer2().setSiblings(baseController.getAppFactory().getRealGrid());
				baseController.getAppFactory().getV1().setSiblings(baseController.getAppFactory().getVirtualGrid());
				baseController.getAppFactory().getV2().setSiblings(baseController.getAppFactory().getVirtualGrid());
			
			}
			
		
		});
		
	}
}
