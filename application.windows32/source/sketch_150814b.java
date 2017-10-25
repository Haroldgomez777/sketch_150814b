import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_150814b extends PApplet {

ArrayList <ant> ants = new ArrayList<ant>();
ArrayList <decor> an = new ArrayList<decor>();
int numAnt = 20;
int nunt = 1000;
PImage bg;
Player player;
boolean[] keys;
Spray spray;
int points=0;

Refill r;
public void setup() {

  size(displayWidth, displayHeight);
  //size(600, 600);
  noStroke();
  player = new Player();
  r = new Refill();
  spray = new Spray(-100, -100);
  keys = new boolean[128];
  frameRate(60);
  bg = loadImage("Background.png");
  for (int i = 0; i<numAnt; i++)
    ants.add(new ant());

  for (int i = 0; i<nunt; i++)
    an.add(new decor());
}



















public void draw() {

  // image(bg, 0, 0);
  // background(#45712C);


  fill(0xff45712C);
  rect(0, 0, width, height);
  fill(0);
  textSize(32);
  text("Points:"+points, 10, 30);
  move();

 
 
 
 
  if (!player.isDead())
  {
    player.drawPlayer();
    player.life();

    if (r.empty()==false)
    {
      spray.drawSpray();
    } else
    {
      player.hasSprayer=false;
    }
  }
 
 
 
 
  if ((second()>10&&second()<20))
  {
    r.drawRefill();
  }
  if (dist(r.x, r.y, player.x, player.y)<=20)
  {
    player.hasSprayer=true;
    r.duty();
  }

  r.upDate();


 
 
 
 
 
 
 
  for (int i = ants.size ()-1; i>=0; i--)
  {
    ant t=ants.get(i);
    t.drawAnt();
    if (r.empty()==false) {
      if (dist(t.x, t.y, spray.x, spray.y)<=20) {
        ants.remove(i);
        int timeBonus = PApplet.parseInt(1.0f/ millis()) + 1;
        int speedBonus = abs(PApplet.parseInt(t.x)/40);
        points=(points+1+timeBonus+speedBonus);
      }
    }









    if (dist(t.x+t.img.width/2, t.y+t.img.height/3, player.x+12, player.y+21)<=20) {

      fill(255, 0, 0, 100);
      player.health-=0.5f;
      rect(0, 0, width, height);
      if (!t.bitten) {
        player.health-=10;
        if (points>=10)
          points-=10;
        t.bitten=true;
      }
    } else {
      t.bitten=false;
    }
  }
  if (player.isDead())
  {
    background(0);
    fill(0xffD7D89D);
    for (int i = 0; i < nunt; i++)
      an.get(i).drawdecor();
    loop();
    textSize(32);
    textAlign(CENTER);
    text("Points:"+points, width/2, 300);
    //noLoop();
  }

  if ((ants.size()==0)&&!player.isDead())
  {
    background(0);
    // image(bg,0,0);
    fill(0xffFF0346);
    for (int i = 0; i < nunt; i++)
      an.get(i).drawdecor();
    
    fill(0xffFFD6FC);
    textSize(32);
    textAlign(CENTER);
    text("Congratulations", width/2, height/2);
    textSize(32);
    textAlign(CENTER);
    text("You Won!!!!", width/2, height/2+50 );
    textSize(32);
    textAlign(CENTER);
    text("Points:"+points, width/2, height/2+100);

   
  }
}


















public void move() {

  int xDelta = 0;
  int yDelta = 0;

  if (keys['w'] || keys['W'] || keys[38])
    yDelta=yDelta-2;  
  if (keys['s']|| keys['S']|| keys[40])
    yDelta=yDelta+2;
  if (keys['a']|| keys['A']|| keys[37])
    xDelta=xDelta-2;
  if (keys['d']|| keys['D']|| keys[39])
    xDelta=xDelta+2;
  if (keys[' ']) {

    if (r.fill>0)
      r.fill-=0.5f;


    if (player.currentDirection==1) //Left
      spray = new Spray(player.x-20, player.y+30);

    else if (player.currentDirection==2)//down
      spray = new Spray(player.x+20, player.y+50);
    else if (player.currentDirection==0)//up
      spray = new Spray(player.x+15, player.y-20);
    else //Right
    spray = new Spray(player.x+40, player.y+30);
  }
  player.updatePlayer(xDelta, yDelta);
  spray.updateSpray();
}













public void keyPressed() {
  if (key == CODED) {
    keys[keyCode] = true;
  } else keys[key] = true;
}












public void keyReleased() {
  if (key == CODED) {
    keys[keyCode] = false;
  } else keys[key] = false;
}

class ant {

  float x, y;
  int bodySize;
  PVector speed;
  PImage img;
  boolean bitten;
                        ant() {
                          x = random(20, width-20);
                          y = random(20, height-20);
                          speed = new PVector(random(0.5f, 1.5f), random(0.5f, 1.5f));
                          img = loadImage("AND.png");
                          bitten = false;
                        } 





  public void drawAnt() {
    moveAnt();
    pushMatrix();
    translate(x+img.width/2, y+img.height/2);
    rotate(speed.heading() + PI/2);
    translate(-img.width/2, -img.height/2);  
    image(img, 0, 0);
    popMatrix();
  }






  public void moveAnt() {

    float testmove = random(0, 1);

    if (testmove < 0.07f)
      speed.rotate(random(-0.3f, 0.3f));

    if (testmove > 0.97f)
      speed.rotate(random(-1, 1));



    x = x + speed.x;
    y = y + speed.y;



    if (isAnt() == true) {
      x = x - speed.x;
      y = y - speed.y;
      speed.rotate(random(-2, 2));
    }
  }







  public boolean isAnt() { 

    if (x < 25 || x > width - 25 ||
      y < 25 ||y > height - 25)
      return true;

    return false;
  }
}

class decor {
  
  float x,y;
  int bodySize;
   PVector speed;
  
  decor() {
   x = random(20,displayWidth);
   y = random(20,displayWidth);
  bodySize = PApplet.parseInt(random(4,6));
  speed = new PVector(random(0.1f,1.2f),random(0.1f,1.2f));
  
  
  } 
  public void drawdecor() {
    moveDec();
    
  ellipse(x,y,bodySize,bodySize);
  
  }
 
  public void moveDec() {
    
    float testmove = random(0,1);
    
    if(testmove < 0.07f)
     speed.rotate(random(-0.3f,0.3f));
    
    if(testmove > 0.97f)
      speed.rotate(random(-1,1));
      
   
    
    x = x + speed.x;
    y = y + speed.y;
    
   
   
    if(isAnt() == true) {
     x = x - speed.x;
     y = y - speed.y;
     speed.rotate(random(-2,2));
    }
      
    
    
    
  }
  
  
  public boolean isAnt() { 
    int radius = bodySize/2;
  if(x < radius || x > width - radius ||
    y < radius ||y > height - radius)
    return true;
    
    return false;
  
  }
  }
  

class Player {
  float x, y;
  PImage playerSpriteSheet, sprayerSpriteSheet;
  PImage [][] movement;
  PImage [][] sprayer;
  boolean inMotion, hasSprayer;
  int currentDirection;
  float currentFrame;
  float health;

  final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3 ;

  Player() {
    inMotion = false;
    hasSprayer = true;
    currentDirection = 1;
    currentFrame = 0;
    x = 300;
    y = 300;
    setupSprites();
    health = 100;
  }
  public void setupSprites() {
    movement = new PImage[4][9];
    sprayer = new PImage[4][9];
    playerSpriteSheet = loadImage("hello.png");
    sprayerSpriteSheet = loadImage("sprayer.png");
    for (int i = 0; i<9; i++) {
      movement[0][i] = playerSpriteSheet.get(13 + 51 * i, 8, 25, 42);
      movement[1][i] = playerSpriteSheet.get(13 + 51 * i, 60, 25, 42);  
      movement[2][i] = playerSpriteSheet.get(13 + 51 * i, 111, 25, 42);
      movement[3][i] = playerSpriteSheet.get(13 + 51 * i, 162, 25, 42);
      sprayer[0][i] = sprayerSpriteSheet.get(15 + 51 * i, 1, 27, 42);
      sprayer[1][i] = sprayerSpriteSheet.get(15 + 51 * i, 55, 27, 42);  
      sprayer[2][i] = sprayerSpriteSheet.get(15 + 51 * i, 105, 27, 42);
      sprayer[3][i] = sprayerSpriteSheet.get(15 + 51 * i, 158, 27, 42);
    }
  }
  public void drawPlayer() {
    if (inMotion && hasSprayer)
      image(sprayer[currentDirection][1 + PApplet.parseInt(currentFrame)], x, y);
    else if (inMotion && !hasSprayer)
      image(movement[currentDirection][1 + PApplet.parseInt(currentFrame)], x, y);
    else if (!inMotion && hasSprayer)
      image(sprayer[currentDirection][0], x, y);
    else
      image(movement[currentDirection][0], x, y);
      //fill(#1A9AD8);
      //stroke(0);
      //rect(x,y-30,health,10);
  }

public boolean isDead() {
 if(health<=0)
 return true;
 return false;
}
  public void updatePlayer(int xDelta, int yDelta) {
    currentFrame = (currentFrame + 0.5f) % 8;
    inMotion = true;
    if (xDelta == 0 && yDelta == 0)
      inMotion = false;
    else if (xDelta == -2)
      currentDirection = LEFT;
    else if (xDelta == 2)
      currentDirection = RIGHT;
    else if (yDelta == -2)
      currentDirection = UP;
    else if (yDelta == 2)
      currentDirection = DOWN;

    x = x + xDelta;
    y = y + yDelta;

    if (isPlayerOffScreen(x, y)) {
      x = x - xDelta;
      y = y - yDelta;
    }
  }
  public boolean isPlayerOffScreen(float x, float y) {
    if (x < 0 || x > width -30 || y < 0 || y > height - 56)
      return true;
    return false;
  }
  public void life() {
    fill(0xffB20E0E);
  
  textSize(15);
  text("LIFE", width-170, height-20);

  fill(0xff1A9AD8);
  stroke(0);

  rect(width-120, height-30, player.health, 10);
  noFill();
  stroke(0);
  rect(width-120, height-30, 100, 10);
  }
}
class Refill {

  float x=random(0,500);
  float y=random(0,500);
  float fill=100;
  PImage refill;
  float duration=0;



  Refill() {
    refill=loadImage("spray_can.png");
  }
  public void drawRefill() {

    image(refill, x, y);
  }
  public void upDate() {

    fill(0xffB20E0E);
    textSize(15);
    text("poison", width-175, height-35);

    fill(0xff1A9AD8);
    stroke(0);
    rect(width-120, height-45, fill, 10);
    noFill();
    stroke(0);
    rect(width-120, height-45, 100, 10);
  }

  public boolean empty() {

    if (fill<=0)
    {
      duration=0;
      return true;
    }
    return false;
  }

  public void duty() {
    
    if(fill<=100)
    {
      fill++;
    }
  }
}

class specialAnt {
  
}
class Spray {

  float x, y;
  float currentDuration;
  float maxDuration;
  PImage SSpriteSheet;
  PImage [][]spray;
  float  cuFrame;

  Spray(float x, float y) {

    this.x = x;
    this.y = y;
    maxDuration = 100;
    currentDuration = 1;
    setSprite();
  } 
  public void setSprite() {
    spray  = new PImage[1][7];
    SSpriteSheet = loadImage("mcsmoke.png");
    for (int i = 0; i<7; i++) {

      if (i>=2)
        spray[0][i] = SSpriteSheet.get(1+(24*i), 12, 25, 25);
      else
        spray[0][i] = SSpriteSheet.get(1+(24*i), 12, 15, 25);
    }
  }

  public void drawSpray() {

    if (currentDuration < maxDuration) {
      image(spray[0][1 + PApplet.parseInt(cuFrame)], x, y);
     
      currentDuration++;
    }
  
  }
  


  public void updateSpray() {
    cuFrame = (cuFrame + 0.1f) % 6;
  }


}


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#000203", "--hide-stop", "sketch_150814b" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
