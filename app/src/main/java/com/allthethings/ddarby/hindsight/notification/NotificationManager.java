package com.allthethings.ddarby.hindsight.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.allthethings.ddarby.hindsight.R;
import com.allthethings.ddarby.hindsight.activity.HomeActivity;
import com.allthethings.ddarby.hindsight.model.Pomodoro;
import com.allthethings.ddarby.hindsight.model.Task;

public class NotificationManager {

    public static NotificationManager instance;

    private NotificationManager() {
        // additional initialization
    }

    public NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }

        return instance;
    }

    /**
     * Returns application icon as a bitmap
     * @param context to create notification
     * @return Bitmap
     */
    private Bitmap getApplicationBitmapIcon(Context context) {
        return ((BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
    }

    /**
     * Handles alerting users for notification of a pomodoro
     * @param context to create notification
     * @param pomodoro to be used for the notification
     */
    public void sendPomodoroNotification(Context context, Pomodoro pomodoro) {
        NotificationCompat.Builder notificationBuilder = getPomodoroNotificationBuilder(context, pomodoro);
        Notification tasksNotification = getTasksNotification(context, pomodoro);
        WearableNotifications.Builder wearNotification = new WearableNotifications.Builder(notificationBuilder);
        if (tasksNotification != null) {
            wearNotification.addPage(tasksNotification);
        }
        wearNotification.addAction(getDeleteNotificationAction(context, pomodoro));

        notify(context, pomodoro.getId(), wearNotification.build());
    }

    /**
     * Handles sending an unknown notification issued by another operation
     * @param context to create notification
     * @param notificationId to be used for the notification
     * @param title to be used for the notification
     * @param description text to be used for the notification
     * @param intent to be launched when notification is clicked
     */
    public void sendNotification(Context context, int notificationId, String title, String description, Intent intent) {
        sendNotification(context, notificationId, title, description, intent, false);
    }

    /**
     * Handles sending an unknown notification issued by another operation
     * @param context to create notification
     * @param notificationId to be used for the notification
     * @param title to be used for the notification
     * @param description text to be used for the notification
     * @param intent to be launched when notification is clicked
     * @param playSound boolean determines if sound plays on notification
     */
    public void sendNotification(Context context, int notificationId, String title, String description, Intent intent, boolean playSound) {
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, notificationId, intent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(getApplicationBitmapIcon(context))
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setContentIntent(viewPendingIntent);

        if (playSound) {
            notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }
        notify(context, notificationId, notificationBuilder.build());
    }

    /**
     * Handles sending notification through the notification manager
     * @param context to create notification
     * @param notificationId to be used for the notification
     * @param notification to be notified
     */
    public void notify(Context context, int notificationId, Notification notification) {
        NotificationManagerCompat.from(context).notify(notificationId, notification);
    }

    /**
     * Returns a NotificationCompat.Builder created from a single UIListing
     * @param context to create notification
     * @param pomodoro to be used for the notification
     * @return NotificationCompat.Builder
     */
    private NotificationCompat.Builder getPomodoroNotificationBuilder(Context context, Pomodoro pomodoro) {
        Bitmap largePhoto;
//        (pomodoro.getImage() != null) {
//            largePhoto = //load image
//        }

        largePhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_photo_not_avail_small);

        Intent viewIntent = new Intent(context, HomeActivity.class);
        viewIntent.putExtra("pomodoro", pomodoro);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, pomodoro.getId(), viewIntent, 0);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(getApplicationBitmapIcon(context))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(largePhoto))
                .setContentTitle(pomodoro.getTitle())
                .setContentText(pomodoro.getTimestamp().toString())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

    /**
     * Returns a Notification based upon the passed UIListing's floorplan selection
     * @param context to create notification
     * @param task to be used for the notification
     * @return Notification
     */
    private Notification getTaskNotification(Context context, Task task) {
        if (task != null) {
            NotificationCompat.InboxStyle taskStyle = new NotificationCompat.InboxStyle();

            taskStyle.addLine(task.getTitle());
            taskStyle.addLine(task.getTimestamp().toString());
            taskStyle.setBigContentTitle(task.getTodo());
            return new NotificationCompat.Builder(context)
                    .setStyle(taskStyle)
                    .build();
        }

        return null;
    }

    /**
     * Returns a Notification based upon the passed UIListing's floorplan selection
     * @param context to create notification
     * @param pomodoro to be used for the notification
     * @return Notification
     */
    private Notification getTasksNotification(Context context, Pomodoro pomodoro) {
        if (pomodoro != null && pomodoro.getPomodoroTasks().size() > 0) {
            NotificationCompat.InboxStyle taskStyle = new NotificationCompat.InboxStyle();

            taskStyle.addLine(pomodoro.getTitle());
            taskStyle.setBigContentTitle("Total Tasks: " + pomodoro.getPomodoroTasks().size());
            return new NotificationCompat.Builder(context)
                    .setStyle(taskStyle)
                    .build();
        }

        return null;
    }

    /**
     * Returns a Notification based upon the passed UIListing's floorplan selection
     * @param context to create notification
     * @param pomodoro to be used for the notification
     * @return WearableNotifications.Action
     */
    private WearableNotifications.Action getDeleteNotificationAction(Context context, Pomodoro pomodoro) {

        Intent viewIntent = new Intent(context, HomeActivity.class);
        viewIntent.putExtra("pomodoroDelete", pomodoro);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, pomodoro.getId(), viewIntent, 0);
        return new WearableNotifications.Action.Builder(android.R.drawable.ic_menu_delete, "Delete", pendingIntent).build();
    }
}
