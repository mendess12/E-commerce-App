package com.yusufmendes.sisterslabgraduationproject.util.retrofit

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.attempt
import com.yusufmendes.sisterslabgraduationproject.domain.error.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Invocation
import retrofit2.Response
import retrofit2.awaitResponse

internal class ResultCall<T : Any>(
    private val proxy: Call<T>,
    private val coroutineScope: CoroutineScope,
) : Call<AppResult<T>> {

    override fun enqueue(callback: Callback<AppResult<T>>) {
        coroutineScope.launch {
            val result = handleApi(proxy) { proxy.awaitResponse() }
            callback.onResponse(this@ResultCall, Response.success(result))
        }
    }

    override fun execute(): Response<AppResult<T>> =
        runBlocking(coroutineScope.coroutineContext) {
            val result = handleApi(proxy) { proxy.execute() }
            Response.success(result)
        }

    override fun clone(): Call<AppResult<T>> = ResultCall(proxy.clone(), coroutineScope)
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() = proxy.cancel()
}

suspend fun <T : Any> handleApi(
    call: Call<T>,
    execute: suspend () -> Response<T>
): AppResult<T> {
    return attempt {
        val result = execute.invoke()
        if (result.isSuccessful) {
            val body = result.body()
            if (body == null) {
                val invocation = call.request().tag(Invocation::class.java)!!
                val method = invocation.method()
                throw KotlinNullPointerException(
                    "Response from " +
                            method.declaringClass.name +
                            '.' +
                            method.name +
                            " was null but response body type was declared as non-null"
                )
            } else {
                body
            }
        } else {
            result.run {
                throw Error.ApiError.DefaultApiError(cause = HttpException(this)).build()
            }
        }
    }
}