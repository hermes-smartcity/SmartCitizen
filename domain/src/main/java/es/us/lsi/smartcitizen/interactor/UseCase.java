package es.us.lsi.smartcitizen.interactor;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase {

    private final Scheduler mExecutorThread;
    private final Scheduler mUIThread;

    private Subscription subscription = Subscriptions.empty();

    @Inject
    protected UseCase(@Named("executor_thread") Scheduler executorThread,
                      @Named("ui_thread") Scheduler uiThread) {
        this.mExecutorThread = executorThread;
        this.mUIThread = uiThread;
    }

    protected abstract Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber UseCaseSubscriber) {
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(mExecutorThread)
                .observeOn(mUIThread)
                .subscribe(UseCaseSubscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}