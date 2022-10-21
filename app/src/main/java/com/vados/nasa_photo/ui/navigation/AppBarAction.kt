package com.vados.nasa_photo.ui.navigation

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

class AppBarAction: CoordinatorLayout.Behavior<LinearLayout>() {

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: LinearLayout,
        dependency: View
    ): Boolean {
        modifyConstrain()
        return super.onDependentViewChanged(parent, child, dependency)
    }

    private fun modifyConstrain(){
        Log.v("@@@","modifyConstrain")
    }
}