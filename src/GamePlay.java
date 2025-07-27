public class GamePlay {
    private Game game;
    public GamePlay(Game game) {
        this.game = game;
    }
    public void start() {
        Sound sound = new Sound(game);
        BouncyGame bouncyGame = new BouncyGame(21,31,game,sound);
        bouncyGame.start();
    }
}
