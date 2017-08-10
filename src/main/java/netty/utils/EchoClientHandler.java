package netty.utils;

import java.nio.charset.Charset;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import proto.utils.PersonElem;
import proto.utils.PersonElem.Person;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

  /**
   * Handler implementation for the echo client.  It initiates the ping-pong
   * traffic between the echo client and server by sending the first message to
   * the server.
   */
  public class EchoClientHandler extends ChannelInboundHandlerAdapter {
  
      private final ByteBuf firstMessage;
      //private final String firstMessage;
  
      /**
       * Creates a client-side handler.
     * @throws InvalidProtocolBufferException 
       */
      public EchoClientHandler() throws InvalidProtocolBufferException {
          firstMessage = Unpooled.buffer(EchoClient.SIZE);
          Person obj = PersonElem.Person.newBuilder().setAge(3)
          .setName("Balaji") 
          .build();
          Gson gson = new Gson();
          String json = gson.toJson(obj); 
          System.out.println(json);
          String hell = "hello";
        for (int i = 0; i < json.length(); i ++) {
              firstMessage.writeByte((byte) json.charAt(i));
          }
      }
  
      @Override
      public void channelActive(ChannelHandlerContext ctx) {
          ctx.writeAndFlush(firstMessage);
      }
  
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) {
          ctx.write(msg);
      }
  
      @Override
      public void channelReadComplete(ChannelHandlerContext ctx) {
         ctx.flush();
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
          // Close the connection when an exception is raised.
          cause.printStackTrace();
          ctx.close();
      }
  }
