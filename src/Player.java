import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private String name;                        // imię
    private int position;                       // aktualna pozycja gracza
    private int cash;                           // stan konta gracza
    private int houses;                         // ilość domków
    private int hotels;                         // ilość hoteli
    private int howLongInPrison;                // ilość odbytego więzienia
    private boolean inPrison;                   // czy w więzieniu
    private boolean GetOutOfJailChance;         // czy ma kartę szansy wyjścia z więzienia
    private boolean GetOutOfJailCommunity;      // czy ma kartę szansy wyjścia z więzienia
    private List<Integer> properties;           // lista posiadanych pozycji


    public Player(String name) {
        this.name = name;
        this.position = 0;
        this.cash = 1500;
        this.houses = 0;
        this.hotels = 0;
        this.inPrison = false;
        this.GetOutOfJailChance = false;
        this.GetOutOfJailCommunity = false;
        this.howLongInPrison = 0;
        this.properties = new ArrayList<Integer>();
    }

    public void addProperty(int property){
        properties.add(property);
        Collections.sort(properties);
    }

    public void spendCash(int amount){
        cash -= amount;
        // TO DO   uzupełnić o opcję sprzedaży rzeczy gdy brakuje!
    }
    public void addCash(int amount){
        cash += amount;
    }

    // przemieszcza
    public void move(int Throw){
        //test czy przechodzi przez start
        if (this.position + Throw >= 40){
            this.cash += 200;
            Game.printText("You collected 200$ salary as you pass go.");
        }
        this.position = (this.position + Throw)%40;
    }

    // reset więzienia po wyjściu / użyciu karty
    public void leavePrison(){
        this.inPrison = false;
        this.howLongInPrison = 0;
    }

    // użycie karty wyjścia z więzienia i zwraca do stosu zużytych. Jeżeli posiada dwie - najpierw szansa, później community
    public void useJailCard(){
        if (this.GetOutOfJailChance){
            this.GetOutOfJailChance = false;
            Game.getChance().returnJailCard();
            leavePrison();
        } else if (this.GetOutOfJailCommunity){
            this.GetOutOfJailCommunity = false;
            Game.getCommunity().returnJailCard();
            leavePrison();
        }
    }

    public void buyHouse(){houses ++;}
    public void sellHouse(int amount){houses -= amount;}
    public void buyhotel(){
        houses -= 4;
        hotels ++;
    }
    public void sellhotel(){
        houses += 4;
        hotels --;
    }

    public void setPosition(int position) { this.position = position;}     // ustawienie nowej pozycji
    public void goToPrison() { this.inPrison = true;}                      // ustawienie statusu więzienia
    public void spendTimeInPrison() { this.howLongInPrison++;}            // zwiększa spędzony czas w więzieniu
    public void addJailChance() { this.GetOutOfJailChance = true;}         // dodanie karty wyjścia z więzienia - szansy
    public void addJailCommunity() { this.GetOutOfJailChance = true;}      // dodanie karty wyjścia z więzienia - community

    public String getName() { return this.name; }                           // zwraca imię
    public int getCash() { return this.cash;}
    public int getPosition() { return this.position;}                       // zwraca pozycję
    public int addCash() { return this.cash;}                               // zwraca stan konta
    public int getHouses() { return this.houses;}                           // zwraca ilość domków
    public int getHotels() { return this.hotels;}                           // zwraca ilość hoteli
    public int getHowLongInPrison() { return this.howLongInPrison;}        // zwraca spędzony czas
    public boolean inPrison() { return inPrison;}                          // zwraca czy w więzieniu
    public boolean ownsJailCard() { return GetOutOfJailChance || GetOutOfJailCommunity;}     // zwraca czy ma kartę wyjścia z więzienia
    public List<Integer> getProperties() { return this.properties;}         // zwraca listę posiadanych miejsc

    public int getOwnedRailroads() {
        int number = 0;
        if (properties.contains(5)){ number++;}
        if (properties.contains(15)){ number++;}
        if (properties.contains(25)){ number++;}
        if (properties.contains(35)){ number++;}
        return number;
    }
    public int getOwnedUtilites() {
        int number = 0;
        if (properties.contains(12)){ number++;}
        if (properties.contains(28)){ number++;}
        return number;
    }

}

