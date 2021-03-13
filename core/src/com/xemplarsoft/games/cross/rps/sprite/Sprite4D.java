package com.xemplarsoft.games.cross.rps.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite4D extends com.xemplarsoft.games.cross.rps.sprite.Sprite {
    protected com.xemplarsoft.games.cross.rps.sprite.Sprite u, d, l, r;
    protected int dir = 0;
    
    public Sprite4D(com.xemplarsoft.games.cross.rps.sprite.Sprite u, com.xemplarsoft.games.cross.rps.sprite.Sprite d, com.xemplarsoft.games.cross.rps.sprite.Sprite l, com.xemplarsoft.games.cross.rps.sprite.Sprite r){
        this.u = u;
        this.d = d;
        this.l = l;
        this.r = r;
    }
    
    public Sprite4D(TextureRegion u, TextureRegion d, TextureRegion l, TextureRegion r){
        this.u = new com.xemplarsoft.games.cross.rps.sprite.Sprite(u);
        this.d = new com.xemplarsoft.games.cross.rps.sprite.Sprite(d);
        this.l = new com.xemplarsoft.games.cross.rps.sprite.Sprite(l);
        this.r = new Sprite(r);
    }
    
    public void update(float delta){
        super.update(delta);
        switch(dir){
            default: u.update(delta); break;
            case 1: d.update(delta); break;
            case 2: l.update(delta); break;
            case 3: r.update(delta); break;
        }
    }
    
    public TextureRegion getTex(){
        switch(dir){
            default: return u.getTex();
            case 1: return d.getTex();
            case 2: return l.getTex();
            case 3: return r.getTex();
        }
    }
    
    public void setDir(int dir){
        this.dir = dir % 4;
    }
}
