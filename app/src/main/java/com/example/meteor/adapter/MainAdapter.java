package com.example.meteor.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meteor.R;
import com.example.meteor.databinding.ItemMeteorBinding;
import com.example.meteor.network.model.Meteor;
import com.example.meteor.ui.MainView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class MainAdapter extends RealmRecyclerViewAdapter<Meteor, MainAdapter.MeteorViewHolder> {

    private MainView mView;

    public MainAdapter(MainView view, @NonNull Context context, @Nullable OrderedRealmCollection<Meteor> data) {
        super(context, data, true);
        mView = view;
    }

    @Override
    public MeteorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMeteorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_meteor, parent, false);

        return new MeteorViewHolder(binding, mView);
    }

    @Override
    public void onBindViewHolder(MeteorViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    class MeteorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemMeteorBinding mBinding;
        private MainView mView;

        MeteorViewHolder(ItemMeteorBinding binding, MainView view) {
            super(binding.getRoot());
            mBinding = binding;
            mView = view;
            mBinding.getRoot().setOnClickListener(this);
        }

        void onBind(Meteor meteor) {
            mBinding.setMeteor(meteor);
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (getData() != null) {
                mView.onItemClick(getData().get(getAdapterPosition()).getId());
            }
        }
    }
}
