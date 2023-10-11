package com.yusufmendes.sisterslabgraduationproject.util.retrofit

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

internal class ResultDeferredCallAdapter<T : Any>(
    private val resultType: Type,
    private val coroutineScope: CoroutineScope
) : CallAdapter<T, Deferred<AppResult<T>>> {

    override fun responseType(): Type {
        return resultType
    }

    @Suppress("DeferredIsResult")
    override fun adapt(call: Call<T>): Deferred<AppResult<T>> {
        val deferred = CompletableDeferred<AppResult<T>>().apply {
            invokeOnCompletion {
                if (isCancelled && !call.isCanceled) {
                    call.cancel()
                }
            }
        }

        coroutineScope.launch {
            val result = handleApi(call) { call.awaitResponse() }
            deferred.complete(result)
        }

        return deferred
    }
}
