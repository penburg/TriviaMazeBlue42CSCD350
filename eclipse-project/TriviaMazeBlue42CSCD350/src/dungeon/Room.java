/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.Random;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author penburg
 */
public class Room implements Drawable {

    private boolean isVisable;
    private boolean hasPit;
    private boolean isEntrance;
    private boolean isExit;
    private boolean doors[] = {false, false, false, false};
    private String treasure;
    private Monster monster;
    private Hero hero;
    private int[] heroLoc;
    private int[][] roomGrid;
    private StringProperty statusString;

    private final int PROBIBILITY = 10;
    public static final int ROOMSIZE = 3;
    private final int ROOM_MID = ROOMSIZE / 3;
    private final int EMPTY = 0;
    private final int HERO = 1;
    private final int MONSTER = 2;
    private final int ENTRANCE = 3;
    private final int EXIT = 4;
    private final int POTION_HEAL = 5;
    private final int POTION_VISION = 6;
    private final int POTION_GREEN = 7;
    private final int TREASURE = 8;
    private final double wallWidth = 3;
    private final double doorWidth = 5;

    public static final Image IMAGE_POTION_HEAL = new Image("images/potion-red.png");
    public static final Image IMAGE_POTION_VISION = new Image("images/potion-blue.png");
    public static final Image IMAGE_POTION_GREEN = new Image("images/potion-green.png");
    public static final Image IMAGE_TREASURE = new Image("images/pillar2.png");
    public static final Image IMAGE_EXIT = new Image("images/exit.png");
    public static final Image IMAGE_ENTRANCE = new Image("images/ladder.png");

    private static final Random rand = new Random();
    private static final MonsterFactory monsterFactory = new MonsterFactory();

    public Room(StringProperty statusS) {
        //System.out.println("Creating room");
        statusString = statusS;
        roomGrid = new int[ROOMSIZE][ROOMSIZE];
        heroLoc = new int[2];
        isVisable = false;
        isExit = false;
        isEntrance = false;
        
        //Heal Potion
        if (rand.nextInt(100) <= PROBIBILITY) {
            setItem(POTION_HEAL);
        }
        //Vision Potion
        if (rand.nextInt(100) <= PROBIBILITY) {
            setItem(POTION_VISION);
        }
        //Monster
        if (rand.nextInt(100) <= PROBIBILITY) {
            this.monster = monsterFactory.createMonster(monsterFactory.randomMonster());
            setItem(MONSTER);
        }

        hasPit = (rand.nextInt(100) <= PROBIBILITY);

    }

    public Monster getMonster() {
        return monster;
    }

    public final void setItem(int item) {
        int x = rand.nextInt(ROOMSIZE);
        int y = rand.nextInt(ROOMSIZE);
        while (roomGrid[x][y] != EMPTY) {
            x = rand.nextInt(ROOMSIZE);
            y = rand.nextInt(ROOMSIZE);
        }
        roomGrid[rand.nextInt(ROOMSIZE)][rand.nextInt(ROOMSIZE)] = item;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        this.isVisable = true;
        //roomGrid[ROOMSIZE / 2][ROOMSIZE / 2] = HERO;
        if (hasPit) {
            this.statusString.set(hero.getName() + " Fell in a pit\n" + hero.subtractHitPoints(rand.nextInt(19) + 1));
            //hero.subtractHitPoints(rand.nextInt(19) + 1);
        }
    }

    public int[] getHeroLoc() {
        return heroLoc;
    }

    public void setHeroLoc(int x, int y) {

        if (x < 0) {
            if (y == ROOM_MID && doors[3]) {
                this.hero = null;
            } else {
                x = 0;
            }
        }

        if (y < 0) {
            if (x == ROOM_MID && doors[0]) {
                this.hero = null;
            } else {
                y = 0;
            }
        }

        if (x >= ROOMSIZE) {
            if (y == ROOM_MID && doors[1]) {
                this.hero = null;
            } else {
                x = ROOMSIZE - 1;
            }
        }

        if (y >= ROOMSIZE) {
            if (x == ROOM_MID && doors[2]) {
                this.hero = null;
            } else {
                y = ROOMSIZE - 1;
            }
        }

        this.heroLoc[0] = x;
        this.heroLoc[1] = y;
        //move Monster
        int monsterX = x, monsterY = y;
        if (this.monster != null && this.hero != null) {
            for (int i = 0; i < ROOMSIZE; i++) {
                for (int j = 0; j < ROOMSIZE; j++) {
                    if (roomGrid[i][j] == MONSTER) {
                        monsterX = i;
                        monsterY = j;
                        break;
                    }
                }
            }
            roomGrid[monsterX][monsterY] = EMPTY;
            if (x > monsterX && monsterX + 1 < ROOMSIZE && roomGrid[monsterX + 1][monsterY] == EMPTY) {
                monsterX ++;
            } else if (x < monsterX && monsterX - 1 >= 0  && roomGrid[monsterX - 1][monsterY] == EMPTY) {
                monsterX --;
            } else if (y > monsterY && monsterY + 1 < ROOMSIZE && roomGrid[monsterX][monsterY + 1] == EMPTY) {
                monsterY ++;
            } else if (y < monsterY && monsterY - 1 >= 0  && roomGrid[monsterX][monsterY - 1] == EMPTY) {
                monsterY --;
            }

            roomGrid[monsterX][monsterY] = MONSTER;

        }
        if (this.hero != null) {
            switch (roomGrid[x][y]) {
                case TREASURE:
                    this.hero.addTreasure(treasure);
                    this.statusString.set(this.hero.getName() + " found the \"" + treasure + "\" pillar.");
                    roomGrid[x][y] = EMPTY;
                    break;
                case MONSTER:
                    this.statusString.set(this.monster.attack(this.hero));
                    if(!this.monster.isAlive()){
                        roomGrid[x][y] = EMPTY;
                        this.monster = null;
                    }
                    break;
                case ENTRANCE:
                    break;
                case EXIT:
                    break;
                case POTION_HEAL:
                    this.hero.addPotionHeal();
                    this.statusString.set(this.hero.getName() + " found a potion of healing");
                    roomGrid[x][y] = EMPTY;
                    break;
                case POTION_VISION:
                    this.hero.addPotionVision();
                    this.statusString.set(this.hero.getName() + " found a potion of vision");
                    roomGrid[x][y] = EMPTY;
                    break;
                case POTION_GREEN://future use
                    break;
                default:
                    break;
            }
        }
    }

    public boolean[] getDoors() {
        return doors;
    }

    public void setIsVisable(boolean isVisable) {
        this.isVisable = isVisable;
    }

    public void setIsEntrance() {
        roomGrid = new int[ROOMSIZE][ROOMSIZE];
        roomGrid[0][0] = ENTRANCE;
        this.monster = null;
        this.isExit = false;
        this.isEntrance = true;
        this.isVisable = true;
        this.hasPit = false;
    }

    public void setIsExit() {
        roomGrid = new int[ROOMSIZE][ROOMSIZE];
        roomGrid[ROOMSIZE - 1][ROOMSIZE - 1] = EXIT;
        this.monster = null;
        this.isEntrance = false;
        this.isExit = true;
        this.hasPit = false;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
        int x = rand.nextInt(ROOMSIZE);
        int y = rand.nextInt(ROOMSIZE);
        while (roomGrid[x][y] != EMPTY) {
            x = rand.nextInt(ROOMSIZE);
            y = rand.nextInt(ROOMSIZE);
        }
        roomGrid[rand.nextInt(ROOMSIZE)][rand.nextInt(ROOMSIZE)] = TREASURE;

    }

    public boolean hasTreasure() {
        return treasure != null;
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public boolean isExit() {
        return isExit;
    }

    @Override
    public void draw(double imgX, double imgY, int x, int y, double offset, Canvas canvas) {
        doors[0] = (y != 0);
        doors[1] = (x < Dungeon.BOARDSIZE - 1);
        doors[2] = (y < Dungeon.BOARDSIZE - 1);
        doors[3] = (x != 0);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (!isVisable) {
            canvas.getGraphicsContext2D().fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
        } else {
            if (hasPit) {
                gc.setFill(Color.color(1, 0, 0, .25));
                canvas.getGraphicsContext2D().fillRect(imgX + (x * offset), imgY + (y * offset), offset, offset);
                gc.setFill(Color.BLACK);
            }
            double topX = imgX + (x * offset);
            double topY = imgY + (y * offset);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(wallWidth);
            gc.strokeRect(topX, topY, offset, offset);
            double doorLength = (double) offset / 3f;
            gc.setStroke(Color.WHITESMOKE);
            gc.setLineWidth(doorWidth);
            if (doors[0]) {
                gc.strokeLine(topX + doorLength, topY, topX + doorLength + doorLength, topY);
            }
            if (doors[1]) {
                gc.strokeLine(topX + offset, topY + doorLength, topX + offset, topY + doorLength + doorLength);
            }
            if (doors[2]) {
                gc.strokeLine(topX + doorLength, topY + offset, topX + doorLength + doorLength, topY + offset);
            }
            if (doors[3]) {
                gc.strokeLine(topX, topY + doorLength, topX, topY + doorLength + doorLength);
            }
            double roomOffset = offset / (double) ROOMSIZE;
            for (int i = 0; i < ROOMSIZE; i++) {
                for (int j = 0; j < ROOMSIZE; j++) {
                    switch (roomGrid[i][j]) {
                        case TREASURE:
                            //System.out.println("draw rock at " + i + "," + j);
                            gc.drawImage(IMAGE_TREASURE, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
                            break;
                        case MONSTER:
                            if (this.monster != null && this.monster.isAlive()) {
                                
                                this.monster.draw(imgX + (x * offset), imgY + (y * offset), i, j, roomOffset, canvas);
                            }
                            else{
                                this.monster = null;
                            }
                            //gc.drawImage(ROCK_CRACKED_IMAGE, (roomOffset * i) + imgX, (roomOffset * j) + imgY, roomOffset, roomOffset);
                            break;
                        case ENTRANCE:
                            gc.drawImage(IMAGE_ENTRANCE, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
                            break;
                        case EXIT:
                            gc.drawImage(IMAGE_EXIT, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
                            break;
                        case POTION_HEAL:
                            gc.drawImage(IMAGE_POTION_HEAL, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
                            break;
                        case POTION_VISION:
                            gc.drawImage(IMAGE_POTION_VISION, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
                            break;
                        case POTION_GREEN:
                            gc.drawImage(IMAGE_POTION_GREEN, (roomOffset * i) + imgX + (x * offset), (roomOffset * j) + imgY + (y * offset), roomOffset, roomOffset);
                            break;
                        default:
                            break;
                    }
                }

            }

            if (this.hero != null) {
                this.hero.draw(imgX + (x * offset), imgY + (y * offset), heroLoc[0], heroLoc[1], roomOffset, canvas);
            }

        }
    }

}
