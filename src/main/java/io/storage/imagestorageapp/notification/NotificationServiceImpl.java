package io.storage.imagestorageapp.notification;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.*;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

import io.storage.imagestorageapp.config.props.SNSClientProps;
import io.storage.imagestorageapp.config.props.SQSClientProps;
import io.storage.imagestorageapp.utils.LambdaMethods;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService, NotificationEvent {
    private static final String SNS_PROTOCOL = "email";

    private final SNSClientProps snsClientProperties;
    private final SQSClientProps sqsClientProperties;
    private final AmazonSNS snsClient;
    private final AmazonSQS sqsClient;

    @Override
    public void subscribe(String email) {
        Try.run(() -> {
            SubscribeRequest request = new SubscribeRequest()
                    .withProtocol(SNS_PROTOCOL)
                    .withEndpoint(email)
                    .withTopicArn(snsClientProperties.getTopicArn());
            snsClient.subscribe(request);
        }).getOrElseThrow(LambdaMethods::throwable);
    }

    @Override
    public void unsubscribe(String email) {
        Try.run(() -> {
            ListSubscriptionsByTopicResult subscribers =
                    snsClient.listSubscriptionsByTopic(snsClientProperties.getTopicArn());

            List<Subscription> subscriptions = subscribers.getSubscriptions();

            subscriptions.stream()
                    .filter(subscription -> email.equals(subscription.getEndpoint()))
                    .findAny()
                    .ifPresent(subscription -> unsubscribeRequest(subscription.getSubscriptionArn()));

        }).getOrElseThrow(LambdaMethods::throwable);
    }

    @Override
    public void sendMessageToQueue(String message) {
        Try.run(() -> {
            SendMessageRequest request = new SendMessageRequest()
                    .withQueueUrl(sqsClientProperties.getQueueUrl())
                    .withMessageBody(message)
                    .withDelaySeconds(5);
            sqsClient.sendMessage(request);
        }).getOrElseThrow(LambdaMethods::throwable);
    }

    @Override
    public void sendMessageToTopic(String message) {
        Try.run(() -> {
                    PublishRequest publishRequest = new PublishRequest()
                            .withMessage(message)
                            .withTopicArn(snsClientProperties.getTopicArn());
                    snsClient.publish(publishRequest);
                })
                .getOrElseThrow(LambdaMethods::throwable);
    }

    public List<Message> readMessages() {
        return Try.of(() -> {
                    String queueUrl = sqsClientProperties.getQueueUrl();

                    ReceiveMessageRequest request = new ReceiveMessageRequest()
                            .withQueueUrl(queueUrl)
                            .withWaitTimeSeconds(10)
                            .withMaxNumberOfMessages(10);

                    List<Message> messages = sqsClient.receiveMessage(request).getMessages();

                    messages.stream()
                            .map(Message::getReceiptHandle)
                            .forEach(receipt -> sqsClient.deleteMessage(queueUrl, receipt));
                    return messages;
                }
        ).getOrElseThrow(LambdaMethods::throwable);
    }

    private void unsubscribeRequest(String subscriptionArn) {
        Try.run(() -> {
            UnsubscribeRequest unsubscribeRequest = new UnsubscribeRequest()
                    .withSubscriptionArn(subscriptionArn);
            snsClient.unsubscribe(unsubscribeRequest);
        }).getOrElseThrow(LambdaMethods::throwable);
    }
}
