package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import core.items.Coin;
import core.items.PriceFormat;
import core.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * @author Jakob Relling
 */

public class PersonalInformationController {

    @FXML
    private BorderPane borderpane = new BorderPane();

    @FXML
    private ListView<Coin> ownCoinsListView = new ListView<>();
    @FXML
    private ListView<Coin> allCoinsListView = new ListView<>();
    @FXML
    private TextField changeAmount;

    @FXML
    private Label errorMessage = new Label();

    /**
     * label for username, mail, userID, coin and coinLabel
     */
    @FXML
    private Label userID, name, mail, coin, coinAmount, currentMode, marketCapUsdValue, price, supply, supplyTag,
            volume, vwap24HrTag, vwap24Hr, totalPortfolioValue;

    /**
     * Buttons in the fxml file
     * F_button - sets the scene to finance.fxml
     * S_button - sets the scene to settings.fxml
     * CU_button - sets the scene to contactus.fxml
     */
    @FXML
    private Button F_button, S_button, CU_button, buyButton, sellButton, proButton;

    /**
     * this is the user that is currently logged in.
     */
    private User currentUser;

    /**
     * collection of all legal coins from API
     */
    private ArrayList<Coin> allCoins;

    /**
     * map of coins that the current user object has in his/her inventory
     */
    // private Map<String, Double> ownedCoins;

    /**
     * a Coin object that keeps control over what coin the user wants to buy
     */
    private Coin selectedCoin;

    /**
     * Initializes the current user on this controller. This lets us delived data
     * between scenes
     * and controllers
     * 
     * @param user     - user object that is logged in
     * @param allCoins - all legal coins from API
     * @throws ConnectException
     * 
     */
    public void initData(final String userId, Collection<Coin> allCoins) throws ConnectException {
        this.currentUser = RequestManagement.getUserWithID(userId);
        this.allCoins = new ArrayList<>(allCoins);
        this.currentMode.setText((currentUser.isPro()) ? "Pro" : "Normal");
        initListView();
    }

    /**
     * Returns the corresponding coin object to a id.
     *
     * @param id
     * @return
     */
    public Coin getCoinObject(String id) {
        for (Coin coin : allCoins) {
            if (id.equals(coin.getId())) {
                return coin;
            }
        }
        return null;
    }

    /**
     * Renderes information about the currently selected coin
     */
    public void renderCoinInformation() {
        if (selectedCoin != null) {
            if (!currentUser.getOwnedCoins().isEmpty()) {
                if (currentUser.getOwnedCoins().get(selectedCoin.getId()) != null) {
                    coinAmount.setText(Double.toString(currentUser.getOwnedCoins().get(selectedCoin.getId())));
                } else {
                    coinAmount.setText("0");
                }
            }
            coin.setText(selectedCoin.getName());
            Double amount = 0.0;
            if (currentUser.getOwnedCoins().get(selectedCoin.getId()) != null) {
                amount = currentUser.getOwnedCoins().get(selectedCoin.getId());
            }
            coinAmount.setText(Double.toString(amount));
            marketCapUsdValue.setText(selectedCoin.prettyMarketCapUsd());
            volume.setText(selectedCoin.prettyVolumeUsd24Hr());
            price.setText(selectedCoin.prettyPriceUsd());
            if (currentUser.isPro()) {
                supplyTag.setText("Supply");
                supply.setText(selectedCoin.prettySupply());
                vwap24HrTag.setText("vwap24hr");
                vwap24Hr.setText(selectedCoin.prettyVwap24hr());
            } else {
                supplyTag.setText("");
                supply.setText("");
                vwap24HrTag.setText("");
                vwap24Hr.setText("");
            }
        }
    }

    /**
     * Initilizes myListView in order for the user to se what coins they have.
     * Sets up addlistener in order for the user to select coins and buy/sell
     */
    private void initListView() {
        ownCoinsListView.setEditable(true);
        allCoinsListView.setEditable(true);
        allCoinsListView.getItems().addAll(getAllCoins());
        allCoinsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue() != null) {
                ownCoinsListView.getSelectionModel().clearSelection();
                selectedCoin = allCoinsListView.getSelectionModel().getSelectedItem();
                renderCoinInformation();
            }
        });

        List<Coin> coins = new ArrayList<>();
        for (String name : currentUser.getOwnedCoins().keySet()) {
            coins.add(getCoinObject(name));
        }

        ownCoinsListView.setCellFactory(new Callback<ListView<Coin>, ListCell<Coin>>() {

            @Override
            public ListCell<Coin> call(ListView<Coin> param) {
                ListCell<Coin> cell = new ListCell<Coin>() {
                    @Override
                    protected void updateItem(Coin coin, boolean empty) {
                        super.updateItem(coin, empty);
                        if (coin != null) {
                            setText(coin.getName() + " - " + PriceFormat.priceNumberFormat(
                                    coin.getPriceUsd() * currentUser.getOwnedCoins().get(coin.getId())));
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }

        });

        ownCoinsListView.getItems().addAll(coins);
        updatePortFolioValue();

        ownCoinsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue() != null) {
                allCoinsListView.getSelectionModel().clearSelection();
                selectedCoin = ownCoinsListView.getSelectionModel().getSelectedItem();
                renderCoinInformation();
            }
        });
    }

    /**
     * mvn
     * 
     * @return the amount of change described in changeAmount textfield
     */
    private double getChangeAmount() {
        if (this.changeAmount.getText().contains("."))
            return Double.parseDouble(this.changeAmount.getText());
        return Double.parseDouble(this.changeAmount.getText() + ".0");

    }

    /**
     * Refreshes myListView and resfreshes the screen so the user gets updated
     * info. Saves the new values to the userfile.
     *
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private void updateListView() throws IllegalArgumentException, IOException {
        ownCoinsListView.getItems().clear();
        updatePortFolioValue();
        ownCoinsListView.getItems()
                .addAll(currentUser.getOwnedCoins().keySet().stream().map(this::getCoinObject).toList());
    }

    /**
     * Adds the coins to the current user's portfolio.
     * Updates the screen so the user can se his updated portfolio
     */
    @FXML
    private void buyCoins() {
        try {
            Double amount = getChangeAmount();
            String strAmount = amount.toString();
            RequestManagement.addCurrency(getCurrentUser().getUserId().toString(), selectedCoin.getId(),
                    strAmount);
            this.currentUser = RequestManagement.getUserWithID(getCurrentUser().getUserId().toString());
            // currentUser.addCurrency(selectedCoin.getId(), getChangeAmount());
            updateListView();
            renderCoinInformation();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Removes the coins from the current user's portifolio.
     * Updates the screen so the user can se his opdated portifolio
     */
    @FXML
    private void sellCoins() {
        try {
            RequestManagement.removeCurrency(getCurrentUser().getUserId().toString(),
                    selectedCoin.getId(), getChangeAmount() + "");
            this.currentUser = RequestManagement.getUserWithID(getCurrentUser().getUserId().toString());
            // currentUser.removeCurrency(selectedCoin.getId(), getChangeAmount());
            updateListView();
            renderCoinInformation();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * 
     * @return a collection of all available coins
     */
    private Collection<Coin> getAllCoins() {
        return new ArrayList<>(this.allCoins);
    }

    /**
     * 
     * @return - user that is currently logged in.
     */
    private User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * This method updates.
     */
    private void updatePortFolioValue() {
        totalPortfolioValue.setText(PriceFormat.priceNumberFormat(getCurrentUser().getOwnedCoins().keySet().stream()
                .map(this::getCoinObject).toList()
                .stream().mapToDouble(p -> p.getPriceUsd() * currentUser.getOwnedCoins().get(p.getId())).sum()));
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setFinance(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "finance", getCurrentUser().getUserId().toString(), allCoins);
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setSettings(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "settings", getCurrentUser().getUserId().toString(), allCoins);
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void setContactUs(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "contactus", getCurrentUser().getUserId().toString(), allCoins);
    }

    /**
     * changes the scene
     * 
     * @param event - buttonclickevent in fxml
     * @throws IOException - if file is not found
     */
    @FXML
    private void loggOut(ActionEvent event) throws IOException {
        SceneChanger.changeStage(this, event, "welcomescreen", null, null);
    }

    /**
     * Toggles pro mode and rerenderes coin information to match pro mode status
     */
    @FXML
    private void togglePro() {
        try {
            RequestManagement.togglePro(getCurrentUser().getUserId().toString());
            this.currentUser.togglePro();
            this.currentMode.setText((currentUser.isPro()) ? "Pro" : "Normal");
            renderCoinInformation();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}