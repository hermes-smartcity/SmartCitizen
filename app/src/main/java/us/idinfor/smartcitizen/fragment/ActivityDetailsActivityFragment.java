package us.idinfor.smartcitizen.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.idinfor.smartcitizen.Constants;
import us.idinfor.smartcitizen.GoogleFitApi;
import us.idinfor.smartcitizen.R;
import us.idinfor.smartcitizen.Utils;
import us.idinfor.smartcitizen.adapter.ActivitySegmentDetailsAdapter;
import us.idinfor.smartcitizen.event.FitBucketsResultEvent;
import us.idinfor.smartcitizen.event.GoogleApiClientConnectedEvent;
import us.idinfor.smartcitizen.model.entities.ActivityDetails;
import us.idinfor.smartcitizen.model.entities.fit.ActivitySummaryFit;
import us.idinfor.smartcitizen.model.entities.fit.CaloriesExpendedFit;
import us.idinfor.smartcitizen.model.entities.fit.DistanceDeltaFit;
import us.idinfor.smartcitizen.model.entities.fit.LocationBoundingBoxFit;
import us.idinfor.smartcitizen.model.entities.fit.StepCountDeltaFit;


public class ActivityDetailsActivityFragment extends BaseGoogleFitFragment {

    private static final String TAG = ActivityDetailsActivityFragment.class.getCanonicalName();

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.activitiesRecyclerView)
    RecyclerView mActivitiesRecyclerView;

    ActivitySegmentDetailsAdapter adapter;

    private ArrayList<ActivityDetails> activities;

    public ActivityDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_details, container, false);
        ButterKnife.bind(this, view);
        mActivitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mActivitiesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mActivitiesRecyclerView.setHasFixedSize(true);
        activities = new ArrayList<ActivityDetails>();
        adapter = new ActivitySegmentDetailsAdapter(activities);
        mActivitiesRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(fitHelper.getGoogleApiClient().isConnected()){
            queryGoogleFit(Constants.RANGE_DAY);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected DataReadRequest.Builder buildFitQuery(){
        DataReadRequest.Builder builder = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_LOCATION_SAMPLE, DataType.AGGREGATE_LOCATION_BOUNDING_BOX)
                .aggregate(DataType.TYPE_ACTIVITY_SEGMENT, DataType.AGGREGATE_ACTIVITY_SUMMARY)
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .aggregate(DataType.TYPE_HEART_RATE_BPM, DataType.AGGREGATE_HEART_RATE_SUMMARY)
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
                .bucketByActivitySegment(1,TimeUnit.MINUTES);
        return builder;
    }

    @Override
    protected void queryGoogleFit(int timeRange) {
        mProgressBar.setVisibility(View.VISIBLE);
        fitHelper.queryFitnessData(
                Utils.getStartTimeRange(timeRange),
                new Date().getTime(),
                buildFitQuery(),
                GoogleFitApi.QUERY_DEFAULT);
    }

    @Subscribe
    public void onEvent(GoogleApiClientConnectedEvent event){
        queryGoogleFit(Constants.RANGE_DAY);
    }

    @Subscribe
    public void onEvent(FitBucketsResultEvent event){
        for (Bucket bucket : event.getBuckets()) {
            ActivityDetails currentActivity = new ActivityDetails();
            for (DataSet dataSet : bucket.getDataSets()) {
                if (dataSet.getDataType().equals(DataType.AGGREGATE_STEP_COUNT_DELTA)) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        if (dp.getDataType().equals(DataType.AGGREGATE_STEP_COUNT_DELTA)) {
                            StepCountDeltaFit stepCount = new StepCountDeltaFit(
                                    dp.getValue(Field.FIELD_STEPS).asInt(),
                                    dp.getStartTime(TimeUnit.MILLISECONDS),
                                    dp.getEndTime(TimeUnit.MILLISECONDS)
                            );
                            currentActivity.setStepCountDelta(stepCount);
                        }
                    }
                }

                if (dataSet.getDataType().equals(DataType.AGGREGATE_DISTANCE_DELTA)) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        if (dp.getDataType().equals(DataType.AGGREGATE_DISTANCE_DELTA)) {
                            DistanceDeltaFit distanceDelta = new DistanceDeltaFit(
                                    dp.getValue(Field.FIELD_DISTANCE).asFloat() / 1000,
                                    dp.getStartTime(TimeUnit.MILLISECONDS),
                                    dp.getEndTime(TimeUnit.MILLISECONDS)
                            );
                            currentActivity.setDistanceDelta(distanceDelta);
                        }
                    }
                }

                if (dataSet.getDataType().equals(DataType.AGGREGATE_CALORIES_EXPENDED)) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        if (dp.getDataType().equals(DataType.AGGREGATE_CALORIES_EXPENDED)) {
                            CaloriesExpendedFit caloriesExpended = new CaloriesExpendedFit(
                                    dp.getValue(Field.FIELD_CALORIES).asFloat(),
                                    dp.getStartTime(TimeUnit.MILLISECONDS),
                                    dp.getEndTime(TimeUnit.MILLISECONDS)
                            );
                            currentActivity.setCaloriesExpended(caloriesExpended);
                        }
                    }
                }

                if (dataSet.getDataType().equals(DataType.AGGREGATE_ACTIVITY_SUMMARY)) {
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        if (dp.getDataType().equals(DataType.AGGREGATE_ACTIVITY_SUMMARY)) {
                            ActivitySummaryFit activitySummary = new ActivitySummaryFit(
                                    dp.getValue(Field.FIELD_ACTIVITY).asActivity(),
                                    dp.getValue(Field.FIELD_DURATION).asInt() / 1000 / 60,
                                    dp.getValue(Field.FIELD_NUM_SEGMENTS).asInt(),
                                    dp.getStartTime(TimeUnit.MILLISECONDS),
                                    dp.getEndTime(TimeUnit.MILLISECONDS)
                            );
                            if(activitySummary.getName().startsWith(Constants.ACTIVITY_SLEEP_PREFIX)){
                                activitySummary.setName(Constants.ACTIVITY_SLEEP_PREFIX);
                            }
                            currentActivity.setActivitySummary(activitySummary);
                        }
                    }
                }

                if (dataSet.getDataType().equals(DataType.AGGREGATE_LOCATION_BOUNDING_BOX)) {
                    Float latitudeSW, longitudeSW, latitudeNE, longitudeNE;
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        if (dp.getDataType().equals(DataType.AGGREGATE_LOCATION_BOUNDING_BOX)) {

                            latitudeSW = dp.getValue(Field.FIELD_LOW_LATITUDE).asFloat();
                            longitudeSW = dp.getValue(Field.FIELD_LOW_LONGITUDE).asFloat();

                            latitudeNE = dp.getValue(Field.FIELD_HIGH_LATITUDE).asFloat();
                            longitudeNE = dp.getValue(Field.FIELD_HIGH_LONGITUDE).asFloat();

                            LocationBoundingBoxFit locationBoundingBox = new LocationBoundingBoxFit(
                                    latitudeSW,
                                    longitudeSW,
                                    latitudeNE,
                                    longitudeNE,
                                    dp.getStartTime(TimeUnit.MILLISECONDS),
                                    dp.getEndTime(TimeUnit.MILLISECONDS)
                            );
                            currentActivity.setLocationBoundingBox(locationBoundingBox);
                        }
                    }
                }
            }

            if(!activities.isEmpty() && currentActivity.getActivitySummary().getName().equals(Constants.ACTIVITY_SLEEP_PREFIX)){
                ActivityDetails lastActivity = activities.get(activities.size() - 1);
                if (lastActivity.getActivitySummary().getName().equals(Constants.ACTIVITY_SLEEP_PREFIX)) {
                    lastActivity.getActivitySummary().setEndTime(currentActivity.getActivitySummary().getEndTime());
                    lastActivity.getStepCountDelta().setSteps(
                            lastActivity.getStepCountDelta().getSteps() + currentActivity.getStepCountDelta().getSteps());
                    lastActivity.getCaloriesExpended().setCalories(
                            lastActivity.getCaloriesExpended().getCalories() + currentActivity.getCaloriesExpended().getCalories());
                    lastActivity.getDistanceDelta().setDistance(
                            lastActivity.getDistanceDelta().getDistance() + currentActivity.getDistanceDelta().getDistance());
                    activities.set(activities.size() - 1, lastActivity);
                } else {
                    activities.add(currentActivity);
                }
            }else{
                activities.add(currentActivity);
            }
        }
        updateUI();
    }

    private void updateUI() {
        if(!activities.isEmpty()){
            Collections.reverse(activities);
            adapter.clear();
            adapter.addAll(activities);
            adapter.notifyDataSetChanged();
        }
        mProgressBar.setVisibility(View.GONE);
    }
}
