package com.encryptorcode.abhay.infinitycalc.models;

/**
 * Created by abhay-5228 on 12/08/17.
 */

public enum Theme {
    BLUE(0),
    DARK(1),
    RED(2),
    GREEN(3),
    PINK(4),
    ORANGE(5),
    YELLOW(6),
    PURPLE(7)
    ;

    private int themeId;
    private static Theme[] themes = Theme.values();
    Theme(int themeId){
        this.themeId = themeId;
    }
    public int getId(){
        return themeId;
    }
    public static Theme getTheme(int themeId){
        return themes[themeId];
    }
}
