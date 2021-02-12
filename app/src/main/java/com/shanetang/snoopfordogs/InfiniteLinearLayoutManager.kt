package com.shanetang.snoopfordogs

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class InfiniteLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {
    // action is invoked when scrolling to element number (itemCount - 1 - invokeXFromFinal)
    private var invokeXFromFinal = 3
    private var furthestScroll = 0
    private var infiniteAction: () -> Unit = {}

    fun setInvokeXFromFinal(invokeXFromFinal: Int) {
        this.invokeXFromFinal = invokeXFromFinal
    }

    fun setInfiniteAction(action: () -> Unit) {
        infiniteAction = action
    }

    private fun shouldInvokeAction() = (
        findFirstCompletelyVisibleItemPosition() > furthestScroll &&
        findFirstCompletelyVisibleItemPosition() >= itemCount - 1 - invokeXFromFinal
    )

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (shouldInvokeAction()) {
            infiniteAction.invoke()
        }
        furthestScroll = maxOf(furthestScroll, findFirstCompletelyVisibleItemPosition())
    }
}