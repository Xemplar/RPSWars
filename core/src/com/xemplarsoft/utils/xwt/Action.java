package com.xemplarsoft.utils.xwt;

/**
 * Created by Rohan on 5/24/2018.
 */
public interface Action {
    public void doAction(Button b, Type t);

    public enum Type{
        ON_DOWN,
        ON_DRAG,
        ON_UP,
        CLICKED
    }
}
