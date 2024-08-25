package com.company.spaceknight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Shot {
    Context context;
    Bitmap shot;
    int sx,sy,shotSpeed;

    public Shot(Context context, int sx, int sy){
        this.context=context;
        shot= BitmapFactory.decodeResource(context.getResources(),R.drawable.bullet2);
        this.sx=sx;
        this.sy=sy;
        shotSpeed=20;
    }
    public Bitmap getShot(){
        return shot;
    }
}
