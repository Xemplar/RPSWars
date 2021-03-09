package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

public class SegmentedPanel extends Panel{
    protected TextureRegion[] pieces;
    protected TextureRegion[] back;
    protected String name;
    public SegmentedPanel(float x, float y, float width, float height, ComponentHandler handler) {
        super(x, y, width, height, null, handler);
    }
    
    public void setTextures(String name){
        pieces = new TextureRegion[9];
        for(int i = 0; i < 9; i++){
            pieces[i] = Wars.ur(name, i);
        }
    }
    
    public void setBack(String name){
        back = new TextureRegion[3];
        for(int i = 0; i < 3; i++){
            back[i] = Wars.ur(name, i);
        }
    }
    
    public void render(SpriteBatch batch) {
        float ratio = 1F;
        if(visible && pieces != null){
            batch.draw(pieces[0], x, y - ratio, ratio, ratio);
            batch.draw(pieces[1], x + ratio, y - ratio, width - ratio * 2, ratio);
            batch.draw(pieces[2], x + width - ratio, y - ratio, ratio, ratio);
        
            batch.draw(pieces[3], x, y - height + ratio / 2, ratio, height - ratio);
            batch.draw(pieces[4], x + ratio, y - height + ratio / 2, width - ratio * 2, height - ratio);
            batch.draw(pieces[5], x + width - ratio, y - height + ratio / 2, ratio, height - ratio);
        
            batch.draw(pieces[6], x, y - height, ratio, ratio);
            batch.draw(pieces[7], x + ratio, y - height, width - ratio * 2, ratio);
            batch.draw(pieces[8], x + width - ratio, y - height, ratio, ratio);
        }
        if(visible && back != null){
            float padding = 0.5F;
            float off = (width - padding * 2) / 8;
            batch.draw(back[0], x + padding, y - off - padding, width - padding * 2, off);
            batch.draw(back[1], x + padding, y - height + ratio + off - padding, width - padding * 2, height - padding * 2F - off * 2F);
            batch.draw(back[2], x + padding, y - height + ratio - padding, width - padding * 2, off);
        }
        super.render(batch);
    }
}
