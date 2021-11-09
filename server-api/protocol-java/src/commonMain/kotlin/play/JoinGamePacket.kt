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

package com.gabrielleeg1.javarock.api.protocol.java.play

import com.gabrielleeg1.javarock.api.protocol.Packet
import com.gabrielleeg1.javarock.api.protocol.ProtocolEnum
import com.gabrielleeg1.javarock.api.protocol.ProtocolNbt
import com.gabrielleeg1.javarock.api.protocol.ProtocolValue
import com.gabrielleeg1.javarock.api.protocol.ProtocolVariant
import com.gabrielleeg1.javarock.api.protocol.Variant
import com.gabrielleeg1.javarock.api.protocol.java.JavaPacket
import com.gabrielleeg1.javarock.api.protocol.types.VarInt
import kotlinx.serialization.Serializable
import net.benwoodworth.knbt.NbtCompound

// TODO: use Identifier type
@Packet(0x26)
@Serializable
class JoinGamePacket(
  val entityId: Int,
  val isHardcore: Boolean,
  val gameMode: GameMode,
  val previousGameMode: PreviousGameMode,
  val worlds: List<String>,
  @ProtocolNbt
  val dimensionCodec: NbtCompound,
  @ProtocolNbt
  val dimension: NbtCompound,
  val world: String,
  val hashedSeed: Long,
  val maxPlayers: VarInt,
  val viewDistance: VarInt,
  val reducedDebugInfo: Boolean,
  val enableRespawnScreen: Boolean,
  val isDebug: Boolean,
  val isFlat: Boolean,
) : JavaPacket

@Serializable
@ProtocolEnum
@ProtocolVariant(Variant.Byte)
enum class PreviousGameMode {
  @ProtocolValue(-1)
  Unknown,

  @ProtocolValue(0)
  Survival,

  @ProtocolValue(1)
  Creative,

  @ProtocolValue(2)
  Adventure,

  @ProtocolValue(3)
  Spectator;

  fun toGameMode(): GameMode? {
    return when (this) {
      Unknown -> null
      else -> GameMode.values()[ordinal]
    }
  }
}

@Serializable
@ProtocolEnum
@ProtocolVariant(Variant.UByte)
enum class GameMode {
  @ProtocolValue(0)
  Survival,

  @ProtocolValue(1)
  Creative,

  @ProtocolValue(2)
  Adventure,

  @ProtocolValue(3)
  Spectator;
}

