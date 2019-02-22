package rx.mvp

import android.view.View
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

/**
 * @author justin on 2019/02/22 09:22
 * @version V1.0
 */

fun View.connectClick(observable: Observer<Any> = PublishSubject.create()){
    setOnClickListener {
        observable.onNext(it)
    }
}