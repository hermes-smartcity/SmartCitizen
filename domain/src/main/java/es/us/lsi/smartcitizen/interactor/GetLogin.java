package es.us.lsi.smartcitizen.interactor;


import javax.inject.Inject;

import es.us.lsi.smartcitizen.repository.LoginRepository;
import rx.Observable;
import rx.Scheduler;

public class GetLogin extends UseCase {

    private final LoginRepository mLoginRepository;

    @Inject
    public GetLogin(LoginRepository loginRepository, Scheduler executorThread,
                       Scheduler uiThread) {
        super(executorThread, uiThread);
        this.mLoginRepository = loginRepository;
    }

    @Override public Observable buildUseCaseObservable() {
        return this.mLoginRepository.googleLogin();
    }
}
