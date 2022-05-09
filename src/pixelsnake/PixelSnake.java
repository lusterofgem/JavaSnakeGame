package pixelsnake;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PixelSnake extends Application
{
    final double width = 800;
    final double height = 600;

    public static void main(String args[])
    {
        launch(PixelSnake.class, args);
    }
	
    @Override
    public void start(Stage stage)
    {
        stage.setTitle("PixelSnake");
        // stage.setMinWidth(width);
        // stage.setMinHeight(height);
        // stage.setMaxWidth(width);
        // stage.setMinHeight(height);

        Rectangle rectangle1 = new Rectangle(0, 0, 50, 50);
        Group group = new Group(rectangle1);
        Scene scene = new Scene(group, width, height);
        stage.setScene(scene);
        stage.show();
	}
}
