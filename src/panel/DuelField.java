package panel;

import main.Game;

import javax.swing.*;
import java.awt.*;

public class DuelField extends JPanel {

    private PlayZonePanel center;
    private JPanel west;
    public DuelField(Game game) {

        //  gameField = new JPanel();
        // gameField.setPreferredSize(new Dimension(1280,720));
        setLayout(new BorderLayout());

        HandPanel hp = new HandPanel(game);

        add(hp, BorderLayout.SOUTH);

         west = new JPanel();

        west.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        west.add(game.getPlayer().getDeck()[0].getCardImage());
        add(west, BorderLayout.WEST);


        center = new PlayZonePanel(game);
        center.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        add(center, BorderLayout.CENTER);

        PhasePanel east = new PhasePanel(game);
        east.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(east, BorderLayout.EAST);


        JPanel north = new JPanel();

        JLabel lp = new JLabel("생존점수:" + game.getPlayer().getLp());
        north.add(lp);
        add(north, BorderLayout.NORTH);

        // add(gameField);
    }

    public PlayZonePanel getCenter(){
        return center;
    }

    public void addAtWest(JPanel cardImage){
      //  JPanel useMe = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.WEST);
        //JPanel useMe =  (JPanel)(((BorderLayout)DuelField.getGameField().getLayout()).getLayoutComponent(BorderLayout.WEST));

        //borderLayout을 쓰는 Panel의 west size acess하기...
//                    JPanel useMe = (JPanel)(((BorderLayout)parent.getLayout()).getLayoutComponent(BorderLayout.WEST));
        west.removeAll();
        west.invalidate();
        west.repaint();
        west.add(cardImage);
        west.validate();
    }


}