package org.example.controlllers;
import org.example.entity.Machine;
import org.example.helper.StatusMachine;
import org.example.model.MachineModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MachineController {
    private MachineModel machineModel;

    public MachineController() {
        machineModel = new MachineModel();
    }

    public void createMachine() {
        String name = JOptionPane.showInputDialog("Enter the machine name:");
        String model = JOptionPane.showInputDialog("Enter the machine model:");
        String serialNumber = JOptionPane.showInputDialog("Enter the serial Number :");
        String status = JOptionPane.showInputDialog("Enter the  status :");


        boolean success = machineModel.registerMachine(name, model, serialNumber, status);

        if (success) {
            JOptionPane.showMessageDialog(null, "Machine registered successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register machine. Please try again.");
        }
    }

    public static List<Machine> getAllMachine (int pageNumber, int pageSize) {
        MachineModel objModel = new MachineModel();
        List <Machine> mymachineList = objModel.listMachinesWithPagination(pageNumber, pageSize);
        return mymachineList;
    }
}
