package rx.mvp.demo

import android.app.Application
import rx.mvp.MVP

/**
 * @author justin on 2019/02/22 15:46
 * @version V1.0
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MVP.init(this)
    }
}