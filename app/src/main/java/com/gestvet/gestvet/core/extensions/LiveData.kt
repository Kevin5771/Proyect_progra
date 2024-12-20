package com.gestvet.gestvet.core.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B, Result> LiveData<A>.combine(
    other: LiveData<B>,
    combiner: (A, B) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other.value
        if (b != null) result.postValue(combiner(a, b))
    }

    result.addSource(other) { b ->
        val a = this@combine.value
        if (a != null) result.postValue(combiner(a, b))
    }

    return result
}

fun <A, B, C, D, E, F, Result> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    other3: LiveData<D>,
    other4: LiveData<E>,
    other5: LiveData<F>,
    combiner: (A, B, C, D, E, F) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other1.value
        val c = other2.value
        val d = other3.value
        val e = other4.value
        val f = other5.value
        if (b != null && c != null && d != null && e != null && f !=null) {
            result.postValue(combiner(a, b, c, d, e, f))
        }
    }
    result.addSource(other1) { b ->
        val a = this@combine.value
        val c = other2.value
        val d = other3.value
        val e = other4.value
        val f = other5.value
        if (a != null && c != null && d != null && e != null && f != null) {
            result.postValue(combiner(a, b, c, d, e, f))
        }
    }
    result.addSource(other2) { c ->
        val a = this@combine.value
        val b = other1.value
        val d = other3.value
        val e = other4.value
        val f = other5.value
        if (a != null && b != null && d != null && e != null && f != null) {
            result.postValue(combiner(a, b, c, d, e, f))
        }
    }
    result.addSource(other3) { d ->
        val a = this@combine.value
        val b = other1.value
        val c = other2.value
        val e = other4.value
        val f = other5.value
        if (a != null && b != null && c != null && e != null && f != null) {
            result.postValue(combiner(a, b, c, d, e, f))
        }
    }
    result.addSource(other4) { e ->
        val a = this@combine.value
        val b = other1.value
        val c = other2.value
        val d = other3.value
        val f = other5.value
        if (a != null && b != null && c != null && d != null && f != null) {
            result.postValue(combiner(a, b, c, d, e, f))
        }
    }
    result.addSource(other5) { f ->
        val a = this@combine.value
        val b = other1.value
        val c = other2.value
        val d = other3.value
        val e = other4.value
        if (a != null && b != null && c != null && d != null && e != null) {
            result.postValue(combiner(a, b, c, d, e, f))
        }
    }
    return result
}