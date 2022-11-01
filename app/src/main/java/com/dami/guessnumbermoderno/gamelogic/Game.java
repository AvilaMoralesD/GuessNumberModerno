package com.dami.guessnumbermoderno.gamelogic;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

/**
 * Clase POJO pura para el juego, sin más metodos que el constructor y los getter y setter
 */
public class Game implements Parcelable {
    private int nTries;
    private int currentTry;
    private int goal;

    /**
     * Objeto POJO para el juego
     *
     * @param nTries Limite de intentos a poner
     * @param goal   Numero objetivo para ganar
     */
    public Game(int nTries, int goal) {
        this.nTries = nTries;
        this.goal = goal;
    }

    protected Game(Parcel in) {
        nTries = in.readInt();
        currentTry = in.readInt();
        goal = in.readInt();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    /**
     * Getter de numero de intentos maximos
     *
     * @return Numero de intentos maximos
     */
    public int getnTries() {
        return nTries;
    }

    /**
     * Setter de numero de intentos maximos
     *
     * @param nTries Numero de intentos maximos
     */
    public void setnTries(int nTries) {
        this.nTries = nTries;
    }

    /**
     * Getter de intento actual
     *
     * @return Inteto actual
     */
    public int getCurrentTry() {
        return currentTry;
    }

    /**
     * Setter de intento actual
     *
     * @param currentTry Intento actual
     */
    public void setCurrentTry(int currentTry) {
        this.currentTry = currentTry;
    }

    /**
     * Getter de numero objetivo
     *
     * @return Numero objetivo
     */
    public int getGoal() {
        return goal;
    }

    /**
     * Getter de intentos que quedan
     *
     * @return Intentos que quedan
     */
    public int getRemainingTries() {
        return nTries - currentTry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(nTries);
        parcel.writeInt(currentTry);
        parcel.writeInt(goal);
    }
}

