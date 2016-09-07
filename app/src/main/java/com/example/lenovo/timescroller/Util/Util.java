package com.example.lenovo.timescroller.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wanghuan
 * @date 2014年10月21日 上午10:39:15
 * @email hunter.v.wang@gmail.com
 */
public class Util {



//    public final static boolean isValidPhone(CharSequence target) {
//        return !isEmpty(target) && Patterns..matcher(target).matches();
//    }

    /**
     * 判断字符串是否为空 空返回""
     *
     * @param text
     * @return
     */
    public static String isEmptyString(String text) {
        if (text == null || text.equals("")) return "";
        return text;
    }

    /**
     * 判断字符串是否为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        if (text == null || text.equals("")) return true;
        return false;
    }

    /**
     * 字符串是否为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(Object text) {
        if (text == null) return true;
        return isEmpty(text.toString());
    }

    /**
     * 判断字符串是否为空 若为空 返回[
     *
     * @param text
     * @return
     */
    public static String isContactsEmpty(String text) {
        if (text == null || text.equals("")) return "[";
        return text;
    }

    /**
     * 手机号码格式 11 位
     *
     * @param text
     * @return
     */
    public static boolean isValidPhone(String text) {
        if (isEmpty(text)) {
            return false;
        }
        if (!isEmpty(text) && text.length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * 密码是否大于 6 位
     *
     * @param text
     * @return
     */
    public static boolean isValidPassword(String text) {
        if (!isEmpty(text) && text.length() >= 6) {
            return true;
        }
        return false;
    }

    public static String formatSum(float sum){
        BigDecimal bigDecimal = new BigDecimal(sum);
      return   bigDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 格式化数据，1.00=>1   1.10=>1.1   1.001=>1
     *
     * @param money
     * @return
     */
    public static String formatMoney(String money) {
        if (money == null || "".equals(money)) {
            return "";
        }
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            String numStr = df.format(new BigDecimal(money));
            if (numStr != null && numStr.indexOf(".") > 0) {
                numStr = numStr.replaceAll("0+?$", "");//去掉多余的0
                numStr = numStr.replaceAll("[.]$", "");//如最后一位是.则去掉
            }
            return Util.isEmptyString(numStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return money;
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        int width = 0;
        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();
        }
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context) {
        int height = 0;
        Display display = context.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            height = display.getHeight();
        }
        return height;
    }

    /**
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getDpi(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        int height = 0;
        @SuppressWarnings("rawtypes") Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public static int[] getScreenWH(Context poCotext) {
        WindowManager wm = (WindowManager) poCotext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        return new int[]{width, height};
    }

    public static int getVrtualBtnHeight(Context poCotext) {
        int location[] = getScreenWH(poCotext);
        int realHeiht = getDpi((Activity) poCotext);
        int virvalHeight = realHeiht - location[1];
        return virvalHeight;
    }


    /**
     * @param activity
     * @return
     */
    @TargetApi(14)
    public static boolean checkDeviceHasNavigationBar(Context activity) {
        try {
            //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
            boolean hasMenuKey = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (!hasMenuKey && !hasBackKey) {
                // 做任何你需要做的,这个设备有一个导航栏
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getPhoneModel() {
        Build build = new Build();
        return build.MODEL;
    }

    public static String getPhoneMacAddr(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获取 APP 的版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String version = "";
        if (context == null) return version;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取 APP 的版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int code = -1;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            code = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static ArrayList<String> getActivityNameList(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<String>();
        if (packInfo != null && packInfo.activities != null) {
            for (ActivityInfo info : packInfo.activities) {
                list.add(info.name);
            }
        }
        return list;
    }

    public static String getSimpleName(String name) {
        String simpleName = name;
        if (!Util.isEmpty(name) && name.contains(".")) {
            int lastIndex = name.lastIndexOf(".");
            simpleName = name.substring(lastIndex + 1, name.length());
            if (!Util.isEmpty(simpleName) && simpleName.contains("Activity")) {
                simpleName = simpleName.substring(0, simpleName.indexOf("Activity"));
            }
        }
        return simpleName;
    }


    /**
     * 获取系统类型
     *
     * @return
     */
    public static String getSystemType() {
        return "android";
    }


    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else return false;
    }

//    public static String getPackage

    /**
     * to get IMEI (international mobile equipment identifier)
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tManager.getDeviceId();
        return uuid;
    }

    /**
     * to get device unique id
     *
     * @param context
     * @return
     */
    public static String getDeviceUniqueID(Context context) {
        String device_unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    /**
     * 可不用
     *
     * @return
     */
    public static String getAndroid_RELEASE() {
        return Build.VERSION.RELEASE;
    }

    /**
     * DP 2 PX
     *
     * @param res
     * @param dp
     * @return
     */
    public static int dpToPx(Resources res, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2spFormServer(float pxValue) {
        return (int) (pxValue / 2);
    }

    /**
     * *
     *
     * @param mobile
     * @return
     */
    public static String formatOurMobile(String mobile) {
        if (!isValidPhone(mobile)) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(mobile.substring(0, 3));
        sb.append("****");
        sb.append((mobile.substring(7, 11)));
        return sb.toString();
    }

    public static String getLastFourNumber(String mobile) {
        return mobile.substring(7, 11);
    }

    public static String getMetaData(Context context, String key) {
        String metaDataValue = "";
        if (isEmpty(key)) return metaDataValue;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            metaDataValue = bundle.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metaDataValue;
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @param resType String, such as "drawable", "layout"
     * @param index   index of the resource
     * @param length  add '0', such as 01, 001..
     * @return
     */
    public static int getResId(Context context, String resType, String prefix, int index, int length) {
        String format = "%0" + length + "d";
        String res = prefix + String.format(format, index);
        return context.getResources().getIdentifier(res, resType, context.getPackageName());
    }



    /**
     * 获得指定字节长度的字符串
     *
     * @param inputStr
     * @return ArrayList<Object>:[0]: String result; [1]: Count of Ordinary Chars [2]:Count of Chinese Chars
     */
    public static ArrayList<Object> getLimitSubstring(String inputStr, int length) {
        if (inputStr == null) {
            inputStr = "";
        }
        ArrayList<Object> result = new ArrayList<Object>();
        int orignLen = inputStr.length();
        int resultChs = 0;
        int resultOth = 0;
        String temp = null;
        for (int i = 0; i < orignLen; i++) {
            temp = inputStr.substring(i, i + 1);
            try {
                if (temp.getBytes("utf-8").length == 3) {
                    resultChs += 2;
                } else {
                    resultOth++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ((resultChs + resultOth) > length) {
                result.add(inputStr.substring(0, i));
                result.add(resultOth);
                result.add(resultChs);
                return result;
            }
        }
        result.add(inputStr);
        result.add(resultOth);
        result.add(resultChs);
        return result;
    }

    /**
     * 对象转换
     *
     * @return
     */
    public static <T> T getTransform(Object sourceObj, Class<T> targetCls) {
        if (sourceObj == null) {
            return null;
        }
        Gson gson = new Gson();
        String json = gson.toJson(sourceObj);
        if (isEmpty(json)) {
            return null;
        }
        T targetObj = null;
        try {
            targetObj = gson.fromJson(json, targetCls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetObj;
    }

    /**
     * 获取AndroidMainest的Activity配置
     *
     * @param context
     * @param activityClsName
     * @return
     */
    public static String getActivityClsName(Context context, String activityClsName) {
        if (context == null || activityClsName == null) {
            return null;
        }
        List<String> activityNameList = getActivityNameList(context);
        if (activityNameList == null || activityNameList.size() == 0) {
            return null;
        }
        for (String name : activityNameList) {
            if (!isEmpty(name) && name.contains(".")) {
                int lastIndex = name.lastIndexOf(".");
                String simpleName = name.substring(lastIndex + 1, name.length());
                if (simpleName.endsWith("_")) {
                    simpleName = simpleName.substring(0, simpleName.length() - 1);
                }
                if (simpleName.equals(activityClsName)) {
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * 获取Intent
     *
     * @param context
     * @param activityClsName
     * @param bundle
     * @return
     */
    private static Intent getIntent(Context context, String activityClsName, Bundle bundle) {
        if (context == null || activityClsName == null) {
            return null;
        }
        activityClsName = getActivityClsName(context, activityClsName);
        if (activityClsName == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setClassName(context, activityClsName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }

    /**
     * 启动AndroidMainest.xml内的Activity
     *
     * @param context
     * @param activityClsName
     * @param bundle
     */
    public static void startActivity(Context context, String activityClsName, Bundle bundle) {
        Intent intent = getIntent(context, activityClsName, bundle);
        if (intent == null) {
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 启动AndroidMainest.xml的Activity
     *
     * @param context
     * @param activityClsName
     * @param bundle
     */
    public static void startActivity(Context context, String activityClsName, Bundle bundle, int requestCode) {
        Intent intent = getIntent(context, activityClsName, bundle);
        if (intent == null) {
            return;
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    /**
     * 启动AndroidMainest.xml的Activity
     *
     * @param activityClsName
     * @param bundle
     */
    public static void startActivity(Fragment fragment, String activityClsName, Bundle bundle, int requestCode) {
        Intent intent = getIntent(fragment.getActivity(), activityClsName, bundle);
        if (intent == null) {
            return;
        }
        fragment.startActivityForResult(intent, requestCode);
    }

    public static String objectToJson (Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public  static Object jsonToObject (String result , Class<?> className){
        Gson gson = new Gson();
        return gson.fromJson(result,className);
    }

}
