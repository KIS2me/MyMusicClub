package music.config;

import music.domain.Collect;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectRabbitMQConfig {
    //使用@Bean注解的方式，方法返回值都会自动注入到Spring容器中
    //RabbitMQ 中不存在 Binding、Queue、Exchange时，就会自动创建
    //RabbitMQ 中存在时，@Bean声明的属性发生变化时也不会覆盖

    /**
     * @return 收藏功能的消息队列的Queue
     */
    @Bean
    public Queue collectQueue() {
        /**
         * name：队列名
         * durable：队列是否持久化
         * exclusive：队列是否只对首次声明它的连接可见，并且在连接断开时自动删除
         * autoDelete：队列是否自动删除
         */
        Queue queue = new Queue("collect.queue",
                true,
                false, false);
        return queue;
    }

    /**
     * @return 收藏功能的消息队列的交换机
     */
    @Bean
    public Exchange collectExchange() {
        TopicExchange exchange = new TopicExchange("collect-exchange",
                true,
                false);

        return exchange;
    }

    /**
     * @return 收藏功能的消息队列的绑定关系
     */
    @Bean
    public Binding collectBinding() {
        /**
         * destination：绑定关系的目的地
         * DestinationType：绑定关系的类型
         * exchange：绑定关系的交换机名
         * routingKey：绑定关系的路由键
         * arguments：绑定关系的传递参数
         */
        Binding binding = new Binding("collect.queue",
                Binding.DestinationType.QUEUE,
                "collect-exchange",
                "collect.create",
                null);

        return binding;
    }
}
