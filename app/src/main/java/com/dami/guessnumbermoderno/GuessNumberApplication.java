package com.dami.guessnumbermoderno;

import android.app.Application;

public class GuessNumberApplication extends Application {
    //Usuario que ha iniciado sesión. DATO GLOBAL porque siempre se va a acceder a él
    //mediante el método getApplication().getUser()
    private static String user = "";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}