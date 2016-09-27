package com.wodm.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 工具类
 */
public class Preferences {
    private static Preferences m_stInstance;

    private SharedPreferences mPrefs;

    private Context m_Context;

    private static final String PREFERENCE_NAME = "config";

    public static synchronized Preferences getInstance(Context context) {
        if (m_stInstance == null) {
            m_stInstance = new Preferences(context.getApplicationContext());
        }
        return m_stInstance;
    }

    private Preferences(Context context) {
        m_Context = context;
    }

    public int getPreference(String name, int defaultValue) {
        return getmPrefs().getInt(name, defaultValue);
    }

    public void setPreference(String name, int value) {

        Editor editor = getmPrefs().edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public long getPreference(String name, long defaultValue) {
        return getmPrefs().getLong(name, defaultValue);
    }

    public void setPreference(String name, long value) {
        Editor editor = getmPrefs().edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public void setPreference(String name, Float value) {
        Editor editor = getmPrefs().edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    public Float getPreference(String name, Float defaultValue) {
        return getmPrefs().getFloat(name, defaultValue);
    }

    public boolean getPreference(String name, boolean defaultValue) {
        return getmPrefs().getBoolean(name, defaultValue);
    }

    public void setPreference(String name, boolean value) {
        Editor editor = getmPrefs().edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public void setPreference(String name, String value) {
        Editor editor = getmPrefs().edit();
        editor.putString(name, value);
        editor.commit();
    }

    public String getPreference(String name, String defaultValue) {
        return getmPrefs().getString(name, defaultValue);
    }

    public SharedPreferences getmPrefs() {
        if (mPrefs == null) {
            mPrefs = m_Context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        return mPrefs;
    }
}