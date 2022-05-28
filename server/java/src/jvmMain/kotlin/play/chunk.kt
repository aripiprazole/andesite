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

package andesite.java.play

import andesite.java.convertChunk
import andesite.java.player.Session
import andesite.java.player.sendPacket
import andesite.java.server.JavaMinecraftServer
import org.apache.logging.log4j.kotlin.logger

private val logger = logger("andesite.handlers.Chunk")

internal suspend fun JavaMinecraftServer.handleChunkMovement(session: Session) {
  for (x in -1 until ((spawn.x * 2) / 16 + 1).toInt()) {
    for (z in -1 until ((spawn.z * 2) / 16 + 1).toInt()) {
      val chunk = spawn.world.getChunkAt(x, z) ?: continue

      session.sendPacket(convertChunk(chunk))
    }
  }
}
