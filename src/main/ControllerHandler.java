package main;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerHandler {

    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean spacePressed = false; // jump or action
    public boolean pausePressed = false;

    private boolean pausePressedPrev = false; // for edge detection

    private Controller gamepad;

    public ControllerHandler() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (Controller c : controllers) {
            if (c.getType() == Controller.Type.GAMEPAD || c.getType() == Controller.Type.STICK) {
                gamepad = c;
                System.out.println("Gamepad found: " + gamepad.getName());
                break;
            }
        }
        if (gamepad == null) {
            System.out.println("No gamepad found.");
        }
    }

    public void update() {
        if (gamepad == null) return;

        gamepad.poll();
        Component[] components = gamepad.getComponents();

        float xAxisValue = 0.0f;
        float yAxisValue = 0.0f;
        float povValue = -1.0f;
        boolean xButtonPressed = false;
        boolean pauseButtonPressed = false;

        for (Component c : components) {
            Component.Identifier id = c.getIdentifier();
            float value = c.getPollData();

            if (id == Component.Identifier.Axis.POV) {
                povValue = value;
            }
            if (id == Component.Identifier.Axis.X) {
                xAxisValue = value;
            }
            if (id == Component.Identifier.Axis.Y) {
                yAxisValue = value;
            }
            if (id == Component.Identifier.Button._1) { // X button (example)
                xButtonPressed = value == 1.0f;
            }
            if (id == Component.Identifier.Button._7) { // Start button = pause
                pauseButtonPressed = value == 1.0f;
            }
        }

        // Movement logic: D-Pad (POV) takes priority, fallback to analog stick
        leftPressed = povValue == 0.25f || xAxisValue < -0.5f;
        rightPressed = povValue == 0.75f || xAxisValue > 0.5f;
        upPressed = povValue == 0.0f || yAxisValue < -0.5f;
        downPressed = povValue == 1.0f || yAxisValue > 0.5f;
        spacePressed = xButtonPressed;

        // Pause button edge detection (only true once when button is pressed)
        if (pauseButtonPressed && !pausePressedPrev) {
            pausePressed = true;
        } else {
            pausePressed = false;
        }
        pausePressedPrev = pauseButtonPressed;
    }
}
