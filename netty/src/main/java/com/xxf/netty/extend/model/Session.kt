package com.xxf.netty.extend.model

import com.xxf.database.xxf.objectbox.converter.BeanPropertyConverter
import com.xxf.database.xxf.objectbox.converter.MapPropertyConverter
import com.xxf.database.xxf.objectbox.id.IdUtils
import com.xxf.netty.protocal.Protocal
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

/**
 * @Author: XGod  xuanyouwu@163.com  17611639080
 * Date: 2/22/21 11:34 AM
 * Description: 会话
 */
@Entity
class Session : Serializable {

    @Id(assignable = true)
    var _id: Long = 0
        get() = IdUtils.generateId(sessionId);

    /**
     * 会话ID
     */
    var sessionId: String = "";

    /**
     * 未读数量
     */
    var unReadNum: Long = 0

    /**
     * 消息收到时间
     */
    var timestamp: Long = 0

    /**
     * 对方 或者群  相对于登陆者
     */
    @Convert(converter = ContactPropertyConverter::class, dbType = String::class)
    var target: Contact? = null

    /**
     * 最后一条消息  可空
     */
    @Convert(converter = MessagePropertyConverter::class, dbType = String::class)
    var message: Protocal? = null

    /**
     * 扩展使用
     */
    @Convert(converter = MapPropertyConverter::class, dbType = String::class)
    var extra: Map<String, Any>? = null


    constructor(from: String, to: String) {
        this.sessionId = Protocal.genSessionIdString(from, to);
    }

    constructor(message: Protocal) {
        this.message = message
        this.sessionId = Protocal.genSessionIdString(message.from, message.to);
    }


    override fun toString(): String {
        return "Session{" +
                "_id='" + _id + '\'' +
                ", unReadNum=" + unReadNum +
                ", timestamp=" + timestamp +
                ", target=" + target +
                ", message=" + message +
                ", extra=" + extra +
                '}'
    }

    internal class ContactPropertyConverter : BeanPropertyConverter<Contact>() {
    }

    internal class MessagePropertyConverter : BeanPropertyConverter<Protocal>() {
    }
}