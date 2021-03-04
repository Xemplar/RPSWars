package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.xemplarsoft.games.cross.rps.Wars;

/**
 * Created by Rohan on 5/25/2018.
 */
public class SwitchButton extends Label implements Interfacable {
    protected boolean state = false;
    protected Texture on, off;

    public SwitchButton(float x, float y, float width, float height, String text){
        super(x, y,text);
        this.width = width;
        this.height = height;

        int aWidth = MathUtils.floor(width) * 32, aHeight = MathUtils.floor(height) * 32, aSpace = 2;
        int sliderWidth = aWidth < 100 ? aSpace * 2 : aSpace * 4;
        Pixmap draw = new Pixmap(aWidth, aHeight, Pixmap.Format.RGBA8888);

        draw.setColor(0.7F, 0.7F, 0.7F, 1);
        draw.fill();
        draw.setColor(0.4F, 0.4F, 0.4F, 1);
        draw.fillRectangle(aWidth - (aWidth / 4), aSpace, (aWidth / 4) - aSpace, aHeight - (aSpace * 2));
        draw.setColor(0.7F, 0.7F, 0.7F, 1);
        draw.fillRectangle(aWidth - (aWidth / 4) + aSpace, aSpace * 2, (aWidth / 4) - (aSpace * 3), aHeight - (aSpace * 4));
        draw.setColor(0.9F, 0.9F, 0.9F, 1);
        draw.fillRectangle(aWidth - (aWidth / 4) + 1, (aSpace * 2) - 1, sliderWidth, aHeight - (aSpace * 2) - 2);
        on = new Texture(draw);

        draw.setColor(0.4F, 0.4F, 0.4F, 1);
        draw.fillRectangle(aWidth - (aWidth / 4), aSpace, (aWidth / 4) - aSpace, aHeight - (aSpace * 2));
        draw.setColor(0.85F, 0.85F, 0.85F, 1);
        draw.fillRectangle(aWidth - (aWidth / 4) + aSpace, aSpace * 2, (aWidth / 4) - (aSpace * 3), aHeight - (aSpace * 4));
        draw.setColor(0.9F, 0.9F, 0.9F, 1);
        draw.fillRectangle((aWidth - (aWidth / 4) + 1) + ((aWidth / 4) - (aSpace * 2) - sliderWidth), (aSpace * 2) - 1, sliderWidth, aHeight - (aSpace * 2) - 2);
        off = new Texture(draw);
    }
    public boolean getState(){
        return state;
    }

    public void render(SpriteBatch batch){
        if(visible) {
            batch.draw(state ? on : off, x, y - height, width, height);
            Wars.fnt_text.draw(batch, text, x + 0.1F, y, width, alignment, true);
        }
    }

    private boolean held;
    public boolean isTouchedDown(float worldX, float worldY, int pointer, int button) {
        if((worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height))){
            held = true;
            return true;
        }
        return false;
    }
    public boolean isTouchedUp(float worldX, float worldY, int pointer, int button) {
        if((worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height))){
            if(held) {
                state = !state;
            }
            held = false;
            return true;
        }
        return false;
    }
    public boolean isDraggedOn(float worldX, float worldY, int pointer) {
        held &= (worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height));
        return held;
    }

    public boolean isMovedOver(float worldX, float worldY) {
        return (worldX >= x && worldX <= (x + width)) && (worldY <= y && worldY >= (y - height));
    }
}
