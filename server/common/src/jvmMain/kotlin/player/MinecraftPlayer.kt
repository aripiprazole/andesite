/*
 *    Copyright 2021 Gabrielle Guimarães de Oliveira
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

package andesite.player

import andesite.event.EventHolder
import andesite.protocol.java.JavaPacket
import andesite.protocol.misc.Chat
import andesite.protocol.misc.ChatBuilder
import andesite.protocol.misc.Uuid
import org.apache.logging.log4j.kotlin.Logging

public sealed interface MinecraftPlayer : EventHolder<PlayerEvent>, Logging {
  public val id: Uuid
  public val protocol: Int
  public val username: String

  public suspend fun sendMessage(chat: Chat)

  public suspend fun sendMessage(text: String = "", builder: ChatBuilder.() -> Unit) {
    sendMessage(Chat.build(text, builder))
  }

  public suspend fun sendMessage(text: String) {
    sendMessage(Chat.of(text))
  }
}

public interface JavaPlayer : MinecraftPlayer {
  public suspend fun sendPacket(packet: JavaPacket, queue: Boolean = false)
}

public interface BedrockPlayer : MinecraftPlayer