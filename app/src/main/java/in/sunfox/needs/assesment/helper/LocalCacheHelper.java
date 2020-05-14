package in.sunfox.needs.assesment.helper;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalCacheHelper {

    private static final String KEY_PIN_CODES = "KEY_PIN_CODES";
    private static final String KEY_PANCHAYATS = "KEY_PANCHAYATS";
    private static final String KEY_MANDALS = "KEY_MANDALS";

    public static void updatePinCodesList(Context context, ArrayList<String> pinCodes){
        StringBuilder csvPinCodeList = new StringBuilder();
        for(String s : pinCodes){
            csvPinCodeList.append(s);
            csvPinCodeList.append(",");
        }
        SharedPreferenceHelper.setSharedPreferenceString(context,KEY_PIN_CODES,csvPinCodeList.toString());
    }

    public static List<String> getPinCodes(Context context){
        String csvPinCodeList = SharedPreferenceHelper.getSharedPreferenceString(context,KEY_PIN_CODES,"");
        String[] items = csvPinCodeList.split(",");
        return new ArrayList<String>(Arrays.asList(items));
    }

    public static void updatePanchayatsList(Context context,ArrayList<String> panchayats){
        StringBuilder csvPanchayatList = new StringBuilder();
        for(String s : panchayats){
            csvPanchayatList.append(s);
            csvPanchayatList.append(",");
        }
        SharedPreferenceHelper.setSharedPreferenceString(context,KEY_PANCHAYATS,csvPanchayatList.toString());
    }

    public static List<String> getPanchayatsList(Context context){
        String csvPanchayatsList = SharedPreferenceHelper.getSharedPreferenceString(context,KEY_PANCHAYATS,"");
        String[] items = csvPanchayatsList.split(",");
        return new ArrayList<String>(Arrays.asList(items));
    }

    public static void updateMandalsList(Context context,ArrayList<String> mandals){
        StringBuilder csvMandalList = new StringBuilder();
        for(String s : mandals){
            csvMandalList.append(s);
            csvMandalList.append(",");
        }
        SharedPreferenceHelper.setSharedPreferenceString(context,KEY_MANDALS,csvMandalList.toString());
    }

    public static ArrayList<String> getMandalsList(Context context){
        String csvMandalList = SharedPreferenceHelper.getSharedPreferenceString(context,KEY_MANDALS,"");
        String[] items = csvMandalList.split(",");
        return new ArrayList<String>(Arrays.asList(items));
    }

    public static ArrayList<String> getGendersList(Context context){
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");
        return genders;
    }

}
