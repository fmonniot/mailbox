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
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
@Entity(name = "Box")
@Table(name = "mb_box")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Box implements EntityIdentifiable {

    private String name;

    /**
     * Used for distinguishing different type of box (done by JPA with @Discriminator[Column|Value])
     */
    private String boxType;

    /**
     * Owner of this box
     */
    private Long clientId;

    /**
     * Messages in this box
     */
    @OneToMany
    private List<Message> messages;

    @Id
    @GeneratedValue
    private Long id;

    protected Box() {
    }

    public Box(String name, String boxType) {
        this.name = name;
        this.boxType = boxType;
    }

    public Box(Box box) {
        this.name = box.name;
        this.boxType = box.boxType;
        this.clientId = box.clientId;
        this.messages = box.messages;
        this.id = box.id;
    }

    /**
     * @return the id of this box
     */
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the name of this box
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return all messages in this box
     */
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Box{" +
                "name='" + name + '\'' +
                ", boxType='" + boxType + '\'' +
                ", id=" + id +
                ", clientId=" + clientId +
                '}';
    }
}
