import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Board {
    JFrame frame;
    public Tile[][] tiles;
    private int tilewidth = 20;
    private int tileheight = 20;

    public Board(int i, int j, int bombCount) {
        tiles = new Tile[i][j];
        for (int m = 0; m < i; m++) {
            for (int n = 0; n < j; n++) {
                tiles[m][n] = new Tile(m, n);
            }
        }
        putBombs(bombCount);
        show(i * tileheight, j * tilewidth);
    }

    public void putBombs(int bombCount) {
        if (bombCount > tiles.length * tiles[0].length) {
            return;
        }

        Random rand = new Random();
        int num = 0;
        while (num < bombCount) {
            int i = rand.nextInt(tiles.length);
            int j = rand.nextInt(tiles[0].length);
            if (tiles[i][j].value < 10) {
                tiles[i][j].value += 10;
                num++;
            }

            if (i > 0) {
                tiles[i - 1][j].value++;
                if (j > 0) { tiles[i - 1][j - 1].value++; }
                if (j < tiles[0].length - 1) { tiles[i - 1][j + 1].value++; }
            }
            if (i < tiles.length - 1) {
                tiles[i + 1][j].value++;
                if (j > 0) { tiles[i + 1][j - 1].value++; }
                if (j < tiles[0].length - 1) { tiles[i + 1][j + 1].value++; }
            }
            if (j > 0) { tiles[i][j - 1].value++; }
            if (j < tiles[0].length - 1) { tiles[i][j + 1].value++; }
        }
    }

    private void show(int width, int height) {
        frame = new JFrame("Minesweeper");
        frame.setLayout(null);

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                Tile t = tiles[i][j];
                t.setBounds(10 + j * tilewidth, 10 + i * tileheight, tilewidth, tileheight);
                t.setMargin(new Insets(0, 0, 0, 0));
                t.addActionListener(e -> {
                    System.out.println(t.value);
                    if (t.value >= 10) {
                        gameOver();
                    } else {
                        t.setText(String.valueOf(t.value));
                    }
                });
                frame.add(t);
            }
        }

        frame.pack();
        frame.setSize(width + 36, height + 59);
        frame.setVisible(true);
    }

    private void gameOver() {
        frame.dispose();
    }
}
