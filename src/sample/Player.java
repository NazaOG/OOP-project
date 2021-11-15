package sample;

import javafx.scene.paint.Color;

public class Player{
    private int sizex;
    private int sizey;
    private int posx;
    private int posy;
    private Color color;
    public static Player[] Players = new Player[2];

    public Player(int posx, int posy, int sizex, int sizey, Color c){
        this.sizex = sizex;
        this.sizey = sizey;
        this.posx = posx;
        this.posy = posy;
        this.color = c;
    }


    public void goDown(){
        if(posy <= Main.field_width-Main.PlayerSize-13){
            posy+=3;
        }
    }

    public void goUp(){
        if(posy >= Main.wallwide+Main.PlayBallSize/2){
            posy-=3;
        }
    }

    public void goDownKI(){
        if(posy <= Main.field_width-Main.PlayerSize-13){
            posy+=Main.KIdifficulty;
        }
    }

    public void goUpKI(){
        if(posy >= Main.wallwide+Main.PlayBallSize/2){
            posy-=Main.KIdifficulty;
        }
    }

    public int getX(){
        return posx;
    }

    public int getY(){
        return posy;
    }

    public int getSizeX(){
        return sizex;
    }

    public int getSizeY(){
        return sizey;
    }

    public void setY(int y){
        this.posy = y;
    }

    public Color getColor(){
        return color;
    }
}

