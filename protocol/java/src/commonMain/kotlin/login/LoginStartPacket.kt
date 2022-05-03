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

package com.gabrielleeg1.andesite.api.protocol.java.login

import com.gabrielleeg1.andesite.api.protocol.ProtocolPacket
import com.gabrielleeg1.andesite.api.protocol.ProtocolString
import com.gabrielleeg1.andesite.api.protocol.java.JavaPacket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("LoginStartPacket")
@ProtocolPacket(0x00)
data class LoginStartPacket(@ProtocolString(16) val username: String) : JavaPacket
