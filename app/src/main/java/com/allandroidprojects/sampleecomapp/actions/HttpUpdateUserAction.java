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

import org.botlibre.sdk.config.UserConfig;

public class HttpUpdateUserAction extends HttpUIAction {
	UserConfig config;

	public HttpUpdateUserAction(Activity activity, UserConfig config) {
		super(activity);
		this.config = config;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			this.config = MainActivityBotLIbre.connection.update(this.config);
		} catch (Exception exception) {
			this.exception = exception;
		}
		return "";
	}

	@Override
	protected void onPostExecute(String xml) {
		super.onPostExecute(xml);
		if (this.exception != null) {
			return;
		}
		try {
			MainActivityBotLIbre.user = this.config;

			this.activity.finish();
		} catch (Exception error) {
			this.exception = error;
			MainActivityBotLIbre.error(this.exception.getMessage(), this.exception, this.activity);
			return;			
		}
	}
	
}