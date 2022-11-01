package com.dami.guessnumbermoderno.gamelogic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Clase que maneja el objeto POJO, aqui contiene toda la logica para poder jugar
 */
public class GameManager implements Parcelable {
    private Game game;
    private int MINRANGE = 1;
    private int MAXRANGE = 100;


    /**
     * Este constructor genera un nuevo juego con un numero objetivo entre 1 y 100 (no incluido)
     */
    public GameManager() {
        int goal = (new Random()).nextInt(MAXRANGE - MINRANGE) + MINRANGE;
        game = new Game(7, goal);
    }

    /**
     * Este constructor genera un nuevo juego con un numero objetivo entre 1 y 100 (no incluido)
     *
     * @param nTries Número de intentos máximos para poder ganar
     */
    public GameManager(int nTries) {
        int goal = (new Random()).nextInt(MAXRANGE - MINRANGE) + MINRANGE;
        game = new Game(nTries, goal);
    }

    protected GameManager(Parcel in) {
        game = in.readParcelable(Game.class.getClassLoader());
        MINRANGE = in.readInt();
        MAXRANGE = in.readInt();
    }

    public static final Creator<GameManager> CREATOR = new Creator<GameManager>() {
        @Override
        public GameManager createFromParcel(Parcel in) {
            return new GameManager(in);
        }

        @Override
        public GameManager[] newArray(int size) {
            return new GameManager[size];
        }
    };

    /**
     * Método que indica si aun quedan intentos disponibles o no
     *
     * @return Verdadero si si, falso si no
     */
    public boolean canMakeGuess() {
        return getRemainingTries() > 0;
    }

    /**
     * Usa un numero para intentar ganar el juego
     *
     * @param guess El numero que probar en el juego
     * @return Devuelve -1 si tu intento es menor, 0 si acertaste y 1 si te pasaste
     */
    public int makeAGuess(int guess) {
        increaseNTries();
        return Integer.compare(guess, getGoal());
    }

    /**
     * Metodo que incrementa el intento por el que vas actualmente
     */
    public void increaseNTries() {
        setCurrentTry(getCurrentTry() + 1);
    }

    /**
     * Getter del numero objetivo del juego creado
     *
     * @return Numero objetivo
     */
    public int getGoal() {
        return game.getGoal();
    }

    /**
     * Getter del objeto Game
     *
     * @return Objeto Game instanciado
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter del numero de intentos muximo del juego creado
     *
     * @return Numero de intentos muximo
     */
    public int getNTries() {
        return game.getnTries();
    }

    /**
     * Getter del numero actual de intento
     *
     * @return Numero actual de intento
     */
    public int getCurrentTry() {
        return game.getCurrentTry();
    }

    /**
     * Getter del numero de intentos que quedan del juego creado
     *
     * @return Numero de intentos que quedan
     */
    public int getRemainingTries() { return game.getRemainingTries(); }

    /**
     * Setter de numero de intentos maximos
     *
     * @param nTries Numero de intentos maximos
     */
    public void setNTries(int nTries) {
        game.setnTries(nTries);
    }

    /**
     * Setter de intento actual
     *
     * @param currentTry Intento actual
     */
    public void setCurrentTry(int currentTry) {
        game.setCurrentTry(currentTry);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(game, i);
        parcel.writeInt(MINRANGE);
        parcel.writeInt(MAXRANGE);
    }
}
