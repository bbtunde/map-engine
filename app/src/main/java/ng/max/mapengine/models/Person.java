package ng.max.mapengine.models;

/**
 * Created by babatundedennis on 4/14/16.
 */
class Person {
    String name;
    String phone;
    String address;
    String lat;
    String lng;
    String town;
    int userId;

    Person(int user_id, String name, String phone, String address, String lat, String lng, String town) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.town = town;
        this.userId = user_id;
    }

    public void setUserID(int id){
        this.userId = id;
    }

    public int getUserID(){
        return this.userId;
    }

    public void setPhone(String phone){
        this.phone =phone;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setLat(String lat){
        this.lat = lat;
    }

    public String getLat(){
        return this.lat;
    }

    public void setLng(String lng){
        this.lng = lng;
    }

    public String getLng(){
        return this.lng;
    }

    public String getTown(){
        return this.town;
    }

    public void setTown(String town){
        this.town = town;
    }


}


