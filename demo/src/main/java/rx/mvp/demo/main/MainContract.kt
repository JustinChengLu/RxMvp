package rx.mvp.demo.main

import rx.mvp.annotation.Contract

/**
 * @author justin on 2019/02/22 16:05
 * @version V1.0
 */
@Contract
interface MainContract {
    @Contract.Model
    interface Model

    @Contract.Presenter
    interface Presenter {
        val model: Model
        val view: View
    }

    @Contract.View
    interface View

}