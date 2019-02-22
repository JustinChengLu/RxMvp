package rx.mvp.demo.main

/**
 * @author justin on 2019/02/22 16:07
 * @version V1.0
 */

class MainPresenter(override val view: MainContract.View, override val model: MainContract.Model) : MainContract.Presenter