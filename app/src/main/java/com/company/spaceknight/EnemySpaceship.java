package com.company.spaceknight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class EnemySpaceship {

    Context context;
    Bitmap enemyShip;
    int ex,ey, enemySpeed;
    Random random;

    public EnemySpaceship(Context context){
        this.context=context;
        enemyShip= BitmapFactory.decodeResource(context.getResources(),R.drawable.enemyship);
        random=new Random();
        resetEnemySpaceship();
    }

    public  Bitmap getEnemyShip(){
        return enemyShip;

    }

    int getEnemyShipHeight(){
        return enemyShip.getHeight();
    }
    int getEnemyShipWidth(){
        return enemyShip.getWidth();
    }

    private  void resetEnemySpaceship(){

        ex=200+random.nextInt(600);
        ey=0;
        enemySpeed=5+random.nextInt(10);

    }

}
