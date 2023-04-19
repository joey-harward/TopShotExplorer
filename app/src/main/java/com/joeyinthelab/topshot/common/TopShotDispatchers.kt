package com.joeyinthelab.topshot.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: TopShotDispatchers)

enum class TopShotDispatchers {
    IO,
}
