package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

/**
 * Created by Rohan on 5/13/2018.
 */
public class Button extends Label implements Interfacable {
    public TextureRegion bg_none, bg_hold;
    protected Action action;
    protected String actionCommand = "";

    public Button(float x, float y, float width, float height, String text){
        super(x, y, text);
        this.width = width;
        this.height = height;
        this.visible = true;
    }

    public void setNormalBG(TextureRegion t){
        this.bg_none = t;
    }
    public void setPressedBG(TextureRegion t){
        this.bg_hold = t;
    }
    public void setBG(TextureRegion t){
        setNormalBG(t);
        setPressedBG(t);
    }
    public void setBG(TextureRegion normal, TextureRegion pressed){
        setNormalBG(normal);
        setPressedBG(pressed);
    }
    public void render(SpriteBatch batch){
        render(batch, Wars.fnt_text);
    }

    public void render(SpriteBatch batch, BitmapFont font){
        if(visible) {
            if (border != null) border.render(batch, this);

            if (bg_none != null) {
                batch.draw(held ? bg_hold : bg_none, x, y - height, width, height);
            }
            font.draw(batch, text, x, y - 0.3F, width, alignment, true);
        }
    }
    public void setAction(Action a){
        this.action = a;
    }
    public void setActionCommand(String str){
        this.actionCommand = str;
    }
    public String getActionCommand(){
        return actionCommand;
    }

    public boolean held = false;
    public boolean over = false;
    public boolean isOver(){
        return over;
    }
    public boolean isTouchedDown(float worldX, float worldY, int pointer, int button) {
        if((worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height))){
            held = true;
            over = true;
            if(action != null) action.doAction(this, Action.Type.ON_DOWN);
            return true;
        }
        return false;
    }
    public boolean isTouchedUp(float worldX, float worldY, int pointer, int button) {
        if((worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height))){
            if(action != null){
                action.doAction(this, Action.Type.ON_UP);
                if(held) {
                    action.doAction(this, Action.Type.CLICKED);
                }
            }
            held = false;
            over = false;
            return true;
        }
        return false;
    }
    public boolean isDraggedOn(float worldX, float worldY, int pointer) {
        over = (worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height));
        held &= over;
        if(action != null) action.doAction(this, Action.Type.ON_DRAG);
        return held;
    }

    public boolean isMovedOver(float worldX, float worldY) {
        over = (worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height));
        return over;
    }
}