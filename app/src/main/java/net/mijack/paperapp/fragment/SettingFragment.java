package net.mijack.paperapp.fragment;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import net.mijack.paperapp.R;
import net.mijack.paperapp.api.ApiService;
import net.mijack.paperapp.util.PreferenceUtil;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.fragment_setting);
        PreferenceManager manager = getPreferenceManager();
        EditTextPreference preference = (EditTextPreference) manager.findPreference("edit_text_preference_1");
        preference.setText(ApiService.baseUrl);
        preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                PreferenceUtil.saveBaseUrl(getActivity().getApplication(), o.toString());
                return true;
            }
        });

    }
}
