package com.marinj.shoppingwarfare.feature.user.presentation.viewmodel

import android.util.Log
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.feature.user.presentation.model.UserEvent

class UserViewModel : BaseViewModel<UserEvent>() {

    override fun onEvent(event: UserEvent) {
        when (event) {
            UserEvent.End -> Log.d("Test", "End")
            UserEvent.Start -> Log.d("Test", "Start")
        }
    }
}
