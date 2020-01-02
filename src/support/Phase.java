package support;

import main.Game;

public abstract class Phase {


    private static Phase phase;
    protected Game game;
    protected Player player;
    protected Player enemy;

    protected static boolean mainPhase2;
    //abstract에서 이따구로하면안된다...
//    public Phase(){
//
//    }
//    public Phase(Game game, Player player){
//        this.game = game;
//        this.player = player;
//        //시작은 항상 스텐바이.ㅎ ㅏ이큐
//        this.phase = new StandbyPhase();
//    }

    public static Phase getPhase(Game game, Player player, Player enemy){
        phase = new StandbyPhase(game, player, enemy);
        return phase;
    }

    public void setPhase(Phase phase){
        this.phase = phase;
    }
}
