package mobileapp.ctemplar.com.ctemplarapp.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.webkit.MimeTypeMap;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import mobileapp.ctemplar.com.ctemplarapp.R;
import timber.log.Timber;

public class AppUtils {
    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }

        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                return "";
            }
        } catch (MalformedURLException e) {
            Timber.e(e);
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();
        int lastQMPos = url.lastIndexOf('?');
        int lastHashPos = url.lastIndexOf('#');

        if (lastQMPos == -1) {
            lastQMPos = length;
        }
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        int endIndex = Math.min(lastQMPos, lastHashPos);
        try {
            return URLDecoder.decode(url.substring(startIndex, endIndex), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(milliseconds);
        }
    }

    public static void setSystemClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(text, text));
        ToastUtils.showToast(context, R.string.success_copy_to_clipboard);
    }

    private boolean noConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE);
    }
}
