package support;

import main.Game;

public class MainPhase extends Phase{

    public MainPhase(Game game, Player player,Player enemy) {

//        this.game = game;
//        this.player = player;
//        this.enemy = enemy;

        if (mainPhase2) {
            phaseName = "메인2";
            System.out.println("메인페이즈2 입니다");

        } else {
            phaseName = "메인1";

            System.out.println("메인페이즈1 입니다");

        }
    }

    public void goFight(){
           setPhase( new BattlePhase(game, player, enemy));
    }
    public void goEnd(){
        setPhase(new EndPhase(game, player,enemy));
    }


}
