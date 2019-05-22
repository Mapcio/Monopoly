import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Community {
    private List<Integer> cards;
    private List<Integer> used;
    private boolean isJailcardHeld;

    private static String[] names = {
            "Get out of Jail Free. This card may be kept until needed, or traded/sold.",    // 0
            "Advance to Go (Collect $200)",                                                 // 1
            "Bank error in your favor—Collect $200",                                       // 2
            "Doctor's fee—Pay $50",                                                        // 3
            "From sale of stock you get $50",                                               // 4
            "Grand Opera Night—Collect $50 from every player for opening night seats",     // 5
            "Holiday Fund matures—Receive $100",                                           // 6
            "Income tax refund–Collect $20",                                                // 7
            "It is your birthday—Collect $10",                                             // 8
            "Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200.",        // 9
            "Life insurance matures–Collect $100",                                          // 10
            "Pay hospital fees of $100",                                                    // 11
            "Pay school fees of $150",                                                      // 12
            "Receive $25 consultancy fee",                                                  // 13
            "You are assessed for street repairs–$40 per house–$115 per hotel",             // 14
            "You have won second prize in a beauty contest–Collect $10",                    // 15
            "You inherit $100",                                                             // 16
    };

    public Community(){
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
        if (card == 0){community0(player);}
        if (card == 1){community1(player);}
        if (card == 2){community2(player);}
        if (card == 3){community3(player);}
        if (card == 4){community4(player);}
        if (card == 5){community5(player, players);}
        if (card == 6){community6(player);}
        if (card == 7){community7(player);}
        if (card == 8){community8(player);}
        if (card == 9){community9(player);}
        if (card == 10){community10(player);}
        if (card == 11){community11(player);}
        if (card == 12){community12(player);}
        if (card == 13){community13(player);}
        if (card == 14){community14(player);}
        if (card == 15){community15(player);}
        if (card == 16){community16(player);}

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

    // "Get out of Jail Free. This card may be kept until needed, or traded/sold.",
    void community0(Player player){player.addJailCommunity();}

    // "Advance to Go (Collect $200)",
    void community1(Player player){
        while (player.getPosition() != 0){
            player.move(1);
        }
    }

    // "Bank error in your favor—Collect $200",
    void community2(Player player){player.addCash(200);}

    // "Doctor's fee—Pay $50",
    void community3(Player player){player.spendCash(50);}

    // "From sale of stock you get $50",
    void community4(Player player){player.addCash(50);}

    // "Grand Opera Night—Collect $50 from every player for opening night seats",
    void community5(Player player, ArrayList<Player> players){
        for (Player otherPlayer : players){
            if (otherPlayer != player){
                player.addCash(50);
                otherPlayer.spendCash(50);
            }
        }
    }

    // "Holiday Fund matures—Receive $100",
    void community6(Player player){player.addCash(100);}

    // "Income tax refund–Collect $20",
    void community7(Player player){player.addCash(20);}

    // "It is your birthday—Collect $10",
    void community8(Player player){player.addCash(10);}

    // "Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200.",
    void community9(Player player){
        player.setPosition(10);
        player.goToPrison();
    }

    // "Life insurance matures–Collect $100",
    void community10(Player player){player.addCash(100);}

    // "Pay hospital fees of $100",
    void community11(Player player){player.spendCash(100);}

    // "Pay school fees of $150",
    void community12(Player player){player.spendCash(150);}

    // "Receive $25 consultancy fee",
    void community13(Player player){player.spendCash(25);}

    // "You are assessed for street repairs–$40 per house–$115 per hotel",
    void community14(Player player){
        int amount = 40 * player.getHouses() + 115 * player.getHotels();
    }

    // "You have won second prize in a beauty contest–Collect $10",
    void community15(Player player){player.addCash(10);}

    // "You inherit $100",
    void community16(Player player){player.addCash(100);}

}
