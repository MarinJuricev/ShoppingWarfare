package com.marinj.shoppingwarfare.core.ext

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi

@OptIn(ExperimentalMaterialApi::class)
suspend fun BottomSheetScaffoldState.expandOrCollapse() {
    if (bottomSheetState.isCollapsed) {
        bottomSheetState.expand()
    } else {
        bottomSheetState.collapse()
    }
}
