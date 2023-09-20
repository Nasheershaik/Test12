package com.kloc.crm.Controller;

import com.kloc.crm.Entity.Status;
import com.kloc.crm.Service.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class StatusControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StatusService statusService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StatusController(statusService)).build();
    }

    @Test
    public void testGetAllStatuses() throws Exception {
    	 // Arrange	
        // Mock a list of statuses and their expected properties.
        // Act and Assert
        // Perform a GET request to the "/app/statuses/getstatus" endpoint and verify the response.
        List<Status> statusList = new ArrayList<>();
        statusList.add(new Status("1", "Active", "Type1", "Table1", "Description1"));
        statusList.add(new Status("2", "Inactive", "Type2", "Table2", "Description2"));
        when(statusService.getAllStatuses()).thenReturn(statusList);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/app/statuses/getstatus"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(statusList.size()));
    }

    @Test
    public void testGetStatusById() throws Exception {
        // Arrange
        String statusId = "1";
        Status status = new Status(statusId, "Active", "Type1", "Table1", "Description1");

        when(statusService.findByid(statusId)).thenReturn(status);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/app/statuses/" + statusId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusId").value(status.getStatusId()));
    }

    @Test
    public void testUpdateStatusById() throws Exception {
        // Arrange
        String statusId = "1";
        Status status = new Status(statusId, "Active", "Type1", "Table1", "Description1");
        Status updatedStatus = new Status(statusId, "Inactive", "Type1", "Table1", "Updated Description");
        when(statusService.findByid(statusId)).thenReturn(status);
        when(statusService.saveStatus(any(Status.class))).thenReturn(updatedStatus);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/app/statuses/" + statusId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"statusValue\": \"Inactive\", \"statusDescription\": \"Updated Description\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusValue").value(updatedStatus.getStatusValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusDescription").value(updatedStatus.getStatusDescription()));
    }

    @Test
    public void testCreateStatus() throws Exception {
    	// Mock a new status and its expected properties.
        // Act and Assert
        // Perform a POST request to the "/app/statuses/createstatus" endpoint with status data and verify the response
        // Arrange
        Status status = new Status("1", "Active", "Type1", "Table1", "Description1");
        when(statusService.saveStatus(any(Status.class))).thenReturn(status);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/app/statuses/createstatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"statusValue\": \"Active\", \"statusDescription\": \"Description1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusValue").value(status.getStatusValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusDescription").value(status.getStatusDescription()));
    }
    
    @Test
    public void testDeleteStatusById() throws Exception {
    	 // Arrange
        // Mock a status to be deleted and its expected properties.
        // Act and Assert
        // Perform a DELETE request to the "/app/statuses/deletestatus/{statusId}" endpoint and verify the response.
        // Arrange
        String statusId = "1";
        Status status = new Status(statusId, "Active", "Type1", "Table1", "Description1");
        when(statusService.deleteByid(statusId)).thenReturn(status);
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/app/statuses/deletestatus/" + statusId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusId").value(status.getStatusId()));
    }
}

