package es.us.lsi.smartcitizen.interactor;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase {

    private final Scheduler mExecutorThread;
    private final Scheduler mUIThread;

    private Subscription mSubscription = Subscriptions.empty();

    protected UseCase(Scheduler executorThread,Scheduler uiThread) {
        this.mExecutorThread = executorThread;
        this.mUIThread = uiThread;
    }

    protected abstract Observable buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber UseCaseSubscriber) {
        this.mSubscription = this.buildUseCaseObservable()
                .subscribeOn(mExecutorThread)
                .observeOn(mUIThread)
                .subscribe(UseCaseSubscriber);
    }

    public void unsubscribe() {
        if (!this.mSubscription.isUnsubscribed()) {
            this.mSubscription.unsubscribe();
        }
    }
}