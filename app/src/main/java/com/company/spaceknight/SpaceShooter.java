package com.company.spaceknight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SpaceShooter extends View {
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS=30;
    static int screenWidth, screenHeight;
    int points=0;
    int life=3;
    Paint scorePaint;
    int TEXT_SIZE=80;
    boolean paused=false;
    KnightShip knightShip;
    EnemySpaceship enemySpaceship;
    Random random;
    ArrayList<Shot> enemyShots, knightShots;
    boolean enemyExplosion=false;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    boolean enemyShotAction=false;

    final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public  SpaceShooter(Context context){
        super(context);
        this.context=context;
        random=new Random();
        enemyShots=new ArrayList<>();
        knightShots=new ArrayList<>();
        explosions=new ArrayList<>();
        Display display=((Activity)  getContext()).getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;
        knightShip= new KnightShip(context);
        enemySpaceship=new EnemySpaceship(context);
        background= BitmapFactory.decodeResource(context.getResources(), R.drawable.space1);
        lifeImage=BitmapFactory.decodeResource(context.getResources(),R.drawable.life);

        handler=new Handler();
        scorePaint=new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);

    }

    @Override
    protected void onDraw(Canvas canvas){

        canvas.drawBitmap(background,0,0,null);
        canvas.drawText("Score: "+points,0,TEXT_SIZE,scorePaint);
        for(int i=life;i>0;i--){
            canvas.drawBitmap(lifeImage,screenWidth-lifeImage.getWidth()*i,0,null);

        }
        if(life==0){
            paused=true;
            handler=null;
            /*Intent intent=new Intent(context,GameOverActivity.class);
            intent.putExtra("points",points);
            context.startActivity(intent);*/
            ((Activity)context).finish();

        }

        enemySpaceship.ex+=enemySpaceship.enemySpeed;
        if(enemySpaceship.ex + enemySpaceship.getEnemyShipWidth()>= screenWidth){
            enemySpaceship.enemySpeed*=-1;
        }
        if(enemySpaceship.ex<=0){
            enemySpaceship.enemySpeed*=-1;
        }
        if((enemyShotAction==false)&& (enemySpaceship.ex>=200+random.nextInt(400))){
            Shot enemyShot=new Shot(context,enemySpaceship.ex+enemySpaceship.getEnemyShipWidth()/2,enemySpaceship.ey);
            enemyShots.add(enemyShot);
            enemyShotAction=true;

        }
        if(!enemyExplosion){
            canvas.drawBitmap(enemySpaceship.getEnemyShip(),enemySpaceship.ex,enemySpaceship.ey,null);

        }
        if(knightShip.isAlive==true){
            if(knightShip.kx>screenWidth- knightShip.getKnightShipWidth()){
                knightShip.kx=screenWidth- knightShip.getKnightShipWidth();
            }else if(knightShip.kx<0){
                knightShip.kx=0;
            }
            canvas.drawBitmap(knightShip.getKnightShip(),knightShip.kx,knightShip.ky,null);
        }
        for(int i=0;i<enemyShots.size();i++){
            enemyShots.get(i).sy+=15;
            canvas.drawBitmap(enemyShots.get(i).getShot(),enemyShots.get(i).sx,enemyShots.get(i).sy,null);
            if((enemyShots.get(i).sx>=knightShip.kx)
                    &&(enemyShots.get(i).sx<=knightShip.kx+knightShip.getKnightShipWidth())
                    &&(enemyShots.get(i).sy>=knightShip.ky)
                    &&(enemyShots.get(i).sy<=screenHeight)){
                        life--;
                        enemyShots.remove(i);
                        explosion=new Explosion(context,knightShip.kx,knightShip.ky);
                        explosions.add(explosion);
                        
                    } else if (enemyShots.get(i).sy>=screenHeight) {
                        enemyShots.remove(i);
                    }
            if(enemyShots.size()==0){
                enemyShotAction=false;
            }
        }
        for(int i=0;i<knightShots.size();i++){
            knightShots.get(i).sy-=15;
            canvas.drawBitmap(knightShots.get(i).getShot(),knightShots.get(i).sx,knightShots.get(i).sy,null);
            if((knightShots.get(i).sx >= enemySpaceship.ex)
                    && ((knightShots.get(i).sx <= (enemySpaceship.ex + enemySpaceship.getEnemyShipWidth())))
                    && (knightShots.get(i).sy >= enemySpaceship.ey)
                    && (knightShots.get(i).sy <= enemySpaceship.getEnemyShipHeight())) {
                        points++;
                        knightShots.remove(i);
                        explosion=new Explosion(context,enemySpaceship.ex,enemySpaceship.ey);
                        explosions.add(explosion);
            } else if (knightShots.get(i).sy<=0)  {
                knightShots.remove(i);
            }
        }

        for(int i=0;i<explosions.size();i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame),explosions.get(i).ex,explosions.get(i).ey,null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame>=2){
                explosions.remove(i);
            }
        }
        if(!paused){
            handler.postDelayed(runnable,UPDATE_MILLIS);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX= (int) event.getX();
        if(event.getAction()==MotionEvent.ACTION_UP){
            if(knightShots.size()<3){
                Shot knightShot=new Shot(context, knightShip.kx+knightShip.getKnightShipWidth()/2, knightShip.ky);
                knightShots.add(knightShot);
            }
        }
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            knightShip.kx=touchX;
        }
        if(event.getAction()==MotionEvent.ACTION_MOVE){
            knightShip.kx=touchX;
        }
        return true;
    }
}
