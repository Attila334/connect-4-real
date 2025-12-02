package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Connect4 {

    private final char[][] tabla;
    public static final int ROWS = 6;
    public static final int COLS = 7;

    public Connect4() {
        tabla = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tabla[i][j] = '.';
            }
        }
    }

    public char[][] getTabla() {
        return tabla;
    }

    public boolean korongLetesz(int oszlop, char jatekos) {
        if (oszlop < 0 || oszlop >= COLS) {
            return false;
        }

        for (int i = ROWS - 1; i >= 0; i--) {
            if (tabla[i][oszlop] == '.') {
                tabla[i][oszlop] = jatekos;
                return true;
            }
        }
        return false;
    }

    public boolean ellenorizNyeres(char jatekos) {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 3; j++) {
                if (tabla[i][j] == jatekos && tabla[i][j + 1] == jatekos &&
                        tabla[i][j + 2] == jatekos && tabla[i][j + 3] == jatekos) {
                    return true;
                }
            }
        }


        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < ROWS - 3; i++) {
                if (tabla[i][j] == jatekos && tabla[i + 1][j] == jatekos &&
                        tabla[i + 2][j] == jatekos && tabla[i + 3][j] == jatekos) {
                    return true;
                }
            }
        }


        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLS - 3; j++) {
                if (tabla[i][j] == jatekos && tabla[i + 1][j + 1] == jatekos &&
                        tabla[i + 2][j + 2] == jatekos && tabla[i + 3][j + 3] == jatekos) {
                    return true;
                }
            }
        }


        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 3; j < COLS; j++) {
                if (tabla[i][j] == jatekos && tabla[i + 1][j - 1] == jatekos &&
                        tabla[i + 2][j - 2] == jatekos && tabla[i + 3][j - 3] == jatekos) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean teleAVanATabla() {
        for (int j = 0; j < COLS; j++) {
            if (tabla[0][j] == '.') {
                return false;
            }
        }
        return true;
    }

    public int aiLepes() {
        for (int j = 0; j < COLS; j++) {
            if (korongLetesz(j, 'S')) {
                return j;
            }
        }
        return -1;
    }
}

class Connect4Test {

    @Test
    void testTablaInicializalas() {
        Connect4 game = new Connect4();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals('.', game.getTabla()[i][j]);
            }
        }
    }

    @Test
    void testKorongLetesz_ValidColumn() {
        Connect4 game = new Connect4();
        assertTrue(game.korongLetesz(0, 'P'));
        assertTrue(game.korongLetesz(6, 'P'));
    }

    @Test
    void testKorongLetesz_InvalidColumn() {
        Connect4 game = new Connect4();
        assertFalse(game.korongLetesz(-1, 'P'));
        assertFalse(game.korongLetesz(7, 'P'));
    }

    @Test
    void testKorongLetesz_ColumnFull() {
        Connect4 game = new Connect4();
        for (int i = 0; i < 6; i++) {
            assertTrue(game.korongLetesz(0, 'P'));
        }
        assertFalse(game.korongLetesz(0, 'P'));
    }

    @Test
    void testEllenorizNyeres_Vizszintes() {
        Connect4 game = new Connect4();
        game.korongLetesz(0, 'P');
        game.korongLetesz(1, 'P');
        game.korongLetesz(2, 'P');
        game.korongLetesz(3, 'P');
        assertTrue(game.ellenorizNyeres('P'));
    }

    @Test
    void testEllenorizNyeres_Fuggoleges() {
        Connect4 game = new Connect4();
        for (int i = 0; i < 4; i++) {
            game.korongLetesz(0, 'P');
        }
        assertTrue(game.ellenorizNyeres('P'));
    }

    @Test
    void testEllenorizNyeres_Atlos() {
        Connect4 game = new Connect4();

        game.korongLetesz(0, 'P');

        game.korongLetesz(1, 'S');
        game.korongLetesz(1, 'P');

        game.korongLetesz(2, 'S');
        game.korongLetesz(2, 'S');
        game.korongLetesz(2, 'P');

        game.korongLetesz(3, 'S');
        game.korongLetesz(3, 'S');
        game.korongLetesz(3, 'S');
        game.korongLetesz(3, 'P');

        assertTrue(game.ellenorizNyeres('P'));
    }

    @Test
    void testTeleAVanATabla() {
        Connect4 game = new Connect4();
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                game.korongLetesz(col, 'P');
            }
        }
        assertTrue(game.teleAVanATabla());
    }

    @Test
    void testAiLepes() {
        Connect4 game = new Connect4();
        int oszlop = game.aiLepes();

        assertTrue(oszlop >= 0 && oszlop < 7);
        assertEquals('S', game.getTabla()[5][oszlop]);
    }

    @Test
    void testTeljesJatek() {
        Connect4 game = new Connect4();

        game.korongLetesz(0, 'P');
        game.korongLetesz(1, 'P');
        game.korongLetesz(2, 'P');
        game.korongLetesz(3, 'P');

        assertTrue(game.ellenorizNyeres('P'));

        game = new Connect4();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                game.korongLetesz(j, 'S');
            }
        }
        assertTrue(game.teleAVanATabla());
    }
}
