package com.company.spaceknight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {

    Bitmap explosion[]= new Bitmap[2];
    int ex,ey,explosionFrame;

    public  Explosion(Context context, int ex, int ey){
        explosion[0]= BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion1);
        explosion[1]= BitmapFactory.decodeResource(context.getResources(),R.drawable.explosion2);
        explosionFrame=0;
        this.ex=ex;
        this.ey=ey;

    }
    public Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }
}
