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
  void setupSprites() {
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
  void drawPlayer() {
    if (inMotion && hasSprayer)
      image(sprayer[currentDirection][1 + int(currentFrame)], x, y);
    else if (inMotion && !hasSprayer)
      image(movement[currentDirection][1 + int(currentFrame)], x, y);
    else if (!inMotion && hasSprayer)
      image(sprayer[currentDirection][0], x, y);
    else
      image(movement[currentDirection][0], x, y);
      //fill(#1A9AD8);
      //stroke(0);
      //rect(x,y-30,health,10);
  }

boolean isDead() {
 if(health<=0)
 return true;
 return false;
}
  void updatePlayer(int xDelta, int yDelta) {
    currentFrame = (currentFrame + 0.5) % 8;
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
  boolean isPlayerOffScreen(float x, float y) {
    if (x < 0 || x > width -30 || y < 0 || y > height - 56)
      return true;
    return false;
  }
  void life() {
    fill(#B20E0E);
  
  textSize(15);
  text("LIFE", width-170, height-20);

  fill(#1A9AD8);
  stroke(0);

  rect(width-120, height-30, player.health, 10);
  noFill();
  stroke(0);
  rect(width-120, height-30, 100, 10);
  }
}
