import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Mao
{
    private ObservableList<Node> cartas;
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);
    private int aces = 0;

    public Mao(ObservableList<Node> cartas)
    {
        this.cartas = cartas;
    }

    public void maoCarta(Carta carta)
    {
        cartas.add(carta);

        if (carta.rank == Carta.Rank.ACE)
        {
            aces++;
        }if (value.get() + carta.value > 21 && aces > 0)
        {
            value.set(value.get() + carta.value - 10); //aqui conta "A" como '1' e não '11'
            aces --;
        }else
        {
            value.set(value.get() + carta.value);
        }
    }

    public void reset()
    {
        cartas.clear();
        value.set(0);
        aces = 0;
    }

    public SimpleIntegerProperty valueProperty()
    {
        return value;
    }
}
