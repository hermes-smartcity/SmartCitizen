package us.idinfor.smartcitizen.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;

import us.idinfor.smartcitizen.R;
import us.idinfor.smartcitizen.di.HasComponent;
import us.idinfor.smartcitizen.di.components.LoginComponent;
import us.idinfor.smartcitizen.di.components.UserComponent;
import us.idinfor.smartcitizen.view.fragment.LoginFragment;


public class LoginActivity extends BaseActivity implements HasComponent<LoginComponent> {

    private static final String TAG = LoginActivity.class.getCanonicalName();

    public static Intent getCallingIntent(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    private LoginComponent mLoginComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);
        buildActionBarToolbar(getString(R.string.title_activity_login), false);

        this.initializeInjector();
        if(savedInstanceState == null){
            addFragment(R.id.fragmentContainer,new LoginFragment());
        }
    }

    private void initializeInjector() {
        this.mLoginComponent = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public LoginComponent getComponent() {
        return mLoginComponent;
    }
}



