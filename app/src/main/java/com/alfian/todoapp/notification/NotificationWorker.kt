package com.alfian.todoapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.alfian.todoapp.R
import com.alfian.todoapp.data.Task
import com.alfian.todoapp.data.TaskRepository
import com.alfian.todoapp.ui.detail.DetailTaskActivity
import com.alfian.todoapp.utils.DateConverter
import com.alfian.todoapp.utils.NOTIFICATION_CHANNEL_ID
import com.alfian.todoapp.utils.TASK_ID

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val channelName = inputData.getString(NOTIFICATION_CHANNEL_ID)

    private fun getPendingIntent(task: Task): PendingIntent? {
        val intent = Intent(applicationContext, DetailTaskActivity::class.java).apply {
            putExtra(TASK_ID, task.id)
        }
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    override fun doWork(): Result {
        //TODO 14 : If notification preference on, get nearest active task from repository and show notification with pending intent    //Done

        val data = TaskRepository.getInstance(applicationContext)
        val task = data.getNearestActiveTask()
        val pendingIntent = getPendingIntent(task) as PendingIntent

        val title = task.title
        val dueDate = DateConverter.convertMillisToString(task.dueDateMillis)

        showNotification(title, applicationContext.getString(R.string.notify_content, dueDate), pendingIntent)

        return Result.success()
    }

    private fun showNotification(title: String, description: String?, pendingIntent: PendingIntent) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(1, notification.build())
    }

}
