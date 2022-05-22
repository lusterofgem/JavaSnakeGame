package javasnakegame;

import java.awt.Color;
import java.util.ArrayList;

public class Snake
{
    public ArrayList<SnakeUnit> snakeUnits = new ArrayList<SnakeUnit>();

    public Color headColor = new Color(255, 100, 100);
    public Color tailColor = new Color(255, 255, 255);
    public Color eyeColor = new Color(255, 255, 0);

    public Direction direction = Direction.NORTH;
    private boolean dead = false;

    private int spawnPointX;
    private int spawnPointY;

    /// constructor
    public Snake(int spawnPointX, int spawnPointY)
    {
        setSpawnPoint(spawnPointX, spawnPointY);
        reset();
    }

    /// set spawn point
    public void setSpawnPoint(int spawnPointX, int spawnPointY)
    {
        this.spawnPointX = spawnPointX;
        this.spawnPointY = spawnPointY;
    }

    /// reset snake, it will start from spawn point
    public void reset()
    {
        dead = false;
        direction = Direction.NORTH;
        snakeUnits.clear();
        snakeUnits.add(new SnakeUnit(spawnPointX, spawnPointY));
        forwardAndGrow();
        forwardAndGrow();
    }

    /// turn to the specified Direction
    public void turn(Direction direction)
    {
        // snake can't turn south
        if(snakeUnits.get(0).y < snakeUnits.get(1).y)
        {
            if(direction != Direction.SOUTH)
            {
                this.direction = direction;
            }
        }
        // snake can't turn west
        else if(snakeUnits.get(0).x > snakeUnits.get(1).x)
        {
            if(direction != Direction.WEST)
            {
                this.direction = direction;
            }
        }
        // snake can't turn north
        else if(snakeUnits.get(0).y > snakeUnits.get(1).y)
        {
            if(direction != Direction.NORTH)
            {
                this.direction = direction;
            }
        }
        // snake can't turn east
        else if(snakeUnits.get(0).x < snakeUnits.get(1).x)
        {
            if(direction != Direction.EAST)
            {
                this.direction = direction;
            }
        }

    }

    /// get the size of snake
    public int size()
    {
        return snakeUnits.size();
    }

    /// get the direction of snake
    public Direction getDirection()
    {
        return direction;
    }

    /// forward the snake
    /// please make sure the target point is not out of boundary
    public void forward()
    {
        for(int i = snakeUnits.size() - 1; i > 0; --i)
        {
            snakeUnits.get(i).x = snakeUnits.get(i - 1).x;
            snakeUnits.get(i).y = snakeUnits.get(i - 1).y;
        }

        if(direction == Direction.NORTH)
        {
            snakeUnits.get(0).y--;
        }
        else if(direction == Direction.EAST)
        {
            snakeUnits.get(0).x++;
        }
        else if(direction == Direction.SOUTH)
        {
            snakeUnits.get(0).y++;
        }
        else if(direction == Direction.WEST)
        {
            snakeUnits.get(0).x--;
        }
    }

    /// forward and grow the snake
    /// please make sure the target point is not out of boundary
    public void forwardAndGrow()
    {
        snakeUnits.add(0, new SnakeUnit(getTargetX(), getTargetY()));
    }

    /// get the target point x axis of the snake faced
    public int getTargetX()
    {
        if(direction == Direction.EAST)
        {
            return snakeUnits.get(0).x + 1;
        }
        else if(direction == Direction.WEST)
        {
            return snakeUnits.get(0).x - 1;
        }

        return snakeUnits.get(0).x;
    }

    /// get the target point y axis of the snake faced
    public int getTargetY()
    {
        if(direction == Direction.NORTH)
        {
            return snakeUnits.get(0).y - 1;
        }
        else if(direction == Direction.SOUTH)
        {
            return snakeUnits.get(0).y + 1;
        }

        return snakeUnits.get(0).y;
    }

    /// kill the snake
    public void suicide()
    {
        dead = true;
    }

    /// check if the snake is dead
    public boolean isDead()
    {
        return dead;
    }

    // if the given point is overlapped with snake body
    public boolean isPointOnBody(int x, int y)
    {
        boolean isOverlapped = false;
        for(int i = 0; i < snakeUnits.size(); ++i)
        {
            if(snakeUnits.get(i).x == x && snakeUnits.get(i).y == y)
            {
                isOverlapped = true;
            }
        }

        return isOverlapped;
    }
}
