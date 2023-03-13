package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.notification.Notification
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime


class NotificationAdapter(val context: Context, private val notificationData: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notifyimage: ImageView = itemView.findViewById(R.id.notifyImage_IV)
        val title: TextView = itemView.findViewById(R.id.notifyMsg_TV)
        val duration: TextView = itemView.findViewById(R.id.msgDuration_TV)
        val root: CardView = itemView.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_tab_seen, parent, false)
            return ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_tab_unseen, parent, false)
            return ViewHolder(view)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationData = notificationData[position]
        val imageUrlModification = notificationData.notificationImage.replaceRange(4, 4, "s")
        val imgUrl = Uri.parse(imageUrlModification)
        Picasso.get().load(imgUrl).into(holder.notifyimage)
        holder.title.text = notificationData.notificationText
        holder.duration.text = notificationData.createdOn

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val currentTime1 = format.parse(LocalDateTime.now().toString())
        val notifyTime1 = format.parse(notificationData.createdOn)
        val diff = currentTime1.time - notifyTime1.time

        if (diff / (24 * 60 * 60 * 1000) > 0) {
            if (diff / (24 * 60 * 60 * 1000) == 1L) {
                holder.duration.text = (diff / (24 * 60 * 60 * 1000)).toString() + " Day ago"
            }
            holder.duration.text = (diff / (24 * 60 * 60 * 1000)).toString() + " Days ago"
        } else if (diff / (60 * 60 * 1000) > 0) {
            holder.duration.text = (diff / (60 * 60 * 1000)).toString() + " Hours ago"
        } else if (diff / (60 * 1000) > 0) {
            holder.duration.text = (diff / (60 * 1000)).toString() + " Minutes ago"
        } else {
            holder.duration.text = (diff / (1000)).toString() + " Seconds ago"
        }
    }

    override fun getItemCount(): Int {
        return notificationData.size
    }

    override fun getItemViewType(position: Int): Int {
        val notificationData = notificationData[position]
        if (notificationData.viewStatus == true) {
            return 0
        } else {
            return 1
        }
    }

}



