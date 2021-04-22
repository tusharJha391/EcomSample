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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.botlibre.MainActivityBotLIbre;

import org.botlibre.sdk.config.ContentConfig;

public class HttpGetTagsAction extends HttpAction {
	ContentConfig config;
	Object[] tags;
	ListView llview;
	
	public HttpGetTagsAction(Activity activity, String type) {
		super(activity);
		this.config = new ContentConfig();
		this.config.type = type;
	}

	@Override
	protected String doInBackground(Void... params) {
		if (this.config.type.equals("Bot") && MainActivityBotLIbre.tags != null) {
			this.tags = MainActivityBotLIbre.tags;
		} else if (this.config.type.equals("Forum") && MainActivityBotLIbre.forumTags != null) {
			this.tags = MainActivityBotLIbre.forumTags;
		} else if (this.config.type.equals("Post") && MainActivityBotLIbre.forumPostTags != null) {
			this.tags = MainActivityBotLIbre.forumPostTags;
		} else if (this.config.type.equals("Channel") && MainActivityBotLIbre.channelTags != null) {
			this.tags = MainActivityBotLIbre.channelTags;
		} else if (this.config.type.equals("Avatar") && MainActivityBotLIbre.avatarTags != null) {
			this.tags = MainActivityBotLIbre.avatarTags;
		} else if (this.config.type.equals("Script") && MainActivityBotLIbre.scriptTags != null) {
			this.tags = MainActivityBotLIbre.scriptTags;
		} else if (this.config.type.equals("Domain") && MainActivityBotLIbre.domainTags != null) {
//-------			//it's not passing anything
//			this.tags = new Object[0];
			//Clean up the domain
			this.config.domain = null;
			this.tags = MainActivityBotLIbre.domainTags;
		} else {
			try {
				this.tags = MainActivityBotLIbre.connection.getTags(this.config).toArray();
			} catch (Exception exception) {
				this.exception = exception;
			}
		}
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onPostExecute(String xml) {
		if (this.exception != null) {
			return;
		}
		if (this.config.type.equals("Bot")) {
			MainActivityBotLIbre.tags = this.tags;
		} else if (this.config.type.equals("Forum")) {
			MainActivityBotLIbre.forumTags = this.tags;
		} else if (this.config.type.equals("Post")) {
			MainActivityBotLIbre.forumPostTags = this.tags;
		} else if (this.config.type.equals("Channel")) {
			MainActivityBotLIbre.channelTags = this.tags;
		} else if (this.config.type.equals("Avatar")) {
			MainActivityBotLIbre.avatarTags = this.tags;
		} else if (this.config.type.equals("Script")) {
			MainActivityBotLIbre.scriptTags = this.tags;
		}else if(this.config.type.equals("Domain")){
			
			//added domainTags
			MainActivityBotLIbre.domainTags = this.tags;
		}
		
		llview = (ListView)this.activity.findViewById(R.id.theListView);
		if(llview != null){
			ArrayAdapter adapter = new ArrayAdapter(this.activity,
                android.R.layout.select_dialog_item, this.tags);
        llview.setAdapter(adapter);
		}
	}
}