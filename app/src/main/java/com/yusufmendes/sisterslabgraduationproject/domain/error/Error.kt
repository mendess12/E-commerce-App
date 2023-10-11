package com.yusufmendes.sisterslabgraduationproject.domain.error

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yusufmendes.sisterslabgraduationproject.model.AppResponse
import com.yusufmendes.sisterslabgraduationproject.util.extensions.orDefault
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.IllegalStateException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val STATUS_CODE_UNAUTHORISED = 401

typealias HttpErrorHandler = (
    e: HttpException
) -> Error.ApiError

typealias ApiErrExtractor = (code: Int, body: String?) -> Error.ApiError

/*
 * Generic Error class
 * Contains all error types can be used in application layers
 */
sealed class Error : Throwable() {

    val raw: String?
        get() = this.javaClass.simpleName


    /*
     * UnknownError
     * Used when get an unexpected situation or exception
     */
    data class UnknownError(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : Error()

    sealed class ApiError : Error() {

        // All api errors must have status code
        abstract var statusCode: Int?
        open var errorBody: AppResponse? = null

        open val handler: HttpErrorHandler = { e ->
            when (e.code()) {
                STATUS_CODE_UNAUTHORISED -> AuthenticationError(cause = e)
                else -> {
                    val body = e.response()?.errorBody()?.string()
                    if (body.isNullOrEmpty()) {
                        UnexpectedResponseError(cause = e)
                    } else {
                        extractor.invoke(e.code(), body)
                    }
                }
            }
        }

        open val extractor: ApiErrExtractor = { code, body: String? ->
            body?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val moshiErrorAdapter = moshi.adapter(AppResponse::class.java)
                val errorResponse = moshiErrorAdapter.fromJson(JSONObject(body).toString())
                val errorBody: AppResponse = errorResponse ?: return@let UnexpectedResponseError()
                DefaultApiError(
                    statusCode = code,
                    errorBody = errorBody,
                    cause = this.cause
                )
            } ?: UnexpectedResponseError()
        }

        open fun build(): ApiError {
            return when (this.cause) {
                is HttpException -> {
                    this.handler.invoke(this.cause as HttpException)
                }
                is UnknownHostException -> {
                    ConnectionError(cause = this.cause)
                }
                is SocketTimeoutException -> {
                    ConnectionError(cause = this.cause)
                }
                else -> {
                    UnexpectedResponseError(cause = this.cause)
                }
            }
        }

        /*
         * ApiError
         * Error type used for deal with errors come from api
         */
        class DefaultApiError(
            @Transient override var statusCode: Int? = null,
            @Transient override val cause: Throwable? = null,
            @Transient override var errorBody: AppResponse? = null
        ) : ApiError() {
            override val message: String
                get() {
                    return getDisplayMessage()
                }

            private fun getDisplayMessage(): String{
                return errorBody?.message.orDefault("Unknown Error")
            }
        }

        /*
         * Unexpected Response Error
         */
        data class UnexpectedResponseError(
            override var statusCode: Int? = null,
            override val cause: Throwable? = null
        ) : ApiError()

        /*
         * AuthenticationError
         * Used for 401 http responses
         */
        data class AuthenticationError(
            @Transient override val cause: Throwable? = null
        ) : ApiError() {
            override var statusCode: Int? = STATUS_CODE_UNAUTHORISED
        }

        /*
         * ConnectionError
         * Defined to connection erros between app and api
         */
        data class ConnectionError(
            override var statusCode: Int? = null,
            override val message: String? = null,
            override val cause: Throwable? = null,
        ) : ApiError()
    }

    /*
     * BusinessError
     * Used in Business layer to define business errors
     */
    data class BusinessError(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : Error()
}
