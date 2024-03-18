package no.ntnu.idatx2003.oblig3.cardgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CardGameGUI extends Application {

    private DeckOfCards deckOfCards;
    private VBox root;
    private TextArea resultArea; // Tekstområde for å vise den utdelte hånden
    private TextArea checkResultArea; // Tekstområde for å vise resultatet av "Check Hand" -handlingene
    private List<PlayingCard> currentHand; // Lagrer den trukne hånden

    @Override
    public void start(Stage primaryStage) {
        deckOfCards = new DeckOfCards();

        Button dealHandButton = new Button("Deal Hand"); // Knapp for å dele ut en hånd
        dealHandButton.setOnAction(e -> dealHand());

        Button checkHandButton = new Button("Check Hand"); // Knapp for å sjekke hånden
        checkHandButton.setOnAction(e -> checkHand());

        resultArea = new TextArea(); // Tekstområde for å vise den utdelte hånden
        resultArea.setEditable(false); // Gjør tekstområdet for den utdelte hånden ikke-redigerbart
        resultArea.setPrefRowCount(4); // Setter foretrukket antall rader til 4

        checkResultArea = new TextArea(); // Tekstområde for å vise resultatet av "Check Hand" -handlingene
        checkResultArea.setEditable(false); // Gjør tekstområdet for "Check Hand" -resultatene ikke-redigerbart
        checkResultArea.setPrefRowCount(4); // Setter foretrukket antall rader til 4

        HBox buttonBox = new HBox(dealHandButton, checkHandButton); // Horisontal boks for knappene
        buttonBox.setSpacing(10); // Angir avstanden mellom knappene

        VBox resultBox = new VBox(resultArea, checkResultArea); // Vertikal boks for å vise tekstområdene
        resultBox.setSpacing(10); // Angir avstanden mellom tekstområdene

        root = new VBox(10); // Vertikal boks for rotkontrollen med en avstand på 10
        root.setPadding(new Insets(10)); // Angir en innpolstring på 10
        root.getChildren().addAll(buttonBox, resultBox); // Legger til knappboksen og resultatboksen som barn av rotkontrollen

        Scene scene = new Scene(root, 300, 200); // Oppretter en scene med rotkontrollen og en størrelse på 300x200 piksler
        primaryStage.setScene(scene); // Setter scenen på primærstadiet
        primaryStage.setTitle("Card Game GUI"); // Setter tittelen på primærstadiet til "Card Game GUI"
        primaryStage.show(); // Viser primærstadiet
    }

    private void dealHand() {
        int handSize = 5; // Størrelsen på hånden som skal trekkes
        currentHand = deckOfCards.dealHand(handSize); // Lagrer den trukne hånden
        StringBuilder handString = new StringBuilder();
        for (PlayingCard card : currentHand) { // Trekker en hånd med kort fra kortstokken
            handString.append(card.getAsString()).append(" "); // Legger til kortene i en streng med mellomrom mellom hvert kort
        }

        resultArea.setText(handString.toString()); // Setter den utdelte hånden i tekstområdet
    }

    private void checkHand() {
        if (currentHand == null) {
            checkResultArea.setText("Deal hand first."); // Gir en melding hvis det ikke er noen hånd å sjekke
            return;
        }

        checkResultArea.setText(""); // Nullstiller tekstområdet for "Check Hand" -resultatene

        // Beregner summen av kortverdiene
        int sumOfValues = currentHand.stream()
                .mapToInt(PlayingCard::getFace) // Bruker getFace() for å hente kortets verdi
                .sum();
        checkResultArea.appendText("Sum of cards: " + sumOfValues + "\n");

        // Filtrer og samle hjerter
        List<PlayingCard> hearts = currentHand.stream()
                .filter(card -> card.getSuit() == '♥') // Hjerter representeres av '♥'
                .collect(Collectors.toList());

        if (!hearts.isEmpty()) {
            String heartsString = hearts.stream()
                    .map(PlayingCard::getAsString)
                    .collect(Collectors.joining(" "));
            checkResultArea.appendText("Hearts: " + heartsString + "\n");
        } else {
            checkResultArea.appendText("No hearts\n");
        }

        // Sjekk om queen of spades finnes
        boolean containsQueenOfSpades = currentHand.stream()
                .anyMatch(card -> card.getFace() == 12 && card.getSuit() == '♠'); // Spar Dame representeres av 12 og '♠'
        checkResultArea.appendText("Queen of spades: " + (containsQueenOfSpades ? "Yes" : "No") + "\n");

        // Sjekk om det er en flush
        boolean isFiveFlush = Arrays.asList('♥', '♦', '♣', '♠').stream() // Liste over alle farger
                .anyMatch(suit -> currentHand.stream()
                        .filter(card -> card.getSuit() == suit)
                        .count() >= 5);
        checkResultArea.appendText("Flush: " + (isFiveFlush ? "Yes" : "No") + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
