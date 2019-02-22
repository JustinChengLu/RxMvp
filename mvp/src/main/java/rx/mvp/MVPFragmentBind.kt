package rx.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * @author justin on 2019/02/22 15:17
 * @version V1.0
 */

internal class MVPFragmentBind(val data: HashMap<Class<*>, MvpBinding>) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentCreated(fm: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?) {
        data[fragment.javaClass]?.bind(fragment)
    }

    fun bind(fragmentManager: FragmentManager){
        fragmentManager.registerFragmentLifecycleCallbacks(this,true)
    }
}