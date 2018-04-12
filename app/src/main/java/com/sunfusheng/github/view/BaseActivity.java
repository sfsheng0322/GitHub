package com.sunfusheng.github.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sunfusheng.github.R;
import com.sunfusheng.github.widget.dialog.ProgressDialogHelper;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author sunfusheng on 2018/4/11.
 */
public class BaseActivity extends RxAppCompatActivity {

    protected ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showProgressDialog() {
        showProgressDialog(R.string.com_waiting);
    }

    protected void showProgressDialog(int resId) {
        if (progressDialogHelper == null) {
            progressDialogHelper = new ProgressDialogHelper(this);
        }
        progressDialogHelper.setMessage(resId);
        progressDialogHelper.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialogHelper != null && progressDialogHelper.isShowing()) {
            progressDialogHelper.dismiss();
        }
    }
}
