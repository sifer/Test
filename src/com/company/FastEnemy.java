package com.company;

/**
 * Created by Administrator on 2017-02-02.
 */
public class FastEnemy extends Enemy{


    public FastEnemy(float x, float y) {
        super.x = x;
        super.y = y;
        displaychar = 'F';
        speed=0.7f;
    }
}
