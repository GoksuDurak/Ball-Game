public class Menu {
    private Game game;
    private Sound sound;
    private int mouseX, mouseY;
    private boolean selectCorretly = false;
    public Menu(Game game,Sound sound) {
        this.game = game;
        this.sound = sound;
    }

    public void interfaceInformation() {
        while (!selectCorretly) {
            game.clear();
            mouse();
            game.cn.getTextWindow().setCursorPosition(25, 5);
            System.out.println("Select Option : ");
            game.cn.getTextWindow().setCursorPosition(25, 6);
            System.out.println("------------------");
            game.cn.getTextWindow().setCursorPosition(25, 7);
            System.out.println("| 1.New Game     |");
            game.cn.getTextWindow().setCursorPosition(25, 8);
            System.out.println("------------------");
            game.cn.getTextWindow().setCursorPosition(25, 9);
            System.out.println("| 2.Level Editor |");
            game.cn.getTextWindow().setCursorPosition(25, 10);
            System.out.println("------------------");
            game.cn.getTextWindow().setCursorPosition(25, 11);
            System.out.println("| 3.Exit         |");
            game.cn.getTextWindow().setCursorPosition(25, 12);
            System.out.println("------------------");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        selectCorretly = false;
    }

    public void mouse() {
        if (Game.isMousePressed()) {
            mouseX = game.mousex;
            mouseY = game.mousey;
            if (mouseX > 28 && mouseX < 37 && mouseY == 7) {
                //New game
                sound.playSound("click");
                BouncyGame bouncyGame = new BouncyGame(21,31,game,sound);
                bouncyGame.start();
            } else if (mouseX > 28 && mouseX < 41 && mouseY == 9) {
                sound.playSound("click");
                //Level editor
            } else if (mouseX > 28 && mouseX < 33 && mouseY == 11) {
                sound.playSound("click");
                //Exit
                selectCorretly = true;
            }
            Game.setMousePressed(false);
        }
    }
}
