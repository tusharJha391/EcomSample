package com.allandroidprojects.sampleecomapp.actions;

import android.app.Activity;

import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;
import com.allandroidprojects.sampleecomapp.botlibre.WebMediumAdminActivity;

import org.botlibre.sdk.config.WebMediumConfig;


public class HttpChangeIconAction extends HttpUIAction {
    WebMediumConfig config;
    String file;

    public HttpChangeIconAction(Activity activity, String file, WebMediumConfig config) {
        super(activity);
        this.config = config;
        this.file = file;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            this.config = MainActivityBotLIbre.connection.updateIcon(this.file, this.config);
        } catch (Exception exception) {
            this.exception = exception;
        }
        return "";
    }

    @Override
    public void onPostExecute(String xml) {
        super.onPostExecute(xml);
        if (this.exception != null) {
            return;
        }
        try {
            MainActivityBotLIbre.instance = this.config;
            ((WebMediumAdminActivity)this.activity).resetView();
        } catch (Exception error) {
            this.exception = error;
            MainActivityBotLIbre.error(this.exception.getMessage(), this.exception, this.activity);
            return;
        }
    }
}
