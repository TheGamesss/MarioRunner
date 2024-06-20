package mariorunner;
import processing.core.PApplet;
import processing.core.PImage;

public class MarioRunner extends PApplet {
    PImage duck;
    PImage background;
    PImage obstacle;

    int duckAnimation = 1;

    int duckX = 70;
    int duckY = 337;

    int bgX = 0;
    int bgY = 0;

    int obstacleX;
    int obstacleY = 326;

    float speed = 8;
    int jumpHeight;

    int startTime;
    int timer;

    float speedIncrease = .8f;
    float rateOfDifficulty = 5;

    int difficultyTime = 0;

    int score = 0;
    int highScore = 0;

    enum GameState {
        GAMESTART, GAMEOVER, RUNNING
    }

    static GameState currentState;

    public static void main(String[] args) {
        PApplet.main("mariorunner.MarioRunner");
    }

    public void settings() {
        size(700, 400);
    }

    public void setup() {
        image(loadImage("Images/newbg.png"), 0, 0, 700, 400);
        image(loadImage("Images/title.png"), 0, 10, 700, 80);
        duck = loadImage("Images/run1.png");
        background = loadImage("Images/newbg.png");
        obstacle = loadImage("Images/pipe.png");
        duck.resize(40, 0);
        obstacle.resize(50, 0);
        startTime = millis();
        currentState = GameState.GAMESTART;
    }

    public void draw() {
        switch (currentState) {
            case RUNNING:
                drawBackground();
                drawDuck();
                createObstacles();
                timer = (millis() - startTime) / 1000;
                drawScore();
                increaseDifficulty();
                break;
            case GAMEOVER:
                drawGameOver();
                break;
            case GAMESTART:
                drawGameStart();
                break;
        }
    }

    public void drawDuck() {
        imageMode(CENTER);
        image(duck, duckX, duckY);
        if (duckY <= 337) {
            jumpHeight += 1;
            duckY += jumpHeight;
            duck = loadImage("Images/jump.png");
            duck.resize(40, 0);
        } else {
            drawDuckAnimation();
            if (duckAnimation <= 6) {
                duckAnimation = (duckAnimation + 1);
            }
            else {
                duckAnimation = 1;
            }
        }
    }

    public void drawDuckAnimation() {
        if (duckAnimation <= 3) {
            duck = loadImage("Images/run1.png");
            duck.resize(40, 0);
        }
        else {
            duck = loadImage("Images/run2.png");
            duck.resize(40, 0);
        }
    }

    public void drawBackground() {
        imageMode(CORNER);
        image(background, bgX, bgY);
        image(background, bgX + background.width, 0);
        bgX -= speed;
        if (bgX <= (background.width*-1)) {
            bgX = 0;
        }
    }

    public void mousePressed() {
        if (currentState == GameState.RUNNING) {
            if (duckY >= 337) {
                jumpHeight = -16;
                duckY += jumpHeight;
            }
        }
        if (currentState == GameState.GAMEOVER) {
            obstacleX = 0;
            bgX = 0;
            startTime = millis();
            speed = 8;
            currentState = GameState.RUNNING;
        }
        if (currentState == GameState.GAMESTART) {
            obstacleX = 0;
            bgX = 0;
            startTime = millis();
            speed = 8;
            currentState = GameState.RUNNING;
        }
    }

    public void createObstacles() {
        imageMode(CENTER);
        image(obstacle, obstacleX, obstacleY);
        obstacleX -= speed;
        if(obstacleX < 0) {
            obstacleX = width;
        }
        if((abs(duckX-obstacleX) < 40) && abs(duckY-obstacleY)< 60 ) {
            score = timer;
            if(score > highScore) {
                highScore = score;
            }
            currentState = GameState.GAMEOVER;
        }
    }

    public void drawScore() {
        fill(255, 255, 255);
        textAlign(CENTER);
        text("Score: " + timer, width - 70, 30);
    }

    public void increaseDifficulty() {
        if (timer % rateOfDifficulty == 0 && difficultyTime != timer) {
            speed += speedIncrease;
            difficultyTime = timer;
        }
    }

    public void drawGameOver() {
        fill(190, 190, 190, 10);
        noStroke();
        rect(width / 2 - 83, height / 2 - 40, 166, 70);
        fill(25, 25, 25);
        textAlign(CENTER);
        text("Game Over!", width / 2, height / 2 - 25);
        text("Your Score: " + score, width / 2, height / 2 - 10);
        text("High Score: " + highScore, width / 2, height / 2 + 5);
        text("Click to play again.", width / 2, height / 2 + 20);
    }

    public void drawGameStart() {
        fill(190, 190, 190, 10);
        noStroke();
        rect(width / 2 - 83, height / 2 - 40, 166, 70);
        fill(25, 25, 25);
        textAlign(CENTER);
        text("Welcome to MarioRunner!", width / 2, height / 2 - 25);
        text("Click to play game.", width / 2, height / 2 + 20);
    }
}