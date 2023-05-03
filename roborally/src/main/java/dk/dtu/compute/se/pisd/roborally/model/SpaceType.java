package dk.dtu.compute.se.pisd.roborally.model;
import dk.dtu.compute.se.pisd.roborally.fieldActions.*;
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
    GREEN_CONVEYOR_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/GreenConveyorUp.png", new GreenConveyor(Heading.NORTH)),
    GREEN_CONVEYOR_DOWN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/GreenConveyorDown.png", new GreenConveyor(Heading.SOUTH)),
    GREEN_CONVEYOR_LEFT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/GreenConveyorLeft.png", new GreenConveyor(Heading.WEST)),
    GREEN_CONVEYOR_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/GreenConveyorRight.png", new GreenConveyor(Heading.EAST)),
    BLUE_CONVEYOR_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/BlueConveyorUp.png", new BlueConveyor(Heading.NORTH)),
    BLUE_CONVEYOR_DOWN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/BlueConveyorDown.png", new BlueConveyor(Heading.SOUTH)),
    BLUE_CONVEYOR_LEFT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/BlueConveyorLeft.png", new BlueConveyor(Heading.WEST)),
    BLUE_CONVEYOR_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/BlueConveyorRight.png", new BlueConveyor(Heading.EAST)),
    PUSH_PANEL_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/PushPanelUp.png", new PushPanel(Heading.SOUTH), Heading.NORTH),
    PUSH_PANEL_DOWN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/PushPanelDown.png", new PushPanel(Heading.NORTH), Heading.SOUTH),
    PUSH_PANEL_LEFT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/PushPanelLeft.png", new PushPanel(Heading.EAST), Heading.WEST),
    PUSH_PANEL_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/PushPanelRight.png", new PushPanel(Heading.WEST), Heading.EAST),
    GEAR_RED("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/GearRedRight.png", new GearRed()),
    GEAR_GREEN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/GearGreenLeft.png", new GearGreen()),
    PIT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/pit.png", new Pit()),
    SPAWN_SPACE_PLAYER1("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/SpawnSpace.png", new SpawnSpace(0)),
    ONE_LASER_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneLaserUp.png", new Laser(1, Heading.SOUTH), Heading.NORTH),
    TWO_LASER_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/TwoLaserUp.png", new Laser(2, Heading.SOUTH), Heading.NORTH),
    THREE_LASER_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/ThreeLaserUp.png", new Laser(3, Heading.SOUTH), Heading.NORTH),
    ONE_LASER_DOWN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneLaserDown.png", new Laser(1, Heading.NORTH), Heading.SOUTH),
    TWO_LASER_DOWN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/TwoLaserDown.png", new Laser(2, Heading.NORTH), Heading.SOUTH),
    THREE_LASER_DOWN("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/ThreeLaserDown.png", new Laser(3, Heading.NORTH), Heading.SOUTH),
    ONE_LASER_LEFT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneLaserLeft.png", new Laser(1, Heading.EAST), Heading.WEST),
    TWO_LASER_LEFT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/TwoLaserLeft.png", new Laser(2, Heading.EAST), Heading.WEST),
    THREE_LASER_LEFT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/ThreeLaserLeft.png", new Laser(3, Heading.EAST), Heading.WEST),
    ONE_LASER_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneLaserRight.png", new Laser(1, Heading.WEST), Heading.EAST),
    TWO_LASER_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/TwoLaserRight.png", new Laser(2, Heading.WEST), Heading.EAST),
    THREE_LASER_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/ThreeLaserRight.png", new Laser(3, Heading.WEST), Heading.EAST),
    BORDER_LEFT(Heading.WEST),
    BORDER_RIGHT(Heading.EAST),
    BORDER_UP(Heading.NORTH),
    BORDER_DOWN(Heading.SOUTH),
    BORDER_CORNER_TOP_LEFT(Heading.NORTH, Heading.WEST),
    BORDER_CORNER_TOP_RIGHT(Heading.NORTH, Heading.EAST),
    BORDER_CORNER_BOTTOM_LEFT(Heading.SOUTH, Heading.WEST),
    BORDER_CORNER_BOTTOM_RIGHT(Heading.SOUTH, Heading.EAST),
    ONE_EMPTY_LASER_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneEmptyLaserUp.png", new DefaultField()),
    ONE_EMPTY_LASER_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneEmptyLaserRight.png", new DefaultField()),
    BORDER_CORNER_LEFT_ONE_EMPTY_LASER_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneEmptyLaserRight.png", new DefaultField(), Heading.WEST),
    BORDER_CORNER_RIGHT_ONE_EMPTY_LASER_RIGHT("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneEmptyLaserRight.png", new DefaultField(), Heading.EAST),
    BORDER_CORNER_BOTTOM_ONE_EMPTY_LASER_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneEmptyLaserUp.png", new DefaultField(), Heading.SOUTH),
    BORDER_CORNER_TOP_ONE_EMPTY_LASER_UP("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/OneEmptyLaserUp.png", new DefaultField(), Heading.NORTH),
    SPAWN_SPACE_PLAYER2("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/SpawnSpace.png", new SpawnSpace(1)),
    SPAWN_SPACE_PLAYER3("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/SpawnSpace.png", new SpawnSpace(2)),
    SPAWN_SPACE_PLAYER4("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/SpawnSpace.png", new SpawnSpace(3)),
    SPAWN_SPACE_PLAYER5("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/SpawnSpace.png", new SpawnSpace(4)),
    SPAWN_SPACE_PLAYER6("file:roborally/src/main/java/dk/dtu/compute/se/pisd/roborally/images/SpawnSpace.png", new SpawnSpace(5)),
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
