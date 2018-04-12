package com.sunfusheng.github.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;

import com.sunfusheng.github.Constants;
import com.sunfusheng.github.R;
import com.sunfusheng.github.database.UserDatabase;
import com.sunfusheng.github.model.AuthParams;
import com.sunfusheng.github.net.Api;
import com.sunfusheng.github.net.exception.ExceptionUtil;
import com.sunfusheng.github.util.PreferenceUtil;
import com.sunfusheng.github.util.StatusBarUtil;
import com.sunfusheng.github.util.ToastUtil;
import com.sunfusheng.github.widget.SvgEnum;
import com.sunfusheng.github.widget.SvgView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author sunfusheng on 2018/4/10.
 */
public class LoginActivity extends BaseActivity {

    private EditText vUsername;
    private EditText vPassword;
    private TextView vLogin;
    private SvgView svgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.fullScreen(getWindow());
        initView();
        initSvgView();
    }

    private void initView() {
        vUsername = findViewById(R.id.username);
        vPassword = findViewById(R.id.password);
        vLogin = findViewById(R.id.login);
        svgView = findViewById(R.id.svg_view);

        String username = PreferenceUtil.getInstance().getString(Constants.PreferenceKey.USERNAME, "");
        String password = PreferenceUtil.getInstance().getString(Constants.PreferenceKey.PASSWORD, "");
        vUsername.setText(username);
        vUsername.setSelection(username.length());
        vPassword.setText(password);

        vLogin.setOnClickListener(v -> login());
    }

    private void initSvgView() {
        svgView.postDelayed(() -> {
            SvgEnum svg = SvgEnum.GITHUB;
            svgView.setGlyphStrings(svg.glyphs);
            svgView.setFillColors(svg.colors);
            svgView.setViewportSize(svg.width, svg.height);
            svgView.setTraceResidueColor(getResources().getColor(R.color.config_color_green_50));
            svgView.setTraceColors(svg.colors);
            svgView.rebuildGlyphData();
            svgView.start();
        }, 200);
    }

    private void login() {
        String username = vUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtil.toast(R.string.username_input);
            return;
        }

        String password = vPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(R.string.password_input);
            return;
        }

        showProgressDialog(R.string.com_waiting_login);

        String credentials = username + ":" + password;
        String basicAuth = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        PreferenceUtil.getInstance().put(Constants.PreferenceKey.USERNAME, username);
        PreferenceUtil.getInstance().put(Constants.PreferenceKey.PASSWORD, password);
        PreferenceUtil.getInstance().put(Constants.PreferenceKey.AUTH, basicAuth);

        Observable.zip(Api.getLoginService().login(), Api.getLoginService().createAuth(AuthParams.getParams()),
                (user, auth) -> {
                    if (user == null || TextUtils.isEmpty(user.getLogin()) || auth == null || TextUtils.isEmpty(auth.getToken())) {
                        return false;
                    }
                    UserDatabase.getDefault(this).getUserDao().insert(user);
                    PreferenceUtil.getInstance().put(Constants.PreferenceKey.TOKEN, auth.getToken());
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    dismissProgressDialog();
                    if (!it) {
                        PreferenceUtil.getInstance().remove(Constants.PreferenceKey.TOKEN);
                    } else {
                        ToastUtil.toast("登录成功");
                    }
                }, throwable -> {
                    dismissProgressDialog();
                    throwable.printStackTrace();
                    ToastUtil.toast(ExceptionUtil.handleException(throwable));
                });
    }
}