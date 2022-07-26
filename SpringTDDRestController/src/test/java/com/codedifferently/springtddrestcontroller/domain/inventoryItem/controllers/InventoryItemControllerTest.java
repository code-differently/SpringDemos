package com.codedifferently.springtddrestcontroller.domain.inventoryItem.controllers;

import com.codedifferently.springtddrestcontroller.domain.core.ResourceCreationException;
import com.codedifferently.springtddrestcontroller.domain.core.ResourceNotFoundException;
import com.codedifferently.springtddrestcontroller.domain.inventoryItem.models.InventoryItem;
import com.codedifferently.springtddrestcontroller.domain.inventoryItem.services.InventoryItemService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class InventoryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryItemService inventoryItemService;

    private InventoryItem mockItem;
    private InventoryItem mockSavedItem;

    @BeforeEach
    public void setUp(){
        mockItem = new InventoryItem("MyTestItem", "This is a Test Item");
        mockSavedItem = new InventoryItem("MyTestItem", "This is a Test Item");
        mockSavedItem.setId(1l);
    }

    @Test
    @DisplayName("Inventory Item Create - /api/v1/inventory : success")
    public void createInventoryItemRequestSuccess() throws Exception{
        BDDMockito.doReturn(mockSavedItem).when(inventoryItemService).create(mockItem);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.asJsonString(mockItem)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    @DisplayName("Inventory Item Create - /api/v1/inventory : failed")
    public void createInventoryItemRequestFailed() throws Exception {
        BDDMockito.doThrow(new ResourceCreationException("Exists")).when(inventoryItemService).create(mockItem);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/inventory")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonConverter.asJsonString(mockItem)))
                    .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("Get All /api/v1/inventory : success")
    public void getAllInventoryItemSuccess() throws Exception{
        List<InventoryItem> itemList = new ArrayList<>();
        itemList.add(mockSavedItem);
        BDDMockito.doReturn(itemList).when(inventoryItemService).getAll();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Get by id /api/v1/inventory/{id} : success")
    public void getByIdSuccess() throws Exception {
        BDDMockito.doReturn(mockSavedItem).when(inventoryItemService).getById(1l);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("MyTestItem")));
    }

    @Test
    @DisplayName("Get by id /api/v1/inventory/{id} : fail")
    public void getByIdFails() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(inventoryItemService).getById(1l);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Get by name /api/v1/inventory/findByName/{name} : success")
    public void getByNameSuccess() throws Exception {
        BDDMockito.doReturn(mockSavedItem).when(inventoryItemService).getByName("MyTestItem");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/findByName/{name}", "MyTestItem"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("MyTestItem")));
    }

    @Test
    @DisplayName("Get by name /api/v1/inventory/findByName/{name} : fail")
    public void getByNameFails() throws Exception {
        BDDMockito.doThrow(new ResourceNotFoundException("Not found")).when(inventoryItemService).getByName("MyTestItem");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/findByName/{name}", "MyTestItem"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/v1/inventory - Success")
    public void putWidgetTestNotSuccess() throws Exception{
        InventoryItem mockUpdatedItem = new InventoryItem("MyTestItemUpdated", "This is a Test Item");
        mockUpdatedItem.setId(1l);
        BDDMockito.doReturn(mockUpdatedItem).when(inventoryItemService).update(1l, mockSavedItem);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/inventory/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.asJsonString(mockSavedItem)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Is.is("MyTestItemUpdated")));
    }

    @Test
    @DisplayName("PUT /api/v1/inventory/{id} - Not Found")
    public void putWidgetTestNotFound() throws Exception{
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(inventoryItemService).update(1l, mockSavedItem);;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/inventory/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.asJsonString(mockSavedItem)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/inventory/{id} - Success")
    public void deleteWidgetTestNotSuccess() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/inventory/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/inventory/{id}- Not Found")
    public void deleteWidgetTestNotFound() throws Exception{
        BDDMockito.doThrow(new ResourceNotFoundException("Not Found")).when(inventoryItemService).delete(1l);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/inventory/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
