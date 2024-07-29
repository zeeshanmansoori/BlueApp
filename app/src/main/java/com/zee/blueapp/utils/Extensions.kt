package com.zee.blueapp.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.zee.blueapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun HomeViewModel.HomeEvent.ApiFailure.getErrorMsg(context: Context): String {
    return if (this.res != null) context.getString(res) else this.failureReason
}

fun Context.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}

@Composable
fun <T> Flow<T>.collectAsEvent(
    context: CoroutineContext = EmptyCoroutineContext,
    block: (T) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        onEach(block).flowOn(context).launchIn(this)
    }
}


