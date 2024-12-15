package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Connect4 {

    private final char[][] tabla;

    public Connect4() {
        tabla = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                tabla[i][j] = '.';
            }
        }
    }

    public char[][] getTabla() {
        return tabla;
    }

    public boolean korongLetesz(int oszlop, char jatekos) {
        if (oszlop < 0 || oszlop >= 7) {
            return false;
        }
        for (int i = 5; i >= 0; i--) {
            if (tabla[i][oszlop] == '.') {
                tabla[i][oszlop] = jatekos;
                return true;
            }
        }
        return false;
    }

    public boolean ellenorizNyeres(char jatekos) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (tabla[i][j] == jatekos && tabla[i][j + 1] == jatekos &&
                        tabla[i][j + 2] == jatekos && tabla[i][j + 3] == jatekos) {
                    return true;
                }
            }
        }

        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 3; i++) {
                if (tabla[i][j] == jatekos && tabla[i + 1][j] == jatekos &&
                        tabla[i + 2][j] == jatekos && tabla[i + 3][j] == jatekos) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (tabla[i][j] == jatekos && tabla[i + 1][j + 1] == jatekos &&
                        tabla[i + 2][j + 2] == jatekos && tabla[i + 3][j + 3] == jatekos) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 7; j++) {
                if (tabla[i][j] == jatekos && tabla[i + 1][j - 1] == jatekos &&
                        tabla[i + 2][j - 2] == jatekos && tabla[i + 3][j - 3] == jatekos) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean teleAVanATabla() {
        for (int j = 0; j < 7; j++) {
            if (tabla[0][j] == '.') {
                return false;
            }
        }
        return true;
    }

    public int aiLepes() {
        for (int j = 0; j < 7; j++) {
            if (korongLetesz(j, 'S')) {
                return j;
            }
        }
        return -1; // Nem tudott lépni, mert tele a tábla
    }
}

class Connect4Test {

    @Test
    void testTablaInicializalas() {
        Connect4 game = new Connect4();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                assertEquals('.', game.getTabla()[i][j], "A tábla nem megfelelően inicializálódott.");
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
        assertTrue(oszlop >= 0 && oszlop < 7, "Az AI lépése kívül esik a megengedett tartományon.");
        assertEquals('S', game.getTabla()[5][oszlop], "Az AI lépése nem történt meg helyesen a táblán.");
    }

    @Test
    void testTeljesJatek() {
        Connect4 game = new Connect4();

        game.korongLetesz(0, 'P');
        game.korongLetesz(1, 'P');
        game.korongLetesz(2, 'P');
        game.korongLetesz(3, 'P');

        assertTrue(game.ellenorizNyeres('P'), "A játékosnak nyernie kellett volna a vízszintes sorral.");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                game.korongLetesz(j, 'S');
            }
        }
        assertTrue(game.teleAVanATabla(), "A táblának teljesen megtelt állapotban kellene lennie.");
    }
}
