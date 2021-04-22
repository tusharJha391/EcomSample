package com.allandroidprojects.sampleecomapp.botlibre;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.allandroidprojects.sampleecomapp.R;

import org.botlibre.sdk.config.InstanceConfig;
import org.botlibre.sdk.config.WebMediumConfig;

public class BotActivity extends WebMediumActivity {

    public String getType() {
        return "Bot";
    }

    public void resetView() {
        setContentView(R.layout.activity_bot);

        InstanceConfig instance = (InstanceConfig)this.instance;

        super.resetView();
        if (instance == null) {
            return;
        }

        if (instance.isExternal && !instance.hasAPI) {
            findViewById(R.id.chatButton).setVisibility(View.GONE);
        }

        TextView text = (TextView) findViewById(R.id.sizeLabel);
        if (instance.size != null && instance.size.length() > 0) {
            text.setText(instance.size + " objects");
        } else {
            text.setText("");
        }

        text = (TextView) findViewById(R.id.chatbotwarsLabel);
        text.setText("" + instance.rank + " rank, " + instance.wins + " wins, " + instance.losses + " losses");
    }

    public void menu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_bot, popup.getMenu());
        onPrepareOptionsMenu(popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
        popup.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        WebMediumConfig instance = this.instance;

        boolean isAdmin = (MainActivityBotLIbre.user != null) && instance.isAdmin;
        if (!isAdmin || instance.isExternal) {
            menu.findItem(R.id.menuAdmin).setEnabled(false);
        }
        if (isAdmin || instance.isFlagged) {
            menu.findItem(R.id.menuFlag).setEnabled(false);
        }
        if (MainActivityBotLIbre.user == null || (!isAdmin && !((InstanceConfig)instance).allowForking)) {
            menu.findItem(R.id.menuFork).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuWar:
                war(null);
                return true;
            case R.id.menuAdmin:
                admin();
                return true;
            case R.id.menuFlag:
                flag();
                return true;
            case R.id.menuFork:
                fork();
                return true;
            case R.id.menuStar:
                star();
                return true;
            case R.id.menuThumbsUp:
                thumbsUp();
                return true;
            case R.id.menuThumbsDown:
                thumbsDown();
                return true;
            case R.id.website:
                openWebsite();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void admin() {
    }

    @SuppressLint("DefaultLocale")
    public void fork() {
    }

    /**
     * Start a chat session with the selected instance and the user.
     */
    public void openChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    /**
     * Start a chat bot war.
     */
    public void war(View view) {
    }

}
