package com.example;

import java.util.Random;
import java.util.Scanner;

record Jatekos(char szimbolum, String nev) {
}

class Connect4 {
    private static final int SOROK = 6;
    private static final int OSZLOPOK = 7;
    private static final char URES = '.';
    private final char[][] tabla = new char[SOROK][OSZLOPOK];
    private final Jatekos jatekos;
    private final Jatekos ai;

    public Connect4() {
        jatekos = new Jatekos('P', "Játékos");
        ai = new Jatekos('S', "AI");

        for (int i = 0; i < SOROK; i++) {
            for (int j = 0; j < OSZLOPOK; j++) {
                tabla[i][j] = URES;
            }
        }
    }

    public void tablaKiir() {
        for (int i = 0; i < SOROK; i++) {
            for (int j = 0; j < OSZLOPOK; j++) {
                System.out.print(tabla[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean korongLetesz(int oszlop, char jatekosSzimbolum) {
        if (oszlop < 0 || oszlop >= OSZLOPOK || tabla[0][oszlop] != URES) {
            return false;
        }

        for (int i = SOROK - 1; i >= 0; i--) {
            if (tabla[i][oszlop] == URES) {
                tabla[i][oszlop] = jatekosSzimbolum;
                return true;
            }
        }
        return false;
    }

    public boolean ellenorizNyeres(char jatekosSzimbolum) {
        for (int i = 0; i < SOROK; i++) {
            for (int j = 0; j < OSZLOPOK - 3; j++) {
                if (tabla[i][j] == jatekosSzimbolum && tabla[i][j + 1] == jatekosSzimbolum && tabla[i][j + 2] == jatekosSzimbolum && tabla[i][j + 3] == jatekosSzimbolum) {
                    return true;
                }
            }
        }

        for (int i = 0; i < SOROK - 3; i++) {
            for (int j = 0; j < OSZLOPOK; j++) {
                if (tabla[i][j] == jatekosSzimbolum && tabla[i + 1][j] == jatekosSzimbolum && tabla[i + 2][j] == jatekosSzimbolum && tabla[i + 3][j] == jatekosSzimbolum) {
                    return true;
                }
            }
        }

        for (int i = 0; i < SOROK - 3; i++) {
            for (int j = 0; j < OSZLOPOK - 3; j++) {
                if (tabla[i][j] == jatekosSzimbolum && tabla[i + 1][j + 1] == jatekosSzimbolum && tabla[i + 2][j + 2] == jatekosSzimbolum && tabla[i + 3][j + 3] == jatekosSzimbolum) {
                    return true;
                }
            }
        }

        for (int i = 0; i < SOROK - 3; i++) {
            for (int j = 3; j < OSZLOPOK; j++) {
                if (tabla[i][j] == jatekosSzimbolum && tabla[i + 1][j - 1] == jatekosSzimbolum && tabla[i + 2][j - 2] == jatekosSzimbolum && tabla[i + 3][j - 3] == jatekosSzimbolum) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean teleAVanATabla() {
        for (int j = 0; j < OSZLOPOK; j++) {
            if (tabla[0][j] == URES) {
                return false;
            }
        }
        return true;
    }

    public int aiLepes() {
        Random veletlen = new Random();
        int oszlop;
        do {
            oszlop = veletlen.nextInt(OSZLOPOK);
        } while (!korongLetesz(oszlop, ai.szimbolum()));
        return oszlop;
    }

    public static void main(String[] args) {
        Connect4 jatek = new Connect4();
        Scanner scanner = new Scanner(System.in);
        boolean nyertValaki = false;
        char aktualisJatekosSzimbolum = jatek.jatekos.szimbolum();

        System.out.println("Üdvözöl a Connect 4 játék!");
        jatek.tablaKiir();

        while (!nyertValaki && !jatek.teleAVanATabla()) {
            if (aktualisJatekosSzimbolum == jatek.jatekos.szimbolum()) {
                System.out.println("Te jössz! Adj meg egy oszlopot (0-6): ");
                int oszlop = scanner.nextInt();
                if (jatek.korongLetesz(oszlop, aktualisJatekosSzimbolum)) {
                    jatek.tablaKiir();
                    if (jatek.ellenorizNyeres(aktualisJatekosSzimbolum)) {
                        System.out.println("Nyertél!");
                        nyertValaki = true;
                    } else {
                        aktualisJatekosSzimbolum = jatek.ai.szimbolum();
                    }
                }
            } else {
                System.out.println("AI jön...");
                int aiOszlop = jatek.aiLepes();
                jatek.tablaKiir();
                System.out.println("AI az oszlopot választotta: " + aiOszlop);
                if (jatek.ellenorizNyeres(aktualisJatekosSzimbolum)) {
                    System.out.println("AI nyert!");
                    nyertValaki = true;
                } else {
                    aktualisJatekosSzimbolum = jatek.jatekos.szimbolum();
                }
            }
        }
        if (!nyertValaki) {
            System.out.println("Döntetlen! A tábla tele van.");
        }
    }
}
