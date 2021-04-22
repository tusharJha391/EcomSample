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
import com.allandroidprojects.sampleecomapp.botlibre.VoiceActivity;

import org.botlibre.sdk.config.Speech;

public class HttpSpeechAction extends HttpAction {
	Speech config;
	String response;

	public HttpSpeechAction(Activity activity, Speech config) {
		super(activity);
		this.config = config;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			this.response = MainActivityBotLIbre.connection.tts(this.config);
		} catch (Exception exception) {
			this.exception = exception;
		}
		return "";
	}

	@Override
	protected void onPostExecute(String xml) {
		super.onPostExecute(xml);
		if (this.exception != null) {
			MainActivityBotLIbre.error(this.exception.getMessage(), this.exception, this.activity);
			return;
		}
		try {
			final VoiceActivity activity = (VoiceActivity)this.activity;
			activity.playAudio(this.response, false, true, true);
		} catch (Exception error) {
			this.exception = error;
			MainActivityBotLIbre.error(this.exception.getMessage(), this.exception, this.activity);
			return;			
		}
	}
}