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
void setup() {

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



















void draw() {

  // image(bg, 0, 0);
  // background(#45712C);


  fill(#45712C);
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
        int timeBonus = int(1.0/ millis()) + 1;
        int speedBonus = abs(int(t.x)/40);
        points=(points+1+timeBonus+speedBonus);
      }
    }









    if (dist(t.x+t.img.width/2, t.y+t.img.height/3, player.x+12, player.y+21)<=20) {

      fill(255, 0, 0, 100);
      player.health-=0.5;
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
    fill(#D7D89D);
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
    fill(#FF0346);
    for (int i = 0; i < nunt; i++)
      an.get(i).drawdecor();
    
    fill(#FFD6FC);
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


















void move() {

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
      r.fill-=0.5;


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













void keyPressed() {
  if (key == CODED) {
    keys[keyCode] = true;
  } else keys[key] = true;
}












void keyReleased() {
  if (key == CODED) {
    keys[keyCode] = false;
  } else keys[key] = false;
}

