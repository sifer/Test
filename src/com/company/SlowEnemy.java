package com.company;

/**
 * Created by Administrator on 2017-02-02.
 */
public class SlowEnemy extends Enemy{

    public SlowEnemy(float x, float y) {
        super.x = x;
        super.y = y;
        displaychar = 'S';
        speed=0.3f;
    }
}
