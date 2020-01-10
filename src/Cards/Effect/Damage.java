package Cards.Effect;

import support.Player;

public class Damage implements Damagable {

    private int damageAmount;

    public void damage(Player player, Player enemy) {
        enemy.setLp(enemy.getLp() - damageAmount);
        player.getDfp().updateEnemyLp();
        enemy.getDfp().updatePlayerLp();

      //  System.out.println("적의 생존점수:" + enemy.getLp());
    }

    public void setDamageAmount(int d) {
        damageAmount = d;
    }

    public int getDamageAmount() {
        return damageAmount;
    }
}
