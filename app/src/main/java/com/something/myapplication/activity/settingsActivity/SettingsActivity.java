package com.something.myapplication.activity.settingsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.something.myapplication.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_settings));
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        mContext = getApplicationContext();
        mActivity = SettingsActivity.this;
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        public static final String KEY_PREF_LANGUAGE = "Languages";

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            switch (key) {
//                case KEY_PREF_LANGUAGE:
//                    LocaleHelper.setLocale(getContext(),PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, ""));
//                    getActivity().recreate();
//                    break;
//            }
            if (key.equals(KEY_PREF_LANGUAGE)) {
                Toast.makeText(getContext(),sharedPreferences.getString(key,""),Toast.LENGTH_LONG).show();
                Preference connectionPref = findPreference(key);
                connectionPref.setSummary(sharedPreferences.getString(key, ""));
                updateLanguage(getContext(), sharedPreferences.getString(key, ""));
               // LocaleHelper.setLocale(getContext(),sharedPreferences.getString(key,""));
            }
        }

        public static void updateLanguage(Context context, String selectedLanguage) {
            if (!"".equals(selectedLanguage)) {
                if ("English".equals(selectedLanguage)) {
                    //selectedLanguage = "en";
                    LanguageManager.setNewLocale(context, LanguageManager.LANGUAGE_KEY_ENGLISH);
                } else if ("Hindi".equals(selectedLanguage)) {
                    //selectedLanguage = "hi";
                    LanguageManager.setNewLocale(context, LanguageManager.LANGUAGE_KEY_HINDI);
                }
                else if ("French".equals(selectedLanguage)) {
                    //selectedLanguage = "fr";
                    LanguageManager.setNewLocale(context, LanguageManager.LANGUAGE_KEY_FRENCH);

                }
//                Locale locale = new Locale(selectedLanguage);
//                Locale.setDefault(locale);
//                Configuration config = new Configuration();
//                config.locale = locale;
//                context.getResources().updateConfiguration(config, null);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
