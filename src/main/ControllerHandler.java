package main;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerHandler {

    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean spacePressed = false; // Changed from jumpPressed

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
        float povValue = 0.0f;
        boolean xButtonPressed = false;

        for (Component c : components) {
            Component.Identifier id = c.getIdentifier();
            float value = c.getPollData();

            // D-Pad support
            if (id == Component.Identifier.Axis.POV) {
                povValue = value;
            }

            // Left stick X-axis
            if (id == Component.Identifier.Axis.X) {
                xAxisValue = value;
            }

            // ✕ button (PlayStation): often Button._1
            if (id == Component.Identifier.Button._1) {
                xButtonPressed = value == 1.0f;
            }
        }

        // Movement: D-Pad takes priority, fallback to analog stick
        leftPressed = povValue == 0.25f || xAxisValue < -0.5f;
        rightPressed = povValue == 0.75f || xAxisValue > 0.5f;
        spacePressed = xButtonPressed;
    }
}
