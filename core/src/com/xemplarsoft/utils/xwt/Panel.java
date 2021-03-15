package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class Panel extends AbstractComponent {
    protected TextureRegion region;
    protected ArrayMap<AbstractComponent, Vector2> views;
    protected ComponentHandler handler;

    public Panel(float x, float y, float width, float height, TextureRegion background, ComponentHandler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.region = background;
        this.views = new ArrayMap<AbstractComponent, Vector2>(AbstractComponent.class, Vector2.class);
        this.handler = handler;
        handler.addToUI(this);
    }

    public void addView(AbstractComponent comp){
        Vector2 pos = comp.getPosition().sub(this.getPosition());
        views.put(comp, pos);
        handler.addToUI(comp);
    }
    
    public void hideAll(){
        for(AbstractComponent comp : views.keys){
            if(comp != null) {
                comp.setVisible(false);
            }
        }
    }
    
    public void showAll(){
        for(AbstractComponent comp : views.keys){
            if(comp != null) {
                comp.setVisible(true);
            }
        }
    }
    
    public void removeView(AbstractComponent comp){
        views.removeKey(comp);
        handler.removeFromUI(comp);
    }

    public void removeAllViews(Array<? extends AbstractComponent> views){
        for(AbstractComponent view : views){
            removeView(view);
        }
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        for(AbstractComponent comp : views.keys){
            if(comp != null) {
                Vector2 delta = views.get(comp);
                comp.setPosition(x + delta.x, y + delta.y);
                views.put(comp, new Vector2(x + delta.x, y + delta.y));
            }
        }
    }
    
    public void setPosition(Vector2 v) {
        this.setPosition(v.x, v.y);
    }
    
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for(AbstractComponent comp : this.views.keys) {
            if(comp != null) comp.setVisible(visible);
        }
    }

    public void destroy(){
        this.setVisible(false);
        for(AbstractComponent comp : this.views.keys) {
            handler.removeFromUI(comp);
            if(comp instanceof Panel) ((Panel) comp).destroy();
        }
    }

    public ComponentHandler getHandler(){
        return handler;
    }
    
    public void render(SpriteBatch batch) {
        if(this.visible && this.region != null){
            batch.draw(this.region, x, y - height, width, height);
            if(this.border != null) border.render(batch, this);
        }
    }
}
