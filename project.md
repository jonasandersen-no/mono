# Mono

## OpenAI

### ChatThread
- Create thread using accountId
- Find thread by threadId
- Delete thread by threadId
  - When a thread is deleted all messages in the thread should be deleted as well.
### Chat Message
- Send message via websocket
  - User need to have an active chat thread before sending a message
  - User can not send a message to a thread that they dont own
  - AI will answer after each chat message.
  - User needs a way to show that the AI is typing
    - Was thinking of sending each character via websocket to simulate the AI typing
    - Store the message to the database after the whole message is sent
  - Link each message to a chat thread
    - Sort the messages by date to show the chat history