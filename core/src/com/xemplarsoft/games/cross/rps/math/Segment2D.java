package com.xemplarsoft.games.cross.rps.math;

import com.badlogic.gdx.math.Vector2;

public class Segment2D {
    public Vector2 a, b;
    
    public Segment2D(){
        a = new Vector2();
        b = new Vector2();
    }
    
    public Segment2D(Vector2 a, Vector2 b){
        this.a = a;
        this.b = b;
    }
    
    public Segment2D(float x1, float y1, float x2, float y2){
        this.a = new Vector2(x1, y1);
        this.b = new Vector2(x2, y2);
    }
    
    public float length(){
        return (float)Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
    
    public Vector2 midpoint(){
        return new Vector2((a.x + b.x) / 2, (a.y + b.y) / 2);
    }
    
    public float slope(){
        return ((a.y - b.y) / (a.x - b.x));
    }
    
    public Segment2D bisector(float scale){
        Vector2 m = midpoint();
        float dxa = a.x - m.x, dya = a.y - m.y;
        float dxb = b.x - m.x, dyb = b.y - m.y;
        
        return new Segment2D((dya * scale) + m.x, (-dxa * scale) + m.y, (dyb * scale) + m.x, (-dxb * scale) + m.y);
    }
    
    public static float linearY(float slope, float x, float offset){
        return slope * x + offset;
    }
    
    public static float linearX(float slope, float x, float offset){
        return slope * x + offset;
    }
}
