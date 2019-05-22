import gui.Board;
import gui.Dice;
import gui.PlayerToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class GUI extends JFrame {
    private Board boardPanel;
    private JLayeredPane lp;
    private JSplitPane split;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JScrollPane eastPanel;
    private JLabel topInfo;
    private JButton moveButton;
    private JButton buyButton;
    private JButton buyHouseButton;
    private JButton endTurnButton;
    private JButton useJailCardButton;
    private JTextArea textArea;
    private ArrayList<Player> players;
    private ArrayList<PlayerToken> tokens;
    private OwnerToken ownerToken;
    private Dice dices;
    private Properties properties;



    public GUI(ArrayList<Player> Players, Properties props){
        players = Players;
        properties = props;
        //tworzy interface graficzny
        initiateGI();
        //wprowadza zmienne elementy grafiki
        initiateIcons();

        //Wprowadza rozmiar okna, nazwę i opcję wyjścia przez kliknięcie X
        setSize(1400,1000);
        setTitle("Monopoly");
        add(topPanel, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void initiateGI() {
        //tworzy górny panel
        topPanel = new JPanel();
        // tworzy zawartość górnego panelu
        topInfo = new JLabel("");
        updateTopInfo();
        topPanel.add(topInfo);

        //tworzy planszę
        Dimension d = new Dimension(880,880);
        boardPanel = new Board();
        boardPanel.setMinimumSize(d);

        // tworzy okienko tekstowe
        Dimension e = new Dimension(320,880);
        textArea = new JTextArea("Welcome \n");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        eastPanel = new JScrollPane(textArea);
        eastPanel.setMinimumSize(e);

        // dzieli centralną zonę między planszę i tekst
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, eastPanel);

        //tworzy dolny panel
        bottomPanel = new JPanel();
        moveButton = new JButton("Throw");
        buyButton = new JButton("Buy");
        endTurnButton = new JButton("End turn");
        useJailCardButton = new JButton("Use Jail Free card");
        buyHouseButton = new JButton("Buy house");
        bottomPanel.add(moveButton);
        bottomPanel.add(buyButton);
        bottomPanel.add(endTurnButton);
        bottomPanel.add(useJailCardButton);
        bottomPanel.add(buyHouseButton);

        buyButton.setEnabled(false);
        useJailCardButton.setVisible(false);
        buyHouseButton.setVisible(false);

        //dodaje funkcje do przycisków
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Game.throwWhenInPrison()){
                    Game.prisonBreak();
                } else {
                    Game.Throw();
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.buy();
                repaint();
            }
        });

        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.endTurn();
            }});

        useJailCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.useJailCard();
            }});

        buyHouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.buyHouse();
                repaint();
            }
        });


    }
    public void initiateIcons(){
        // tworzy panel na którym umieszczane będą elementy zmienne
        lp = getLayeredPane();

        // tworzy pionki dla każdego graczy, dodaje je na warstwę lp, ustala ich położenie
        tokens = new ArrayList<PlayerToken>();
        for (int i = 0; i < players.size(); i++){
            tokens.add(new PlayerToken(i));
            lp.add(new JLabel(tokens.get(i)));
            lp.getComponent(i).setBounds(0,25,880,880);
        }

        // umiejscawia na planszy własność
        ownerToken = new OwnerToken(properties);
        JLabel ownership = new JLabel(ownerToken);
        lp.add(ownership);
        ownership.setBounds(0,25,880,880);

        // umiejscawia na planszy kości
        dices = new Dice();
        JLabel showDices = new JLabel(dices);
        lp.add(showDices);
        showDices.setBounds(0,25, 880, 880);
    }

    public void moveToken(int position, int playerIndex){
        tokens.get(playerIndex).moveToken(position);
        repaint();
    }

    public void changeDices(int throw1, int throw2){
        dices.setDice(throw1, throw2);
        repaint();
    }

    public void updateTopInfo(){
        StringBuilder build = new StringBuilder();
        String[] colors = {"(blue)", "(red)", "(green)", "(black)"} ;
        for (int i = 0; i < players.size(); i++){
            build.append(players.get(i).getName() + " " + colors[i] + ": " + players.get(i).addCash() + "$            ");
        }
        topInfo.setText(build.toString());
    }

    public void updateTextArea(String string){
        textArea.append(string);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void setMoveButton(boolean value){
        moveButton.setEnabled(value);
    }
    public void setEndTurnButton(boolean value){
        endTurnButton.setEnabled(value);
    }
    public void setBuyButton(boolean value){
        buyButton.setEnabled(value);
    }
    public void setUseJailCardButton(boolean value){useJailCardButton.setVisible(value);}
    public void setCanBuyHousesButton(boolean value){buyHouseButton.setVisible(value);}
}
