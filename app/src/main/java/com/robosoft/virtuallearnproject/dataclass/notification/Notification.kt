package com.robosoft.virtuallearnproject.dataclass.notification

data class Notification(
    val _id: String,
    val createdOn: String,
    val notificationImage: String,
    val notificationText: String,
    val viewStatus: Boolean
)