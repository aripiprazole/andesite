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

package com.gabrielleeg1.javarock.api.world

/**
 * Class that represents a location in the game.
 */
data class Location(
  val x: Double,
  val y: Double,
  val z: Double,
  val yaw: Float,
  val pitch: Float,
) {
  operator fun div(other: Location): Location {
    return Location(x / other.x, y / other.y, z / other.z, yaw / other.yaw, pitch / other.pitch)
  }

  operator fun times(other: Location): Location {
    return Location(x * other.x, y * other.y, z * other.z, yaw * other.yaw, pitch * other.pitch)
  }

  operator fun minus(other: Location): Location {
    return Location(x - other.x, y - other.y, z - other.z, yaw - other.yaw, pitch - other.pitch)
  }

  operator fun plus(other: Location): Location {
    return Location(x + other.x, y + other.y, z + other.z, yaw + other.yaw, pitch + other.pitch)
  }

  companion object {
    val Empty = Location(0.0, 0.0, 0.0, 0.0f, 0.0f)
  }
}