
module dungeon {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.web;
	requires java.sql;
	requires java.prefs;
	requires java.base;
	requires java.desktop;
	requires java.scripting;
	requires java.xml;
	requires transitive javafx.graphics;
	
	exports dungeon to javafx.graphics;
}