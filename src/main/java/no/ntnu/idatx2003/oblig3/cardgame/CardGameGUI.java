package no.ntnu.idatx2003.oblig3.cardgame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CardGameGUI extends Application {

    private DeckOfCards deckOfCards;
    private VBox root; // Legg til rotkontrollen som en instansvariabel

    @Override
    public void start(Stage primaryStage) {
        deckOfCards = new DeckOfCards();

        // Opprett knappen for å dele ut en hånd med kort
        Button dealHandButton = new Button("Deal Hand");
        dealHandButton.setOnAction(e -> dealHand());

        // Opprett tekstområdet for å vise resultatet av den utdelte hånden
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(4);

        // Opprett layout og legg til knappen og tekstområdet
        root = new VBox(10); // Initialiser rotkontrollen
        root.setPadding(new Insets(10));
        root.getChildren().addAll(dealHandButton, resultArea);

        // Opprett og vis scenen
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Card Game GUI");
        primaryStage.show();
    }

    private void dealHand() {
        // Trekker en hånd med 5 kort fra kortstokken
        int handSize = 5;
        StringBuilder handString = new StringBuilder();
        for (PlayingCard card : deckOfCards.dealHand(handSize)) {
            handString.append(card.getAsString()).append(" ");
        }

        // Viser den utdelte hånden i tekstområdet
        TextArea resultArea = (TextArea) root.getChildren().get(1); // Henter tekstområdet fra rotkontrollen
        resultArea.setText(handString.toString());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
