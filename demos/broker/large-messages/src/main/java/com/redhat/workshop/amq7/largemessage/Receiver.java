/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.workshop.amq7.largemessage;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;

public class Receiver {
   public static void main(String args[]) throws Exception{

      try {
         InitialContext context = new InitialContext();

         Queue queue = (Queue) context.lookup("queue/exampleQueue");

         ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");

         try (
               Connection connection = cf.createConnection();
         ) {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            Message message = consumer.receiveNoWait();
            System.out.println("received message from broker " + message);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
