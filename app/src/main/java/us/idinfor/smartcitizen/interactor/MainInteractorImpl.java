package us.idinfor.smartcitizen.interactor;

import android.content.SharedPreferences;

import javax.inject.Inject;

import us.idinfor.smartcitizen.Constants;
import us.idinfor.smartcitizen.data.api.hermes.entity.User;

public class MainInteractorImpl implements MainInteractor {

    private static final String TAG = MainInteractorImpl.class.getCanonicalName();

    private final SharedPreferences mPrefs;

    @Inject
    public MainInteractorImpl(SharedPreferences prefs){
        this.mPrefs = prefs;
    }

    @Override
    public User getUserFromPreferences() {
        return new User(this.mPrefs.getString(Constants.PROPERTY_USER_NAME,""), null);
    }

    @Override
    public boolean isDrawerLearnedInPreferences() {
        return this.mPrefs.getBoolean(Constants.PROPERTY_DRAWER_LEARNED, false);
    }

    @Override
    public void setDrawerLearnedInPreferences(boolean learned) {
        this.mPrefs.edit().putBoolean(Constants.PROPERTY_DRAWER_LEARNED, learned).commit();
    }


}