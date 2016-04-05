package us.idinfor.smartcitizen.navigation;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

import us.idinfor.smartcitizen.view.activity.LoginActivity;
import us.idinfor.smartcitizen.view.activity.MainActivity;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToLogin(Activity activity) {
        if (activity != null) {
          Intent intentToLaunch = LoginActivity.getCallingIntent(activity);
          ActivityCompat.startActivity(activity, intentToLaunch, null);
        }
    }

    public void navigateToMain(Activity activity) {
        if (activity != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(activity);
            ActivityCompat.startActivity(activity, intentToLaunch, null);
        }
    }
}