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

package andesite.komanda

import andesite.komanda.errors.CommandNotFoundException
import andesite.komanda.errors.NoSwitchableTargetException
import andesite.komanda.parsing.ExecutionNode
import andesite.komanda.parsing.parseCommandString
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.withContext

public interface KomandaRoot<S : Any> {
  public val komanda: KomandaSettings

  public fun komanda(configure: KomandaSettingsBuilder.() -> Unit)

  public fun command(name: String, builder: CommandBuilder.() -> Unit)

  public suspend fun dispatch(string: String, sender: S)
}

public abstract class AbstractKomandaRoot<S : Any>(
  builder: KomandaSettingsBuilder.() -> Unit = {},
) : KomandaRoot<S> {
  public val commands: MutableMap<String, Command> = mutableMapOf()

  public override var komanda: KomandaSettings = KomandaSettings(DefaultKomandaSettings, builder)

  public abstract fun createExecutionScope(sender: S, nodes: List<ExecutionNode>): ExecutionScope<S>

  override fun komanda(configure: KomandaSettingsBuilder.() -> Unit) {
    komanda = KomandaSettings(komanda, configure)
  }

  override fun command(name: String, builder: CommandBuilder.() -> Unit) {
    commands[name] = CommandBuilder(name).apply(builder).build()
  }

  override suspend fun dispatch(string: String, sender: S) {
    val executionNodes = parseCommandString(string)
    if (executionNodes.isEmpty()) return

    val name = executionNodes.first()
    val arguments = executionNodes.drop(1)

    val command = commands[name.text]
      ?: throw CommandNotFoundException(name.text)

    val handler = command.rootPattern.executionHandlers[sender::class]
      ?: throw NoSwitchableTargetException(sender::class)

    withContext(CoroutineName("command/$name")) {
      handler.invoke(createExecutionScope(sender, arguments))
    }
  }
}