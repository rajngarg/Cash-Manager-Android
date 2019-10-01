package pvtltd.ecodinghub.com.cashmanager.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceHelper {


    public SharedPreferenceHelper(Context context) {

    }

    public static String getJobProfile(Context context) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        return preference.getString("jobProfile", "Set Job Profile");
    }

    public static void setJobProfile(Context context, String jobProfile) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("jobProfile", jobProfile);
        editor.apply();
    }

    public static int getJobSalary(Context context) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        return preference.getInt("jobSalary", 0);
    }

    public static void setJobSalary(Context context, int salary) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt("jobSalary", salary);
        editor.apply();
    }

    public static int getCash(Context context) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        return preference.getInt("cash", 0);
    }

    public static void setCash(Context context, int cash) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt("cash", cash + preference.getInt("cash", 0));
        editor.apply();
    }

    public static void subtractFromCash(Context context, int value) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt("cash", preference.getInt("cash", 0) - value);
        editor.apply();
    }

    public static void addIntoCash(Context context, int value) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt("cash", preference.getInt("cash", 0) + value);
        editor.apply();
    }

    public static void deleteData(Context context) {
        SharedPreferences preference = context.getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.apply();
    }
}
