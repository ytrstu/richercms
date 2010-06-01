package com.sfeir.richercms.image.server.business;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.fileupload.FileItem;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity(name="MemoryFileItem")
@Unindexed
public class MemoryFileItem implements FileItem {

    private static final long serialVersionUID = 6873943621309250882L;
        
    @Id
    protected Long id;
    
    private String fieldName;
    
    protected String contentType;
    
    protected String fileName;
    
    @Transient
    private boolean isFormField;
    
    @Transient
    private ByteArrayOutputStream content_out;
    
    protected Blob content;
    
    protected Date date_created;
    
    public MemoryFileItem(){
	    this.fieldName = null;
	    this.contentType = null;
	    this.isFormField = false;
	    this.fileName = null;
	    this.content = null;
	    this.content_out = null;
	    this.date_created = null;
    }
        
    public MemoryFileItem(String fieldName, String contentType, boolean isFormField,
                    String fileName, int maxSize){
            this.fieldName = fieldName;
            this.contentType = contentType;
            this.isFormField = isFormField;
            this.fileName = fileName;
            this.content = null;
            this.content_out = (maxSize > 0) ? new ByteArrayOutputStream(maxSize) : new ByteArrayOutputStream();
            this.date_created = new Date();
    }
      
	public long getSize() {
    	return (content != null) ? content.getBytes().length : content_out.size();
    }

    public String getString() {
    	return (content != null) ? new String(content.getBytes()) : new String(content_out.toByteArray());
    }

    public String getString(String arg0) throws UnsupportedEncodingException {
    	return (content != null) ? new String(content.getBytes(), arg0) : new String(content_out.toByteArray(), arg0);
    }

    public boolean isFormField() {
    	return isFormField;
    }

    public boolean isInMemory() {
    	return true;
    }
    
	public void write(File arg0) throws Exception {
            // Unimplemented - can't use FileWriter in GAE.
    }

    public void commit(){
    	content = new Blob(get());
    }
    
    public void delete() {
        //Unimplemented - memory only, no cleaning up needed.
    }

    public byte[] get() {
    	return (content != null) ? content.getBytes() : content_out.toByteArray(); 
    }
    
    /**
     * This method can't be used if object was fetched from datastore.
     * */
    public OutputStream getOutputStream() throws IOException {
    	return content_out;
    }  
    
    public InputStream getInputStream() throws IOException {
        ByteArrayInputStream bais;
        if(content != null){
                bais = new ByteArrayInputStream(content.getBytes());
        } else {
                bais = new ByteArrayInputStream(content_out.toByteArray());
        }
        return bais;
    }    
    
    
///////////////////////////////////////// GETTER & SETTER  ///////////////////////////////////////////////
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ByteArrayOutputStream getContent_out() {
		return content_out;
	}

	public void setContent_out(ByteArrayOutputStream contentOut) {
		content_out = contentOut;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date dateCreated) {
		date_created = dateCreated;
	}

	public void setFormField(boolean isFormField) {
		this.isFormField = isFormField;
	}

    public String getName() {
        return fileName;
    }
}
