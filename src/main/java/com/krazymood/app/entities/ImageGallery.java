package com.krazymood.app.entities;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity(name = "image_gallery")
public class ImageGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String filename;
    LocalDateTime  uploadedOn=LocalDateTime.now();
    


	public ImageGallery(String filename) {
        this.filename = filename;
    }
	
	public ImageGallery() {

	}
    

    public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public LocalDateTime getUploadedOn() {
		return uploadedOn;
	}



	public void setUploadedOn(LocalDateTime uploadedOn) {
		this.uploadedOn = uploadedOn;
	}


}