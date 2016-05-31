package ng.max.mapengine.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ng.max.mapengine.AddressTownListener;
import ng.max.mapengine.R;

/**
 * Created by babatundedennis on 4/13/16.
 */
public class Utils {

    public void onNotify(String s, Context context){
        LayoutInflater li = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View layout = li.inflate(R.layout.on_notify,null);
        TextView text = (TextView) layout.findViewById(R.id.custom_toast_message);
        text.setText(s);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0, 1);
        toast.setView(layout);//setting the view of custom toast layout
        toast.show();
    }

    public boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null){
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    public Dialog Waiting(final Activity activity, final String text){
        final Dialog dialog = new Dialog(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.progress_bar);

                dialog.setCancelable(false);

                TextView splash_text =  (TextView)dialog.findViewById(R.id.splash_text);
                splash_text.setText(text);

            }
        });

        return dialog;
    }

    private AddressTownListener callback;

    public synchronized String [] AddressTownPopUp(final Activity activity){
        callback = (AddressTownListener) activity;

        final String[] address_town = new String[2];

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog popup_caption = new Dialog(activity);
                popup_caption.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                popup_caption.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                popup_caption.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                popup_caption.setContentView(R.layout.upload_popup);
                popup_caption.setCancelable(true);

                Window window = popup_caption.getWindow();
                window.setGravity(Gravity.TOP);

                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);



                Spinner spinner = (Spinner) popup_caption.findViewById(R.id.town_spinner);
                String[] town_list = new String[]{
                        "Select a town", "Ajah", "Ayobo"
                };

                final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        activity, R.layout.town_item, town_list) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        }
                        else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };

                final EditText address = (EditText)popup_caption.findViewById(R.id.popupAddress);

                spinnerArrayAdapter.setDropDownViewResource(R.layout.town_item);
                spinner.setAdapter(spinnerArrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        // If user change the default selection
                        // First item is disable and it is used for hint
                        if (position > 0) {
                            // Notify the selected item text
                            address_town[0] = (String) parent.getItemAtPosition(position);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                Button uploadButton = (Button) popup_caption.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                uploadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        address_town[1] = address.getText().toString();
                        callback.AddressTown(address_town);
                        //notify();
                    }
                });



                popup_caption.show();

            }
        });
        /*try{
            wait();
        }
        catch (InterruptedException e){

        }
        */
        return address_town;
    }




}
