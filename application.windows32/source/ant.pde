class ant {

  float x, y;
  int bodySize;
  PVector speed;
  PImage img;
  boolean bitten;
                        ant() {
                          x = random(20, width-20);
                          y = random(20, height-20);
                          speed = new PVector(random(0.5, 1.5), random(0.5, 1.5));
                          img = loadImage("AND.png");
                          bitten = false;
                        } 





  void drawAnt() {
    moveAnt();
    pushMatrix();
    translate(x+img.width/2, y+img.height/2);
    rotate(speed.heading() + PI/2);
    translate(-img.width/2, -img.height/2);  
    image(img, 0, 0);
    popMatrix();
  }






  void moveAnt() {

    float testmove = random(0, 1);

    if (testmove < 0.07)
      speed.rotate(random(-0.3, 0.3));

    if (testmove > 0.97)
      speed.rotate(random(-1, 1));



    x = x + speed.x;
    y = y + speed.y;



    if (isAnt() == true) {
      x = x - speed.x;
      y = y - speed.y;
      speed.rotate(random(-2, 2));
    }
  }







  boolean isAnt() { 

    if (x < 25 || x > width - 25 ||
      y < 25 ||y > height - 25)
      return true;

    return false;
  }
}

