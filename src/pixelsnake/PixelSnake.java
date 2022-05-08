package pixelsnake;

import javafx.application.Application;
import javafx.stage.Stage;

public class PixelSnake extends Application
{
    public static void main(String args[])
    {
        launch(PixelSnake.class, args);
    }
	
    @Override
    public void start(Stage stage)
    {
        stage.setTitle("PixelSnake");
        stage.show();
	}
}
