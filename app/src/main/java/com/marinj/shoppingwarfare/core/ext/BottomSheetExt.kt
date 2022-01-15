package com.marinj.shoppingwarfare.core.ext

import androidx.compose.material.BottomSheetScaffoldState

suspend fun BottomSheetScaffoldState.expandOrCollapse() {
    if (bottomSheetState.isCollapsed) {
        bottomSheetState.expand()
    } else {
        bottomSheetState.collapse()
    }
}
