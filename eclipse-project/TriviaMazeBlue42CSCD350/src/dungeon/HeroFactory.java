/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

import java.util.prefs.Preferences;

/**
 *
 * @author patrick
 */
public class HeroFactory {
    public final Hero createHero(){
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String species = prefs.get("Species", "Warrior");
        Hero ret;
        switch (species){
            case "Sorceress":
                ret = new Sorceress();
                break;
            case "Thief":
                ret = new Thief();
                break;
            case "Warrior":
                ret = new Warrior();
                break;
            default:
               return null;
        }
        
        return ret;
    }
}
