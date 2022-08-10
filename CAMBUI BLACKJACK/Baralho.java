public class Baralho
{
    private final Carta[] cartas = new Carta[52];

    public Baralho()
    {
        refill();
    }

    public final void refill()
    {
        int i = 0;
        for (Carta.Suit suit : Carta.Suit.values())
        {
            for (Carta.Rank rank : Carta.Rank.values())
            {
                cartas[i++] = new Carta(suit, rank);
            }
        }
    }

    public Carta empateCarta()
    {
        Carta carta = null;
        while (carta == null)
        {
            int index = (int) (Math.random() * cartas.length);
            carta = cartas[index];
            cartas[index] = null;
        }
        return carta;
    }

}

