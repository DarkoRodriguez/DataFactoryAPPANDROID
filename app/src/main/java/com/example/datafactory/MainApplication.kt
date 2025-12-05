package com.example.datafactory

import android.app.Application
import com.example.datafactory.data.local.AppDatabase

class MainApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}