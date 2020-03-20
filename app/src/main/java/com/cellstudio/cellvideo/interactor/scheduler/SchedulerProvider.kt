package com.cellstudio.cellvideo.interactor.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler

    fun ui(): Scheduler
}