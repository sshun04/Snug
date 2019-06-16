package com.shojishunsuke.kibunnsns.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("custom_fragment")
class CustomNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transAction = manager.beginTransaction()

        var initialNavigate = false
        var currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transAction.detach(currentFragment)
        }else{
            initialNavigate = true
        }

        var fragment = manager.findFragmentByTag(tag)
        if (fragment == null) {
            val className = destination.className
            fragment =  manager.fragmentFactory.instantiate(context.classLoader, className)
            transAction.add(containerId,fragment,tag)
        }else {
            transAction.attach(fragment)
        }

        transAction.setPrimaryNavigationFragment(fragment)
        transAction.setReorderingAllowed(true)
        transAction.commit()


        return if (initialNavigate) {
            destination
        } else {
            null
        }

    }
}