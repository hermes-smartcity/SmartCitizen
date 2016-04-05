package us.idinfor.smartcitizen.mvp.view;

import android.content.Context;

public interface LoadDataView extends View {

    void showLoadingView();
    void hideLoadingView();
    void showRetryView();
    void hideRetryView();
    void showErrorView(String message);
    void hideErrorView();
    void showEmptyIndicator();
    void hideEmptyIndicator();
    Context context();
}