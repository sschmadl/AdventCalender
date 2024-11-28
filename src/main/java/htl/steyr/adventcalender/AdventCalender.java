package htl.steyr.adventcalender;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.stream.IntStream;

public class AdventCalender extends Application {
    private static final int NUM_BOXES = 24; // Number of boxes
    private static final String IMAGE_PATH_TEMPLATE = "images/image%d.png"; // Image path template
    private static final String DEFAULT_IMAGE = "images/imageX.png"; // Default fallback image
    private static final int COLUMNS = 6; // Number of columns

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Configure columns and rows to grow dynamically
        for (int i = 0; i < COLUMNS; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(100.0 / COLUMNS); // Divide width equally
            gridPane.getColumnConstraints().add(colConstraints);
        }

        int rows = (int) Math.ceil((double) NUM_BOXES / COLUMNS); // Calculate required rows
        for (int i = 0; i < rows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / rows); // Divide height equally
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // Add buttons to the GridPane
        IntStream.range(1, NUM_BOXES + 1).forEach(i -> {
            Button button = new Button("Box " + i);
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow buttons to grow
            button.setOnAction(e -> openImageWindow(i)); // Set action to open image window

            // Add button to the grid
            gridPane.add(button, (i - 1) % COLUMNS, (i - 1) / COLUMNS);
        });

        // Create the main scene and stage
        Scene scene = new Scene(gridPane, 800, 600); // Initial window size
        primaryStage.setTitle("Resizable Picture Boxes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Opens a new window displaying the specified image.
     *
     * @param imageIndex The index of the image to display.
     */
    private void openImageWindow(int imageIndex) {
        Stage imageStage = new Stage();
        StackPane imagePane = new StackPane();
        Image image;

        // Load image
        String imagePath = String.format(IMAGE_PATH_TEMPLATE, imageIndex);
        try {
            image = new Image(AdventCalender.class.getResource(imagePath).toExternalForm().toString());
            if (image.isError()) throw new Exception("Image not found.");
        } catch (Exception e) {
            // Load the default image if the specified image is not found
            image = new Image(AdventCalender.class.getResource(DEFAULT_IMAGE).toExternalForm().toString());
        }

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);

        imagePane.getChildren().add(imageView);

        Scene imageScene = new Scene(imagePane, 400, 400);
        imageStage.setTitle("Image " + imageIndex);
        imageStage.setScene(imageScene);
        imageStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
