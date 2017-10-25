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
  void setSprite() {
    spray  = new PImage[1][7];
    SSpriteSheet = loadImage("mcsmoke.png");
    for (int i = 0; i<7; i++) {

      if (i>=2)
        spray[0][i] = SSpriteSheet.get(1+(24*i), 12, 25, 25);
      else
        spray[0][i] = SSpriteSheet.get(1+(24*i), 12, 15, 25);
    }
  }

  void drawSpray() {

    if (currentDuration < maxDuration) {
      image(spray[0][1 + int(cuFrame)], x, y);
     
      currentDuration++;
    }
  
  }
  


  void updateSpray() {
    cuFrame = (cuFrame + 0.1) % 6;
  }


}


