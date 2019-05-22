// - pomimo ograniczonej ilości domków - przyjąłem brak limitu na nie

/**
 *
 *  Klasa zawierająca bazę danych o kartach i pozwalająca na modyfikacje
 *
 */

public class Properties {
    // Kolumna 0 - czy pozycja jest posiadalna
    // Kolumna 1 - czy da się ją obecnie kupić
    // Kolumna 2 - czy jest obecnie zastawiona
    // Kolumna 3 - ile domków
    // Kolumna 4 - kto jest właścicielem (4 - nikt)
    // Kolumna 5 - cena kupna - sprzedania  - zastawienia (/2)
    // Kolumna 6 - cena kupna - sprzedaży domków - 0 dla tych co nie mogą

    private static int properties[][] = {
           //0  1  2  3  4    5    6
            {0, 0, 0, 0, 4,   0,   0},  // 0    "Go"
            {1, 1, 0, 0, 4,  60,  50},  // 1    "Mediterranean Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 2    "Community Chest"
            {1, 1, 0, 0, 4,  60,  50},  // 3    "Baltic Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 4    "Income Tax"
            {1, 1, 0, 0, 4, 200,   0},  // 5    "Reading Railroad"
            {1, 1, 0, 0, 4, 100,  50},  // 6    "Oriental Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 7    "Chance"
            {1, 1, 0, 0, 4, 100,  50},  // 8    "Vermont Avenue"
            {1, 1, 0, 0, 4, 120,  50},  // 9    "Connecticut Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 10   "Jail"
            {1, 1, 0, 0, 4, 140, 100},  // 11   "St. Charles Place"
            {1, 1, 0, 0, 4, 150,   0},  // 12   "Electric Company"
            {1, 1, 0, 0, 4, 140, 100},  // 13   "States Avenue"
            {1, 1, 0, 0, 4, 160, 100},  // 14   "Virginia Avenue"
            {1, 1, 0, 0, 4, 200,   0},  // 15   "Pennsylvania Railroad"
            {1, 1, 0, 0, 4, 180, 100},  // 16   "St. James Place"
            {0, 0, 0, 0, 4,   0,   0},  // 17   "Community Chest"
            {1, 1, 0, 0, 4, 180, 100},  // 18   "Tennessee Avenue"
            {1, 1, 0, 0, 4, 200, 100},  // 19   "New York Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 20   "Free Parking"
            {1, 1, 0, 0, 4, 220, 150},  // 21   "Kentucky Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 22   "Chance"
            {1, 1, 0, 0, 4, 220, 150},  // 23   "Indiana Avenue"
            {1, 1, 0, 0, 4, 240, 150},  // 24   "Illinois Avenue"
            {1, 1, 0, 0, 4, 200,   0},  // 25   "B & O RailRoad"
            {1, 1, 0, 0, 4, 260, 150},  // 26   "Atlantic Avenue"
            {1, 1, 0, 0, 4, 260, 150},  // 27   "Ventnor Avenue"
            {1, 1, 0, 0, 4, 150,   0},  // 28   "Water Works"
            {1, 1, 0, 0, 4, 280, 150},  // 29   "Marvin Gardens"
            {0, 0, 0, 0, 4,   0,   0},  // 30   "Go to Jail"
            {1, 1, 0, 0, 4, 300, 200},  // 31   "Pacific Avenue"
            {1, 1, 0, 0, 4, 300, 200},  // 32   "North Carolina Avenue"
            {0, 0, 0, 0, 4,   0,   0},  // 33   "Community Chest"
            {1, 1, 0, 0, 4, 320, 200},  // 34   "Pennsylvania Avenue"
            {1, 1, 0, 0, 4, 200,   0},  // 35   "Short Line Railroad"
            {0, 0, 0, 0, 4,   0,   0},  // 36   "Chance"
            {1, 1, 0, 0, 4, 350, 200},  // 37   "Park Place"
            {0, 0, 0, 0, 4,   0,   0},  // 38   "Luxury Tax"
            {1, 1, 0, 0, 4, 400, 200}   // 39   "Boardwalk"
    };

    private static int[][] rents = {
            // jeżeli posiadany cały set - wartość 0*2
         //   0    1    2     3     4    hotel
            { 0,   0,   0,    0,    0,    0},  // 0    "Go"
            { 2,  10,  30,   90,  160,  250},  // 1    "Mediterranean Avenue"
            { 0,   0,   0,    0,    0,    0},  // 2    "Community Chest"
            { 4,  20,  60,  180,  320,  450},  // 3    "Baltic Avenue"
            { 0,   0,   0,    0,    0,    0},  // 4    "Income Tax"
            { 0,  25,  50,  100,  200,    0},  // 5    "Reading Railroad"   - zależy od ilości posiadanych kolei
            { 6,  30,  90,  270,  400,  550},  // 6    "Oriental Avenue"
            { 0,   0,   0,    0,    0,    0},  // 7    "Chance"
            { 6,  30,  90,  270,  400,  550},  // 8    "Vermont Avenue"
            { 8,  40, 100,  300,  450,  600},  // 9    "Connecticut Avenue"
            { 0,   0,   0,    0,    0,    0},  // 10   "Jail"
            {10,  50, 150,  450,  625,  750},  // 11   "St. Charles Place"
            { 0,   4,  10,    0,    0,    0},  // 12   "Electric Company"  - mnożnik kostki
            {10,  50, 150,  450,  625,  750},  // 13   "States Avenue"
            {12,  60, 180,  500,  700,  900},  // 14   "Virginia Avenue"
            {0,   25,  50,  100,  200,    0},  // 15   "Pennsylvania Railroad"  - zależy od ilości posiadanych
            {14,  70, 200,  550,  750,  950},  // 16   "St. James Place"
            { 0,   0,   0,    0,    0,    0},  // 17   "Community Chest"
            {14,  70, 200,  550,  750,  950},  // 18   "Tennessee Avenue"
            {16,  80, 220,  600,  800, 1000},  // 19   "New York Avenue"
            { 0,   0,   0,    0,    0,    0},  // 20   "Free Parking"
            {18,  90, 250,  700,  875, 1050},  // 21   "Kentucky Avenue"
            { 0,   0,   0,    0,    0,    0},  // 22   "Chance"
            {18,  90, 250,  700,  875, 1050},  // 23   "Indiana Avenue"
            {20, 100, 300,  750,  925, 1100},  // 24   "Illinois Avenue"
            {0,   25,  50,  100,  200,    0},  // 25   "B & O RailRoad"   - zależy od ilości posiadanych
            {22, 110, 330,  800,  975, 1150},  // 26   "Atlantic Avenue"
            {22, 110, 330,  800,  975, 1150},  // 27   "Ventnor Avenue"
            { 0,   4,  10,    0,    0,    0},  // 28   "Water Works"      - mnożnik kostki
            {24, 120, 360,  850, 1025, 1200},  // 29   "Marvin Gardens"
            { 0,   0,   0,    0,    0,    0},  // 30   "Go to Jail"
            {26, 130, 390,  900, 1100, 1275},  // 31   "Pacific Avenue"
            {26, 130, 390,  900, 1100, 1275},  // 32   "North Carolina Avenue"
            { 0,   0,   0,    0,    0,    0},  // 33   "Community Chest"
            {28, 150, 450, 1000, 1200, 1400},  // 34   "Pennsylvania Avenue"
            {0,   25,  50,  100,  200,    0},  // 35   "Short Line Railroad"  - zależy od ilości posiadanych
            { 0,   0,   0,    0,    0,    0},  // 36   "Chance"
            {35, 175, 500, 1100, 1300, 1500},  // 37   "Park Place"
            { 0,   0,   0,    0,    0,    0},  // 38   "Luxury Tax"
            {50, 200, 600, 1400, 1700, 2000}   // 39   "Boardwalk"
    };

    private static String[] names = {
            "Go",
            "Mediterranean Avenue",
            "Community Chest",
            "Baltic Avenue",
            "Income Tax",
            "Reading Railroad",
            "Oriental Avenue",
            "Chance",
            "Vermont Avenue",
            "Connecticut Avenue",
            "Jail",
            "St. Charles Place",
            "Electric Company",
            "States Avenue",
            "Virginia Avenue",
            "Pennsylvania Railroad",
            "St. James Place",
            "Community Chest",
            "Tennessee Avenue",
            "New York Avenue",
            "Free Parking",
            "Kentucky Avenue",
            "Chance",
            "Indiana Avenue",
            "Illinois Avenue",
            "B & O RailRoad",
            "Atlantic Avenue",
            "Ventnor Avenue",
            "Water Works",
            "Marvin Gardens",
            "Go to Jail",
            "Pacific Avenue",
            "North Carolina Avenue",
            "Community Chest",
            "Pennsylvania Avenue",
            "Short Line Railroad",
            "Chance",
            "Park Place",
            "Luxury Tax",
            "Boardwalk"
    };

    private static int sets[][] = {
            //
            { 1,  4},           //0
            { 6,  8,  9},       //1
            {11, 13, 14},       //2
            {16, 18, 19},       //3
            {21, 23, 24},       //4
            {26, 28, 29},       //5
            {31, 33, 34},       //6
            {37, 39},           //7
    };

    private static boolean set0 = false;
    private static boolean set1 = false;
    private static boolean set2 = false;
    private static boolean set3 = false;
    private static boolean set4 = false;
    private static boolean set5 = false;
    private static boolean set6 = false;
    private static boolean set7 = false;

    /**
     *
     *  Metody modyfikujące tabele
     *
     */

    // kupno domków
    public void buyHouse(int position){
        properties[position][3] ++;
    }

    // sprzedaż domków, zwrot pieniędzy
    public int sellHouse(int position, int howMany){
        properties[position][3] -= howMany;
        return howMany * properties[position][6];
    }

    // wykupienie posesji
    public void buy(int position, int playerIndex){
        properties[position][1] = 0;
        properties[position][4] = playerIndex;
    }

    // zastawienie, zwraca ilość pieniędzy
    public int mortage(int position){
        properties[position][2] = 1;
        return properties[position][5];
    }

    // odzastawienie
    public void unMortage(int position){properties[position][2] = 0;}

    // sprzedaż posesji, zwraca ilość pieniędzy
    public int sell(int position){
        properties[position][1] = 1;
        properties[position][4] = 0;

        if (properties[position][2] == 1){
            return properties[position][5]/2;
        }
        return properties[position][5];
    }

    /**
     *
     *  Ustawienie czy gracz zebrał cały zestaw
     *
     */

    public void setSet0(boolean value) { set0 = value;}
    public void setSet1(boolean value) { set1 = value;}
    public void setSet2(boolean value) { set2 = value;}
    public void setSet3(boolean value) { set3 = value;}
    public void setSet4(boolean value) { set4 = value;}
    public void setSet5(boolean value) { set5 = value;}
    public void setSet6(boolean value) { set6 = value;}
    public void setSet7(boolean value) { set7 = value;}

    public boolean isSet0() { return set0;}
    public boolean isSet1() { return set1;}
    public boolean isSet2() { return set2;}
    public boolean isSet3() { return set3;}
    public boolean isSet4() { return set4;}
    public boolean isSet5() { return set5;}
    public boolean isSet6() { return set6;}
    public boolean isSet7() { return set7;}

    public boolean isPositionInFullSet(int position){
        if ((position == 1 || position == 3) && isSet0()){ return true;}
        if ((position == 6 || position == 8 || position == 9) && isSet1()){ return true;}
        if ((position == 11 || position == 13 || position == 14) && isSet2()){ return true;}
        if ((position == 16 || position == 18 || position == 19) && isSet3()){ return true;}
        if ((position == 21 || position == 23 || position == 24) && isSet4()){ return true;}
        if ((position == 26 || position == 27 || position == 29) && isSet5()){ return true;}
        if ((position == 31 || position == 33 || position == 34) && isSet6()){ return true;}
        if ((position == 37 || position == 39) && isSet7()){ return true;}
        return false;
    }

    /**
     *
     *  Metody zwracające info o posesji
     *
     */

    public String getName(int position){return  names[position];}
    public boolean isOwnable (int position){return properties[position][0] == 1;}
    public boolean isBuyable (int position){return properties[position][1] == 1;}
    public boolean isMortage(int position){return properties[position][2] == 1;}
    public boolean ownsHouses(int position){return properties[position][3] != 0;}
    public int howManyHouses (int position){return properties[position][3];}
    public int getOwner (int position){return properties[position][4];}
    public int getPrice (int position){return properties[position][5];}
    public int getHousePrice (int position){return properties[position][6];}
    public int[][] getSets(){return sets;}

    // metody zwracające kwotę czynszu
    public int getRent (int position){
        // Jeżeli pełny set i 0 domków - podwójny czynsz
        if (isPositionInFullSet(position) && howManyHouses(position) == 0){
            return 2 * rents[position][howManyHouses(position)];
        }
        return rents[position][howManyHouses(position)];
    }

    public int getRailroadRent(int position, int howManyRailroads){return rents[position][howManyRailroads];}
    public int getUtilityRent(int position, int howManyUtilities){return rents[position][howManyUtilities];}
}
