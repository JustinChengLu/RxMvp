@file:Suppress("unused")

package rx.mvp

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author justin on 2019/02/22 08:53
 * @version V1.0
 */

fun bindLifecycle(view: Any, observable: BehaviorSubject<Lifecycle.Event> = BehaviorSubject.create<Lifecycle.Event>()): Observable<Lifecycle.Event> {
    val lifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            observable.onNext(Lifecycle.Event.ON_CREATE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            observable.onNext(Lifecycle.Event.ON_START)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            observable.onNext(Lifecycle.Event.ON_RESUME)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onPause() {
            observable.onNext(Lifecycle.Event.ON_PAUSE)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            observable.onNext(Lifecycle.Event.ON_STOP)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            observable.onNext(Lifecycle.Event.ON_DESTROY)
            observable.onComplete()
        }
    }
    when (view) {
        is AppCompatActivity -> view.lifecycle.addObserver(lifecycleObserver)
        is Fragment -> view.lifecycle.addObserver(lifecycleObserver)
        else -> throw error("not support this type ${view.javaClass}")
    }
    return observable
}


fun Observable<Lifecycle.Event>.onLife(event: Lifecycle.Event): Observable<Lifecycle.Event> {
    return this.filter { it === event }
}

fun Observable<Lifecycle.Event>.onCreate() = onLife(Lifecycle.Event.ON_CREATE)
fun Observable<Lifecycle.Event>.onStart() = onLife(Lifecycle.Event.ON_START)
fun Observable<Lifecycle.Event>.onResume() = onLife(Lifecycle.Event.ON_RESUME)
fun Observable<Lifecycle.Event>.onPause() = onLife(Lifecycle.Event.ON_PAUSE)
fun Observable<Lifecycle.Event>.onStop() = onLife(Lifecycle.Event.ON_STOP)
fun Observable<Lifecycle.Event>.onDestroy() = onLife(Lifecycle.Event.ON_DESTROY)