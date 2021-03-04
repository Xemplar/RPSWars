package com.xemplarsoft.games.cross.rps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;
import com.xemplarsoft.utils.xwt.AbstractComponent;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class CreditsScroller {
    public static String title(String input, int size){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            input = input.substring(9);
        } else {
            chars = new char[8];
            Arrays.fill(chars, '0');
        }
        chars[0] = 't';
        chars[1] = ("" + size).charAt(0);
        return (new String(chars)) + input;
    }
    
    public static String normal(String input, int size){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            input = input.substring(9);
        } else {
            chars = new char[8];
            Arrays.fill(chars, '0');
        }
        chars[0] = 'n';
        chars[1] = ("" + size).charAt(0);
        return (new String(chars)) + input;
    }
    
    /**
     * Split char is a comma (,) for input.
     * @param input
     * @param size
     * @return
     */
    public static String col2(String input, int size){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            input = input.substring(9);
        } else {
            chars = new char[8];
            Arrays.fill(chars, '0');
        }
        chars[0] = '2';
        chars[1] = ("" + size).charAt(0);
        return (new String(chars)) + input;
    }
    
    /**
     * Split char is a comma (,) for input.
     * @param input
     * @param size
     * @return
     */
    public static String col3(String input, int size){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            input = input.substring(9);
        } else {
            chars = new char[8];
            Arrays.fill(chars, '0');
        }
        chars[0] = '3';
        chars[1] = ("" + size).charAt(0);
        return (new String(chars)) + input;
    }
    
    /**
     * For color:
     * 0 = Red
     * 1 = Orange
     * 2 = Yellow
     * 3 = Green
     * 4 = Blue
     * 5 = Purple
     * 6 = White
     * 7 = Gray
     * 8 = Black
     * 9 = Brown
     * @param input
     * @param color
     * @return colored text.
     */
    public static String color(String input, char color){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            input = input.substring(9);
        } else {
            chars = new char[8];
            Arrays.fill(chars, '0');
        }
        chars[7] = color;
        return (new String(chars)) + input;
    }
    
    /**
     * For align:
     * l = left
     * r = right
     * c = center
     * = = left for 1st col, right for last col, if 3 col, center is centered.
     * @param input
     * @param align
     * @return aligned text.
     */
    public static String align(String input, char align){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            input = input.substring(9);
        } else {
            chars = new char[8];
            Arrays.fill(chars, '0');
        }
        chars[6] = align;
        return (new String(chars)) + input;
    }
    
    public static Color getColor(String input){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            switch(chars[7]){
                case 0: return Color.RED;
                case 1: return Color.ORANGE;
                case 2: return Color.YELLOW;
                case 3: return Color.GREEN;
                case 4: return Color.BLUE;
                case 5: return Color.PURPLE;
                default: return Color.WHITE;
                case 7: return Color.GRAY;
                case 8: return Color.BLACK;
                case 9: return Color.BROWN;
            }
        } else {
            return Color.WHITE;
        }
    }
    
    public static int getAlignment(String input){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            switch(chars[6]){
                default: return Align.left;
                case 'r': return Align.right;
                case 'c': return Align.center;
                case '=': return Align.top;
            }
        } else {
            return Align.left;
        }
    }
    
    public static int getSize(String input){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            try {
                return Integer.parseInt(chars[1] + "");
            } catch (NumberFormatException e){
                return 4;
            }
        } else {
            return 4;
        }
    }
    
    public static int cols(String input){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            try {
                return Integer.parseInt(chars[0] + "");
            } catch (NumberFormatException e){
                return 1;
            }
        } else {
            return 1;
        }
    }
    
    public static boolean isTitle(String input){
        char[] chars;
        if(input.charAt(8) == ':'){
            chars = input.substring(0, 8).toCharArray();
            return chars[0] == 't';
        } else {
            return false;
        }
    }
    
    public static String[] getContents(String input){
        if(input.charAt(8) == ':'){
            return input.substring(9).split(",");
        } else {
            return input.split(",");
        }
    }
}
