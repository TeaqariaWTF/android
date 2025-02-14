package mobileapp.ctemplar.com.ctemplarapp.login;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import mobileapp.ctemplar.com.ctemplarapp.BaseFragment;
import mobileapp.ctemplar.com.ctemplarapp.R;
import mobileapp.ctemplar.com.ctemplarapp.login.step.SignUpFragmentsAdapter;
import mobileapp.ctemplar.com.ctemplarapp.login.step.StepPasswordFragment;
import mobileapp.ctemplar.com.ctemplarapp.login.step.StepRecoveryFragment;
import mobileapp.ctemplar.com.ctemplarapp.login.step.StepRegistrationActions;
import mobileapp.ctemplar.com.ctemplarapp.login.step.StepRegistrationViewModel;
import mobileapp.ctemplar.com.ctemplarapp.login.step.StepUsernameFragment;
import mobileapp.ctemplar.com.ctemplarapp.login.step.ViewPagerNoScroll;

public class SignUpFragment extends BaseFragment {
    @BindView(R.id.fragment_sign_up_view_pager)
    ViewPagerNoScroll viewPager;

    @BindView(R.id.fragment_sign_up_page_indicator_tab_layout)
    TabLayout pageIndicatorTabLayout;

    private StepRegistrationViewModel stepModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new StepUsernameFragment());
        list.add(new StepPasswordFragment());
        list.add(new StepRecoveryFragment());
        //list.add(new StepSecurityFragment());

        viewPager.setAdapter(new SignUpFragmentsAdapter(getChildFragmentManager(), list));
        viewPager.setOnTouchListener(null);
        pageIndicatorTabLayout.setupWithViewPager(viewPager, true);

        stepModel = new ViewModelProvider(this).get(StepRegistrationViewModel.class);
        stepModel.getAction().observe(getViewLifecycleOwner(), this::handleRegistrationActions);
    }

    @OnClick(R.id.fragment_sign_up_back)
    public void onClickBack() {
        stepModel.changeAction(StepRegistrationActions.ACTION_BACK);
        // onBackPressed();
    }

    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            getActivity().onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void onNextPressed() {
        if (viewPager.getCurrentItem() != Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    public void handleRegistrationActions(StepRegistrationActions actions) {
        switch (actions) {
            case ACTION_BACK:
                onBackPressed();
                stepModel.changeAction(StepRegistrationActions.ACTION_DEFAULT);
                break;
            case ACTION_NEXT:
                onNextPressed();
                stepModel.changeAction(StepRegistrationActions.ACTION_DEFAULT);
                break;
        }
    }
}
