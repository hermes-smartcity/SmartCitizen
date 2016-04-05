package us.idinfor.smartcitizen.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.common.SignInButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.idinfor.smartcitizen.R;
import us.idinfor.smartcitizen.di.components.LoginComponent;
import us.idinfor.smartcitizen.mvp.presenter.LoginPresenter;
import us.idinfor.smartcitizen.mvp.view.LoginView;

public class LoginFragment extends BaseFragment implements LoginView /*, GoogleApiClient.OnConnectionFailedListener*/ {

    private static final String TAG = LoginFragment.class.getCanonicalName();
    private static final int REQUEST_SIGN_IN = 2001;

    @Inject
    LoginPresenter mLoginPresenter;

    @Bind(R.id.signInGoogleBtn)
    SignInButton mSignInGoogleBtn;
    @Bind(R.id.progressBar)
    RelativeLayout mProgressBar;

   /* @Inject
    GoogleApiClient mGoogleApiClient;
    @Inject
    HermesCitizenApi mHermesCitizenApi;
    @Inject
    Lazy<SharedPreferences> prefs;*/

    public LoginFragment(){
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(LoginComponent.class).inject(this);
        //initDependencies();
        //injectGoogleApiClientComponent(mGoogleApiClientSignInComponent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        customizeSignInBtn();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mLoginPresenter.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /*private void initDependencies() {
        mGoogleApiClientSignInComponent = DaggerGoogleApiClientSignInComponent
                .builder()
                .applicationComponent(getBaseActivity().getApplicationComponent())
                .activityModule(new ActivityModule(getBaseActivity()))
                .googleApiClientSignInModule(new GoogleApiClientSignInModule(this))
                .build();
    }

    private void injectGoogleApiClientComponent(GoogleApiClientSignInComponent googleApiClientSignInComponent) {
        googleApiClientSignInComponent.inject(this);
    }*/

   /* @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }*/

    @OnClick(R.id.signInGoogleBtn)
    public void onClickSignInGoogleButton() {
        this.mLoginPresenter.attemptLogin();
        /*Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_SIGN_IN);*/
    }

    private void customizeSignInBtn(){
        mSignInGoogleBtn.setSize(SignInButton.SIZE_WIDE);
        mSignInGoogleBtn.setColorScheme(SignInButton.COLOR_LIGHT);
    }

   /* private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
           if(status.isSuccess())
               Log.d(TAG,"User signed out from Google");
        });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(status -> {
            if(status.isSuccess())
                Log.d(TAG,"Revoked Google access");
        });
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }*/

    /*private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess() && result.getSignInAccount() != null) {
            // Signed in successfully, register user in Smart Citizen backend.
            GoogleSignInAccount acct = result.getSignInAccount();
            loginOrRegisterUserInHermes(acct.getEmail());
        } else {
            // Signed out
            hideProgressDialog();
            Toast.makeText(getActivity(),
                    getString(R.string.common_google_play_services_sign_in_failed_text),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }*/

    /*private void loginOrRegisterUserInHermes(final String email) {
        showProgressDialog();
        mHermesCitizenApi.existsUser(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(booleanResponse -> {
                    if(booleanResponse.body()) {
                        // User exists
                        doLoginUser(email);
                        return Observable.empty();
                    }else{
                        // User doesn't exist, sign up user
                        return mHermesCitizenApi.registerUser(
                                new User(email, Constants.DEFAULT_PASSWORD))
                                .doOnNext(integerResponse -> {
                                    doRegisterUser(integerResponse.body(),email);
                                });
                    }
                })
                .doOnError(throwable ->
                        Toast.makeText(getContext(),throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show()
                )
                .doOnTerminate(() -> hideProgressDialog())
                .subscribe();
    }*/

    /*private void doLoginUser(String email){
        Log.i(TAG, "User registered or logged successfully");
        Toast.makeText(getContext(), "User signed up", Toast.LENGTH_LONG).show();
        prefs.get()
                .edit()
                .putString(Constants.PROPERTY_USER_NAME, email)
                .commit();
        GoogleFitHelper.initFitApi(getBaseActivity().getApplicationContext());
        MainActivity.launch(getActivity());
        getActivity().finish();
    }*/

    /*private void doRegisterUser(Integer result, String email){
        switch (result) {
            case HermesCitizenApi.RESPONSE_OK:
                doLoginUser(email);
                break;
            case HermesCitizenApi.RESPONSE_ERROR_USER_EXISTS:
                Log.e(TAG, "Error: User already taken");
                Toast.makeText(getActivity(), "Error: User already taken", Toast.LENGTH_LONG).show();
                signOut();
                revokeAccess();
                break;
            case HermesCitizenApi.RESPONSE_ERROR_USER_NOT_REGISTERED:
                Log.e(TAG, "Error: User not registered");
                Toast.makeText(getActivity(), "Error: User not registered", Toast.LENGTH_LONG).show();
                signOut();
                revokeAccess();
                break;
            default:
                Log.e(TAG, "Unknown error");
                Toast.makeText(getActivity(), "Unknown error", Toast.LENGTH_LONG).show();
                signOut();
                revokeAccess();
        }
    }*/


    @Override
    public void showLoading() {
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.mProgressBar.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }
}
