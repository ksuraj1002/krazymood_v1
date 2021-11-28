package com.krazymood.app.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.channels.Channels;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Service
public class FirebaseService {
    FirebaseOptions firebaseOptions;
    Bucket bucket;
    Firestore firestore;

    @Value("${upload.location.path}")
    private String uploadLocation;

    @PostConstruct
    public void initialize() {
       // log.info("Initializing FireBase ...............");
        System.err.print("Initializing FireBase ...............");
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream(uploadLocation+"krazymood-22d16-firebase-adminsdk-6n0ed-b03b696e49.json");

            firebaseOptions = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                   // .setDatabaseUrl("easy-tutorials-images.firebaseio.com")
                    .setStorageBucket("krazymood-22d16.appspot.com")
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            bucket = StorageClient.getInstance().bucket();
            firestore = FirestoreClient.getFirestore();


          //  log.info(".................................... done :)");
            System.err.print(".................................... done :)");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String uDate = sdf.format(new Date());
        String fileName=uDate+multiPart.getOriginalFilename().substring(multiPart.getOriginalFilename().lastIndexOf('.'));
        return /*new Date().getTime() + "-" +*/ Objects.requireNonNull(fileName);
    }

    public byte[] fetchFile(String filename) {
        byte[] content = new byte[0];
        try {
            Blob blob = bucket.getStorage().get(BlobId.of(bucket.getName(), filename));
            ReadChannel reader = blob.reader();
            InputStream inputStream = Channels.newInputStream(reader);
            content = IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
             e.printStackTrace();
             System.err.print("File not found: "+filename);
        }
        return content;
    }


   /* public byte[] thumbnail(String filename) {
        byte[] content = new byte[0];
        try {
          //  log.info("thumbnailing File : " + filename);
            //log.info("Fetching from bucket : " + bucket.getName());
            Blob blob = bucket.getStorage().get(BlobId.of(bucket.getName(), filename));
            ReadChannel reader = blob.reader();
            InputStream inputStream = Channels.newInputStream(reader);
            //content = inputStream.readAllBytes();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(ImageIO.read(inputStream))
                    .size(400, 210)
                    .outputFormat("png")
                    .outputQuality(1)
                    .toOutputStream(outputStream);
            content = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            //log.info("File not found: "+filename);
        }
        return content;
    } */
}


