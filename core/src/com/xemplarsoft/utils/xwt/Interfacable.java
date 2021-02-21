package com.xemplarsoft.utils.xwt;

/**
 * Created by Rohan on 5/24/2018.
 */
public interface Interfacable {
    public boolean isTouchedDown(float worldX, float worldY, int pointer, int button);
    public boolean isTouchedUp(float worldX, float worldY, int pointer, int button);
    public boolean isDraggedOn(float worldX, float worldY, int pointer);
    public boolean isMovedOver(float worldX, float worldY);

    public boolean isVisible();
}
