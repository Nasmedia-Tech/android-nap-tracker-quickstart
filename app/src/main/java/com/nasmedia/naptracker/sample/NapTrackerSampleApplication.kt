package com.nasmedia.naptracker.sample

import android.app.Application
import com.nasmedia.naptracker.presentation.NapTracker

class NapTrackerSampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        NapTracker.initialize(this)
    }
}

