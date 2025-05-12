package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Diamond extends SuperObject {

    public OBJ_Diamond() {

        name = "Diamond";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Diamond.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
