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

import com.allandroidprojects.sampleecomapp.botlibre.ChatActivity;
import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;

import org.botlibre.sdk.config.DomainConfig;
import org.botlibre.sdk.config.InstanceConfig;
import org.botlibre.sdk.config.WebMediumConfig;

public class HttpFetchAction extends HttpUIAction {
	WebMediumConfig config;
	boolean launch;

	public HttpFetchAction(Activity activity, WebMediumConfig config) {
		super(activity);
		this.config = config;
	}
	
	public HttpFetchAction(Activity activity, WebMediumConfig config, boolean launch) {
		super(activity);
		this.config = config;
		this.launch = launch;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			this.config = MainActivityBotLIbre.connection.fetch(this.config);
		} catch (Exception exception) {
			this.exception = exception;
		}
		return "";
	}
	
	public void superOnPostExecute(String xml) {
		super.onPostExecute(xml);
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
			
        	SharedPreferences.Editor cookies = MainActivityBotLIbre.current.getPreferences(Context.MODE_PRIVATE).edit();
        	cookies.putString(this.config.getType(), this.config.name);
        	cookies.commit();

        	Class childActivity = MainActivityBotLIbre.getActivity(this.config);
        	if (this.launch && !this.config.isExternal) {
	        	if (this.config instanceof InstanceConfig) {
	        		childActivity = ChatActivity.class;
	        		HttpAction action = new HttpGetVoiceAction(this.activity, (InstanceConfig)MainActivityBotLIbre.instance.credentials());
	        		action.execute();
	        	} else if (this.config instanceof DomainConfig) {
	        		MainActivityBotLIbre.connection.setDomain((DomainConfig)this.config);
	    			MainActivityBotLIbre.domain = (DomainConfig)this.config;
	    			MainActivityBotLIbre.tags = null;
	    			MainActivityBotLIbre.categories = null;
	    			MainActivityBotLIbre.forumTags = null;
	    			MainActivityBotLIbre.forumCategories = null;
	    			MainActivityBotLIbre.channelTags = null;
	    			MainActivityBotLIbre.channelCategories = null;
	    			MainActivityBotLIbre.domainTags=null;
	    			MainActivityBotLIbre.domainCategories=null;
	    			MainActivityBotLIbre.graphicTags = null;
	    			MainActivityBotLIbre.graphicCategories = null;
	    			
	    			MainActivityBotLIbre.type = MainActivityBotLIbre.defaultType;
	    	        Intent intent = new Intent(this.activity, MainActivityBotLIbre.class);
	    			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    			this.activity.startActivity(intent);
	    			return;
	        	}
        	} else {
        		if (this.config instanceof InstanceConfig) {
	        		HttpAction action = new HttpGetVoiceAction(this.activity, (InstanceConfig)MainActivityBotLIbre.instance.credentials());
	        		action.execute();
	        	}
        	}
	        Intent intent = new Intent(this.activity, childActivity);
	        this.activity.startActivity(intent);
		} catch (Exception error) {
			this.exception = error;
			MainActivityBotLIbre.error(this.exception.getMessage(), this.exception, this.activity);
			return;
		}
	}
	
}