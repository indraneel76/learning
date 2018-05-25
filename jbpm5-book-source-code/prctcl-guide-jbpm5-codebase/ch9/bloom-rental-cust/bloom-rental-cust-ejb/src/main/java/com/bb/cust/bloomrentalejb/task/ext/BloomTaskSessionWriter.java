package com.bb.cust.bloomrentalejb.task.ext;
 
import java.io.IOException;

import org.jbpm.task.service.SessionWriter;
 
public class BloomTaskSessionWriter implements SessionWriter
{
    public BloomTaskSessionWriter()
    {
        super();
    }
 
    @Override
    public void write(Object message) throws IOException
    {
        // No action needed for Bloom JMS implementation
        // but here is where you would communicate back
    	// to the client
    }
 
}