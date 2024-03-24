package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> paths = new ArrayList<>();
        while (true) {
            System.out.println("File Management Options:");
            System.out.println("1. Create File\n2. Delete File\n3. Protect File\n4. Unlock File Permanently\n5. Hide File\n6. Unhide File\n7. List All Protected Files\n8. List All Hidden Files");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter file path to create");
                    String path = scanner.nextLine();
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
                    System.out.println("Enter the file path to delete");
                    String filePath = scanner.nextLine();
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
                    String folderPath = scanner.nextLine();

                    // Rename the folder to make it less visible
                    File folder = new File(folderPath);
                    File hiddenFolder = new File(folder.getParent(), "." + folder.getName());

                    if (folder.exists()) {
                        if (folder.renameTo(hiddenFolder)) {
                            paths.add(hiddenFolder.getAbsolutePath());
                            System.out.println("Folder hidden successfully.");
                        } else {
                            System.out.println("Failed to hide the folder.");
                        }
                    } else {
                        System.out.println("Folder does not exist.");
                    }
                    break;
                case 6:
                    String pathUnhide = scanner.nextLine();
                    File hiddenFolderNew = new File(pathUnhide);
                    if (hiddenFolderNew.exists()) {       // folder name length should be greater than 8
                        String originalFolderPath = hiddenFolderNew.getParent() + File.separator + hiddenFolderNew.getName().substring(8);
                        File originalFolder = new File(originalFolderPath);

                        if (hiddenFolderNew.renameTo(originalFolder)) {
                            System.out.println("Folder un-hided successfully");
                        } else {
                            System.out.println("Failed to un-hide the folder");
                        }
                   }
                    else {
                        System.out.println("Hidden folder doesn't exist");
                    }
                    break;
                case 8:
                    System.out.println("List of hidden files :");
                    if(paths.size() >0){
                    for (String demo : paths) {
                        System.out.println(demo);
                    }
                }
                    else {
                        System.out.println("There is no hidden files");
                    }
                    break;
            }
        }
    }
}