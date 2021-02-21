package com.xemplarsoft.utils.xwt;

import com.badlogic.gdx.utils.Align;
import com.xemplarsoft.games.cross.rps.Wars;

public class NumberSpinner extends Panel implements Action{
    private long value = 0, minimum = Long.MIN_VALUE, maximum = Long.MAX_VALUE;
    private Button more, less;
    private Label amount;

    public NumberSpinner(float x, float y, ComponentHandler handler){
        super(x, y, 1F, 3F, null, handler);

        more = new Button(0.25F + x, 2.25F + y, 0.5F, 0.5F, "");
        less = new Button(0.25F + x, 0.25F + y, 0.5F, 0.5F, "");
        amount = new Label(x, y + 0.385F, 1F, 1F, "0");

        amount.setAlignment(Align.center);

        more.setNormalBG(Wars.ur("scroll_up"));
        less.setNormalBG(Wars.ur("scroll_down"));

        more.setPressedBG(Wars.ur("scroll_up_press"));
        less.setPressedBG(Wars.ur("scroll_down_press"));

        addView(more);
        addView(less);
        addView(amount);

        more.setAction(this);
        less.setAction(this);
    }

    public void setVisible(boolean visible){
        super.setVisible(visible);
        more.setVisible(visible);
        less.setVisible(visible);
        amount.setVisible(visible);
    }

    public void setMinimum(long min){
        this.minimum = min;
    }

    public void setMaximum(long max){
        this.maximum = max;
    }

    public void setValue(long value){
        this.value = value;
    }

    public long getValue(){
        return value;
    }

    public void doAction(Button b, Type t) {
        if(b.equals(more) && t.equals(Type.CLICKED) && value < maximum){
            value++;
            amount.setText(value + "");
        } else if(b.equals(less) && t.equals(Type.CLICKED) && value > minimum){
            value--;
            amount.setText(value + "");
        }
    }
}
