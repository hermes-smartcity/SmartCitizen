package us.idinfor.smartcitizen.mvp.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import us.idinfor.smartcitizen.mvp.view.LoginView;
import us.idinfor.smartcitizen.mvp.view.View;

public class LoginPresenter implements Presenter {

    private LoginView mLoginView;
    private final UseCase mLoginUseCase;

    @Inject
    public LoginPresenter(@Named("login") UseCase getLoginUseCase){
        this.mLoginUseCase = getLoginUseCase;
    }


    @Override
    public void setView(View v) {
        mLoginView = (LoginView) v;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

}
