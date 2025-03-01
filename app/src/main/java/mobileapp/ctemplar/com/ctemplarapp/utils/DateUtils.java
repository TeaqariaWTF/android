package mobileapp.ctemplar.com.ctemplarapp.utils;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mobileapp.ctemplar.com.ctemplarapp.R;
import mobileapp.ctemplar.com.ctemplarapp.repository.provider.MessageProvider;

public class DateUtils {
    private static final String SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    private static final String MAIN_TIME_PATTERN = "h:mm aa";
    private static final String MAIN_DATE_PATTERN = "E, dd MMM yyyy";
    private static final String MAIN_MONTH_PATTERN = "MMM d";
    private static final String MAIN_YEAR_PATTERN = "MMM d, yyyy";

    private static final String MESSAGE_FULL_DATE_PATTERN = "MMM d, yyyy',' h:mm a";
    private static final String EMAIL_PATTERN = "EEE',' MMMM d, yyyy 'at' h:mm a";
    private static final String FILTER_DATE_PATTERN = "yyyy-MM-dd";

    private static final String ELAPSED_TIME_FORMAT = "%2dd %02d:%02d";
    private static final String ELAPSED_TIME_SHORT_FORMAT = "%02d:%02d";

    public static final Gson GENERAL_GSON = new GsonBuilder()
            .setDateFormat(SERVER_DATE_PATTERN)
            .create();

    @NotNull
    public static String displayMessageDate(@Nullable Date date, Resources resources) {
        if (date == null) {
            return "";
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateFormat timeFormat = new SimpleDateFormat(MAIN_TIME_PATTERN, Locale.getDefault());
        DateFormat monthFormat = new SimpleDateFormat(MAIN_MONTH_PATTERN, Locale.getDefault());
        DateFormat yearFormat = new SimpleDateFormat(MAIN_YEAR_PATTERN, Locale.getDefault());

        if (nowCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            if (nowCalendar.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
                return timeFormat.format(date);
            } else if (nowCalendar.get(Calendar.DATE) - calendar.get(Calendar.DATE) == 1) {
                return resources.getString(R.string.txt_yesterday);
            } else {
                return monthFormat.format(date);
            }
        }
        return yearFormat.format(date);
    }

    @NotNull
    public static String messageFullDate(@Nullable Date date) {
        if (date == null) {
            return "";
        }
        DateFormat messageFullDateFormat = new SimpleDateFormat(MESSAGE_FULL_DATE_PATTERN, Locale.getDefault());
        return messageFullDateFormat.format(date);
    }

    @NotNull
    public static String simpleDate(@Nullable Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(MAIN_YEAR_PATTERN, Locale.getDefault()).format(date);
    }

    @Nullable
    public static String elapsedTime(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        long diffInMillis = date.getTime() - new Date().getTime();
        if (diffInMillis < 0) {
            return null;
        }

        long seconds = diffInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        minutes %= 60;
        hours %= 24;

        if (days <= 0) {
            return String.format(Locale.getDefault(), ELAPSED_TIME_SHORT_FORMAT, hours, minutes);
        } else {
            return String.format(Locale.getDefault(), ELAPSED_TIME_FORMAT, days, hours, minutes);
        }
    }

    @Nullable
    public static Date getDeliveryDate(@Nullable MessageProvider message) {
        if (message == null) {
            return null;
        }
        return message.getUpdatedAt().after(message.getCreatedAt()) ? message.getUpdatedAt() : message.getCreatedAt();
    }

    @Nullable
    public static String deadMansTime(long hours) {
        if (hours <= 0) {
            return null;
        }

        long days = hours / 24;
        long minutes = hours * 60;
        hours %= 24;
        minutes %= 60;

        if (days <= 0) {
            return String.format(Locale.getDefault(), ELAPSED_TIME_SHORT_FORMAT, hours, minutes);
        } else {
            return String.format(Locale.getDefault(), ELAPSED_TIME_FORMAT, days, hours, minutes);
        }
    }

    @NotNull
    public static String getStringDate(@Nullable Date date) {
        if (date == null) {
            return "";
        }
        DateFormat emailFormat = new SimpleDateFormat(EMAIL_PATTERN, Locale.getDefault());
        return emailFormat.format(date);
    }

    public static String getFilterDate(long timeInMillis) {
        return new SimpleDateFormat(FILTER_DATE_PATTERN, Locale.getDefault()).format(timeInMillis);
    }

    public static String dateFormat(long timeInMillis) {
        DateFormat dateFormat = new SimpleDateFormat(MAIN_DATE_PATTERN, Locale.getDefault());
        return dateFormat.format(timeInMillis);
    }

    public static String timeFormat(long timeInMillis) {
        DateFormat timeFormat = new SimpleDateFormat(MAIN_TIME_PATTERN, Locale.getDefault());
        return timeFormat.format(timeInMillis);
    }
}
