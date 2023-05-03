# RoboRally1.2
How to start the project:  
Open the project with your preffered IDE.  
Make sure you have JavaFX installed and placed, preferable in the Program Files directory  
Make sure the javaFX library is added as an external library on the project. (if it is not add it yourself)  
Edit your run configuration, so it uses the javaFX library  
Run RoboRally.java and enjoy the game    

## what features does this contain  
This version of the game has fully functioning graphics and 2 different maps to play (+ a test map). 
All board elements now functions as intended, the player can shoot lasers (though it is not shown as an animation due to limitations of the graphical software).
Spam cards now exist, these cards will fill up your hand as you get hit by lasers.
Spam cards will be removed from your hand if you play them from hand (and thereby use a random action on that register) 
or land on a pit field and thereby have to reboot.
The game also have an easy way to implement new maps, they just need a matrix of spaceTypes and a name (see PremadeMaps and SpaceTypes for how to do that).
Finally, to make sure the game functions as intended, the game has a lot of tests.
