package com.example.ducvietho.iotapp.screen.group;

import com.example.ducvietho.iotapp.data.model.Group;

import java.util.List;

/**
 * Created by ducvietho on 2/3/2018.
 */

public interface GroupContract {
    interface Presenter {
        void getAllGroup();
    }

    interface View {
        void getAllGroupSuccess(List<Group> groups);
        void getAllGroupFailure(String message);
    }
}
