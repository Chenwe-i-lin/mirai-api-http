/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.api.http.queue

import net.mamoe.mirai.message.ContactMessage
import net.mamoe.mirai.message.data.MessageSource

class CacheQueue : LinkedHashMap<Int, ContactMessage>() {

    var cacheSize = 4096

    override fun get(key: Int): ContactMessage = super.get(key) ?: throw NoSuchElementException()

    override fun put(key: Int, value: ContactMessage): ContactMessage? = super.put(key, value).also {
        if (size > cacheSize) {
            remove(this.entries.first().key)
        }
    }

    fun add(packet: ContactMessage) {
        put(packet[MessageSource].id, packet)
    }
}