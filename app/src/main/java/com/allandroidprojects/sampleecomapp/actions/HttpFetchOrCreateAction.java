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

import org.botlibre.sdk.config.InstanceConfig;
import org.botlibre.sdk.config.WebMediumConfig;

import android.app.Activity;

public class HttpFetchOrCreateAction extends HttpFetchAction {

	public HttpFetchOrCreateAction(Activity activity, WebMediumConfig config, boolean launch) {
		super(activity, config, launch);
	}

	@Override
	public void onPostExecute(String xml) {
		if (this.exception != null) {
	    	InstanceConfig instance = new InstanceConfig();
	    	instance.name = config.name;
	    	instance.isPrivate = true;
	    	instance.template = "template";
	    	
	    	instance.categories = "Misc";
	    	instance.tags = "";
	    	
			HttpCreateAction action = new HttpCreateAction(activity, instance);
			action.execute();
		} else {
			super.onPostExecute(xml);
		}
	}
	
}