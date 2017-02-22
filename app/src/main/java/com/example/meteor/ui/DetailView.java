package com.example.meteor.ui;

import com.example.meteor.network.model.Meteor;

/**
 * @author martin
 * @since 21/02/2017.
 */
public interface DetailView extends BaseView {

    void showMeteor(Meteor meteor);
}
