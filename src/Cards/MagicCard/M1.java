package Cards.MagicCard;

import Cards.Card;
import Cards.Effect.Damage;
import Cards.Magic;
import support.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class M1 extends Magic {

    private Damage dm = new Damage();

    public M1(String name, String imgURL, String description) {
        super(name, imgURL, description);
        dm.setDamageAmount(1000);
    }


    public void activate(Player player, Player enemy) {
        System.out.println("메테오 스톰 발동.");

        java.util.Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        t.cancel();
                        //여기서 효과 발동
                        dm.damage(player, enemy);

                        removeCard(player, enemy);
                    }
                },
                500
        );
            //발동 1초뒤,효과를 쓰고, 묘지로보냅니다.


    }


}
