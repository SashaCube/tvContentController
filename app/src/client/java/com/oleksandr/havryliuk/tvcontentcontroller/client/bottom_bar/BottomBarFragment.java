package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;

import java.util.Objects;

public class BottomBarFragment extends Fragment {

    private BottomBarContract.IBottomBarPresenter presenter;
    private BottomBarContract.IBottomBarView view;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bottom_bar, container, false);

        initView();
        initPresenter();

        return root;
    }

    private void initView() {
        view = new BottomBarView();
        view.init(this, root);
    }

    private void initPresenter() {
        presenter = new BottomBarPresenter(view,
                WeatherRepository.getInstance(
                        Objects.requireNonNull(Objects.requireNonNull(
                                getActivity()).getApplication())));
        view.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.startDisplayInfo();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.stopDisplayInfo();
        }
    }
}
