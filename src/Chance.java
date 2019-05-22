import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chance {
    private List<Integer> cards;
    private List<Integer> used;
    private boolean isJailcardHeld;

    private static String[] names = {
            "Get out of Jail Free. This card may be kept until needed, or traded/sold.",                                                                                            // 0
            "Advance to Go (Collect $200)",                                                                                                                                         // 1
            "Advance to Illinois Ave. {Avenue}. If you pass Go, collect $200.",                                                                                                     // 2
            "Advance to St. Charles Place. If you pass Go, collect $200.",                                                                                                          // 3
            "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 times the amount thrown.",                   // 4
            "Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.",    // 5
            "Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.",    // 6
            "Bank pays you dividend of $50.",                                                                                                                                       // 7
            "Go Back Three {3} Spaces.",                                                                                                                                            // 8
            "Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200.",                                                                                                // 9
            "Make general repairs on all your property: For each house pay $25, For each hotel $100.",                                                                              // 10
            "Pay poor tax of $15",                                                                                                                                                  // 11
            "Take a trip to Reading Railroad. If you pass Go, collect $200.",                                                                                                       // 12
            "Take a walk on the Boardwalk. Advance token to Boardwalk.",                                                                                                            // 13
            "You have been elected Chairman of the Board. Pay each player $50.",                                                                                                    // 14
            "Your building loan matures. Receive {Collect} $150.",                                                                                                                  // 15
            "You have won a crossword competition. Collect $100."                                                                                                                   // 16
    };

    public Chance(){
        cards = new ArrayList<>();
        used = new ArrayList<>();
        isJailcardHeld = false;

        for (int i = 0; i < 17; i++) {
            cards.add(i);
        }
        Collections.shuffle(cards);
    }

    // losuje kartę szansy, tasuje jeżeli wszystkie zużyte. Następnie aktywuje akcję danej karty
    public String getCard(Player player, ArrayList<Player> players){
        int card = cards.get(0);
        cards.remove(0);

        if (card == 0){                 // jeżeli wylosowano wyjście z więzienia - uwzględnia to
            isJailcardHeld = true;
        } else {
            used.add(card);
        }
        shuffle();

        // aktywuje kartę
        if (card == 0){action0(player);}
        if (card == 1){action1(player);}
        if (card == 2){action2(player);}
        if (card == 3){action3(player);}
        if (card == 4){action4(player);}
        if (card == 5){action5(player);}
        if (card == 6){action6(player);}
        if (card == 7){action7(player);}
        if (card == 8){action8(player);}
        if (card == 9){action9(player);}
        if (card == 10){action10(player);}
        if (card == 11){action11(player);}
        if (card == 12){action12(player);}
        if (card == 13){action13(player);}
        if (card == 14){action14(player, players);}
        if (card == 15){action15(player);}
        if (card == 16){action16(player);}

        // zwraca tekst karty
        return names[card];
    }

    // tasuje karty szansy jeżeli wszystkie zostały zużyte
    public void shuffle(){
        if (cards.isEmpty()){
            cards.addAll(used);
            Collections.shuffle(cards);
            used.clear();
        }
    }

    // dodaje zwróconą kartę wyjścia z więzienia do zużytych
    public void returnJailCard(){
        isJailcardHeld = false;
        used.add(0);
    }

    /**
     *
     *  Metody aktywuące każdą kartę
     *
     */

    // jail free
    void action0(Player player){player.addJailChance();}

    // Advance to start
    void action1(Player player){
        while (player.getPosition() != 0){
        }
    }

    // Advance to Illinois Ave.
    void action2(Player player){
        while (player.getPosition() != 24){
            player.move(1);
        }
    }

    // Advance to St. Charles Place.
    void action3(Player player){
        while (player.getPosition() != 11){
            player.move(1);
        }
    }

    // Advance token to nearest Utility.
    void action4(Player player){
        while (player.getPosition() != 12 || player.getPosition() != 28 ){
            player.move(1);
        }
    }

    // Advance token to the nearest Railroad
    void action5(Player player){
        while (player.getPosition() != 5 || player.getPosition() != 15 || player.getPosition() != 25 || player.getPosition() != 35 ){
            player.move(1);
        }
    }

    // Advance token to the nearest Railroad
    void action6(Player player){
        while (player.getPosition() != 5 || player.getPosition() != 15 || player.getPosition() != 25 || player.getPosition() != 35 ){
            player.move(1);
        }
    }

    // "Bank pays you dividend of $50."
    void action7(Player player){player.spendCash(50);}

    // "Go Back Three {3} Spaces."
    void action8(Player player){player.move(-3);}

    // Go to Jail. Go directly to Jail. Do not pass GO
    void action9(Player player){
        player.setPosition(10);
        player.goToPrison();
    }

    // "Make general repairs on all your property: For each house pay $25, For each hotel $100.",
    void action10(Player player){
        int amount = 25 * player.getHouses() + 100 * player.getHotels();
        player.spendCash(amount);
    }

    //  "Pay poor tax of $15",
    void action11(Player player){player.spendCash(15);}

    // "Take a trip to Reading Railroad. If you pass Go, collect $200.",
    void action12(Player player){
        while (player.getPosition() != 5){
            player.move(1);
        }
    }

    // "Take a walk on the Boardwalk. Advance token to Boardwalk.",
    void action13(Player player){
        while (player.getPosition() != 39){
            player.move(1);
        }
    }

    // "You have been elected Chairman of the Board. Pay each player $50.",
    void action14(Player player, ArrayList<Player> players){
        for (Player otherPlayer : players){
            if (otherPlayer != player){
                player.spendCash(50);
                otherPlayer.addCash(50);
            }
        }
    }

    // "Your building loan matures. Receive {Collect} $150.",
    void action15(Player player){player.addCash(150);}

    // "You have won a crossword competition. Collect $100."
    void action16(Player player){player.addCash(100);}
}