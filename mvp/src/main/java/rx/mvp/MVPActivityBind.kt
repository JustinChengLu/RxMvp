package rx.mvp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author justin on 2019/02/22 15:16
 * @version V1.0
 */

internal class MVPActivityBind : Application.ActivityLifecycleCallbacks {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNCHECKED_CAST")
    val data: HashMap<Class<*>, MvpBinding>  by lazy {
        val bindingData = MVP::class.java.classLoader.loadClass("rx.mvp.BindingData")
        val field = bindingData.getField("data")
        field.get(null) as HashMap<Class<*>, MvpBinding>
    }

    private val fragmentBind = MVPFragmentBind(data)

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        data[activity.javaClass]?.bind(activity)
        if (activity is AppCompatActivity) {
            fragmentBind.bind(activity.supportFragmentManager)
        }
    }

    fun bind(application: Application){
        application.registerActivityLifecycleCallbacks(this)
    }
}