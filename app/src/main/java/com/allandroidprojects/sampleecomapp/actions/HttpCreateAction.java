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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;

import org.botlibre.sdk.config.WebMediumConfig;

public class HttpCreateAction extends HttpUIAction {
	
	WebMediumConfig config;
	boolean finish;

	public HttpCreateAction(Activity activity, WebMediumConfig config) {
		super(activity);
		this.config = config;
		this.finish = true;
	}
	
	public HttpCreateAction(Activity activity, WebMediumConfig config, boolean finish) {
		super(activity);
		this.config = config;
		this.finish = finish;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
		this.config = MainActivityBotLIbre.connection.create(this.config);
		} catch (Exception exception) {
			this.exception = exception;
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onPostExecute(String xml) {
		super.onPostExecute(xml);
		if (this.exception != null) {
			return;
		}
		try {
			MainActivityBotLIbre.instance = this.config;
			
			if (this.finish) {
				this.activity.finish();
			}

	    	SharedPreferences.Editor cookies = MainActivityBotLIbre.current.getPreferences(Context.MODE_PRIVATE).edit();
	    	cookies.putString(this.config.getType(), this.config.name);
	    	cookies.commit();

        	Class childActivity = MainActivityBotLIbre.getActivity(this.config);
	        Intent intent = new Intent(this.activity, childActivity);	
	        this.activity.startActivity(intent);
	        
		} catch (Exception error) {
			this.exception = error;
			MainActivityBotLIbre.error(this.exception.getMessage(), this.exception, this.activity);
			return;
		}
	}

}