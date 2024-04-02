package org.example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
//import jcifs.smb.NtlmPasswordAuthentication;
//import jcifs.smb.SmbFile;
import java.io.*;
import java.util.stream.Stream;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

public class Main {
    private static Map<String, String> userCredentials = new HashMap<>();
    private static boolean flag = false;

    public static void hideFile(File folderPath) throws IOException {
        String command = "cmd.exe /c attrib +H \"" + folderPath + "\"";
        Runtime.getRuntime().exec(command);
        System.out.println("Folder hidden successfully.");
    }

    public static void unhideFolder(String folderPath) throws IOException {
        String command = "cmd.exe /c attrib -H \"" + folderPath + "\"";
        Runtime.getRuntime().exec(command);
        System.out.println("Folder unhidden successfully.");
    }
    private static void signUp(Scanner scanner){
        System.out.println("Sign Up");
        System.out.println("Enter your username:");
        String userName= scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        userCredentials.put(userName,password);
        System.out.println("SignUp successful");
        flag = true;

    }
    private static void login(Scanner scanner){
        System.out.println("Login");
        System.out.println("Enter your username:");
        String userName= scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        if(userCredentials.containsKey(userName) && userCredentials.get(userName).equals(password)){
            System.out.println("Login Succesfull");
            flag = true;
        }
        else{
            System.out.println("Invalid UserName or Password , Please try again");
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<File> paths = new ArrayList<>();
        while (true) {
            System.out.println("File Management Options:");
            System.out.println("1. Create File\n2. Delete File\n3. Hide File\n4. Unhide File\n5. List All Hidden Files\n6.List All the Existing Files");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 100:
                    signUp(scanner);
                    break;
                case 200:
                    login(scanner);
                    break;
                // trying to check for account if not cant perfomr any further cases like 1 2 3 4
                case 1:
                    String hardCodePath = "C:\\Users\\IndhuGane\\File_Handling\\";
                    System.out.println("Enter file name to create");
                    String path = scanner.nextLine();
                    String result = hardCodePath + path;
                    File file = new File(result);
                    try {
                        boolean flag = file.createNewFile();
                        if (flag) {
                            System.out.println("File created");
                        } else {
                            System.out.println("File already exists");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    String hardCodePathToDel = "C:\\Users\\IndhuGane\\File_Handling\\";
                    //String hardCodePathToDel = "C:\\Users\\IndhuGane\\File_Handling\\";
                    System.out.println("Enter the file name to delete");
                    String fileName = scanner.nextLine();
                    if (fileName.isEmpty()) {
                        System.out.println("Invalid File name.");
                        return;
                    }
                    String filePath = hardCodePathToDel + fileName;
                    File fileToDelete = new File(filePath);
                    try {
                        if (fileToDelete.exists()) {
                            boolean isDelete = fileToDelete.delete();
                            if (isDelete) {
                                System.out.println("File Deleted");
                            } else {
                                System.out.println("Failed to delete file");
                            }
                        } else {
                            System.out.println("File not exists");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 3:
                    String folderToHide_Path = "C:\\Users\\IndhuGane\\File_Handling\\";
                    File folder = new File(folderToHide_Path);
                    File[] files = folder.listFiles();
                    List<File> onlyUnHiddenFiles = new ArrayList<>();
                    if (files != null) {
                        int temp = 0;
                        System.out.println("Files in the folder to hide:");
                        for (int i = 0; i < files.length; i++) {
                            if (!files[i].isHidden()) {
                                onlyUnHiddenFiles.add(files[i]);
                                System.out.println((temp + 1) + ") " + files[i].getName());
                                temp++;
                            }
                        }
                    }

                    System.out.println("Enter the number or name of the file to hide:");
                    String userInput = scanner.nextLine();

                    File fileToHide = null;
                    try {
                        int fileNumber = Integer.parseInt(userInput);
                        if (fileNumber > 0 && fileNumber <= onlyUnHiddenFiles.size()) {
                            fileToHide = onlyUnHiddenFiles.get(fileNumber - 1);
                        } else {
                            System.out.println("Invalid file number.");
                        }
                    } catch (NumberFormatException e) {
                        // If input is not a number, try to match with file names
                        for (File file_specific : onlyUnHiddenFiles) {
                            if (file_specific.getName().equals(userInput)) {
                                fileToHide = file_specific;
                                break;
                            }
                        }
                        if (fileToHide == null) {
                            System.out.println("File not found.");
                        }
                    }

                    if (fileToHide != null) {
                        try {
                            // Hide the file
                            hideFile(fileToHide);
                            paths.add(fileToHide);
                            System.out.println("File \"" + fileToHide.getName() + "\" has been hidden.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Closing the scanner
                    break;


                case 4:
                    String folderToUnHide_Path = "C:\\Users\\IndhuGane\\File_Handling\\"; // Specify the path to your folder here
                    System.out.println("Enter the file name to Unhide");
                    String fileNametoUnHide = scanner.nextLine();
                    String filepathtoUnHide = folderToUnHide_Path + fileNametoUnHide;

                    try {
                        // Uncomment the below line to unhide the folder
                        unhideFolder(filepathtoUnHide);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
//                case 3:
//                    String url = scanner.nextLine();
//                    String username = "USER";
//                    String password = "PASSWORD";
//
//                    try {
//                        // Convert SMB URL to Path
//                        Path smbPath = Paths.get(url);
//
//                        // Set up authentication if needed
//                        if (username != null && password != null) {
//                            smbPath = smbPath.resolve(username + ":" + password + "@");
//                        }
//
//                        // Use DirectoryStream to list files
//                        try (DirectoryStream<Path> stream = Files.newDirectoryStream(smbPath)) {
//                            for (Path entry : stream) {
//                                System.out.println(entry.getFileName());
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case 4:
//                    String folderToUnProtect_url = "smb://yourhost/yourpath/";
//
//                    try {
//                        URL smbUrl = new URL(folderToUnProtect_url);
//                        URLConnection connection = smbUrl.openConnection();
//                        InputStream inputStream = connection.getInputStream();
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            System.out.println(line);
//                        }
//
//                        reader.close();
//                        inputStream.close();
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                case 13:
//                    String folderPath = scanner.nextLine();
//
//                    // Rename the folder to make it less visible
//                    File folder = new File(folderPath);
//                    File hiddenFolder = new File(folder.getParent(), "." + folder.getName());
//
//                    if (folder.exists()) {
//                        if (folder.renameTo(hiddenFolder)) {
//                            paths.add(hiddenFolder.getAbsolutePath());
//                            System.out.println("Folder hidden successfully.");
//                        } else {
//                            System.out.println("Failed to hide the folder.");
//                        }
//                    } else {
//                        System.out.println("Folder does not exist.");
//                    }
//                    break;
//                case 14:
//                    String pathUnhide = scanner.nextLine();
//                    File hiddenFolderNew = new File(pathUnhide);
//                    if (hiddenFolderNew.exists()) {       // folder name length should be greater than 8
//                        String originalFolderPath = hiddenFolderNew.getParent() + File.separator + hiddenFolderNew.getName().substring(8);
//                        File originalFolder = new File(originalFolderPath);
//
//                        if (hiddenFolderNew.renameTo(originalFolder)) {
//                            System.out.println("Folder un-hided successfully");
//                        } else {
//                            System.out.println("Failed to un-hide the folder");
//                        }
//                   }
//                    else {
//                        System.out.println("Hidden folder doesn't exist");
//                    }
//                    break;
                case 5:
                    File dir = new File("C:\\Users\\IndhuGane\\File_Handling\\");
                    File[] fileDirectory = dir.listFiles(File::isHidden);
                    List<File> fileStream = Arrays.stream(fileDirectory).toList();
                    for (int index = 0; index < fileStream.size(); index++) {
                        System.out.println((index + 1) + ") " + fileStream.get(index).getName());
                    }
                    break;
                case 6:
//                    File displayAllFiles = new File("C:\\Users\\IndhuGane\\File_Handling\\");
//                    if (displayAllFiles.isHidden())
//                        System.out.println(
//                                "The specified file is hidden");
//                    else
//                        System.out.println("The specified file is not hidden");
//                    break;
                    File directory = new File("C:\\Users\\IndhuGane\\File_Handling\\");
                    File[] fileList = directory.listFiles();
                    int temp=1;
                    if (fileList != null) {
                        for (File fileNew : fileList) {
                            System.out.println(temp+") "+fileNew.toString());
                            temp++;
                        }
                    }
                    break;
                default:
                    System.out.println("Please Enter the valid choice");
                    break;
            }
        }
    }
}