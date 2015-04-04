package com.donthaveawebsite.mhy.omnichess;


public class Piece {
    private boolean available;
    private int x;
    private int y;
    private int z; // for board id. Right now only one board.
    public int mc = 0;

    public Piece(boolean available, int x, int y, int z)
    {
        super();
        this.available = available;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public boolean isAvailable() {  //IE The can this piece move function
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getMC() {return mc;}
    public void setMC(int mc) {this.mc = mc;}
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

   // public boolean isValid(Board board, int fromX, int fromY, int toX, int toY){
   //     if(toX == fromX && toY == fromY)
   //         return false; //cannot move nothing
  //      if(toX < 0 || toX > 7 || fromX < 0 || fromX > 7 || toY < 0 || toY > 7 || fromY <0 || fromY > 7)
   //         return false;
  //      return true;
   // }  On a 3 x 3 board an 8 x 8 border doesn't do much good.

}