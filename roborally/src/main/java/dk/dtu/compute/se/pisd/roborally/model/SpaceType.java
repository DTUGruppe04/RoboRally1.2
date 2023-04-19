package dk.dtu.compute.se.pisd.roborally.model;
import dk.dtu.compute.se.pisd.roborally.controller.CheckPoint;
import dk.dtu.compute.se.pisd.roborally.controller.DefaultField;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.Pit;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum SpaceType {
    EMPTY_SPACE(),
    CHECKPOINT1("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/Checkpoint.png", new CheckPoint(1)),
    CHECKPOINT2("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/Checkpoint2.png", new CheckPoint(2)),
    CHECKPOINT3("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/Checkpoint3.png", new CheckPoint(3)),
    CHECKPOINT4("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/Checkpoint4.png", new CheckPoint(4)),
    CHECKPOINT5("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/Checkpoint5.png", new CheckPoint(5)),
    PIT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/pit.png", new Pit()),
    BORDER_LEFT(Heading.WEST), BORDER_RIGHT(Heading.EAST), BORDER_UP(Heading.NORTH), BORDER_DOWN(Heading.SOUTH),
    BORDER_CORNER_TOP_LEFT(Heading.NORTH, Heading.WEST), BORDER_CORNER_TOP_RIGHT(Heading.NORTH, Heading.EAST),
    BORDER_CORNER_BOTTOM_LEFT(Heading.SOUTH, Heading.WEST), BORDER_CORNER_BOTTOM_RIGHT(Heading.SOUTH, Heading.EAST)
    ;
    final public Background Background;
    public Border Borders;
    final public List<Heading> BorderHeadings;
    final public FieldAction fieldAction;
    private static final SpaceType[] values = values();

    public static SpaceType get(int ordinal) { return values[ordinal]; }
    //tile with custom background and optional borders
    SpaceType(String imagePath, FieldAction fieldAction, Heading... borderHeadings){
        Image image = new Image(imagePath);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        Background = new Background(backgroundImage);
        this.BorderHeadings = Collections.unmodifiableList(Arrays.asList(borderHeadings));
        createBorder();
        this.fieldAction = fieldAction;
    }
    //tile with standard background, and optional borders
    SpaceType(Heading... borderHeadings){
        Image image = new Image("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/normalTile.png");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
        Background = new Background(backgroundImage);
        this.BorderHeadings = Collections.unmodifiableList(Arrays.asList(borderHeadings));
        createBorder();
        this.fieldAction = new DefaultField();
    }

    private void createBorder(){
        double up = 0;
        double left = 0;
        double right = 0;
        double down = 0;
        for(Heading heading : BorderHeadings) {
            switch (heading) {
                case NORTH -> up = 5.0;
                case SOUTH -> down = 5.0;
                case EAST -> right = 5.0;
                case WEST -> left = 5.0;
            }
        }
        this.Borders = new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(up,right,down,left)));
    }
}
