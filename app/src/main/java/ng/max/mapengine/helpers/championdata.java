package ng.max.mapengine.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by babatundedennis on 4/11/16.
 */
public class championdata {

    public JSONArray ikeja(){
        JSONArray champions =new JSONArray();

        try{
            JSONObject champion1 =new JSONObject();
            JSONObject champion2 =new JSONObject();
            JSONObject champion3 =new JSONObject();

            champion1.put("id", "08292");
            champion1.put("name", "Victor Asampe");
            champion1.put("lat", 6.612163);
            champion1.put("lng", 3.345423);
            champion1.put("phone", "+2348066257276");

            champion2.put("id", "08292");
            champion2.put("name", "Perry WonderKid");
            champion2.put("lat", 6.609803);
            champion2.put("lng", 3.346119);
            champion2.put("phone", "+2348066257276");

            champion3.put("id", "08292");
            champion3.put("name", "Yusuf Babalola");
            champion3.put("lat", 6.626264);
            champion3.put("lng", 3.346336);
            champion3.put("phone", "+2348066257276");

            champions.put(champion1);
            champions.put(champion2);
            champions.put(champion3);

        }
        catch(JSONException ex)
        {

        }

        return champions;
    }
}
