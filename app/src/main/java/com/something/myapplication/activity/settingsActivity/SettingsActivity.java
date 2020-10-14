package com.something.myapplication.activity.settingsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.something.myapplication.R;
import java.util.Locale;


public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_settings));
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        //public static final String KEY_PREF_LANGUAGE = "Languages";
        public static final String KEY_GET_LANGUAGE_SETTING = "languageSettings";

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getPreferenceManager().setSharedPreferencesName("settingsPreference");
            getPreferenceManager().setSharedPreferencesMode(Context.MODE_PRIVATE);
            addPreferencesFromResource(R.xml.settings);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            switch (key) {
//                case KEY_PREF_LANGUAGE:
//                    LocaleHelper.setLocale(getContext(),PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, ""));
//                    getActivity().recreate();
//                    break;
//            }
            //SessionManager sessionManager = new SessionManager(getActivity());
            if (key.equals(KEY_GET_LANGUAGE_SETTING)) {
                Toast.makeText(getContext(), sharedPreferences.getString(key, ""), Toast.LENGTH_LONG).show();
                Preference connectionPref = findPreference(key);
                connectionPref.setSummary(sharedPreferences.getString(key, ""));
               //LocaleHelper.setLocale(getContext(), sharedPreferences.getString(key, ""));
                if(sharedPreferences.getString(key, "")=="2")
                {
                    setAppLocale("hi");
                }
                else
                    setAppLocale("en");
            }

        }
        private void setAppLocale(String localeCode){
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration config = resources.getConfiguration();
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
                config.setLocale(new Locale(localeCode.toLowerCase()));
            } else {
                config.locale = new Locale(localeCode.toLowerCase());
            }
            resources.updateConfiguration(config, dm);
        }
    }
}
//        public void updateLanguage(Context context, String selectedLanguage) {
//            if (!"".equals(selectedLanguage)) {
//                if ("English".equals(selectedLanguage)) {
//                    selectedLanguage = "en";
//                    //LanguageManager.setNewLocale(context, selectedLanguage);
//                } else if ("Hindi".equals(selectedLanguage)) {
//                    selectedLanguage = "hi";
//                   //LanguageManager.setNewLocale(context, LanguageManager.LANGUAGE_KEY_HINDI);
//                }
//                else if ("French".equals(selectedLanguage)) {
//                    selectedLanguage = "fr";
//                    //LanguageManager.setNewLocale(context, LanguageManager.LANGUAGE_KEY_FRENCH);
//                }
//                Locale locale = new Locale(selectedLanguage);
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
//                context.getResources().updateConfiguration(config, null);
//            }
//        }
//        @Override
//        public void onResume() {
//            super.onResume();
//            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
//        }
//
//        @Override
//        public void onPause() {
//            super.onPause();
//            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
//        }
//    }
//}
//context = LocaleHelper.setLocale(MainActivity.this, "en");
//        resources = context.getResources();
//        text1.setText(resources.getString(R.string.language));
//        }