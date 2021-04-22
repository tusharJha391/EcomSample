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
import android.widget.EditText;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;
import com.allandroidprojects.sampleecomapp.botlibre.WebMediumUsersActivity;

import org.botlibre.sdk.config.UserAdminConfig;

public class HttpUserAdminAction extends HttpUIAction {

	UserAdminConfig config;
	
	public HttpUserAdminAction(Activity activity, UserAdminConfig config) {
		super(activity);
		this.config = config;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			MainActivityBotLIbre.connection.userAdmin(this.config);
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
		if (this.config.operation.equals("AddUser")) {
			((WebMediumUsersActivity)this.activity).users.add(this.config.operationUser);
		} else if (this.config.operation.equals("RemoveUser")) {
			((WebMediumUsersActivity)this.activity).users.remove(this.config.operationUser);
		} else if (this.config.operation.equals("AddAdmin")) {
			((WebMediumUsersActivity)this.activity).admins.add(this.config.operationUser);
		} else if (this.config.operation.equals("RemoveAdmin")) {
			((WebMediumUsersActivity)this.activity).admins.remove(this.config.operationUser);
		}
        EditText text = (EditText) this.activity.findViewById(R.id.adminText);
		text.setText("");
        text = (EditText) this.activity.findViewById(R.id.userText);
		text.setText("");
		((WebMediumUsersActivity)this.activity).resetView();
	}
}