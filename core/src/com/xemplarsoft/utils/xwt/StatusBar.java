package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xemplarsoft.games.cross.rps.Wars;

import static com.xemplarsoft.games.cross.rps.screens.GameScreen.$_GLOBAL_CLOCK;

/**
 * Created by Rohan on 6/2/2018.
 */
public class StatusBar extends AbstractComponent {
    private TextureRegion bg;
    private String message = "Nothing Happening";
    private static final long debounce = 3000;
    private long time;
    public StatusBar(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bg = Wars.ur("bar");
    }

    public void postMessage(String message){
        this.message = message;
        time = $_GLOBAL_CLOCK;
    }

    public void render(SpriteBatch batch) {
        if(visible) {
            if (time + debounce <= $_GLOBAL_CLOCK) message = "Nothing Happening";
            batch.draw(bg, x, y, width, height);
            Color original = Wars.fnt_text.getColor();
            Wars.fnt_text.setColor(Color.WHITE);
            Wars.fnt_text.draw(batch, message, x + 0.25F, y + 0.875F);
            Wars.fnt_text.setColor(original);

            time++;
        }
    }
}
