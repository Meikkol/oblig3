package no.ntnu.idatx2003.oblig3.cardgame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
// klasse som representerer alle kortene
public class DeckOfCards {
    // Array for å representere fargene i kortstokken
    private final char[] suits = { '♠', '♥', '♦', '♣' };

    // Liste som inneholder alle kortene i kortstokken
    private final List<PlayingCard> deck;

    // Konstruktør som oppretter en ny kortstokk og fyller den med 52 kort
    public DeckOfCards() {
        deck = new ArrayList<>();
        initializeDeck();
    }

    // Metode for å initialisere kortstokken med alle 52 kortene
    private void initializeDeck() {
        // Loop gjennom alle fargene i kortstokken
        for (char suit : suits) {
            // Loop gjennom alle ansiktene (1 til 13) for hver farge
            for (int face = 1; face <= 13; face++) {
                // Opprett et nytt PlayingCard-objekt med gjeldende farge og ansikt
                deck.add(new PlayingCard(suit, face));
            }
        }
    }

    // Metode for å få tilgang til kortstokken
    public List<PlayingCard> getDeck() {
        return deck;
    }

    /**
     * Metode for å trekke en hånd med tilfeldige kort fra kortstokken.
     * @param n Antall kort som skal trekkes (mellom 1 og 52).
     * @return En samling (liste) med de trukkede kortene.
     * @throws IllegalArgumentException Hvis antallet kort er utenfor gyldig område.
     */
    public List<PlayingCard> dealHand(int n) {
        // Sjekk om antall kort er innenfor gyldig område
        if (n < 1 || n > 52) {
            throw new IllegalArgumentException("Number of cards must be between 1 and 52");
        }

        // Opprett en ny liste for å representere hånden
        List<PlayingCard> hand = new ArrayList<>();
        // Opprett et Random-objekt for å trekke tilfeldige kort
        Random random = new Random();

        // Bland kortstokken før vi trekker kortene
        Collections.shuffle(deck);

        // Trekker n tilfeldige kort fra kortstokken og legger dem til i hånden
        for (int i = 0; i < n; i++) {
            hand.add(deck.get(i));
        }

        // Returnerer hånden med trukkede kort
        return hand;
    }
}