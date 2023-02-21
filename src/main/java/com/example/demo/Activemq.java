package com.example.demo;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
public  class Activemq {
    /**
     * 消息发送者
     */
        public static void sender() {
            Connection connection = null;
            Session session = null;
            try {
                String brokerURL = "tcp://127.0.0.1:61616";//ActiveMQ 中间件连接地址
                /**
                 * 创建 javax.jms.ConnectionFactory 连接工厂
                 * org.apache.activemq.ActiveMQConnectionFactory 中默认设置了大量的参数，还有几个重载的构造器可以选择
                 */
                ConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
                //如果 ActiveMQ 连不上，则抛异常：java.net.ConnectException: Connection refused: connect
                connection = mqConnectionFactory.createConnection();//通过连接工厂获取连接 javax.jms.Connection
                connection.start();//启动连接，同理还有 stop、close
                /**
                 * Session createSession(boolean transacted, int acknowledgeMode) 创建会话
                 * transacted ：表示是否开启事务
                 * acknowledgeMode：表示会话确认模式
                 *      AUTO_ACKNOWLEDGE 自动确认
                 *      CLIENT_ACKNOWLEDGE 客户确认
                 *      还有  DUPS_OK_ACKNOWLEDGE、SESSION_TRANSACTED
                 */
                session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
                /**
                 * createQueue(String queueName)：创建消息队列，指定队列名称，消费者可以根据队列名称获取消息
                 * Destination 目的地，重点，interface Queue extends Destination
                 */
                Destination destination = session.createQueue("queue-app");
                //createProducer(Destination destination)：根据目的地创建消息生产者
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                int massageTotal = 5;
                for (int i = 0; i < massageTotal; i++) {
                    //创建一个文本消息
                    TextMessage textMessage = session.createTextMessage("神州飞船 " + (i + 1) + " 号！");
                    producer.send(textMessage);//生产者发送消息
                    session.commit();//会话提交
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } finally {
                if (session != null) {
                    try {
                        session.close();//关闭会话
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();//关闭连接
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
            /**
             * 消息消费者
             */
            public static void JmsReceiver () {
                Connection connection = null;
                Session session = null;
                try {
                    String brokerURL = "tcp://127.0.0.1:61616";//ActiveMQ 中间件连接地址
                    /**
                     * 创建 javax.jms.ConnectionFactory 连接工厂
                     * org.apache.activemq.ActiveMQConnectionFactory 中默认设置了大量的参数，还有几个重载的构造器可以选择
                     */
                    ConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
                    //如果 ActiveMQ 连不上，则抛异常：java.net.ConnectException: Connection refused: connect
                    connection = mqConnectionFactory.createConnection();//通过连接工厂获取连接 javax.jms.Connection
                    connection.start();//启动连接，同理还有 stop、close
                    /**
                     * Session createSession(boolean transacted, int acknowledgeMode) 创建会话
                     * transacted ：表示是否开启事务
                     * acknowledgeMode：表示会话确认模式
                     *      AUTO_ACKNOWLEDGE 自动确认
                     *      CLIENT_ACKNOWLEDGE 客户确认
                     *      还有  DUPS_OK_ACKNOWLEDGE、SESSION_TRANSACTED
                     */
                    session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
                    /**
                     * createQueue(String queueName)：创建消息队列，指定队列名称，消费者可以根据队列名称获取消息
                     * Destination 目的地，重点，interface Queue extends Destination
                     */
                    Destination destination = session.createQueue("queue-app");

                    //createProducer(Destination destination)：根据目的地创建消息消费者
                    MessageConsumer consumer = session.createConsumer(destination);
                    int massageTotal = 5;
                    for (int i = 0; i < massageTotal; i++) {
                        TextMessage message = (TextMessage) consumer.receive();//消费者接收消息。因为对方发送的文本消息，所以可以强转
                        session.commit();//确认消息，告诉中间件，消息已经确认接收
                        System.out.println((i + 1) + "：" + message.getText());//获取消息文本
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                } finally {
                    if (session != null) {
                        try {
                            session.close();//关闭会话
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();//关闭连接
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
}


