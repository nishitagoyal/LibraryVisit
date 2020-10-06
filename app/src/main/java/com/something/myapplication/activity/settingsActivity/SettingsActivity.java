package com.something.myapplication.activity.settingsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.something.myapplication.R;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    Resources resources;
    private SharedPreferences mSharedPreferences;

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

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

         public static final String KEY_PREF_LANGUAGE = "pref_key_language";

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            switch (key) {
//                case KEY_PREF_LANGUAGE:
//                    LocaleHelper.setLocale(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(key, ""));
//                    getActivity().recreate(); // necessary here because this Activity is currently running and thus a recreate() in onResume() would be too late
//                    break;
//            }
            if(key.equals(KEY_PREF_LANGUAGE))
            {
                Preference connectionPref = findPreference(key);
                connectionPref.setSummary(sharedPreferences.getString(key, ""));
                changeLanguagePref(this ,sharedPreferences.getString(key, ""));
            }
        }

        private void changeLanguagePref(MyPreferenceFragment context, String lang) {
            Locale locale = null;
            if (lang.equals("English")){
                locale = new Locale("en");
            }else if (lang.equals("Hindi")){
                locale = new Locale("hi");
            }else
                {
                locale = new Locale("en");
            }
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, null);
        }
        @Override
        public void onResume() {
            super.onResume();
            // documentation requires that a reference to the listener is kept as long as it may be called, which is the case as it can only be called from this Fragment
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}