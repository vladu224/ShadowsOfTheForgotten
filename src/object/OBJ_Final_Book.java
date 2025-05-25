package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Final_Book extends SuperObject{

    public OBJ_Final_Book() {

        name = "Final_Book";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Final_Book.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
