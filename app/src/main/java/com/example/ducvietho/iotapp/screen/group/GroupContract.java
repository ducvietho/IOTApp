package com.example.ducvietho.iotapp.screen.group;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ducvietho.iotapp.data.model.Equipment;
import com.example.ducvietho.iotapp.data.model.Group;
import com.example.ducvietho.iotapp.data.model.Response;

import java.util.List;

/**
 * Created by ducvietho on 2/3/2018.
 */

public interface GroupContract {
    interface Presenter {
        void getAllGroupLAN();
        void getAllGroupInternet();
        void getAllGroupDomain();
        void turnOnGroupLAN(Group group, ImageView imageView);
        void turnOnGroupInternet(Group group, ImageView imageView);
        void turnOnGroupDomain(Group group, ImageView imageView);
        void turnOffGroupLan(Group group,ImageView imageView);
        void turnOffGroupInternet(Group group,ImageView imageView);
        void turnOffGroupDomain(Group group,ImageView imageView);
    }

    interface View {
        void getAllGroupSuccess(List<Group> groups);
        void getAllGroupFailureLAN();
        void getAllGroupFailureInternet();
        void getAllGroupFailureDoamin(String message);
        void turnOnGroupSuccess( Group group, Response response, ImageView imageView);
        void turnOnGroupFailureLAN(Group group, ImageView imageView);
        void turnOnGroupFailureInternet(Group group, ImageView imageView);
        void turnOnGroupFailure(String message);
        void turnOffGroupSuccess(Group group,Response response,ImageView imageView);
        void turnOffGroupFailureLAN(Group group, ImageView imageView);
        void turnOffGroupFailureInternet(Group group, ImageView imageView);
        void turnOffGroupFailure(String message);
    }
}
