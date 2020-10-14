package com.something.myapplication.activity.settingsActivity;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String CURRENT_LANG = "CURRENT_LANG";
    private static final String PREF_NAME = "LibraryVisit";
    private static final String APP_LANGUAGE = "Language";
    private int PRIVATE_MODE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public String getCurrentLang() {
        return pref.getString(CURRENT_LANG, "");
    }

    public void setCurrentLang(String lang) {
        editor.putString(CURRENT_LANG, lang);
        editor.commit();
    }
}
