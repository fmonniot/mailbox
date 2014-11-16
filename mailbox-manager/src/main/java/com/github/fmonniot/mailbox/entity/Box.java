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

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance
@DiscriminatorColumn(name = "box_type")
@Table(name = "mb_box")
public abstract class Box {

    protected String name;

    // Used for distinguishing different type of box (done by JPA with @Discriminator[Column|Value])
    @SuppressWarnings("UnusedDeclaration")
    protected String box_type;

    private Long clientId;

    @Id
    @GeneratedValue
    private Long id;

    protected Box() {
    }

    public Box(String name) {
        this.name = name;
    }

    /**
     * @return the id of this box
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the name of this box
     */
    public String getName() {
        return name;
    }

    /**
     * @return all messages in this box
     */
    public List<Message> readAllMessages() {
        return null;
    }

    /**
     * @return the last message received in this box
     */
    public Message readLastMessage() {
        return null;
    }

    /**
     * Add the given message to this box
     *
     * @param message the message to post
     */
    public void postMessage(Message message) {

    }
}
