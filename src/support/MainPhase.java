package support;

import main.Game;

public class MainPhase extends Phase{

    public MainPhase(Game game, Player player,Player enemy) {

        this.game = game;
        this.player = player;
        this.enemy = enemy;

        if (mainPhase2) {
            System.out.println("메인페이즈2 입니다");
            setPhase(new EndPhase(game, player,enemy));
        } else {

            System.out.println("메인페이즈1 입니다");
            setPhase(new BattlePhase(game, player, enemy));
        }
    }


}
