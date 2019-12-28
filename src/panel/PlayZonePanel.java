package panel;

import main.Game;

import javax.swing.*;
import java.awt.*;

public class PlayZonePanel extends JPanel {
    private Game game;

    public PlayZonePanel(Game game) {
        this.game = game;
        setLayout(new GridLayout(2, 7));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {

                JPanel zone = new JPanel();
           //     zone.setLayout(null);
                if (i == 0) {
                    if(j==0) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                        zone.add(new JLabel("융합"));
                    }
                    else if (j == 6) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        zone.add(new JLabel("덱"+ game.getPlayer().getDeckStack().size()));
                        //남은 덱카드수만큼 add.

                            JLabel deckSurface = new JLabel(new ImageIcon("data/images/cards/back.PNG"));

                            zone.add(deckSurface);

                    } else {
                        zone.setBorder(BorderFactory.createLineBorder(Color.blue));
                        zone.add(new JLabel("마/함"));
                    }
                } else {

                    if (i == 1 && j == 0) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.green));
                        zone.add(new JLabel("필드"));
                    } else if (i == 1 && j == 6) {
                        zone.setBorder(BorderFactory.createLineBorder(Color.darkGray));
                        zone.add(new JLabel("묘지"));

                    } else {
                        zone.setBorder(BorderFactory.createLineBorder(Color.RED));
                    zone.add(new JLabel("몬스터"));
                    }
                }
                add(zone, i, j);
            }
        }


    }
}
