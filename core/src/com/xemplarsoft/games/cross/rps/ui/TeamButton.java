package com.xemplarsoft.games.cross.rps.ui;

import com.xemplarsoft.games.cross.rps.model.Team;
import com.xemplarsoft.utils.xwt.SegmentedButton;

public class TeamButton extends SegmentedButton {
    protected Team t;
    public TeamButton(float x, float y, float width, float height, Team t) {
        super(x, y, width, height, "");
        this.t = t;
    }
}
