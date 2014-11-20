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

package com.github.fmonniot.mailbox;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class App 
{
    public static void main( String[] args )
    {
        try {
            final String baseUrl = "http://localhost:8080/directory-manager/api/v1/directory/";

            String input = "{\"userName\":\"test\",\"permission\":{\"readAccess\":true,\"writeAccess\":true}}";

            Client client = Client.create();

            // create a user
            WebResource webResource = client
                    .resource(baseUrl + "create");

            ClientResponse response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String output = response.getEntity(String.class);
            Long userId = Long.parseLong(output);

            System.out.println("Create user .... " + output);

            // lookup user rights
            webResource = client
                    .resource(baseUrl + userId);

            response = webResource.accept("application/json")
                    .type("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("User rights .... " + response.getEntity(String.class));

            // update user rights
            input = "{\"readAccess\":false,\"writeAccess\":false}";

            webResource = client
                    .resource(baseUrl + userId);

            response = webResource.accept("application/json")
                    .type("application/json").post(ClientResponse.class, input);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("Update rights .... OK");

            // lookup user rights
            webResource = client
                    .resource(baseUrl + userId);

            response = webResource.accept("application/json")
                    .type("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("User rights .... " + response.getEntity(String.class));

            // lookup all users
            webResource = client
                    .resource(baseUrl + "all");

            response = webResource.accept("application/json")
                    .type("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("User List .... " + response.getEntity(String.class));

            // delete user
            webResource = client
                    .resource(baseUrl + userId);

            response = webResource.accept("application/json")
                    .type("application/json").delete(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("Delete user .... OK");

            // lookup all users
            webResource = client
                    .resource(baseUrl + "all");

            response = webResource.accept("application/json")
                    .type("application/json").get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }
            System.out.println("User List .... " + response.getEntity(String.class));


        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
