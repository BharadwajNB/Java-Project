package LeaveManagementSystem;

import java.io.*;
import java.util.ArrayList;

public class InspectData {
    public static void main(String[] args) {
        String filename = "employees.dat";
        File file = new File(filename);
        
        System.out.println("START INSPECTION");
        if (!file.exists()) {
            System.out.println("File does not exist!");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                ArrayList<?> list = (ArrayList<?>) obj;
                System.out.println("Count: " + list.size());
                for (Object item : list) {
                    if (item instanceof Employee) {
                        Employee e = (Employee) item;
                        System.out.println("FOUND EMPLOYEE -> ID: " + e.getId() + ", Name: '" + e.getName() + "'");
                    } else {
                        System.out.println("Unknown item type: " + item.getClass().getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
        }
        System.out.println("END INSPECTION");
    }
}
