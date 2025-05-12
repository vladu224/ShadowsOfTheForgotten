package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Book extends SuperObject {

    public OBJ_Book() {

        name = "Book";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Book.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
