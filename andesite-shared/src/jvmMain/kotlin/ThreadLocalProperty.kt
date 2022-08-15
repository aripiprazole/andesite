/*
 *    Copyright 2022 Gabrielle Guimarães de Oliveira
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package andesite.shared

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Represents a value-initialized thread local property delegate.
 *
 * @param value the initial value of the property
 */
public actual class ThreadLocalProperty<A : Any> internal actual constructor(value: A) :
  ReadWriteProperty<Any?, A> {
  private val _value: ThreadLocal<A> = ThreadLocal.withInitial { value }

  public actual var value: A
    get(): A = _value.get()
    set(value) = _value.set(value)

  override fun getValue(thisRef: Any?, property: KProperty<*>): A {
    return value
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: A) {
    this.value = value
  }
}
