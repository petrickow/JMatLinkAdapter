/**
 * This class takes an input stream which deliver respiration data
 * Used with the StreamCreater
 *   
 */

package no.uio.taco.pukaMatControl.pukaReduced;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.List;

public class StreamGobbler implements Runnable {

	private Logger log; 
	private List<String> sharedBuffer;
	
	private String fileName = "signal.txt";
	private int port = 4444;

	private ByteBuffer receiveBuffer = ByteBuffer.allocate(100);


	
	public StreamGobbler(List<String> sharedBuffer) {
		log = Logger.getLogger(this.getClass());
		BasicConfigurator.configure();
		log.debug("init gobbler");
		this.sharedBuffer = sharedBuffer;
	}
	/*
	 * This requires the DataFeeder application to be running on the same host
	 * as it is running. We will connect using Network Channel from the Java NIO
	 * package
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		log.debug("We now wait for connection to 'sensor'");
		haltFor(1); // just to be able to read

		SocketChannel channel; // connection to DataFeeder 
		
		try {
			channel = intiateConnection();
			
			if (channel.isOpen()) {
				String message = readFromChannel(channel);
				if (sendAck(channel, message)) {
					receiveLoop(channel);
				}
			}
		} catch (IOException e) {
			//TODO: check error and handle appropriate (most likely connection exception)
			
			
			log.error(e.getMessage());
			resetShell();
		}
	}

	private SocketChannel intiateConnection() throws IOException {

		SocketChannel channel = SocketChannel.open();	

		// we open this channel in non blocking mode
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress("localhost", port));

		while (!channel.finishConnect()) {
			log.info("still connecting");
			haltFor(0.5);
		}
		
		
		return channel;
	}


	private String readFromChannel(SocketChannel channel) throws IOException {
		String message = "";
		while (message.length() == 0) {
			while (channel.read(receiveBuffer) > 0) {
				// flip the buffer to start reading
				receiveBuffer.flip();
				message += Charset.defaultCharset().decode(receiveBuffer);
			}
			receiveBuffer.clear();
		}
		
		return message;
	}

	/**
	 * Respond to initiation with ack and filename
	 * 
	 * @param message response we got from the server
	 * @return false if server has not respondend with 200, ergo nothing to ack
	 */
	private boolean sendAck(SocketChannel channel, String message) throws IOException {
		if (message.endsWith("200")) {
			// String[] split = message.split(","); // split and display connection status?
			
			CharBuffer buffer = CharBuffer.wrap(fileName + ",200");
			
			while (buffer.hasRemaining()) {
				channel.write(Charset.defaultCharset().encode(buffer));
			}
			return true;
		} else {
			// different status code from server, abort
			log.error(message);
			return false;
		}
		
	}

	/**
	 * The main workhorse of the class TODO: remove throw and handle abrupt disconnect
	 * @param channel
	 * @throws IOException
	 */
	private void receiveLoop(SocketChannel channel) throws IOException {
		for(;;) {
			String line = readFromChannel(channel);
			
			if (line.endsWith(",400")) {
				log.error(line);
				resetShell();
				break;
			}
			
			sharedBuffer.add(line);
			//TODO: init analysis
		}
		
	}
	
	
	private void resetShell() {
		System.out.print("$> ");
	}
	
	/**
	 * Wait method for seconds
	 * @param sec
	 */
	private synchronized void haltFor(double sec) {
		try {
			long ms = (long) sec * 1000;
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
