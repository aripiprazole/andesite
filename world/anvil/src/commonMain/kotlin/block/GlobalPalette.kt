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

@file:OptIn(ExperimentalSerializationApi::class)

package com.gabrielleeg1.andesite.api.world.anvil.block

import com.gabrielleeg1.andesite.api.protocol.misc.Identifier
import com.gabrielleeg1.andesite.api.world.block.Block
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.mapSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer
import kotlin.math.ceil
import kotlin.math.log2

typealias StateId = Int

@Serializable
class BlockPaletteEntry(
  val properties: JsonObject = buildJsonObject { },
  val states: List<BlockState> = emptyList(),
)

@Serializable
class BlockState(
  val id: StateId,
  val properties: JsonObject = buildJsonObject { },
  val default: Boolean = false,
)

@Serializable(GlobalPaletteSerializer::class)
class GlobalPalette(private val map: Map<Identifier, BlockPaletteEntry>) :
  Map<Identifier, BlockPaletteEntry> by map {
  val bitsPerBlock: Int = ceil(log2(size.toDouble())).toInt()

  val totalStates: Int =
    flatMap { it.value.states }.maxOfOrNull { it.id } ?: error("No states found")

  fun stateIdForBlock(block: Block): StateId? {
    return this[block.id]
      ?.states
      ?.find { state -> state.properties == block.properties }
      ?.id
  }

  companion object {
    fun empty(): GlobalPalette {
      return GlobalPalette(emptyMap())
    }
  }
}

private val json = Json {
  ignoreUnknownKeys = true
}

fun readGlobalPalette(text: String): GlobalPalette {
  return json.decodeFromString(serializer(), text)
}

internal object GlobalPaletteSerializer : KSerializer<GlobalPalette> {
  override val descriptor: SerialDescriptor = mapSerialDescriptor<String, BlockPaletteEntry>()

  override fun serialize(encoder: Encoder, value: GlobalPalette) {
    encoder.encodeSerializableValue(
      MapSerializer(Identifier.serializer(), BlockPaletteEntry.serializer()),
      value,
    )
  }

  override fun deserialize(decoder: Decoder): GlobalPalette {
    return GlobalPalette(
      decoder.decodeSerializableValue(
        MapSerializer(Identifier.serializer(), BlockPaletteEntry.serializer()),
      ),
    )
  }
}