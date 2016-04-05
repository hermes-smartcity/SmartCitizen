package us.idinfor.smartcitizen.mvp.presenter;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import es.us.lsi.smartcitizen.entity.UserEntity;
import es.us.lsi.smartcitizen.interactor.DefaultSubscriber;
import es.us.lsi.smartcitizen.interactor.UseCase;
import us.idinfor.smartcitizen.mvp.view.LoginView;
import us.idinfor.smartcitizen.mvp.view.View;

public class LoginPresenter implements Presenter {

    private LoginView mLoginView;
    private final UseCase mGoogleLoginUseCase;

    @Inject
    public LoginPresenter(@Named("google_login") UseCase googleLoginUseCase){
        this.mGoogleLoginUseCase = googleLoginUseCase;
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

    public void attemptLogin(){
        this.showViewLoading();
        this.mGoogleLoginUseCase.execute(new UserSubscriber());

    }

    private void showViewLoading() {
        this.mLoginView.showLoading();
    }

    private void hideViewLoading() {
        this.mLoginView.hideLoading();
    }

    private final class UserSubscriber extends DefaultSubscriber<UserEntity>{
        @Override
        public void onCompleted() {
            LoginPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            Log.e("LoginPresenter",e.getLocalizedMessage());
        }

        @Override
        public void onNext(UserEntity user) {
            Log.e("LoginPresenter",user.getEmail());
        }
    }

}
