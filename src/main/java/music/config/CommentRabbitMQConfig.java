package music.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentRabbitMQConfig {
    //使用@Bean注解的方式，方法返回值都会自动注入到Spring容器中
    //RabbitMQ 中不存在 Binding、Queue、Exchange时，就会自动创建
    //RabbitMQ 中存在时，@Bean声明的属性发生变化时也不会覆盖

    /**
     * @return 评论功能的消息队列的Queue
     */
    @Bean
    public Queue commentQueue() {
        /**
         * name：队列名
         * durable：队列是否持久化
         * exclusive：队列是否只对首次声明它的连接可见，并且在连接断开时自动删除
         * autoDelete：队列是否自动删除
         */
        Queue queue = new Queue("comment.queue",
                true,
                false,
                false);

        return queue;
    }

    /**
     * @return 评论功能的消息队列的交换机
     */
    @Bean
    public Exchange commnetExchange() {
        TopicExchange exchange = new TopicExchange("comment-exchange",
                true,
                false);

        return exchange;
    }

    /**
     * @return 收藏功能的消息队列的绑定关系
     */
    @Bean
    public Binding commentBinding() {
        /**
         * destination：绑定关系的目的地，即队列Queue
         * DestinationType：绑定关系的类型
         * exchange：绑定关系的交换机名
         * routingKey：绑定关系的路由键
         * arguments：绑定关系的传递参数
         */
        Binding binding = new Binding("comment.queue",
                Binding.DestinationType.QUEUE,
                "comment-exchange",
                "comment.create",
                null);

        return binding;
    }
}
