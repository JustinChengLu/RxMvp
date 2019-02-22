package rx.mvp

import android.app.Application

/**
 * @author justin on 2019/02/21 15:47
 * @version V1.0
 */
object MVP {
    fun init(app: Application) {
        MVPActivityBind().bind(app)
    }
}