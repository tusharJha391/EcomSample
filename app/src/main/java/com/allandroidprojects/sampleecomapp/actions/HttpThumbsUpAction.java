/******************************************************************************
 *
 *  Copyright 2014 Paphus Solutions Inc.
 *
 *  Licensed under the Eclipse Public License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package com.allandroidprojects.sampleecomapp.actions;


import android.app.Activity;

import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;
import com.allandroidprojects.sampleecomapp.botlibre.WebMediumActivity;

import org.botlibre.sdk.config.WebMediumConfig;

public class HttpThumbsUpAction extends HttpUIAction {
	WebMediumConfig config;

	public HttpThumbsUpAction(Activity activity, WebMediumConfig config) {
		super(activity);
		this.config = config;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
		MainActivityBotLIbre.connection.thumbsUp(this.config);
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
	    MainActivityBotLIbre.instance.thumbsUp++;
	    ((WebMediumActivity)this.activity).resetView();
	}
	
}