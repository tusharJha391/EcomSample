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

package com.allandroidprojects.sampleecomapp.botlibre;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.allandroidprojects.sampleecomapp.R;

import org.botlibre.sdk.config.VoiceConfig;

import java.util.Arrays;

/**
 * Activity for changing a bot's voice.
 */
public class ChangeVoiceActivity extends VoiceActivity {

	public void save(View view) {
		VoiceConfig config = new VoiceConfig();
        config.instance = MainActivityBotLIbre.instance.id;

        Spinner spin = (Spinner) findViewById(R.id.voiceSpin);
		config.voice = MainActivityBotLIbre.voices[Arrays.asList(MainActivityBotLIbre.voiceNames).indexOf(spin.getSelectedItem().toString())];
        spin = (Spinner) findViewById(R.id.languageSpin);
        config.language = spin.getSelectedItem().toString();
        spin = (Spinner) findViewById(R.id.voiceModSpin);
		config.mod = spin.getSelectedItem().toString();
		EditText text = (EditText) findViewById(R.id.pitchText);
		config.pitch = text.getText().toString();
		text = (EditText) findViewById(R.id.speechRateText);
		config.speechRate = text.getText().toString();		
		CheckBox checkbox = (CheckBox) findViewById(R.id.deviceVoiceCheckBox);
		config.nativeVoice = checkbox.isChecked();
		MainActivityBotLIbre.deviceVoice = config.nativeVoice;
		MainActivityBotLIbre.voice = config;
		MainActivityBotLIbre.customVoice = true;
		
    	SharedPreferences.Editor cookies = getPreferences(Context.MODE_PRIVATE).edit();
    	cookies.putString("voice", MainActivityBotLIbre.voice.voice);
    	cookies.putString("language", MainActivityBotLIbre.voice.language);
    	cookies.putString("nativeVoice", String.valueOf(MainActivityBotLIbre.voice.nativeVoice));
    	cookies.commit();
    	
    	finish();
		
	}
}
