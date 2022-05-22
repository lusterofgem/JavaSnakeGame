package javasnakegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JavaSnakeGame extends JPanel implements KeyListener
{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int MAP_X = 40;
    public static final int MAP_Y = 30;
    public static final int UNIT_WIDTH = WIDTH / MAP_X;
    public static final int UNIT_HEIGHT = HEIGHT / MAP_Y;
    public static final int TICK = 200;

    private boolean paused = true;
    private Snake snake = new Snake(MAP_X / 2, MAP_Y / 2);
    private Food food = new Food();

    public JavaSnakeGame()
    {
        randomFoodPosition();
    }

    @Override
    public void paint(Graphics graphics)
    {
        // draw background
        graphics.setColor(new Color(60, 60, 60));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // draw food
        graphics.setColor(food.color);
        graphics.fillRect(UNIT_WIDTH * food.x, UNIT_HEIGHT * food.y, UNIT_WIDTH, UNIT_HEIGHT);

        // draw snake
        for(int i = 0; i < snake.size(); ++i)
        {
            SnakeUnit snakeUnit = snake.snakeUnits.get(i);
            if(isGameOver())
            {
                graphics.setColor(new Color(120, 120, 120));
            }
            else
            {
                double redUnitDistance = ((double)snake.headColor.getRed() - snake.tailColor.getRed()) / (snake.size() - 1);
                double greenUnitDistance = ((double)snake.headColor.getGreen() - snake.tailColor.getGreen()) / (snake.size() - 1);
                double blueUnitDistance = ((double)snake.headColor.getBlue() - snake.tailColor.getBlue()) / (snake.size() - 1);
                if(isPaused())
                {
                    Color color = new Color(
                        snake.headColor.getRed() - (int)(i * redUnitDistance),
                        snake.headColor.getGreen() - (int)(i * greenUnitDistance),
                        snake.headColor.getBlue() - (int)(i * blueUnitDistance),
                        127);
                    graphics.setColor(color);
                }
                else
                {
                    Color color = new Color(
                        snake.headColor.getRed() - (int)(i * redUnitDistance),
                        snake.headColor.getGreen() - (int)(i * greenUnitDistance),
                        snake.headColor.getBlue() - (int)(i * blueUnitDistance));
                    graphics.setColor(color);
                }
            }
            graphics.fillRect(snakeUnit.x * UNIT_WIDTH, snakeUnit.y * UNIT_HEIGHT, UNIT_WIDTH, UNIT_HEIGHT);
        }

        // draw snake eye
        if(!isGameOver())
        {
            SnakeUnit snakeUnit = snake.snakeUnits.get(0);
            final int EYE_UNIT_WIDTH = UNIT_WIDTH / 5;
            final int EYE_UNIT_HEIGHT = UNIT_HEIGHT / 5;
            graphics.setColor(snake.eyeColor);
            if(snake.getDirection() == Direction.NORTH)
            {
                // left eye
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT, EYE_UNIT_WIDTH, EYE_UNIT_HEIGHT * 2);
                // right eye
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH * 3, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT, EYE_UNIT_WIDTH, EYE_UNIT_HEIGHT * 2);
            }
            if(snake.getDirection() == Direction.EAST)
            {
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH * 2, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT, EYE_UNIT_WIDTH * 2, EYE_UNIT_HEIGHT);
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH * 2, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT * 3, EYE_UNIT_WIDTH * 2, EYE_UNIT_HEIGHT);
            }
            if(snake.getDirection() == Direction.SOUTH)
            {
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH * 3, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT* 2 , EYE_UNIT_WIDTH, EYE_UNIT_HEIGHT * 2);
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT * 2, EYE_UNIT_WIDTH, EYE_UNIT_HEIGHT * 2);
            }
            if(snake.getDirection() == Direction.WEST)
            {
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH * 1, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT * 3, EYE_UNIT_WIDTH * 2, EYE_UNIT_HEIGHT);
                graphics.fillRect(snakeUnit.x * UNIT_WIDTH + EYE_UNIT_WIDTH * 1, snakeUnit.y * UNIT_HEIGHT + EYE_UNIT_HEIGHT, EYE_UNIT_WIDTH * 2, EYE_UNIT_HEIGHT);
            }
        }
    }

    // trigger when key down
    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        switch(keyEvent.getKeyCode())
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            {
                turnSnake(Direction.NORTH);
                break;
            }
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
            {
                turnSnake(Direction.EAST);
                break;
            }
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
            {
                turnSnake(Direction.SOUTH);
                break;
            }
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
            {
                turnSnake(Direction.WEST);
                break;
            }
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
            {
                if(isGameOver())
                {
                    reset();
                }
                else
                {
                    paused = !paused;
                }
                repaint();
                break;
            }
        }
    }

    /// trigger when release a key, not used
    @Override
    public void keyReleased(KeyEvent keyEvent)
    {

    }

    /// trigger when type a key, not used
    @Override
    public void keyTyped(KeyEvent keyEvent)
    {

    }

    /// make the snake forward
    public void forwardSnake()
    {
        snake.forward();
    }

    /// make the snake forward and grow
    public void forwardAndGrowSnake()
    {
        snake.forwardAndGrow();
    }

    /// turn the snake
    public void turnSnake(Direction direction)
    {
        if(!isGameOver())
        {
            snake.turn(direction);
            repaint();
        }
    }

    /// random generate the food, it'll not overlapped with snake
    public void randomFoodPosition()
    {
        // there is not more space to generate food
        if(snake.size() >= MAP_X * MAP_Y)
        {
            setToGameOver();
        }

        // regenerate food until the food is not overlapped with snake
        int foodX;
        int foodY;
        do {
            foodX = (int)(Math.random() * MAP_X);
            foodY = (int)(Math.random() * MAP_Y);
        } while(snake.isPointOnBody(foodX, foodY));

        food.x = foodX;
        food.y = foodY;
    }

    /// check if it is paused
    public boolean isPaused()
    {
        return paused;
    }

    /// check if it is game over
    public boolean isGameOver()
    {
        return snake.isDead();
    }

    /// let's game over
    public void setToGameOver()
    {
        snake.suicide();
    }

    /// reset game
    public void reset()
    {
        paused = true;
        snake.reset();
        randomFoodPosition();
    }

    /// check if snake is going to eat food
    public boolean isSnakeGoingToEat()
    {
        if(snake.getTargetX() == food.x && snake.getTargetY() == food.y)
        {
            return true;
        }
        return false;
    }

    /// check if the snake is going to hit something
    public boolean isGoingToGameOver()
    {
        int targetX = snake.getTargetX();
        int targetY = snake.getTargetY();
        if(snake.isPointOnBody(targetX, targetY) || targetX < 0 || targetY < 0 || targetX >= MAP_X || targetY >= MAP_Y)
        {
            return true;
        }
        return false;
    }

    /// main function
    public static void main(String args[])
    {
        JFrame mainFrame = new JFrame("JavaSnakeGame");
        JavaSnakeGame game = new JavaSnakeGame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(WIDTH + 14, HEIGHT + 37);
        mainFrame.setResizable(false);
        mainFrame.addKeyListener(game);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(game);
        mainFrame.setVisible(true);

        /// game loop
        while(true)
        {
            try
            {
                Thread.sleep(TICK);
            }
            catch (InterruptedException e)
            {
                System.out.println("InterruptedException");
            }

            if(!game.isPaused() && !game.isGameOver())
            {
                if(game.isGoingToGameOver())
                {
                    game.setToGameOver();
                }
                else
                {
                    if(game.isSnakeGoingToEat())
                    {
                        game.forwardAndGrowSnake();
                        game.randomFoodPosition();
                    }
                    else
                    {
                        game.forwardSnake();
                    }
                }
                game.repaint();
            }
        }
    }
}
