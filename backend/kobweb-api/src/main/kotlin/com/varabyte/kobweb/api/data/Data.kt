package com.varabyte.kobweb.api.data

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Read-only access to a [MutableData] store.
 *
 * See the header comment for that class for more information.
 */
interface Data {
    operator fun <T : Any> get(key: Class<T>): T?
}

fun <T : Any> Data.getValue(key: Class<T>): T = this[key]!!
inline fun <reified T : Any> Data.get(): T? = this[T::class.java]
inline fun <reified T : Any> Data.getValue(): T = getValue(T::class.java)

/**
 * A thread-safe in-memory data store providing access to values using the
 * [Service Locator pattern](https://en.wikipedia.org/wiki/Service_locator_pattern)
 *
 * It is provided so methods annotated with [InitApi] can store values (through their [InitApiContext] which provides
 * access to a mutable data store) which can later be retrieved by methods annotated with [Api] (through their
 * [ApiContext] which provides access to a read-only view).
 */
@Suppress("UNCHECKED_CAST")
class MutableData : Data {
    private val lock = ReentrantLock()
    private val cache = mutableMapOf<Class<*>, Any>()

    operator fun <T : Any> set(key: Class<T>, value: T) {
        lock.withLock { cache[key] = value }
    }

    override operator fun <T : Any> get(key: Class<T>): T? {
        return lock.withLock { cache[key] as? T }
    }
}

inline fun <reified T: Any> MutableData.add(value: T) { this[T::class.java] = value }