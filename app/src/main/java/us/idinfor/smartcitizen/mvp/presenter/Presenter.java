package us.idinfor.smartcitizen.mvp.presenter;

import us.idinfor.smartcitizen.mvp.view.View;

public interface Presenter {

    void setView (View v);
    void onCreate();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
