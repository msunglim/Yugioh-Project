package Cards.MagicCard;

import Cards.Effect.Damage;
import Cards.Effect.Destroy;
import Cards.Magic;
import support.Player;

public class M2 extends Magic {

    private Destroy destroy = new Destroy();
    public M2(String name, String imgURL, String description) {
        super(name, imgURL, description);

    }

    public void activate(Player player, Player enemy) {
        destroy.setCardListLength(5);
        destroy.setEnemyCardListLength(5);
        destroy.setDestroyList(player.getDfp().getCenter().getMyFieldMonsterZone());
        destroy.setDestroyEnemyList(enemy.getDfp().getCenter().getMyFieldMonsterZone());

        System.out.println("블랙홀 발동.");
        java.util.Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        t.cancel();

                        destroy.destroy(player, enemy);
                        removeCard(player, enemy);
                    }
                },
                500
        );
    }

}
