package com.company.spaceknight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class KnightShip {
    Context context;
    Bitmap knightShip;
    int kx,ky,knightSpeed;
    boolean isAlive=true;
    Random random;

    public KnightShip(Context context){
        this.context=context;
        knightShip= BitmapFactory.decodeResource(context.getResources(),R.drawable.spaceship);
        random=new Random();
        resetKnightShip();
    }

    public Bitmap getKnightShip() {
        return knightShip;
    }
    int getKnightShipWidth(){
        return knightShip.getWidth();
    }

    private void resetKnightShip(){

        kx=random.nextInt(SpaceShooter.screenWidth );
        ky=SpaceShooter.screenHeight-knightShip.getHeight();
        knightSpeed=10 + random.nextInt(20);

    }
}
