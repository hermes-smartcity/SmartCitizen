package es.us.lsi.smartcitizen.interactor;


import javax.inject.Inject;
import javax.inject.Named;

import es.us.lsi.smartcitizen.repository.LoginRepository;
import rx.Observable;
import rx.Scheduler;

public class GetGoogleLogin extends UseCase {

    private final LoginRepository mLoginRepository;

    @Inject
    public GetGoogleLogin(LoginRepository loginRepository,
                          @Named("executor_thread") Scheduler executorThread,
                          @Named("ui_thread") Scheduler uiThread) {
        super(executorThread, uiThread);
        this.mLoginRepository = loginRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.mLoginRepository.googleLogin();
    }
}
