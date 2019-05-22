import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game {
    private static ArrayList<Player> players;
    private static Player currentPlayer;
    private static GUI gui;
    private static Properties properties;
    private static Chance chance;
    private static Community community;
    private static int dice1;
    private static int dice2;
    private static int howManyDoubles;
    private static int position;
    private static boolean canMove;
    private static boolean endTurn;


    public Game(){
        // pyta się o ilość graczy
        String numberOfPlayers =  JOptionPane.showInputDialog("How Many Players?");
        // w przypadku nie podania prawidłowej odpowiedzi zadaje pytanie ponownie
        while (!numberOfPlayers.matches("[1-4]")){
            numberOfPlayers =  JOptionPane.showInputDialog("How Many Players? (Insert value between 1-4)");
        }

        // pyta o imiona i dodaje określoną ilość graczy
        players = new ArrayList<Player>();
        String name;
        for (int i = 0; i < Integer.parseInt(numberOfPlayers); i++){
            name = JOptionPane.showInputDialog("Name of player " + (i + 1) + ":");
            if (name.isEmpty()){name = "Player " + (i + 1);}                         //w przypadku nie nadania imienia - nadaje imię automatycznie
            players.add(new Player(name));
        }

        // pczytuje interface graficzny i klasy
        properties = new Properties();
        chance = new Chance();
        community = new Community();
        Game.gui = new GUI(players, properties);

        //rozpoczyna grę
        play();
    }

    public void play(){
        String command;

        //iteruje kolejne tury kolejnych graczy
        while(true) {
            for (int i = 0; i < players.size(); i++) {
                currentPlayer = players.get(i);         // Ustawia obecnego gracza
                resetTurn();                            // Resetuje ilość dubli, ustawia możliwość ruchu, blokuje kończenie tury
                playerStatus();                         // Drukuje obecny status gracza

                if (currentPlayer.inPrison() && currentPlayer.ownsJailCard()){
                    printText("You can use your get out of jail card. \n");
                    gui.setUseJailCardButton(true);
                } else {
                    gui.setUseJailCardButton(false);
                }

                gui.setCanBuyHousesButton(false);

                // Oczekiwanie na instrukcje od gracza i mechanizm kończenia tury
                while (!endTurn){
                    if (!canMove){
                        gui.setMoveButton(false);
                        gui.setEndTurnButton(true);
                    }
                }
            }
        }
    }

    /**
     *
     *  Metody opisujące przemieszczanie się po planszy
     *
     */

    public static void Throw(){
        gui.setCanBuyHousesButton(false);   // reset kupowania domków przy kolejnym rzucie

        //losowanie dwóch rzutów
        dice1 = 1 + new Random().nextInt(5);
        dice2 = 1 + new Random().nextInt(5);
        updateBoard();

        //test na dubel, jeżeli prawdziwy - zwiększenie ilości dubli + możliwość dalszego ruchu
        canMove = (dice1 == dice2);
        if(canMove){howManyDoubles++;}

        //jeżeli 3 duble pod rząd - wysyła do więzienia, kończy możliwość ruchu
        if(howManyDoubles == 3){
            gui.updateTextArea("You go to prison!\n");
            currentPlayer.setPosition(10);
            currentPlayer.goToPrison();
            canMove = false;
            updateBoard();

        } else {
            //przemieszcza gracza o rzut kośćmi
            currentPlayer.move(dice1 + dice2);
            position = currentPlayer.getPosition();
            printText("You moved to " + properties.getName(position) + ".");
            event();
        }

        updateBoard();
    }

    public static void prisonBreak() {
        // rzuć dubel by wyjść z więzienia
        dice1 = 1 + new Random().nextInt(5);
        dice2 = 1 + new Random().nextInt(5);
        updateBoard();

        if (dice1 == dice2) {
            //wychodzi z więzienia
            currentPlayer.leavePrison();
            canMove = true;

            // jeżeli graczowi nie powiodły się 3 rzuty - wychodzi z więzienia i płaci 50%
        } else {
            //spędza kolejną rundę w więzieniu
            currentPlayer.spendTimeInPrison();
            if (currentPlayer.getHowLongInPrison() != 3) {
                canMove = false;
            } else {
                printText("You've been in prison long enough. Pay 50$ fine and leave.");
                currentPlayer.spendCash(50);
                currentPlayer.leavePrison();
                canMove = true;
            }
        }
    }

    /**
     *
     *  Metody opisujące wybory gracza
     *
     */

    // Event po stanięciu na danym polu
    public static void event(){
        // zapłata czynszu
        rent();

        // karta community
        if (position == 2 || position == 17 || position == 33){
            printText(community.getCard(currentPlayer, players));
            if (currentPlayer.inPrison()){canMove = false;}
            position = currentPlayer.getPosition();
            updateBoard();
        }

        // karta szansy
        if (position == 7 || position == 22 || position == 36){
            printText(chance.getCard(currentPlayer, players));
            if (currentPlayer.inPrison()){canMove = false;}
            position = currentPlayer.getPosition();
            rent();
            updateBoard();
        }

        if (position == 4){currentPlayer.spendCash(200);}       // Income tax
        if (position == 38){currentPlayer.spendCash(100);}      // Luxury tax

        // pole idź do więzienia
        if (position == 30){
            currentPlayer.setPosition(10);
            position = currentPlayer.getPosition();
            currentPlayer.goToPrison();
            gui.updateTextArea("You go to prison!\n");
            canMove = false;
            updateBoard();
        }
    }

    public static void buy(){
        currentPlayer.addProperty(position);
        currentPlayer.spendCash(properties.getPrice(position));
        currentPlayer.addProperty(position);
        properties.buy(position, players.indexOf(currentPlayer));
        printText("You bought " + properties.getName(position) + ". (" + properties.getPrice(position) + "$)");
        updateBoard();

        // zaznacza jeżeli gracz posiada cały set
        if ((position == 1 || position == 3) &&
                currentPlayer.getProperties().contains(1) && currentPlayer.getProperties().contains(3)){
            properties.setSet0(true);
            printText("You now own whole set!");
        }
        if ((position == 6 || position == 8 || position == 9) &&
                currentPlayer.getProperties().contains(6) && currentPlayer.getProperties().contains(8) && currentPlayer.getProperties().contains(9)){
            properties.setSet1(true);
            printText("You now own whole set!");
        }
        if ((position == 11 || position == 13 || position == 14) &&
                currentPlayer.getProperties().contains(11) && currentPlayer.getProperties().contains(13) && currentPlayer.getProperties().contains(14)){
            properties.setSet2(true);
            printText("You now own whole set!");
        }
        if ((position == 16 || position == 18 || position == 19) &&
                currentPlayer.getProperties().contains(16) && currentPlayer.getProperties().contains(18) && currentPlayer.getProperties().contains(19)){
            properties.setSet3(true);
            printText("You now own whole set!");
        }
        if ((position == 21 || position == 23 || position == 24) &&
                currentPlayer.getProperties().contains(21) && currentPlayer.getProperties().contains(23) && currentPlayer.getProperties().contains(24)){
            properties.setSet4(true);
            printText("You now own whole set!");
        }
        if ((position == 26 || position == 28 || position == 29) &&
                currentPlayer.getProperties().contains(26) && currentPlayer.getProperties().contains(27) && currentPlayer.getProperties().contains(29)){
            properties.setSet5(true);
            printText("You now own whole set!");
        }
        if ((position == 31 || position == 33 || position == 34) &&
                currentPlayer.getProperties().contains(31) && currentPlayer.getProperties().contains(33) && currentPlayer.getProperties().contains(34)){
            properties.setSet6(true);
            printText("You now own whole set!");
        }
        if ((position == 37 || position == 39) &&
                currentPlayer.getProperties().contains(37) && currentPlayer.getProperties().contains(39)){
            properties.setSet7(true);
            printText("You now own whole set!");
        }
    }

    public static void buyHouse(){
        int houses = properties.howManyHouses(position);
        int price = properties.getHousePrice(position);

        if (houses < 3){
            properties.buyHouse(position);
            currentPlayer.spendCash(price);
            currentPlayer.buyHouse();
            printText("You bought " + (houses + 1) + ". house!");
        }

        if (houses == 3){
            properties.buyHouse(position);
            currentPlayer.spendCash(price);
            currentPlayer.buyHouse();
            printText("You bought " + (houses + 1) + ". house!");
            gui.setCanBuyHousesButton(false);
        }

        // zmienia informację na kupno motelu, wyłącza klawisz kupowania domków
        if (houses == 4){
            properties.buyHouse(position);
            currentPlayer.spendCash(price);
            currentPlayer.buyhotel();
            printText("You bought hotel!");
            gui.setCanBuyHousesButton(false);
        }

        updateBoard();
    }

    public static void useJailCard(){
        currentPlayer.useJailCard();
        printText("You used your leave jail card. You can throw normally.\n");
        gui.setUseJailCardButton(false);
    }

    public static void endTurn(){
        endTurn = true;
    }

    /**
     *
     *  Metody opisujące eventy
     *
     */

    public static void rent(){
        // zmiennie pomocnicze
        int amount;
        Player owner;

        // test na własność
        if (properties.getOwner(position) != 4 && properties.getOwner(position) != players.indexOf(currentPlayer)) {
            owner = players.get(properties.getOwner(position));
            // test czy w więzieniu
            if (owner.inPrison()) {
                printText("The owner is in prison. You don't have to pay!");

                // test czy zastawione
            } else if (properties.isMortage(position)) {
                printText("Position is mortage. You don't have to pay!");


                // czynsz za kolej
            }else if (position == 5 || position == 15 || position == 25 || position == 35){
                amount = properties.getRailroadRent(position, owner.getOwnedRailroads());
                currentPlayer.spendCash(amount);
                owner.addCash(amount);
                printText("You paid " + owner.getName() + " " + amount + "$.");
            // czynsz za utility
            } else if (position == 12 || position == 28){
                int multiplier = properties.getUtilityRent(position, owner.getOwnedUtilites());
                amount = multiplier * (dice1 + dice2);
                currentPlayer.spendCash(amount);
                owner.addCash(amount);
                printText("You paid " + owner.getName() + " " + amount + "$.");
            // zwyczajny czynsz
            } else {
                amount = properties.getRent(position);
                currentPlayer.spendCash(amount);
                owner.addCash(amount);
                printText("You paid " + owner.getName() + " " + amount + "$.");
            }
        }
    }

    /**
     *
     *  Metody aktualizujące ekran gry
     *
     */

    //Aktualizacja planszy po ruchu.
    public static void updateBoard(){
        gui.moveToken(currentPlayer.getPosition(), players.indexOf(currentPlayer));
        gui.changeDices(dice1, dice2);
        gui.updateTopInfo();
        canBuy();
        canBuyHouse();
    }

    public static void resetTurn(){
        howManyDoubles = 0;
        canMove = true;
        gui.setMoveButton(true);
        endTurn = false;
        gui.setBuyButton(false);
        gui.setEndTurnButton(false);
    }

    /**
     *
     *  Testy logiczne
     *
     */

    public static boolean throwWhenInPrison(){return currentPlayer.inPrison();}
    public static boolean canBuy(){
        if(properties.isBuyable(position) && currentPlayer.addCash() > properties.getPrice(position)){
            gui.setBuyButton(true);
            return true;
        }
        gui.setBuyButton(false);
        return false;
    }
    public static boolean canBuyHouse(){
        if ((players.indexOf(currentPlayer) != properties.getOwner(position)) ||              // sprawdza czy odpowiedni gracz,
                (!properties.isPositionInFullSet(position)) ||                                // sprawdza czy set pełny
                    (properties.howManyHouses(position) == 5) ||                              // jak w pełni zabudowane - nie
                        (currentPlayer.getCash() < properties.getHousePrice(position))){      // sprawdza czy stać
            gui.setCanBuyHousesButton(false);
            return false;
        }

        gui.setCanBuyHousesButton(true);
        return true;
    }

    public static Chance getChance(){return chance;}
    public static Community getCommunity(){return community;}
    public static Properties getProperties(){return properties;}

    /**
     *
     *  Dialogi w oknie tekstowym
     *
     */

    public static void playerStatus(){
        // Informuje czyja tura
        gui.updateTextArea("\n" + currentPlayer.getName() + " turn.\n");

        // Jeżeli gracza że jest w więzieniu
        if (currentPlayer.inPrison()){
            gui.updateTextArea("Throw doubles to leave prison.\n");
        }
    }

    public static void printText(String text){
        gui.updateTextArea(text + "\n");
    }
}
