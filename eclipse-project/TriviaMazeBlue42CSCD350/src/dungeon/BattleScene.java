/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author patrick
 */
public class BattleScene implements Drawable {

    private Hero mHero;
    private Monster mMonster;
    private int numTurns;
    private int turn;
    private int round;
    private int itemSelected = 0;
    boolean showMenu;
    private String textToShow;
    private ArrayList<String> items;
    private BooleanProperty battleOver;

    private final Image bgImage = new Image("images/battle-background.jpg");
    private final Image gravestoneImage = new Image("images/gravestone.png");
    private final double lineWidth = 1.0f;
    private final String fontString = "System";
    private final double fontSize = .05;
    private final String potionString = "Potion of Healing.";
    private final String runString = "Run away in terror.";

    public BattleScene(Hero hero, Monster monster) {
        this.battleOver = new SimpleBooleanProperty(false);
        this.mHero = hero;
        this.mMonster = monster;
        this.numTurns = (monster == null) ? 3 : (hero.getAttackSpeed() / monster.getAttackSpeed());
        this.round = 1;
        if (numTurns <= 0) {
            numTurns++;
        }
        turn = 1;
        items = new ArrayList();
        if (hero.getNumPotionsHeal() > 0) {
            items.add(potionString);
        }
        for (Attack a : hero.getAttackList()) {
            items.add("" + a.getName());
        }
        items.add(runString);
        showMenu = true;
        textToShow = "";
    }

    public BooleanProperty isBattleOver() {
        return battleOver;
    }

    @Override
    public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(bgImage, imgX, imgY, offset, offset);
        double tenPercent = offset * 0.1f;
        double fivePercent = offset * 0.05f;
        double thirtyFivePercent = offset * 0.35f;
        gc.save();
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(lineWidth);
        gc.setFont(new Font(fontString, fontSize * offset));

        String monsterName = (mMonster == null) ? "Air" : mMonster.getName();

        gc.fillText("Round:  " + this.round, imgX + tenPercent, imgY + fivePercent);
        gc.strokeText("Round:  " + this.round, imgX + tenPercent, imgY + fivePercent);

        gc.fillText(mHero.getName(), imgX + tenPercent, imgY + tenPercent, thirtyFivePercent);
        gc.strokeText(mHero.getName(), imgX + tenPercent, imgY + tenPercent, thirtyFivePercent);
        gc.fillText("Vs.", imgX + tenPercent + thirtyFivePercent, imgY + tenPercent, tenPercent);
        gc.strokeText("Vs.", imgX + tenPercent + thirtyFivePercent, imgY + tenPercent, tenPercent);
        gc.fillText(monsterName, imgX + tenPercent + thirtyFivePercent + tenPercent, imgY + tenPercent, thirtyFivePercent);
        gc.strokeText(monsterName, imgX + tenPercent + thirtyFivePercent + tenPercent, imgY + tenPercent, thirtyFivePercent);

        double heroHealth = (double) mHero.getHitPoints() / (double) mHero.getMaxHitPoints();
        double monsterHealth = (mMonster == null) ? 1.0 : (double) mMonster.getHitPoints() / (double) mMonster.getMaxHitPoints();

        gc.setFill(Color.DARKRED);

        gc.strokeRect(imgX + tenPercent, imgY + tenPercent + (tenPercent / 10.0f), thirtyFivePercent, tenPercent / 10.0f);
        gc.fillRect(imgX + tenPercent, imgY + tenPercent + (tenPercent / 10.0f), thirtyFivePercent * heroHealth, tenPercent / 10.0f);

        gc.strokeRect(imgX + tenPercent + thirtyFivePercent + tenPercent, imgY + tenPercent + (tenPercent / 10.0f), thirtyFivePercent, tenPercent / 10.0f);
        gc.fillRect(imgX + tenPercent + thirtyFivePercent + tenPercent, imgY + tenPercent + (tenPercent / 10.0f), thirtyFivePercent * monsterHealth, tenPercent / 10.0f);

        double heroTopX = imgX;
        double heroTopY = imgY + tenPercent + tenPercent;
        mHero.draw(heroTopX, heroTopY, 0, 0, offset / 2.0f, canvas);

        if (mMonster != null) {
            mMonster.draw(heroTopX + (offset / 2.0f), heroTopY, 0, 0, offset / 2.0f, canvas);
        }

        if (this.turn > this.numTurns) {
            showMenu = false;
            this.turn = 0;

            gc.fillText("Monster's turn ", imgX + tenPercent, imgY + thirtyFivePercent + thirtyFivePercent + fivePercent);
            gc.strokeText("Monster's turn ", imgX + tenPercent, imgY + thirtyFivePercent + thirtyFivePercent + fivePercent);

            if (this.mMonster != null) {
                textToShow = this.mMonster.attack(mHero);
            } else {
                textToShow = "The wind blows, " + mHero.getName() + " is uneffected";
            }
        } else {
            gc.fillText("Turn " + turn + " of " + numTurns, imgX + tenPercent, imgY + thirtyFivePercent + thirtyFivePercent + fivePercent);
            gc.strokeText("Turn " + turn + " of " + numTurns, imgX + tenPercent, imgY + thirtyFivePercent + thirtyFivePercent + fivePercent);

        }
        if (!this.mHero.isAlive() && !showMenu) {
            this.mHero.setCharacterImage(gravestoneImage);
            this.isBattleOver().set(true);
            showMenu = false;
            textToShow = mHero.getName() + " has been killed";
        } else if (mMonster != null && !mMonster.isAlive() && showMenu) {
            this.isBattleOver().set(true);
            showMenu = false;
            textToShow = mMonster.getName() + " has been killed";
            
        }
        else if (mMonster == null && !showMenu) {
            this.isBattleOver().set(true);
            showMenu = false;
            textToShow = mHero.getName() + " attacks the air\nThe air blows away in terror";
        }

        gc.setFill(Color.WHITE);
        double listX = imgX + tenPercent;
        double listY = imgY + thirtyFivePercent + thirtyFivePercent + tenPercent;

        if (showMenu) {
            double listSpace = fivePercent;
            for (int i = 0; i < items.size(); i++) {
                gc.fillText(items.get(i), listX, listY + (i * listSpace));
                gc.strokeText(items.get(i), listX, listY + (i * listSpace));
                if (i == itemSelected) {
                    gc.fillText("->", listX - fivePercent, listY + (i * listSpace));
                    gc.strokeText("->", listX - fivePercent, listY + (i * listSpace));
                }
            }
        } else {
            gc.fillText(textToShow, listX, listY, offset - tenPercent);
            gc.strokeText(textToShow, listX, listY, offset - tenPercent);
            this.turn++;
        }

        //if (!this.mHero.isAlive()) {
        //    this.isBattleOver().set(true);
        //}

        gc.restore();
    }

    public void onEnter() {
        if (showMenu) {
            showMenu = false;
            textToShow = "Unknown command";
            if (items.get(itemSelected).equals(potionString)) {
                textToShow = mHero.getName() + " drank the potion and was \nhealed " + this.mHero.usePotionHeal() + " hitpoints";
                if (this.mHero.getNumPotionsHeal() <= 0) {
                    items.remove(itemSelected);
                }
                //this.turn++;
            }
            if (items.get(itemSelected).equals(runString)) {
                this.battleOver.set(true);
                textToShow = runString;
            }
            for (Attack a : this.mHero.getAttackList()) {
                if (a.getName().equals(items.get(itemSelected))) {
                    if (this.mMonster != null) {
                         textToShow = a.attackOpponent(mMonster);
                    } 
                    //textToShow = (this.mMonster == null) ? mHero.getName() + " Attacked the air and failed." : a.attackOpponent(mMonster);
                    //this.turn++;
                }
            }

        } else if (this.turn > this.numTurns) {
            //this.turn = 1;
        } else {
            showMenu = true;
        }
    }

    public void onUp() {
        itemSelected = (itemSelected - 1 + items.size()) % items.size();
    }

    public void onDown() {
        itemSelected = (itemSelected + 1) % items.size();
    }

}
