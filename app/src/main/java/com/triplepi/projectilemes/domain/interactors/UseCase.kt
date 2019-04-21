package com.triplepi.projectilemes.domain.interactors

typealias ResultCallback<TResult> = ((TResult) -> Unit)

abstract class UseCase<TResult> {

    private var callback: ResultCallback<TResult>? = null

    abstract fun run() : TResult

    fun execute(callback: ResultCallback<TResult>) {

        this.callback = callback

        val result = run()

        this.callback?.invoke(result)
    }

    fun cancel() {
        callback = null
    }
}
