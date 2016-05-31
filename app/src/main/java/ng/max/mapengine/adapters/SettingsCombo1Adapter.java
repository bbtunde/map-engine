package ng.max.mapengine.adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ng.max.mapengine.R;

/**
 * Created by babatundedennis on 4/12/16.
 */
public class SettingsCombo1Adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] icons,names;
    private final String[] description;
    private
    static class ViewHolder {
        public TextView text;
        public TextView desc, icon;

    }

    public SettingsCombo1Adapter(Activity context, String[] icons, String[] names, String[] desc) {
        super(context, R.layout.activity_user_settings, names);
        this.context = context;
        this.icons = icons;
        this.names = names;
        this.description = desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.settings_combo1, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon = (TextView) rowView.findViewById(R.id.userIcon);
            viewHolder.text = (TextView) rowView.findViewById(R.id.aboutListTitle);
            viewHolder.desc = (TextView) rowView.findViewById(R.id.aboutListDesc);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.icon.setText(icons[position]);
        holder.icon.setTypeface(Typeface.createFromAsset(context.getAssets(), "icomoon.ttf"));
        holder.text.setText(names[position]);
        holder.desc.setText(description[position]);

        return rowView;
    }
}

