package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;

/**
 * Created by Rohan on 5/13/2018.
 */
public class Label extends AbstractComponent {
    protected int alignment = Align.left;
    protected String text;
    protected BitmapFont font;
    protected Color color;
    protected float padLeft, padRight, padTop, padBottom;

    public Label(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.width = -1;

        this.text = text;
    }

    public Label(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.text = text;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public float getWidth(){
        return width;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public String getText() {
        return text;
    }

    public void setFont(BitmapFont font){
        this.font = font;
    }

    public void setColor(Color c){
        this.color = c;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setPadding(float left, float right, float top, float bottom){
        this.padLeft = left;
        this.padRight = right;
        this.padTop = top;
        this.padBottom = bottom;
    }

    public void render(SpriteBatch batch) {
        render(batch, font == null ? Wars.fnt_text : font);
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        if(visible) {
            if (border != null) border.render(batch, this);

            Color original = font.getColor();
            font.setColor(color == null ? original : color);
            if (width == -1) {
                font.draw(batch, text, x + padLeft, y + 1F + padBottom);
            } else {
                font.draw(batch, text, x + padLeft, y + 1F + padBottom, width - (padLeft + padRight), alignment, true);
            }
            font.setColor(original);
        }
    }
}