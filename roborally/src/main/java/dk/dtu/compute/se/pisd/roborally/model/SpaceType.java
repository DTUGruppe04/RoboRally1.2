package dk.dtu.compute.se.pisd.roborally.model;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SpaceType {
    EMPTY_SPACE("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/normalTile.png"),
    CHECKPOINT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/Checkpoint.png"),
    PIT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/pit.png");
    final public Background Background;
    final public List<Heading> Headings;
    SpaceType(String imagePath, Heading... headings){
        Image image = new Image(imagePath);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        Background = new Background(backgroundImage);
        this.Headings = Collections.unmodifiableList(Arrays.asList(headings));
    }
}
