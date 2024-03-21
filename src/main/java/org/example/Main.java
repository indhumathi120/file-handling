package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("File Management Options:");
            System.out.println("1. Create File\n2. Delete File\n3. Protect File\n4. Unlock File Permanently\n5. Hide File\n6. Unhide File\n7. List All Protected Files\n8. List All Hidden Files");
            Integer choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    String path = "C:\\Users\\IndhuGane\\File_Handling\\Files\\file1.text";
                    File file = new File(path);
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
                    String filePath = "C:\\Users\\IndhuGane\\File_Handling\\Files\\file.text";
                    File fileDelete = new File(filePath);
                    try {
                        if (fileDelete.exists()) {
                            boolean isDelete = fileDelete.delete();
                            if (isDelete) {
                                System.out.println("File Deleted");
                            } else {
                                System.out.println("Failed to existing file");
                            }
                        } else {
                            System.out.println("File not exists");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 5:
                    String folderPath = "C:\\Users\\IndhuGane\\File_Handling\\Files";

                    // Rename the folder to make it less visible
                    File folder = new File(folderPath);
                    File hiddenFolder = new File(folder.getParent(), "." + folder.getName());

                    if (folder.exists()) {
                        if (folder.renameTo(hiddenFolder)) {
                            System.out.println("Folder hidden successfully.");
                        } else {
                            System.out.println("Failed to hide the folder.");
                        }
                    } else {
                        System.out.println("Folder does not exist.");
                    }
                    break;
                case 6:
                    String pathUnhide = "C:\\Users\\IndhuGane\\File_Handling\\Files";
                    File hiddenFolderNew = new File(pathUnhide);
                    if(hiddenFolderNew.exists()){
                        String originalFolderpath = hiddenFolderNew.getParent() +File.separator +hiddenFolderNew.getName().substring(8);
                        File originalFolder = new File(originalFolderpath);

                        if(hiddenFolderNew.renameTo(originalFolder)){
                            System.out.println("Folder unhided successfully");
                        }
                        else {
                            System.out.println("Failed to unhide the folder");
                        }
                    }
                    else{
                        System.out.println("Hiddem folder doesn't exist");
                    }

            }
        }
    }
}