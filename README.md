# Pacman_Project
To run this, place the "src" folder with "Pacman_Game.java" and "Start.java" into the main directory. For the sprites to appear in the game, place the "images" folder within the main directory as well.

# 3rd Release Changes
## Goal: Service-oriented programming 
- Add enemy AI algorithm(service)
- Add scores for gameplay
- Optimize gameplay pace

  - minor changes like player speed, enemy speed
 
 - Optimize map drawing algorithm(service)
 
 # Service/Functionality 
 ## Enemy AI algorithm
 We have implemented an algorithm for Pacman ghost that wonders around the map. 
             
           for (i = 0; i < N_GHOSTS; i++) {
                if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                        pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);
                        count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                       dx[count] = -1;
                       dy[count] = 0;
                       count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                       dx[count] = 0;
                       dy[count] = -1;
                       count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                       dx[count] = 1;
                       dy[count] = 0;
                       count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                       dx[count] = 0;
                       dy[count] = 1;
                       count++;
                }
 
 
 
 
 If player run into an enemmy, game over.

    if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
          && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
          && inGame) {
            dying = true;
           }


 ## Map Drawing
 We have implemented a map drawing algorithm that will takes number to dertimine the maze.
 
         for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }
 
Map variable 
 
    private final short map[] = {19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 20,
        17, 16, 16, 20,  0,  0,  0, 0,  0,  0,  0, 17, 16, 16, 20,
        17, 16, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24,24, 24, 28
    };

Actual Map in game:

![Map](https://github.com/RedDogSlay/Pacman_Project/blob/3rdRelease/images/Map.png?raw=true)





# -------------------------2nd Release-------------------------
# User document
Authors: Peter Adamopoulos, Jiayu Yan, Zanxiang Wang

## Goal:
The goal of this project is to try and recreate a Pac-Man game for entertainment purposes. The end goal of this project is to create an avatar with which the player can control and can move around and collect points inside a maze while avoiding enemies from catching it much like the original game. The purpose of the game is to have players compete with one another to see who can get the highest score.

## Potential Users:
The potential users of this project are children and adults or whoever would be interested in playing the original game. Since the main purpose of this project is for pastime entertainment, anyone who may be interested in video games are potential users of the project. There is currently no fixed type of user.

## Functions:

The first function of the use case is to start the game. This action is controlled by the players. Players can start the game by clicking “S” to initialize the game when given the on-screen prompt. After user type “S”, the game will initialize the map and the Pac-Man character.

Another function is to pause the game. This function’s main purpose is to pause the game when the user chooses to during the game. This function is activated by pressing “Enter” on the keyboard, and as the name implies will temporarily pause the game and the player may choose to resume the game whenever they want.

The third function is to end the game. The purpose of this game is for player to shut down the program after finishing the game. After player's character have died from the game, the player will have the choice to start a new game or end the game and quit the program. Currently this function is not yet fully implemented as there are no enemies or way to die within the game, but the game can be quit from by pressing the close button on the interface. This function currently allows the player to shut down the program whenever they choose to.

The next functions are the movement controls for the players to control the character. Currently the movements are controlled by the arrow keys on the keyboard.

- The first function is to go up which allows the player to move the in-game character upwards.
- The second function is to go down. This function allows the player to move the in-game character downwards.
- The third function is to move left. This function allows the player to move the in-game character to move leftwards.
- The fourth function is to move right. This function allows the player to move the in-game character rightwards.
- The next function is for the program to initialize the map. This function will initialize the map or maze for the player to move the in-game character in. This function is started as soon as the program runs.

Another function with the program that is included when initializing the map, is initializing the dots that Pac-Man eats within the game. Currently there is a function to create the dots and when creating the map the dot locations are implemented into specific locations.

Another function is to initialize the character/s. This function will initialize a character for the player to control inside the initialized map by loading an image to use as a sprite.

This function will also initialize enemies, but in the current version of the game there are no enemies but will be implemented in a future release. In the original game of Pac-Man, players are chased around by several enemies and this function initializes and loads the sprites in game for the NPCs to chase around the player as well as loads the playable sprite within the map.
