package jadex.standalone.transport.niotcpmtp;

import jadex.commons.SUtil;
import jadex.standalone.transport.MessageEnvelope;
import jadex.standalone.transport.codecs.CodecFactory;
import jadex.standalone.transport.codecs.IEncoder;
import jadex.standalone.transport.niotcpmtp.NIOTCPTransport.Cleaner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *  NIO-TCP output connection for sending messages to a specific target address. 
 */
class NIOTCPOutputConnection
{
	//-------- constants --------
	
	/** 5 sec timeout. */
	public static final int TIMEOUT = 5000;
	
	/** 2MB as message buffer. */
	public static final int BUFFER_SIZE = 2* 1024 * 1024;
	
	//-------- attributes --------
	
	/** The client socket for sending data. */
	protected SocketChannel sc;
	
	/** The buffer. */
//	protected ByteBuffer buffer;
	
	/** The dead connection time. */
	protected long deadtime;
	
	/** The codec factory. */
	protected CodecFactory codecfac;

	/** The cleaner. */
	protected Cleaner cleaner;
	
	/** The classloader. */
	protected ClassLoader classloader;

	//-------- constructors --------
	
	/**
	 *  Create a new tcp connection for sending data. 
	 *  @param iaddr
	 *  @param iport
	 *  @throws IOException
	 */
	public NIOTCPOutputConnection(InetAddress iaddr, int iport, CodecFactory codecfac, 
		Cleaner cleaner, ClassLoader classloader) throws IOException
	{
		this.codecfac = codecfac;
		this.cleaner = cleaner;
		this.classloader = classloader;
		
		// Create a non-blocking socket channel
	    this.sc = SocketChannel.open();
	    
	    // todo: perform sending asynchronous to caller thread
	    //this.sc.configureBlocking(false);
	  
//		try
//		{
//			System.out.println("NIOTCP Connection: "+iaddr+":"+iport);
		    // Kick off connection establishment
//		    this.sc.connect(new InetSocketAddress(iaddr, iport));	// Requires this for non blocking (what about timeouts?) 
			sc.socket().connect(new InetSocketAddress(iaddr, iport) , TIMEOUT);	// Doesn't work for non blocking.
//			System.out.println("NIOTCP Connection: "+iaddr+":"+iport+" established");
//		}
//		catch(IOException e)
//		{
//			System.out.println("NIOTCP Connection: "+iaddr+":"+iport+" failed");
////			e.printStackTrace();
//			throw e;
//		}

//	    this.buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
	    
		//address = SMTransport.SERVICE_SCHEMA+iaddr.getHostAddress()+":"+iport;
	 }

	//-------- methods --------
	
	/**
	 *  Send a message.
	 *  @param msg The message.
	 *  Sending is done synchronously on caller thread.
	 *  (todo: relax synchronization by performing sends 
	 *  on extra sender thread of transport, only needed
	 *  if message service is used in synchronous mode.)
	 */
	public synchronized void send(MessageEnvelope msg) throws IOException
	{
		// Code using preallocated buffer
//		IEncoder enc = codecfac.getDefaultEncoder();
//		byte codec_id = codecfac.getCodecId(enc.getClass());
//		byte[] enc_msg = enc.encode(msg, classloader);
//		int size = enc_msg.length+NIOTCPTransport.PROLOG_SIZE;
//		buffer.put(codec_id);
//		buffer.putInt(size);
//		buffer.put(enc_msg);
//		buffer.flip();
//		sc.write(buffer);
//		buffer.clear();
//		cleaner.refresh();
		
		// Code using new buffers
		IEncoder	enc	= codecfac.getDefaultEncoder();
		byte	codec_id	= codecfac.getCodecId(enc.getClass());
		byte[]	enc_msg	= enc.encode(msg, classloader);
		byte[]	buffer	= new byte[enc_msg.length+NIOTCPTransport.PROLOG_SIZE];
		System.arraycopy(enc_msg, 0, buffer, NIOTCPTransport.PROLOG_SIZE, enc_msg.length);
		System.arraycopy(SUtil.intToBytes(buffer.length), 0, buffer, 1, 4);
		buffer[0]	= codec_id;
		sc.write(ByteBuffer.wrap(buffer));
		cleaner.refresh();
	}
	
	/**
	 *  Close the connection.
	 */
	public void close()
	{
		try
		{
			sc.close();
		}
		catch(IOException e)
		{
			//e.printStackTrace();
		}
		cleaner.remove();
	}
}
