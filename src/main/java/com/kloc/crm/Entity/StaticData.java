package com.kloc.crm.Entity;
import com.kloc.crm.Repository.StatusRepo;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class StaticData {
    private final StatusRepo statusRep;
    public StaticData(StatusRepo statusRep) {
        this.statusRep = statusRep;
    }
    public void insertStatusFromFile(String filePath) {
    try {
            Path path = Paths.get(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String statusDescription = fields[0].trim();
                String statusType = fields[1].trim();
                String statusValue = fields[2].trim();
                String tableName = fields[3].trim();
             if (!statusRep.existsByTableNameAndStatusTypeAndStatusValueAndStatusDescription(
                        tableName, statusType, statusValue, statusDescription)) {
                    Status status = new Status();
                    status.setTableName(tableName);
                    status.setStatusType(statusType);
                    status.setStatusValue(statusValue);
                    status.setStatusDescription(statusDescription);
                    Status save = statusRep.save(status);
                }
           }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
