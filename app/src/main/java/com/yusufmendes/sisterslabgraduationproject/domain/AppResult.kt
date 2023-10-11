package com.yusufmendes.sisterslabgraduationproject.domain


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import java.util.*

sealed class AppResult<out T : Any> {
    abstract val id: String

    inline fun <R : Any> map(
        mapFunction: (T) -> R
    ): AppResult<R> {
        return when (this) {
            is Success -> attempt { mapFunction.invoke(data) }
            is Failure -> Failure(error)
        }
    }

    inline fun <R : Any> flatMap(
        flatMapFunction: (T) -> AppResult<R>
    ): AppResult<R> {
        return map { result -> flatMapFunction.invoke(result) }.getOrElse { Failure(it) }
    }

    inline fun <E : Throwable> mapError(mapAction: (Throwable) -> E): AppResult<T> {
        return when (this) {
            is Success -> Success(data)
            is Failure -> Failure(mapAction.invoke(error))
        }
    }

    inline fun doOnSuccess(action: (T) -> Unit): AppResult<T> {
        if (this is Success) {
            action.invoke(data)
        }
        return this
    }

    inline fun doOnFailure(action: (Throwable) -> Unit): AppResult<T> {
        if (this is Failure) {
            action.invoke(error)
        }

        return this
    }

    inline fun doOnFinally(action: () -> Unit): AppResult<T> {
        action.invoke()
        return this
    }

    inline fun fold(onSuccess: (T)-> Unit, onFailure: (Throwable) -> Unit){
        return when(this){
            is Success -> onSuccess.invoke(data)
            is Failure -> onFailure.invoke(error)
        }
    }

    fun isSuccess() = this is Success

    fun isFailure() = this is Failure

    companion object {
        fun <T : Any> succeed(data: T): AppResult<T> = Success(data)

        fun <E : Throwable> fail(error: E): AppResult<Nothing> = Failure(error)
    }
}

data class Success<out T : Any>(val data: T) : AppResult<T>() {
    override val id = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Success<*>) return false

        if (data != other.data) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }
}

data class Failure(val error: Throwable) : AppResult<Nothing>() {
    override val id = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Failure) return false

        if (error != other.error) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = error.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}


fun <T : Any> AppResult<T>.getOrNull(): T? {
    return when (this) {
        is Success -> data
        is Failure -> null
    }
}

fun <T : Any> AppResult<T>.getOrDefault(default: T) = when (this) {
    is Success -> this.data
    is Failure -> default
}

fun <T : Any> AppResult<T>.get(): T {
    return when (this) {
        is Success -> data
        is Failure -> throw error
    }
}

inline fun <T : Any> AppResult<T>.getOrElse(f: (Throwable) -> T): T {
    return when (this) {
        is Success -> data
        is Failure -> f.invoke(error)
    }
}


inline fun <T : Any> attempt(f: () -> T): AppResult<T> {
    return try {
        Success(f.invoke())
    } catch (e: Throwable) {
//        FirebaseCrashlytics.getInstance().recordException(e)
        Failure(error = e)
    }
}

suspend inline fun <T : Any> attemptSuspend(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline f: suspend () -> T
): AppResult<T> {
    return withContext(dispatcher) {
        try {
            Success(f.invoke())
        } catch (e: Throwable) {
            Failure(error = e)
        }
    }
}

fun <T : Any> AppResult<Flow<T>>.toFlowResult(): Flow<AppResult<T>> {
    return try {
        get().map { Success(it) }.catch { Failure(it) }
    } catch (error: Throwable) {
        flowOf(Failure(error))
    }
}

fun <T : Any> AppResult<Flow<T>>.toFlow(): Flow<T> {
    return when (this) {
        is Success -> data
        is Failure -> flow { throw error }
    }
}

fun <T : Any> AppResult<T>.asFlow(): Flow<AppResult<T>> {
    return when (this) {
        is Success -> flow { emit(Success(data)) }
        is Failure -> flow { throw error }
    }
}

fun <T : Any> Flow<AppResult<T>>.flatten(): Flow<T> = transform { value ->
    emit(value.get())
}

inline fun <T : Any, R : Any> Flow<AppResult<T>>.mapResult(
    crossinline mapFunction: (T) -> R
): Flow<AppResult<R>> = transform { value ->
    emit(value.map(mapFunction))
}

fun <T : Any> Flow<T>.asAppResult(): Flow<AppResult<T>> {
    return this.map { AppResult.succeed(it) }.catch { AppResult.fail(it) }
}

fun <T: Any> AppResult<AppResult<T>>.flatMap(): AppResult<T> {
    return  getOrElse { Failure(it) }
}

fun <T : Any> AppResult<Flow<T>>.toFlowAppResult(): Flow<AppResult<T>> {
    return try {
        get().map { Success(it) }.catch { Failure(it) }
    } catch (error: Throwable) {
        flowOf(Failure(error))
    }
}
fun <T: Any> inFlow(request: suspend ()-> AppResult<T>): Flow<AppResult<T>>{
    return flow {
        emit(request.invoke())
    }
}