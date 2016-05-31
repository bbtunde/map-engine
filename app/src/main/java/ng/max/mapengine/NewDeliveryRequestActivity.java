package ng.max.mapengine;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class NewDeliveryRequestActivity extends AppCompatActivity {
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String ADDRESS = "address";
    public static final String TOWN = "town";

    private String sender_address;
    private String sender_town;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.ConOver, true);
        setContentView(R.layout.activity_new_delivery_request);

        Intent intent = getIntent();
        sender_address = intent.getStringExtra(ADDRESS);
        sender_town = intent.getStringExtra(TOWN);

        TextView address_et = (TextView)findViewById(R.id.address);
        address_et.setText(sender_address);

        TextView edit_address = (TextView)findViewById(R.id.edit_address);

        edit_address.setText(String.valueOf((char) 0xe90d));
        edit_address.setTypeface(Typeface.createFromAsset(getAssets(), "icomoon.ttf"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_new_delivery_request, menu);
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
