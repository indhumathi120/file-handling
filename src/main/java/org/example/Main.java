package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static Map<String, String> userCredentials = new HashMap<>();
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    static boolean flag = true;
    public static void hideFile(File folderPath) throws IOException {
        String command = "cmd.exe /c attrib +H \"" + folderPath + "\"";
        Runtime.getRuntime().exec(command);
        logger.info("Folder hidden successfully.");
    }

    public static void unhideFolder(File folderPath) throws IOException {
        String command = "cmd.exe /c attrib -H \"" + folderPath + "\"";
        Runtime.getRuntime().exec(command);
        System.out.println("Folder unhidden successfully.");
    }

    private static void signUp(Scanner scanner) {
        System.out.println("Sign Up");
        System.out.println("Enter your username:");
        String userName = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        userCredentials.put(userName, password);
        System.out.println("SignUp successful");
        flag = true;

    }

    private static void login(Scanner scanner) {
        System.out.println("Login");
        System.out.println("Enter your username:");
        String userName = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        if (userCredentials.containsKey(userName) && userCredentials.get(userName).equals(password)) {
            System.out.println("Login Succesfull");
            flag = true;
        } else {
            System.out.println("Invalid UserName or Password , Please try again");
        }
    }

    public static void createFile(Scanner scanner) {
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
    }

    public static void deleteFile(Scanner scanner) {
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
    }

    public static void hideFileMain(Scanner scanner) {
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
                System.out.println("File \"" + fileToHide.getName() + "\" has been hidden.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void displayAllExistingFiles(Scanner scanner) {
        File directory = new File("C:\\Users\\IndhuGane\\File_Handling\\");
        File[] fileList = directory.listFiles();
        int temp = 1;
        try {
            if (fileList != null) {
                for (File fileNew : fileList) {
                    System.out.println(temp + ") " + fileNew.getPath().toString());
                    temp++;
                }
            } else {
                System.out.println("No files found in the " + directory);
            }
        } catch (Exception e) {
            System.out.println("Error occurred while listing files :" + e.getMessage());
        }
    }

    public static void unHideFileMain(Scanner scanner) {
        String folderToUnHide_Path = "C:\\Users\\IndhuGane\\File_Handling\\"; // Specify the path to your folder here
        File folderNew = new File(folderToUnHide_Path);
        File[] filesnew = folderNew.listFiles();
        if (filesnew != null) {
            System.out.println("Files in folder");
            int temp = 1;
            for (int index = 0; index < filesnew.length; index++) {
                if (filesnew[index].isHidden()) {
                    logger.info(temp + ") " + filesnew[index].getName());
                    temp++;
                }
            }
        }
        logger.info("Enter the file name to Unhide");
        String fileNametoUnHide = scanner.nextLine();
//                    String filepathtoUnHide = folderToUnHide_Path + fileNametoUnHide;

        File fileToUhide = null;
        try {
            int fileNumbetToUnhide = Integer.parseInt(fileNametoUnHide);
            if (fileNumbetToUnhide > 0 && fileNumbetToUnhide <= filesnew.length) {
                fileToUhide = filesnew[fileNumbetToUnhide - 1];
            } else {
                System.out.println("Invalid file number");
            }
        } catch (NumberFormatException e) {
            for (File fileSpecific : filesnew) {
                if (fileSpecific.getName().equals(fileNametoUnHide)) {
                    fileToUhide = fileSpecific;
                    break;
                }
            }
            if (fileToUhide == null) {
                logger.info("File not found");
            }
        }
        if (fileToUhide != null) {
            try {
                unhideFolder(fileToUhide);
                logger.info("File \"" + fileToUhide.getName() + "\" has been Unhidden.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hiddenfiles(Scanner scanner) {
        File dir = new File("C:\\Users\\IndhuGane\\File_Handling\\");
        File[] fileDirectory = dir.listFiles(File::isHidden);
        List<File> fileStream = Arrays.stream(fileDirectory).toList();
        for (int index = 0; index < fileStream.size(); index++) {
            logger.info((index + 1) + ") " + fileStream.get(index).getName());
        }
    }

    public static void main(String[] args){

        boolean continueRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (continueRunning) {
            logger.info("File Management Options:");
            logger.info("1. Create File\n2. Delete File\n3. Hide File\n4. Unhide File\n5. List All Hidden Files\n6. List All the Existing Files");
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
                    createFile(scanner);
                    break;

                case 2:
                    deleteFile(scanner);
                    break;
                case 3:
                    hideFileMain(scanner);
                    break;
                case 4:
                    unHideFileMain(scanner);
                    break;
                case 5:
                    hiddenfiles(scanner);
                    break;
                case 6:
                    displayAllExistingFiles(scanner);
                    break;
                default:
                    logger.info("Please Enter the valid choice");
                    break;
            }
           logger.info("Do you want to continue? (Yes/No)");
            String continueChoice = scanner.nextLine();
            if(!continueChoice.equalsIgnoreCase("yes")){
                continueRunning=false;
            }
        }
    }
}