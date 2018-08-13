package com.app.join.sistemajoin.Tools;

import android.util.Base64;

public class Base64Custon {


    public static String codificadorBase64(String text) {
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String descodificadorBase64(String testoCodificado) {
        return new String(Base64.decode(testoCodificado, Base64.DEFAULT));
    }

}
