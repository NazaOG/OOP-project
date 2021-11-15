package sample;

public class Ball{
    private int ballx;
    private int bally;
    private int ballsizex;
    private int ballsizey;
    private int dir = 0;
    private int toPlayer = 0;

    public Ball(int startx, int starty, int ballsizex, int ballsizey){
        this.ballx = startx;
        this.bally = starty;
        this.ballsizex = ballsizex;
        this.ballsizey = ballsizey;
    }

    public void moveBall(){
        if(toPlayer == 0){
            ballx-=2;
        }else if(toPlayer == 1){
            ballx+=2;
        }

        if(dir == 0){
            bally--;
        }else if(dir == 1){
            bally++;
        }
    }

    public int getX(){
        return ballx;
    }

    public int getY(){
        return bally;
    }

    public int getBallSizeX(){
        return ballsizex;
    }

    public int getBallSizeY(){
        return ballsizey;
    }

    public int gettoPlayer(){
        return toPlayer;
    }

    // Collision Detection & Score increase
    public void CheckhitWall(){
        if(((Player.Players[0].getX() - ballx < Player.Players[0].getSizeX() && Player.Players[0].getX() - ballx > -Player.Players[0].getSizeX()) && (Player.Players[0].getY() - bally < 5 && Player.Players[0].getY() - bally > -Player.Players[0].getSizeY()))){
            toPlayer = 1;
        }else if(((Player.Players[1].getX() - ballx < ballsizex && Player.Players[1].getX() - ballx > -ballsizex) && (Player.Players[1].getY() - bally < ballsizey && Player.Players[1].getY() - bally > -Player.Players[1].getSizeY()))){
            toPlayer = 0;
        }else if(bally >= Main.field_width-Main.wallwide-Main.PlayBallSize/2){
            dir = 0;
        }else if(bally <= Main.wallwide){
            dir = 1;
        }
        // If ball has come thru the Player
        if(ballx > Main.field_length){
            // Increase his Score
            Main.scorep1++;
            // Reset the Ball and fly direction
            ballx = (Main.field_length/2)-Main.PlayBallSize;
            bally = (Main.field_width/2)-Main.PlayBallSize*2;
            toPlayer = 0;
            // If the Player has 5 Points
            if(Main.scorep1 == 5){
                // End the Game, set the winner and reset the Scores
                Main.GameIsOver = true;
                Main.winner = 1;
                Main.scorep2 = 0;
                Main.scorep1 = 0;
            }
        }else if(ballx < 0){
            Main.scorep2++;
            ballx = (Main.field_length/2)-Main.PlayBallSize;
            bally = Main.field_width/2;
            toPlayer = 1;
            if(Main.scorep2 == 5){
                Main.GameIsOver = true;
                Main.winner = 2;
                Main.scorep2 = 0;
                Main.scorep1 = 0;
            }
        }
    }
}
