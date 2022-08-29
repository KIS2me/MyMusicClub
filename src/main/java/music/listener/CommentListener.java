package music.listener;

import com.rabbitmq.client.Channel;
import music.controller.CommentController;
import music.domain.Collect;
import music.domain.Comment;
import music.service.CollectService;
import music.service.CommentService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RabbitListener(queues = "comment.queue")
public class CommentListener {
    @Autowired
    private CommentService commentService;

    @RabbitHandler
    public void collectListener(Comment comment, Channel channel, Message message) throws IOException {
        try {
            //将消息中的Collect对象插入表
            commentService.insert(comment);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch(Exception e) {
            //消息出现异常后，拒绝接收消息，并将其重新放入队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
