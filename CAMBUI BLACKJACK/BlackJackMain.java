import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackJackMain extends Application
{
    private Baralho baralho = new Baralho();
    private Mao dealer, player;
    private Text message = new Text();

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    private HBox dealerCartas = new HBox(20);
    private HBox playerCartas = new HBox(20);

    private Parent createContent() {
        dealer = new Mao(dealerCartas.getChildren());
        player = new Mao(playerCartas.getChildren());

    Pane mesa = new Pane();
    mesa.setPrefSize(800, 600);

    Region fundo = new Region();
    fundo.setPrefSize(800, 600);
    fundo.setStyle("-fx-fundo-color: rgba(0,0,0,1)");

    HBox mesaLayout = new HBox(5);
    mesaLayout.setPadding(new Insets(5, 5, 5, 5));
    Rectangle esquerdoBG = new Rectangle(550, 560);
    esquerdoBG.setArcWidth(50);
    esquerdoBG.setArcHeight(50);
    esquerdoBG.setFill(Color.GREEN);
    Rectangle direitoBG = new Rectangle(230, 560);
    direitoBG.setArcWidth(50);
    direitoBG.setArcHeight(50);
    direitoBG.setFill(Color.ORANGE);

    //LADO ESQUERO
    VBox esquerdoVBox = new VBox(50);
    esquerdoVBox.setAlignment(Pos.TOP_CENTER);

    Text dealerScore = new Text("Dealer: ");
    Text playerScore = new Text("Player: ");

    esquerdoVBox.getChildren().addAll(dealerScore, dealerCartas, message, playerCartas, playerScore);

    //LADO DIREITO
    VBox direitoVBox = new VBox(20);
    direitoVBox.setAlignment(Pos.CENTER);

    final TextField bet = new TextField("BET");
    bet.setDisable(true);
    bet.setMaxWidth(50);
    Text money = new Text("MONEY");

    Button btnPlay = new Button("PLAY");
    Button btnHit = new Button("HIT");
    Button btnStand = new Button("STAND");

    HBox buttonsHBox = new HBox(15, btnHit, btnStand);
    buttonsHBox.setAlignment(Pos.CENTER);
    direitoVBox.getChildren().addAll(bet, btnPlay, money, buttonsHBox);


    //ADICIONE AMBAS AS PILHAS AO LAYOUT DA MESA
        mesaLayout.getChildren().addAll(new StackPane(esquerdoBG, esquerdoVBox), new StackPane(direitoBG, direitoVBox));
        mesa.getChildren().addAll(fundo, mesaLayout);

    //PROPRIEDADES DO BIND
        btnPlay.disableProperty().bind(playable);
        btnHit.disableProperty().bind(playable.not());
        btnStand.disableProperty().bind(playable.not());

        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));

        player.valueProperty().addListener((obs, old, newValue) ->
        {
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        dealer.valueProperty().addListener((obs, old, newValue) ->
        {
            if (newValue.intValue() >= 21) {
                endGame();
            }
        });

        // BOTÕES DE INICIALIZAÇÃO

        btnPlay.setOnAction(event ->
        {
            startNewGame();
        });

        btnHit.setOnAction(event ->
        {
            player.levaCarta(baralho.empateCarta());
        });

        btnStand.setOnAction(event ->
        {
            while (dealer.valueProperty().get() < 17) {
                dealer.levaCarta(baralho.empateCarta());
            }

            endGame();
        });

        return mesa;
    }

    private void startNewGame()
    {
        playable.set(true);
        message.setText("");

        baralho.refill();

        dealer.reset();
        player.reset();

        dealer.levaCarta(baralho.empateCarta());
        dealer.levaCarta(baralho.empateCarta());
        player.levaCarta(baralho.empateCarta());
        player.levaCarta(baralho.empateCarta());
    }

    private void endGame()
    {
        playable.set(false);

        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();
        String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

        // the order of checking is important
        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
                || (dealerValue < 21 && dealerValue > playerValue))
        {
            winner = "DEALER";
        } else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue)
        {
            winner = "PLAYER";
        }

        message.setText(winner + " WON");
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlackJack");
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }


}
