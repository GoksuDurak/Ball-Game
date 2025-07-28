import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LevelEditor {
    private Game game;
    private char selectedChar;
    private char[][] board;
    private Scanner scanner;
    private int mouseX = 0;
    private int mouseY = 0;
    public LevelEditor(Game game) {
        //WIP
        this.game = game;
        board = new char[15][15]; //-----> burada boyut belirle board'ın
        scanner = new Scanner(System.in);
    }
    public void start() {
        System.out.println("Welcome to Level Editor!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (!game.isExitActive()) {
            game.clear();
            printBoard();
            mouse();
            save();
            changeChar();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        game.setExitActive(false);
    }

    public void mouse() {
        if (Game.isMousePressed()) {
            mouseX = game.mousex;
            mouseY = game.mousey;
            if (mouseX < board[0].length && mouseY < board.length) {
                board[mouseY][mouseX] = selectedChar;
            }
            Game.setMousePressed(false);
        }
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void save() {
        if (game.isKeySPressed) {
            game.clear();
            try {
                BufferedWriter bfWrite = new BufferedWriter(new FileWriter("board.txt"));
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        bfWrite.write(board[i][j] + ",");
                    }
                    bfWrite.newLine();
                }
                bfWrite.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Save is successful");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            game.isKeySPressed = false;
        }
    }

    public void changeChar() {
        if (game.isKeyCPressed) {
            while (true) {
                game.clear();
                System.out.println("Character OR U++ format Write (U or C)");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("U")) {
                    while (true) {
                        game.clear();
                        System.out.println("Write character");
                        String input = scanner.nextLine().trim().toUpperCase();
                        // Eğer varsa U+ kısmını kaldır
                        if (input.startsWith("U+")) {
                            input = input.substring(2);
                        }
                        try {
                            int codePoint = Integer.parseInt(input, 16); // 16 lık sistemde inputu int yap

                            if (codePoint >= 0 && codePoint <= 0xFFFF) {
                                char charValue = (char) codePoint;
                                System.out.println("Character: " + charValue);
                                selectedChar = charValue;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            } else {
                                System.out.println("Bu Unicode değeri tek bir char ile temsil edilemez.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Geçersiz hexadecimal sayı girdiniz.");
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                } else if (choice.equalsIgnoreCase("C")) {
                    while (true) {
                        game.clear();
                        System.out.println("Write character");
                        String input = scanner.nextLine().toUpperCase();
                        if (!input.isEmpty()) {
                            selectedChar = input.charAt(0);
                            break;
                        }
                    }
                    break;
                }
            }
            game.isKeyCPressed = false;
        }
    }

}
