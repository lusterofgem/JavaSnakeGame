package javasnakegame;

import java.awt.Color;

class Food
{
    public int x;
    public int y;

    Color color = new Color(255, 0, 0);

    public Food()
    {

    }

    public Food(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
