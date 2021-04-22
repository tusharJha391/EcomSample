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

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;
import com.allandroidprojects.sampleecomapp.actions.HttpAction;
import com.allandroidprojects.sampleecomapp.actions.HttpChangeUserIconAction;
import com.allandroidprojects.sampleecomapp.actions.HttpFlagUserAction;
import com.allandroidprojects.sampleecomapp.actions.HttpGetImageAction;

import org.botlibre.sdk.config.UserConfig;
import org.botlibre.sdk.config.WebMediumConfig;
import org.botlibre.sdk.util.Utils;

import java.io.StringWriter;

/**
 * Activity for viewing a user's details.
 */
public class ViewUserActivity extends LibreActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        resetView();
		MainActivityBotLIbre.searching = false;
	}
	
	@Override
	public void onResume() {
		MainActivityBotLIbre.searching = false;
		if (MainActivityBotLIbre.user != null && MainActivityBotLIbre.user.equals(MainActivityBotLIbre.viewUser)) {
			MainActivityBotLIbre.viewUser = MainActivityBotLIbre.user;
		}
		resetView();
		super.onResume();
	}
	
	public void resetView() {
        UserConfig user = MainActivityBotLIbre.viewUser;
        if (user == null) {
        	return;
        }
        
        TextView text = (TextView) findViewById(R.id.title);
        text.setText(user.user);
        
        text = (TextView) findViewById(R.id.userText);
        text.setText(user.user);
        text = (TextView) findViewById(R.id.nameText);
        if (user.showName) {
        	text.setText(user.name);
        } else {
        	text.setVisibility(View.GONE);
        }
        text = (TextView) findViewById(R.id.websiteText);
        if (user.website == null || user.website.length() == 0) {
        	text.setVisibility(View.GONE);
        } else {
        	text.setText(user.website);
        }
        text = (TextView) findViewById(R.id.joinedText);
        text.setText("Joined " + user.displayJoined());
        text = (TextView) findViewById(R.id.connectsText);
        text.setText(user.connects + " connects");
        text = (TextView) findViewById(R.id.lastConnectText);
        text.setText("Last connected " + user.displayLastConnect());
        
        text = (TextView) findViewById(R.id.contentText);
        StringWriter writer = new StringWriter();
        if (user.bots != null && !"0".equals(user.bots)) {
        	writer.write("" + user.bots + " bots, ");
        }
        if (user.avatars != null && !"0".equals(user.avatars)) {
        	writer.write("" + user.avatars + " avatars, ");
        }
        if (user.channels != null && !"0".equals(user.channels)) {
        	writer.write("" + user.channels + " channels, ");
        }
        if (user.forums != null && !"0".equals(user.forums)) {
        	writer.write("" + user.forums + " forums, ");
        }
        if (user.domains != null && !"0".equals(user.domains)) {
        	writer.write("" + user.domains + " domains, ");
        }
        if (user.scripts != null && !"0".equals(user.scripts)) {
        	writer.write("" + user.scripts + " scripts, ");
        }
        if (user.graphics != null && !"0".equals(user.graphics)) {
        	writer.write("" + user.graphics + " graphics");
        }
        text.setText(writer.toString());
        
        text = (TextView) findViewById(R.id.statsText);
        text.setText(user.posts + " posts, " + user.messages + " messages");
        
        text = (TextView) findViewById(R.id.typeText);
        text.setText(user.type + " account");
        
        WebView web = (WebView) findViewById(R.id.bioText);
        web.loadDataWithBaseURL(null, Utils.formatHTMLOutput(user.bio), "text/html", "utf-8", null);        
        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
            	try {
            		view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            	} catch (Exception failed) {
            		return false;
            	}
                return true;
            }
        });

        if (!user.isFlagged) {
	        findViewById(R.id.flaggedLabel).setVisibility(View.GONE);
        } else {
	        findViewById(R.id.imageView).setVisibility(View.GONE);        	
        }
        
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageActivity.image = MainActivityBotLIbre.viewUser.avatar;
		        Intent intent = new Intent(ViewUserActivity.this, ImageActivity.class);
		        startActivity(intent);
			}
		});
        
        HttpGetImageAction.fetchImage(this, MainActivityBotLIbre.viewUser.avatar, (ImageView)findViewById(R.id.icon));
        HttpGetImageAction.fetchImage(this, MainActivityBotLIbre.viewUser.avatar, (ImageView)findViewById(R.id.imageView));
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_view_user, menu);
        return true;
    }
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
        for (int index = 0; index < menu.size(); index++) {
    	    menu.getItem(index).setEnabled(true);        	
        }
        if (MainActivityBotLIbre.user == null || MainActivityBotLIbre.user == MainActivityBotLIbre.viewUser) {
    	    MenuItem item = menu.findItem(R.id.menuFlag);
    	    if (item != null) {
    	    	item.setEnabled(false);
    	    }
        }
        if (MainActivityBotLIbre.user != MainActivityBotLIbre.viewUser) {
    	    menu.findItem(R.id.menuChangeIcon).setEnabled(false);
    	    menu.findItem(R.id.menuEditUser).setEnabled(false);
        }
	    return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
	    case R.id.menuChangeIcon:
	    	changeIcon();
	        return true;
	    case R.id.menuEditUser:
	    	editUser();
	        return true;
	    case R.id.menuFlag:
	    	flag();
	        return true;
	    case R.id.menuBots:
	    	browseBots();
	        return true;
	    case R.id.menuForums:
	    	browseForums();
	        return true;
	    case R.id.menuChannels:
	    	browseChannels();
	        return true;
	    case R.id.menuGraphics:
	    	browseGraphics();
	    	return true;
	    case R.id.menuDomains:
	    	browseDomains();
	        return true;
	    case R.id.menuPosts:
	    	browsePosts();
	        return true;
        case R.id.website:
        	openWebsite();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	public void openWebsite() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivityBotLIbre.WEBSITE + "/login?view-user=" + MainActivityBotLIbre.viewUser.user));
		startActivity(intent);
	}

	public void browseBots() {
	}

	public void browseForums() {
	}

	public void browseChannels() {
	}
	
	public void browseGraphics() {
	}

	public void browseDomains() {
	}

	public void browsePosts() {
	}

	public void editUser() {
        Intent intent = new Intent(this, EditUserActivity.class);
        startActivity(intent);
	}

	public void flag() {
        if (MainActivityBotLIbre.user == null) {
        	MainActivityBotLIbre.showMessage("You must sign in to flag a user", this);
        	return;
        }
        final EditText text = new EditText(this);
        MainActivityBotLIbre.prompt("Enter reason for flagging the user", this, text, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            WebMediumConfig instance = MainActivityBotLIbre.instance.credentials();
	            instance.flaggedReason = text.getText().toString();
	            if (instance.flaggedReason.trim().length() == 0) {
	            	MainActivityBotLIbre.error("You must enter a valid reason for flagging the user", null, ViewUserActivity.this);
	            	return;
	            }
	            
	            HttpAction action = new HttpFlagUserAction(ViewUserActivity.this, MainActivityBotLIbre.viewUser);
	        	action.execute();
	        }
        });
	}

	public void changeIcon() {
		Intent upload = new Intent(Intent.ACTION_GET_CONTENT);
		upload.setType("image/*");
		startActivityForResult(upload, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		try {
			String file = MainActivityBotLIbre.getFilePathFromURI(this, data.getData());
			HttpAction action = new HttpChangeUserIconAction(this, file, MainActivityBotLIbre.user);
			action.execute().get();
    		if (action.getException() != null) {
    			throw action.getException();
    		}
		} catch (Exception exception) {
			MainActivityBotLIbre.error(exception.getMessage(), exception, this);
			return;
		}
	}
	
	public void menu(View view) {
		PopupMenu popup = new PopupMenu(this, view);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.menu_view_user, popup.getMenu());
	    onPrepareOptionsMenu(popup.getMenu());
	    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
	        @Override
	        public boolean onMenuItemClick(MenuItem item) {
	            return onOptionsItemSelected(item);
	        }
	    });
	    popup.show();
	}
}
