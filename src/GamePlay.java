public class GamePlay {
    private Game game;
    public GamePlay(Game game) {
        this.game = game;
    }
    public void start() {
        Sound sound = new Sound(game);
        Menu menu = new Menu(game,sound);
        menu.interfaceInformation();
        sound.playSound("exit");
        System.exit(0);
    }
}
