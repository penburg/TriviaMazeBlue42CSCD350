/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.Random;

/**
 *
 * @author patrick
 */
public class MonsterFactory {
    private final String[] MonsterList = {"Gremlin", "Ogre", "Skeleton"};
    
    public final Monster createMonster(String monster){
        switch (monster){
            case "Gremlin":
                return new Gremlin();
            case "Ogre":
                return new Ogre();
            case "Skeleton":
                return  new Skeleton();
            default:
               return null;
        }
    }
    
    public String randomMonster(){
        Random rand = new Random();
        return MonsterList[rand.nextInt(MonsterList.length)];
    }
}
