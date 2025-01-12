package com.example.game1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private Camera camera;
    private Monster monster;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/healthbar.fxml"));
            Pane root = loader.load();

            // Access the controller
            HealthBarController controller = loader.getController();

            // Interact with shield bar (example: adjust size or color)
            controller.updateShieldBar(1.0); // Reset shield bar to full
            controller.takeShieldDamage();   // Simulate shield damage

            // Create Scene with fixed size
            Scene scene = new Scene(root, 800, 600);

            // Map data matrix
            int[][] mapData = {
                    {0, 0, 1, 1, 2, 2, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                    {1, 1, 0, 2, 0, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                    {1, 2, 2, 0, 0, 1, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2},
                    {0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2, 0, 1, 1, 2, 2, 0, 2, 0, 0, 1, 1, 2, 2, 2}
            };

            // Create the map
            TileMap map = new TileMap(mapData, root);

            // Ensure health bars stay on top
            controller.getGreenbar().toFront();
            controller.getShieldbar().toFront();

            // Character animation paths
            String[] shieldFramesPaths = {
                    "/character/shield/frame1.png", "/character/shield/frame2.png"
            };
            String[] hurtFramesPaths = {
                    "/character/hurt/frame1.png", "/character/hurt/frame2.png"
            };
            String[] runFramesPaths = {
                    "/character/run/frame1.png", "/character/run/frame2.png"
            };
            String[] idleFramesPaths = {
                    "/character/idle/frame1.png", "/character/idle/frame2.png"
            };
            String[] jumpFramesPaths = {
                    "/character/jump/frame1.png", "/character/jump/frame2.png"
            };
            String[] walkFramesPaths = {
                    "/character/walk/frame1.png", "/character/walk/frame2.png"
            };
            String[][] attackFramesPaths = {
                    {"/character/attack/frame1.png", "/character/attack/frame2.png"}
            };

            // Create the main character
            Character character = new Character(
                    walkFramesPaths,
                    jumpFramesPaths,
                    idleFramesPaths,
                    runFramesPaths,
                    attackFramesPaths,
                    hurtFramesPaths,
                    shieldFramesPaths,
                    100,
                    500
            );

            // Add character to the root Pane
            root.getChildren().add(character.getSprite());

            // Monster animation paths
            String[] monsterWalkFrames = {
                    "/monster/walk/frame1.png", "/monster/walk/frame2.png"
            };

            // Create a monster
            monster = new Monster(monsterWalkFrames, 300.0, 500.0);
            root.getChildren().add(monster.getSprite());

            // Initialize the camera
            camera = new Camera(800, 600, root);

            // Set up and show Stage
            primaryStage.setTitle("Game Map Example");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Keyboard controls for the character
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case A -> character.setSpeedX(-2); // Move left
                    case D -> character.setSpeedX(2);  // Move right
                    case SPACE -> character.jump();    // Jump
                    case J -> character.attack();      // Attack
                    case H -> character.hurt();        // Take damage
                    case S -> character.shield();      // Activate shield
                    case K -> controller.takeShieldDamage(); // Decrease shield
                    case R -> controller.updateShieldBar(1.0); // Reset shield bar
                }
            });

            scene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case A, D -> character.setSpeedX(0);  // Stop movement
                    case S -> character.stopShielding(); // Deactivate shield
                }
            });

            // Game loop
            new javafx.animation.AnimationTimer() {
                @Override
                public void handle(long now) {
                    updateGame(character);
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Exit program if loading fails
        }
    }

    private void updateGame(Character character) {
        // Update the camera to follow the character
        camera.update(character.getX(), character.getY());

        // Update monster's target to the character's position
        monster.setTarget(character.getX(), character.getY());

        // Move the monster
        monster.move();

        // Additional game logic can be added here
    }

    public static void main(String[] args) {
        launch(args);
    }
}
