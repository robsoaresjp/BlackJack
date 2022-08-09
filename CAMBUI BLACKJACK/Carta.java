import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Carta extends Parent
{
    private static final int CARTA_WIDTH = 100;
    private static final int CARTA_HEIGHT = 140;
    enum Suit
    {
        PAUS, COPAS, ESPADAS, OURO

        final Image images;

        Suit() {
            this.images = new Image(Carta.class.getResourceAsStream("images/".concat(name().toLowerCase()).concat(".png")),
                    32, 32, true, true);
    };

    enum Rank
    {
        DOIS(2), TRES(3), QUATRO(4), CINCO(5), SEIS(6), SETE(7), OITO(8), NOVE(9),
        DEZ(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        final int value;
        Rank(int value)
        {
            this.value = value;
        }

        String displayName() {
            return ordinal() < 9 ? String.valueOf(value) : name().substring(0, 1);
    };
    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Carta(Suit suit, Rank rank)
    {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;

        Rectangle bg = new Rectangle(CARTA_WIDTH, CARTA_HEIGHT);
        bg.setArcWidth(20);
        bg.setArcHeight(20);
        bg.setFill(Color.WHITE);

        Text text1 = new Text(rank.displayName());
        text1.setFont(Font.font(18));
        text1.setX(CARTA_WIDTH - text1.getLayoutBounds().getWidth() - 10);
        text1.setY(text1.getLayoutBounds().getHeight());

        Text text2 = new Text(text1.getText());
        text2.setFont(Font.font(18));
        text2.setX(10);
        text2.setY(CARTA_HEIGHT - 10);

        ImageView view = new ImageView(suit.images);
        view.setRotate(180);
        view.setX(CARTA_WIDTH - 32);
        view.setY(CARTA_HEIGHT - 32);

        getChildren().addAll(bg, new ImageView(suit.images), view, text1, text2);

    }

        @Override
        public String toString()
        {
            return rank.toString() + " of " + suit.toString();
        }
}

