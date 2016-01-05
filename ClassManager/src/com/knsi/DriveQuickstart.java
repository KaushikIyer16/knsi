/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.knsi;

/**
 *
 * @author Suma Latha
 */
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class DriveQuickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quick start. */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY,DriveScopes.DRIVE,"https://www.googleapis.com/auth/drive.install",
"https://www.googleapis.com/auth/drive.file","profile");
        
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        FileInputStream ins=new FileInputStream(new java.io.File("client_secret.json"));
        //InputStream in =
          //  DriveQuickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(ins));

        // Build flow and trigger user authorization request.
        /*thia portoikn of the code determines the portion of the code that 
        actually appears on the user browser*/
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Drive service = getDriveService();
       File fileMetadata = new File();
        fileMetadata.setTitle("Details.xlsx");
        fileMetadata.setMimeType("application/vnd.google-apps.spreadsheet");
        
        
       
        System.out.println(service.changes());
        Channel c= new Channel();
        System.out.println(c.getId());
       /* File f = service.files().insert(fileMetadata,new InputStreamContent(null,new FileInputStream(new java.io.File("details.xlsx")) ))
                .setFields("id")
                .execute();
        System.out.println("File ID: " + f.getId());*/
        //System.out.println(f1.getExpiration());
        File f2=service.files().get("1xsvXMOYLBFrt7g7FfC9lcdVm0QavRxASKStcMHNXAtM").execute();
        f2.setTitle("Details.xlsx");
        f2.setMimeType("application/vnd.google-apps.spreadsheet");
        java.io.File filecontent = new java.io.File("details.xlsx");
        FileContent mediacontent = new FileContent(null, filecontent);
        File f= service.files().update("1xsvXMOYLBFrt7g7FfC9lcdVm0QavRxASKStcMHNXAtM", f2, mediacontent).execute();
        System.out.println("File ID: " + f.getId());
        System.out.println(service.getBaseUrl());
        System.out.println("-------------------------------\n"+service.getJsonFactory());
        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
             .execute();
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
            }
        }
    }

}