class decor {
  
  float x,y;
  int bodySize;
   PVector speed;
  
  decor() {
   x = random(20,displayWidth);
   y = random(20,displayWidth);
  bodySize = int(random(4,6));
  speed = new PVector(random(0.1,1.2),random(0.1,1.2));
  
  
  } 
  void drawdecor() {
    moveDec();
    
  ellipse(x,y,bodySize,bodySize);
  
  }
 
  void moveDec() {
    
    float testmove = random(0,1);
    
    if(testmove < 0.07)
     speed.rotate(random(-0.3,0.3));
    
    if(testmove > 0.97)
      speed.rotate(random(-1,1));
      
   
    
    x = x + speed.x;
    y = y + speed.y;
    
   
   
    if(isAnt() == true) {
     x = x - speed.x;
     y = y - speed.y;
     speed.rotate(random(-2,2));
    }
      
    
    
    
  }
  
  
  boolean isAnt() { 
    int radius = bodySize/2;
  if(x < radius || x > width - radius ||
    y < radius ||y > height - radius)
    return true;
    
    return false;
  
  }
  }
  

