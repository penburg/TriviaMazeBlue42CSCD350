/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author patrick modified from a game made for CSCD370 fall 2018
 */
public class DungeonAdventure extends Application {

	private Label mStatus;
	private Dungeon mGame;

	private final double CONTROL_IMAGE_WIDTH = 25;
    private Stage mStage;

	@Override
	public void start(Stage primaryStage){
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		boolean firstRun = prefs.getBoolean("firstRun", true);
		if(firstRun) {
			prefs.putBoolean("firstRun", false);
			String userDataDir = getUserDataDirectory(getClass().getCanonicalName());
			File dataDir = new File(userDataDir);
			if(!dataDir.exists()) {
				dataDir.mkdirs();
			}
			if(dataDir.isDirectory()) {
				InputStream db = getClass().getResourceAsStream("/questions.db");
				if(db == null) {
					//System.out.println("db null");
				}
				Path outPath = FileSystems.getDefault().getPath(userDataDir, "questions.db");
				try {
					Files.copy(db,  outPath,  StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("DB Copy Error");
					alert.setHeaderText("Error While coping DB to user data dir");
					alert.showAndWait();
					e.printStackTrace();
				}

			}
			//System.out.println(userDataDir);
		}
		mStage = primaryStage;
		mGame = new Dungeon();

		BorderPane root = new BorderPane();
		root.setCenter(mGame);

		// add the menus
		root.setTop(buildMenuBar());

		mStatus = new Label();
		mStatus.setMinHeight(mStatus.getMinHeight() * 3);
		mStatus.textProperty().bind(mGame.getStatusStringProperty());
		ToolBar toolBar = new ToolBar(mStatus);

		root.setBottom(toolBar);

		Scene scene = new Scene(root, 750, 800);
		scene.setOnKeyPressed(keyEvent -> onKeyPressed(keyEvent));
		primaryStage.setTitle("Blue42 Maze Game");
		primaryStage.setScene(scene);

		primaryStage.show();



	}

	private void onAbout() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("Team Blue43, CSCD 350 Final Project, Fall 2019");
		alert.showAndWait();

	}

	private MenuBar buildMenuBar() {
		// from pdf provided
		// build a menu bar
		MenuBar menuBar = new MenuBar();
		// File menu with just a quit item for now
		Menu fileMenu = new Menu("_File");
		MenuItem openMenuItem = new MenuItem("_Open");
		openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		openMenuItem.setOnAction(notUsed -> onOpen());
		
		MenuItem saveMenuItem = new MenuItem("_Save");
		saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		saveMenuItem.setOnAction(notUsed -> onSave());
		
		MenuItem quitMenuItem = new MenuItem("_Quit");
		quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
		quitMenuItem.setOnAction(notUsed -> Platform.exit());
		
		fileMenu.getItems().addAll(openMenuItem, saveMenuItem, new SeparatorMenuItem(), quitMenuItem);

		Menu GameMenu = new Menu("_Game");
		MenuItem newMenuItem = new MenuItem("_New");
		newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		newMenuItem.setOnAction(actionEvent -> onNewGame());


		MenuItem settingsMenuItem = new MenuItem("S_ettings");
		settingsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		settingsMenuItem.setOnAction(actionEvent -> onSettings());

		MenuItem addQuestionMenuItem = new MenuItem("_Add Question");
		addQuestionMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
		addQuestionMenuItem.setOnAction(actionEvent -> onAddQuestion());

		GameMenu.getItems().addAll(newMenuItem, new SeparatorMenuItem(), addQuestionMenuItem, settingsMenuItem);

		// Help menu with just an about item for now
		Menu helpMenu = new Menu("_Help");
		MenuItem aboutMenuItem = new MenuItem("_About");
		aboutMenuItem.setOnAction(actionEvent -> onAbout());

		MenuItem controlsMenuItem = new MenuItem("_Controls");
		controlsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
		controlsMenuItem.setOnAction(actionEvent -> onShowControls());

		helpMenu.getItems().addAll(controlsMenuItem,  new SeparatorMenuItem(), aboutMenuItem);
		menuBar.getMenus().addAll(fileMenu, GameMenu, helpMenu);
		return menuBar;

	}


	/**
	 * Opens a file chooser dialog to save the file
	 * 
	 * @return void
	 */
	private void onSave() {
			File selectedFile = null;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Blue42 Trivia Maze");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Blue42 Trivia Maze Files (*.b42m)", "*.b42m"),
                    new ExtensionFilter("All Files (*.*)", "*.*"));

            selectedFile = fileChooser.showSaveDialog(mStage);
      
        if (selectedFile != null) {
        	if(!selectedFile.getName().endsWith(".b42m")) {
        		selectedFile = new File(selectedFile.getAbsolutePath() + ".b42m");
        	}
        	mGame.saveGame(selectedFile);
        	
        } else {
        	mGame.getStatusStringProperty().set("Save aborted");
        }
	}

	/**
	 * Opens a file chooser dialog to open the file
	 * 
	 * @return void
	 */
	private void onOpen() {
		 FileChooser chooser = new FileChooser();
	        chooser.setTitle("Open an Blue42 Trivia Maze File");
	        chooser.setInitialDirectory(new File("."));
	        chooser.getExtensionFilters().addAll(
	                new ExtensionFilter("MyRectangle Files (*.b42m)", "*.b42m"),
	                new ExtensionFilter("All Files (*.*)", "*.*"));
	        File selectedFile = chooser.showOpenDialog(mStage);
	        if (selectedFile != null) {
	        	mGame.openGame(selectedFile);
	        }
	        else {
	        	mGame.getStatusStringProperty().set("open aborted");
	        }
	        

	}

	private void onNewGame() {
		mGame.newGame();
		mGame.draw();
	}

	private void onShowControls() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Controls");
		alert.setHeaderText("Controls and Symbols ");

		GridPane gp = new GridPane();
		gp.setHgap(CONTROL_IMAGE_WIDTH);
		gp.setVgap(5.0);

		gp.add(new Label("▲"), 0, 0);
		gp.add(new Label("▼"), 0, 1);
		gp.add(new Label("◀"), 0, 2);
		gp.add(new Label("▶"), 0, 3);

		gp.add(new Label("Move Up"), 1, 0);
		gp.add(new Label("Move Down"), 1, 1);
		gp.add(new Label("Move Left"), 1, 2);
		gp.add(new Label("Move Right"), 1, 3);

		gp.add(new Label("v"), 0, 4);
		gp.add(new Label("enter"), 0, 5);

		gp.add(new Label("Use Vision Potion"), 1, 4);
		gp.add(new Label("Enter / Execute"), 1, 5);


		ImageView potionVision = new ImageView(Room.IMAGE_POTION_VISION);
		potionVision.setFitWidth(CONTROL_IMAGE_WIDTH);
		potionVision.setPreserveRatio(true);
		gp.add(potionVision, 0, 6);
		gp.add(new Label("Vision Potion"), 1, 6);

		ImageView potionEntrance = new ImageView(Room.IMAGE_ENTRANCE);
		potionEntrance.setFitWidth(CONTROL_IMAGE_WIDTH);
		potionEntrance.setPreserveRatio(true);
		gp.add(potionEntrance, 0, 7);
		gp.add(new Label("Entrance"), 1, 7);

		ImageView potionExit = new ImageView(Room.IMAGE_EXIT);
		potionExit.setFitWidth(CONTROL_IMAGE_WIDTH);
		potionExit.setPreserveRatio(true);
		gp.add(potionExit, 0, 8);
		gp.add(new Label("Exit"), 1, 8);


		alert.getDialogPane().setContent(gp);


		alert.showAndWait();

	}

	private void onSettings() {
		try {
			SettingDialogController control = new SettingDialogController(mGame);
			control.show();
		} catch (IOException ex) {
			Logger.getLogger(DungeonAdventure.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void onAddQuestion() {
		try {
			AddQuestionDialog control = new AddQuestionDialog(mGame);
			control.show();
		} catch (IOException ex) {
			Logger.getLogger(DungeonAdventure.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void onKeyPressed(KeyEvent keyEvent) {;
	switch (keyEvent.getCode()) {
	case LEFT:
		mGame.onLeft();
		break;
	case RIGHT:
		mGame.onRight();
		break;
	case UP:
		mGame.onUp();
		break;
	case DOWN:
		mGame.onDown();
		break;
	case V:
		mGame.onUseVisionPotion();
		break;
	case C:
		mGame.onCheatCode();
		break;
	case U:
		mGame.onDoorCheatCode();
		break;
	case T:
		mGame.traverseMaze();
		break;
	case ENTER:
		mGame.onEnter();
		break;
	default:
		break;

	}
	//keyEvent.consume();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) { 
		launch(args);
	}

	public static String getUserDataDirectory(String className) {
		String userHome = System.getProperty("user.home");
		String pathSeperator = System.getProperty("file.separator");
		String ret = userHome + pathSeperator;
		String os = System.getProperty("os.name").toLowerCase();
		if(os.contains("win")) {
			ret += "AppData" + pathSeperator + "Local" 
					+ pathSeperator + className;
		}
		else if(os.contains("linux")) {
			ret += ".config" + pathSeperator + className;
		}
		else if(os.contains("mac")) {

		}
		return ret;

	}


}
