package ng.max.mapengine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ng.max.mapengine.adapters.SettingsCombo1Adapter;
import ng.max.mapengine.utils.Constants;


public class UserSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        setTitle("Tunde Akinnuwa");

        ListView combo1 = (ListView)findViewById(R.id.settings_combo_1);

        String[] titles = new String[] {"Profile", "History","Settings", "Share MAX", "Feedback & Support"};

        String[] descriptions = new String[] {
                "Edit your profile",
                "Check your order history",
                "Set your preferences",
                "Sharing is caring",
                "Help us improve. Tell us what you think.",
        };

        String[] icons = new String[] {
                String.valueOf((char) 0xe908),
                String.valueOf((char) 0xe900),
                String.valueOf((char) 0xe901),
                String.valueOf((char) 0xe90c),
                String.valueOf((char) 0xe90b)
        };


        final SettingsCombo1Adapter adapter_combo_1 = new SettingsCombo1Adapter(this,icons,titles,descriptions);
        combo1.setAdapter(adapter_combo_1);
        combo1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, final View view,
                                   int position, long id) {
               if (position == 0) {
                   Intent sendIntent = new Intent();
                   sendIntent.setAction(Intent.ACTION_SEND);
                   sendIntent.putExtra(Intent.EXTRA_TEXT, "Download LWKMD (Laugh Wan Kill Me Die) For Android. Click " + Constants.DOWNLOAD_LINK);
                   sendIntent.setType("text/plain");
                   startActivity(sendIntent);
               } else if (position == 1) {
                   Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                           "mailto", Constants.SUPPORT_EMAIL, null));
                   startActivity(Intent.createChooser(emailIntent, "Contact MAX"));
               }

            }

       });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_user_settings, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
