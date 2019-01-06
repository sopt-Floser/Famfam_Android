package com.sopt.famfam

import android.support.v4.app.Fragment

class BackPressImpl(private val parentFragment: Fragment?) : OnBackPressListener {

    override fun onBackPressed(): Boolean {

        if (parentFragment == null) return false

        val childCount = parentFragment!!.getChildFragmentManager().getBackStackEntryCount()

        if (childCount == 0) {
            // it has no child Fragment
            // can not handle the onBackPressed task by itself
            return false

        } else {
            // get the child Fragment
            val childFragmentManager = parentFragment!!.getChildFragmentManager()
            val childFragment = childFragmentManager.getFragments().get(0) as OnBackPressListener

            // propagate onBackPressed method call to the child Fragment
            if (!childFragment.onBackPressed()) {
                // child Fragment was unable to handle the task
                // It could happen when the child Fragment is last last leaf of a chain
                // removing the child Fragment from stack
                childFragmentManager.popBackStackImmediate()

            }

            // either this Fragment or its child handled the task
            // either way we are successful and done here
            return true
        }
    }
}