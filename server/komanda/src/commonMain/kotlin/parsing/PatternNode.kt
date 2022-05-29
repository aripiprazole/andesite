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

package andesite.komanda.parsing

import kotlin.reflect.KClass

public sealed interface PatternNode

public data class ArgumentNode<A : Any>(val type: KClass<A>, val name: String) :
  PatternNode

public data class VarargNode(val name: String) : PatternNode

public data class PathNode(val name: String) : PatternNode

public data class OptionalNode(val name: String) : PatternNode

public data class IntersectionNode(val identifiers: Set<String>) :
  PatternNode

public fun parsePatternNode(string: String): List<PatternNode> {
  TODO()
}
