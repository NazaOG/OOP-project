package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    GraphicsContext gc;
    public static int field_width = 600;
    public static int field_length = 900;
    public static int player_windows_distance = 75;
    public static Ball playball;
    public static int PlayBallSize = 10;
    public static int scorep1 = 0;
    public static int scorep2 = 0;
    public static boolean p1Up = false;
    public static boolean p2Up = false;
    public static boolean p1Down = false;
    public static boolean p2Down = false;
    public static int startcountdown = 5;
    public static boolean GameIsOver = false;
    public static Timeline tf, tf2;
    public static int winner = 0;
    public static boolean kip1 = true, kip2 = true;
    public static int gamespeed = 4;
    public static int KIdifficulty = 3;
    public static int PlayerSize = 100;
    public static int PlayerThickness = 20;
    public static int wallwide = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primStage) throws Exception {
        Group root = new Group();
        Scene s = new Scene(root, field_length, field_width, Color.BLACK);
        Canvas canvas = new Canvas(field_length, field_width);
        gc = canvas.getGraphicsContext2D();

        Player p1 = new Player(player_windows_distance, field_width/3, PlayerThickness, PlayerSize, Color.BLUE);
        Player p2 = new Player(field_length-player_windows_distance, field_width/3, PlayerThickness, PlayerSize, Color.RED);
        playball = new Ball((field_length/2)-PlayBallSize, field_width/2, PlayBallSize, PlayBallSize);

        Player.Players[0] = p1;
        Player.Players[1] = p2;

        s.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.W) {
                    p1Up = true;
                    kip1 = false;
                }else if(ke.getCode() == KeyCode.S){
                    p1Down = true;
                    kip1 = false;
                }else if(ke.getCode() == KeyCode.UP){
                    p2Up = true;
                    kip2 = false;
                }else if(ke.getCode() == KeyCode.DOWN){
                    p2Down = true;
                    kip2 = false;
                }
            }
        });

        s.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.W) {
                    p1Up = false;
                }else if(ke.getCode() == KeyCode.S){
                    p1Down = false;
                }else if(ke.getCode() == KeyCode.UP){
                    p2Up = false;
                }else if(ke.getCode() == KeyCode.DOWN){
                    p2Down = false;
                }

                if (ke.getCode() == KeyCode.N) {
                    if(GameIsOver){
                        restart(gc);
                    }
                }
            }
        });
        tf = new Timeline(new KeyFrame(Duration.millis(gamespeed), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(startcountdown < -1){
                    playball.moveBall();
                }
                playball.CheckhitWall();
                redraw(gc);

                if(kip2){
                    if((playball.getY() < Player.Players[1].getY()+Player.Players[1].getSizeY()/2) && playball.gettoPlayer() == 1 && playball.getX() > field_length/2){
                        Player.Players[1].goUpKI();
                    }else if((playball.getY() > Player.Players[1].getY()-Player.Players[1].getSizeY()/2) && playball.gettoPlayer() == 1 && playball.getX() > field_length/2){
                        Player.Players[1].goDownKI();
                    }
                }

                if(kip1){
                    if((playball.getY() < Player.Players[0].getY()+Player.Players[0].getSizeY()/2) && playball.gettoPlayer() == 0 && playball.getX() < field_length/2){
                        Player.Players[0].goUpKI();
                    }else if((playball.getY() > Player.Players[0].getY()-Player.Players[0].getSizeY()/2) && playball.gettoPlayer() == 0 && playball.getX() < field_length/2){
                        Player.Players[0].goDownKI();
                    }
                }
            }
        }));
        tf.setCycleCount(Timeline.INDEFINITE);
        tf2 = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                drawStart(gc);
                if(startcountdown == -1){
                    tf.play();
                }
            }
        }));
        tf2.setCycleCount(7);
        tf2.play();
        root.getChildren().add(canvas);
        gc.clearRect(0, 0, field_length, field_width);
        primStage.setScene(s);
        primStage.setTitle("Ping Pong");
        primStage.show();
    }

    public static void restart(GraphicsContext gc){
        gc.clearRect(0, 0, field_length, field_width);
        startcountdown = 5;
        scorep1 = 0;
        scorep2 = 0;
        Player.Players[0].setY(field_width/3);
        Player.Players[1].setY(field_width/2);
        GameIsOver = false;
        tf2.play();
    }

    public static void drawStart(GraphicsContext gc){
        gc.clearRect(0, 0, field_length, field_width);
        gc.setFill(Color.LIME);
        gc.setFont(new Font((field_length*field_width)/6750));
        gc.fillText(String.valueOf(startcountdown) + "...", (field_length/2.25), field_width/2);
        gc.setFill(Color.GOLD);
        gc.setFont(new Font((field_length*field_width)/13500));
        gc.fillText("Controls: Player 1-> W/D | Player 2-> Up/Down", field_length/30, field_width/1.65);
        startcountdown--;
    }

    public static void drawFinalScreen(GraphicsContext gc){
        gc.clearRect(0, 0, field_length, field_width);
        tf.stop();
        if(winner == 1){
            gc.setFill(Color.BLUE);
            gc.setFont(new Font(50));
            gc.fillText("Player 1 won!!! ~ Press N to restart", 60, 280);
        }else if(winner == 2){
            gc.setFill(Color.RED);
            gc.setFont(new Font(50));
            gc.fillText("Player 2 won!!! ~ Press N to restart", 60, 280);
        }
    }

    public static void redraw(GraphicsContext gc){
        gc.clearRect(0, 0, field_length, field_width);
        for(int i = 0; i<Player.Players.length; i++){
            gc.setFill(Player.Players[i].getColor());
            gc.fillRect(Player.Players[i].getX(), Player.Players[i].getY(), Player.Players[i].getSizeX(), Player.Players[i].getSizeY());
        }


        gc.setFill(Color.GOLD);
        gc.fillRect(playball.getX(), playball.getY(), playball.getBallSizeX(), playball.getBallSizeY());


        gc.setStroke(Color.GRAY);
        gc.setLineDashes(10, 10, 10, 10, 10, 10);
        gc.setLineDashOffset(10);
        gc.setLineWidth(10);
        gc.strokeLine((field_length/2)-wallwide, -5, (field_length/2)-wallwide, field_width+wallwide);

        gc.setFill(Color.AZURE);
        gc.setFont(new Font(80));
        gc.fillText(String.valueOf(Main.scorep1), field_length/4.5, field_width/2);
        gc.fillText(String.valueOf(Main.scorep2), field_length/1.38, field_width/2);
        gc.setFont(new Font(30));
        if(kip1){
            gc.fillText("KI on\nPress W/S to join", field_length/6, field_width/1.5);
        }
        if(kip2){
            gc.fillText("KI on\nPress UP/DOWN to join", field_length/1.8, field_width/1.5);
        }
        gc.setFill(Color.BROWN);
        gc.fillRect(0, 0, field_length, wallwide);
        gc.fillRect(0, field_width-wallwide, field_length, wallwide);

        if(p1Up){
            Player.Players[0].goUp();
        }
        if(p2Up){
            Player.Players[1].goUp();
        }
        if(p1Down){
            Player.Players[0].goDown();
        }
        if(p2Down){
            Player.Players[1].goDown();
        }
        if(GameIsOver){
            tf.stop();
            drawFinalScreen(gc);
        }
    }
}
