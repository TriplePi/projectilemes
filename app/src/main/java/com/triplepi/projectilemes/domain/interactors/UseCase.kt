package com.triplepi.projectilemes.domain.interactors

import kotlinx.coroutines.*

typealias ResultCallback<TResult> = ((TResult) -> Unit)

abstract class UseCase<TResult> {

    private var callback: ResultCallback<TResult>? = null

    abstract suspend fun run(): TResult

    protected abstract val dispatcher: CoroutineDispatcher

    fun execute(callback: ResultCallback<TResult>) {

        this@UseCase.callback = callback

        val result = runBlocking(dispatcher) { run() }

        this@UseCase.callback?.invoke(result)
    }

    fun cancel() {
        callback = null
    }
}