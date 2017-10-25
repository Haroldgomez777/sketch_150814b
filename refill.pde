class Refill {

  float x=random(0,500);
  float y=random(0,500);
  float fill=100;
  PImage refill;
  float duration=0;



  Refill() {
    refill=loadImage("spray_can.png");
  }
  void drawRefill() {

    image(refill, x, y);
  }
  void upDate() {

    fill(#B20E0E);
    textSize(15);
    text("poison", width-175, height-35);

    fill(#1A9AD8);
    stroke(0);
    rect(width-120, height-45, fill, 10);
    noFill();
    stroke(0);
    rect(width-120, height-45, 100, 10);
  }

  boolean empty() {

    if (fill<=0)
    {
      duration=0;
      return true;
    }
    return false;
  }

  void duty() {
    
    if(fill<=100)
    {
      fill++;
    }
  }
}

