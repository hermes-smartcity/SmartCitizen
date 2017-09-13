package es.us.hermes.smartcitizen.mvp.presenter;


import es.us.hermes.smartcitizen.mvp.model.hermes.User;

public interface SyncServicePresenter extends Presenter {

    User getUser();
    void onDestroy();
    void queryPeriodicLocations();
    void queryPeriodicActivities();

    void queryPeriodicSteps();

    void queryPeriodicDistances();

    void queryPeriodicCaloriesExpended();

    void queryPeriodicHeartRates();

    void queryPeriodicSleep();

    void queryFullDayData();

}
