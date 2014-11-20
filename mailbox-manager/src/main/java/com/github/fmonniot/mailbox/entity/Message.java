/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 François Monniot & Alexis Mousset
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.fmonniot.mailbox.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
@Entity(name = "Message")
@Table(name = "mb_message")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Message implements EntityIdentifiable {

    @Id
    @GeneratedValue
    private Long id;

    private String senderName;

    @ManyToOne
    @JoinColumn(name = "BOX_ID")
    private Box box;

    private String receiverName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sendingDate;

    private String subject;

    private String body;

    private boolean isRead;

    protected Message() {
    }

    public Message(String senderName, String receiverName, String subject, String body, boolean isRead) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.subject = subject;
        this.body = body;
        this.isRead = isRead;
    }

    public Message(String senderName, String receiverName, String subject, String body) {
        this(senderName, receiverName, subject, body, false);
    }

    /**
     * @return this message id
     */
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name of the sender (if any)
     */
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the name of the receiver (if any)
     */
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @return when this message has been sent
     */
    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    /**
     * @return The subject fo this message (if any)
     */
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the content of this message (if any)
     */
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return whether this message has been read or not.
     */
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * Mark this message as read or as unread
     *
     * @param isRead the read status of this message
     */
    public void markAsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderName='" + senderName + '\'' +
                ", box=" + box +
                ", subject='" + subject + '\'' +
                '}';
    }

    public void removeBox() {
        box = null;
    }
}
