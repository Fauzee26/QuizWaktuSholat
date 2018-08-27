package fauzi.hilmy.quizwaktusholat.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Shalat implements Parcelable {
    private String state;
    private String country;
    private String country_code;
    private String date;
    private String subuh;
    private String syuruq;
    private String zuhur;
    private String ashar;
    private String maghrib;
    private String isya;

    public Shalat(JSONObject jsonObject) {
        try {
            String state = jsonObject.getString("state");
            String country = jsonObject.getString("country");
            String country_code = jsonObject.getString("country_code");
            JSONObject item = jsonObject.getJSONObject("items");
            String date = item.getString("date_for");
            String subuh = item.getString("fajr");
            String syuruq = item.getString("shurooq");
            String zuhur = item.getString("dhuhr");
            String ashar = item.getString("asr");
            String maghrib = item.getString("maghrib");
            String isya = item.getString("isha");

            this.state = state;
            this.country = country;
            this.country_code = country_code;
            this.date = date;
            this.subuh = subuh;
            this.syuruq = syuruq;
            this.zuhur = zuhur;
            this.ashar = ashar;
            this.maghrib = maghrib;
            this.isya = isya;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getDate() {
        return date;
    }

    public String getSubuh() {
        return subuh;
    }

    public String getSyuruq() {
        return syuruq;
    }

    public String getZuhur() {
        return zuhur;
    }

    public String getAshar() {
        return ashar;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public String getIsya() {
        return isya;
    }

    protected Shalat(Parcel in) {
        state = in.readString();
        country = in.readString();
        country_code = in.readString();
        date = in.readString();
        subuh = in.readString();
        syuruq = in.readString();
        zuhur = in.readString();
        ashar = in.readString();
        maghrib = in.readString();
        isya = in.readString();
    }

    public static final Creator<Shalat> CREATOR = new Creator<Shalat>() {
        @Override
        public Shalat createFromParcel(Parcel in) {
            return new Shalat(in);
        }

        @Override
        public Shalat[] newArray(int size) {
            return new Shalat[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(state);
        dest.writeString(country);
        dest.writeString(country_code);
        dest.writeString(date);
        dest.writeString(subuh);
        dest.writeString(syuruq);
        dest.writeString(zuhur);
        dest.writeString(ashar);
        dest.writeString(maghrib);
        dest.writeString(isya);
    }
}
