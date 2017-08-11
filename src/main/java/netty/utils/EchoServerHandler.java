package netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

import kafka.utils.KafkaProducerClass;
import proto.utils.PersonElem;
import proto.utils.PersonElem.Person;

import com.google.gson.Gson;
 
 /**
  * Handler implementation for the echo server.
  */
 @Sharable
 public class EchoServerHandler extends ChannelInboundHandlerAdapter {
 
     @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	 ByteBuf  buff= (ByteBuf)msg;
    	 KafkaProducerClass producer=new KafkaProducerClass();
    	 Gson gson = new Gson();
    	 String json =buff.toString(Charset.defaultCharset());
    	 Person obj2 = gson.fromJson(json, Person.class);  
    	  Person person1= PersonElem.Person.newBuilder()
                  .setAge(obj2.getAge())
                  .setName(obj2.getName()) 
                  .build();
        System.out.println(person1);
       
    	 ctx.write(msg);
    	 if(person1!=null){
    		 producer.sendMessage(person1.toString());
      	buff.release(); 
       channelReadComplete(ctx);
    	 //ctx.close();
     }
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
