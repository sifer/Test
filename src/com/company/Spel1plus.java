package com.company;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 2017-01-27.
 */
public class Spel1plus {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));

        terminal.enterPrivateMode();

        Player player = new Player(10, 10);
        int scoreCounter = 0;

        List<Enemy> enemies = new ArrayList<>();
        Enemy enemy1 = new SlowEnemy(0, 0);
        Enemy enemy2 = new SlowEnemy(15, 15);
        Enemy enemy3 = new FastEnemy(15, 0);
        Enemy enemy4 = new FastEnemy(0, 15);
        enemies.add(enemy1);
        enemies.add(enemy2);
        enemies.add(enemy3);
        enemies.add(enemy4);

        boolean win = true;
        while (win) {
            scoreCounter++;
            MovePlayer(player, terminal);
            win = GameLogic(player, enemies);
            UpdateScreen(player, terminal, enemies);
        }

        //Fil med bästa High Score
        File file = new File("highscore.txt");
        //Scanner för att läsa filen
        Scanner in = new Scanner(file);
        //Spara nuvarande High Score i en int
        int valueHighScore = in.nextInt();
        in.close();
        //Jämför föregående HighScore med nuvarande och spara endast på nytt om bättre poäng finns
        if (scoreCounter > valueHighScore) {
            try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
                String highScore = "" + scoreCounter;
                br.write(highScore);
            } catch (IOException e) {
                System.out.println("Unable to read file: " + file.toString());
            }
            //Använd denna printtext om det blev ett nytt bättre High Score
            printText(10, 10, "Spelet slut!", "Du fick " + scoreCounter + " poäng.", "Grattis du slog rekordet. Bästa poäng är " + scoreCounter, terminal);
        } else {
            Scanner in2 = new Scanner(file);
            int valueHighScore2 = in2.nextInt();
            in2.close();
            //Används denna printtext om inget nytt rekord slogs
            printText(10, 10, "Spelet slut!", "Du fick " + scoreCounter + " poäng.", "Bästa poäng är " + valueHighScore2, terminal);
        }
    }

    private static void printText(int x, int y, String message, String scoreMessage, String bestScore, Terminal terminal) {
        for (int i = 0; i < message.length(); i++) {
            terminal.moveCursor(x++, y);
            terminal.putCharacter(message.charAt(i));
        }
        int y2 = 11;
        int x2 = 10;
        for (int i = 0; i < scoreMessage.length(); i++) {
            terminal.moveCursor(x2++, y2);
            terminal.putCharacter(scoreMessage.charAt(i));
        }
        int y3 = 15;
        int x3 = 10;
        for (int i = 0; i < bestScore.length(); i++) {
            terminal.moveCursor(x3++, y3);
            terminal.putCharacter(bestScore.charAt(i));
        }

    }

    private static void UpdateScreen(Player player, Terminal terminal, List<Enemy> enemy) {

        terminal.clearScreen();
        terminal.moveCursor(player.x, player.y);
        terminal.putCharacter('\u263A');
        for (int i = 0; i < enemy.size(); i++) {
            terminal.moveCursor((int) enemy.get(i).x, (int) (enemy.get(i).y));
            terminal.putCharacter(enemy.get(i).displaychar);
        }


        terminal.moveCursor(0, 0);

    }

    private static void MovePlayer(Player player, Terminal terminal) throws InterruptedException {
        Key key;
        do {
            Thread.sleep(5);
            key = terminal.readInput();
        } while (key == null);

        System.out.println(key.getCharacter() + " " + key.getKind());
        switch (key.getKind()) {
            case ArrowUp:
                player.y -= 1;
                if (player.y == -1) {
                    player.y = 0;
                }
                System.out.println(player.y);
                break;
            case ArrowDown:
                player.y += 1;
                if (player.y == 20) {
                    player.y = 19;
                }
                System.out.println(player.y);
                break;
            case ArrowLeft:
                player.x -= 1;
                if (player.x == -1) {
                    player.x = 0;
                }
                break;
            case ArrowRight:
                player.x += 1;
                if (player.x == 20) {
                    player.x = 19;
                }
                break;
        }


    }

    private static boolean GameLogic(Player player, List<Enemy> enemy) {
        boolean win = true;

        for (int i = 0; i < enemy.size(); i++) {
            float dx = player.x - enemy.get(i).x;

            if (dx > 0) {
                enemy.get(i).x += enemy.get(i).speed;
            } else {
                enemy.get(i).x += -(enemy.get(i).speed);
            }
            //if-satsen går att skriv på följande sätt också
            //enemy.x += (dx>0 ? 1 : -1);

            float dy = player.y - enemy.get(i).y;
            if (dy > 0) {
                enemy.get(i).y += enemy.get(i).speed;
            } else {
                enemy.get(i).y += -(enemy.get(i).speed);
            }
            if (player.x - (int)enemy.get(i).x == 0 && player.y - (int)enemy.get(i).y == 0) {
                System.out.println("false");
                win = false;

            } else {
                win = true;
            }

        }
        return win;


    }
}
