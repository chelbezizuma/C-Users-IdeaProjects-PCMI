package zmejka;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author DAKondrateva
 */
public class Zmejka extends Application {
  
    int LZmei = 2;              
    int Speed = 5;           
    int nodeSize = 30;
    int windowH = 900;
    int windowL = 900;
    int X = 0;
    int Y = 0;
    int pointX = (windowL/nodeSize)/2;
    int pointY = (windowH/nodeSize)/2;
    int[][] nodeGameState = new int[(windowH / nodeSize)][(windowL / nodeSize)]; 
    int moveDirection = 1;   
    boolean ateAnApple = true;
    Pane root = new Pane();
        
    @Override
    public void start(Stage stage) throws Exception {
        
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(windowH, windowL);
        root.setStyle("-fx-background-color: white");
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {  
            @Override
            public void handle(KeyEvent event) {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(keyCode.W)) {
                    moveDirection = 0;
                } else if (keyCode.equals(keyCode.A)) {
                    moveDirection = 3;
                } else if (keyCode.equals(keyCode.S)) {
                    moveDirection = 2;
                } else if (keyCode.equals(keyCode.D)) {
                    moveDirection = 1;
                }
            }
        });
        
        Tick tick = new Tick(gc);
        
        stage.setScene(scene);
        stage.setHeight(windowH);
        stage.setWidth(windowL);
        stage.setTitle("Zmeika");
        stage.show();
        
        Timer timer = new Timer(true);
        timer.schedule(tick, 0, 50);
    }
    
    public class Tick extends TimerTask {
        GraphicsContext gc;
        int tickCount = 0;
        
        public Tick(GraphicsContext gc) {  
            this.gc = gc;
            nodeGameState[X][Y] = LZmei;   
            PaintNode(0,0);
        }
        
        public void DrawFruit(int x, int y) {
            gc.setFill(Color.TEAL);
            gc.setStroke(Color.TEAL);
            gc.fillRoundRect(x * nodeSize, y * nodeSize, nodeSize, nodeSize, 0, 0);
        }
        
        public void PaintNode(int x, int y) {
            gc.setFill(Color.TEAL);
            gc.setStroke(Color.TEAL);
            gc.fillRoundRect(x, y, nodeSize, nodeSize, 0, 0);
        }
        
        public void ClearNode(int x, int y) {
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.WHITE);
            gc.fillRoundRect(x*nodeSize, y*nodeSize, nodeSize, nodeSize, 0, 0);
        }
        
        public void GameOver() {
            Speed = -1;
            gc.setFill(Color.YELLOW);
            gc.setStroke(Color.YELLOW);
            for (int i = 0; i < (windowH / nodeSize); i++) { 
                for (int j = 0; j < (windowL / nodeSize); j++) { 
                    gc.fillRoundRect(i*nodeSize, j*nodeSize, nodeSize, nodeSize, 0, 0);
                }
            }
            ClearNode(3, 1); 
            ClearNode(6, 1);
            ClearNode(2, 3);
            ClearNode(3, 4);
            ClearNode(4, 4);
            ClearNode(5, 4);
            ClearNode(6, 4);
            ClearNode(7, 3);
        }
        
        @Override
        public void run() {
            Platform.runLater(() -> {
                
                if (tickCount == Speed) {
                    tickCount = 0;
                    
                    if (moveDirection == 0){          
                        Y--;
                    } else if (moveDirection == 1) {
                        X++;
                    } else if (moveDirection == 2) {
                        Y++;
                    } else if (moveDirection == 3) {
                        X--;
                    }
                    
                    if ((Y < 0) || (Y >= windowH/nodeSize) || (X < 0) || (X >= windowL/nodeSize)) {
                        GameOver();
                        return;
                    } else
                    if (nodeGameState[X][Y] > 0) {
                        GameOver();
                        return;
                    }
                    
                    if (nodeGameState[X][Y] == -2) {  
                        LZmei++;
                        ateAnApple = true;
                        ClearNode(X,Y);
                        if ((LZmei == 5) || (LZmei == 10) || (LZmei == 15) || (LZmei == 20)) { 
                            Speed--;
                            tickCount--;
                        }
                    }
                    
                    PaintNode(X*nodeSize,Y*nodeSize); 
                    nodeGameState[X][Y] = LZmei;
                    
                    if (ateAnApple == false) {
                        for (int i = 0; i < (windowH / nodeSize); i++) { 
                            for (int j = 0; j < (windowL / nodeSize); j++) { 
                                if (nodeGameState[i][j] > -1) {
                                    nodeGameState[i][j]--;
                                }
                                if (nodeGameState[i][j] == -1) {
                                    ClearNode(i, j);
                                }
                            }
                        } 
                    } else {
                        DrawFruit(pointX, pointY);
                        nodeGameState[pointX][pointY] = -2;
                        do {
                            pointX = (int)(Math.random() * (windowL / nodeSize)); 
                            pointY = (int)(Math.random() * (windowH / nodeSize));
                        } while (nodeGameState[pointX][pointY] <= 0);
                    }
                    ateAnApple = false;
                } 
                
                tickCount++;                
            });
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
